package org.yash.rms.util;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class RMGReportHaederFooterEvent extends PdfPageEventHelper{
	
	protected String reportPeriod;
	protected Font font;
	
	public RMGReportHaederFooterEvent(String reportPeriod, Font font) throws DocumentException, IOException {
		  this.reportPeriod = reportPeriod;
		  this.font = font;
		 }
	
	public void onEndPage(PdfWriter writer, Document document) {

		try {
			PdfPTable table = new PdfPTable(2);
			table.setTotalWidth(500);
		     table.setLockedWidth(true);
		     
		     PdfPCell cell = null;		 	
			 Phrase dataValue = null;
			 
			 cell = new PdfPCell();
			 dataValue = new Phrase();
			 dataValue.setFont(font);
		     dataValue.add(reportPeriod);
		     cell.setPaddingBottom((float) 2.0);
		     cell.setPhrase(dataValue);
		     cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		     cell.setPaddingTop((float) 2.0);
		     cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		     cell.setBorder(Rectangle.NO_BORDER);
		     table.addCell(cell);
		     
		     cell = new PdfPCell();
			 dataValue = new Phrase();
			 dataValue.setFont(font);
		     dataValue.add(String.format("Page %d", writer.getCurrentPageNumber()));
		     cell.setPaddingBottom((float) 2.0);
		     cell.setPhrase(dataValue);
		     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		     cell.setPaddingTop((float) 2.0);
		     cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		     cell.setBorder(Rectangle.NO_BORDER);
		     table.addCell(cell);
		     table.setWidthPercentage(100.0f);
			ColumnText column = new ColumnText(writer.getDirectContent());
			column.addElement(table);
			column.setSimpleColumn(-12, -20, 604, 803); // set LLx, LLy, URx,
			column.go();

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	 }

}
