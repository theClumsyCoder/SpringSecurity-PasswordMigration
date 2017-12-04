package net.exampleApp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController
{
	@RequestMapping(value="/loginPage", method=RequestMethod.GET)
	public String loginPage (ModelMap model)
	{
		return "loginPage";
	}
}
