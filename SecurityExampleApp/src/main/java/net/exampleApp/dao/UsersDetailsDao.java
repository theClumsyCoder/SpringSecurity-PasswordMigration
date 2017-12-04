package net.exampleApp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import net.exampleApp.domain.Authorities;
import net.exampleApp.domain.Users;

@Repository
public class UsersDetailsDao implements UserDetailsService
{
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException
	{   
		try{
			
			String sql = "select * from users where username = ?";
			Users user = (Users)getJdbcTemplate().queryForObject(sql, new Object[] { username }, new RowMapper<Users>()
			{
				public Users mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					Users user = new Users();
					if (user.getEnabled() == null)
						user.setEnabled(rs.getBoolean("enabled"));
					if (user.getUsername() == null)
						user.setUsername(rs.getString("username"));
					if (user.getPassword() == null)
						user.setPassword(rs.getString("password"));
					return user;
				}
			});

			sql = "select authority from authorities where username = ?";
			List<String> authorities = getJdbcTemplate().queryForList(sql, new Object[] {username}, String.class);
			Set<Authorities> userAuths = new HashSet<Authorities>();
			for (String authority : authorities)
			{
				userAuths.add(new Authorities(username, authority));
			}
			user.setAuthorities(userAuths);

			return user;
			
		}catch(EmptyResultDataAccessException e){ //User does not exist in database
			return null;
		}

	}

	private JdbcTemplate getJdbcTemplate ()
	{
		if (this.jdbcTemplate == null)
			return new JdbcTemplate(dataSource);
		else 
			return jdbcTemplate;
	}
}
