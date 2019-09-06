package org.yash.rms.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model uiModel,HttpSession session) {
     	/*if (session != null) {
    		//String timehrs =(String) session.getAttribute(Constants.TIME_HRS);
    		if (session.getAttribute(Constants.TIME_SHEET_STATUS) != null &&( !session.getAttribute(Constants.TIME_SHEET_STATUS).equals(""))&&(session.getAttribute(Constants.TIME_SHEET_STATUS).equals("A")||session.getAttribute(Constants.TIME_SHEET_STATUS).equals("R"))) {
    			String find =(String)session.getAttribute(Constants.FIND);
        		Integer res_alloc_id =Integer.parseInt(session.getAttribute(Constants.RES_ALLOC_ID).toString()) ;
        		Integer res_id =Integer.parseInt(session.getAttribute(Constants.RESOURCE_ID).toString()) ;
        		Double reported_hrs =Double.parseDouble(session.getAttribute(Constants.REPORTED_HRS).toString()) ;
        		String week_end_date =(String)session.getAttribute(Constants.WEEK_END_DATE);
        		Integer user_act_id =Integer.parseInt(session.getAttribute(Constants.USER_ACTIVITY_ID).toString()) ;
        		String code = (String)session.getAttribute(Constants.CODE);
        		String status = (String)session.getAttribute(Constants.TIME_SHEET_STATUS);
        		String redirectURL = getURL(find,res_alloc_id,res_id,reported_hrs,week_end_date,user_act_id,code,status);
        		if (find != null &&find != "") {
        			return "redirect:"+redirectURL;
    			}
			}
    		
    		if((session.getAttribute(Constants.USER_PROFILE) !=null ) && ((!session.getAttribute(Constants.USER_PROFILE).equals("")))){
    			session.removeAttribute("editProfile");
    			return "redirect:editProfile/showProfile";
    		}
    	}*/
    	
    	
    	
    	String adminURL = (String)session.getAttribute("adminURL");
    	if((adminURL != null) && (adminURL.trim().length() > 0)) {
    		session.removeAttribute("adminURL");
    		return "redirect:"+adminURL;
    	
    	}
    	if(UserUtil.getUserContextDetails().getUserRole().equalsIgnoreCase("ROLE_BG_ADMIN")||UserUtil.getUserContextDetails().getUserRole().equalsIgnoreCase("ROLE_MANAGER")||UserUtil.getUserContextDetails().getUserRole().equalsIgnoreCase("ROLE_DEL_MANAGER")){
    		return "redirect:dashboard/admindashboard";
    	}else if(UserUtil.getUserContextDetails().getUserRole().equalsIgnoreCase("ROLE_ANONYMOUS")){
    		return "index";
    	}else{
    		return "redirect:dashboard/userdashboard";
    	}
    	
//    	if(UserUtil.isCurrentUserIsVisitor()) {     		
//    		return "accessDenied";	
//    	}
//    	if(UserUtil.isCurrentUserinUserRole()) {
//    		return "redirect:" + UserUtil.prepareURLForUserRole();
//    	}
    	
//        return "index";
        
    }

	private String getURL(String find, Integer res_alloc_id, Integer res_id,
			Double reported_hrs, String week_end_date, Integer user_act_id,
			String code, String status) {
		// TODO Auto-generated method stub
		
		StringBuffer sb = new StringBuffer();
		sb.append("/"+Constants.TIME_HRS);
		sb.append("?");
		sb.append(Constants.FIND);
		sb.append("="+find);
		
		sb.append("&");
		sb.append(Constants.RES_ALLOC_ID);
		sb.append("="+res_alloc_id);
		
		sb.append("&");
		sb.append(Constants.RESOURCE_ID);
		sb.append("="+res_id);
		
		sb.append("&");
		sb.append(Constants.REPORTED_HRS);
		sb.append("="+reported_hrs);
		
		sb.append("&");
		sb.append(Constants.USER_ACTIVITY_ID);
		sb.append("="+user_act_id);
		
		sb.append("&");
		sb.append(Constants.TIME_SHEET_STATUS);
		char sta= status.charAt(0); 
		sb.append("="+sta);
		
		sb.append("&");
		sb.append(Constants.CODE);
		sb.append("="+code);
		
		sb.append("&");
		sb.append(Constants.WEEK_END_DATE);
		sb.append("="+week_end_date);
		
		
		return sb.toString();
	}
    
}
