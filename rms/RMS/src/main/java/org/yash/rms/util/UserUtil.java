package org.yash.rms.util;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.domain.Designation;
import org.yash.rms.domain.Location;
import org.yash.rms.domain.OrgHierarchy;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.UserActivity;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.service.ResourceService;

@Component
public class UserUtil {

	@Autowired
	private ResourceService resourceService;

	private HttpSession session;

	public void setSession(HttpSession session) {
		this.session = session;
	}
	
	public HttpSession getSession() {
		return session;
	}

	
	public UserContextDetails getCurrentResource(String userName) {		
		UserContextDetails userContextDetails = new UserContextDetails();
		System.out.println("userName"+userName);
		Resource currentResource = resourceService.getCurrentResource(userName);
		boolean isSEPGUser=false;
		boolean isBehalfManager = false;
		if(null != currentResource){
			isBehalfManager = resourceService.isResourceBehalfManager(currentResource.getEmployeeId());
		}
		userContextDetails.setEmployeeId(currentResource.getEmployeeId());
		userContextDetails.setUploadImage(currentResource.getUploadImage());
		userContextDetails.setEmailId(currentResource.getEmailId());
		userContextDetails.setMiddleName(currentResource.getMiddleName());
		userContextDetails.setFirstName(currentResource.getFirstName());
		userContextDetails.setLastName(currentResource.getLastName());
		userContextDetails.setUserName(currentResource.getUserName());
		userContextDetails.setEmployeeName(currentResource.getEmployeeName());
		userContextDetails.setReleaseDate(currentResource.getReleaseDate());
		userContextDetails.setGrade(currentResource.getGradeId().getGrade());
		userContextDetails.setYashEmpId(currentResource.getYashEmpId());
		//For get user resume
		userContextDetails.setUploadResumeFileName(currentResource.getResumeFileName());
		
		//For get employee Current and Parent BU
		userContextDetails.setParentBU(currentResource.getCurrentBuId().getParentId().getName());
		userContextDetails.setCurrentBU(currentResource.getCurrentBuId().getName());
		
		//For get employee Current and Parent location
		userContextDetails.setLocation(currentResource.getLocationId().getLocation());
		userContextDetails.setDeploymentLocation(currentResource.getDeploymentLocation().getLocation());
		
		if(currentResource.getPreferredLocation() != null) {
		
		//For get employee preferred location
		userContextDetails.setPreferredLocation(currentResource.getPreferredLocation().getLocation());
		}
		userContextDetails.setAccessRight(resourceService.getBUListForBGADMIN(currentResource));
		userContextDetails.setRRFAccess(
				currentResource.getRrfAccess() !=null && (currentResource.getRrfAccess()=='Y' || currentResource.getRrfAccess()=='y') 
			? true: false);
	//Code to check report access - start
		userContextDetails.setReportAccess(
			
				currentResource.getReportUserId() !=null && (currentResource.getReportUserId()==1) 
			? true: false);
		//Code to check report access - end		
		if(currentResource.getUserRole().equals(Constants.ROLE_SEPG_USER)){
			isSEPGUser=true;
			userContextDetails.setSEPGUser(isSEPGUser);
		}
		
		if(isBehalfManager && currentResource.getUserRole().equals(Constants.ROLE_SEPG_USER )){
			userContextDetails.setUserRole(Constants.ROLE_BEHALF_MANAGER);
		  }
		
		
		if(isBehalfManager && currentResource.getUserRole().equals(Constants.RESOURCE_ROLE)){
			userContextDetails.setUserRole(Constants.ROLE_BEHALF_MANAGER);
		}
		
		else{
			userContextDetails.setUserRole(currentResource.getUserRole());
		}
		
		userContextDetails.setDateOfJoining(currentResource.getDateOfJoining());
		
		if(currentResource.getDesignationId()!=null)
		{
		userContextDetails.setDesignation(currentResource.getDesignationId()
					.getDesignationName());
		}
		userContextDetails.setBehalfManager(isBehalfManager);
		/*userContextDetails.setOstVisibility(currentResource.getCurrentBuId().getParentId().getDescription()+"-"+currentResource.getCurrentBuId().getDescription());*/
		userContextDetails.setOstVisibility(currentResource.getCurrentBuId().getParentId().getName()+"-"+currentResource.getCurrentBuId().getName());
		return userContextDetails;
	}

