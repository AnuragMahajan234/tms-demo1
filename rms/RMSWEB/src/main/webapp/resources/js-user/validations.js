/*-------- START - function to validate the inputs starts ----------------*/
function validateInputs(tableClass){
	var flag = true;
	var flag1=true;
 	$(tableClass+" tbody").find("input[type=text]").each(function(){
 		if (!this.readOnly) {	
 			var dataTblInputVal = $(this).val();
 			dataTblInputVal=dataTblInputVal.trim();
 			if(dataTblInputVal == ""){
 				flag = false;
 				$(this).css("border", "1px solid #ff0000");
 			}
 			else		{				
 				$(this).css("border", "1px solid #D5D5D5");	
 			}
 			if(dataTblInputVal.length>50){
 	 			flag=false;
 	 			flag1=false;
 	 		}
					 
		}
 		
 	});
 	
 	//validation for numeric check
	$(tableClass+" tbody").find("input.maxVal").each(function(){
		var valueNew = $(this).val();
		var test = Number(valueNew);
		 if(test==valueNew){ 
			 $(this).css("border", "1px solid #D5D5D5");	
		  }else  
		  {    
				// alert("Please Enter only Numeric Value");  
				 var errorMsg ="Please Enter only Numeric Value"; 
			  	    showError(errorMsg);
				// flag = false;
					$(this).css("border", "1px solid #ff0000");
		  }
 	});
 	
	if(flag == false){
		if(flag1==false){
			var errorMsg ="Please enter values less than 50 characters"; 
	  		showError(errorMsg); 
		}else{
		var errorMsg ="Please enter the Required values"; 
  		showError(errorMsg);  
		}
	}
	return flag;
}


/*function validateLength(tableClass){
	var flag1 = true;
	alert("Hello");
	$(tableClass+"tbody").find("input[type=text]").each(function(){
		if(!this.readOnly){
			var dataTbInputVal = $(this).val();
			alert("dataTbInputVal" + dataTbInputVal);
			
				//alert("Hello");
				if(dataTbInputVal.length > 10){
						flag1 = false;
				}
			
		}
	});
	
	
	
	return flag1;
}*/
/*--------- END - function to validate the inputs ends --------------------*/

/*------- START - function to validate the start and end dates ------------*/
function validDates(fromDate, toDate) {
	var SDate = fromDate;
	var EDate = toDate;
	var endDate = new Date(EDate);
	var startDate = new Date(SDate);
	if(SDate != '' && EDate != '' && startDate >= endDate) 
    	return false;	    	
	return true;
	}
/*-------- END - function to validate the start and end dates ---------------*/

/*------- START - function to validate the Email domain and Email Id ------------*/
function validateEmail(emailValue) {
	
	if(emailValue != '') {
    	/*var emailDomain = emailValue.split('@').pop();//alert(emailDomain);
    	if(emailDomain == null )//"yash.com".toUpperCase()  ----.toUpperCase()
    		return false;*/
	
		 var lastAtPos = emailValue.lastIndexOf('@');
		 var emailDomain=emailValue.split('@').pop();
		  if(lastAtPos>=0 && emailDomain=='yash.com' )
			  {
			  return true;
			  }
		  
    }

    return false;
 }
