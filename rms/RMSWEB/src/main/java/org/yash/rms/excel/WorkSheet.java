package org.yash.rms.excel;
import java.util.ArrayList;
import java.util.List;

public class WorkSheet {
	
	private List<Row> rowList;

	public WorkSheet(){
		this.rowList = new ArrayList<Row>();
	}
	
	public List<Row> getRows() {
		return this.rowList;
	}

	public Row getRow(int rowIndex){
		
		return this.rowList.get(rowIndex);
	}
	
	public int getLastRowNum(){
		
		return rowList.size()-1;
	}

}
