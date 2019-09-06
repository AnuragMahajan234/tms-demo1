package org.yash.rms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.forms.LoginForm;
import org.yash.rms.service.SampleService;

@Controller
public class SampleController {
	@Autowired
	SampleService sampleService;
	public void sample(){
		SampleService s ;
	}
	
	@RequestMapping("/login")
	public ModelAndView loginUser(Model model) {	
		sampleService.save();
		return new ModelAndView("login","loginForm", new LoginForm());
	}
}

