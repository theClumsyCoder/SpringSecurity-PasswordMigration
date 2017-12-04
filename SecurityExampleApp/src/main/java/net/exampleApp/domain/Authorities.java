package net.exampleApp.domain;

import org.springframework.security.core.GrantedAuthority;

public class Authorities implements GrantedAuthority
{
  private static final long serialVersionUID = -1973394345889425952L;
  
	private String username;
  private String authority;
  
  public Authorities (String username, String authority)
  {
  	this.username = username;
  	this.authority = authority;
  }
  
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getAuthority()
	{
		return authority;
	}
	public void setAuthority(String authority)
	{
		this.authority = authority;
	}
}