	/**
	 * Method to get current logged in Resource
	 * @return
	 */
	public static UserContextDetails getCurrentResource() {
			if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
				 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				 if(auth != null) {
					 UserContextDetails userContextDetails = (UserContextDetails)auth.getPrincipal();
					 return userContextDetails;
				 }
			}
		return null;
	}
	
	
	
	public static UserContextDetails getUserContextDetails() {
		
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
			 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			 if(auth != null) {
				 UserContextDetails userContextDetails = (UserContextDetails)auth.getPrincipal();
				 return userContextDetails;
			 }
	
		}
	return null;
}
	
	/**
	 * Method to get current logged in Resource
	 * @return
	 */
	public Resource getLoggedInResource() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
			 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			 if(auth != null) {
				 Resource resource= new Resource();
				 UserContextDetails userContextDetails = (UserContextDetails)auth.getPrincipal();
				 if(null!=userContextDetails && !RMSUtil.NullTOEmptyString(userContextDetails.getUserName()).isEmpty()){
					 resource.setFirstName(userContextDetails.getFirstName());
					 resource.setLastName(userContextDetails.getLastName());
					 resource.setDateOfJoining(userContextDetails.getDateOfJoining());
					 Designation designation = new Designation();
					 designation.setDesignationName(userContextDetails.getDesignation());
					 resource.setDesignationId(designation);
					 resource.setUserRole(userContextDetails.getUserRole());
					 resource.setUserName(userContextDetails.getUserName());
					 resource.setEmailId(userContextDetails.getEmailId());
					 resource.setEmployeeId(userContextDetails.getEmployeeId());
					 resource.setMiddleName(userContextDetails.getMiddleName());
					 resource.setUploadImage(userContextDetails.getUploadImage());
					 resource.setYashEmpId(userContextDetails.getYashEmpId());
					 OrgHierarchy orgParent=new OrgHierarchy();
					 orgParent.setName(userContextDetails.getParentBU());
					 OrgHierarchy orgCurrent=new OrgHierarchy();
					 orgCurrent.setName(userContextDetails.getCurrentBU());
					 orgCurrent.setParentId(orgParent);
					 resource.setCurrentBuId(orgCurrent);
					 
					 Location perLocation=new Location();
					 perLocation.setLocation(userContextDetails.getLocation());
					 
					 Location curLocation=new Location();
					 curLocation.setLocation(userContextDetails.getDeploymentLocation());
					 resource.setLocationId(perLocation);
					 resource.setDeploymentLocation(curLocation);
					
					 
					 return resource;
					 //return resourceService.getCurrentResource(userContextDetails.getUsername());
				 }
			 }
		}
	return null;
    }
	
	/**
	 * Method to get current logged in Resource
	 * @return
	 */
	public Integer getLoggedInResourceId() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
			 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			 if(auth != null) {
				 UserContextDetails userContextDetails = (UserContextDetails)auth.getPrincipal();
				 if(null!=userContextDetails){
					 return userContextDetails.getEmployeeId();
				 }
			 }
		}
	return null;
    }

	public  static boolean isCurrentUserinUserRole() {
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				UserContextDetails resource = (UserContextDetails) auth.getPrincipal();
				return StringUtils.equals("ROLE_USER", resource.getUserRole());

			}
		}
		return false;
	}	

	
	
	
	public  static boolean isCurrentUserinSEPGUserRole() {
		if (SecurityContextHolder.getContext() != null
			    && SecurityContextHolder.getContext().getAuthentication() != null) {
			   Authentication auth = SecurityContextHolder.getContext()
			     .getAuthentication();
			   if (auth != null) {
			    UserContextDetails resource = (UserContextDetails) auth.getPrincipal();
			    return StringUtils.equals("ROLE_SEPG_USER", resource.getUserRole());

			   }
			  }
			  return false;
	}	
	

	/**
	 * Method to get original logged in Resource
	 * 
	 * @return
	 */
	public Resource getOriginalResource() {
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				if (null == session.getAttribute("AdminResource")) {
					Resource resource = (Resource) auth.getPrincipal();
					return resource;
				} else {
					return (Resource) session.getAttribute("AdminResource");
				}
			}
		}
		return null;
	}

	public  static boolean isCurrentUserIsVisitor() {
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				UserContextDetails resource = (UserContextDetails) auth.getPrincipal();
				return StringUtils.equals("ROLE_VISITOR", resource.getUserRole());
			}
		}
		return false;
	}

	/**
	 * Method to check if current user is Admin or not
	 * 
	 * @return
	 */
	public static  boolean isCurrentUserIsAdmin() {
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				UserContextDetails resource = (UserContextDetails) auth.getPrincipal();
				return StringUtils.equals("ROLE_ADMIN", resource.getUserRole());
			}
		}
		return false;
	}
	
	public static  boolean isCurrentUserIsHr() {
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				UserContextDetails resource = (UserContextDetails) auth.getPrincipal();
				return StringUtils.equals("ROLE_HR", resource.getUserRole());
			}
		}
		return false;
	}
	
	
	public static boolean isCurrentUserIsBusinessGroupAdmin()
	{
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				UserContextDetails resource = (UserContextDetails) auth.getPrincipal();
				return StringUtils.equals("ROLE_BG_ADMIN", resource.getUserRole());
			}
		}
		return false;
	}

	/*public static boolean isCurrentUserIsBehalfManager() {
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				Resource resource = (Resource) auth.getPrincipal();
				Collection<GrantedAuthority> autorities = resource
						.getAuthorities();
				for (GrantedAuthority authority : autorities) {
					if (authority.getAuthority() != null
							&& authority.getAuthority().equals(
									"ROLE_BEHALF_MANAGER")) {
						return true;
					}
				}
			}
		}
		return false;
	}
*/
	public  static boolean isCurrentUserIsDeliveryManager() {
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				UserContextDetails resource = (UserContextDetails) auth.getPrincipal();
				return StringUtils.equals("ROLE_DEL_MANAGER",
						resource.getUserRole());
			}
		}
		return false;
	}
	
	

	public  static String prepareURLForUserRole() {
		// /useractivitys?minWeekStartDate=10/1/2012&maxWeekStartDate=11/30/2012&find=ByWeekStartDateBetweenAndEmployeeId
		StringBuffer str = new StringBuffer("/useractivitys");
		Calendar prevMonthCal = Calendar.getInstance();
		prevMonthCal.add(Calendar.MONTH, -1);
		prevMonthCal.set(Calendar.DATE, 1);
		Calendar curMonthCal = Calendar.getInstance();
		curMonthCal.set(Calendar.DATE,
				curMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		str.append("?minWeekStartDate=")
				.append(prevMonthCal.get(Calendar.MONTH) + 1).append("/")
				.append(prevMonthCal.get(Calendar.DATE)).append("/")
				.append(prevMonthCal.get(Calendar.YEAR));
		str.append("&maxWeekStartDate=")
				.append(curMonthCal.get(Calendar.MONTH) + 1).append("/")
				.append(curMonthCal.get(Calendar.DATE)).append("/")
				.append(curMonthCal.get(Calendar.YEAR));
		str.append("&find=ByWeekStartDateBetweenAndEmployeeIdView");
		return str.toString();

	}

	public static Resource userContextDetailsToResource(UserContextDetails 
			userContextDetails){
			         if(userContextDetails!=null){
			         Resource resource = new Resource();
			         resource.setUserName(userContextDetails.getUsername());
			         resource.setUserRole(userContextDetails.getUserRole());
			         resource.setEmployeeId(userContextDetails.getEmployeeId());
			         return resource;
			         }else
			             return null;
			     }

	/**
	 * Method to get current logged in Resource
	 * @return
	 */
	public  String getCurrentUserName() {
			if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
				 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				 String userName = "";
				 if(auth != null) {
					userName = (String)auth.getName();
					 return userName;
				 }
			}
		return "";
	}
	
	public Integer getCurrentLoggedInUseId() {
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				//if (auth.getPrincipal().getClass().getTypeName().equalsIgnoreCase("String")) {

					// throw new AuthenticationException("Invalid user credential");
					// return "login";
					// HttpServletResponse.sendRedirect("/login");
					//session.invalidate();

				//}
				UserContextDetails userContextDetails = (UserContextDetails) auth.getPrincipal();
				
				return null != userContextDetails ? userContextDetails.getEmployeeId() : 0;
			}
		}
		return null;
	}
	
	public boolean authenticateUser(String userName,String password) {		
		return resourceService.authenticateResource(userName, password);
	}

	public String  getURL(HttpServletRequest request,UserActivity activity,Character status) {
		String url = null;
		if ((request != null)	&& (activity != null)	 && (status != null)) {
			StringBuffer sb = new StringBuffer();
			if (request.isSecure()) {
				sb.append("https");
			} else {
				sb.append("http");
			}
			sb.append("://");
			if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
				sb.append("rms.yash.com");
			} else {
				sb.append(request.getServerName());
				int port = request.getServerPort();
				if (port > 0) {
					sb.append(":");
					sb.append(port);
				}
			}
			
			
			sb.append("/rms/");
			sb.append(Constants.TIME_HRS);
			sb.append("?");
			sb.append(Constants.FIND);
			sb.append("=" + Constants.APPROVE_TIMESHEET_FROM_MAIL);
			sb.append("&");
			sb.append(Constants.RES_ALLOC_ID);
			
			sb.append("=" + activity.getResourceAllocId().getId());
			sb.append("&");
			sb.append(Constants.RESOURCE_ID);
			sb.append("=" + activity.getEmployeeId().getEmployeeId());
			sb.append("&");
			sb.append(Constants.REPORTED_HRS);
			sb.append("=" + activity.getRepHrsForProForWeekEndDate());
			sb.append("&");
			sb.append(Constants.USER_ACTIVITY_ID);
			sb.append("=" + activity.getId());
			sb.append("&");
			sb.append(Constants.TIME_SHEET_STATUS);
			sb.append( "="+status);
			sb.append("&");
			sb.append(Constants.CODE);
					if (status == 'A') {
						sb.append("=" + activity.getApproveCode());
					}
					else if (status== 'R') {
						sb.append("=" + activity.getRejectCode());
					}
					sb.append("&");
					sb.append(Constants.WEEK_END_DATE);
			sb.append("=" + activity.getWeekEndDate().toString());
			url = sb.toString();
			
			
		}

		if ((request != null) && (activity == null)	 && (status != null )) {
			StringBuffer sb = new StringBuffer();
			if (request.isSecure()) {
				sb.append("https");
			} else {
				sb.append("http");
			}
			sb.append("://");
			if (Constants.ENV_VARIABLE.equalsIgnoreCase("PROD")) {
				sb.append("rms.yash.com");
			} else {
				sb.append(request.getServerName());
				int port = request.getServerPort();
				if (port > 0) {
					sb.append(":");
					sb.append(port);
				}
			}
			
			sb.append("/rms/");
			sb.append("editProfile/showProfile/?editProfile=1");
			
			url = sb.toString();
			
		}
		System.out.println("url :" + url);
		return url;
	}
	
	
}

