$(document).ready(function () {
	$('.searchInp').keyup(function(event) {
		var search_text = $(this).val();
		var rg = new RegExp(search_text,'i');
		
		$(this).parent().parent('.search_body').find($('#skillsList .product-list .product')).each(function(){
 			if($.trim($(this).html()).search(rg) == -1) {
				$(this).parent().parent().css('display', 'none');
 				$(this).css('display', 'none');
				$(this).next().css('display', 'none');
				$(this).next().next().css('display', 'none');
			}	
			else {
				$(this).parent().parent().css('display', '');
				$(this).css('display', '');
				$(this).next().css('display', '');
				$(this).next().next().css('display', '');
			}
		});
	});
	$('.searchClearIcon').click(function() {
		$(this).parent().find($('.searchInp')).val('');	
	 
		$(this).parent().parent('.search_body').find($('#skillsList .product-list .product')).each(function(){
			$(this).parent().parent().css('display', '');
			$(this).css('display', '');
			$(this).next().css('display', '');
			$(this).next().next().css('display', '');
		});
	});
	$('.see_seleted').click(function(){
		$('.popUp').show();
	});
	$('.closeAccordianPopUp').click(function(){
		$('.popUp').hide();
	});
		
	/*$(".search_list .search_body:gt(0)").hide();
	$('.search_body:eq(0)').show();
	$('h3.search_head').click(function() {
		if(false == $(this).parent("li").find(".search_body").is(':visible')) {
			$('.search_body').slideUp(300);
			$('.searchClearIcon').click();
		}
		$(this).parent("li").find(".search_body").slideToggle(300);
	});*/
	$('h3.search_head').click(function() {
		$(this).parent("li").find(".search_body").slideToggle(300);
	});
});