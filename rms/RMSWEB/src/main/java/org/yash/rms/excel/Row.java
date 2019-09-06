package org.yash.rms.excel;
import java.util.HashMap;
import java.util.Map;

public class Row {

	Map<Integer, Cell> cellMap;

	public Row(){
		this.cellMap = new HashMap<Integer, Cell>();
	}
	
	public Map<Integer, Cell> getCellMap() {
		return cellMap;
	}
		
	public Cell getCell(int cellIndex){
		return cellMap.get(cellIndex);
	}
	
}
