/*-----------------function for container width---------------------*/
function containerWidth( datatableId){
		var wWidth =  $(datatableId).width();
		if($(datatableId).is(":visible")==true){
			$('.container').css('width', wWidth+262);
		}
		else 
			$('.container').css('width',100+"%" );	
}
/*-----------------function for container width end------------------*/