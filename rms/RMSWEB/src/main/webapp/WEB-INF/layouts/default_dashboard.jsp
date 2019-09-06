<!DOCTYPE HTML>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<html>

<head>
	<title>RMS | Dashboard</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport" />

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>




	<link href="/rms/resources/dashboardscript/dist/img/favicon.ico" rel="icon" type="image/x-icon">
	<!-- Bootstrap 3.3.5 -->
	<link rel="stylesheet" href="/rms/resources/dashboardscript/bootstrap/css/bootstrap.min.css" />
	<!-- Font Awesome -->
	<link rel="stylesheet" href="/rms/resources/dashboardscript/bootstrap/css/font-awesome.min.css" />
	<!-- Roboto Font -->
	<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900" rel="stylesheet" />
	<!-- Theme style -->
	<!-- RMS Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
	<link rel="stylesheet" href="/rms/resources/dashboardscript/dist/css/skins/_all-skins.min.css" />
	<link rel="stylesheet" href="/rms/resources/styles/jquery-ui/css/ui-darkness/jquery-ui-1.8.22.custom.css" />
	

	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
        <script src="bootstrap/js/html5shiv.min.js"></script>
        <script src="bootstrap/js/respond.min.js"></script>
    <![endif]-->
	<!-- jQuery 2.1.4 -->
	<spring:message code="application_js_version" var="app_js_ver" htmlEscape="false" />
	<link rel="stylesheet" href="/rms/resources/dashboardscript/dist/css/RMS.min.css?ver=${app_js_ver}" />
	<spring:url value="/resources/styles/toastr/toastr.css?ver=${app_js_ver}" var="toastr_css" />
	<link href="${toastr_css}" rel="stylesheet" />

	<spring:url value="resources/js-framework/weeklycalendar.js?ver=${app_js_ver}" var="jquery_weeklycalendar_js" />
	<spring:url value="resources/js-framework/date.js?ver=${app_js_ver}" var="jquery_date_js" />
	<script src="${jquery_weeklycalendar_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	
	<!-- Week Date -->
	<script src="${jquery_date_js}" type="text/javascript"></script>
	<script src="/rms/resources/dashboardscript/plugins/jQuery/jQuery-2.1.4.min.js?ver=${app_js_ver}" type="text/javascript"></script>
	<spring:url value="/resources/js-framework/fancy-box/jquery.fancybox.js?ver=${app_js_ver}" var="jquery_fancybox" />
	<script src="${jquery_fancybox}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<!-- Bootstrap 3.3.5 -->
	<script src="/rms/resources/dashboardscript/bootstrap/js/bootstrap.min.js?ver=${app_js_ver}" type="text/javascript"></script>
	<!-- Morris.js charts -->
	<script src="/rms/resources/dashboardscript/plugins/raphael-min.js?ver=${app_js_ver}" type="text/javascript"></script>
	<script src="/rms/resources/dashboardscript/plugins/morris/morris.js?ver=${app_js_ver}" type="text/javascript"></script>
	<!-- RMS App -->
	<script src="/rms/resources/dashboardscript/dist/js/app.min.js?ver=${app_js_ver}" type="text/javascript"></script>
	<!-- SlimScroll 1.3.0 -->
	<script src="/rms/resources/dashboardscript/plugins/slimScroll/jquery.slimscroll.min.js?ver=${app_js_ver}" type="text/javascript"></script>
	<!-- jQuery Knob -->
	<script src="/rms/resources/dashboardscript/plugins/knob/jquery.knob.js?ver=${app_js_ver}" type="text/javascript"></script>
	<!-- Demo -->
	<script src="/rms/resources/dashboardscript/dist/js/demo.js?ver=${app_js_ver}" type="text/javascript"></script>
	<spring:url value="/resources/js-framework/toastr/toastr.js?ver=${app_js_ver}" var="toastr_js" />
	<script src="${toastr_js}" type="text/javascript"></script>
	<spring:url value="/resources/js-framework/blockUI/jquery.blockUI.js?ver=${app_js_ver}" var="blockUI" />
	<script src="${blockUI}" type="text/javascript"></script>
	<spring:url value="/resources/js-user/rmsCommon.js?ver=${app_js_ver}" var="rmsCommon_js" />
	<script src="${rmsCommon_js}" type="text/javascript"></script>
	<spring:url value="/resources/js-user/validations.js?ver=${app_js_ver}" var="validations_js" />
	<script src="${validations_js}" type="text/javascript"></script>
	<spring:url value="/resources/js-framework/datatables/jquery.dataTables.js?ver=${app_js_ver}" var="jquery_datatables_js" />
	<script src="${jquery_datatables_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<spring:url value="/resources/js-framework/datatables/jquery.dataTables.min.js?ver=${app_js_ver}" var="jquery_dataTables_min_js" />
	<script src="${jquery_dataTables_min_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<!-- js and css for tableTools -->
	<spring:url value="/resources/js-framework/datatables/TableTools.js?ver=${app_js_ver}" var="tableTools_js" />
	<script src="${tableTools_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<spring:url value="/resources/js-framework/datatables/ZeroClipboard.js?ver=${app_js_ver}" var="ZeroClipboard_js" />
	<script src="${ZeroClipboard_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<spring:url value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}" var="ColReorder_js" />
	<spring:url value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}" var="ColVis_js" />
	<spring:url value="/resources/js-framework/jsrender/jsrender.js?ver=${app_js_ver}" var="jsrender_js" />
	<spring:url value="/resources/js-framework/jquery.autogrowtextarea.js?ver=${app_js_ver}" var="jquery_autogrowtextarea_js" />
	<script src="${jsrender_js}" type="text/javascript"></script>
	<script src="${jquery_autogrowtextarea_js}" type="text/javascript"></script>
	<spring:url value="/resources/js-framework/jquery-ui/jquery-ui-1.8.22.custom.min.js?ver=${app_js_ver}" var="jquery_ui_1_8_21_custom_min_js" />
	<script src="${jquery_ui_1_8_21_custom_min_js}" type="text/javascript"></script>
	<spring:url value="/resources/js-framework/noty/jquery.noty.js?ver=${app_js_ver}" var="noty" />
	<spring:url value="/resources/js-framework/noty/layouts/center.js?ver=${app_js_ver}" var="noty_center" />
	<spring:url value="/resources/js-framework/noty/themes/default.js?ver=${app_js_ver}" var="noty_default" />
	<script src="${noty}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${noty_default}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<script src="${noty_center}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<spring:url value="/resources/js-user/rmsCommon.js?ver=${app_js_ver}" var="rmsCommon_js" />
	<script src="${rmsCommon_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<%-- <spring:url value="/resources/js-framework/validate/jquery.validVal-4.0.3-packed.js?ver=${app_js_ver}" var="jquery_validVal"/> --%>
	<spring:url value="/resources/js-framework/validate/jquery.validVal.js?ver=${app_js_ver}" var="jquery_validVal" />
	<script src="${jquery_validVal}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<spring:url value="/resources/js-framework/validate/jquery.validate.min.js?ver=${app_js_ver}" var="jquery_validate_min_js" />
	<script src="${jquery_validate_min_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>
	<spring:url value="/resources/js-framework/populate/jquery.populate.pack.js?ver=${app_js_ver}" var="jquery_populate_pack_js" />
	<!-- Populate -->
	<script src="${jquery_populate_pack_js}" type="text/javascript">
		<!-- required for FF3 and Opera -->
	</script>



	<spring:url value="/resources/js-framework/jquery-ui/combobox.js?ver=${app_js_ver}" var="combobox_js" />
	<script src="${combobox_js}" type="text/javascript"></script>
	<spring:url value="/resources/js-framework/jquery.multiselect.filter.js?ver=${app_js_ver}" var="multiselect_filter_js" />

	<script src="${multiselect_filter_js}" type="text/javascript"></script>
	<spring:url value="/resources/js-framework/jquery.multiselect.js?ver=${app_js_ver}" var="multiselect_js" />
	<script src="${multiselect_js}" type="text/javascript"></script>
	
	<!-- remove js from jsp page and put into default_dashboard.jsp (Start)-->
	<%-- <spring:url
	value="resources/js/moment.min.js?ver=${app_js_ver}"
	var="moment_js"/>
