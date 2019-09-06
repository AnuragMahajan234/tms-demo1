
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" media="all"
	href="http://static.jstree.com/v.1.0pre/themes/default/style.css?ver=${app_js_ver}">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />

<title>Insert title here</title>
<spring:url
	value="/resources/js-framework/jquery-1.7.2.min.js?ver=${app_js_ver}"
	var="jquery_url" />
<spring:url
	value="/resources/js-framework/jquery.jstree.js?ver=${app_js_ver}"
	var="jquery_jstree_js" />
<script src="${jquery_url}" type="text/javascript"></script>
<script src="${jquery_jstree_js}" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#selector").jstree({
			"plugins" : [ "themes", "html_data", "ui" ]
		});
	});

	function getValue() {
		//alert("hhhh---");
		$("#selector").jstree("create", null, "last");
		// alert(node.value);

	}
	$("#add").click(function() {
		// alert("add cald");
		$("#selector").jstree("create", null, "last");
	});
	/* $(".jstree-open").live("click", function () {
		alert($(this).text());
	}); */
</script>
<style type="text/css">
.wrapper {
	width: 600px;
	margin: 0px auto;
}

.childCheckboxContainer {
	padding: 6px 15px;
	width: 100%;
}

div#copyData div.child {
	margin-left: 15px;
	margin-top: 15px;
}

.listBox {
	min-width: 130px;
	height: auto;
	margin: 5px 0px;
	max-height: 100px;
	overflow: auto;
	padding: 3px;
	line-height: 18px;
}

.active,.icon {
	width: 9px;
	height: 11px;
	background: url(images/coll.png) left 1px no-repeat;
	cursor: pointer;
	float: left;
	margin-top: 3px;
}

.active {
	background: url(images/coll.png) right 1px no-repeat;
}

ul li {
	list-style: none;
	padding: 0px;
	margin: 0px;
}
</style>
</head>
<body>
	<!--
<div class="wrapper">
 <form id="BGfilterForm" name="BGfilterForm" action="bgs">

      <div id="selector" class="demo jstree jstree-1 jstree-default jstree-focused" style="height:150px;">
      
         
        <c:set var="count" value="0" scope="page" />
		
        <c:set var="tableData" value="${orglist}"/>
        
        <c:set var="count" value="${count + 1}" scope="page"/>
            
               <ul>
                <li id="rhtml_1" class="jstree-open>
                
               <ins class="jstree-icon"> </ins>
               <a class="jstree-clicked" href="#">
              <span>${tableData.name}</span><br/></a>
             <ul>
            
             <c:forEach var="tableColumn" items="${tableData.orgHierarchies}">
             
                 <li id="${tableColumn.id}" >
                  <a class="jstree-clicked" href="#">
                <span>${tableColumn.name}</span></a>
                <ul>
                <c:forEach var="tableSecondColumn" items="${tableColumn.orgHierarchies}">
               
                 <li id="${tableSecondColumn.id}">
                <span>${tableSecondColumn.name}</span><br/>
              </li>
                 </c:forEach>
                  </ul></li>
                 </c:forEach>
                </ul></li>
                 </ul>
               
            </div>
          <br><input type="button" value="submit" id="submit">
            <br><input type="button" class="button" value="add" id="add" onclick="getValue()">
             </form>
</div>
        -->
      

</body>
</html>