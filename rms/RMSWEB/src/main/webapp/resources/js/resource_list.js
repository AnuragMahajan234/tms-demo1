var resourceHistoryData;

var resourcePrimarySkill;
var rerourceSecondrySkill;
var vLoan = null;
var vTransfer = null;
var vContractor = null;
var resourceTable;
var eventTarget = null;
var comboBoxBlur;
var select2Arr;

$(document).on('click', '.closemeAlert', function() {
    $('.alert-danger').empty();
    $('.alertWrapper').fadeOut("slow");
});

$(document).ready(function() {
	
	$('#preferred-label, #preferredLocation').hide();
    $('#Activerecord').on('change', function() {
    	alert(this.value);
        if (this.value == 'All') {
            var oSettings = resourceTable.fnSettings();
            // resourceTable.fnDestroy();
            oSettings.sAjaxSource = contextPath + "list/all";
            resourceTable.fnClearTable();
            resourceTable.fnDraw();
            // resourceTable = $('#resourceTableId').dataTable(initParams);

        } else {
            var oSettings = resourceTable.fnSettings();
            // resourceTable.fnDestroy();
            oSettings.sAjaxSource = contextPath + "list/active";
            resourceTable.fnClearTable();
            resourceTable.fnDraw();
        }

        //alert(this.value );
    });


    $("#currentReportingManagerOne").select2({
        //OpenOnEnter : false,
        ajax: {
            //url: "/rms/resources/activeUserList",
            url: contextPath + 'activeUserList',
            dataType: 'json',
            data: function(params) {
                return {
                    userInput: params.term || '',
                };
            },
            processResults: function(data, params) {
                return {
                    results: formatData1(JSON.parse(data.activeUserList)),
                };
            },
        },
        minimumInputLength: 3,
        allowClear: true,
        createTag: function(params) {
            return undefined;
        },
        placeholder: 'Search Recipient(s)'
    });
    $("#currentReportingManagerTwo").select2({
        //OpenOnEnter : false,
        ajax: {
            //url: "/rms/resources/activeUserList",
            url: contextPath + 'activeUserList',
            dataType: 'json',
            data: function(params) {
                return {
                    userInput: params.term || '',
                };
            },
            processResults: function(data, params) {
                return {
                    results: formatData2(JSON.parse(data.activeUserList)),
                };
            },
        },
        minimumInputLength: 3,
        allowClear: true,
        createTag: function(params) {
            return undefined;
        },
        placeholder: 'Search Recipient(s)'
    });

    comboBoxBlur = $("select.required").parent("td").find("span.ui-combobox input");
    select2Arr = $("select.required").parent("td").find("span.select2-selection");


    $('#orgHierarchy, #projects,  #resource, #resourceOrgHierarchy, #Ownership').multiselect({
        includeSelectAllOption: true,
    }).multiselectfilter();

    $("#project").change(function() {
        $('.noWidth').each(function() {
            this.checked = true;
        });
    });

    $('#project').multiselect({
        includeSelectAllOption: true
    }).multiselectfilter();

    $("#userRole").change(function() {
        var userRole = $("#userRole option:selected").text();
        if (null != userRole && (userRole == "ROLE_BG_ADMIN" || userRole == "ROLE_HR")) {
            $('#showOrgStructure').show();
        } else {
            $('#showOrgStructure').hide()
            document.getElementById("bgAdmin").value = null;
        }
    });

    function startProgress() {
        $.blockUI({
            message: '</br></br><div class="circle"></div><div class="circle1"></div><center>Just a moment</center>'
        });
    }

    function stopProgress() {
        $.unblockUI();
    }
    stopProgress();
    /*End custom Loader*/
    $("#dialog").dialog({
        autoOpen: false
    });
    $("#dialog").dialog("option", "modal", true);
    $("#dialog").dialog("option", "title", "RMS");
    $("#dialog").dialog("option", "buttons", [{
        text: "Ok",
        click: function() {
            $(this).dialog("close");
        }
    }]);
    $("#dialog").dialog({
        closeText: "hide"
    });
    $("#dialog").dialog({
        dialogClass: "alert"
    });
    $("#dialog").dialog("option", "hide", {
        effect: "explode",
        duration: 1000
    });
    $("#dialog").dialog("option", "resizable", false);
    $("#dialog").dialog({
        show: {
            effect: "explode",
            duration: 800
        }
    });
    $("#dialog").dialog({
        minHeight: 400,
        width: 360,
        height: 500
    });

    $("#primaryskilldialog").dialog({
        autoOpen: false
    });
    $("#primaryskilldialog").dialog("option", "modal", true);
    $("#primaryskilldialog").dialog("option", "title", "RMS");
    $("#primaryskilldialog").dialog("option", "buttons", [{
        text: "Close",
        click: function() {
            $(this).dialog("close");
        }
    }]);
    $("#primaryskilldialog").dialog({
        closeText: "hide"
    });
    $("#primaryskilldialog").dialog({
        dialogClass: "alert"
    });
    $("#primaryskilldialog").dialog("option", "hide", {
        effect: "explode",
        duration: 1000
    });
    $("#primaryskilldialog").dialog("option", "resizable", false);
    $("#primaryskilldialog").dialog({
        show: {
            effect: "explode",
            duration: 800
        }
    });
    $("#primaryskilldialog").dialog({
        minHeight: 400,
        width: 360,
        height: 500
    });

    $("#seconddaryskilldialog").dialog({
        autoOpen: false
    });
    $("#seconddaryskilldialog").dialog("option", "modal", true);
    $("#seconddaryskilldialog").dialog("option", "title", "RMS");
    $("#seconddaryskilldialog").dialog("option", "buttons", [{
        text: "Close",
        click: function() {
            $(this).dialog("close");
        }
    }]);
    $("#seconddaryskilldialog").dialog({
        closeText: "hide"
    });
    $("#seconddaryskilldialog").dialog({
        dialogClass: "alert"
    });
    $("#seconddaryskilldialog").dialog("option", "hide", {
        effect: "explode",
        duration: 1000
    });
    $("#seconddaryskilldialog").dialog("option", "resizable", false);
    $("#seconddaryskilldialog").dialog({
        show: {
            effect: "explode",
            duration: 800
        }
    });
    $("#seconddaryskilldialog").dialog({
        minHeight: 400,
        width: 360,
        height: 500
    });
    //Uncomment below below comment if you want to handle any activity at the time of opening of dialouge **Do delete below part
    /* $("#dialog").dialog({
    	open: function( ) {
    	}
    	}); */

    $("#jstree2").dynatree({
        checkbox: true,
        selectMode: 3,
        onSelect: function(select, node) {
            // Get a list of all selected nodes, and convert to a key array:
            var selKeys = $.map(node.tree.getSelectedNodes(), function(node) {
                return node.data.key;
            });

            //Below line is setting the selected bg/bu id's to the hidden field
            document.getElementById('bgAdmin').value = selKeys.join(", ");
            /*Please not do not delete below code, this will help in debugging/future enhancements*/
            /* // Get a list of all selected TOP nodes
            var selRootNodes = node.tree.getSelectedNodes(true);
            // ... and convert to a key array:
            var selRootKeys = $.map(selRootNodes, function(node){
              return node.data.key;
            });
            $("#echoSelection3").text(selData.join(", "));
            $("#echoSelectionRootKeys3").text(selRootKeys.join(", "));
            $("#echoSelectionRoots3").text(selRootNodes.join(", ")); */
        },
        onDblClick: function(node, event) {
            node.toggleSelect();
        },
        onKeydown: function(node, event) {
            if (event.which == 32) {
                node.toggleSelect();
                return false;
            }
        },
        // The following options are only required, if we have more than one tree on one page:
        //   	        initId: "treeData",
        cookieId: "dynatree-1",
        idPrefix: "dynatree-1"
    });

    $("#skillResourcePrimaries").dynatree({
        checkbox: true,
        selectMode: 3,
        onSelect: function(select, node) {
            // Get a list of all selected nodes, and convert to a key array:
            var selKeys = $.map(node.tree.getSelectedNodes(), function(node) {
                return node.data.key;
            });
            document.getElementById('resourcePrimaryskills').value = selKeys.join(",");

        },
        onClick: function(node, event) {

        },
        onDblClick: function(node, event) {
            // node.toggleSelect();

        },
        onKeydown: function(node, event) {
            if (event.which == 32) {
                node.toggleSelect();
                return false;
            }
        },
        // The following options are only required, if we have more than one tree on one page:
        //                 initId: "treeData",
        cookieId: "dynatree-1",
        idPrefix: "dynatree-1"
    });

    $("#skillResourceSecondaries").dynatree({
        checkbox: true,
        selectMode: 3,
        onSelect: function(select, node) {
            // Get a list of all selected nodes, and convert to a key array:
            var selKeys = $.map(node.tree.getSelectedNodes(), function(node) {
                return node.data.key;
            });
            document.getElementById('resourceSecondaryskills').value = selKeys.join(",");

        },
        onClick: function(node, event) {

        },
        onDblClick: function(node, event) {
            // node.toggleSelect();

        },
        onKeydown: function(node, event) {
            if (event.which == 32) {
                node.toggleSelect();
                return false;
            }
        },
        // The following options are only required, if we have more than one tree on one page:
        //                 initId: "treeData",
        cookieId: "dynatree-1",
        idPrefix: "dynatree-1"
    });

    $("#showOrgStructure").click(
        function() {
            $("#dialog").dialog('open');
        }
    );
    $("#showSkillResourcePrimaries").click(
        function() {
            $("#primaryskilldialog").dialog('open');

            if (resourcePrimarySkill.length > 0) {

                for (var i = 0; i < resourcePrimarySkill.length; i++) {
                    var id = "" + resourcePrimarySkill[i].skillId.id + "";

                    $("#skillResourcePrimaries").dynatree("getTree").getNodeByKey(id).select();
                    $("#skillResourcePrimaries").dynatree("getTree").getNodeByKey(id).makeVisible();
                }
            }

        }
    );
    $("#showSkillResourceSecondaries").click(
        function() {
            $("#seconddaryskilldialog").dialog('open');

            if (rerourceSecondrySkill.length > 0) {
                for (var i = 0; i < rerourceSecondrySkill.length; i++) {
                    var id = "" + rerourceSecondrySkill[i].skillId.id + "";

                    $("#skillResourceSecondaries").dynatree("getTree").getNodeByKey(id).select();
                    $("#skillResourceSecondaries").dynatree("getTree").getNodeByKey(id).makeVisible();
                }
            }
        }
    );
    /* Start - code to populate relevant experience dropdowns - Digdershika*/

    $("#totalExperYear").removeClass("brdrRed");
    $("#totalExperMonth").removeClass("brdrRed");
    $("#relevantExpYear").removeClass("brdrRed");
    $("#relevantExpMonth").removeClass("brdrRed");

    var totalExperMonthSelect = document.getElementById('totalExperMonth');
    var totalExperYearSelect = document.getElementById('totalExperYear');
    var relevantExpMonthSelect = document.getElementById('relevantExpMonth');
    var relevantExpYearSelect = document.getElementById('relevantExpYear');

    for (var i = 0; i <= 50; i++) {
        var optYear = document.createElement('option');
        if (i.toString().length == 1) {
            optYear.value = '0' + i;
            optYear.innerHTML = i + " YY";
            if (i <= 11) {
                var optMonth = document.createElement('option');
                optMonth.value = '0' + i;
                optMonth.innerHTML = i + " MM";
            }
        } else {
            optYear.value = i;
            optYear.innerHTML = i + " YY";
            if (i <= 11) {
                var optMonth = document.createElement('option');
                optMonth.value = i;
                optMonth.innerHTML = i + " MM";
            }
        }
        relevantExpYearSelect.append(optYear);
        relevantExpMonthSelect.append(optMonth);
    }
    for (var i = 0; i <= 50; i++) {
        var optYear = document.createElement('option');
        if (i.toString().length == 1) {
            optYear.value = '0' + i;
            optYear.innerHTML = i + " YY";
            if (i <= 11) {
                var optMonth = document.createElement('option');
                optMonth.value = '0' + i;
                optMonth.innerHTML = i + " MM";
            }
        } else {
            optYear.value = i;
            optYear.innerHTML = i + " YY";
            if (i <= 11) {
                var optMonth = document.createElement('option');
                optMonth.value = i;
                optMonth.innerHTML = i + " MM";
            }
        }
        totalExperYearSelect.append(optYear);
        totalExperMonthSelect.append(optMonth);
    }
    /* End - code to populate relevant experience dropdowns - Digdershika*/

    $("#dialoglt").dialog({
        autoOpen: false
    });
    $("#dialoglt").dialog("option", "modal", true);
    $("#dialoglt").dialog("option", "title", "Report");
    $("#dialoglt").dialog("option", "buttons", [{
        text: "Ok",
        click: function() {
            $(this).dialog("close");
        }
    }]);
    $("#dialoglt").dialog({
        closeText: "hide"
    });
    $("#dialoglt").dialog({
        dialogClass: "alert"
    });
    $("#dialoglt").dialog({
        minHeight: 250,
        width: 860,
        height: 350
    });
    $("#dialoglt").dialog("option", "hide", {
        effect: "explode",
        duration: 1000
    });
    $("#dialoglt").dialog({
        show: {
            effect: "explode",
            duration: 800
        }
    });

    /* table header width*/
    //$("div.dataTables_scrollHead").width($("div.dataTables_scrollBody").width());
    stopProgress();
    $("div.errorNumericLast").hide();
    /*---------------validation for 40 hours-------------*/
    $(".capacity").hide();
    $(".capacityInp").on("blur", function() {
        var value = $(this).val();
        if (value > 40) {
            $(this).parent().parent("td").find(".capacity").show();
            $(this).css("border", "1px solid #ff0000");
        } else {
            $(this).css("border", "1px solid #D5D5D5");
            $(this).parent().parent("td").find(".capacity").hide();
        }
    });
    $(".tab_div").hide();

    $('ul.tabs a').click(function() {
        $(".tab_div").hide().filter(this.hash).show();
        $("ul.tabs a").removeClass('active');
        $('a[href$="tab2"]').addClass('MaintenanceTab');
        $(this).addClass('active');
        containerWidth();
        return false;
    }).filter(':first').click();

    $("thead input").keyup(function(key) {

        if (key.which == 13 || (key.which == 8 && this.value.length == 0)) {

            resourceTable.fnFilter(this.value, resourceTable.oApi._fnVisibleToColumnIndex(resourceTable.fnSettings(), $("thead input").index(this)));
        }
    });

    $("thead input").each(function(i) {
        this.initVal = this.value;
    });
    $("thead input").focus(function() {
        if (this.className == "search_init") {
            this.className = "";
            this.value = "";
        }
    });

    $("thead input").blur(function(i) {
        if (this.value == "") {
            this.className = "search_init";
            this.value = this.initVal;
        }
    });

    resourceTable = $('#resourceTableId').dataTable({
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": contextPath + 'list/' + "active",
        "sPaginationType": 'full_numbers',
        "pagingType": "full_numbers",
        "bAutoWidth": false,
        "sScrollX": "100%",
        "sScrollY": "350",
        "bScrollCollapse": true,
        "bPaginate": true,
        "aaSorting": [
            [1, 'asc']
        ],
        "bDestory": true,
        //bRetrieve": true,
        "sDom": 'RC<"clear"><"top"lf>rt<"bottom"ip<"clear">',
        //  "sDom": '<"projecttoolbar">lfrtip',
        "aaSorting": [
            [0, 'asc']
        ],
        "oLanguage": {
            "sLengthMenu": 'Show <SELECT>' + '<OPTION value=10>10</OPTION>' + '<OPTION value=25>25</OPTION>' + '<OPTION value=50>50</OPTION>' + '<OPTION value=100>100</OPTION>' + '<OPTION value=200>200</OPTION></SELECT> entries'
        },

        "bSortCellsTop": true,
        "fnServerData": function(sSource, aoData, fnCallback) {
            sSource = sSource + '?noCache=' + new Date().getTime();
            callJSONWithErrorCheck(sSource, aoData, null, function(json) {
                fnCallback(json);
            });
        },
        "fnDrawCallback": function() {
            handlePaginationButtons(resourceTable, "resourceTableId");
        },
        "fnInitComplete": function() {},
        "fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
            var releaseDate = aData[6];
            //alert(releaseDate);
            if (releaseDate != 'N.A') {
                nRow.className = 'redColorResource';
                //nRow.className=nRow.className +" " +'redColor';
            }
            //$('td:eq(17)', nRow).html(  aData[5] );
            //$('td:eq(18)', nRow).html(  aData[6] );
            $('td:eq(6)', nRow).html(aData[5]);
            $('td:eq(7)', nRow).html(aData[6]);

            $('td:eq(8)', nRow).html(aData[7]); //ownership
            $('td:eq(9)', nRow).html(aData[22]);
            $('td:eq(10)', nRow).html(aData[20]);
            $('td:eq(11)', nRow).html(aData[21]);
            $('td:eq(12)', nRow).html(aData[8]);
            $('td:eq(13)', nRow).html(aData[9]);
            $('td:eq(14)', nRow).html(aData[10]);
            $('td:eq(15)', nRow).html(aData[12]);
            $('td:eq(16)', nRow).html(aData[13]);
            $('td:eq(17)', nRow).html(aData[14]);
            $('td:eq(18)', nRow).html(aData[15]);
            //$('td:eq(10)', nRow).html(  aData[22] );
            //$('td:eq(11)', nRow).html(  aData[20] );
            //$('td:eq(12)', nRow).html(  aData[21] );
            //$('td:eq(13)', nRow).html(  aData[12] );
            //$('td:eq(14)', nRow).html(  aData[13] );
            // $('td:eq(15)', nRow).html(  aData[14] );
            //$('td:eq(16)', nRow).html(  aData[15] );
            $('td:eq(0)', nRow).html('<a href="javascript:void(0);" onClick="openMaintainance(' + aData[0] + ');">' + aData[1] + '</a>');

            $('td:eq(4)', nRow).html('<a href="javascript:void(0);" onClick="fileDownload(' + aData[0] + ' , \'' + "resume" + '\');">' + aData[18] + '</a>');

            $('td:eq(5)', nRow).html('<a href="javascript:void(0);" onClick="fileDownload(' + aData[0] + ' ,\'' + "TEF" + '\' );">' + aData[19] + '</a>');
            // $('td:eq(5)', nRow).html(  aData[22] );
            return nRow;
        },
        'aoColumnDefs': [{
            'bSortable': false,
            'aTargets': ['nosort']
        }],
        "aoColumns": [{
                sName: "employeeId",
                sWidth: "",
                bSortable: false,
                bVisible: false
            }, {
                sName: "yashEmpId",
                sWidth: "",
                bSortable: true,
                bVisible: true,

            }, {
                sName: "employeeName",
                sWidth: "",
                bSortable: true,
                bVisible: true
            }, {
                sName: "designationId",
                sWidth: "",
                bSortable: true,
                bVisible: true
            }, {
                sName: "gradeId",
                sWidth: "",
                bSortable: true
            }, {
                sName: "dateOfJoining",
                sWidth: "",
                bSortable: false,
            },

            {
                sName: "releaseDate",
                sWidth: "",
                bSortable: false,
            },

            {
                sName: "ownership",
                sWidth: "",
                bSortable: true
            },

            {
                sName: "currentBuId",
                sWidth: "",
                bSortable: true
            }, {
                sName: "buId",
                sWidth: "",
                bSortable: true
            },

            {
                sName: "emailId",
                sWidth: "",
                bSortable: false
            }, , {
                sName: "totalExper",
                sWidth: "",
                bSortable: false
            }, {
                sName: "relevantExper",
                sWidth: "",
                bSortable: false
            }, {
                sName: "yearDiff",
                sWidth: "",
                bSortable: false
            },
            /* { 
            	sName: "resumeFileName",
            	sWidth : "10px",
            	bSortable: true
            }, */
            /* { 
            	sName: "contactNumber",
            	sWidth : "",
            	bSortable: true
            }, */
            {
                sName: "locationId",
                sWidth: "",
                bSortable: true
            }, {
                sName: "currentReportingManager.employeeId	",
                sWidth: "",
                bSortable: true
            }, {
                sName: "currentReportingManagerTwo.employeeId	",
                sWidth: "",
                bSortable: true
            },
            /* { 
            	sName: "plannedCapacity",
            	sWidth : "10px",
            	bSortable: true
            },
            { 
            	sName: "actualCapacity",
            	sWidth : "10px",
            	bSortable: true
            }, */

            /* { 
											sName: "userRole",
											sWidth : "",
											bSortable: true
										},  
											 */
            {
                sName: "uploadResume",
                sWidth: "",
                bSortable: true
            }, {
                sName: "uploadTEF",
                sWidth: "",
                bSortable: true
            }

            /* ,
            { 
            	sName: "resignationDate",
            	sWidth : "10px",
            	bSortable: true
            } */
        ],
        //"sDom": '<"projecttoolbar">lfrtip'
        "sDom": '<"projecttoolbar">lfi<"top">rt<"bottom"<"#refresh">ip><"clear">'
        // "sDom": '<"top"i>rt<"bottom"<"#refresh">flp><"clear">'
    });
    $("#resourceTableId_filter").hide();

    resourceTable.$('tr').dblclick(function() {
        var sData = resourceTable.fnGetData(this);
        openMaintainance(($(this).attr('id')));
    });

    //containerWidth($("#resourceTableId"));
    //Added for new format date task:Team//
    //$("#dateOfJoining").datepicker({changeMonth: true,changeYear: true, dateFormat:'dd-M-yy'}).val();
    $("#dateOfJoining").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd-M-yy',
        yearRange: '1989:2029',
        onSelect: function(date) {
            // Your CSS changes, just in case you still need them
            var date = $(this).val();
            $("#dateOfJoining").removeClass("invalid");
            $("#dateOfJoining").parent("td").removeClass("invalid");
        }
    }).val();
    $("#resignationDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd-M-yy'
    }).val();
    $("#penultimateAppraisalId").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd-M-yy'
    }).val();
    $("#lastAppraisalId").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd-M-yy'
    }).val();
    $("#transferDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd-M-yy'
    }).val();
    $("#confirmationDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd-M-yy'
    }).val();
    $("#releaseDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd-M-yy'
    }).val();

    $("#visavalidDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd-M-yy'
    }).val();
    //Added for new format date task:Team//



    $('a[href$="tab2"]').click(function() {
        //ownershipDropdown();
        $("#newEmployee").reset();
        disableEnableSelect();
        $("div.errorNumericLast").hide();
        $("#newEmployee").reset();
        errorValidationHide();
        containerWidth();

    });

    $('#MaintenanceTabInactive').off('click');

    $('a[href$="tab1"]').click(function() {
        $("input.search_init").each(function() {
            var default_value = $(this).prop("defaultValue");
            //$("#currentReportingManagerOne").val(null).trigger('change');	
            //$("#currentReportingManagerTwo").val(null).trigger('change');	
            $(this).val(default_value);
        });

        $("div.errorNumericLast").hide();
        initTable();
        $("#showOrgStructure").hide();

        $("#jstree2").dynatree("getRoot").visit(function(node) {
            node.select(false);
        });
        $("#primaryskilldialog").hide();

        $("#skillResourcePrimaries").dynatree("getRoot").visit(function(node) {
            node.select(false);
        });

        $("#seconddaryskilldialog").hide();
        $("#skillResourceSecondaries").dynatree("getRoot").visit(function(node) {
            node.select(false);
        });

        document.getElementById("employeeIdTxt").value = "";

    });

    /*--------pop up-------*/
    $('#upload').click(function() {
        $('body').append('<div id="popUp_div" class="popUp"></div>');
        $('.popUp').show();
    });
    $('.popUp_cancel , .close_popUp').click(function() {
        $('.popUp').hide();
    });

    /*----------------------File Upload-----------------*/
    /*for placeholder*/
    /* $("input.placeHolder").focus(function(){
						if ($(this).val()=="'doc','docx','rtf','odt'"){
							$(this).val('').css({"color":"#000", "font-size":"13px"});	
						}
						}).blur(function() {
				        if($(this).val()==''){
								$(this).val("'doc','docx','rtf','odt'").css({"color":"#373737","font-size":"12px"});
						}
			   		}); */

    $("#fileUpload").on("change", function() {
        var iSize = 0;
        var file = $('input[type="file"]#fileUpload').val();

        $('#uploadInput').val(file);
        var fileValue = $('#uploadInput').val();
        var exts = ['doc', 'docx', 'rtf', 'odt'];
        var bgDiv = $("<div id = 'bgDiv' />");
        $("body").append(bgDiv);

        var x = $(window).width();
        var y = $(document).height();
        $("#bgDiv").css("height", y);
        $("#bgDiv").css("width", x);

        if (fileValue) {
            // split file name at dot
            var get_ext = file.split('.');
            // reverse name to check extension
            get_ext = get_ext.reverse();

            // check file type is valid as given in 'exts' array
            if ($.inArray(get_ext[0].toLowerCase(), exts) > -1) {
                //alert( 'Allowed extension!' );
                if ($.browser.msie) {
                    var objFSO = new ActiveXObject("Scripting.FileSystemObject");
                    var sPath = $("#fileUpload")[0].value;
                    var objFile = objFSO.getFile(sPath);
                    var iSize = objFile.size;
                    iSize = iSize / 1024;
                    //file
                } else
                    iSize = ($("#fileUpload")[0].files[0].size);
                //alert(iSize);
                if (iSize > 1048570) {

                    $("#bgDiv").css("display", "block");
                    $('#fileAlert').show().find('p').html('File Size is Too Large');
                    $('#uploadInput').val('');
                    $("#lblSize").html("");
                } else {
                    iSize = (Math.round(iSize * 100) / 100)
                    $("#lblSize").html(iSize + "kb");
                }
            } else {
                $("#bgDiv").css("display", "block");
                //$('#fileAlert').show().find('p').html(showError('\u2022 Invalid file Type'));

                // Added for task # 304 - start
                $('#fileAlert').html(showError('\u2022 Invalid file Type !'));
                $("#bgDiv").hide();
                // Added for task # 304 - end
                $('#uploadInput').val('');
                $("#lblSize").html("");
            }
        }
    });

    $('#uploadInput').on("click", function() {
        $('input[type="file"]#fileUpload').click();
    });
    $("#ok").click(function() {
        $(this).parent().parent().hide();
        $("#bgDiv").remove();
    });


    $(comboBoxBlur).each(function() {
        $(this).addClass("required");
    });
    $(comboBoxBlur).blur(function() {
        if ($(this).hasClass("required")) {
            if ($(this).val() == '') {
                $(this).next("a").addClass("brdrRed");
            } else {
                $(this).next("a").removeClass("brdrRed");
            }
        }
    });

    var checkServerValidationFlag = false;
    var emailId_empId_errorMsg = "";
    $("#save").click(function(event) {
    	
        var dojCheck = true;
        document.getElementById('yashEmpId').value = $.trim($("#yashEmpId").val());
        document.getElementById('firstName').value = $.trim($("#firstName").val());
        document.getElementById('middleName').value = $.trim($("#middleName").val());
        document.getElementById('lastName').value = $.trim($("#lastName").val());
        document.getElementById('contactNumber1').value = $.trim($("#contactNumber1").val());
        document.getElementById('contactNumber2').value = $.trim($("#contactNumber2").val());
        document.getElementById('emailId').value = $.trim($("#emailId").val());

        /* document.getElementById('projectIds').value= $.trim($("#reportAccessProjectList").val()); */
        //document.getElementById('usernameId').value= $.trim($("#usernameId").val());

        document.getElementById("uploadResumeFileName").value = $.trim($("#uploadResume").val());
        document.getElementById("uploadTEFfileName").value = $.trim($("#uploadTEF").val());
        if (document.getElementById('rrfAccess').checked == true) {
                 document.getElementById("rrfAccess").value = 'Y';
        }
        
        //Code to correct report access - start
    
        if (document.getElementById('reportUserId').checked == true) {
            document.getElementById("reportUserId").value = '1';
        }else if (document.getElementById('reportUserId').checked == false) {
             document.getElementById("reportUserId").value = '3';
        }
      
        //Code to correct report access - end

        $.each($('form#newEmployee select.check'), function() {
            $(this).removeAttr("disabled");
        });

        var dateFieldIds = ["#dateOfJoining", "#penultimateAppraisalId", "#lastAppraisalId", "#confirmationDate", "#releaseDate", "#transferDate"];
        $.each(dateFieldIds, function(index, fieldId) {
            //$(fieldId).datepicker("destroy");
            $(fieldId).datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: 'dd-M-yyyy'
            }).val();

            $(fieldId).removeAttr("disabled");
        });
        var validationFlag;

        validateSelect2(select2Arr);

        if (validateCombo(comboBoxBlur) && validateSelect2(select2Arr)) {
            validationFlag = true;
        } else {
            validationFlag = false;
        }

        event.preventDefault();

        var form_data = $("#newEmployee").triggerHandler("submitForm");
        // alert("form data"+form_data);
        //  alert('form_data >'+form_data);
        $(".capacityInp").each(function() {
            var value = $(this).val();
            if (value > 40 || value == '') {
                //isValid = false;
                $(this).parent().parent("td").find(".capacity").show();
                $(this).css("border", "1px solid #ff0000");
            } else {
                $(this).css("border", "1px solid #D5D5D5");
                $(this).parent().parent("td").find(".capacity").hide();
            }
        });
        var dateOfJoining = document.getElementById("dateOfJoining").value;
        var employeeId = $("#newEmployee input[name=employeeId]").val();
        var confirmationDate = document.getElementById("confirmationDate").value;
        var releaseDate = document.getElementById("releaseDate").value;
        var appraisalDate = document.getElementById("lastAppraisalId").value;
        var appraisalDate1 = document.getElementById("penultimateAppraisalId").value;
        var errorMsg = "";
        var empId = document.getElementById("yashEmpId").value;
        var firstName = document.getElementById("firstName").value;
        //added middle name
        var middleName = document.getElementById("middleName").value;
        var parentBu = document.getElementById("buId").value;
        var currentBu = document.getElementById("currentBuId").value;
        var bgAdminList = document.getElementById('bgAdmin').value;
        var userRole = document.getElementById('userRole').value;
        var lastName = document.getElementById("lastName").value;
        var resignationDate = document.getElementById("resignationDate").value;
        // var resignationDate = document.getElementById("resignationDate").value;
        /* added by neha for rejoining issue */
        $("#rejoiningFlag").removeAttr('disabled');
        /* added by neha for rejoining issue */

        var iChars = "!`@#$%^&*()+=-[]\\\';,./{}|\":<>?~_";

        for (var i = 0; i < empId.length; i++)

        {
            if (iChars.indexOf(empId.charAt(i)) != -1) {
                errorMsg = errorMsg + ("Please Enter AlphaNumeric values for Employee ID.");
                validationFlag = false;
                break;
            }

        }

        if (userRole == "ROLE_BG_ADMIN" && (bgAdminList == null || bgAdminList == "")) {
            validationFlag = false;
            errorMsg = errorMsg + "Please select at least 1 BG/BU for Group Admin";
            //showError(errorMsg);	
        }

        if (userRole == "ROLE_HR" && (bgAdminList == null || bgAdminList == "")) {
            validationFlag = false;
            errorMsg = errorMsg + "Please select at least 1 BG/BU for HR";
            //showError(errorMsg);	
        }

        // Added for task # 309 - Start

        if (empId == '' || empId == null) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Employee Id should not be blank ! <br/>";
            $("#yashEmpId").addClass("brdrRed");
        } else {
            $("#yashEmpId").removeClass("brdrRed");
        }
        if ($.trim($("#firstName").val()) == "") {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 First Name should not be blank ! <br />";
            $("#firstName").addClass("brdrRed");
        } else {
            $("#firstName").removeClass("brdrRed");
        }

        if (firstName.length > 40) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 FirstName should be less than 40 ! <br />";
            $("#firstName").addClass("brdrRed");
        } else if ($("#firstName").val() != '') {
            if (!(validateName($("#firstName").val()))) {
                validationFlag = false;
                errorMsg = errorMsg + "\u2022 Enter correct First Name, alphanumeric characters are not allowed !<br />";
                $("#firstName").addClass("brdrRed");
            } else {
                $("#firstName").removeClass("brdrRed");
            }
        }

        if (middleName.length > 40) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 MiddleName should be less than 40 ! <br />";
        }

        if ($.trim($("#lastName").val()) == '' || $("#lastName").val() == null) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Last Name should not be blank ! <br />";
            $("#lastName").addClass("brdrRed");
        } else {
            $("#lastName").removeClass("brdrRed");
        }
        if (lastName.length > 40) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 LastName should be less than 40 ! <br />";
            $("#lastName").addClass("brdrRed");
        } else if ($("#lastName").val() != '') {
            if (!(validateName($("#lastName").val()))) {
                validationFlag = false;
                errorMsg = errorMsg + "\u2022 Enter correct Last Name, alphanumeric characters are not allowed !<br />";
                $("#lastName").addClass("brdrRed");
            } else {
                $("#lastName").removeClass("brdrRed");
            }
        }

        if ($.trim($("#designationId").val()) == '' || $("#designationId").val() == null) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Designation should not be blank ! <br />";
        }
        if ($.trim($("#selGrade").val()) == '' || $("#selGrade").val() == null) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Grade should not be blank ! <br />";
        }

        if (parentBu == "-1" || currentBu == "-1" || parentBu == null || currentBu == null) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Please select BU ! <br/>";
        }

        /* if(($("#ownership option[value="+document.getElementById("ownership").value+"]").text()=='Owned')&& parentBu!=currentBu ){
					  			validationFlag = false;
					  			errorMsg=errorMsg+"For Owned Resource Parent BG-BU & Current BG-BU should be same";
					  				} */
        if (($("#ownership option:selected").text() == 'Owned') && parentBu != currentBu) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 For Owned Resource Parent BG-BU & Current BG-BU should be same ! <br />";
            $("#buId").parent("td").find("span.ui-combobox input").addClass("brdrRed").next("a").addClass("brdrRed");
            $("#currentBuId").parent("td").find("span.ui-combobox input").addClass("brdrRed").next("a").addClass("brdrRed");
        } else {
            $("#buId").parent("td").find("span.ui-combobox input").removeClass("brdrRed").next("a").removeClass("brdrRed");
            $("#currentBuId").parent("td").find("span.ui-combobox input").removeClass("brdrRed").next("a").removeClass("brdrRed");
        }

        if ($.trim($("#locationId").val()) == "" || $("#locationId").val() == null) {

            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Location should not be blank ! <br />";
            $("#locationId").parent("td").find("span.ui-combobox input").addClass("brdrRed").next("a").addClass("brdrRed");
        } else {
            $("#locationId").parent("td").find("span.ui-combobox input").removeClass("brdrRed").next("a").removeClass("brdrRed");
        }

        if (document.getElementById("deploymentLocation").value == "-1" || $("#deploymentLocation").val() == null) {

            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Current Location should not be blank ! <br />";
            $("#deploymentLocation").parent("td").find("span.ui-combobox input").addClass("brdrRed").next("a").addClass("brdrRed");
        } else {
            $("#deploymentLocation").parent("td").find("span.ui-combobox input").removeClass("brdrRed").next("a").removeClass("brdrRed");
        }

        if (document.getElementById("currentReportingManagerOne").value == "") {

            validationFlag = false;
            errorMsg = errorMsg + "\u2022 RM1 should not be blank ! <br />";
        }

        if (document.getElementById("currentReportingManagerTwo").value == "") {

            validationFlag = false;
            errorMsg = errorMsg + "\u2022 RM2 should not be blank ! <br />";
        }
        if ($.trim($("#ownership").val()) == "") {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Ownership should not be blank ! <br />";
            $("#ownership").addClass("brdrRed");
        } else {
            $("#ownership").removeClass("brdrRed");
        }
        if ($.trim($("#employeeCategory").val()) == "") {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Employee Category should not be blank ! <br />";
            $("#employeeCategory").addClass("brdrRed");
        } else {
            $("#employeeCategory").removeClass("brdrRed");
        }

        // Start To fix #189
        if ($.trim($("#emailId").val()) == "") {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Email Id should not be blank ! <br />";
            $("#emailId").addClass("brdrRed");
        } else {
            $("#emailId").removeClass("brdrRed");
        }
        if ($.trim($("#emailId").val()) != "") {
            if (!(validateEmail($("#emailId").val()))) {
                validationFlag = false;
                errorMsg = errorMsg + "\u2022 Domain of E-mail should be 'yash.com' ! <br />";
                $("#emailId").addClass("brdrRed");
            } else if (!(validateEmailForSplCharacters($("#emailId").val()))) {
                validationFlag = false;
                errorMsg = errorMsg + "\u2022 Email id should be like - aa@bb.yash.com ! <br />";
                $("#emailId").addClass("brdrRed");
            } else {
                $("#emailId").removeClass("brdrRed");
            }
        }

        if ($("#contactNumber1").val() != "") {
            if (!(validateContactNumber($("#contactNumber1").val()))) {
                validationFlag = false;
                errorMsg = errorMsg + "\u2022 Contact Number1 can only contain + - and numbers!<br />";
                $("#contactNumber1").addClass("brdrRed");
            } else {
                $("#contactNumber1").removeClass("brdrRed");
            }
        }

        if ($("#contactNumber2").val() != "") {
            if (!(validateContactNumber($("#contactNumber2").val()))) {
                validationFlag = false;
                errorMsg = errorMsg + "\u2022 Contact Number2 can only contain + - and numbers! <br />";
                $("#contactNumber2").addClass("brdrRed");
            } else {
                $("#contactNumber2").removeClass("brdrRed");
            }
        }
        if ($.trim($("#visaId").val()) == "") {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Visa Status should not be blank ! <br />";
            $("#visaId").addClass("brdrRed");
        } else {
            $("#visaId").removeClass("brdrRed");
        }

        if ($.trim($("#competency").val()) == "") {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Competency should not be blank ! <br />";
            $("#competency").addClass("brdrRed");
        } else {
            $("#competency").removeClass("brdrRed");
        }
        if ($.trim($("#dateOfJoining").val()) == "") {
            validationFlag = false;
            dojCheck = false;
            errorMsg = errorMsg + "\u2022 Date of Joining should not be blank ! <br />";
            $("#dateOfJoining").addClass("brdrRed");
        } else {
            dojCheck = true;
            $("#dateOfJoining").removeClass("brdrRed");
        }

        /* Start - Validations for all types of Experiences - Digdershika */
        /*Check for validation*/
        var relevantExpYY = document.getElementById("relevantExpYear").value;
        var relevantExpMM = document.getElementById("relevantExpMonth").value;
        var relevantExpInFloat = parseFloat("00.00");

        var totalExperYY = document.getElementById("totalExperYear").value;
        var totalExperMM = document.getElementById("totalExperMonth").value;
        if (totalExperYY.length == 1)
            totalExperYY = totalExperYY + "0";
        if (totalExperMM.length == 1)
            totalExperMM = totalExperMM + "0";
        var totalExpFloatValue = parseFloat("00.00");

        var dDate = $.trim($("#dateOfJoining").val());
        var rDate = $.trim($("#releaseDate").val());

        var yashExpInString = "";
        var yashExpFloatValue = parseFloat("00.00");
        var yashExpValue = document.getElementById("yearDiff").value;
        if (yashExpValue != null && yashExpValue != '') {
            yashExpInString = parseFloat(yashExpValue);
        } else
            yashExpInString = yashExpCal(dDate, rDate);
        yashExpFloatValue = yashExpInString;
        /*if YY and MM for relevant exp is in correct format then save value in relevant exp hidden field and let it pass to backend*/
        if (!basicValidationsNullTypeEmpty(relevantExpYY)) {
            validationFlag = false;
            $("#relevantExpYear").addClass("brdrRed");
            errorMsg = errorMsg + "\u2022 Please enter Relevant experience (years) ! <br />";
        } else if (!basicValidationsNullTypeEmpty(relevantExpMM)) {
            validationFlag = false;
            $("#relevantExpMonth").addClass("brdrRed");
            errorMsg = errorMsg + "\u2022 Please enter Relevant experience (months) ! <br />";
        } else if (basicValidationsNullTypeEmpty(relevantExpYY) && basicValidationsNullTypeEmpty(relevantExpMM) && dojCheck) {
            var relevantExpInString = relevantExpYY.toString() + "." + relevantExpMM.toString();
            relevantExpInFloat = parseFloat(relevantExpInString);
            if (relevantExpInString == "00.00" && dojIsMoreThan30Days($.trim($("#dateOfJoining").val()))) {
                validationFlag = false;
                $("#relevantExpYear").addClass("brdrRed");
                $("#relevantExpMonth").addClass("brdrRed");
                errorMsg = errorMsg + "\u2022 Please include yash exp in Relevant experience ! <br />";
            } else {
                $("#relevantExpYear").removeClass("brdrRed");
                $("#relevantExpMonth").removeClass("brdrRed");
                document.getElementById("relevantExper").value = relevantExpInString;
            }
        }
        //Code for total exp
        if (!basicValidationsNullTypeEmpty(totalExperYY)) {
            validationFlag = false;
            $("#totalExperYear").addClass("brdrRed");
            errorMsg = errorMsg + "\u2022 Please enter Total experience (years) ! <br />";
        } else if (!basicValidationsNullTypeEmpty(totalExperMM)) {
            validationFlag = false;
            $("#totalExperMonth").addClass("brdrRed");
            errorMsg = errorMsg + "\u2022 Please enter Total experience (months) ! <br />";
        } else if (basicValidationsNullTypeEmpty(totalExperYY) && basicValidationsNullTypeEmpty(totalExperMM) && dojCheck) {
            var totalExperInString = totalExperYY.toString() + "." + totalExperMM.toString();
            totalExpFloatValue = parseFloat(totalExperInString);
            if (totalExperInString == "00.00" && dojIsMoreThan30Days($.trim($("#dateOfJoining").val()))) {
                validationFlag = false;
                $("#totalExperYear").addClass("brdrRed");
                $("#totalExperMonth").addClass("brdrRed");
                errorMsg = errorMsg + "\u2022 Please include yash exp in Total experience ! <br />";
            } else {
                $("#totalExperYear").removeClass("brdrRed");
                $("#totalExperMonth").removeClass("brdrRed");
                document.getElementById("totalExper").value = totalExperInString;
            }

        }
        /*Start - Check for less than and greater than*/
        if (totalExpFloatValue < yashExpFloatValue) {
            validationFlag = false;
            $("#totalExperYear").addClass("brdrRed");
            $("#totalExperMonth").addClass("brdrRed");
            errorMsg = errorMsg + "\u2022 Please enter Total experience greater or equal to Yash experience ! <br />";
        } else if (relevantExpInFloat < yashExpFloatValue) {
            validationFlag = false;
            $("#relevantExpYear").addClass("brdrRed");
            $("#relevantExpMonth").addClass("brdrRed");
            errorMsg = errorMsg + "\u2022 Please enter Relevant experience greater or equal to Yash experience ! <br />";
        } else if (relevantExpInFloat > totalExpFloatValue) {
            validationFlag = false;
            $("#relevantExpYear").addClass("brdrRed");
            $("#relevantExpMonth").addClass("brdrRed");
            errorMsg = errorMsg + "\u2022 Please enter Relevant experience less than Total Experience ! <br />";
        }

        /*End - Check for less than and greater than*/
        /* End - Validations for all types of Experiences - Digdershika */
        // Added for Task # 309 - End

        if (!validDates1(dateOfJoining, appraisalDate)) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Appraisal Date 1 should be greater than the date of joining ! <br />";
            $("#lastAppraisalId").addClass("brdrRed");
            $("#dateOfJoining").addClass("brdrRed");
        } else {
            $("#lastAppraisalId").removeClass("brdrRed");
            $("#dateOfJoining").removeClass("brdrRed");
        }
        if (!validDates1(dateOfJoining, appraisalDate1)) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Appraisal Date 2 should be greater than the date of joining ! <br />";
            $("#penultimateAppraisalId").addClass("brdrRed");
            $("#dateOfJoining").addClass("brdrRed");
        } else {
            $("#penultimateAppraisalId").removeClass("brdrRed");
            $("#dateOfJoining").removeClass("brdrRed");
        }
        if (!validDates1(appraisalDate, appraisalDate1)) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Appraisal Date 2 should be greater than Appraisal Date 1 ! <br />";
            $("#lastAppraisalId").addClass("brdrRed");
            $("#penultimateAppraisalId").addClass("brdrRed");
        } else {
            $("#lastAppraisalId").removeClass("brdrRed");
            $("#penultimateAppraisalId").removeClass("brdrRed");
        }
        if (!validDates1(dateOfJoining, confirmationDate)) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Confirmation Date should be greater than the Date of Joining ! <br />";
            $("#confirmationDate").addClass("brdrRed");
            $("#dateOfJoining").addClass("brdrRed");
        } else {
            $("#dateOfJoining").removeClass("brdrRed");
            $("#confirmationDate").removeClass("brdrRed");
        }
        if (!validDates1(confirmationDate, resignationDate)) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Resignation Date should be greater than the Confirmation Date ! <br />";
            $("#resignationDate").addClass("brdrRed");
            $("#confirmationDate").addClass("brdrRed");
        } else {
            $("#resignationDate").removeClass("brdrRed");
            $("#confirmationDate").removeClass("brdrRed");
        }
        if (!validDates1(dateOfJoining, releaseDate)) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Release Date should be greater than the Date of Joining ! <br />";
            $("#releaseDate").addClass("brdrRed");
            $("#dateOfJoining").addClass("brdrRed");
        } else {
            $("#releaseDate").removeClass("brdrRed");
            $("#dateOfJoining").removeClass("brdrRed");
        }
        if (!validDates1(confirmationDate, releaseDate)) {
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Release Date should be greater than the Confirmation Date ! <br />";
            $("#releaseDate").addClass("brdrRed");
            $("#confirmationDate").addClass("brdrRed");
        } else {
            $("#releaseDate").removeClass("brdrRed");
            $("#confirmationDate").removeClass("brdrRed");
        }
        if (!validDates1(dateOfJoining, resignationDate)) { //added for defect 3840
            validationFlag = false;
            errorMsg = errorMsg + "\u2022 Resignation Date should be greater than the Date of Joining ! <br />";
            $("#resignationDate").addClass("brdrRed");
            $("#dateOfJoining").addClass("brdrRed");
        } else {
            $("#resignationDate").removeClass("brdrRed");
            $("#dateOfJoining").removeClass("brdrRed");
        }

        if (!validDates1(resignationDate, releaseDate)) {
            validationFlag = false;
            //errorMsg = errorMsg + "\u2022 Resignation Date should be lesser than the Release Date OR " + "Release Date should be greater than the Resignation Date ! <br />";
            errorMsg = errorMsg + "\u2022 Release Date should be greater than the Resignation Date ! <br />";
            $("#resignationDate").addClass("brdrRed");
            $("#releaseDate").addClass("brdrRed");
        } else {
            $("#releaseDate").removeClass("brdrRed");
            $("#resignationDate").removeClass("brdrRed");
        }
        /*Anjana Resource Allocation Resume/TEF File upload*/

        var uploadResume = document.getElementById("uploadResume").value;
        if (uploadResume != '') {
            var checkimg = uploadResume;
            if (!checkimg
                .match(/(\.doc|\.docx|\.pdf)$/)) {
                errorMsg = errorMsg + "\u2022 Please enter Document File Extensions .doc,.docx,.pdf ! <br/>";
                validationFlag = false;
                $("#uploadResume").addClass(
                    "brdrRed");

            } else {
                $("#uploadResume")
                    .removeClass(
                        "brdrRed");
            }
        }
        var uploadTEF = document.getElementById("uploadTEF").value;
        if (uploadTEF != '') {
            var checkimg = uploadTEF;
            if (!checkimg
                .match(/(\.doc|\.docx|\.pdf)$/)) {
                errorMsg = errorMsg + "\u2022 Please enter Document File Extensions .doc,.docx,.pdf ! <br/>";
                validationFlag = false;
                $("#uploadTEF").addClass(
                    "brdrRed");

            } else {
                $("#uploadTEF")
                    .removeClass(
                        "brdrRed");
            }
        }
        var sizeofdoc = 0;
        if (uploadResume != "") {
            sizeofdoc = document.getElementById("uploadResume").files[0].size;
            if (sizeofdoc == 0) {
                errorMsg = errorMsg + "\u2022 File size is 0 [Zero] for Upload Resume ! <br/>";
                validationFlag = false;
            }
            if (uploadTEF == "" && sizeofdoc > 0 && sizeofdoc > 2097152) {
                errorMsg = errorMsg + "\u2022 Unable to proceed Resume upload, File is larger than 2MB. ! <br/>";
                validationFlag = false;
            }
        }
        if (uploadTEF != "") {
            sizeofdoc = document.getElementById("uploadTEF").files[0].size;
            if (sizeofdoc == 0) {
                errorMsg = errorMsg + "\u2022 File size is 0 [Zero] for Upload TEF ! <br/>";
                validationFlag = false;
            }
            if (uploadResume == "" && sizeofdoc > 0 && sizeofdoc > 2097152) {
                errorMsg = errorMsg + "\u2022 Unable to proceed TEF upload, File is larger than 2MB. ! <br/>";
                validationFlag = false;
                //alert('This file size is: ' + document.getElementById("uploadResume").files[0].size/1024/1024 + "MB");
            }
        }
        if (uploadTEF != "" && uploadResume != "") {
            sizeofdoc = document.getElementById("uploadResume").files[0].size + document.getElementById("uploadTEF").files[0].size;

            if (sizeofdoc > 3250585) {
                errorMsg = errorMsg + "\u2022 Uploaded files size exceed ! <br/>";
                validationFlag = false;

            }
        }

        /*Ended By Anjana*/

        if ($('#emailErrorDiv').css('display') == 'block') {
            validationFlag = false;
        }

        var dateofjoining = document.getElementById("dateOfJoining").value;

        var monthNames = ["Jan", "Feb", "Mar",
            "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct",
            "Nov", "Dec"
        ];

        var today = new Date();
        var curr_date = today.getDate();
        var curr_month = monthNames[today.getMonth()];
        var curr_year = today.getFullYear();
        var todayDate = curr_date + "-" + curr_month + "-" + curr_year;
        if (!validDatesForJoining(dateofjoining, todayDate)) {
            errorMsg = errorMsg + "\u2022 Date Of Joining  should not be greater than the current date! <br />";
            validationFlag = false;
            $("#dateOfJoining").addClass("brdrRed");
        } else {
            $("#dateOfJoining").removeClass("brdrRed");
        } // End - validDatesForJoining
        // Start - validationFlag check 
        if (validationFlag && releaseDate.length != 0) {
            $.ajax({
                async: false, // !important
                url: "validateReleaseDate",
                data: "releaseDate=" + releaseDate + "&employeeId=" + employeeId,
                success: function(data) {

                },
                error: function(data) {
                    errorMsg = errorMsg + "\u2022 Release Date should be entered once All Allocations have been released! <br />";
                    validationFlag = false;
                }
            }); // End - ajax validateReleaseDate
        } // End - validationFlag check

        if (!validationFlag) { //mean validation Failed
            if (checkServerValidationFlag) {
                errorMsg = errorMsg + emailId_empId_errorMsg;
            }
            showErrorAlert(errorMsg);
            //Code to let fields disable in edit mode
            if (isEditable) {
                $("#confirmationDate").attr("disabled", "disabled");
                $("#designationId").attr("disabled", "disabled");
                $("#selGrade").attr("disabled", "disabled");
                $("#buId").attr("disabled", "disabled");
                $("#currentBuId").attr("disabled", "disabled");
                $("#locationId").attr("disabled", "disabled");
                $("#deploymentLocation").attr("disabled", "disabled");
                $("#employeeCategory").attr("disabled", "disabled");
                $("#ownership").attr("disabled", "disabled");
                $("#currentReportingManagerOne").attr("disabled", "disabled");
                $("#currentReportingManagerTwo").attr("disabled", "disabled");
                $("#dateOfJoining").attr("disabled", "disabled");
                $("#yearDiff").attr("disabled", "disabled");
                $("#confirmationDate").attr("disabled", "disabled");
                $("#releaseDate").attr("disabled", "disabled");
                $("#transferDate").attr("disabled", "disabled");
                $("#lastAppraisalId").attr("disabled", "disabled");
                $("#penultimateAppraisalId").attr("disabled", "disabled");
            }
        } else {
            // startProgress();
            //stopProgress();
            if (checkServerValidationFlag) {
                showErrorAlert(emailId_empId_errorMsg);
            } else {
                $('#currentReportingManagerOne, #currentReportingManagerTwo').prop('disabled', false);
                $("#newEmployee").submit();
                var successMsg = "Resource details have been saved successfully.";
                showSuccessAlert(successMsg);
            }
        }

    }); // End - save click function start

    //To disable visa valid date.			
    $("input.ui-state-default").blur(function() {
        var selectVal = $(this).val();
        if (selectVal == "No Visa") {
            $("#visavalidDate").datepicker('setDate', null);

            $("#visavalidDate").attr("disabled", "disabled");
        } else {
            $("#visavalidDate").removeAttr("disabled", "disabled");
        }
    });

    // Start - newEmployee form validaval
    $("#newEmployee").validVal({
        //Start - customvalidation
        customValidations: {
            "server-validation": function(val) { //Start - Server-validation
                var employeeId;
                var result = true;
                var requestData;
                employeeId = $("#newEmployee input[name=employeeId]").val();
                /* employeeId = $("#newEmployee").find('input[name="employeeId"]').val() ; */
                if (employeeId == null || employeeId == undefined || employeeId == '') {
                    employeeId = 0;
                }
                if (val == null || val == undefined || val == '') {
                    checkServerValidationFlag = false;
                    emailId_empId_errorMsg = "";
                    $(this).siblings("div.errorNumericLast").hide();
                    $(this).siblings("div.errorNumeric").hide();
                    return result;
                }
                var rejoiningFlag = $('#rejoiningFlag').is(':checked');

                // Start - employeeId check
                if (employeeId == 0) {
                    if ($('#rejoiningFlag').is(':unchecked'))
                        requestData = "name=" + $(this).attr("name") + "&value=" + val + "&employeeId=" + employeeId;
                    else
                        requestData = "name=" + $(this).attr("name") + "&value=" + val + "&employeeId=" + employeeId + "&rejoiningFlag=" + rejoiningFlag;

                } else {
                    requestData = "name=" + $(this).attr("name") + "&value=" + val + "&employeeId=" + employeeId;
                }
                // End - employeeId check
                // Start - requestData check
                if (requestData != "") {

                    $.ajax({
                        async: false, // !important
                        url: contextPath + "validate",
                        data: requestData,
                        success: function(data) {
                            if (data.result != true) {
                                result = false;
                                //isDuplicateEmailOrID = true;
                            }
                        }
                    });
                } // End - requestData check
                // Start - result flag check
                if (!result) {

                    if ($(this).attr("name") == 'yashEmpId') {
                        checkServerValidationFlag = true;
                        emailId_empId_errorMsg = emailId_empId_errorMsg + "\u2022 Please enter correct EmployeeId ! <br />";
                        $(this).siblings("div.errorNumericLast").show();
                        $(this).siblings("div.errorNumeric").show();
                    } else if ($(this).attr("name") == 'emailId' && $('#rejoiningFlag').prop('checked') != true) {
                        checkServerValidationFlag = true;
                        emailId_empId_errorMsg = emailId_empId_errorMsg + "\u2022 Please enter correct EmailId ! <br />";
                        $(this).siblings("div.errorNumericLast").show();
                        $(this).siblings("div.errorNumeric").show();
                    }
                    // added by neha for rejoining issue
                    else if ($(this).attr("name") == 'emailId' && $('#rejoiningFlag').prop('checked') == true) {
                        checkServerValidationFlag = true;
                        emailId_empId_errorMsg = emailId_empId_errorMsg + "\u2022 Please enter correct EmailId ! <br />";
                        $(this).siblings("div.errorNumericLast").show();
                        $(this).siblings("div.errorNumeric").show();
                    }
                    // added by neha for rejoining issue
                } else {

                    if ($(this).attr("name") == 'yashEmpId') {
                        checkServerValidationFlag = false;
                        emailId_empId_errorMsg = "";
                        $(this).siblings("div.errorNumericLast").hide();
                        $(this).siblings("div.errorNumeric").hide();
                    } else if ($(this).attr("name") == 'emailId') {
                        checkServerValidationFlag = false;
                        emailId_empId_errorMsg = "";
                        $(this).siblings("div.errorNumericLast").hide();
                        $(this).siblings("div.errorNumeric").hide();
                    }
                    return result;
                }
                // End - result flag check
                // Start - rejoiningFlag Click function
                document.getElementById('rejoiningFlag').onclick = function() {
                    if (!(this.checked)) {
                        if (!result) {
                            if ($(this).attr("name") == 'emailId') {
                                checkServerValidationFlag = true;
                                emailId_empId_errorMsg = emailId_empId_errorMsg + "\u2022 Please enter correct EmailId ! <br />";
                                $(this).siblings("div.errorNumericLast").show();
                                $(this).siblings("div.errorNumeric").show();
                            }
                        } else {
                            if ($(this).attr("name") == 'emailId') {
                                checkServerValidationFlag = false;
                                emailId_empId_errorMsg = "";
                                $(this).siblings("div.errorNumericLast").hide();
                                $(this).siblings("div.errorNumeric").hide();
                            }
                            return result;
                        }
                    } else {
                        if ($(this).attr("name") == 'emailId') {
                            checkServerValidationFlag = false;
                            emailId_empId_errorMsg = "";
                            $("div.errorNumericLast").hide();
                            $("div.errorNumeric").hide();
                            return true;
                        }
                    }
                }; // End - rejoiningFlag Click function
            } //End - search validation
        } // End - Custom validation
    }); // End - newEmployee form validaval

    var isEditable = false;
    $('.close').click(function() {
        $('.notification-bar').hide();
    });
    $("#dateOfJoining").bind("click", function() {
        //alert("The paragraph was clicked.");
        var dojInput = $(this).val();
    });


});

