<%@ page import="org.yash.rms.util.Constants"%>
<%@ page import="org.yash.rms.util.UserUtil"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<spring:message code="application_js_version" var="app_js_ver" htmlEscape="false" />

<spring:url
	value="/resources/js-framework/datatables/ColReorder.js?ver=${app_js_ver}"
	var="ColReorder_js" />
<spring:url
	value="/resources/js-framework/datatables/ColVis.js?ver=${app_js_ver}"
	var="ColVis_js" />
	
<spring:url value="/resources/styles/style.css?ver=${app_js_ver}"
	var="style_css" />
<link href="${style_css}" rel="stylesheet" type="text/css"></link>

<spring:url value="/resources/images/spinner.gif" var="spinner" />

<!-- Start custom loader -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/js/jquery.nice-select.min.js?ver=${app_js_ver}"></script>
<!-- End custom loader -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="pragma" content="no-cache" />
<style type="text/css" title="currentStyle">
.toolbar {
	float: left;
	margin-top: -25px;
}

.close {
	position: relative;
	right: 0px;
	top: 5px;
	width: 29px;
	height: 0px;
	opacity: 0.3;
	z-index: 5;
}

.close:hover {
	opacity: 1;
}

.close:before, .close:after {
	position: absolute;
	left: 15px;
	content: ' ';
	height: 15px;
	width: 2px;
	background-color: #333;
}

.close:before {
	transform: rotate(45deg);
}

.close:after {
	transform: rotate(-45deg);
}

.toolbar {
	float: left;
}

.closeButton {
	float: right;
}

thead input {
	width: 100%
}

#records_tableId {
	table-layout: fixed;
	background: transparent;
	border: 0;
	display: table;
}

#records_tableId thead th {
	width: 120px;
	word-wrap: break-word;
}

#records_tableId td {
	word-wrap: break-word;
}

div.dataTables_wrapper {
	width: 100%;
	margin: 0 auto;
	position: relative;
}

table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.tablefixed.dataTable
	{
	table-layout: auto;
}

table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.dataTable
	{
	max-height: 751px;
	display: table !important;
}

table.dataTbl tr.selected td {
	background: #a4e3f1;
}

table.dataTable tr.selected td.sorting_1 {
	background: #a4e3f1;
}
/** new css added for datatable**/
table.dataTable {
	margin: 10px auto 0;
}

.dataTables_filter {
	top: 30px
}

.dataTables_filter label {
	margin-bottom: 0px;
}

/* th, td {
	    white-space: nowrap;
	} */
.dataTables_length {
	top: 30px;
}

.dataTables_length select {
	width: 50px;
}

.dataTables_info {
	display: none;
}

.bottom .dataTables_info {
	display: block;
}

.dataTables_scrollHeadInner, .dataTable {
	width: 100% !important;
}

tfoot {
	display: table-header-group;
}

tfoot input[type="text"] {
	height: 22px;
	padding: 0 2px;
	font-size: 11px;
}

table.tablesorter tfoot tr {
	background: transparent;
}

table.display tfoot th {
	padding: 2px 5px;
	border-top: 1px solid #ccc;
	font-weight: normal;
	text-align: left;
}

.dataTables_paginate {
	margin-top: 0px;
}

#requestRequisitionReportTable .dataTables_scroll {
	overflow-x: hidden !important;
}

#requestRequisitionReportTable .dataTables_scrollHead {
	width: 100% !important;
}
/* .dataTables_scrollBody {
		max-height: 260px;
	} */
.dataTables_wrapper {
	padding-top: 0px;
}

table.display {
	margin-top: 0px;
}

.modal-body {
	/*max-height: calc(90vh - 250px); */
	overflow-y: auto;

	/* padding-top: 190px !important; */
}

.pointer {
	cursor: pointer;
}

.comment {
	float: left;
}

form select {
	width: 130px;
	height: 25px;
}

form input[type="text"] {
	height: 25px;
}

.modal-dialog {
	margin-top: 14%;
	width: 50%;
}

.request-reporttable th, .request-reporttable td, .report-status>table td,
	.report-status>table th {
	padding: 8px;
	border: 1px solid #ccc;
	vertical-align: top;
}

.request-reporttable td:first-child, .request-reporttable th:first-child
	{
	width: 20%
}

.request-reporttable td input[type="text"] {
	min-height: 27px;
	margin-right: 3px;
	line-height: 27px;
	display: inline-block;
	padding: 0 2px;
	float: left;
	width: 200px;
	margin: 0 3px 0 0;
}

.sub-row {
	clear: both;
	margin: 8px 0 0 0;
	display: table;
	width: 100%;
}

.sub-row:first-child {
	margin-top: 0;
}

.report-status {
	margin-top: 10px;
}

.request-reporttable  .remove_field {
	padding-top: 5px;
	display: inline-block;
}

.new-value {
	width: 63%;
	float: left;
}

.blocked-select {
	width: 35%;
	float: left;
}

.new-value textarea {
	width: 100%;
	min-height: 45px;
}

.well {
	background-color: #f6f6f6;
	border: 1px solid #e3e3e3;
	border-radius: 4px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.05) inset;
	margin-bottom: 20px;
	min-height: 20px;
	padding: 19px;
}

td table tbody tr td {
	border: none !important;
}

.ui-multiselect-menu {
	display: none;
	padding: 3px;
	position: absolute;
	z-index: 10000;
	text-align: left;
}

form#forwardRRFForm table .ui-multiselect-menu {
	position: inherit;
	margin: 0px;
}

/*Sumit Jain style changes*/
.tab_div {
	background: #fff;
	padding: 0;
	border: none !important;
}

.f_inline {
	display: inline-flex;
}

.my_table {
	border-collapse: collapse !important;
	/*overflow: hidden !important;*/
}

/*.my_table:hover{
		overflow: auto !important;
	}*/
.dataTables_scrollBody {
    max-height: calc(100vh - 265px);
    width: 100% !important;
    min-height:310px;
}

 
td.stop_click.action-dropdown.dropbtn.dropdowns.stop_click.align-center.action-dropdown.dropdowns
	{
	padding-right: 30px !important;
}

.my_form {
	margin-top: 0;
}

.my_form table tr {
	background: #fff !important;
	border-bottom: 1px solid #cecece;
}

.my_form table tr td {
	border-radius: 0;
	border: 0;
	padding: 0;
}

.client-filter button.uiDropDown {
	width: 300px !important;
	padding: 7px 18px 5px 15px !important;
	border-radius: 50px !important;
	box-shadow: unset;
	color: #999 !important;
	vertical-align: middle;
	border: 1px solid #ccc !important;
	position: relative;
	background: transparent !important;
	font-size: 12px !important;
	outline: none !important;
}

.group-filter button.uiDropDown {
	width: 300px !important;
	padding: 7px 18px 5px 15px !important;
	border-radius: 50px !important;
	box-shadow: unset;
	color: #999 !important;
	vertical-align: middle;
	border: 1px solid #ccc !important;
	position: relative;
	background: transparent !important;
	font-size: 12px !important;
	outline: none !important;
}

.my_btn {
	background: #badcff !important;
	color: #3276b7 !important;
	font-size: 12px !important;
	box-shadow: none !important;
	border-radius: 2px !important;
	border: 0 !important;
	width: 80px !important;
	font-weight: bold;
	text-shadow: none !important;
	transition: all 0.5s;
}

.my_btn:hover {
	background: #3276b7 !important;
	color: #fff !important;
	transition: all 0.5s;
}

/*Datatable css changes*/
tr {
	transition: all 1s !important;
}

#records_tableId_wrapper.dataTables_wrapper {
	padding-top: 0;
}

div.dataTables_wrapper {
	background: #f5faff;
}

div.dataTables_wrapper .top .dataTables_paginate {
	display: none;
}

.paging_full_numbers a {
	margin: 0 !important;
	padding: 10px 14px !important;
	background: #fff !important;
	color: #999 !important;
}

.paging_full_numbers a.paginate_active {
	color: #fff !important;
	background-color: var(--nav-active) !important;
	font-weight: bold;
	border-radius: 50%;
	border: 1px solid var(--nav-active);
}

.dataTables_filter {
	top: -40px;
	color: #fff;
	border: 0;
	margin: 0;
	left: 400px;
}

.dataTables_filter input {
	width: 300px !important;
	padding: 7px 18px 5px 15px !important;
	border-radius: 50px !important;
	box-shadow: unset;
	color: #999 !important;
	vertical-align: middle;
	border: 1px solid #ccc;
	position: relative;
	background: transparent;
	font-size: 12px !important;
}

.my_table .details {
	padding: 0 !important;
}

.my_table .details table {
	border-collapse: collapse !important;
}

.details .dataTables_wrapper {
	background: #f5faff !important;
	padding-left: 10px;
}

.details .dataTables_wrapper table thead th {
	background: #f5faff !important;
	border: 0 !important;
	color: #5189c2 !important;
}

.details .dataTables_wrapper table tbody tr td {
	background: #f5faff !important;
}

.my_table tbody tr.content:hover td {
	background: var(--nav-active) !important;
	cursor: pointer;
	transition: 0.5s;
	color: #fff;
}

.my_table tbody tr td.active {
	background: #dddd !important; */
	color: #5189c2 !important;
}

.main-footer {
	padding: 8px;
}

.sidebar-menu>li>a {
	padding: 11px 5px 11px 15px
}

/* .action_btn:hover{
		color: #fff !important;
		background: #3376bc;
	} */
.action_btn:hover.delete_btn {
	color: #fff !important;
	background: #ff0000;
}

.action_btn i {
	font-size: 14px;
}

.my_submit_btn {
	min-width: 16px !important;
	border-radius: 50px !important;
	width: 10px !important;
	padding: 0px !important;
}

body .positiondashboard-table .align-center {
	text-align: center !important;
}

.wrap-div {
	white-space: normal;
	min-width: 200px;
}

.pd-20 {
	padding: 20px;
}

.bg-none {background-none;
	
}

.close-right {
	display: inline-block;
	width: 100%;
	text-align: right;
}

#modal_data_table_wrapper table.dataTbl thead th {
	width: 180px !important;
}
/*Datatable css changes*/
.resourceSubmission {
	color: #29abe2 !important;
}

.resourceShortlisted {
	color: orange;
}

.resourceJoined {
	color: #998675;
}

.resourceRejected {
	color: #666667;
}

.orange-batch {
	width: 10px;
	height: 10px;
	background: orange;
	display: inline-block;
	border-radius: 40px;
	position: relative;
	top: -10px;
	right: 5px;
}

.forward-rrf {
	position: relative;
}

.forward-rrf button {
	width: 230px !important;
	text-indent: 8px;
}

.forward-rrf .ui-multiselect-menu {
	top: inherit !important;
	left: 7px !important;
	position: absolute !important;
}

/*Comnents css*/
.commentIcon {
	color: #139f72;
	font-size: 24px;
	margin: 1px 10px;
	cursor: pointer;
}

.modal-content.commentmodal {
	width: 80%;
	margin-top: 15%;
}

.commentmodal>.modal-header {
	background-color: #0067a5 !important;
	color: #fff !important;
	padding: 10px;
	border-bottom: 1px solid #4f92d7;
}

.commentmodal>.modal-header>.close {
	opacity: 1;
	color: #fff;
	box-shadow: none;
}

.commentmodal>.modal-header>.close:hover {
	background-color: transparent;
}

.commentmodal .modal-body {
	min-height: 100px;
	max-height: 250px;
	overflow-x: auto;
	padding-top: 0 !important;
}

.commentmodal .commentBody>.commentText {
	word-wrap: break-word;
	padding: 2px 5px;
	margin: 0 0 10px;
	padding-bottom: 10px;
	font-size: 14px;
	background-color: #eee;
	border-left: 2px solid #72A1C9;
}

.commentBody>.commentText footer {
	display: block;
	font-size: 80%;
	line-height: 0.5;
	color: #777;
}

.commentmodal .commentbox {
	border-top: 1px solid #e5e5e5;
	padding: 5px 10px;
	background-color: #f5faff;
	border-right: 2px solid #72A1C9;
	box-shadow: 0px 0px 4px #333;
}

.JDate, .IDate, .interviewDate, .joinDate {
	display: none;
}

/*Comnents css*/

/*Sumit Jain style changes*/
.my-comments-modal .sidebar a {
	color: #01498B;
}

.my-comments-modal .sidebar li {
	margin: 5px 0;
	white-space: normal;
	padding: 5px;
}

.my_btn {
	background: #badcff !important;
	color: #3276b7 !important;
	font-size: 12px !important;
	box-shadow: none !important;
	border-radius: 2px !important;
	border: 0 !important;
	width: 80px !important;
	font-weight: bold;
	text-shadow: none !important;
	transition: all 0.5s;
}

.my-comments-modal .sidebar li .treeview-menu {
	background: #eee;
	margin: 5px 0;
}

.my-comments-modal .sidebar .comments-box {
	border: 1px solid #ccc;
	padding: 5px;
	background: #eee;
}

.my-comments-modal .sidebar li .treeview-menu .comments-box .comments-detail
	{
	
}

.my-comments-modal .sidebar .treeview-menu .comments-date {
	font-size: 12px;
	color: #999;
	padding-top: 10px;
}

.my-comments-modal .sidebar-menu .treeview>a {
	background: #eee;
}

.text-width {
	width: 75% !important;
	margin-right: 5px !important;
}

i.fa.fa-download {
	margin: 6px 0px 4px 10px;
	color: #333333;
	font-size: 1em;
	width: 26px;
}

i.fa.fa-edit {
	margin: 4px 0px 4px 10px;
	color: #333333;
	font-size: 1em;
	width: 26px;
}

i.fa.fa-share {
	margin: 4px 0px 4px 10px;
	color: #333333;
	font-size: 1em;
	width: 26px;
}

i.fa.fa-comments {
	margin: 4px 0px 4px 10px;
	color: #333333;
	font-size: 1em;
	width: 26px;
}

i.fa.fa-trash {
	margin: 4px 0px 4px 10px;
	color: #333333;
	font-size: 1em;
	width: 26px;
}

i.fa.fa-users {
	margin: 4px 0px 4px 10px;
	color: #333333;
	font-size: 1em;
	width: 26px;
}

i.fa.fa-plus-square-o.my_submit_btn {
	margin: 4px 10px 4px 10px;
	color: #333333;
	font-size: 15px;
}

input#submitId {
	margin: 4px 14px 4px 4px;
}

#records_tableId .dropdown-content {
	 
	position: absolute;
	z-index: 1;
    top: -2px;
    left: 20px;
	box-shadow: 3px 4px 3px #888888;
	border-style: double;
	border-radius: 12px !important;
}

.textwrap {
	white-space: nowrap;
	width: 81px;
	overflow: hidden;
	text-overflow: ellipsis;
	margin-top: 0px;
	font-size: 11px;
}
/* td.stop_click.action-dropdown.dropdown.dropbtn.stop_click.align-center.action-dropdown.dropdown :hover {
	    background: #fff;
	} */
i.fa.fa-ellipsis-v :hover {
	background: #fff;
	color: #fff;
}
/* .dropdown-content a:hover {background-color: #ddd;} */
/* .dropdown:hover .dropdown-content {
	display: block;
} */

.dropdown:hover .dropbtn {
	background-color: #3e8e41;
}

.table#records_tableId {
	min-height: 700px;
}

#records_tableId tbody tr:nth-child(10) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

#records_tableId tbody tr:nth-child(9) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

#records_tableId tbody tr:nth-child(8) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

#records_tableId tbody tr:nth-child(8) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

#records_tableId tbody tr:nth-child(7) td.dropdowns .dropdown-content {
	top: auto !important;
	bottom: 0px !important
}

.dropdown-menu.dropdown-content {
	border-color: #F4F4F4;
}

