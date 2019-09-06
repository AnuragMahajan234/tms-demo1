package org.yash.rms.util;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper { 
	
		protected PdfPTable table;
		protected float tableHeight;
		
	 public HeaderFooterPageEvent(PdfPTable table) throws DocumentException, IOException {
	  
	  this.table = table;
	     
	     tableHeight = table.getTotalHeight();
	 }
	 
	 public float getTableHeight() {
	  
	     return tableHeight;
	 }
	 
	public void onEndPage(PdfWriter writer, Document document) {

		try {

			ColumnText column = new ColumnText(writer.getDirectContent());
			column.addElement(table);
			column.setSimpleColumn(-12, -20, 604, 803); // set LLx, LLy, URx,
			column.go();

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	 }
	
}