// Added for task # 292 - Start
function validateEmailAddress(emailValue)
{
	var regxFirstSpecialChar=/[!$%^&*()+|~=`\\#{}\[\]:";'<>?,\/]/;
    var regxSpecialChar=/[@_.-]/;
    var regxAlphanumeric=/^[ A-Za-z0-9_@.-]*$/;
    var numricRegx=/^[0-9]/;
     if(emailValue != ''){
     if(!(regxFirstSpecialChar.test(emailValue.charAt(0)))&& !(regxSpecialChar.test(emailValue.charAt(0))) && (regxAlphanumeric.test(emailValue))&&!(numricRegx.test(emailValue.charAt(0))))
	return true;
    }
    return false;
	}

/*function validateEmailNitin(emailValue)  
{  alert("asasasasasa: "+emailValue);
var atposition=emailValue.indexOf("@");  
var dotposition=emailValue.lastIndexOf(".");  
if (atposition<1 || dotposition<atposition+2 || dotposition+2>=emailValue.length){  
  alert("Please enter a valid e-mail address \n atpostion:"+atposition+"\n dotposition:"+dotposition);  
  return false;  
  } 
return true;
}*/  

function validateEmailForSplCharacters(emailStr) {
	// checks if the e-mail address is valid
	
	var emailPat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	//var emailPat = /^(\".*\"|[A-Za-z]\w*)@(\[\d{1,3}(\.\d{1,3}){3}]|[A-Za-z]\w*(\.[A-Za-z]\w*)+)$/;
	var matchArray = emailStr.match(emailPat);
	if (matchArray == null) {
	return false;
	}
	return true;
}
function validateEailUserName(emailUserName)
{
	if(emailUserName != '') {
		 var lastAtPos = emailUserName.lastIndexOf('@');
		if(lastAtPos<=0)
			{
			return false;
			}
	}
	return true;

}

function validateEmailSpecialCharacter(email)
{
	if(email!='')
		{
		var specialChar='@';
		/*if(email.contains(specialChar))*/
		// Added by Neha for getting error on IE because of .contains
		if(email.indexOf(specialChar) > -1)
			{
			return true;
			}
		}
	return false;
	}
//Added for task # 292 - End
/*-------- END - function to validate the Email domain and Email Id ---------------*/

/*-------- START - function to validate the Duplicate data on Master Data screen ----------------*/
function validateDuplicates(tableId, screenId, columnId) {
	var inputArray = new Array();
	var labelArray = new Array();
	var rowCount = $('#' + tableId + ' >tbody >tr').length;
	var flag = true;
	var tdInpVal = '';
	var inpSelect = $.trim(($('#' + tableId + ' >tbody >tr').find("td:nth-child("+columnId+") input")).val());
	var labelSelect = $('#' + tableId + ' >tbody >tr').find("td:nth-child("+columnId+")");
	$(labelSelect).each(function() {
		if ($(this).text() != '') {
			tdInpVal = $.trim($(this).text());
			labelArray.push(tdInpVal);
		} else {
			inputArray.push(inpSelect);
		}
	});
	if (rowCount > 1) {
		for ( var i = 0; i < inputArray.length; i++) {
			var arrlen = labelArray.length;
			for ( var j = 0; j < arrlen; j++) {
				if (inputArray[i].toUpperCase() == labelArray[j].toUpperCase()) {
					flag = false;
					break;
				}
			}
		}
		if (flag == false) {			
			var errorMsg = screenId +" \"" + inpSelect +"\" is already present. Please provide different " +screenId;
			showError(errorMsg);
		}
	}
	return flag;
}
/*-------- END - function to validate the Duplicate data on Master Data screen ----------------*/

/*------- START - function to validate the First Name, Middle Name and Last Name ------------*/

// Added for task # 309 - Start
function validateName(str) {
	//var regxValue= /^[a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]*$/;                     /*/[^a-zA-Z0-9]/;*/
	var name=$.trim(str);
	var regx=  /^[a-zA-Z\s]+$/; 
	//var oneDot= /[a-zA-Z]+\.[a-zA-Z]+/;
	//var oneDotNumeric= /[a-zA-Z]+\.[a-zA-Z0-9]+/;
	var oneDot= /^[a-zA-Z\.]+$/;
	if(name != '') {	
			if(oneDot.test(name)||regx.test(name))
			{
			return true;
			}
    } 		    
    return false;
 }
/*-------- END - function to validate the First Name  Middle Name and Last Name ---------------*/

/*------- START - function to validate the Contact Number 1 and Contact Number 2  ------------*/
function validateContactNumber(number)
{
	var regExp = /^((\+)?[1-9]{1,2})?([-\s])?((\(\d{1,4}\))|\d{1,4})(([-\s])?[0-9]{1,12}){1,2}$/;
 if(number!=null)
	 {
	 if(regExp.test(number))
		 {
		
		 return true;
		 }
	 }
      return false;  
	}
/*-------- END - function to validate the Contact Number 1 and Contact Number 2  ---------------*/
//Added for task # 309 - End

function validDatesForBrowserCompatible(fromDate, toDate) {
	var SDate='';
	var startDate='';
	var EDate='';
	var endDate='';
	
	if(toDate != "" && toDate != null){
		 var substring = "-";
		 var string = toDate.toString();
		 if(string.indexOf(substring) !== -1)
			 {
			  var dateSplit = toDate.split("-"); 
			  dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
			  EDate = dateObjendDate;
			 }else{			
				 EDate = toDate;	
			 }	
		
		/*var dateSplit = toDate.split("-"); 
		dateObjendDate = new Date(dateSplit[1] + " " + dateSplit[0] + ", " + dateSplit[2]);
	    EDate = dateObjendDate;*/
	    endDate = new Date(EDate);endDate.setHours(0, 0, 0, 0);
	}
	
	if(fromDate != ""){	
		 var substring = "-";
		 var string = fromDate.toString();
		 if(string.indexOf(substring) !== -1)
			 {
			 var dateSplit1 = fromDate.split("-"); 
			 var dateObjfromDate = new Date(dateSplit1[1] + " " + dateSplit1[0] + ", " + dateSplit1[2]);
			 SDate = dateObjfromDate;
			 }else{			
		 	SDate = fromDate;	
			 }
		// SDate = fromDate;	
		 startDate = new Date(SDate);startDate.setHours(0, 0, 0, 0); 
	}
	
	if(SDate != '' && EDate != '' && startDate>endDate) 
		return false;	   	
		return true;
}

function dateConverToNewDateBrowserCompatible(fromDate) {
	if(fromDate != "" && fromDate != null){	
		 var substring = "-";
		 var string = fromDate.toString();
		 if(string.indexOf(substring) !== -1)
			 {
			 var dateSplit1 = fromDate.split("-"); 
			 var dateObjfromDate = new Date(dateSplit1[1] + " " + dateSplit1[0] + ", " + dateSplit1[2]);
			 SDate = dateObjfromDate;
			 }else{			
		 	SDate = fromDate;	
			 }
		  return new Date(SDate);startDate.setHours(0, 0, 0, 0); 
	}
	else{
		return null;
	}
}

function basicValidationsNullTypeEmpty(experience){
	if(typeof experience != 'undefined' && experience != null && experience != ""){
		return true;
	}
	return false;
}
