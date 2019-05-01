package io.mosip.core.api.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import io.mosip.core.api.model.Resident;

public class GenerateResidentReport 
{
	
	public static ByteArrayInputStream residentReport(Resident res) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	

		try {

			Paragraph header = new Paragraph(new Chunk("Resident Report",FontFactory.getFont(FontFactory.HELVETICA, 30)));
			Paragraph uin = new Paragraph(new Chunk("UIN : " + res.getUin() ,FontFactory.getFont(FontFactory.HELVETICA, 20)));
			Paragraph name = new Paragraph(new Chunk("Name : " + res.getName() ,FontFactory.getFont(FontFactory.HELVETICA, 20)));
			Paragraph fname = new Paragraph(new Chunk("Father Name : " + res.getFather_name() ,FontFactory.getFont(FontFactory.HELVETICA, 20)));
			Paragraph dob = new Paragraph(new Chunk("DOB : " + res.getDob() ,FontFactory.getFont(FontFactory.HELVETICA, 20)));
			Paragraph address = new Paragraph(new Chunk("Address : " + res.getHouse_no() + " ," + res.getStreet() + " ," + res.getCity() 
			                                       + " ," + res.getDist() + ", " + res.getState() + " ," + res.getCountry() + " "
			                                       ,FontFactory.getFont(FontFactory.HELVETICA, 20)));

			
			PdfWriter.getInstance(document, out);
			document.open();
			document.add(header);
		    document.add(uin);
		    document.add(name);
		    document.add(fname);
		    document.add(dob);
		    document.add(address);

			document.close();

		} catch (DocumentException ex) {

			Logger.getLogger(GenerateResidentReport.class.getName()).log(Level.SEVERE, null, ex);
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

}