function showErrorAlert(message) {
    $('.alertWrapper .alert-danger').append('<p>' + message + '</p>');
    $('.alertWrapper.danger').show();
    //.delay(3000).empty().fadeOut("slow");
}

function showSuccessAlert(message) {
    $('.alertWrapper>.alert-success').text(message);
    $('.alertWrapper.success').show().delay(5000).fadeOut('slow');
}

function daydiff(dateofJoining, releaseDate) {
    if (releaseDate == '' || releaseDate == null || releaseDate == 'null') {
        releaseDate = new Date();
    }
    var firstDate = Date.parse(dateofJoining);
    var secondDate = Date.parse(releaseDate);
    var diff = (secondDate - firstDate) / (1000 * 60 * 60 * 24 * 7);
    var weeks = Math.round(diff);
    return weeks;
}

function getDaysInMonth(month, year) {
    // Here January is 1 based
    //Day 0 is the last day in the previous month
    return new Date(year, month, 0).getDate();
    // Here January is 0 based
    // return new Date(year, month+1, 0).getDate();
}

function dojIsMoreThan30Days(dateOfJoiningDate) {
    var todayDate = new Date();
    var curr_date = todayDate.getDate();
    var dojDate = Date.parse(dateOfJoiningDate);
    var todayMonth = todayDate.getMonth();
    var todayYear = todayDate.getYear();
    var days = parseInt((todayDate - dojDate) / (1000 * 60 * 60 * 24));
    var daysInLastMonth = getDaysInMonth(todayMonth, todayYear)
    var diffOfTwoDays = days - daysInLastMonth;
    if (diffOfTwoDays < 1)
        return false;
    return true;
}