#records_tableId .dropdown.dropleft.dropDownHover {
	    text-align: center;
    border-radius: 50%;
    background: transparent; 
    height: 24px;
    width: 24px;
    padding: 5px 1px 2px 1px;
}

#records_tableId .dropdown.dropleft.dropDownHover:hover {
	border-radius: 50%;
	background: #6e9ece;
	color: white;
	height: 24px;
	width: 24px;
	padding: 5px 1px 2px 1px;
}
/* .dataTables_scrollBody {
	    height: 751px !important;
	} */
/* a.dropdown-item:hover {
	    /* background: #f2f2f2; 
	color:#014689 !important;
	
	
	}
	* /
		/* .dropdown.dropleft:hover {
	    background: white;
	    border-radius: 50%;
	    width: 22px;
	} */
a.dropdown-item {
	font-weight: 600;
	color: #01498B !important;
	font-size: 0.9em;
	line-height: 22px;
	font-size: 12px;
}
/* table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.dataTable {
	    height: 767px;
	} */
.circle {
	background-color: rgba(0, 0, 0, 0);
	border: 5px solid rgba(0, 183, 229, 0.9);
	opacity: .9;
	border-right: 5px solid rgba(0, 0, 0, 0);
	border-left: 5px solid rgba(0, 0, 0, 0);
	border-radius: 50px;
	box-shadow: 0 0 35px #2187e7;
	width: 50px;
	height: 50px;
	margin: 0 auto;
	-moz-animation: spinPulse 1s infinite ease-in-out;
	-webkit-animation: spinPulse 1s infinite linear;
}

.circle1 {
	background-color: rgba(0, 0, 0, 0);
	border: 5px solid rgba(0, 183, 229, 0.9);
	opacity: .9;
	border-left: 5px solid rgba(0, 0, 0, 0);
	border-right: 5px solid rgba(0, 0, 0, 0);
	border-radius: 50px;
	box-shadow: 0 0 15px #2187e7;
	width: 30px;
	height: 30px;
	margin: 0 auto;
	position: relative;
	top: -50px;
	-moz-animation: spinoffPulse 1s infinite linear;
	-webkit-animation: spinoffPulse 1s infinite linear;
}

.blockUI {
	z-index: 10000 !important;
}

.blockUI.blockOverlay {
	z-index: 9999 !important;
}

.blockUI.blockMsg.blockPage {
	z-index: 9999 !important;
}
/*End Custom loader*/
.modal-body.model-padding {
	padding-top: 12px !important;
	color: black;
}

.modal-header.headerStyle {
	color: black;
}

table.tablesorter tr td a {
	color: black;
	text-decoration: none;
	font-size: 12px;
}
/* a.dropdown-item:hover {
	    color: #01498B !important;
	}
	 */
p.p-whitespace {
	white-space: initial;
	height: 250px;
}

td.stop_click.align-center.action-dropdown.dropdowns.dropbtn {
	height: 48px;
}

tr.content {
	height: 0px !important;
}

/* th.stop_click.align-center.action-dropdown.dropdowns.sorting.dropbtn {
	    width: 70px !important;
	}
	th.sorting {
	    width: 66px !important;
	}
	th.rrfText.sorting {
	    width: 45px !important;
	} */
table.dataTbl.display.tablesorter.addNewRow.alignCenter.my_table.dataTable
	{
	table-layout: fixed;
}

.modal-body.noPadding {
	padding-top: 15px !important;
}
/* 19-feb-19*/
table#modal_data_table {
	width: 119% !important;
}

div#modal_data_table_wrapper {
	overflow: auto;
	overflow-x: scroll;
	height: 217px;
}

a.bgclass {
	background: #01498B !important;
	border-left-color: rgb(243, 171, 31) !important;
	color: #fff;
}

input.search_init {
	color: #999
}

.projecttoolbar {
	border: -65px solid red;
	width: 50%;
	float: left;
}

.blue_link {
	left: -200
}

#defaultTableId {
	
}

#defaultTableId thead th {
	width: 120px;
}

#defaultTableId td {
	word-wrap: break-word;
}

.dtable td {
	background: #ebebeb;
}

.rRReportFilter td {
	padding: 10px;
}

.activeReportTab {
	background: #84DFC1;
}

.datatable-scroll {
	max-width: 2000px;
	overflow-x: auto;
	position: relative;
}
/*position dashboard css by anurag*/
.width110 {
	width: 145px !important;
}

.positiondashboard-table {
	position: relative;
	border: 1px solid rgba(0, 0, 0, .12);
	border-collapse: collapse;
	/* white-space: nowrap; */
	font-size: 13px;
	background-color: #fff;
}

.positiondashboard-table thead {
	padding-bottom: 3px;
}

table.dataTable thead>tr>th.sorting_asc, table.dataTable thead>tr>th.sorting_desc,
	table.dataTable thead>tr>th.sorting, table.dataTable thead>tr>td.sorting_asc,
	table.dataTable thead>tr>td.sorting_desc, table.dataTable thead>tr>td.sorting
	{
	padding-right: 30px;
}

.positiondashboard-table th {
	vertical-align: middle;
	text-overflow: ellipsis;
	text-align: left !important;
	font-weight: 700;
	line-height: 24px;
	border-right: 0px solid #FFFFFF !important;
	letter-spacing: 0;
	border-radius: 0 !important;
	font-size: 12px;
	color: rgba(0, 0, 0, .54);
	padding-bottom: 8px;
	background: #fff !important;
	color: #333 !important;
	border: none;
	padding-left: 24px !important;
}

.positiondashboard-table td, .positiondashboard-table th {
	position: relative;
	height: 48px;
	box-sizing: border-box;
}

.positiondashboard-table td {
	border-top: 1px solid rgba(0, 0, 0, .12);
	border-bottom: 1px solid rgba(0, 0, 0, .12);
	padding-top: 12px;
	vertical-align: middle;
	text-align: left !important;
	background: #fff !important;
	border: 0px solid #FFFFFF !important;
	border-top: 1px solid rgba(0, 0, 0, .12) !important;
	border-bottom: 1px solid rgba(0, 0, 0, .12) !important;
	border-radius: 0 !important;
	padding-left: 24px !important;
	padding-right: 18px !important;
}

.positiondashboard-table tbody tr:hover {
	background-color: #ddd;
}

.bottom {
	padding: 15px;
	background: #fff;
	position: relative;
}

.first.paginate_button {
	display: none;
}

.last.paginate_button {
	display: none;
}

.previous.fa.fa-chevron-left.paginate_button {
	color: rgba(20, 67, 88, 0.7) !important;
	padding: 8px 8px !important;
	border-top-left-radius: 12px !important;
	border-bottom-left-radius: 12px !important;
}

.next.fa.fa-chevron-right.paginate_button {
	color: rgba(20, 67, 88, 0.7) !important;
	padding: 8px 8px !important;
	border-top-right-radius: 12px !important;
	border-bottom-right-radius: 12px !important;
}

.dataTables_paginate.paging_full_numbers {
	margin-bottom: 8px;
	float: left !important;
	margin-left: 165px !important;
}

.dataTables_length label {
	/* color: #999;
	font-weight: normal; */
	color: black;
	font-weight: 600;
}

#requestRequisitionReportTable .dataTables_length label  {
    position: relative;
    top: -5px;
    }
    
    
#requestRequisitionReportTable  #records_tableId_length.dataTables_length label  {
    position: relative;
    top: -5px;
    }
    
#requestRequisitionReportTable  #records_tableId_length.dataTables_length label select {
    padding: 5px;
    width: 60px;
    border-radius: 50px;
    }

.dataTables_length select {
	/* width: 150px !important; 
	padding: 7px 18px 5px 45px !important;
	/* border-radius: 50px !important;
	box-shadow: unset;
	color: #999 !important;
	vertical-align: middle;
	border: 1px solid #ccc;
	position: relative;
	background: transparent;
	font-size: 12px !important; */
	
}

.dataTables_length select:focus {
	-moz-transition: border 0.2s linear 0s, box-shadow 0.2s linear 0s;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px
		rgba(82, 168, 236, 0.6);
	-moz-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px
		rgba(82, 168, 236, 0.6);
	-webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px
		rgba(82, 168, 236, 0.6);
}

.datalengthlabel1 {
	position: absolute;
	left: 18px;
	top: 8px;
}

.datalengthlabel2 {
	position: absolute;
	right: 45px;
	top: 8px;
}

.bottom .dataTables_length {
	top: 15px;
	left: 10px;
	color: #fff;
}

.top {
	position: relative;
}

.top .dataTables_length {
	top: -34px !important;
	left: 10px;
	color: #fff;
}

.width80 {
	width: 90px !important;
}

.sorting {
	background: #fff url(images/sort_both.png) no-repeat center right
		!important;
}

.filter-icon {
	top: 5px;
	right: 5px;
	font-size: 15px;
	cursor: pointer;
}

.filter-table-ps {
	height: 48px !important;
}

.modal-header {
	height: 48px;
}

.modal-title {
	float: left !important
}

.close span {
	display: none !important;
}

.filter-contain-row {
	text-align: left !important;
	height: 240px !important;
}

.filter-contain-row .f_inline .client-filter {
	padding: 0 !important;
}

.client-filter .ui-widget-header {
	border: none;
}

.client-filter .ui-multiselect-menu {
	width: 300px !important;
	border: 1px solid #ccc !important;
}

.client-filter .ui-multiselect-menu .ui-widget-header .ui-multiselect-filter input
	{
	margin: 0 !important;
	border-bottom-right-radius: 6px !important;
	border-bottom-left-radius: 6px !important;
	border-top-left-radius: 6px !important;
	border-top-right-radius: 6px !important;
	padding: 4px !important;
	width: 100% !important;
	border: 1px solid #ccc !important;
}


.client-filter .ui-multiselect-menu .ui-widget-header ul li.ui-multiselect-close
	{
	float: left !important;
}

.group-filter .ui-multiselect-menu .ui-widget-header ul li.ui-multiselect-close
	{
	float: left !important;
}

.filter-contain-row .f_inline .group-filter {
	padding: 0 !important;
	margin-left: 15px !important;
}

.group-filter .ui-widget-header {
	border: none;
}

.group-filter .ui-multiselect-menu {
	width: 300px !important;
	border: 1px solid #ccc !important;
}

.group-filter .ui-multiselect-menu .ui-widget-header .ui-multiselect-filter input
	{
	margin: 0 !important;
	border-bottom-right-radius: 6px !important;
	border-bottom-left-radius: 6px !important;
	border-top-left-radius: 6px !important;
	border-top-right-radius: 6px !important;
	padding: 4px !important;
	width: 100% !important;
	border: 1px solid #ccc !important;
}


/*.ui-icon {
	background-image: url(images/ui-icons_888888_256x240.png) !important;
}

.ui-icon {
	background-image: url(images/ui-icons_cccccc_256x240.png) !important;
}*/

.my_table thead th {
	background: #fff url('../resources/images/sort_both.png') no-repeat center right !important;
	color: #333 !important;
	    border-bottom: 1px solid #ccc !important;
}

.my_table .sorting {
	background:#fff url('../resources/images/sort_both.png') no-repeat center right !important;
}

.my_table .sorting_asc {
	background:#fff url('../resources/images/sort_asc.png') no-repeat center right !important;
}

.my_table .sorting_desc {
	background:#fff url('../resources/images/sort_desc.png') no-repeat center right !important;
}

.my_table .sorting_asc_disabled {
	background:#fff url('../resources/images/sort_asc_disabled.png') no-repeat center right !important;
}

.my_table .sorting_desc_disabled {
	background:#fff url('../resources/images/sort_desc_disabled.png') no-repeat center right !important;
}

.my_form .nice-select {
	width: 150px !important;
	padding: 7px 18px 5px 10px !important;
	border-radius: 50px !important;
	box-shadow: unset !important;
	color: #999 !important;
	vertical-align: middle !important;
	border: 1px solid #ccc !important;
	position: relative !important;
	background: transparent !important;
	font-size: 12px !important;
	height: 30px !important;
	font-size: 12px !important;
	line-height: 16px !important;
	float: left;
    clear: none;
}

.nice-select .current {
	margin-left: 5px;
}

.nice-select ul {
	width: 145px;
}

.width70 {
	width: 94px !important;
}
.width125 {
	width:125px !important;
}

.width71 {
width:71px !important;
}

.no-sort { pointer-events: none!important; cursor: default!important; }
.sortby-filtercontain {
	left: 170px;
	position: relative;
	top: 0px; 
}

.sortby-filter {
	left: 0; 
	top: 6px;
	float: left;
	margin-right: 10px;
	position: relative
}

.sortby-filtercontain .nice-select .current {
	margin-left: 8px !important;
}

#requestRequisitionReportTable .dataTables_wrapper .dataTables_info {
	width: 20%;
	margin-top: 9px;
}

.positiondashboard-table .paging_full_numbers a.paginate_button,
	.paging_full_numbers a.paginate_active {
	/* border: 1px solid #aaa; */
	padding: 2px 5px;
	margin: 0 3px;
	cursor: pointer;
	*cursor: hand;
	 
}

 

.color-red {
 
	padding: 5px 8px;
	border-radius: 40px;
	transition: 0.5s all;
}

.color-green {
	color: #000 !important;
	/* background: #00a99d !important;
		padding: 5px 8px !important;
		border-radius: 40px !important;
		transition: 0.5s all; */
}

.color-darkblue {
	color: #000 !important;
	/* background: #1e73b8 !important;
		padding: 5px 8px !important;
		border-radius: 40px !important;
		transition: 0.5s all; */
}

.color-lightblue {
	color: #fff !important;
	background: #29abe2 !important;
	padding: 5px 8px !important;
	border-radius: 40px !important;
	transition: 0.5s all;
}

.color-brown {
	color: #fff !important;
	background: #998675 !important;
	padding: 5px 8px !important;
	border-radius: 40px !important;
	transition: 0.5s all;
}

.color-grey {
	color: #fff !important;
	background: #666667 !important;
	padding: 5px 8px !important;
	border-radius: 40px !important;
	transition: 0.5s all;
}

.color-yellow {
	color: #000 !important;
	/* background: #e5bb53 !important; */
	padding: 5px 8px !important;
	border-radius: 40px !important;
	transition: 0.5s all;
}

.color-blue {
	color: blue !important;
	background: #0000ff40;
	padding: 5px 8px;
	border-radius: 40px;
	transition: 0.5s all;
}

.color-orange {
	color: #fff !important;
	background: #f7931e;
	padding: 5px 8px;
	border-radius: 40px;
	transition: 0.5s all;
}

/*added by kratika to align modal table from center to left-28-3-19*/
#myPreviewModal table.alignCenter tbody td {
	text-align: left !important;
}

.fixed-datatable-head {
  position: fixed;
  top:0; left:0;
  width: 100%;
}

#InterviewPanelModal button.ui-multiselect  {
    width: 100% !important;
    border-color: #ccc;
    border-radius: 50px !important;
    -webkit-border-radius: 50px !important;
    -moz-border-radius: 50px !important;
    height: auto !important;
    padding: 4px 15px !important;
    line-height: 26px !important;
    color: aliceblue;
    font-size: 14px !important;
    font-weight: 400 !important;
    }
    
 #InterviewPanelModal textarea {
 border-radius:50px;
 }
 
 #InterviewPanelModal button.ui-multiselect span.ui-icon {
    top: 5px !important;
    position: relative;
}

#InterviewPanelModal .title-sub-txt {
    font-size: 12pt;
    font-style: italic;
    color: #ccc;
    font-weight: normal;
}

.validateMe.invalid_input button {
  border: 1px solid #d87777 !important;
  background: rgb(251, 238, 238) !important;
}

