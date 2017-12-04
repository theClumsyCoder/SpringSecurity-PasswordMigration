package net.exampleApp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/app/admin")
public class AdminController
{
  @RequestMapping(value="app/admin/adminPage", method=RequestMethod.GET)
  public String adminPage (ModelMap model)
  {
  	return "app/admin/adminPage";
  }
}