function yashExpCal(dateofJoining, releaseDate) {
    var doj = "",
        rlDate = "";

    if (null == dateofJoining || '' == dateofJoining) {
        doj = new Date();
    } else
        doj = Date.parse(dateofJoining);
    if (null == releaseDate || '' == releaseDate) {
        rlDate = new Date();
    } else
        rlDate = Date.parse(releaseDate);
    var dateB = rlDate;
    var dateA = doj;

    var yearB = dateB.getFullYear();
    var monthB = dateB.getMonth();
    var dayB = dateB.getDate();

    var yearA = dateA.getFullYear();
    var monthA = dateA.getMonth();
    var dayA = dateA.getDate();

    var dateBB = moment([yearB, monthB, dayB]);
    var dateAA = moment([yearA, monthA, dayA]);

    var years = dateBB.diff(dateAA, 'year'); //2
    dateAA.add(years, 'years');
    var months = dateBB.diff(dateAA, 'months'); //35
    dateAA.add(months, 'months');
    if (months < 10)
        months = "0" + months;
    return years + "." + months;
}

function loadResourceLoanTrasfer() {
    $("#newEmployee").triggerHandler("submitForm");
    document.getElementById('userAction').value = 'redirectToLoanTransfer';
    $("#newEmployee").submit();
}