.form-control.invalid_input {
  border: 1px solid #d87777 !important;
  background: rgb(251, 238, 238) !important;
}

 #InterviewPanelModal button.ui-multiselect:focus {
    outline: none;
 }
</style>
 
<script>
	
	 
	$(document).ready(function() {
		
		  
	/*Start custom Loader*/
	function startProgress(){
	
		  $.blockUI({ message: '</br></br><div class="circle"></div><div class="circle1"></div><center>Just a moment</center>' }); 
	  }
	  
	 function stopProgress(){
	
		 $.unblockUI();	
	 }
	 stopProgress();
	/*End custom Loader*/	 
	var flag = true;
	
	$(function() {
		$('#internalUserIds').multiselect({
			includeSelectAllOption: true,
			noneSelectedText: "Select Recipient",
		}).multiselectfilter();
	});
	
	$(function() {
		$('#pdlGroupIds').multiselect({
			includeSelectAllOption: true,
			noneSelectedText: "Select PDL",
		}).multiselectfilter();
	});
		
	$(function() {
		$('#client').multiselect({
			includeSelectAllOption: true,
			noneSelectedText: "Select Client",
		})
		
		$('#client').multiselectfilter({
			placeholder: 'Filter..',
		});


	$(document).ready(function() {
  $('.form-control.comboselect.check').niceSelect();
  $('#example-sort').niceSelect();
  
});
	});
	
	$(function() {
		$('#group').multiselect({
			includeSelectAllOption: true,
			noneSelectedText: "Select Group",
		});
		$('#group').multiselectfilter({
			placeholder: 'Filter..',
		});

});
	
	$('#client')
	.multiselect({
		checkAll : function() {
			getGroupForCustomer();
			$("#group").multiselect("disable");
		},
		uncheckAll : function() {
			flag= false;
			$("#group").multiselect("enable");
			getGroupForCustomer();
	}});
	
	
	var getGroupForCustomer =  function(){
		 var clientIds = [];
		 
		 $("#client option:selected").each(function(){
				var id = $(this).val();
				clientIds.push(id);
		 });

		 
		 
		if(flag == true && clientIds.length>0){
			startProgress();
			flag = true;
			$.ajax({
				type: 'GET',
				dataType: 'json',
		        url: '/rms/customers/'+clientIds+'/groups/', 
		        cache: false,
		        success: function(response) { 
		        	stopProgress();
						var grpDrpDwn="";
						 $('#group').empty();
						 for(var i=0; i<response.length; i++){
								 var populateGrp = response[i];
				                grpDrpDwn = grpDrpDwn + "<option value='"+populateGrp.groupId+"'>"+populateGrp.groupName+"</option>";     
				            }
						 $("#group").append(grpDrpDwn);
						 $('#group').multiselect('refresh');
			     	},
			     	error: function(error){
			     		stopProgress();
			     		showError("Something went wrong!!");
			     	}
				});
			}else{
				
				 flag = true;
				 $('#group').empty();	
				 $('#group').multiselect('refresh');
			}
		};
	
		
	
		$('#modal_data_table').DataTable({
	       	"bPaginate": false,
	       	"bFilter": false,
	        "scrollX":        true,
	        "scrollY":        200,
	       	"aaSorting": [],
	        "bInfo": false,
	        'bStyle':'overflow: auto; display: inline-block; height: 200px; border: 1px solid #ccc;',
	    	 'aoColumns': [ 
		   			{'aTargets': 0, 'sClass': 'stop_click stop_click align-left','sWidth':'60px' }, // numeric id
		   			{'aTargets': 1,'sClass': ''},	
		   			{'aTargets': 2,'sClass': ''},
	 				{'aTargets': 3,'sClass': ''}, // required skill
	 				{'aTargets': 4,'sClass': ''},
	 				{'aTargets': 5,'sClass': ''},
	 				{'aTargets': 6,'sClass': ''},
	 				{'aTargets': 7,'sClass': ''},
	 				{'aTargets': 8,'sClass': ''},
	 				{'aTargets': 9,'sClass': ''},
	 				{'aTargets': 10,'sClass': ''},
	 				{'aTargets': 11,'sClass': ''},
	 	        
		           ]
	 	});

		 
		
		var dataTablepd=  $('#records_tableId').DataTable( {
			   "sDom": 'RC<"clear"><"top"lfp>rt<"bottom"ip<"clear">', 
			   "aaSorting": [[1, 'asc']],
			  	//"sDom" : '<"top"flp<"clear">>rt<"bottom"ifp<"clear">>',
			   "sPaginationType": "full_numbers",
			   "oLanguage": {"sSearch": ""},
			   "bAutoWidth" : false,
			   "bInfo": true,
			   "iDisplayLength": 10,
			   "sScrollX": "100%",
	           "sScrollY": "250",
			   "bScrollCollapse": true,
			   "bSort": true,
			  
		       'aoColumns': [ 
		    	   			{'aTargets': 0, 'bVisible': false,'sWidth':'45px' }, // numeric id
		    	   			{'aTargets': 1,'sClass': 'stop_click align-center action-dropdown dropdowns'},	
		    	   		/* 	{'aTargets': 2,'sClass': 'stop_click align-center','bVisible': false }, 
		    	   <sec:authorize access="hasRole('ROLE_ADMIN')">
		        			{'aTargets': 3,'sClass': 'stop_click align-center','bVisible': false }, //submit profile
		           </sec:authorize> */		
		        			{'aTargets': 2,'sClass': ''},
		        			{'aTargets': 3,'sClass': 'rrfText'}, // required skill
		        			{'aTargets': 4,'sClass': ''},
		        			{'aTargets': 5,'sClass': ''},
		        			{'aTargets': 6,'sClass': ''},
		        			{'aTargets': 7,'sClass': 'align-center'},
		        			{'aTargets': 8,'sClass': 'align-center'},
		        	        {'aTargets': 9,'sClass': 'stop_click align-center'},
				            {'aTargets': 10,'sClass': 'stop_click align-center'},
				            {'aTargets': 11,'sClass': 'stop_click align-center'},
				            {'aTargets': 12,'sClass': 'stop_click align-center'},
				            {'aTargets': 13,'sClass': 'align-center'}, 
				            {'aTargets': 14,'sClass': 'align-center'},
				            {'aTargets': 15,'sClass': ''},
				            {'aTargets': 16,'sClass': ''},
				           ],
				           /* columnDefs: [
				                        { width: 200, targets: 0 }
				                    ],
				                    fixedColumns: true, */
				           "fnDrawCallback": function( oSettings ) {
			        	      
			        	      $(".even").addClass("content");
			        	  	 
			        	  	$(".action-dropdown").addClass("dropdowns");
			        	  	$(".action-dropdown").addClass("dropbtn"); 
							$(".dropdown-menu").addClass("dropdown-content");
							$('#records_tableId_filter label input').attr('placeholder','Search table...');
							 
			        	   } 
		});

		$('#example-sort').change(function() {

    var col = $(this).val();
    dataTablepd.fnSort([ [ col, 'asc'] ]);


});    
		 
	
		$("#client").on('change', function(){
			getGroupForCustomer();
			if($('#client').multiselect("widget").find("input:checked").length > 1 ){
				 $("#group").multiselect("disable"); 
			 }else{
				 $("#group").multiselect("enable");  
			 }
		})
		var isClick=true; 
		$(".action-dropdown").on('click', function(){
			if(isClick){
				//$(this).css("background", "#d9ebff");
				//$(this).css("cursor", "pointer");
				//$(this).css("transition", "0.5s");
				//$(this).css("color", "#5189c2");
				//for apply the style to sibblings
				//$(this).siblings().css("background", "#d9ebff");
				//$(this).siblings().css("cursor", "pointer");
				//$(this).siblings().css("transition", "0.5s");
				//$(this).siblings().css("color", "#5189c2");
				isClick=false;
			}
			else{
				$(this).css("background", "");
				$(this).css("cursor", "");
				$(this).css("transition", "");
				$(this).css("color", "");
				//for apply the style to sibblings
				$(this).siblings().css("background", "");
				$(this).siblings().css("cursor", "");
				$(this).siblings().css("transition", "");
				$(this).siblings().css("color", "");
				isClick=true;
			}
		});
		
		$(".additionalDataClick").bind('click', function(){		
		});
		
		
	});
	
	function commentClassForAjaxClick(resourceId, resourceName, skillReqName){
		
		$('#resCMId').val(resourceId);
		var resourceName=resourceName;
		$('#getResultDiv').html('');
		$.ajax({
	    	   type: 'GET',
	    	   url: 'loadResourceCommentByResourceId',
	    	   dataType: "text",
	    	   data: {"resourceId":resourceId},
	    	   success: function(response){
	    		   arrob = JSON.parse(response);
	    		   var comment = "";
					//var customer = "- Customer with Id = " + i + ", firstname = " + customer.firstname + ", lastName = " + customer.lastname + "<br>";	
					$.each(arrob, function(intIndex, objComment){
						var date=new Date(objComment.comment_Date);
						var day=date.getDate();
						var month=date.getMonth() + 1;
						var year =date.getFullYear();
						var hours =date.getHours();
						var minutes =date.getMinutes();
						var seconds =date.getSeconds();
						
						
						if (month.toString().length < 2)
							month = '0' + month;
					    if (day.toString().length < 2) 
					    	day = '0' + day;
					    
					    if (hours.toString().length < 2) 
					    	hours = '0' + hours;
					   
					    if (minutes.toString().length < 2) 
					    	minutes = '0' + minutes;
					    
					    if (seconds.toString().length < 2) 
					    	seconds = '0' + seconds;
										
						comment = "<div class='commentText'><p>"+objComment.resourceComment+"</p> <footer>From " + objComment.commentBy + " : " +" " + day+"-"+month+"-"+year+" " +hours+":"+minutes+":"+seconds+"</footer></div>";
											
						
						$('#getResultDiv').append(comment) 
					});
					
					//var requirementID = $("#requirementID").val();
					//alert(requirementID);
				    
					$("#reqNameId").text( skillReqName + " : " + resourceName);//+ resourceId
					$("#resourceCommentModal").removeClass("fade");
					$("#resourceCommentModal").show();  
	    	   },
			   error:function(response){
				   //stopProgress();
			       //showError("Something wrong happened!!");
				   alert(response);
			   },
			   complete:function(){
				   $('.commentmodal .modal-body').scrollTop($('.commentmodal .modal-body')[0].scrollHeight);
				   $('#usr').focus();
			   }
		   });
	}
	
	
	
	function strCSubmit(){
	  var strComment = $("#usr").val().trim();
	  var intResourceId = $("#resCMId").val().trim();
	  
	  var headerVal= $('#reqNameId').text().split(': ')[0].trim();
	 
	  var resourceComment = {
			  "resourceComment" : strComment,
			  "resourceId" : intResourceId
	  }
	  
	  if($("#usr").val()!= "" ){
		  if($("#usr").val().length <= 500){
	  
	  $.ajax({
	        type: "POST",
	        url: 'postResourceComment',
	        contentType:'application/json; charset=utf-8',
	        dataType:'json',
	        data: JSON.stringify(resourceComment),    
	        success: function () {  
	     	   //showSuccess("Your Comment is Saved Successfully!");	        	  
	        },
	        complete: function(){
	     	 callGetAjax(intResourceId, headerVal);        	  
	        }
	    });
	  //showSuccess("Your Comment is Saved Successfully!");	 
		  }
	  else
		   showError("Please enter character less than or equal to 500 characters");		  
	  }
	  else
		   showError("Please add comment!");
	  $("#strCSubmit").attr('disabled',true);
	    	  setTimeout(function(){
	    		  $("#strCSubmit").attr('disabled',false);
	    		  $('.toast-close').click();
	    	  }, 2000);	  
	  
	    $("#usr").val('');
			
		  
	}
		
	var callGetAjax = function(id, headerVal){
	  var resourceId = id;
	  var resourceName = $('#reqNameId').text().split(': ')[1].trim();
	  $('#getResultDiv').html('');
	  $.ajax({
	   	   type: 'GET',
	   	   url: 'loadResourceCommentByResourceId',
	   	   dataType: "text",
	   	   data: {"resourceId":resourceId},
	   	   success: function(response){
		   		   arrob = JSON.parse(response);
		   		   var comment = "";
					//var customer = "- Customer with Id = " + i + ", firstname = " + customer.firstname + ", lastName = " + customer.lastname + "<br>";	
					$.each(arrob, function(intIndex, objComment){
						var date=new Date(objComment.comment_Date);
						
						var day=date.getDate();
						var month=date.getMonth() + 1;
						var year =date.getFullYear();
						var hours =date.getHours();
						var minutes =date.getMinutes();
						var seconds =date.getSeconds();
						
						if (month.toString().length < 2)
							month = '0' + month;
					    if (day.toString().length < 2) 
					    	day = '0' + day;
					    
					    if (hours.toString().length < 2) 
					    	hours = '0' + hours;
					   
					    if (minutes.toString().length < 2) 
					    	minutes = '0' + minutes;
					    
					    if (seconds.toString().length < 2) 
					    	seconds = '0' + seconds;
										
						comment = "<div class='commentText'><p>"+objComment.resourceComment+"</p> <footer>From " + objComment.commentBy + " : " +" " + day+"-"+month+"-"+year+" " +hours+":"+minutes+":"+seconds+"</footer></div>";
						
						$('#getResultDiv').append(comment) 
					});
					var requirementID = $("#requirementID").val();
					//alert(requirementID);
			       // $("#reqNameId").text( $('.ReqID').text().split(':')[1] + " :: " + resourceName);//+ resourceId
			        $("#reqNameId").text( headerVal + " : " + resourceName);//+ resourceId
					$("#resourceCommentModal").removeClass("fade");
					$("#resourceCommentModal").show();  
	   	   		},
			   error:function(response){
				  
				  
			   },
			   complete: function(){
				   $('.commentmodal .modal-body').scrollTop($('.commentmodal .modal-body')[0].scrollHeight);
				   $('#usr').focus();
			   }
		   });
	}
	
	
	 
	 // close popup comment box.
	
	function onClickclsComment(){
	
		document.getElementById("strCSubmit").disabled = false;
		$("#usr").val('');
		$("#resourceCommentModal").addClass("fade");
		$("#resourceCommentModal").hide();  
	}
	
	
	
	var statusOption = [];
	var statusLabel = [];
	
	var resourceCounter = 0;
	
	function intializeVariable(){
		
		if(statusOption.length<=0){
			<c:forEach var="reportData"	items="${reportStatusList}">
				statusOption.push('${reportData.id}');
				statusLabel.push('${reportData.statusName}');
			</c:forEach>
		}
	}
	
