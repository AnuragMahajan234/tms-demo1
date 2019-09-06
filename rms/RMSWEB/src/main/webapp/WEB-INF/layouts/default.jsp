<!DOCTYPE HTML>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>

<head>


	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="pragma" content="no-cache" />

	<!-- 
[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]
	-->
	<spring:message code="application_js_version" var="app_js_ver" htmlEscape="false" />
	<spring:url value="/resources/styles/style.css?ver=${app_js_ver}" var="style_css" />
	<link href="${style_css}" rel="stylesheet" type="text/css">
	</link>
	<spring:url value="/resources/images/favicon.ico" var="favicon" />
	<spring:url value="/resources/js-framework/jquery-1.7.2.min.js?ver=${app_js_ver}" var="jquery_url" />
	<spring:url value="/resources/js-framework/jquery-ui/jquery-ui-1.8.22.custom.min.js?ver=${app_js_ver}" var="jquery_ui_1_8_21_custom_min_js" />
	<spring:url value="/resources/js-framework/jquery-ui/combobox.js?ver=${app_js_ver}" var="combobox_js" />
	<spring:url value="/resources/js-framework/form2js/form2js.js?ver=${app_js_ver}" var="form2js_js" />
	<spring:url value="/resources/js-framework/form2js/jquery.toObject.js?ver=${app_js_ver}" var="jquery_toObject_js" />
	<spring:url value="/resources/js-framework/form2js/js2form.js?ver=${app_js_ver}" var="js2form_js" />
	<spring:url value="/resources/js-framework/form2js/json2.js?ver=${app_js_ver}" var="json2_js" />
	<spring:url value="/resources/js-framework/validate/jquery.validate.min.js?ver=${app_js_ver}" var="jquery_validate_min_js" />
	<spring:url value="/resources/js-framework/validate/jquery.validVal-4.0.3-packed.js?ver=${app_js_ver}" var="jquery_validVal" />
	<spring:url value="/resources/js-framework/validate/additional-methods.min.js?ver=${app_js_ver}" var="additional_methods_min_js" />
	<spring:url value="/resources/js-framework/jsrender/jsrender.js?ver=${app_js_ver}" var="jsrender_js" />
	<spring:url value="/resources/js-framework/jquery.autogrowtextarea.js?ver=${app_js_ver}" var="jquery_autogrowtextarea_js" />
	<spring:url value="/resources/js-user/rmsCommon.js?ver=${app_js_ver}" var="rmsCommon_js" />
	<spring:url value="/resources/js-user/validations.js?ver=${app_js_ver}" var="validations_js" />

	<spring:url value="/resources/js-framework/fancy-box/jquery.fancybox.pack.js?ver=${app_js_ver}" var="jquery_fancybox" />
	<spring:url value="/resources/js-framework/toastr/toastr.js?ver=${app_js_ver}" var="toastr_js" />
	<spring:url value="/resources/js-framework/blockUI/jquery.blockUI.js?ver=${app_js_ver}" var="blockUI" />
	<spring:url value="/resources/js-framework/noty/jquery.noty.js?ver=${app_js_ver}" var="noty" />
	<spring:url value="/resources/js-framework/noty/layouts/center.js?ver=${app_js_ver}" var="noty_center" />
	<spring:url value="/resources/js-framework/noty/themes/default.js?ver=${app_js_ver}" var="noty_default" />
	<!-- Get the user local from the page context (it was set by Spring MVC's locale resolver) -->
	<c:set var="userLocale">
		<c:set var="plocale">${pageContext.response.locale}</c:set>
		<c:out default="en" value="${fn:replace(plocale, '_', '-')}" />
	</c:set>

	<script src="${jquery_url}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${jquery_ui_1_8_21_custom_min_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${combobox_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${json2_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${form2js_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${jquery_toObject_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${js2form_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${jquery_validate_min_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${additional_methods_min_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${jquery_validVal}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${jsrender_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${jquery_autogrowtextarea_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>

	<!-- required for common functions -->
	<script src="${rmsCommon_js}" type="text/javascript"></script>
	<!-- required for common validations -->
	<script src="${validations_js}" type="text/javascript"></script>

	<script src="${noty}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${noty_default}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${noty_center}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>

	<spring:url value="/resources/js-framework/datatables/jquery.dataTables.min.js?ver=${app_js_ver}" var="jquery_dataTables_min_js" />
	<spring:url value="/resources/js-framework/populate/jquery.populate.pack.js?ver=${app_js_ver}" var="jquery_populate_pack_js" />
	<!-- DataTables CSS -->
	<!-- DataTables -->
	<script src="${jquery_dataTables_min_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>

	<!-- Populate -->
	<script src="${jquery_populate_pack_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${jquery_fancybox}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${toastr_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${blockUI}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>

	<script type="text/javascript">
		/*---------------------------user notification-------------------------------------*/
		function getUserNotification() {
			var msg = "<ul>";
			$.getJSON("usernotifications", {
				find: "ByEmployeeIdAndIsReadNotUpdated"
			}, function (json) {
				var items = [];
				$.each(json, function (key, val) {
					msg = msg + "<li>" + val.msg + "</li>";
				});

				if (msg.length > 4) {
					msg = msg + "</ul>"
					showSuccess(msg);
				}
			});
		}
		/*--------------------------------end user notification-----------------------------------*/
	</script>




	<script type="text/javascript">
		/*-----------------function for container width---------------------*/
		function containerWidth() {}
		/*-----------------function for container width end------------------*/



		jQuery.fn.reset = function () {
			$(this).each(function () {
				this.reset();
			});
		};


		$.fn.serializeFormJSON = function () {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function () {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [o[this.name]];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};

		function _ajax_request(url, data, callback, type, method) {
			if (jQuery.isFunction(data)) {
				callback = data;
				data = {};
			}
			return jQuery.ajax({
				type: method,
				url: url,
				data: data ? JSON.stringify(data) : null,
				success: callback,
				dataType: type,
				contentType: "application/json; charset=utf-8",
				async: false,
				error: function (data) {
					stopProgress();
					var obj = jQuery.parseJSON(data.responseText);
					showError("The selected row has references and hence can't be deleted");
					//showError("Transaction failed: " + obj.error);
				}
			});
		}

		/** This method will submit the data where the form is not used */
		function _ajax_request_data(url, data, callback, type, method) {
			if (jQuery.isFunction(data)) {
				callback = data;
				data = {};
			}
			return jQuery.ajax({
				type: method,
				url: url,
				data: data,
				success: callback,
				dataType: type,
				contentType: "application/json; charset=utf-8",
				error: function (data) {
					stopProgress();
					var obj = jQuery.parseJSON(data.responseText);
					showError("Transaction failed: ");
				}
			});
		}

		jQuery.extend({
			putJson: function (url, data, callback, type) {
				return _ajax_request(url, data, callback, type, 'PUT');
			},
			deleteJson: function (url, data, callback, type) {
				return _ajax_request(url, data, callback, type, 'DELETE');
			},
			deleteJson_: function (url, data, callback, type) {
				return _ajax_request(url, data, callback, type, 'DELETE');
			},
			postJson: function (url, data, callback, type) {
				return _ajax_request(url, data, callback, type, 'POST');
			},
			putJsonData: function (url, data, callback, type) {
				return _ajax_request_data(url, data, callback, type, 'PUT');
			},
			postJsonData: function (url, data, callback, type) {
				return _ajax_request_data(url, data, callback, type, 'POST');
			},
			postData: function (url, data, callback, type) {
				if (jQuery.isFunction(data)) {
					callback = data;
					data = {};
				}
				return jQuery.ajax({
					type: 'POST',
					url: url,
					data: data,
					success: callback,
					dataType: type,
					contentType: "application/json; charset=utf-8",
					error: function (data) {
						stopProgress();
						var obj = jQuery.parseJSON(data.responseText);
						showError("Transaction failed: " + obj.error);
					}
				});
			}
		});


		(function ($) {
			$.fn.toDeepJson = function () {
				function parse(val) {
					if (val == "") {
						return null;
					}
					if (val == "true") {
						return true;
					}
					if (val == "false") {
						return false;
					}
					if (val == String(parseInt(val))) {
						return parseInt(val);
					}
					if (val == String(parseFloat(val))) {
						return parseFloat(val);
					}
					return val;
				}

				function toNestedObject(obj, arr) {
					var key = arr.shift();
					if (arr.length > 0) {
						var nestedObj = toNestedObject(obj[key] || {}, arr);
						var re = new RegExp("\\[\\d\\]");
						if (key.match(re)) {
							var objIndex = key.charAt(key.length - 2);
							key = key.replace(re, "");
							var arr = obj[key];
							if (arr == null) {
								arr = [];
							}
							try {
								var secObj;
								if (arr.length > objIndex) {
									secObj = arr[objIndex];
								}
								if (secObj == null) {
									while (secObj == null) {
										secObj = new Object();
										arr.push(secObj);
										secObj = arr[objIndex];
									}
									arr[objIndex] = nestedObj;
								} else {
									for (var k in nestedObj) secObj[k] = nestedObj[k];
								}

								obj[key] = arr;
							} catch (e) {
								alert(e);
							}
						} else {
							obj[key] = nestedObj;
						}
						return obj;
					}
					return key;
				}
				if (this.length == 1) {
					return $.makeArray(this[0].elements).filter(function (e) {
						return e.name && e.name != "" && (e.type == 'radio' ? e.checked : true);
					}).map(function (e) {
						var names = e.name.split('.');
						if (e.type == 'checkbox') {
							e.value = e.checked;
						}
						if ($(e).hasClass("string")) {
							names.push(e.value);
						} else {
							names.push(parse(e.value));
						}
						return names;
					}).reduce(toNestedObject, {});
				} else {
					throw ({
						error: "Can work on a single form only"
					});
				}
			};

			$.flatten = function (obj) {
				var ret = {};
				for (var key in obj) {
					if (typeof obj[key] == 'object') {
						var fobj = $.flatten(obj[key]);
						for (var extkey in fobj) {
							ret[key + "." + extkey] = fobj[extkey];
						}
					} else {
						ret[key] = String(obj[key]);
					}
				}
				return ret;
			};

		})(jQuery);
	</script>
	<script type="text/javascript" charset="utf-8">
		$.fn.dataTableExt.oApi.fnAddDataAndDisplay = function (oSettings, aData) {
			/* Add the data */
			var iAdded = this.oApi._fnAddData(oSettings, aData);
			var nAdded = oSettings.aoData[iAdded].nTr;

			/* Need to re-filter and re-sort the table to get positioning correct, not perfect
			 * as this will actually redraw the table on screen, but the update should be so fast (and
			 * possibly not alter what is already on display) that the user will not notice
			 */
			this.oApi._fnReDraw(oSettings);

			/* Find it's position in the table */
			var iPos = -1;
			for (var i = 0, iLen = oSettings.aiDisplay.length; i < iLen; i++) {
				if (oSettings.aoData[oSettings.aiDisplay[i]].nTr == nAdded) {
					iPos = i;
					break;
				}
			}

			/* Get starting point, taking account of paging */
			if (iPos >= 0) {
				oSettings._iDisplayStart = (Math.floor(i / oSettings._iDisplayLength)) * oSettings._iDisplayLength;
				this.oApi._fnCalculateEnd(oSettings);
			}

			this.oApi._fnDraw(oSettings);
			return {
				"nTr": nAdded,
				"iPos": iAdded
			};
		};

		jQuery.fn.dataTableExt.aTypes.unshift(
			function (sData) {
				if (sData !== null && sData.match('^<.*[0-9]+</.*>$')) {
					return 'intComparer';
				}
				return null;
			}
		);

		jQuery.fn.dataTableExt.oSort['intComparer-asc'] = function (a, b) {
			var value1 = parseInt($(a).html());
			var value2 = parseInt($(b).html());
			return ((value1 < value2) ? -1 : ((value1 > value2) ? 1 : 0));
		};

		jQuery.fn.dataTableExt.oSort['intComparer-desc'] = function (a, b) {
			var value1 = parseInt($(a).html());
			var value2 = parseInt($(b).html());
			return ((value1 < value2) ? 1 : ((value1 > value2) ? -1 : 0));
		};

		$(document).ready(function () {
			/* $("#toast-container").live("click", function(){
 			   alert("hi");
 			  $('.toasterBgDiv').remove(); 
 		   }); */

			$(".dataTable > tbody > tr:even").addClass("even");
			$(".dataTable > tbody > tr:odd").addClass("odd");

			/*----------------------------- Main Menu--------------------------------*/
			$(".navMenu").find("> li").hover(function () {
					$('ul', this).stop().animate({
						top: '16px'
					}, 200, function () {
						$(this).css({
							'z-index': '13'
						});
					}).css({
						'display': 'block'
					});
				},
				function () {
					$('ul', this).stop(true, true).animate({
						top: '10px'
					}, 50, function () {
						$(this).css({
							'z-index': '-1'
						});
					}).css("display", "none");
					$("ul.subMenu").animate({
						top: '-133px'
					}, 20).css("display", "none");
				});
			/* $('.navMenu').find('> li').hover(function(){
				//$(".navMenu li").find('ul').animate({top:'40px', opacity:'0.1'}).hide();
				$(this).find('ul').stop(true, true).animate({top:'15px',opacity:'1.0'},'medium').show();
			}, 
			  function () {
				$(this).find('ul').animate({top:'40px', opacity:'0.1'},'medium').hide();
			}); */
			$(".comboselect").combobox();


			/*------------------------------------User Notification-------------*/

			try {
				var userRole = $(".userRole").text(); // A div with class "userRole" is present in header.jsp
				if (userRole != null && userRole != "" && userRole.indexOf("ROLE_USER") == -1) { // If current user role is not ROLE_USER , then only get notifications
					getUserNotification();
					//window.setInterval("getUserNotification()", 10*60*1000);
				}
			} catch (err) {
				alert("Error " + err);
			}

			/*help content*/
			$("#helpIcon").click(function () {
				$(".helpContent").show();
			});
			$(".closeHelp").click(function () {
				$(".helpContent").hide();
			});

		});

		function startProgress() {
			$.blockUI({
				message: '</br></br><div class="circle"></div><div class="circle1"></div><center>Just a moment</center>'
			});
		}

		function stopProgress() {
			$.unblockUI();
		}

		function showSuccess(msg) {
			toastr.success(msg, "Success")
		}

		function showError(msg) {
			toastr.options.timeOut = 12000;
			toastr.error(msg, "Error")
		}

		function showWarning(msg) {
			toastr.warning(msg, "Warning")
		}


		function spclCharacterValidation(name) {
			if (/^[a-zA-Z0-9- ]*$/.test(name) == false) {
				return true;
				stopProgress();
			}
		}
	</script>

	<spring:message code="application_name" var="app_name" htmlEscape="false" />
	<title>
		<spring:message code="welcome_h3" arguments="${app_name}" />
	</title>
	<link href="${favicon}" rel="SHORTCUT ICON" />
	<link href="${roo_css_url}" media="screen" rel="stylesheet" type="text/css" />

</head>

<body>

	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
				<div class="container">
					<div class="contentArea">
						<tiles:insertAttribute name="header" />

						<!-- menu section start here -->
						<div class="navMenuDiv">
							<%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_BEHALF_MANAGER','ROLE_DEL_MANAGER')"> --%>
							<div class="topSection">
								<div class="fl left"></div>
								<div class="fr right"></div>
								<div class="mid"></div>
								<div class="clear"></div>
							</div>
							<div class="midSection posRelMidSection">
								<!--left section-->
								<tiles:insertAttribute name="menu" ignore="true" />
								<!--left section-->
							</div>
							<div class="botSection">
								<div class="fl left"></div>
								<div class="fr right"></div>
								<div class="mid"></div>
								<div class="clear"></div>
							</div>
							<%-- </sec:authorize> --%>
						</div>



						<!-- menu section end here -->
						<div class="centerContent">
							<div class="topSection">

								<div class="fl left"></div>
								<div class="fr right"></div>
								<div class="mid"></div>
								<div class="clear"></div>
							</div>
							<div class="midSection" style=" min-height:450px;">
								<%-- <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
				<div class="errorMsg">You are not authorized user of RMS,Please contact  Ebiz_RMSSupport </div>
			</sec:authorize> --%>
								<tiles:insertAttribute name="body" />
							</div>
							<div class="botSection">
								<div class="fl left"></div>
								<div class="fr right"></div>
								<div class="mid"></div>
								<div class="clear"></div>
							</div>
						</div>
						<tiles:insertAttribute name="footer" />
					</div>
				</div>

			</td>
		</tr>
	</table>
</body>

</html>