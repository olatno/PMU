package scr.protool.client.miscelleneous;
import java.io.IOException;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.net.URL;
import javax.swing.ImageIcon;
import scr.protool.client.utilities.*;



public class QuotePdf1{

	
	private boolean addPage = false;
	private int listCounter = 0;
	private int tableCounter = 37;
	private int tableCounter1 = 55;
	private UserLogIn parent;
	private BeanStud bean;
	private Utilities ul;
	private ClassLoader cl = this.getClass().getClassLoader();
	private ArrayList<String> customer_details;
	private String quote;
	private ArrayList<ArrayList<String>> datas;
	private String[] quoteSummary;
	private String str;
  
	public QuotePdf1(ArrayList<Object> quoteInfo){//throws DocumentException, IOException
		bean = new BeanStud();
		ul = new Utilities();
		
		PdfWriter writer = null;
		if((String[])quoteInfo.get(0) != null){
			quoteSummary = (String[])quoteInfo.get(0);
		}
		if((ArrayList<ArrayList<String>>)quoteInfo.get(1) != null){
			datas = (ArrayList<ArrayList<String>>)quoteInfo.get(1);
		}
		if( (ArrayList<String>)quoteInfo.get(2) != null){
			customer_details = (ArrayList<String>)quoteInfo.get(2);
		}
		if(!datas.isEmpty()){
			quote = quoteSummary[2]+"_"+quoteSummary[1]+".pdf";
		}
		else{
			JOptionPane.showMessageDialog(parent, String.format("%s %s","Please add quote items once available.",

				"Full quotation will remain unavailable until then."),"", JOptionPane.INFORMATION_MESSAGE, ul.iconImage("images/src/gui_icon.png"));
		
		}
		
		JFileChooser saver = new JFileChooser();   
        File file = new File(quote);
		saver.setSelectedFile(file);
		int returnVal = saver.showSaveDialog(parent);	
		
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		String pathFile = saver.getCurrentDirectory() + "\\" + quote;
		try{
			if(returnVal == JFileChooser.APPROVE_OPTION){
			
			 writer = PdfWriter.getInstance(document, new FileOutputStream(pathFile));
			
				document.open();
			
				PdfContentByte canvas = writer.getDirectContent();
		
				createHearder(document, canvas);
			
				createFirstTable().writeSelectedRows(0, -1, 5, 560, canvas);
			
				if(datas.size() - 1 > listCounter){
					createSecoundTable(document, canvas);
			
				}
				else{
				
					createFooter(canvas);
				
				}
					JOptionPane.showMessageDialog(parent, "File Saved: " + pathFile,   
                    "Success!", JOptionPane.INFORMATION_MESSAGE, ul.iconImage("images/src/gui_icon.png")); 
			}
				/*JOptionPane.showMessageDialog(parent, "File Saved: " + pathFile,   
                        "Success!", JOptionPane.INFORMATION_MESSAGE); */

			}			
            catch (Exception e) { 
			JOptionPane.showMessageDialog(parent, "Quote PDF not generated!" +e.toString(),   
                        "Error!", JOptionPane.INFORMATION_MESSAGE, ul.iconImage("images/src/gui_icon.png"));
				
			}
				
					document.close();
	}
	