<script src="${moment_js}" type="text/javascript"></script>	 --%> 




<!-- remove js from jsp page and put into default_dashboard.jsp (End)-->



	<script type="text/javascript">
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
				oSettings._iDisplayStart = (Math.floor(i /
						oSettings._iDisplayLength)) *
					oSettings._iDisplayLength;
				this.oApi._fnCalculateEnd(oSettings);
			}

			this.oApi._fnDraw(oSettings);
			return {
				"nTr": nAdded,
				"iPos": iAdded
			};
		};

		jQuery.fn.dataTableExt.aTypes.unshift(function (sData) {
						
			if (sData !== null && typeof(sData)!="string" && typeof(sData)!="object" && sData.match('^<.*[0-9]+</.*>$')) {
				return 'intComparer';
			} 
			return null;
		});

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

		$(document).ready(
			function () {
				$(".comboselect").combobox();
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
				}, function () {
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

				/*------------------------------------User Notification-------------*/

				try {
					var userRole = $(".userRole").text(); // A div with class "userRole" is present in header.jsp
					if (userRole != null && userRole != "" &&
						userRole.indexOf("ROLE_USER") == -1) { // If current user role is not ROLE_USER , then only get notifications
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
			$
				.blockUI({
					message: '</br></br><div class="circle"></div><div class="circle1"></div><center>Just a moment</center>'
				});
		}

		function stopProgress() {
			$.unblockUI();
		}

		function showSuccess(msg) {
			toastr.success(msg, "Success")
		}

		function showMessage(msg) {
			toastr.options.timeOut = 12000;
			toastr.error(msg, " ")
		}

		function showError(msg) {
			toastr.options.timeOut = 12000;
			toastr.error(msg, "Error")
		}

		function showWarning(msg) {
			toastr.warning(msg, "Warning")
		}

		function spclCharacterValidation(name) {
			var res = name.charAt(0);
			if (res != null && res.match(/[a-zA-Z]/i)) {} else {
				return true;
				stopProgress();
			}
		}
	</script>

</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="menu" ignore="true" />
		<tiles:insertAttribute name="body" />
		<!-- /.content-wrapper -->
		<tiles:insertAttribute name="footer" />
	</div>
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
			return jQuery
				.ajax({
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
									for (var k in nestedObj)
										secObj[k] = nestedObj[k];
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
					return $.makeArray(this[0].elements).filter(
						function (e) {
							return e.name &&
								e.name != "" &&
								(e.type == 'radio' ? e.checked :
									true);
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



</body>

</html>