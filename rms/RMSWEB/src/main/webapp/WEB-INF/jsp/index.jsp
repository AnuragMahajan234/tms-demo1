  <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
  <div class="mid_section">
  <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
				<div class="errorMsg">You are not authorized user of RMS,Please contact  Ebiz_RMSSupport </div>
			</sec:authorize>
			
	Welcome to YASH RMS Tool!
	Based on the role in the system, you are able to see different menu and options.
	Contact Us
	<li> <b>For any discrepancy/ bug/ issue/ permissions, please write at Ebiz_RMSSupport ebiz_rmssupport@yash.com </b>
		
	<li> <b>	For any suggestions, idea to make it a better YASH tool, please write at YASH RMS Tool rms.tool@yash.com</b>
			
			
			
			
         
 </div>
<div class="clear"></div>