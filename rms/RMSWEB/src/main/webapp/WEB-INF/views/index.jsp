  <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
  <div class="mid_section">
  <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
				<div class="errorMsg">You are not authorized user of RMS,Please contact   <a href="mailto:ebiz_rmssupport@yash.com">YASH Ebiz_RMSSupport</a></div>
			</sec:authorize>
          <b>Welcome to YASH RMS Tool!
Based on the role in the system, you are able to see different menu and options.</b><br>Contact Us
    <ul> <li class="spcleft">For any discrepancy/ bug/ issue/ permissions, please write at <b>YASH Ebiz_RMSSupport</b> <a href="mailto:ebiz_rmssupport@yash.com"><font color="blue">ebiz_rmssupport@yash.com</font></a></li>
    <li class="spcleft">For any suggestions, idea to make it a better YASH tool, please write at <b>YASH RMS Tool </b> <a href="mailto:rms.tool@yash.com"><font color="blue">rms.tool@yash.com</font></a></li>
    
    </ul>
         
 
<div class="clear"></div>