function updateResourceStatus()
	{
		var resourceStatus = [];
		var e ="";
		var profileStaus ="";
		var interviewDate="";
		var resourceId="";
		var skillReqId="";
		var allocationStartDate="";
		
		for(var i = 0; i< resourceCounter;i++){
			
			 e = document.getElementById("statusId"+i);
			 profileStaus = e.options[e.selectedIndex].value;
					
			//interviewDate=document.getElementById("dateStatus"+i).value;
			resourceId=document.getElementById("resourceId"+i).value;
			skillReqId=document.getElementById("skillReqId"+i).value;
			
			interviewDate="";
			if(profileStaus==9)
			{
				if(document.getElementById("statusChange"+i)!=null)
					interviewDate=document.getElementById("statusChange"+i).value; 
			}
			allocationStartDate="";
			
			if(profileStaus==2){
				if(document.getElementById("statusChange"+i)!=null)
					allocationStartDate=document.getElementById("statusChange"+i).value; 
				else
					allocationStartDate="";
			}
			
			if(profileStaus==8){
				if(document.getElementById("statusChange"+i)!=null)
					allocationStartDate=document.getElementById("statusChange"+i).value; 
			}
			
				
			
			resourceStatus.push({"resourceId":new Number(resourceId),"profileStaus" :new Number(profileStaus) ,"interviewDate" :interviewDate,"allocationStartDate":allocationStartDate, "skillReqId":skillReqId});
			
		}
		
		
		$.ajax({
			type: 'POST',
	        url: 'updateResourcesWithStatus',
	        contentType: 'application/json',
	        dataType: 'json',
	        async:false,
	        processData: false,
	     	data: JSON.stringify(resourceStatus),
	     	success: function(response) { 	
	     			closePopup();
	     			showSuccess("Resource Status updated successfully !");
	     			if(typeof document.getElementById("openId"+response[0].id).childNodes[0]!='undefined'&&typeof document.getElementById("openId"+response[0].id).childNodes[0]!='null'){
	     				document.getElementById("openId"+response[0].id).childNodes[0].innerHTML=response[0].open;	
	     			}
	     			if(typeof document.getElementById("closedId"+response[0].id).childNodes[1]!='undefined'&&typeof document.getElementById("closedId"+response[0].id).childNodes[1]!='null'){
	     				document.getElementById("closedId"+response[0].id).childNodes[1].childNodes[1].innerHTML=response[0].closed;	
	     				document.getElementById("closedId"+response[0].id).childNodes[1].onclick = function() {
		      			 	onClickResourceProfile('Joined Resources','8',response[0].id,response[0].requirementId,response[0].closed);
		      			 };
	     			}
	     			if(typeof document.getElementById("shortlistedId"+response[0].id).childNodes[1]!='undefined'&&typeof document.getElementById("shortlistedId"+response[0].id).childNodes[1]!='null'){
	     				document.getElementById("shortlistedId"+response[0].id).childNodes[1].childNodes[1].innerHTML=response[0].shortlisted;
	     				document.getElementById("shortlistedId"+response[0].id).childNodes[1].onclick = function() {	      					
		      			 	 onClickResourceProfile('Shortlisted Resources','2',response[0].id,response[0].requirementId,response[0].shortlisted)
		      			 };
	     			}
	   				if(typeof document.getElementById("rejectId"+response[0].id).childNodes[1]!='undefined'&&typeof document.getElementById("rejectId"+response[0].id).childNodes[1]!='null'){
	   					document.getElementById("rejectId"+response[0].id).childNodes[1].childNodes[1].innerHTML=response[0].notFitForRequirement;
	   					document.getElementById("rejectId"+response[0].id).childNodes[1].onclick = function() {
		   			 		onClickResourceProfile('Not Fit Resources','3,6',response[0].id,response[0].requirementId,response[0].notFitForRequirement)
		   			 	 };
	   				}
	   				document.getElementById("statusId"+response[0].id).innerHTML=response[0].status;
	      			 $('#updateResPopUp').modal('hide'); 
	      			 
	     	},
	     	 error:function(data){
	     		 showError("Some thing went wrong !");
	     		 $('#updateResPopUp').modal('hide'); 
	     		 //window.location.href  = "positionDashboard";
		    }   	
	 	}) 
		
		
	}
	function prepareActionComments(resourceId, internalResId, resourceType, resourceName, skillReqName){
		
		var tdData = "";
		
		tdData='<a href="#" class="pointer action_btn" onclick="fileDownload('+internalResId+',`'+resourceType+'`)" title="Download Resume"><i class="fa fa-download" aria-hidden="true"></i></a>';
		
		if(resourceType=="External")
			tdData = tdData+'<a  href="#" class="pointer action_btn" onclick="tacDownload('+resourceId+')" title="Download TEF"><i class="fa fa-download" aria-hidden="true"></i></a>';
		
		tdData = tdData+ '<a class="pointer action_btn" data-toggle="modal"  title="Comments" onClick="commentClassForAjaxClick('+resourceId+',`'+resourceName+'`,`'+skillReqName+'`)"> <i class="fa fa-commenting  commentClassForAjax" aria-hidden="true"></i></a>';
		
		
		return tdData;
	
	}
	function preparenoticePeriod( noticePeriod){
		var tdData = "";
		if(noticePeriod!="" && noticePeriod!=null )
			tdData= "<center>"+noticePeriod+"</center>";
		return tdData;
	}
	function prepareTotalExp(totalExp){
		var tdData = "";
		if(totalExp!="" && totalExp!=null )
			tdData= "<center>"+totalExp+"</center>";
		return tdData;
	}
	function prepareProfileStatus(id, selectedStatus,joinDate,interviewDate, resourceCounter, resourceName, skillReqName){
		intializeVariable();
		var tdData = "";
			tdData = "<input>"	
		if(selectedStatus=="JOINED")
			tdData = '<select id="statusId'+resourceCounter+'" onchange="statusChange(this.value,'+resourceCounter+',`'+joinDate+'`,`'+interviewDate+'`)" style="width: 50px !important; min-width: 150px;" disabled>';
		else
			tdData = '<select id="statusId'+resourceCounter+'" onchange="statusChange(this.value,'+resourceCounter+',`'+joinDate+'`,`'+interviewDate+'`)" style="width: 50px !important; min-width: 150px;">';
			
		for(var i = 0; i< statusLabel.length;i++){
			if(selectedStatus==statusLabel[i]){
				tdData = tdData+'<option value="'+statusOption[i]+'" selected>'+statusLabel[i]+'</option>';
						
			}else{
				tdData = tdData+'<option value="'+statusOption[i]+'">'+statusLabel[i]+'</option>';
			}
		}
		tdData = tdData+'</select><br/>';
		
		//tdData = tdData+ '<a class="pointer action_btn" data-toggle="modal"  onClick="commentClassForAjaxClick('+id+',`'+resourceName+'`,`'+skillReqName+'`)"> <i class="fa fa-commenting  commentClassForAjax" aria-hidden="true"></i></a>';
	 	
		tdData = tdData + '<input id="resourceId'+resourceCounter+'" type="hidden" value="'+id+'">';
		
		return tdData;
	} 
	
	function prepareDate(id,selectedStatus,joinDate,interviewDate, resourceCounter,skillReqId){
	 	
		var tdData ="";
	 		
	 	if(selectedStatus=="JOINED")
	 		tdData='<div id="dateStatus'+resourceCounter+'"> Joining Date '+joinDate+'</br> Interview Date '+interviewDate+'</br></div>';
	 	else if(selectedStatus=="INTERVIEW DONE - FEEDBACK PENDING")
	 		tdData='<div id="dateStatus'+resourceCounter+'"> Interview Date '+interviewDate+'</br></div>';
	 	
	 	else if(selectedStatus=="INTERVIEW LINED UP"){
	 		//tdData='<div id="dateStatus'+resourceCounter+'"> Interview Date '+interviewDate+'</br></div>';
	 		tdData='<div id="dateStatus'+resourceCounter+'" >';//onclick='datePicker("+resourceCounter+",`"+interviewDate+"`)'
	 		tdData = tdData+ "<input type='text' class='text-width' value=' Interview Date "+interviewDate+"' >";
	 		tdData = tdData+"<span class='action_btn'><i class='fa fa-calendar commentClassForAjax' data-id='statusChange"+resourceCounter+"' onclick='datePicker("+resourceCounter+",`"+interviewDate+"`)' aria-hidden='true'></i></span></div>";
	 		//tdData = "<input type='text' onclick='datepickernew("+id+")' value='"+interviewDate+"' id='dateStatus"+resourceCounter+"' class='datepickernew'>";
	 	}
	 	else if(selectedStatus=="SELECTED"){
	 	 	//tdData='<div id="dateStatus'+resourceCounter+'" onclick="datePicker('+resourceCounter+','+joinDate+')" > Joining Date '+joinDate+'</br></div>';
	 	 	tdData = '<div id="dateStatus'+resourceCounter+'">';//onclick='datePicker("+resourceCounter+",`"+joinDate+"`)' 
	 	 	tdData = tdData+"<input type='text'   class='text-width' value=' Joining Date "+joinDate+"' >";
	 	 	tdData = tdData+"<span class='action_btn'><i class='fa fa-calendar commentClassForAjax' data-id='statusChange"+resourceCounter+"' onclick='datePicker("+resourceCounter+",`"+joinDate+"`)' aria-hidden='true'></i></span></div>";
	 	}
	 	 	 
	 	else
	 		tdData = '<div id="dateStatus'+resourceCounter+'"></div>';
	 	
	 		tdData = tdData+ '<input id="resourceId'+resourceCounter+'" type="hidden" value="'+id+'">';
	 		
	 		tdData = tdData+ '<input id="skillReqId'+resourceCounter+'" type="hidden" value="'+skillReqId+'">';
		
	 	return tdData; 
	}
	function statusChange(statusId, id, joinDate, interviewDate){
		 
		
		var tdData= document.getElementById("dateStatus"+id);
		
		if(statusId==2){
		 
			 
			
			//tdData.innerHTML = "Joining Date: </br><input id='fromdatepicker' type='text' size='8' value='"+ joinDate + "'/>";
			// tdData.innerHTML = "<input type='text' onclick='datepickernew("+id+")' id='statusChange"+id+"' class='datepickernew' value='"+joinDate+"'>";
			tdData.innerHTML = "<input type='text'  id='statusChange"+id+"' class='datepickernew text-width' value='"+joinDate+"'>"+"<span class='action_btn'><i class='fa fa-calendar commentClassForAjax' data-id='statusChange"+id+"' onclick='datepickernew("+id+")' aria-hidden='true'></i></span>";
			
		}
		else if (statusId==9)
			//tdData.innerHTML = "Interview Line Up Date: </br><input type='text' size='8' value='"+interviewDate+"'/>";
			//tdData.innerHTML = "<input type='text' onclick='datepickernew("+id+")' id='statusChange"+id+"' class='datepickernew'>";
			tdData.innerHTML = "<input type='text' id='statusChange"+id+"' class='datepickernew text-width'>"+"<span class='action_btn'><i class='fa fa-calendar commentClassForAjax' data-id='statusChange"+id+"' onclick='datepickernew("+id+")' aria-hidden='true'></i></span>";
		
		else if (statusId==8)
			tdData.innerHTML = "Joining Date: "+joinDate+"<br/> Interview Date:  "+interviewDate;
		
		else if(statusId==14)
			//tdData.innerHTML = "Interview Done - FeedBack Pending: "+interviewDate+"<br/>";
			tdData.innerHTML = "Interview Date: "+interviewDate+"<br/>";
		
		else
			tdData.innerHTML="";
	
	}
	
	$(document).on("click", ".hasDatepicker, .fa-calendar", function(){
	    var test= $(this).attr("id") || $(this).data("id");
	    $("#clearDate").click(function(){
	        $("#"+test).val("");
	        $("#"+test).datepicker('hide');
	    });
	});
	
	function datepickernew(id, date)
	{
		$("#statusChange"+id).datepicker({
			dateFormat: 'd-M-yy',
			setDate:date
		});
		$("#statusChange"+id).datepicker('show');
	}
	
	function datePicker(id, date)
	{
		var tdData= document.getElementById("dateStatus"+id);
		//tdData.innerHTML = "<input type='text' onclick='datepickernew("+id+")' id='statusChange"+id+"' class='datepickernew' value='"+date+"'>";
		tdData.innerHTML = "<input type='text' id='statusChange"+id+"' class='datepickernew text-width' value='"+date+"'>"+"<span class='action_btn'><i class='fa fa-calendar commentClassForAjax' data-id='statusChange"+id+"' onclick='datepickernew("+id+")' aria-hidden='true'></i></span>";
		datepickernew(id, date);
	}
	
	
	 
	function prepareResume(id, resourceType){
		
		var tdData = '';
		
		tdData='<a href="#" class="pointer action_btn" onclick="fileDownload('+id+',`'+resourceType+'`)" title="Download Resume"><i class="fa fa-download" aria-hidden="true"></i></a>';
		
		return tdData;
	}
	function prepareTEF(id,resourceType){
		var tdData = '';
		if(resourceType=="External")
			tdData='<a  href="#" class="pointer action_btn" onclick="tacDownload('+id+')" title="Download TEF"><i class="fa fa-download" aria-hidden="true"></i></a>';
		
		return tdData;
	}
	function fileDownload(id,resourceType){	
		
	 	$.ajax({				
	        url: 'downloadfiles',
	     	contentType: "application/json",
	     	async:false,
	     	data: {"id":id,
					"resourceType":resourceType
				},  	
	     	success: function(response) { 			     			
	     		if(response==""){
	     			showAlert("\u2022 File not found for id " + id + " ! <br />");
	     		}else{     	     			     			     		
	     		    if(id !== ''){
						window.location.href = 'downloadfiles?id='+id+'&resourceType='+resourceType;
					} 
					else{				 
						showAlert("\u2022 File not found for id " + id + " ! <br />");
					}
	    		}
	     	},
	     	 error:function(data){
	     		showAlert("\u2022 File not found for id " + id + " ! <br />");
		    }   	
	 	}) 
	}
	
	function tacDownload(id){	
		 	$.ajax({				
		        url: 'downloadTac',
		     	contentType: "application/json",
		     	async:false,
		     	data: {"id":id				
					},  	
		     	success: function(response) { 			     			
		     		if(response==""){
		     			showAlert("\u2022 File not found for id " + id + " ! <br />");
		     		}else{     	     			     			     		
		     		    if(id !== ''){
							window.location.href = 'downloadTac?id='+id;
						} 
						else{				 
							showAlert("\u2022 File not found for id " + id + " ! <br />");
						}
		    		}
		     	},
		     	 error:function(data){
		     		showAlert("\u2022 File not found for id " + id + " ! <br />");
			    }   	
		 	}) 
		}
	
	
	function prepareTableDataElement(statusId, id , resourcesCount, reqSkillName){
			var tdData = '';
			if(statusId==0){	
				tdData = '<a href="#myPreviewModal" onclick="onClickResourceProfile(`Submitted Resources`,`0`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)">';
				tdData= tdData+'<span class="open_pos_popup align-center color-lightblue">' ;
			}else if(statusId==2){
				tdData = '<a href="#myPreviewModal" onclick="onClickResourceProfile(`Shortlisted Resources`,`2`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)">';
				tdData = tdData+'<span class="open_pos_popup align-center color-orange">';
			}else if(statusId==8){
				tdData = '<a href="#myPreviewModal" onclick="onClickResourceProfile(`Joined Resources`,`8`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)">';
				tdData = tdData+'<span class="open_pos_popup align-center color-brown">';
			}else {
				tdData = '<a href="#myPreviewModal" onclick="onClickResourceProfile(`Not Fit Resources`,`3,6`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)">';
				tdData = tdData+'<span class="open_pos_popup align-center color-grey">';
			}
			tdData = tdData+resourcesCount+'</span>';
			return tdData;
	}
	function prepareAdditionalComments(addComments,id){
		var tdData ="";
		//tdData=  '<div class="tooltip"><div class="textwrap">'+addComments+'</div><span class="tooltiptext">'+addComments+'</span></div>';
		//tdData=  '<div class="textwrap"><a href="#" data-toggle="tooltip" style="margin-left:0" title="${reportData.addtionalComments}">${reportData.addtionalComments}</a></div>';
		/* <div class="textwrap" data-toggle="modal" data-target="#myModal${reportData.id}">${reportData.addtionalComments}</div> */
		tdData='<div class="textwrap" data-toggle="modal" data-target="#myModal'+id+'">'+addComments+'</div><div id="myModal'+id+'" class="modal fade" role="dialog"><div class="modal-dialog modal-sm"><div class="modal-content"><a href="#" onclick="closePopup()"><span class="close" data-dismiss="modal" id="close-icon" style="right: 20px; top: 18px;"></span></a><div class="modal-header headerStyle"><h4 class="modal-title">Additional Comments</h4></div><div class="modal-body model-padding"><p class="p-whitespace">'+addComments+'</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">Close</button></div></div></div></div>';
		return tdData;
	}
	function prepareActions(id, status, requestRequisitionId, sentToList, pdlList, resourcesCount, reqSkillName ){
		
		
		
		var tdData='<div class="dropdown dropleft dropDownHover" onclick="showActionDropdown(this)"><i class="fa fa-ellipsis-v" style="font-size:15px" class="btn dropdown-toggle"></i><div class="dropdown-menu">';
		
		 <sec:authorize access="hasRole('ROLE_ADMIN')">
		tdData = tdData+ '<div class="borderHover"><a class="dropdown-item" onclick="addRemoveResourceInRequestRequisition('+id+')" data-target="#addResource" id="'+id+'" href="javascript:void(0)" >'+
		 '<i class="fa fa-plus-square-o my_submit_btn"></i>Submit Resources</a></br></div>';
	
	</sec:authorize> 
		
	tdData =tdData+'<div class="borderHover"><a class="dropdown-item" data-toggle="modal" data-target="#myModalShowAll" title="Show all resources"onclick="onClickResourceProfile(`All Resources`,`0`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)" >'+
	'<i class="fa fa-users"></i>View all resources</a></br></div>';
		
		tdData=tdData+ '<div class="borderHover"><a class="dropdown-item"  onclick="skillRequestReportPDF(this,'+id+')" title="Download RRF"><i class="fa fa-download"></i>Download RRF</a></br></div>';
		tdData=tdData+'<div class="borderHover"><a class="dropdown-item" href="#" onclick="editrequest(this,'+id+')" title="Edit RRF"><i class="fa fa-edit" aria-hidden="true"> </i>Edit RRF</a></br></div>';
			
		if((sentToList != '' && sentToList != null) || (pdlList != '' && pdlList != null)){         
	    	tdData=tdData+ '<div class="borderHover"><a class="dropdown-item" data-toggle="modal" data-target="#forwrd-rrf-PD"  onClick="forwardRRF('+id+')" title="Forward">'+
	        '<i class="fa fa-share" aria-hidden="true"><span class="orange-batch" id="forwardOrangeId'+id+'"></span></i>Forward</a></br></div>';
	    }else{
	    	tdData = tdData+'<div class="borderHover"><a class="dropdown-item" data-toggle="modal" data-target="#forwrd-rrf-PD"  onClick="forwardRRF('+id+')" title="Forward">'+
	        '<i class="fa fa-share" aria-hidden="true"><span id="forwardOrangeId'+id+'"></span></i>Forward</a></br></div>';
	    }
		tdData = tdData+'<div class="borderHover"><a class="dropdown-item" data-toggle="modal" data-target="#myCommentsModal" onclick="getLatestCommentOnResource('+id+')" title="Comments">'+
		 	'<i class="fa fa-comments" aria-hidden="true"> </i>Comment</a></br></div>';
		tdData = tdData+'<div class="borderHover"><a class="dropdown-item" href="#" onclick="BGHfileDownload('+requestRequisitionId+')" title="Download BGH Approval">'+
	    	 '<i class="fa fa-download" aria-hidden="true"> </i>BGH approval</a></br></div>'+
	     	 '<div class="borderHover"><a class="dropdown-item" href="#" onclick="BUHfileDownload('+requestRequisitionId+')" title="Download BUH Approval">'+
	           '<i class="fa fa-download" aria-hidden="true"> </i>BUH approval</a></br></div>';
		
	    <sec:authorize access="hasRole('ROLE_ADMIN')">
			if(status != 'Closed'){
				tdData = tdData+'<div class="borderHover"><a onclick="skillRequestReportDelete('+requestRequisitionId+','+id+')" title="Delete RRF"class="dropdown-item" href="#">'+
		        '<i class="fa fa-trash" aria-hidden="true"> </i>Delete</a></br></div>';
			}
		</sec:authorize>	
		/* tdData = tdData+'<div class="borderHover"><a data-toggle="modal" data-target="#myInterviewerModal" onclick="getInterviewer(${reportData.requestRequisitionId},${reportData.id})" title="Interviewer"class="dropdown-item" href="#">'+
	    			'<i class="fa fa-user" aria-hidden="true"> </i>Interviewer</a></br></div>'; */
		<c:if test="<%=UserUtil.getCurrentResource().getEmployeeId() == 230 || UserUtil.getCurrentResource().getEmployeeId() == 4648 || UserUtil.getCurrentResource().getEmployeeId() == 5456 ||
				UserUtil.getCurrentResource().getEmployeeId() == 5929 ||UserUtil.getCurrentResource().getEmployeeId() == 5493 || UserUtil.getCurrentResource().getEmployeeId() == 211 ||UserUtil.getCurrentResource().getEmployeeId() == 353 ||UserUtil.getCurrentResource().getEmployeeId() == 4623
		%>">
	 	tdData = tdData+  '<div class="borderHover"><a onclick="addRemoveResourceInRequestRequisition('+id+')" data-target="#addResource" id="'+id+'" href="javascript:void(0)">'+
	 	'<input type="button" class="request-form-style my_btn my_submit_btn" style="font-size: medium;" id="submit" value="S" title="Submit"/></a></div>';
	 	</c:if> 
		
		tdData = tdData+ '</div></div>';
		
		return tdData;
	}
	
	function prepareRrfId(id){
		var tdData='<div class="textwraps" title="'+id+'" data-original-title="'+id+'">'+id+'</div>';
		return tdData;
	}
	/* var requestSkillName="";
	function prepareShowResource(id, resourcesCount, reqSkillName){ 
		
		requestSkillName=reqSkillName;
		var tdData = '<a class="pointer action_btn" data-toggle="modal" data-target="#myModalShowAll" title="show all resources"'+
		' onclick="onClickResourceProfile(`All Resources`,`0`,`'+id+'`,`'+reqSkillName+'`,`'+resourcesCount+'`)" ><i class="fa fa-group" aria-hidden="true"></i></a>';
		return tdData;
	} */
	
	function prepareBGHApproval(id){
		
		var tdData = '<a class="pointer action_btn" onclick="BGHfileDownload(${reportData.requestRequisitionId})" title="Download BGH Approval"><i class="fa fa-download" aria-hidden="true"></i></a>';
		return tdData;
	}
	
	function prepareBUHApproval(id){
		
		var tdData = '<a class="pointer action_btn" onclick="BUHfileDownload(${reportData.requestRequisitionId})" title="Download BUH Approval"><i class="fa fa-download" aria-hidden="true"></i></a>';
		return tdData;
	}
	
	function prepareComments(id){
		var tdData = '<a class="pointer action_btn" href="#myPreviewModalForComment" data-toggle="modal" onclick="getAllComments(${reportData.id})" data-target="#myModalComments" title="Comments"><i class="fa fa-commenting  commentClassForAjax" aria-hidden="true"></i></a>';
		return tdData;
	}
	
	/* function prepareSubmitActions(id){
		var tdData =  '<a onclick="addRemoveResourceInRequestRequisition('+id+')" data-target="#addResource" id="'+id+'" href="javascript:void(0)">'+
			'<input type="button" class="request-form-style my_btn my_submit_btn" style="font-size: medium;" id="submit" value="S" title="Submit"/></a>';
		return tdData;
	} */
	
	function editrequest(obj,id) {
	    $(this).attr('target', '_blank');
	    window.location = "../requests?reqId="+id;
	}
	
	function forwardRRF(skillRequestId){
		var modal = document.getElementById('forwardModal');
		modal.style.display = "block";
		document.getElementById('skillRequestId').value=skillRequestId;
	}
	
	function skillRequestReportPDF(obj,id) {
	    $(this).attr('target', '_blank');
	    window.location = "../requestsReports/showPDFReport?reqId="+id;
	}
	
	function addRemoveResourceInRequestRequisition(id){
		window.location = "../requestsReports/resources?requestRequisitionId="+id;
	}
	
	function skillRequestReportDelete(requestId,skillRequestId){
	    if(confirm("Sure you want to delete this RRF? There is NO undo!"))
	       {
	              $.ajax({
	                     type: 'POST',
	                     url: '${pageContext.request.contextPath}/requestsReports/deleteSkillRequest',
	                     dataType: "text json",
	                     data: {"requestId":requestId,"skillRequestId":skillRequestId},
	                     success: function(data){
	                            showSuccess("RRF has been deleted Successfully! " + data.errMsg);
	                            window.location.reload();
	                     },
	                           error:function(response){
	                                  stopProgress();
	                               showError("Something happend wrong!!");
	                           },
	              });
	               
	       }
	       return false;
	    }
	    
	function BUHfileDownload(id){
	 	$.ajax({				
	        url: 'downloadBUHApproval',
	     	contentType: "application/json",
	     	async:false,
	     	data: {
	     			"reqid":id
	     		  
			},  	
	     	success : function(response) { 			     			
	     		if(response==""){
	     			
	     			showAlert("\u2022 File not found for id " + id + " ! <br />");
	     			
	     		}else{     	     			     			     		
	     		    if(id !== ''){
						window.location.href = 'downloadBUHApproval?reqid='+id;
					} 
					else{				 
		
						showAlert("\u2022 File not found for id " + id + " ! <br />");
					}
	    		}
	     	},
	     	 error:function(data){
	     		showAlert("\u2022 File not found for id " + id + " ! <br />");
		    }   	
	 	});
	}
	
	function BGHfileDownload(id){
		 
		$.ajax({				
	       url: 'downloadBGHApproval',
	    	contentType: "application/json",
	    	async:false,
	    	data: {
	    			"reqid":id
	    		  
			},  	
	    	success : function(response) { 			     			
	    		if(response==""){
	    			
	    			showAlert("\u2022 File not found for id " + id + " ! <br />");
	    			
	    		}else{     	     			     			     		
	    		    if(id !== ''){
						window.location.href = 'downloadBGHApproval?reqid='+id;
					} 
					else{				 
		
						showAlert("\u2022 File not found for id " + id + " ! <br />");
					}
	   		}
	    	},
	    	 error:function(data){
	    		showAlert("\u2022 File not found for id " + id + " ! <br />");
		    }   	
		});
	}
	
	function getAllComments(skillid){
		$.ajax({				
		       url: 'getAllComments',
		    	contentType: "application/json",
		    	async:false,
		    	data: {
		    			"id":skillid
		    		  
				}, 
				success : function(response){ 
				var data = JSON.stringify(response);
					
				},
				error : function(data){
				}
				});
		
	}
	
	function formatDateTime(dateTime)
	{
		var date=new Date(dateTime);
		
		var day=date.getDate();
		var month=date.getMonth() + 1;
		var year =date.getFullYear();
		var hours =date.getHours();
		var minutes =date.getMinutes();
		var seconds =date.getSeconds();
		
		
		if (month.toString().length < 2)
			month = '0' + month;
	    if (day.toString().length < 2) 
	    	day = '0' + day;
	    
	    if (hours.toString().length < 2) 
	    	hours = '0' + hours;
	   
	    if (minutes.toString().length < 2) 
	    	minutes = '0' + minutes;
	    
	    if (seconds.toString().length < 2) 
	    	seconds = '0' + seconds;
	    
	    return day+'-'+month+'-'+year+' '+hours+':'+minutes+':'+seconds;
	}
	
	function getLatestCommentOnResource(skillid){
		$.ajax({				
		        url: 'getLatestCommentOnResource',
		    	contentType: "application/json",
		    	async:false,
		    	dataType: "text",
		    	data: {
		    			"id":skillid
				}, 
				success : function(response){ 
						var resource_id;
					 	var arrObj = JSON.parse(response);
					    var ulist = document.getElementById("dynamic-list");
					    var li1,a,i,ul,a1,li,div;
						while (ulist.firstChild) {
					    	ulist.removeChild(ulist.firstChild);
					    }
					    if(arrObj.length === 0) {
					    	var li = document.createElement("li");
							li.setAttribute('class','comments-detail');
							li.appendChild(document.createTextNode('No Comments Available'));
							ulist.appendChild(li);
					    }
					    	$.each(arrObj, function(intIndex, objComment){
								if(resource_id == objComment['resourceId'])
					    		{
					    			li = document.createElement("li");
								    li.setAttribute('class','comments-detail','id','comment_list');
								    div = document.createElement("div");
								    div.setAttribute('class','comments-date');
								    li.appendChild(document.createTextNode(objComment['resourceComment']));
									div.appendChild(document.createTextNode('from '+objComment['commentBy'] +' : '+formatDateTime(objComment['comment_Date'])));
									ul.appendChild(li);
									li.appendChild(div);
									ulist.appendChild(li1);
									
					    		}else
					    		{
					    			resource_id = objComment['resourceId'];
					    			li1 = document.createElement("li");
									li1.setAttribute('class','treeview');
									a = document.createElement("a");
									a.setAttribute('href','javascript:void(0);');
									a.setAttribute('class','bgclass');
									li1.appendChild(a);
									a.appendChild(document.createTextNode(objComment['resourceName']));
									i = document.createElement("i");
									i.setAttribute('class','fa fa-angle-left pull-right');
									a.appendChild(i);
									ul = document.createElement("ul");
									ul.setAttribute('class','treeview-menu');
									a1 = document.createElement("a");
									a1.setAttribute('href','javascript:void(0);');
									li1.appendChild(ul);
									ul.appendChild(a1);
								 	li = document.createElement("li");
								    li.setAttribute('class','comments-detail','id','comment_list');
								    div = document.createElement("div");
								    div.setAttribute('class','comments-date');
								    li.appendChild(document.createTextNode(objComment['resourceComment']));
									div.appendChild(document.createTextNode('from '+objComment['commentBy'] +' : '+formatDateTime(objComment['comment_Date'])));
									ul.appendChild(li);
									li.appendChild(div);
									ulist.appendChild(li1);
									/* ul.appendChild(li);
									li.appendChild(div);  */
								}
								});
									},
				error : function(data){	
				}
				});
	}
	
	function setInterviewPanelInTextArea() {
	    if ($("#round1Select option:selected").length) {
			var round1data = '';
			$.each(	$("#round1Select option:selected"),
				function () {
					if ($("#round1Select option:selected").length == 1) {
						round1data = $(this).text()
					} else {
						round1data = round1data + $(this).text() + ', ';
					}
			})
		$("#keyInterviewer1").val(round1data);
		}
	    
	    if ($("#roundTwoSelect option:selected").length) {
			var round2data = '';
			$.each($("#roundTwoSelect option:selected"),
				function () {
					if ($("#roundTwoSelect option:selected").length == 1) {
						round2data = $(this).text()
					} else {
						round2data = round2data + $(this).text() +	', ';
					}
				})
			$("#keyInterviewer2").val(round2data);
		}
    }

	function getInterviewPanel(skillid) {
		document.getElementById('skillRequestId').value=skillid;
		$.ajax({				
			url: '/rms/requestsReports/getKeyInterviewPanels',
		   	contentType: "application/json",
		  	async:false,
		   	data: {"skillRequestId":skillid }, 
			success : function(response){ 
				var data = response;
				if(data){
					$('#round1Select').val(data.keyInterviewPanelOne);
					$('#roundTwoSelect').val(data.keyInterviewPanelTwo);
					$('#round1Select').multiselect('refresh');
					$('#roundTwoSelect').multiselect('refresh');
					empIdForRound1 = data.keyInterviewPanelOne;
					empIdForRound2 = data.keyInterviewPanelTwo;
					requiredFor = data.requiredFor;
					if (requiredFor == 'ODC'){
						$("#round1Select, #roundTwoSelect").closest('.form-group').addClass('validateMe'); //key interviewers mandatory in case of ODC
				        $('#keyInterviewer1,#keyInterviewer2').addClass('validateMe');
					}
					else {
					     $("#round1Select, #roundTwoSelect").closest('.form-group').removeClass('validateMe');
					     $('#keyInterviewer1,#keyInterviewer2').removeClass('validateMe');//key interviewers not mandatory other than ODC
					}
					setInterviewPanelInTextArea();
				 }
			},
			error : function(data){
			}
		});		
	}
	
	$(document).on('click','#submit',function() {
		var startDate = "";
		var endDate ="";
		var errmsg = "Please select:";
		var validationFlag = true;
		var customerIds = "";
		var groupIds = "";
		var statusIds ="";
		var customerIds = [];
		var groupIds = [];
		var hiringUnits = [];
		var reqUnits = [];
		/* setTimeout(function(){ startProgress(); }, 3000); */
		startProgress();
		$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
		$("#client option:selected").each(function () {
			var id = $(this).val();
			customerIds.push(id);
	 	}); 
		
		if(customerIds.length <=0){
			errmsg = errmsg + " Client";
			validationFlag = true;
		}
		
		
		if (!validationFlag) {
			stopProgress();
			if (errmsg.length > 0)
				showError(errmsg);
			return;
		} else {
			
			$("#group option:selected").each(function () {
				var id = $(this).val();
				groupIds.push(id);
		 	});
			$.ajax({
				type : 'GET',
				url : '/rms/requestRequisitionReport/getRequestRequisitionReport',
				contentType : "text/html; charset=utf-8",
				async : false,
				data :  "&customerIds="+customerIds
						+"&groupIds="+groupIds
						+"&statusIds="+statusIds
						+ "&hiringUnits=" +hiringUnits
						+ "&reqUnits=" +reqUnits,
						
						success : function(data) {
							if (data.length != 0) {
								$('#records_tableId').show();
								$("#NoAllocMessage").hide();
								$("#requestRequisitionReportTable").show();
								$('#records_tableId').dataTable().fnClearTable();
								var i = 1;
								stopProgress();
								$.each(data,function(key,val) {
									$('#records_tableId').show();
									$('#records_tableId').dataTable().fnAddData(
										[	
											val.id+'',
											prepareActions(val.id, val.status, val.requestRequisitionId, val.sentToList, val.pdlList, val.submissions, val.requirementId),
											//val.status,(replace status with reportStatus for showing only single status)
											prepareRrfId(val.requirementId),
											val.aliasAllocationName,
											val.designationName,
											val.skill,
											val.noOfResources,
											val.open,
											prepareTableDataElement(0,val.id,val.submissions,val.requirementId),
											prepareTableDataElement(2,val.id,val.shortlisted,val.requirementId),
											prepareTableDataElement(8,val.id, val.closed,val.requirementId),
											prepareTableDataElement(3,val.id, val.notFitForRequirement,val.requirementId),
											val.hold,
											val.lost,
											val.requestedBy,
											prepareAdditionalComments(val.addtionalComments,val.id),
											val.reportStatus
										]
									)												                                             
								});
							}
						},
						error : function(errorResponse) {
							//showError("Records Not Found");
							$('#records_tableId').hide();
							var htmlVarObj = '<div id="NoAllocMessage" class="NoAllocMessage">No Record Exist for selected filters.</div>';
							$('#NoAllocMessage').empty().append(htmlVarObj);
							$("#NoAllocMessage").show();
							$("#requestRequisitionReportTable").hide();
	
						}
			});
		}
		
		var isClick=true; 
		$(".action-dropdown").on('click', function(){
			if(isClick){
				//$(this).css("background", "#d9ebff");
				//$(this).css("cursor", "pointer");
				//$(this).css("transition", "0.5s");
				//$(this).css("color", "#5189c2");
				//for apply the style to sibblings
				//$(this).siblings().css("background", "#d9ebff");
				//$(this).siblings().css("cursor", "pointer");
				//$(this).siblings().css("transition", "0.5s");
				//$(this).siblings().css("color", "#5189c2");
				isClick=false;
			}
			else{
				$(this).css("background", "");
				$(this).css("cursor", "");
				$(this).css("transition", "");
				$(this).css("color", "");
				//for apply the style to sibblings
				$(this).siblings().css("background", "");
				$(this).siblings().css("cursor", "");
				$(this).siblings().css("transition", "");
				$(this).siblings().css("color", "");
				isClick=true;
			}
		});
		$(".odd").addClass("content");
		$(".even").addClass("content");
		/* $(".odd").addClass("dropdown");
		$(".even").addClass("dropdown"); */ 
		$(".action-dropdown").addClass("dropdowns");
		$(".action-dropdown").addClass("dropbtn");
		$(".dropdown-menu").addClass("dropdown-content");
	});
	var maxHeight = 400;
	
	$(".dropdown > li").hover(function() {
	     var $container = $(this),
	         $list = $container.find("ul"),
	         $anchor = $container.find("a"),
	         height = $list.height() * 1.1,       // make sure there is enough room at the bottom
	         multiplier = height / maxHeight;     // needs to move faster if list is taller
	    
	    // need to save height here so it can revert on mouseout            
	    $container.data("origHeight", $container.height());
	    
	    // so it can retain it's rollover color all the while the dropdown is open
	    $anchor.addClass("hover");
	    
	    // make sure dropdown appears directly below parent list item    
	    $list
	        .show()
	        .css({
	            paddingTop: $container.data("origHeight")
	        });
	    
	    // don't do any animation if list shorter than max
	    if (multiplier > 1) {
	        $container
	            .css({
	                height: maxHeight,
	                overflow: "hidden"
	            })
	            .mousemove(function(e) {
	                var offset = $container.offset();
	                var relativeY = ((e.pageY - offset.top) * multiplier) - ($container.data("origHeight") * multiplier);
	                if (relativeY > $container.data("origHeight")) {
	                    $list.css("top", -relativeY + $container.data("origHeight"));
	                };
	            });
	    }
	    
	}, function() {
	
	    var $el = $(this);
	    
	    // put things back to normal
	    $el
	        .height($(this).data("origHeight"))
	        .find("ul")
	        .css({ top: 0 })
	        .hide()
	        .end()
	        .find("a")
	        .removeClass("hover");
	
	});
	
	// Add down arrow only to menu items with submenus
	$(".dropdown > li:has('ul')").each(function() {
	    $(this).find("a:first").append("<img src='images/down-arrow.png' />");
	});
	var oInnerTable;
	var lastTableObject;
	var lastNTr;
	var lastIdx;
		
		$('.close').on('click', function() {
			closeSubTable();
		})
		
		function closeSubTable(){
			 lastTableObject.removeClass( 'details' );
	       	 lastTableObject.fnClose(lastNTr);
		}
		
		function onClickResourceProfile(stausName,status, reqId, rrfName, resourceCount){
			if(resourceCount>0){
				showSubTable(reqId, status, rrfName,stausName);
			}
			if(resourceCount<=0 && stausName=='All Resources'){
				showError("No Resource found !");
			}
	    }
	    
		function getResources(status,skillRequisitionId, responseData){
	    	$.ajax({
	    		type: 'GET',
	    		dataType: 'json',
	            
	           url: '/rms/requestsReports/requestRequisitionSkills/'+skillRequisitionId+'/resources',
	           cache: false,
	           data : "&resourceStatusIds="+status,
	           success: function(response) { 
	    	       	stopProgress();
	    	       	responseData(response);
	        	},
	        	error: function(error){
	        		stopProgress();
	        		showError("Something happend wrong!!");
	        	}
	    	})
	    }
		
		function showSubTable(reqId, status, rrfName,stausName){
			resourceCounter = 0;
			var modal = document.getElementById('myPreviewModal');
			modal.style.display = "block";
		 	var responseData;
		 	
		 	$('#resource-update-btn').hide();
		 	if(stausName ==='All Resources'){
		 		$('#resource-update-btn').show();
		 		//$('#resource-update-btn').prop("disabled", true);
		 	}
		 	
		 	if(status == '0'){
		 		stausName = '<span class="resourceSubmission">'+stausName+" : "+rrfName+'</span>';
		 	}else if(status =='2'){
		 		stausName = '<span class="resourceShortlisted">'+stausName+" : "+rrfName+'</span>';
		 	}else if(status =='8'){
		 		stausName = '<span class="resourceJoined">'+stausName+" : "+rrfName+'</span>';
		 	}else{
		 		stausName = '<span class="resourceRejected">'+stausName+" : "+rrfName+'</span>';
		 	}
		 	
		 	$('.positionStatus').html('');
		 	$('.positionStatusPlaceHolder').html(stausName);
		 	
	    	getResources(status, reqId, function(data){
	    	responseData = data;
	    	if (data.length > 0) {
				$('#modal_data_table').dataTable().fnClearTable();
				var i = 1;
				$.each(data,function(key,val) {
					
					var dateValue=null;
					
					if(val.status=="SELECTED")
						dateValue=val.allocationDate;
					
					if(val.status=="JOINED")
						dateValue=val.allocationDate;
					
					else if(val.status=="REJECTED")
						dateValue=val.resourceRejectedDate;
					
					else if(val.status=="SUBMITTED TO POC")
						dateValue=val.resourcePOCsubmittedDate;
					
					else if(val.status=="SUBMITTED TO AM TEAM")
						dateValue=val.resourceAMsubmittedDate;
					
					else if(val.status=="INTERVIEW LINED UP")
						dateValue=val.interviewDate;
					
					$('#modal_data_table').show();
					$('#modal_data_table').dataTable().fnAddData(
						[
							prepareActionComments(val.resourceId, val.internalResId, val.resourceType,  val.resourceName, val.skillReqName),
							val.resourceType,
							val.resourceName,
							val.skill,
							val.designation,
							/* val.status,
							dateValue, */
							prepareProfileStatus(val.resourceId,val.status,val.joiningDate,val.interviewDate, resourceCounter,val.resourceName, val.skillReqName),
							prepareDate(val.resourceId,val.status,val.joiningDate,val.interviewDate,resourceCounter,val.skillReqId),
							val.location,
							preparenoticePeriod(val.noticePeriod),
							prepareTotalExp(val.totalExperince),
							//val.noticePeriod,
							//val.totalExperince,
							val.contactNum,
							val.emailId, 
							//prepareResume(val.internalResId, val.resourceType),
							//prepareTEF(val.resourceId,  val.resourceType)
						]
					);
					resourceCounter++
				 });
			  }else{
					$('#modal_data_table').show();
					$('#modal_data_table').dataTable().fnClearTable();  
			  }
	     });
		}
		
		function closePopup(){
			var modal = document.getElementById('myPreviewModal');
			modal.style.display = "none";
			 $('#modal_data_table').hide();
			 $('#modal_data_table').dataTable().fnClearTable();
		}
		
		function closeRRFPopup(){
			 $("#internalUserIds option:selected").prop("selected", false);
			 $("#pdlGroupIds option:selected").prop("selected", false);
			 $("#pdlGroupIds").multiselect('refresh'); 
			 $("#internalUserIds").multiselect('refresh'); 
			 $("#commentForwardRRF").val('');
			 document.getElementById("forward-btn").disabled = false;
			var modal = document.getElementById('forwardModal');
			modal.style.display = "none";
		}

		function closeInterviewPanelPopUp() {
			  var modal = document.getElementById('InterviewPanelModal');
			 modal.style.display = "none";
			 $("#round1Select option:selected").prop("selected", false);
			 $("#roundTwoSelect option:selected").prop("selected", false);
			 $("#round1Select").multiselect('refresh'); 
			 $("#roundTwoSelect").multiselect('refresh'); 
			 $("#keyInterviewer1, #keyInterviewer2").val('');
			 $("#round1Select, #roundTwoSelect").closest('.form-group').removeClass('validateMe').removeClass('invalid_input');
		     $('#keyInterviewer1, #keyInterviewer2').removeClass('invalid_input');//key interviewers not mandatory other than ODC
			 requiredFor = '';
			 $('#InterviewPanelModal').modal('hide');
		}
		
		function onForwardRRF(){
			var internalUserIds =document.getElementById("internalUserIds");
			var pdlGroupIds =document.getElementById("pdlGroupIds");
			var send =document.getElementById("sendId");
			var userComment =  document.getElementById('commentForwardRRF').value;
			var len =internalUserIds.selectedOptions.length+pdlGroupIds.selectedOptions.length;
			var skillReqId=document.getElementById('skillRequestId').value;
			if(internalUserIds.value=="" && pdlGroupIds.value==""){
			    showError("Please select atleast one email address.");
				return false;
			}
			 if(len>10){
				showError("Please don't select more than 10 emailIds");
				return false;
			}
			 
			if(userComment.length > 250){
				showError("Your comment exceeds 250 characters!");
				return false;
			}
			 var pdlGroupEmailIds="";
			 $("#pdlGroupIds option:selected").each(function(){
					var id = $(this).val();
					pdlGroupEmailIds =id+","+pdlGroupEmailIds;
			 });
			 
			 var internalUserEmailIds="";
			 $("#internalUserIds option:selected").each(function(){
					var id = $(this).val();
					internalUserEmailIds =id+","+internalUserEmailIds;
			 });
			 
			var data = {
						"userIdList":internalUserEmailIds,
						"pdlList":pdlGroupEmailIds,
						
						
						"skillReqId":skillReqId, 
						"commentForwardRRF" : userComment
				}
			document.getElementById("forward-btn").disabled = true;
			$.ajax({
					type: 'POST',
			        url: '/rms/requestsReports/forwardRRF',
			        data: JSON.stringify(data),
			        cache: false,
			        contentType :'application/json',
			        success: function(response) { 
			    		 $("#internalUserIds option:selected").prop("selected", false);
			    		 $("#pdlGroupIds option:selected").prop("selected", false);
			    		 $("#pdlGroupIds").multiselect('refresh'); 
			    		 $("#internalUserIds").multiselect('refresh'); 
			    		 $("#commentForwardRRF").val('');
			        	 var modal = document.getElementById('forwardModal');
						 modal.style.display = "none";
			        	 showSuccess("RRF has been forwarded successfully !");
			        	 document.getElementById("forward-btn").disabled = false;
			        	 var element = document.getElementById("forwardOrangeId"+skillReqId);
			        	 element.classList.add("orange-batch");
				     	},
				     	error: function(error){
				     		document.getElementById("forward-btn").disabled = false;
				     		showError("Something went wrong!!");
				     	}
			});
		}

	var empIdForRound2 = [];
	var empIdForRound1 = [];
	
	var requiredFor = "";	
	$(document).ready(function() {
		
	    function fnFormatDetails(table_id, html) {
		    var sOut = "<div><div class='close_sub_table'><a href='#"+table_id+"' onclick='closeSubTable();' class='close'></a></div><table id=\"sub_record_table_"+table_id+"\" class=\"dataTbl display tablesorter addNewRow alignLeft\" style='width:60%'>";
		    sOut = sOut+"<thead><tr><th align='center' valign='middle' width='1%'>Type</th><th>Resource Name</th><th>Skill</th><th>Designation</th><th>Status</th><th>Date</th></tr></thead>";
		    sOut += html;
		    sOut += "</table></div>";
		    return sOut;
		}
	    
	    $('#records_tableId tbody').on( 'click', 'tr td.closed-resources', function () {
	    	var dt = $('#records_tableId').dataTable();
	        var tr = $(this).closest('tr');
	        var nTr = $(this).parents('tr')[0];
	        var position = dt.fnGetPosition(nTr);
	        var reqId = dt.fnGetData(position)[0];
	        var status = 8;
	        var stausName = 'Joined Resources';
	        var rrfName = dt.fnGetData(position)[3];
	        var selectedColumnValue =  dt.fnGetData(position)[9];
	         if(selectedColumnValue !='0' && selectedColumnValue !=''){
	        	showSubTable(dt, tr, nTr, reqId, status,rrfName,stausName );
	          }
	    } );
	    
	    $('#records_tableId tbody').on( 'click', 'tr td.shortlisted-resources', function () {
	    	var dt = $('#records_tableId').dataTable();
	        var tr = $(this).closest('tr');
	        var nTr = $(this).parents('tr')[0];
	        var position = dt.fnGetPosition(nTr);
	        var reqId = dt.fnGetData(position)[0];
	        var status = 2;
	        var stausName = 'Shortlisted Resources';
	        var rrfName = dt.fnGetData(position)[3];
	        var selectedColumnValue =  dt.fnGetData(position)[8];
	        if(selectedColumnValue !='0' && selectedColumnValue !=''){
	       		showSubTable(dt, tr, nTr, reqId, status,rrfName,stausName );
	         }
	    } );
	    
	    $('#records_tableId tbody').on( 'click', 'tr td.notfit-resources', function () {
	    	var dt = $('#records_tableId').dataTable();
	        var tr = $(this).closest('tr');
	        var nTr = $(this).parents('tr')[0];
	        var position = dt.fnGetPosition(nTr);
	        var reqId = dt.fnGetData(position)[0];
	        var status = '3,6';
	        var stausName = 'Not Fit Resources';
	        var rrfName = dt.fnGetData(position)[3];
	        var selectedColumnValue =  dt.fnGetData(position)[10];
	        if(selectedColumnValue !='0' && selectedColumnValue !=''){
	       		showSubTable(dt, tr, nTr, reqId, status,rrfName,stausName );
	         }
	    } );
	  
	     
	    $('#records_tableId tbody').on( 'click', 'td.stop_btn_click', function (e) {
	    	e.stopPropagation();
	    	return false;
	    
		});
		
		if ($("#round1Select option:selected").length) {
			$.each($("#round1Select option:selected"),
				function () {
				empIdForRound1.push($(this).val());
			})
		}
		
		$('#round1Select').multiselect({
			includeSelectAllOption: true,
			id: 'round1Select'
		}).multiselectfilter();
		
		
		$('#round1Select').bind(
				'change multiselectcheckall multiselectuncheckall',
					function (ev) {
						var selMulti = $.map($(this).find('option'),
							function (el, i) {
								if (el.selected) {
									empIdForRound1 = $('#round1Select').val();
									$("#keyInterviewer1").val($(el).text());
									return $(el).text();
								}
							});
					if ((ev && ev.type === 'multiselectuncheckall') || !selMulti.length) {
				  		empIdForRound1 = [];
					}
					if (selMulti == "") {
						$('#keyInterviewer1').html('');
			  	 	}
					$("#keyInterviewer1").val(selMulti.join(", "));
			});
		
		
		$('#roundTwoSelect').multiselect({
			includeSelectAllOption: true,
			id: 'roundTwoSelect'
		}).multiselectfilter();

		if ($("#roundTwoSelect option:selected").length) {
			$.each($("#roundTwoSelect option:selected"),
				function () {
				empIdForRound2.push($(this).val());
			})
		}
		
		$('#roundTwoSelect').bind(
			'change multiselectcheckall multiselectuncheckall',
				function (ev) {
					var selMulti = $.map($(this).find('option'),
						function (el, i) {
							if (el.selected) {
								empIdForRound2 = $('#roundTwoSelect').val();
								$("#keyInterviewer2").val($(el).text());
								return $(el).text();
							}
						});
				if ((ev && ev.type === 'multiselectuncheckall') || !selMulti.length) {
					empIdForRound2 = [];
				}
				if (selMulti == "") {
					$('#keyInterviewer2').html('');
		  	 	}
				$("#keyInterviewer2").val(selMulti.join(", "));
		});
		
	} );
	
	function validateODC() {
		if (requiredFor !== 'ODC') {
			return 0;
		}
		var al = 0;
		var items = $('#InterviewPanelModal').find(".ui-combobox-input,input[type='text'],input[type='number'],textarea,select");
		var al = 0;
		for (var i = 0; i < items.length; i++) {
			var b = items[i].value;
			if (b < 0) {
				$(items[i]).closest('.form-group').addClass('invalid_input');
				al = al + 1;

			} else if (b == "Select" || b == "" ||	b.trim() == '') {
				$(items[i]).closest('.form-group').addClass('invalid_input');
				$(items[i]).closest('.form-control').addClass('invalid_input');
				al = al + 1;
			} else {
				$(items[i]).closest('.form-group').removeClass('invalid_input');
				$(items[i]).closest('.form-control').removeClass('invalid_input');
			}
		}
		return al;
	}
	
	function submitInterviewPanels() {
		var isNotValidated = validateODC();
		if (isNotValidated) {
			return;
		}

		var skillRequestId = document.getElementById('skillRequestId').value;
		var stringForRound1 = "" ,stringForRound2="";
		
		if (empIdForRound1.length) {
			 stringForRound1 = empIdForRound1.join(",");
		} 
		
		if (empIdForRound2.length) {
			stringForRound2 = empIdForRound2.join(",");
		}

		var data = {"skillRequestId":skillRequestId,"keyInterviewPanelOne":stringForRound1,"keyInterviewPanelTwo":stringForRound2}
		startProgress();
		
		$.ajax({
			type: 'POST',
	        url: '/rms/requestsReports/saveInterviewPanel',
	        dataType: 'text json',
	        data: JSON.stringify(data),
	        cache: false,
	    	async : false,
	    	contentType: "application/json; charset=utf-8",
            success: function(response) { 
            	stopProgress();
            	$('#InterviewPanelModal').modal('hide');
            	showSuccess("Interview Panel Added Successfully");
            },
	 		error : function(error){
	 			stopProgress();
	 			$('#InterviewPanelModal').modal('hide');
	 			if(error.errCode == 417 || error.errCode == 2)
		 			showError(error.message);
	 			else 
	 				showError("Something went wrong !");
	 		}
		});
	}
	
	function updateResourcePopup() {
		$('#updateResPopUp').modal('show');
		$(".close-btn1").click(function(){
				$('#updateResPopUp').modal('hide');
			 
		 });
	}
	$('#dataTbl').on( 'page.dt', function () {
	    var info = table.page.info();
    });
	
	function showActionDropdown(ele) {
		$('#requestRequisitionReportTable .dropdown-content').css('display', 'none');
		$(ele).children().next('.dropdown-content').css('display', 'block');
	}

	$(document).on('click', function(e) {
		if (!event) { var event = window.event; }
		event.stopPropagation();
		var src = event.srcElement ? event.srcElement : event.target;
		if(($(src).hasClass('dropdown-content')==false) && $(src).hasClass('dropDownHover')==false && $(src).hasClass('fa-ellipsis-v')==false)
		{ 
			$('#requestRequisitionReportTable .dropdown-content').hide();
		}
		});
	</script>