	public BaseColor hearderColor(){
	 	Integer intval = Integer.decode("#394C86");
		int i = intval.intValue();
		return new BaseColor((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
	 }
      public PdfPTable createFirstTable()throws DocumentException, IOException {
    	// a table with three columns
         PdfPTable table = new PdfPTable(6);
		table.setTotalWidth(585);
		table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);

		int colorhelper = 0;
        // the cell object
		Integer alter = Integer.decode("#F0F0F0");
		BaseColor alter_color = new BaseColor((alter.intValue() >> 16) & 0xFF, (alter.intValue() >> 8) & 0xFF, alter.intValue() & 0xFF);
		
		Font color = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.WHITE); 
		int rows = 36;
		table.getDefaultCell().setBackgroundColor(hearderColor());
		table.getDefaultCell().setMinimumHeight(10f);
		table.setWidths(new float[] {24.4f,296f,54.9f,54.9f,54.9f,54.9f});
		//table.getDefaultCell().setBorder(Rectangle.RIGHT);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		//table.getDefaultCell().enableBorderSide(Rectangle.LEFT);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(new Phrase("#", color));
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(new Phrase("Description", color));
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);		
		table.addCell(new Phrase("Unit Price", color));	
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);		
		table.addCell(new Phrase("Qty", color));	
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(new Phrase("Total", color));	
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);		
		table.addCell(new Phrase("VAT", color));

		table.setHeaderRows(1);
		//table.getDefaultCell().setBorder(Rectangle.RIGHT);
		//table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		//table.getDefaultCell().enableBorderSide(Rectangle.LEFT);
		Integer boader = Integer.decode("#616158");
		table.getDefaultCell().setBorderColor(new BaseColor((boader.intValue() >> 16) & 0xFF, (boader.intValue() >> 8) & 0xFF, boader.intValue() & 0xFF));
		//table.getDefaultCell().setBackgroundColor(null);
		table.getDefaultCell().setBackgroundColor(alter_color);
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		// table.deleteRow(1);
		//ArrayList<ArrayList<String>> datas = null;

		Font colordata = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.BLACK);

		int r = 0;
		do {
			
			if(datas.get(listCounter).get(0).length() > 75){
				ArrayList<String> strtruncate = listPhrase(datas.get(listCounter).get(0));
				for(int k = 0; k < strtruncate.size(); k++){
					if(k != strtruncate.size() - 1){
						if(k == 0){
							table.addCell(new Phrase(String.valueOf(listCounter+1), colordata));
						}
						else{
							table.addCell("");
						}
						table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
						table.addCell(new Phrase(strtruncate.get(k), colordata));
						table.addCell("");
						table.addCell("");		
						table.addCell("");		
						table.addCell("");
					}
					else{
						table.addCell("");
						table.addCell(new Phrase(strtruncate.get(k), colordata));
						table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(new Phrase(datas.get(listCounter).get(1), colordata));		
						table.addCell(new Phrase(datas.get(listCounter).get(2), colordata));		
						table.addCell(new Phrase(datas.get(listCounter).get(3), colordata));		
						table.addCell(new Phrase(datas.get(listCounter).get(4), colordata));
						//datas.remove(datas.get(r));
					}
					if((k + colorhelper) % 2 == 0 ){
						
						table.getDefaultCell().setBackgroundColor(null);
						
					}
					else{
						
						table.getDefaultCell().setBackgroundColor(alter_color);
					}
					colorhelper = k + colorhelper;
					if(table.size()  == tableCounter ){

						table.getDefaultCell().enableBorderSide(Rectangle.BOTTOM);

					}					
				}


			}
			else{
			
				table.addCell(new Phrase(String.valueOf(listCounter+1), colordata));
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				table.addCell(new Phrase(datas.get(listCounter).get(0), colordata));
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);				
				table.addCell(new Phrase(datas.get(listCounter).get(1), colordata));		
				table.addCell(new Phrase(datas.get(listCounter).get(2), colordata));		
				table.addCell(new Phrase(datas.get(listCounter).get(3), colordata));		
				table.addCell(new Phrase(datas.get(listCounter).get(4), colordata));
				//datas.remove(datas.get(r));
			}
			if(colorhelper++  % 2 == 0){

				table.getDefaultCell().setBackgroundColor(null);
			}
			else{
				table.getDefaultCell().setBackgroundColor(alter_color);
			}
			if(table.size()  == tableCounter ){
	
				//table.getDefaultCell().enableBorderSide(Rectangle.BOTTOM);
			
			}

			//datas.remove(datas.get(r));
			listCounter++;
		}while(listCounter < datas.size() && table.size() <= tableCounter);
		if(table.size()  == 36 && !datas.isEmpty()){
			addPage = true;
		}
		if(table.size() < tableCounter){
			for(int y = datas.size(); y < rows; y++){
				table.addCell("");
				table.addCell("");		
				table.addCell("");		
				table.addCell("");		
				table.addCell("");		
				table.addCell("");
				if(y % 2 == 0){

					table.getDefaultCell().setBackgroundColor(null);
				}
				else{
					table.getDefaultCell().setBackgroundColor(alter_color);
				}
				if(y == 34){
					//table.getDefaultCell().enableBorderSide(Rectangle.BOTTOM);
				}

			}
		
		}

		//table.setHeaderRows(2);
        return table;
    }
	
	public void createSecoundTable(Document document, PdfContentByte canvas)throws DocumentException, IOException {
    	// a table with three columns
		document.newPage();
		//tableCounter = tableCounter + 36;
        PdfPTable table = new PdfPTable(6);
		table.setTotalWidth(585);
		table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);

		int colorhelper = 0;
        // the cell object
		Integer alter = Integer.decode("#F0F0F0");
		BaseColor alter_color = new BaseColor((alter.intValue() >> 16) & 0xFF, (alter.intValue() >> 8) & 0xFF, alter.intValue() & 0xFF);
		
		Font color = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.WHITE); 
		int rows = 45;
		table.getDefaultCell().setBackgroundColor(hearderColor());
		table.getDefaultCell().setMinimumHeight(10f);
		table.setWidths(new float[] {24.4f,296f,54.9f,54.9f,54.9f,54.9f});
		//table.getDefaultCell().setBorder(Rectangle.RIGHT);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		//table.getDefaultCell().enableBorderSide(Rectangle.LEFT);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(new Phrase("#", color));
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(new Phrase("Description", color));
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);		
		table.addCell(new Phrase("Unit Price", color));	
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);		
		table.addCell(new Phrase("Qty", color));	
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(new Phrase("Total", color));	
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);		
		table.addCell(new Phrase("VAT", color));

		table.setHeaderRows(1);
		//table.getDefaultCell().setBorder(Rectangle.RIGHT);
		//table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		//table.getDefaultCell().enableBorderSide(Rectangle.LEFT);
		Integer boader = Integer.decode("#616158");
		table.getDefaultCell().setBorderColor(new BaseColor((boader.intValue() >> 16) & 0xFF, (boader.intValue() >> 8) & 0xFF, boader.intValue() & 0xFF));
		//table.getDefaultCell().setBackgroundColor(null);
		table.getDefaultCell().setBackgroundColor(alter_color);
		//table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

		Font colordata = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.BLACK);

		do{
			
			if(datas.get(listCounter).get(0).length() > 75){
				ArrayList<String> strtruncate = listPhrase(datas.get(listCounter).get(0));
				for(int k = 0; k < strtruncate.size(); k++){
					if(k != strtruncate.size() - 1){
						if(k == 0){
							table.addCell(new Phrase(String.valueOf(listCounter+1), colordata));
						}
						else{
							table.addCell("");
						}
						table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
						table.addCell(new Phrase(strtruncate.get(k), colordata));
						table.addCell("");
						table.addCell("");		
						table.addCell("");		
						table.addCell("");
					}
					else{
						table.addCell("");
						table.addCell(new Phrase(strtruncate.get(k), colordata));
						table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(new Phrase(datas.get(listCounter).get(1), colordata));		
						table.addCell(new Phrase(datas.get(listCounter).get(2), colordata));		
						table.addCell(new Phrase(datas.get(listCounter).get(3), colordata));		
						table.addCell(new Phrase(datas.get(listCounter).get(4), colordata));
						//datas.remove(datas.get(r));
					}
					if((k + colorhelper) % 2 == 0 ){
						
						table.getDefaultCell().setBackgroundColor(null);
						
					}
					else{
						
						table.getDefaultCell().setBackgroundColor(alter_color);
					}
					colorhelper = k + colorhelper;
					if(table.size()  == tableCounter1 ){

						//table.getDefaultCell().enableBorderSide(Rectangle.BOTTOM);

					}					
				}


			}
			else{
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(new Phrase(String.valueOf(listCounter+1), colordata));
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				table.addCell(new Phrase(datas.get(listCounter).get(0), colordata));
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);				
				table.addCell(new Phrase(datas.get(listCounter).get(1), colordata));		
				table.addCell(new Phrase(datas.get(listCounter).get(2), colordata));		
				table.addCell(new Phrase(datas.get(listCounter).get(3), colordata));		
				table.addCell(new Phrase(datas.get(listCounter).get(4), colordata));
				//datas.remove(datas.get(r));
			}
			if(colorhelper++  % 2 == 0){

				table.getDefaultCell().setBackgroundColor(null);
			}
			else{
				table.getDefaultCell().setBackgroundColor(alter_color);
			}
			if(table.size()  == tableCounter1 ){
	
				//table.getDefaultCell().enableBorderSide(Rectangle.BOTTOM);
			
			}

		listCounter++;	
	
		}while(listCounter < datas.size() && table.size() <= tableCounter1);
		if( table.size() < tableCounter1 ){
			for(int y = table.size()+1 ; y < tableCounter1; y++){
				table.addCell("");
				table.addCell("");		
				table.addCell("");		
				table.addCell("");		
				table.addCell("");		
				table.addCell("");
				if(y % 2 == 0){

					table.getDefaultCell().setBackgroundColor(null);
				}
				else{
					table.getDefaultCell().setBackgroundColor(alter_color);
				}
				if(y == tableCounter1 - 2){
				//	table.getDefaultCell().enableBorderSide(Rectangle.BOTTOM);
				}

			}
		
		}

        table.writeSelectedRows(0, -1, 5, 780, canvas);
		if(datas.size() - 1 > listCounter){
			createSecoundTable(document, canvas);
			System.out.println("In recursive" + datas.size());
		}

		else{
			createFooter(canvas);
			System.out.println("Not in recursive" + listCounter +""+ datas.size());
		}
    }
	
	public void createHearder(Document document, PdfContentByte canvas)throws DocumentException, IOException{

		int space = 0; int x_left = 5; int x_middle1 = 200; int x_middle2 = 260; int x_middle3 = 465; 
		int x_rightdata = 504; int x_rightrec = 470;
		
		URL imageURL = cl.getResource("1200110.jpeg");

		Image img = null;		
		try{
			if(imageURL != null){
				img = Image.getInstance(imageURL);
			}
		
		}
		catch(FileNotFoundException fx){}
		if(img != null){
			img.setAbsolutePosition(x_left, 770);
			img.scalePercent(250, 50);
			document.add(img);
			space = 27;
		}

		else{
			Paragraph comp_name = new Paragraph("J W Eletrical", new Font(FontFamily.HELVETICA, 23,Font.NORMAL, BaseColor.BLACK));
			ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, comp_name, x_left, 780, 0);
			space = 37;
		}
		Font main_font = new Font(FontFamily.HELVETICA, 10);
		Paragraph address = new Paragraph();
		if(img != null){

			Phrase com_name = new Phrase("J W Eletrical", main_font);
			ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, com_name, x_left, 720 + space, 0);
		}
		Phrase first_line = new Phrase("24 Spencer Road", main_font);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, first_line, x_left, 705 + space, 0);
		Phrase second_line = new Phrase("Langley Green", main_font);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, second_line, x_left, 693 + space, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, new Phrase("Crawley", main_font), x_left, 681 + space, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, new Phrase("West Sussex", main_font), x_left, 669 + space, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, new Phrase("RH11 7SN", main_font), x_left, 657 + space, 0);
		Phrase land_line = new Phrase("Telephone:", main_font);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, land_line, x_middle1, 720 + space, 0);
		Phrase mobile_line = new Phrase("Mobile:", main_font);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, mobile_line, x_middle1, 705 + space, 0);
		Phrase email = new Phrase("Email:", main_font);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, email, x_middle1, 693 + space, 0);
	
		Phrase web = new Phrase("Web Site:", main_font);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, web, x_middle1, 681 + space, 0);
		
		Phrase land_line1 = new Phrase("01293 452452", main_font);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, land_line1, x_middle2, 720 + space, 0);
		Phrase mobile_line1 = new Phrase("07885426541", main_font);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, mobile_line1, x_middle2, 705 + space, 0);
		Phrase email1 = new Phrase("j.w.eletrical@yahoo.com", main_font);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, email1, x_middle2, 693 + space, 0);
	
		Phrase web1 = new Phrase("www.jwelectrical.com", main_font);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, web1, x_middle2, 681 + space, 0);

	
		Paragraph quote = new Paragraph("QUOTE", new Font(FontFamily.HELVETICA, 23, Font.NORMAL, BaseColor.RED));
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, quote, 495, 780, 0);
	
	//customer details header
		canvas.saveState();
		canvas.setLineWidth(0.05f);
		canvas.rectangle(x_left, 660, 180, 10);
		canvas.setColorFill(hearderColor());
		canvas.fill();
		canvas.restoreState();
	
		//customer info
		canvas.saveState();
		BaseFont bf = BaseFont.createFont();
		canvas.beginText();
		canvas.setLineWidth(0.05f);
		canvas.setColorFill(BaseColor.WHITE);
		canvas.setFontAndSize(bf, 10);
		canvas.moveText(x_left, 661);
		canvas.showText("Customer");
		canvas.endText();
		canvas.restoreState();
	
		int add_one = 650;
		/*customer_details.add("Mr Solomon Smith");
		customer_details.add("Runtime Collection LTD");
		customer_details.add("2A Hamalton Avenue");
		customer_details.add("Redhill");
		customer_details.add("Surrey");
		customer_details.add("RH1 6SC");*/

		for(int j = 0; j < customer_details.size(); j++){
			Phrase customer_add = new Phrase(customer_details.get(j), main_font);
			ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, customer_add, x_left, add_one, 0);
			add_one-=12;
		}

		//quote informations descriptor
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT, 
			new Paragraph("Date :", new Font(FontFamily.HELVETICA, 10)), x_middle3, 747, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT, 
			new Paragraph("QUOTE :", new Font(FontFamily.HELVETICA, 10)), x_middle3, 727, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT, 
			new Paragraph("Customer Id :", new Font(FontFamily.HELVETICA, 10)), x_middle3, 707, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT,
			new Paragraph("Valid Until :", new Font(FontFamily.HELVETICA, 10)), x_middle3, 687, 0);
			
			//quote pdfptable information details
			Font quotefont = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.BLACK);
			PdfPTable quotebox = new PdfPTable(1);
			quotebox.setTotalWidth(110);
			quotebox.getDefaultCell().setMinimumHeight(20f);
			quotebox.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			quotebox.addCell(new Phrase(ul.usDateString(quoteSummary[0]), quotefont));
			quotebox.addCell(new Phrase("0000000"+quoteSummary[1], quotefont));
			quotebox.addCell(new Phrase("0000"+quoteSummary[2], quotefont));
			quotebox.addCell(new Phrase(ul.usDateString(quoteSummary[3]), quotefont));
			quotebox.writeSelectedRows(0, -1, 480, 760, canvas);


	
	}
	
	public void createFooter(PdfContentByte canvas)throws DocumentException, IOException{
		int vat_code = 346; int vat_rate = 416;
		int name = 510; int value = 588;
		LineSeparator sep =  new LineSeparator();
		//terms and condition header
		canvas.saveState();
		canvas.setLineWidth(0.05f);
		canvas.rectangle(5, 110, 246, 10);
		canvas.setColorFill(hearderColor());
		BaseFont bf = BaseFont.createFont();
		canvas.fill();
		canvas.restoreState();
	
		canvas.saveState();
		canvas.beginText();
		canvas.setLineWidth(0.05f);
		canvas.setColorFill(BaseColor.WHITE);
		canvas.setFontAndSize(bf, 10);
		canvas.moveText(5, 111);
		canvas.showText("TERMS AND CONDITIONS");
		canvas.endText();
		canvas.restoreState();
	
		//terms and conditions details
		canvas.saveState();
		canvas.setLineWidth(0.05f);
		canvas.rectangle(5, 10, 246, 100);
		canvas.stroke();
		canvas.restoreState();
		
		//vat analysis
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT, 
			new Paragraph("VAT Code", new Font(FontFamily.HELVETICA, 10)), vat_code, 110, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT, 
			new Paragraph("VAT Rate", new Font(FontFamily.HELVETICA, 10)), vat_rate, 110, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT, 
			new Paragraph("0", new Font(FontFamily.HELVETICA, 10)), vat_code, 90, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT,
			new Paragraph("0.00%", new Font(FontFamily.HELVETICA, 10)), vat_rate, 90, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT,	
			new Paragraph("1", new Font(FontFamily.HELVETICA, 10)), vat_code, 70, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT,
			new Paragraph("20.00%", new Font(FontFamily.HELVETICA, 10)), vat_rate, 70, 0);
			
		//quote table summary name
		canvas.saveState();
		canvas.setLineWidth(0.05f);
		canvas.rectangle(471, 60, 59.5f, 60);
		canvas.stroke();
		canvas.restoreState();
		
		//quote summary names
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT, 
			new Paragraph("Net :", new Font(FontFamily.HELVETICA, 10)), name, 105, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT, 
			new Paragraph("VAT :", new Font(FontFamily.HELVETICA, 10)), name, 85, 0);
		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT, 
			new Paragraph("Total :", new Font(FontFamily.HELVETICA, 10)), name, 65, 0);
		
		//amount pdfptable information details
		Font amountfont = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.BLACK);
		PdfPTable amountbox = new PdfPTable(1);
		amountbox.setTotalWidth(59.5f);
		amountbox.getDefaultCell().setMinimumHeight(20f);
		amountbox.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		amountbox.getDefaultCell().disableBorderSide(Rectangle.LEFT);
		amountbox.addCell(new Phrase("£500096.69", amountfont));
		amountbox.addCell(new Phrase("£100078.14", amountfont));
		amountbox.addCell(new Phrase("£600039.83", amountfont));
		
		amountbox.writeSelectedRows(0, -1, 531, 120, canvas);
		
		
	
	}
	
	public void tableLayout(PdfPTable table, float[][] widths, float[] heights,
        int headerRows, int rowStart, PdfContentByte[] canvases) {
        int columns;
        Rectangle rect;
        int footer = widths.length - table.getFooterRows();
        int header = table.getHeaderRows() - table.getFooterRows() + 1;
        for (int row = header; row < footer; row += 2) {
            columns = widths[row].length - 1;
            rect = new Rectangle(widths[row][0], heights[row],
                        widths[row][columns], heights[row + 1]);
            rect.setBackgroundColor(BaseColor.YELLOW);
            rect.setBorder(Rectangle.NO_BORDER);
            canvases[PdfPTable.BASECANVAS].rectangle(rect);
        }
    }
	
	/*public ArrayList<ArrayList<String>> quoteDatas(){
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		try{
			BufferedReader in = new BufferedReader(new FileReader("C:/Users/Ola/Desktop/itext/dataset2.txt"));
			String str = null;
			while((str = in.readLine()) != null){
				ArrayList<String> temp = new ArrayList<String>();
				String [] lineArray = str.split(";");
				for(int i = 0; i < lineArray.length; i++){
					temp.add(lineArray[i]);
				}
				data.add(temp);
			}
		}
		catch(IOException e){}
		return data;
	}*/
	

		public ArrayList<String> listPhrase(String truncate){
			ArrayList<String> data = new ArrayList<String>();
			int srtchunk = 74;
			int initialValue = 74;
			int offset = 0;
			int count = 3;
			int subIndex = 0;
			//initialised counter with possible number of loop in string chunck
			int counter = (truncate.length() % srtchunk == 0) ? (truncate.length()/initialValue) : ((truncate.length()/initialValue) + 1);
			for(int i = 0; i < counter; i++){
				if(srtchunk == truncate.length()-1){//end of the string	
					data.add(truncate.substring(offset));
				}
				else{
					if(truncate.charAt(srtchunk)  != ' '){
						//possition of string chunk to be added to list
						srtchunk = truncate.lastIndexOf(' ', srtchunk);
						data.add(truncate.substring(offset, srtchunk));
						offset = srtchunk + 1;
						//update string chunk with new position in the whole string
						srtchunk = (truncate.length() - srtchunk + 1 > initialValue) ? (srtchunk+initialValue) : (truncate.length() - 1);
					}
					else{
						data.add(truncate.substring(offset, srtchunk));
						offset = srtchunk + 1;
							//update string chunk with new position in the whole string
						srtchunk = (truncate.length() - srtchunk + 1 > initialValue) ? (srtchunk+initialValue) : (truncate.length() - 1); 
					}

				}
			}
			return data;
		}

}
  
