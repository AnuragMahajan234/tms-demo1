/**
 * 
 */
package org.yash.rms.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bhakti.barve
 *
 */
@Controller

public class LogoutController {
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model uiModel, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception{
		System.out.println("logout");
		session.invalidate();
		return "logout";
	}
}
