function spclCharacterValidationForLocation(name) {
	if (/^[a-zA-Z0-9- ]*$/.test(name) == false) {
		return true;
		stopProgress();
	}
}

function colorCoding(selfDataValue, rmsID,infoID ){
	var color = "";
	if((typeof rmsID!="undefined" && rmsID!="NA" && rmsID!='')){
		if(rmsID==infoID && selfDataValue!="NA"){
			color="green";
		}else if(rmsID!=infoID){
			// no need to check  && selfDataValue!="NA" 
			color = "red"
		}
	}			
	return color;
}
function displayValueOfRmsField(selfDataValue, selfId){
 var displayValue = "";
 if((typeof selfId!="undefined" && selfId!="NA" && selfId!=''&& selfId!=' ')){
	 if(selfDataValue!="NA"){
		 displayValue ="BOTH";
	 }else{
		 displayValue = "ID";
	 }
 }else{
		 displayValue = "NA";
 	}
return displayValue;
}

function regExForMsgTxtFailed(txt) {
	if (/^[a-zA-Z0-9-_!@#$%^&*()~\,\.\;\'\:\"\{\}\[\]\<\>\?\|\/\`\~\=\\\+ ]+$/.test(txt) == false) {
		return true;
	}else{
		return false;
	}
}