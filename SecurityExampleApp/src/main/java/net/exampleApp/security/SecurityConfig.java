package net.exampleApp.security;

import net.exampleApp.security.CustomAuthenticationProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

	@Autowired 
	private CustomAuthenticationProvider customAuthProvider;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception
	{
	
		// This is handy if you need to implement your own custom authentication and authorization
		auth.authenticationProvider(customAuthProvider);
			
	}

	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
		// note that "app/admin/**" fails
		// note that "admin/**" fails
		// note that "**admin/**" fails
		.antMatchers("/app/admin/**").hasRole("ADMIN").anyRequest()
		.authenticated().and().formLogin().loginPage("/loginPage")
		.defaultSuccessUrl("/app/home").permitAll().and().logout()
		// this allows you to logout with a GET, but a GET request logout
		// isn't the best approach as a malicious user could log other users
		// out.
		// You can omit this line to force POST logouts only via the default
		// logout
		// url of "/j_spring_security_logout"
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
	}
}