function loadHistory() {
    $.ajax({
        url: contextPath + 'loadHistory',
        contentType: "application/json",
        async: false,
        data: {
            "resourceid": document.getElementById("employeeIdTxt").value
        },
        success: function(response) {
            resourceHistoryData = response;
            if (resourceHistoryData == "") {
                //alert('Resource Loan or Transfer History data not found');
                // Added for task # 216 - Start
                var text = "Resource Loan or Transfer History data not found";
                showAlert(text);
                // Added for task # 216 - End

            } else {
                $("#resourceHData").html("");
                $("#tableResourceData > tbody:last").append($("#resourceAllocationRows").render(resourceHistoryData));
                containerWidth();
                $("#dialoglt").dialog('open');
            }
        },
        error: function(errorResponse) {
            showError(errorResponse);
        }
    })
}

function callJSONWithErrorCheck(sSource, aoData, callback1, callback2) {
    if (typeof(callback1) != 'function' && callback1 != null) {
        return callback2(callback1);
    }
    if (callback1 == null) {
        callback1 = callback2;
    }
    try {
        $.ajax({
            url: sSource,
            type: "GET",
            dataType: 'json',
            data: aoData,
            success: function(json) {
                if (json != null && json != "") {
                    callback1(json);
                } else {
                    // alert("Error Occured - Nothing returned from server");
                    // Added for task # 216 - Start
                    var text = "Error Occured - Nothing returned from server";
                    showAlert(text);
                    // Added for task # 216 - End
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                //   alert("Error Occured. \nText: " + textStatus + "\nError: " + errorThrown + "\nHTTP Status: " + XMLHttpRequest.responseText);
            }
        });
    } catch (err) {
        txt = "There was an error on this page.\n\n";
        txt += "Error description: " + err.description + "\n\n";
        txt += "Click OK to continue.\n\n";
        //  alert(txt);    
    }
}

function handlePaginationButtons(dataTableObj, dataTableId) {
    if (dataTableObj != null) {
        var oSettings = dataTableObj.fnSettings();
        if (oSettings._iDisplayLength >= oSettings.fnRecordsDisplay()) {
            $('#' + dataTableId + '_paginate').fadeTo(0, .1);
            $('#' + dataTableId + '_first').css('cursor', 'default');
            $('#' + dataTableId + '_last').css('cursor', 'default');
            $('#' + dataTableId + '_next').css('cursor', 'default');
            $('#' + dataTableId + '_previous').css('cursor', 'default');
            $('.paginate_active').css('cursor', 'default');
        } else {
            $('#' + dataTableId + '_paginate').fadeTo(0, 1);
            $('#' + dataTableId + '_first').css('cursor', 'pointer');
            $('#' + dataTableId + '_last').css('cursor', 'pointer');
            $('#' + dataTableId + '_next').css('cursor', 'pointer');
            $('.paginate_active').css('cursor', 'pointer');
        }
    }
}

function ownershipDropdown() {
    if (vLoan == null && vTransfer == null && vContractor == null) {
        vLoan = $("#ownership option[value='7']").detach();
        vTransfer = $("#ownership option[value='8']").detach();
        vContractor = $("#ownership option[value='6']").detach();
    }
}

function openMaintainance(id) {

    $('input[type=checkbox]').each(function() {
        this.checked = false;
    });
    startProgress();
    errorValidationHide();
    getResourceById(id);
    displayMaintainance();
    document.getElementById('userAction').value = 'update';
    //window.setTimeout("Rmvalidate()", 500);
    stopProgress();
    isEditable = true;

}

function populateBuForGroupAdmin(data) {
    var bulist = data.split(",")
    for (var i = 0; i < bulist.length; i++) {
        $("#jstree2").dynatree("getTree").getNodeByKey(bulist[i]).select();
        $("#jstree2").dynatree("getTree").getNodeByKey(bulist[i]).makeVisible();
    }
}

function Rmvalidate() {

    var yashtxt = document.getElementById('employeeIdTxt').value;

    /* RM1 List VALIDATION	 */
    var i;
    var ddlArray = new Array();
    var rm1 = document.getElementById('currentReportingManagerOne');

    for (i = 0; i < rm1.options.length; i++) {
        ddlArray[i] = rm1.options[i].value;

        if (ddlArray[i] == (yashtxt)) {
            //splice(i,1,ddlArray);

            rm1.options[i] = null;
            break;
        }
    }

    /* RM2 List VALIDATION	 */

    var ddlArray1 = new Array();
    var rm2 = document.getElementById('currentReportingManagerTwo');

    for (i = 0; i < rm2.options.length; i++) {
        ddlArray1[i] = rm2.options[i].value;

        if (ddlArray1[i] == (yashtxt)) {
            //splice(i,1,ddlArray);

            rm2.options[i] = null;
            break;
        }
    }
}

function validateCombo(comboBoxArray) {
    var varcount = 0;
    $(comboBoxArray).each(function(index) {
        var selectDdVal = $(this).val();
        if (selectDdVal == '') {
            $(this).addClass("brdrRed").next("a").addClass("brdrRed");
            varcount++;
        } else {
            $(this).removeClass("brdrRed").next("a").removeClass("brdrRed");
        }
    });
    if (varcount > 0) return false;
    else return true;

}

function validateSelect2(select2Array) {
    var varcount = 0;
    $(select2Array).each(function(index) {
        var selectDdVal = $(this).parents('td').find('select').val();
        if (!selectDdVal) {
            $(this).addClass("brdrRed");
            varcount++;
        } else {
            $(this).removeClass("brdrRed");
        }
    });
    if (varcount > 0) {
        return false;
    } else {
        return true;
    }
}

function errorValidationHide() {
    $(".capacityInp").css("border", "1px solid #D5D5D5");
    $(".capacity").hide();
    $("select.required").parent("td").find("span.ui-combobox input").removeClass("brdrRed").next("a").removeClass("brdrRed");
    $("#totalExperYear").removeClass("brdrRed");
    $("#totalExperMonth").removeClass("brdrRed");
    $("#relevantExpYear").removeClass("brdrRed");
    $("#relevantExpMonth").removeClass("brdrRed");
}

function getResourceById(id) {
    $.getJSON(contextPath + id, {}, showResource);
}

function getBuForGroupAdmin(id) {
    $.getJSON("", {
        find: "ByAllBuForGroupAdmin",
        employeeId: id
    }, populateBuForGroupAdmin);
}

function showResource(data) {
    $('#currentReportingManagerOne').append('<option value="' + data.currentReportingManager.employeeId + '">' + data.currentReportingManager.employeeName + '[' + data.currentReportingManager.yashEmpId + ']' + '</option>');
    $('#currentReportingManagerTwo').append('<option  value="' + data.currentReportingManagerTwo.employeeId + '">' + data.currentReportingManagerTwo.employeeName + '[' + data.currentReportingManagerTwo.yashEmpId + ']' + '</option>');
    $('#currentReportingManagerOne, #currentReportingManagerTwo').prop('disabled', true);
    validateCombo(comboBoxBlur);
    validateSelect2(select2Arr);
    removeInvalidProp(); 

    $('#preferred-label, #preferredLocation').show();
  
    var totalExp = Math.floor(data.totalExper);
    var totalMon = ((data.totalExper % 1).toFixed(2)).slice(".");
    totalExp = totalExp + totalMon[1] + totalMon[2] + totalMon[3];
    var relevantExp = Math.floor(data.relevantExper);
    var relevantMon = ((data.relevantExper % 1).toFixed(2)).slice(".");
    relevantExp = relevantExp + relevantMon[1] + relevantMon[2] + relevantMon[3];
    data.totalExper = totalExp;
    data.relevantExper = relevantExp;
    /* Start - code to split relevant experience in years and months and then display - Digdershika*/

    var relevantExpValue = data.relevantExper;
    var relevantExpYear = Math.floor(relevantExpValue);
    var relevantMonth = ((relevantExpValue % 1).toFixed(2)).slice(".");

    if (relevantExpYear.toString().length == 1) {
        relevantExpYear = "0" + relevantExpYear;
    }

    data.relevantExpYear = relevantExpYear;
    data.relevantExpMonth = relevantMonth.split(".")[1];

    var totalExperValue = data.totalExper;
    var totalExperYear = Math.floor(totalExperValue);
    var totalExperMonth = ((totalExperValue % 1).toFixed(2)).slice(".");

    if (totalExperYear.toString().length == 1) {
        totalExperYear = "0" + totalExperYear;
    }
    data.totalExperYear = totalExperYear;
    data.totalExperMonth = totalExperMonth.split(".")[1];

    /* End - code to split relevant experience in years and months and then display - Digdershika*/
    $.each($('form#newEmployee select.check'), function() {
        $(this).show();
        $(this).next().hide();
        $(this).attr("disabled", "disabled");
    });

    //$('form#newEmployee input').attr("readonly","readonly");

    var dateFieldIds = ["#dateOfJoining", "#penultimateAppraisalId", "#lastAppraisalId", "#confirmationDate", "#releaseDate", "#transferDate"];
    $.each(dateFieldIds, function(index, fieldId) {
        $(fieldId).datepicker("destroy");
        $(fieldId).attr("disabled", "disabled");
    });

    $("#newEmployee").populate(data, {
        debug: false,
        resetForm: true
    });

    document.getElementById("employeeIdTxt").value = data.employeeId;
  
    if(data.preferredLocation){
	    document.getElementById("preferredLocation").value = data.preferredLocation.location;
	    
    }
    // Start-Task given by Chitra
    // var pk= document.getElementById("employeeIdTxt").value;
    var pk = $("#newEmployee input[name=employeeId]").val();
    if (pk == null || pk == 0) {

        $('#loanOrTransfer').hide();
        $('#loanId').hide();
    } else {
        $('#loanOrTransfer').show();
        if (data.releaseDate != null)
            $('#loanId').hide();
        else
            $('#loanId').show();
    }
    // End- Task given by Chitra

    function populateBuForGroupAdmin(data) {
        var bulist = data.split(",")
        for (var i = 0; i < bulist.length; i++) {
            $("#jstree2").dynatree("getTree").getNodeByKey(bulist[i]).select();
            $("#jstree2").dynatree("getTree").getNodeByKey(bulist[i]).makeVisible();
        }
    }

    if (data.userRole == "ROLE_BG_ADMIN" || data.userRole == "ROLE_HR") {
        $("#showOrgStructure").show();
        getBuForGroupAdmin(data.employeeId);
    } else {
        $("#showOrgStructure").hide();
    }

    resourcePrimarySkill = data.skillResourcePrimaries;
    rerourceSecondrySkill = data.skillResourceSecondaries;

    /* added by neha for rejoining issue - start  */
    if (data.rejoiningFlag == 'Y') {
        $("#rejoiningFlagTr").show();
        $("#rejoiningFlag").attr('checked', true).prop('disabled', true);
        document.getElementById("rejoiningFlag").value = 'Y';
    } else {
        $("#rejoiningFlagTr").hide();
        document.getElementById("rejoiningFlag").value = '';
    }
    /* added by neha for rejoining issue - end  */
    /* added by Anjana - Start  */
    if (data.rrfAccess == 'Y') {
        document.getElementById('rrfAccess').checked = true;
        document.getElementById("rrfAccess").value = 'Y';
    } else {
        document.getElementById("rrfAccess").value = '';
    }
   
    if (data.reportUserId == '1') {
        document.getElementById('reportUserId').checked = true;
        document.getElementById("reportUserId").value = '1';
    } else if (data.reportUserId == '3') {
    	document.getElementById('reportUserId').checked = false;
        document.getElementById("reportUserId").value = '3';
    }
    //Code to correct Report access - end
    if ($('#uploadInput').val() == '') {
        //$('#uploadInput').val("'doc','docx','rtf','odt'");
        $('#uploadInput').val("Select File");
    }

    $('input[type="file"]#fileUpload').height($('div.uploadBtnDiv').height());
    stopProgress();

}

function disableEnableSelect() {

    var pk = $("#newEmployee input[name=employeeId]").val();

    var inputhtml;
    //not doing any validation .. We will use jquery validation framework to do that for us before form gets submitted

    $.each($('form#newEmployee select.check'), function() {
        inputhtml = $(this).next().html;
        if (pk != null && pk > 0) {
            $(this).show();
            $(this).next().hide();
            $(this).attr("disabled", "disabled");

            var dateFieldIds = ["#dateOfJoining", "#penultimateAppraisalId", "#lastAppraisalId", "#confirmationDate", "#releaseDate", "#transferDate"];
            $.each(dateFieldIds, function(index, fieldId) {
                $(fieldId).datepicker("destroy");
                $(fieldId).attr("disabled", "disabled");
            });
        } else {
            $(this).hide();
            $(this).next().show();
            $(this).removeAttr("disabled");
            $('#loanOrTransfer').hide(); // Task given by Chitra
            $('#loanId').hide();
        }

        //$('form#newEmployee input').attr("readonly","readonly");
        if (pk == null || pk == 0) {
            var dateFieldIds = ["#dateOfJoining", "#penultimateAppraisalId", "#lastAppraisalId", "#confirmationDate", "#releaseDate", "#transferDate"];
            $.each(dateFieldIds, function(index, fieldId) {
                //$(fieldId).datepicker("destroy");
                $(fieldId).datepicker({
                    changeMonth: true,
                    changeYear: true
                }).val();
                $(fieldId).removeAttr("disabled");
            });
        }
    });
}
//End - disableEnableSelect	
//Start - saveResource
function saveResource() {

    var pk = $("#newEmployee input[name=employeeId]").val();
    //not doing any validation .. We will use jquery validation framework to do that for us before form gets submitted
    if (pk != null && pk > 0) {

        $.putJson('resources', $("#newEmployee").toDeepJson(), function(data) {
            var successMsg = "Resource details have been saved successfully";
            //showSuccess(successMsg);
            showSuccessAlert(successMsg);
            startProgress();
            location.reload();
        }, 'json');
    } else {

        $('#currentReportingManagerOne').val(null).trigger('change');
        $('#currentReportingManagerTwo').val(null).trigger('change');
        $.postJson('resources', $("#newEmployee").toDeepJson(), function(data) {
            //should call refreshGrid instead of location reload, and update customerTableId table.
            startProgress();
            location.reload();
        }, 'json');
    }
    stopProgress();
}

function refreshGrid() {
    $.getJSON("resources", function(data) {

    });
}

function validDates1(fromDate, toDate) {
    var SDate = '';
    var startDate = '';
    var EDate = '';
    var endDate = '';

    if (toDate != "") {
        var dateSplit = toDate.split("-");
        var dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
        var EDate = dateObjendDate;
        endDate = new Date(EDate);
        endDate.setHours(0, 0, 0, 0);
    }

    if (fromDate != "") {
        var dateSplit1 = fromDate.split("-");
        var dateObjfromDate = new Date(dateSplit1[1] + " " + dateSplit1[0] + ", " + dateSplit1[2]);
        var SDate = dateObjfromDate;
        startDate = new Date(SDate);
        startDate.setHours(0, 0, 0, 0);
    }

    if (SDate != '' && EDate != '' && startDate > endDate)
        return false;
    return true;
}

function validDatesForJoining(fromDate, toDate) {

    var SDate = '';
    var startDate = '';
    var EDate = '';
    var endDate = '';

    if (toDate != "") {
        var dateSplit = toDate.split("-");
        var dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
        EDate = dateObjendDate;
        endDate = new Date(EDate);
        endDate.setHours(0, 0, 0, 0);
    }
    if (fromDate != "") {
        var dateSplit1 = fromDate.split("-");
        var dateObjfromDate = new Date(dateSplit1[1] + " " + dateSplit1[0] + ", " + dateSplit1[2]);
        SDate = dateObjfromDate;
        startDate = new Date(SDate);
        startDate.setHours(0, 0, 0, 0);
    }
    if (SDate != '' && EDate != '' && startDate > endDate)
        return false;
    return true;
}

/*Start- Added By Anjana for Resume and TEF upload Download */
function fileUpload(fileName) {
    if (fileName.value != "") {
        var sizeofdoc = fileName.files[0].size;
        //alert(sizeofdoc + "bytes");
        if (!/(\.doc|\.docx|\.pdf)$/i.test(fileName.value) /* | sizeofdoc > 358400 */ ) {
            showError("Upload only doc or pdf files");

            return false;
        } else {
            return true;
        }
    } else {
        showError('Please select pdf or doc file');
        return false;
    }
}

function fileDownload(id, downloadFileFlag){	
 	$.ajax({				
        url: contextPath+'downloadfiles',
     	contentType: "application/json",
     	async:false,
     	data: {"id":id, "downloadFileFlag":downloadFileFlag},  	
     	success: function(response) { 			     			
     		if(response==""){
     			showAlert("\u2022 File not found for id " + id + " ! <br />");
     			
     		}else{   
     		    if(id !== '' && downloadFileFlag !== ''){
					window.location.href = contextPath+'downloadfiles?id='+id+'&downloadFileFlag='+downloadFileFlag+'';
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

function formatData1(userList) {
    $.map(userList, function(item, idx) {
        item.id = item.employeeId;
        item.text = item.firstName + " " + item.lastName + "[" + item.yashEmpId + "]";
    });

    return userList;
}

function formatData2(userList) {
    $.map(userList, function(item, idx) {
        item.id = item.employeeId;
        item.text = item.firstName + " " + item.lastName + "[" + item.yashEmpId + "]";
    });

    return userList;
}

function removeInvalidProp() {
    $('input, select').removeClass('brdrRed');
}

function initTable() {
    //resourceTable.fnDraw();
}
//Ankur Arora has fixed it- start
function addNew(e) {
    ownershipDropdown();
    isEditable = false;
    var validator = $("#newEmployee").validate();
    validator.resetForm();
    $("showSkillResourcePrimaries").reset();
    $("showSkillResourceSecondaries").reset();
    $("#yashEmpId").removeClass("brdrRed");
    $("#firstName").removeClass("brdrRed");
    $("#lastName").removeClass("brdrRed");
    $("#emailId").removeClass("brdrRed");
    $("#dateOfJoining").removeClass("brdrRed");
    $("#totalExper").removeClass("brdrRed");
    $("#relevantExper").removeClass("brdrRed");
    //$("tr#trUsernameId").hide();
    $('#formTable .trUsername').hide();
    $('#formTable .tdYashExp').hide();
    $("#newEmployee").reset();
    $('#currentReportingManagerOne, #currentReportingManagerTwo').val(null).trigger('change');
    $('#currentReportingManagerOne, #currentReportingManagerTwo').prop('disabled', false);
    disableEnableSelect();
    errorValidationHide();
    $("#rejoiningFlag").attr('checked', false).prop('disabled', false); // added by neha for rejoining issue
    $("#rrfAccess").attr('checked', false).prop('disabled', false);
    $("#reportUserId").attr('checked', false).prop('disabled', false);
    /* $("#rejoiningFlag").attr('checked',false); */
    eventTarget = e.target;
    displayMaintainance();

    $('input[type="file"]#fileUpload').height($('div.uploadBtnDiv').height());
    $('#preferred-label, #preferredLocation').hide();
}

function OnActiveRecord(value) {
    if (value == 'All') {
        var oSettings = resourceTable.fnSettings();
        // resourceTable.fnDestroy();
        oSettings.sAjaxSource = contextPath + "list/all";
        //resourceTable.fnClearTable();
        resourceTable.fnDraw();
        // resourceTable = $('#resourceTableId').dataTable(initParams);

    } else {
        var oSettings = resourceTable.fnSettings();
        // resourceTable.fnDestroy();
        oSettings.sAjaxSource = contextPath + "list/active";
       // resourceTable.fnClearTable();
        resourceTable.fnDraw();
    }

    //alert(this.value );
}
//Ankur Arora has fixed it- End