<body>
	<div class="content-wrapper">
		<div class="botMargin">
		<h1>Resource Request</h1>
		</div>
		
		<div class="tab_seaction">
			<!-- <ul class='tabs'>
					<li><a href='#tab1' id="tab1id">List</a></li>
	
				</ul> -->
			<div id='tab1' class="tab_div">
				<div class="form my_form">
					<form name="defaultFormName" method="post" id="defaultForm">
						<div class="tblBorderDiv">
							<table id="mail" cellpadding="5" cellspacing="5"
								class="display dataTable filter-table-ps"
								style="border-collapse: collapse; width: 100%; margin-top: 0px;">
								<tr>
									<td><input type="hidden" id="formHiddenId" name="id"></input></td>

									<td align="right">
										<!-- <div class="sortby-filtercontain">
											<label class="sortby-filter"> Sort By</label> <select
												id="example-sort">
												<option value="3">Status</option>
												<option value="4">RRF ID</option>
												<option value="5">Allocation Type</option>
												<option value="6">Designation</option>
												<option value="15">Requested By</option>
											</select>
										</div> -->
									<!-- 	<a href="/rms/requests" class="btn next-button">+ Add RRF</a> -->
										<span class="glyphicon glyphicon-filter filter-icon"
										data-toggle="modal" data-target="#exampleModal"></span>
										<div class="modal fade" id="exampleModal" tabindex="-1"
											role="dialog" aria-labelledby="exampleModalLabel"
											aria-hidden="true">
											<div class="modal-dialog" role="document">
												<div class="modal-content" style="width: 654px;">
													<div class="modal-header">
														<h5 class="modal-title" id="exampleModalLabel">Filter</h5>
														<button type="button" class="close" data-dismiss="modal"
															aria-label="Close">
															<span aria-hidden="true">&times;</span>
														</button>
													</div>
													<div class="modal-body">
														<div class="filter-contain-row">
															<div class="f_inline">
																<div class="positionRel combo-multiselet col-sm-6 client-filter">
																	<select id="client" class="required"
																		multiple="multiple">
																		<c:forEach var="customer" items="${customerList}">
																			<option value="${customer.id}">${customer.customerName}</option>
																		</c:forEach>
																	</select>
																</div>

																<div class="positionRel col-sm-6 group-filter combo-multiselet">
																	<select id="group" class="required" multiple="multiple">
																		<option value='Select'>Select</option>
																	</select>
																</div>
															</div>
														</div>
														<div class="modal-footer">
															<button type="button" class="btn btn-secondary"
																data-dismiss="modal">Close</button>
															<button type="button" class="btn btn-primary"
																type="submit" data-dismiss="modal" id="submit"
																value="Filter">Apply</button>
														</div>
													</div>
												</div>
											</div>
											<!-- <input type="button" class="request-form-style my_btn"
													style="font-size: medium;" id="submit" value="Apply" /> -->
											&nbsp;&nbsp;
										</div>
									</td>
								</tr>
							</table>
						</div>
					</form>
				</div>
				<div class="tbl"></div>
				<div class="clear"></div>
				<div id="requestRequisitionReportTable">
					<table
						class="dataTbl display tablesorter addNewRow alignCenter my_table positiondashboard-table"
						id="records_tableId"
						style="overflow: auto; display: inline-block;">
						<thead>
							<tr class="content">

								<th class="width110" align="center" valign="middle">requirementId</th>
								<!-- <th class="width80" align="center" valign="middle"><input type="checkbox"
								value=""></th> -->
								
								<th class="width71 no-sort" style="width:71px !important; background: #fff !important;" align="center" valign="middle">Actions</th>
								<th class="width110 no-sort" style="width:185px !important;background: #fff !important;"align="center" valign="middle">RRF ID</th>
								<th class="width110" align="center" valign="middle">Allocation
									Type</th>
								<th class="width110" align="center" valign="middle">Designation</th>
								<th class="width110" align="center" valign="middle">Required
									Skill</th>
								<th class="width80" align="center" valign="middle">
									Positions</th>
								<th class="width80" align="center" valign="middle">Open</th>
								<th class="width125" align="center" valign="middle">Submissions</th>
								<th class="width125" align="center" valign="middle">ShortListed</th>
								<th class="width80" align="center" valign="middle">Closed</th>
								<th class="width125" align="center" valign="middle" width="1%">Not
									Fit</th>
								<th class="width80" align="center" valign="middle" width="1%">Hold</th>
								<th class="width80" align="center" valign="middle">Lost</th>
								<th class="width110" align="left" valign="middle" style="text-align:left !important;">Requested
									By</th>
								<th class="width110" align="center" valign="middle">Additional
									Comments</th>
								<th class="width125" align="center" valign="middle">Status</th>
								
							</tr>
						</thead>
						<tbody>

							<c:forEach var="reportData" items="${reportDataList}">
								<tr id="RRFId${reportData.id}"
									class="rowClick content even odd ">
									<td class="align-center ">${reportData.id}</td>
									<!-- <td><input type="checkbox" value=""></td> -->
									
 
									<td class="stop_click action-dropdown dropbtn dropdowns">
										<span title="Click here" class="dropdown dropleft dropDownHover hover-area" onclick="showActionDropdown(this)">
 

											<i class="fa fa-ellipsis-v" style="font-size: 15px;"
												class="btn dropdown-toggle"></i>

											<div class="dropdown-menu dropdown-content">
												<c:if
													test='<%=UserUtil.getCurrentResource().getEmployeeId() == 230
							|| UserUtil.getCurrentResource().getEmployeeId() == 4648
							|| UserUtil.getCurrentResource().getEmployeeId() == 5456
							|| UserUtil.getCurrentResource().getEmployeeId() == 5929
							|| UserUtil.getCurrentResource().getEmployeeId() == 5493
							|| UserUtil.getCurrentResource().getEmployeeId() == 211
							|| UserUtil.getCurrentResource().getEmployeeId() == 353
							|| UserUtil.getCurrentResource().getEmployeeId() == 4623
							|| UserUtil.getCurrentResource().getUserRole().equalsIgnoreCase("ROLE_ADMIN")%>'>

													<div class="borderHover">
														<a class="dropdown-item"
															onclick="addRemoveResourceInRequestRequisition(${reportData.id})"
															data-target="#addResource" id="${reportData.id}"
															href="javascript:void(0)"> <i
															class="fa fa-plus-square-o my_submit_btn"></i>Submit
															Resources
														</a></br> </a>
													</div>
												</c:if>
												<div class="borderHover">
													<a class="dropdown-item" data-toggle="modal"
														data-target="#myModalShowAll" title="Show all resources"
														onclick="onClickResourceProfile('All Resources','0',${reportData.id},'${reportData.requirementId}','${reportData.submissions}')">

														<i class="fa fa-users"></i>View all resources
													</a></br>
												</div>
												<div class="borderHover">
													<a class="dropdown-item" data-toggle="modal"
														data-target="#view-resources-PD"
														onclick="skillRequestReportPDF(this,${reportData.id})"
														title="Download RRF"> <i class="fa fa-download"></i>Download
													</a></br>
												</div>
												<div class="borderHover">
													<a class="dropdown-item" href="#"
														onclick="editrequest(this,${reportData.id})"
														title="Edit RRF"> <i class="fa fa-edit"
														aria-hidden="true"> </i>Edit RRF
													</a></br>
												</div>
												<c:choose>
													<c:when
														test="${not empty reportData.sentToList || not empty reportData.pdlList}">
														<div class="borderHover">
															<a class="dropdown-item" data-toggle="modal"
																data-target="#forwrd-rrf-PD"
																onClick="forwardRRF(${reportData.id})" title="Forward">
																<i class="fa fa-share" aria-hidden="true"><span
																	class="orange-batch"
																	id="forwardOrangeId${reportData.id}"></span></i>Forward
															</a></br>
														</div>
													</c:when>
													<c:otherwise>
														<div class="borderHover">
															<a class="dropdown-item" data-toggle="modal"
																data-target="#forwrd-rrf-PD"
																onClick="forwardRRF(${reportData.id})" title="Forward">
																<i class="fa fa-share" aria-hidden="true"><span
																	id="forwardOrangeId${reportData.id}"></span></i>Forward
															</a></br>
														</div>
													</c:otherwise>
												</c:choose>
												<div class="borderHover">
													<a class="dropdown-item" data-toggle="modal"
														data-target="#myCommentsModal"
														onclick="getLatestCommentOnResource(${reportData.id})"
														title="Comments"> <i class="fa fa-comments"
														aria-hidden="true"> </i>Comment
													</a></br>
												</div>
												<div class="borderHover">
													<a class="dropdown-item" href="#"
														onclick="BGHfileDownload(${reportData.requestRequisitionId})"
														title="Download BGH Approval"> <i
														class="fa fa-download" aria-hidden="true"> </i>BGH
														approval
													</a></br>
												</div>
												<div class="borderHover">
													<a class="dropdown-item" href="#"
														onclick="BUHfileDownload(${reportData.requestRequisitionId})"
														title="Download BUH Approval"> <i
														class="fa fa-download" aria-hidden="true"> </i>BUH
														approval
													</a></br>
												</div>
												<sec:authorize access="hasRole('ROLE_ADMIN')">
													<c:if test="${reportList.status !='Closed'}">
														<div class="borderHover">
															<a
																onclick="skillRequestReportDelete(${reportData.requestRequisitionId},${reportData.id})"
																title="Delete RRF" class="dropdown-item" href="#"> <i
																class="fa fa-trash" aria-hidden="true"> </i>Delete
															</a></br>
														</div>
													</c:if>
												</sec:authorize>
												<div class="borderHover">
													<a class="dropdown-item" data-toggle="modal"
														data-target="#InterviewPanelModal" data-backdrop="static"
														onclick="getInterviewPanel(${reportData.id})"
														title="Add Interview Panel"> <i class="fa fa-users"
														aria-hidden="true"> </i>Add Interviewer
													</a></br>
												</div>
											</div>
										</span>
									</td>
									
									
									<td><div class="textwraps"
											title="${reportData.requirementId}">${reportData.requirementId}</div></td>
									<td>${reportData.aliasAllocationName}</td>
									<td>${reportData.designationName}</td>
									 <td>${reportData.skill}</td>


									<td class="align-center stop_click"><span
										class="open_pos_popup align-center">
											${reportData.noOfResources}</span></td>
									<td id="openId${reportData.id}" class="stop_click"><span
										class="open_pos_popup align-center">${reportData.open}
									</span></td>

									<td class="align-center"><a href="#myPreviewModal"
										onclick="onClickResourceProfile('Submitted Resources','0',${reportData.id},'${reportData.requirementId}','${reportData.submissions}')">
											<span class="open_pos_popup align-center color-lightblue">${reportData.submissions}</span>
									</a></td>
									<td id="shortlistedId${reportData.id}" class="align-center">
										<a href="#myPreviewModal"
										onclick="onClickResourceProfile('Shortlisted Resources','2',${reportData.id},'${reportData.requirementId}','${reportData.shortlisted}')">
											<span class="open_pos_popup align-center color-orange">${reportData.shortlisted}</span>
									</a>
									</td>

									<td id="closedId${reportData.id}" class="align-center"><a
										href="#myPreviewModal"
										onclick="onClickResourceProfile('Joined Resources','8',${reportData.id},'${reportData.requirementId}','${reportData.closed}')">
											<span class="open_pos_popup align-center color-brown">${reportData.closed}</span>
									</a></td>
									<td id="rejectId${reportData.id}" class="align-center"><a
										href="#myPreviewModal"
										onclick="onClickResourceProfile('Not Fit Resources','3,6',${reportData.id},'${reportData.requirementId}','${reportData.notFitForRequirement}')">
											<span class="open_pos_popup align-center color-grey">${reportData.notFitForRequirement}</span>
									</a></td>
									<td class="align-center"><span
										class="open_pos_popup align-center">${reportData.hold}</span></td>
									<td><span class="open_pos_popup align-center">${reportData.lost}</span></td>
									<td style="text-align:left !important;">${reportData.requestedBy}</td>

									<td>
										<div class="textwrap" data-toggle="modal"
											data-target="#myModal${reportData.id}">${reportData.addtionalComments}</div>
										<!-- Modal -->
										<div id="myModal${reportData.id}" class="modal fade"
											role="dialog">
											<div class="modal-dialog modal-sm">

												<!-- Modal content-->
												<div class="modal-content">
													<a href="#" onclick="closePopup()"><span class="close"
														data-dismiss="modal" id="close-icon"
														style="right: 20px; top: 18px;"></span></a>
													<div class="modal-header headerStyle">
														<h4 class="modal-title">Additional Comments</h4>
													</div>
													<div class="modal-body model-padding">
														<p class="p-whitespace">${reportData.addtionalComments}</p>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default"
															data-dismiss="modal">Close</button>
													</div>
												</div>

											</div>
										</div>
									</td>
									<td id="statusId${reportData.id}">
										${reportData.reportStatus}</td>
									

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div>
					<table id="NoAllocMessage"></table>
				</div>
			</div>
			<div id="myPreviewModal" class="modal">
				<div class="modal-content" style="width: 92%; margin-top: 40px;">
					<a href="#" onclick="closePopup()"><span class="close"
						id="close-icon" style="right: 20px; top: 18px;"></span></a>
					<h1 class="pd-20">
						<span class="positionStatus positionStatusPlaceHolder"></span>
					</h1>
					<div class="dataTables_wrapper pd-20 bg-none" role="grid">
						<table
							class="dataTbl display tablesorter addNewRow alignCenter my_table tablefixed"
							id="modal_data_table"
							style="overflow: auto; display: inline-block; height: 200px; border: 1px solid #ccc;">
							<thead>
								<tr class="content">
									<!-- <th align="center" valign="middle">RRF ID</th> -->
									<th align="center" valign="middle" class="no-sort" style="background:#fff !important;">Actions</th>
									<th align="center" valign="middle">Type</th>
									<th align="center" valign="middle" width="1%">Resource
										Name</th>
									<th align="center" valign="middle">Skill</th>
									<th align="center" valign="middle">Designation</th>
									<!-- <th align="center" valign="middle">Status</th> -->
									<!-- <th align="center" valign="middle">Date</th>  -->
									<th align="center" valign="middle">Profile Status</th>
									<!-- <th align="center" valign="middle">Comments</th> -->
									<th align="center" valign="middle">Joining Date /
										Interview Date</th>
									<th align="center" valign="middle">Location</th>
									<th align="center" valign="middle">Notice Period</th>
									<th align="center" valign="middle">Exp(Total IT Exp)</th>
									<th align="center" valign="middle">Contact</th>
									<th align="center" valign="middle">Email</th>
									<!-- <th align="center" valign="middle">Resume</th>
										<th align="center" valign="middle">TEF</th>  -->

								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<div class="close-right">
						<button type="button" id="resource-update-btn"
							class="btn btn-secondary next-button" style="margin: 20px"
							onclick="updateResourcePopup()">Update Resource Status</button>
						<button type="button" id="close-btn"
							class="btn btn-secondary next-button" style="margin: 20px"
							onclick="closePopup()">Close</button>
					</div>
					<!-- <div class="close-right">
							<button type="button" id="close-btn"
								class="btn btn-secondary next-button" style="margin: 20px" onclick="closePopup()">Close</button>
						</div> -->
				</div>
			</div>

			<div id="myCommentsModal" class="modal">
				<div class="modal-content"
					style="width: 80%; margin-top: 40px; display: table;">
					<a href="#" data-dismiss="modal"><span class="close"
						id="close-icon" style="right: 20px; top: 18px;"></span></a>
					<h1 class="pd-20">
						<span class="positionStatus positionStatusPlaceHolder"><span
							class="resourceSubmissionss">Comments</span></span>
					</h1>
					<div class="col-md-12 my-comments-modal"
						style="max-height: 400px; overflow: auto;">
						<section class="sidebar" style="height: auto;">
							<!-- sidebar menu: : style can be found in sidebar.less -->
							<div id="resourceCommentId">
								<ul class="sidebar-menu" id="dynamic-list">
									<!-- <li class="treeview" >
										<a href="javascript:void(0);"> <span id="CandidateName">Name of candidate</span>
											<i 	class="fa fa-angle-left pull-right"></i>
										</a>
										  <ul class="treeview-menu" id="dynamic-list">
												<a href="javascript:void(0);"></a>
										</ul>  
									</li> -->
								</ul>

							</div>
						</section>
					</div>
					<div class="close-right">
						<button type="button" id="close-btn"
							class="btn btn-secondary next-button" style="margin: 20px"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>

			<div id="myPreviewModalForComment" class="modal">
				<div class="modal-content" style="width: 80%; margin-top: 40px;">
					<a href="#" onclick="closePopup()"><span class="close"
						id="close-icon" style="right: 20px; top: 18px;"></span></a>
					<h1 class="pd-20">
						<span class="positionStatus positionStatusPlaceHolder"></span>
					</h1>
					<div class="dataTables_wrapper pd-20 bg-none" role="grid">
						<table
							class="dataTbl display tablesorter addNewRow alignCenter my_table"
							id="modal_data_table"
							style="overflow: auto; display: inline-block; height: 200px; border: 1px solid #ccc;">
							<thead>
								<tr class="content">

									<th align="center" valign="middle" width="1%">Resource
										Name</th>
									<th align="center" valign="middle">Skill</th>
									<th align="center" valign="middle">Designation</th>
									<th align="center" valign="middle">Status</th>
									<th align="center" valign="middle">Date</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<div class="close-right">
						<button type="button" id="close-btn"
							class="btn btn-secondary next-button" style="margin: 20px"
							onclick="closePopup()">Close</button>
					</div>
				</div>
			</div>

			<!-- start: Forward Popup  -->

			<div id="forwardModal" class="modal">
				<div class="modal-content" style="width: 530px; margin-top: 40px;">
					<a href="#" data-dismiss="modal" onclick="closeRRFPopup()"><span
						class="close" id="close-icon" style="right: 20px; top: 18px;"></span></a>
					<h1 class="pd-20">
						<span id="forward_placeholder">Forward RRF</span>
					</h1>
					<div class="dataTables_wrapper pd-20 bg-none" role="grid">
						<form id="forwardRRFForm" action="#">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>Select Recipient</th>
										<th>Select PDL Group</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="forward-rrf"><select id="internalUserIds"
											name="internalUserIds" class="required" multiple="multiple">
												<c:forEach var="activeUsers" items="${activeUserList}">
													<option value="${activeUsers.emailId}">${activeUsers.firstName}
														${activeUsers.lastName}</option>
												</c:forEach>
										</select></td>
										<td class="forward-rrf"><select id="pdlGroupIds"
											name="pdlGroupIds" class="required" multiple="multiple">
												<c:forEach var="pdlGrp" items="${pdlGroups}">
													<option value="${pdlGrp.pdlEmailId}">${pdlGrp.pdlEmailId}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th>RRF Forward Comment (if Any) : 
										</th>
									</tr>
									<tr>
									
										<td colspan="2">
										<input type="text" id="commentForwardRRF"  name="commentForwardRRF" >
										<input name="skillRequestId"
											id="skillRequestId" type="hidden" /></td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
					<div class="close-right">
						<button type="button" id="close-btn"
							class="btn btn-secondary next-button" style=""
							data-dismiss="modal" onclick="closeRRFPopup()">Close</button>
						<button type="button" id="forward-btn" class="btn next-button"
							style="margin: 10px 28px 10px 10px;" data-dismiss="modal"
							onclick="onForwardRRF()">Submit</button>
					</div>
				</div>
			</div>

			<div class="modal fade" id="resourceCommentModal" role="dialog">
				<div class="modal-dialog" style="margin-top: 0;">

					<!-- Modal content-->
					<div class="modal-content commentmodal">
						<div class="modal-header"
							style="background: #fff !important; border-bottom: 1px solid #ccc;">
							<button type="button" onclick="onClickclsComment()"
								id="clsComment" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title" style="color: #333; font-weight: bold;"
								id="reqNameId"></h4>
						</div>
						<div class="modal-body commentBody" id="getResultDiv"></div>
						<div class="commentbox"
							style="z-index: 1; position: relative; background: #f5faff; border: 0;">

							<label for="usr" style="display: block;">Comment:</label>
							<textarea class="form-control" id="usr" cols="20" wrap="hard"
								style="display: inline-block; width: 82%;"></textarea>
							<input id="resCMId" type="hidden" value="" />
							<button onclick="strCSubmit()" type="button" id="strCSubmit"
								class="btn my_btn"
								style="position: absolute; margin-top: 14px; margin-left: 12px; padding: 4px 3px;">Submit</button>
						</div>
					</div>

				</div>
			</div>

						
			<div id="InterviewPanelModal" class="modal">
				<div class="modal-content" style="width: 750px;padding:0 30px; margin-top: 40px;">
					<div class="row">
					<h1 style="padding: 20px 0 20px 10px;">Add Interview Panels
						<span class="text-inside-brackets title-sub-txt">(preferrably E3 and above)</span>
						<a href="#" onclick="closeInterviewPanelPopUp()"><span
						class="close" id="close-icon" style="right: 0px;top: 6px;z-index: 999;float: right;width: 30px;height: 16px;background: none;"></span></a>
					</h1> 
							<div class="form-row">
								<div class="form-group clearfix">
									 
									<div class="col-md-4">
										<label for="round1Select"><span data-toggle="tooltip" title="" data-placement="right" data-original-title="Select Interviwer(s) for round 1, mandatory for ODC">Key
												Interviewer(s) Round 1</span></label>
										<div class="positionRel combo-multiselet">
											<select class="form-control required" id="round1Select" name="round1Select" multiple="multiple" placeholder="Press Ctrl and click to select mulitple">
												<c:forEach var="activeUsers" items="${activeUserList}"> 
													<option value="${activeUsers.employeeId}">${activeUsers.firstName} ${activeUsers.lastName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-8">
										<label for="keyInterviewer1"><span>&nbsp;</span></label>
										<textarea  class="col-md-12 disabled_val form-control" id="keyInterviewer1" disabled></textarea>
									</div>
								</div>
								<div class="form-group clearfix">
									<div class="col-md-4">
										<label for="roundTwoSelect"><span data-toggle="tooltip" title="" data-placement="right" data-original-title="Select Interviwer(s) for round 2, mandatory for ODC">Key
												Interviewer(s) Round 2</span></label>
										<div class="positionRel combo-multiselet">
											<select class="form-control required" id="roundTwoSelect" name="round2Select" multiple="multiple" placeholder="Press Ctrl and click to select mulitple">
												<c:forEach var="activeUsers" items="${activeUserList}"> 
													<option value="${activeUsers.employeeId}">${activeUsers.firstName} ${activeUsers.lastName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-8">
										<label for="keyInterviewer2"><span>&nbsp;</span></label> 
										<textarea  class="col-md-12 disabled_val form-control" id="keyInterviewer2" disabled></textarea>
									</div>
								</div>
								
								<div>
								  <input name="skillRequestId" id="skillRequestId" type="hidden" value=""/> </div>
							</div>
						</div>
						
					<div class="close-right" style="margin:10px;">
						<button type="button" id="close-btn" class="btn btn-secondary next-button" style="background: #ccc;color: #000 !important;border: none;padding: 5px 26px;"
							onclick="closeInterviewPanelPopUp()">Close</button>
						<button type="submit" id="submit-btn" class="btn next-button" style="padding: 5px 26px;border: none;" onclick="submitInterviewPanels()">Submit</button>
					</div>
				</div>
			</div>
			
		</div>
	</div>
	<!-- start: Delete popup -->

	<div id="updateResPopUp" class="modal">
		<div class="modal-content"
			style="width: 30%; margin-top: 40px; display: table;">
			<a href="javascript:void(0)" class="close-btn1"> <span
				class="close fa fa-close" id="close-icon"
				style="right: 20px; top: 22px; position: relative; font-size: 20px;"></span>
			</a>


			<h1 style="padding: 20px;">
				<span class="positionStatus positionStatusPlaceHolder"> <span
					class="resourceSubmissionss">Delete Resource</span>
				</span>
			</h1>

			<div class="col-md-12"
				style="background: #f5faff; padding: 20px 22px 15px;">

				<section class="sidebar" style="height: auto;">Do you
					Really want to change Resource Status ?</section>


			</div>
			<div class="close-right"
				style="display: inline-block; width: 100%; text-align: right;">

				<button type="button" id="deleteFinalBtnNew"
					onclick="updateResourceStatus()"
					class="btn btn-secondary next-button ">OK</button>
				<button type="button" id="close-btn"
					class="btn btn-secondary next-button close-btn1"
					style="margin: 10px 24px 10px 10px;">Close</button>

			</div>
		</div>

	</div>
</body>
</html>