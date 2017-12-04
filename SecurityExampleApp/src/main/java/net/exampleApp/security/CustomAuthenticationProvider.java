package net.exampleApp.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.exampleApp.dao.UsersDetailsDao;
import net.exampleApp.domain.Users;

@Component 
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private UsersDetailsDao userSerivce;
	private JdbcTemplate jdbcTemplate;
	private Pbkdf2PasswordEncoder pbkdf2;
	private static final Logger logger = Logger.getLogger(CustomAuthenticationProvider.class.getName());

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		//Retrieve user provided username and password from authentication request
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		//Retrieve User credentials from database and store in User domain object
		//If user doesn't exist in database throw exception
		Users user = (Users)userSerivce.loadUserByUsername(name);

		if(user!=null){

			//Check if password stored in database is pbkdf2 or md5
			//This custom implementation looks at the size of the 
			//message digest to actually determine whether to use md5 or pbdfk2  
			//for authentication verification
			if(user.getPassword().length()==24){ //MD5 verification and migration

				try {
					//convert the user provided password to MD5 and compare it with retrieved password
					MessageDigest MD5Hash = MessageDigest.getInstance("MD5");
					MD5Hash.update(password.getBytes());
					byte[] digest = MD5Hash.digest();
					String hash = Base64.getEncoder().encodeToString(digest);

					if(hash.equals(user.getPassword())){
						//Change MD5 to pbkdf2

						pbkdf2 = new Pbkdf2PasswordEncoder();
						String encodedPwd = pbkdf2.encode(password);

						String sql = "Update users set password = ? where username = ?";
						int row = getJdbcTemplate().update(sql, encodedPwd, name);

						if(row==1){
							//Successfully migrated user from MD5 to PBKDF2
							return new UsernamePasswordAuthenticationToken(name, encodedPwd, user.getAuthorities());
						}

						throw new BadCredentialsException("An error occurred. Please try again.");	
					}

					throw new BadCredentialsException("Username or Password is Incorrect!");

				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					logger.log(Level.SEVERE, "No Algorithm Exception", e); //Log Exception 
				}
			}else { //pbkdf2 verification
				pbkdf2 = new Pbkdf2PasswordEncoder();
				if(pbkdf2.matches(password, user.getPassword())){ //check if pbkdf2 passwords match
					return new UsernamePasswordAuthenticationToken(name, password, user.getAuthorities()); //successful then return auth object
				} //else throw exception
				throw new BadCredentialsException("Username or Password is Incorrect!");
			}
		}else{ //User does not exist in database
			throw new BadCredentialsException("Username or Password is Incorrect!");
		}

		return null;
	}

	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication));
	}

	private JdbcTemplate getJdbcTemplate ()
	{
		if (this.jdbcTemplate == null)
			return new JdbcTemplate(dataSource);
		else 
			return jdbcTemplate;
	}

}
