function initDataTable(tableid, displayFilter, displayShowHideColumns, canBeDestroyed, toolbarHTML){
	
	displayFilter = typeof displayFilter !== 'undefined' ? displayFilter : true;
	displayShowHideColumns = typeof displayShowHideColumns !== 'undefined' ? displayShowHideColumns : true;
	canBeDestroyed = typeof canBeDestroyed !== 'undefined' ? canBeDestroyed : true;
	toolbar = typeof toolbar !== 'undefined' ? toolbar : '';
	
	if(displayFilter){
		$("thead input").keyup( function () {
			oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex(oTable.fnSettings(), $("thead input").index(this) ) );
		});
		$("thead input").each( function (i) {
			this.initVal = this.value;
		});
		$("thead input").focus( function () {
			if ( this.className == "search_init" ){
				this.className = "";
				this.value = "";
			}
		});
		$("thead input").blur( function (i) {
			if ( this.value == "" ){
				this.className = "search_init";
				this.value = this.initVal;
			}
		});
		oTable = $(table).dataTable( {
			"sDom": '<"datatabletoolbar">RC<"clear">lfrtip',
			"bDestroy": canBeDestroyed,
			"aoColumnDefs": [
				{ "bVisible": false, "aTargets": [] }
			],
			"oLanguage": {
				"sSearch": "Search:"
			},
			"bSortCellsTop": true
		} );
		
		$("div.datatabletoolbar").html(toolbar);
		
	}
	return oTable;
}