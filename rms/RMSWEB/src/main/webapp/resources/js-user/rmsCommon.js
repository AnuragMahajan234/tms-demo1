/*----------------- function for preparing json data starts ------------------*/
var saveOpen = false;
var nEditing = null;

function getNextWeekday(date,weekday) { 

	var nextWeekEndDate = new Date(date);
	nextWeekEndDate.setDate(nextWeekEndDate.getDate() + weekday - nextWeekEndDate.getDay() + 7);
	if (nextWeekEndDate > new Date()) {
		return null;
	} else {
		return nextWeekEndDate;
	}
}

function getLastWeekday(date,weekday) { 
	date.setDate( date.getDate() + weekday - date.getDay()-7); 
    return date;
}

function getDateInFormatMM_DD_YY(latestWeekEndDate)
{
	var dateValues = latestWeekEndDate.split("/");
	var dateInFormat_MM_DD_YY = new Date();
	dateInFormat_MM_DD_YY.setMonth(dateValues[0]-1);
	dateInFormat_MM_DD_YY.setDate(dateValues[1]);
	dateInFormat_MM_DD_YY.setFullYear(dateValues[2]);
	return dateInFormat_MM_DD_YY;
}

function getFormattedDate(date) {

	var year = date.getFullYear();
	var month = (1 + date.getMonth()).toString();
	month = month.length > 1 ? month : '0' + month;
	var day = date.getDate().toString();
	day = day.length > 1 ? day : '0' + day;
	return month + '/' + day + '/' + year;
}

function getJsonString(name, value){
	if(name.indexOf(".") > -1){
		var items = name.split(".", 2);
		var jsonInner = getJsonString(items[1],value);
		var json = '"'+items[0]+ '":{'+jsonInner+'}';
		return json;
	}
	if(value.toLowerCase() == "true") return '"'+name+ '":' + true;
	if(value.toLowerCase() == "false") return '"'+name+ '":' + false;
	if($.trim(value) == '' || $.trim(value).toLowerCase() == 'null') return '';
	return '"'+name+ '":"'+value+'"';				
}
/*----------------- function for preparing json data ends ------------------*/

/*----------------- function to edit a row stars ------------------*/
function editRow ( oTable, nRow )
{
	startProgress();
	var aData = oTable.fnGetData(nRow);
	var jqTds = $('>td', nRow);
	for(var i=1; i<aData.length-2; i++) {
		jqTds[i].innerHTML = '<input type="text" value="'+aData[i]+'">';
	}
	jqTds[0].innerHTML = '<input type="text" value="'+aData[0]+'" readonly="readonly" onfocus="this.blur()">';		
	jqTds[i].innerHTML = '<a class="edit" href="">Save</a>';
	stopProgress();
}

function restoreRow ( oTable, nRow )
{
	var aData = oTable.fnGetData(nRow);
	var jqTds = $('>td', nRow);	
	for ( var i=0, iLen=jqTds.length ; i<iLen ; i++ ) {
		oTable.fnUpdate( aData[i], nRow, i, true );
	}	
	oTable.fnDraw();
}
/*----------------- function to edit a row ends ------------------*/

/*----------------- function to capitalize the first letter starts ------------------*/
function capitaliseFirstLetter(string)
{
    return string[0].toUpperCase() + string.slice(1);
}
/*----------------- function to capitalize the first letter ends ------------------*/

// To delete row from database
function deleteRow ( oTable,nRow, url, text, successMessage )
{
	 var id = $(nRow).attr('id');
	  noty({
 	      text: text,
 	      type: 'confirm',
 	      dismissQueue: false,
 	      layout: 'center',
 	      theme: 'defaultTheme',
 	      buttons: [
 	        {addClass: 'btn btn-primary', text: 'Ok', onClick: function($noty) {
 	        	//Changed to solve bug# 269 ****Start*****
 	        //    $noty.close();
 	        	  $.noty.closeAll();
 	        	 //****End*****//
 	           if (id == undefined || id == null || id == '') {
 	            	oTable.fnDeleteRow( nRow );
 	            	saveOpen = false;
 	            	nEditing = null;
 	            	$('.toasterBgDiv').remove();
 	                 return ;
 	            }
 	         //   noty({dismissQueue: true, force: true, layout: 'top', theme: 'defaultTheme', text: 'You clicked "Ok" button', type: 'success'});
 	            startProgress();
 	       //   e.preventDefault();
 	          	 $.deleteJson(url, {}, function(data){
					oTable.fnDeleteRow( nRow );
					stopProgress();
					showSuccess(successMessage);window.location.reload(true);
				},"json"); 
 	          	$('.toasterBgDiv').remove();
 	        }
 	        },
 	        {addClass: 'btn btn-danger', text: 'Cancel', onClick: function($noty) {
 	        	//Changed to solve bug# 269 ****Start*****
 	        	//  $noty.close();
 	              $.noty.closeAll();
 	             $('.toasterBgDiv').remove();
 	             //****End*****//
 	       //     noty({dismissQueue: true, force: true, layout: 'top', theme: 'defaultTheme', text: 'You clicked "Cancel" button', type: 'error'});
 	         }
 	        }
 	      ],
 	   closeWith:['Button']
 	    });
}
function showAlert(text)
{
	  noty({
 	      text: text,
 	      type: 'confirm',
 	      dismissQueue: false,
 	      layout: 'center',
 	      theme: 'defaultTheme',
 	      buttons: [
 	        {
 	        	addClass: 'btn btn-primary', text: 'Ok', onClick: function($noty) {
 	        	  $.noty.closeAll();
 	        	 $.unblockUI();
 	           $('.toasterBgDiv').remove();
 	        }
 	        },
 	        /*{addClass: 'btn btn-danger', text: 'Cancel', onClick: function($noty) {
 	              $.noty.closeAll();
 	             $('.toasterBgDiv').remove();
 	             }
 	        }*/
 	      ],
 	   closeWith:['Button']
 	    });
	}