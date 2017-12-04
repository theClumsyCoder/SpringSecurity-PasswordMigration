package net.exampleApp.domain;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Users implements UserDetails
{
	private static final long serialVersionUID = 7512498128332506892L;

	private Set<Authorities> authorities;
	private String username;
	private String password;
	private Boolean enabled;

	public Boolean getEnabled()
	{
		return enabled;
	}

	public void setEnabled(Boolean enabled)
	{
		this.enabled = enabled;
	}

	public void setAuthorities(Set<Authorities> authorities)
	{
		this.authorities = authorities;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.authorities;
	}

	public String getPassword()
	{
		return this.password;
	}

	public String getUsername()
	{
		return this.username;
	}

	public boolean isAccountNonExpired()
	{ 
		return this.enabled;
	}

	public boolean isAccountNonLocked()
	{
		return this.enabled;
	}

	public boolean isCredentialsNonExpired()
	{
		return this.enabled;
	}

	public boolean isEnabled()
	{
		return this.enabled;
	}

}
