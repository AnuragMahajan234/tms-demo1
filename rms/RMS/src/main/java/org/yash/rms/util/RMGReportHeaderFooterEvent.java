package org.yash.rms.util;

import java.awt.Color;
import java.io.IOException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class RMGReportHeaderFooterEvent extends PdfPageEventHelper {

	/** The template with the total number of pages. */
	protected PdfTemplate total;

	protected String reportPeriod;
	protected String reportId;
	protected Image companyLogo;
	protected Font regularFont;
	protected Font boldFont;

	public RMGReportHeaderFooterEvent() {
		try {
			FontFactory.register("/fonts/Calibri_Bold.ttf", "Calibri_Bold_Font");
			boldFont = FontFactory.getFont("Calibri_Bold_Font", 10, new BaseColor(Color.decode("#2d2d2d")));

			FontFactory.register("/fonts/Calibri_Regular.ttf", "Calibri_Regular_Font");
			regularFont = FontFactory.getFont("Calibri_Regular_Font", 10, new BaseColor(Color.decode("#333333")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RMGReportHeaderFooterEvent(String reportPeriod, String reportId, Image companyLogo)
			throws DocumentException, IOException {
		this();
		this.reportPeriod = reportPeriod;
		this.reportId = reportId;
		this.companyLogo = companyLogo;
	}

	/**
	 * Creates the PdfTemplate that will hold the total number of pages.
	 * 
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onOpenDocument(PdfWriter writer, Document document) {
		total = writer.getDirectContent().createTemplate(30, 16);
	}

	/**
	 * Adds a header and footer to every page
	 * 
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onEndPage(PdfWriter writer, Document document) {

		/* Below header table used to add header to every page */
		try {
			PdfPTable header = new PdfPTable(2);
			header.setTotalWidth(500);
			header.setLockedWidth(true);

			PdfPCell cell = null;
			Phrase dataValue = null;

			cell = new PdfPCell();
			cell.addElement(companyLogo);
			cell.setPaddingBottom((float) 2.0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(0);
			cell.setBorderWidthBottom(.5f);
			cell.setBorderColorBottom(new BaseColor(Color.BLACK));
			cell.setRowspan(2);
			header.addCell(cell);
			header.setWidthPercentage(100.0f);

			cell = new PdfPCell();
			dataValue = new Phrase();
			dataValue.setFont(regularFont);
			dataValue.add(reportPeriod);
			cell.setPhrase(dataValue);
			cell.setPaddingBottom((float) 2.0);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(Rectangle.NO_BORDER);
			header.addCell(cell);

			cell = new PdfPCell();
			dataValue = new Phrase();
			dataValue.setFont(boldFont);
			dataValue.add(reportId);
			cell.setPhrase(dataValue);
			cell.setPaddingBottom(10f);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorder(0);
			cell.setBorderWidthBottom(.5f);
			cell.setBorderColorBottom(new BaseColor(Color.BLACK));
			header.addCell(cell);

			ColumnText column = new ColumnText(writer.getDirectContent());
			column.addElement(header);
			column.setSimpleColumn(-12, -20, 607, 830); // set LLx, LLy, URx,URy

			column.go();
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		/* Below footer table used to add footer to every page */

		try {
			PdfPTable footer = new PdfPTable(2);
			footer.setTotalWidth(500f);
			footer.setWidths(new float[]{19.8f,0.2f});
			footer.setLockedWidth(true);

			PdfPCell cell = new PdfPCell();
			cell.setFixedHeight(20f);
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPhrase(new Phrase(String.format("Page %d of", writer.getPageNumber()), regularFont));
			footer.addCell(cell);

			cell = new PdfPCell(Image.getInstance(total));
			cell.setBorder(0);
			cell.setFixedHeight(20f);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			footer.addCell(cell);
			
			ColumnText column = new ColumnText(writer.getDirectContent());
			column.addElement(footer);
			column.setSimpleColumn(-12, -20, 607, document.bottomMargin()); // set LLx, LLy, URx,URy
			column.go();
			
			/*footer.writeSelectedRows(-12, -20, 607, document.bottomMargin() - 5,
					writer.getDirectContent());*/
			
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Fills out the total number of pages before the document is closed.
	 * 
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onCloseDocument(PdfWriter pdfWriter, Document document) {
		ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(pdfWriter.getPageNumber() - 1),regularFont),2, 3, 0);
	}

}
