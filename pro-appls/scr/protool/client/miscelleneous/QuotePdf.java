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
import com.itextpdf.text.pdf.PdfPRow;
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
import java.text.NumberFormat;
import java.math.*;
import java.util.Locale;
import java.text.ParsePosition;
import scr.protool.client.utilities.*;

public class QuotePdf{

	private ArrayList<ArrayList<String>> datas;
	private boolean addPage = false;
	private int listCounter = 0;
	private int tableCounter = 35;  //37
	private int tableCounter1 = 51;
	private int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	private final int DECIMALS = 2;
	private UserLogIn parent;
	private Utilities ul;
	
  //java.util.List<String> customer_details = new ArrayList<String>();
  private ArrayList<String> customer_details;
  //java.util.List<String> site_details = new ArrayList<String>();
  private ArrayList<String> site_details;
  private String[] quoteSummary;
  
  //Quote global variable
  java.util.List<String> client_details1 = new ArrayList<String>();
  java.util.List<String> client_details2 = new ArrayList<String>();
  java.util.List<String> client_details3 = new ArrayList<String>();
  
  private String quote;
  private PdfPTable tableTwo;
  private PdfPTable tableOne;
  private Locale locale = Locale.UK;
  private ArrayList<PdfPTable> documentTable = new ArrayList<PdfPTable>();
  
  private Font bigFont;
  private Font smallFont;
  private Font invoiceFont;
  private Font regFont;
  private Font invoiceTotal;
  private Font logoFont;
  
	public QuotePdf(ArrayList<Object> quoteInfo){//throws DocumentException, IOException georgia
		
		ul = new Utilities();
	
		FontFactory.register("font/src/Walkway_UltraBold.ttf", "my_workway");
		Font myWalkWay = FontFactory.getFont("my_workway");
		BaseFont bfworkway = myWalkWay.getBaseFont();
		logoFont = new Font(bfworkway , 20);
		
	    FontFactory.register("font/src/tahoma.ttf", "my_tahoma");
		Font myTahoma = FontFactory.getFont("my_tahoma");
		BaseFont bft =  myTahoma.getBaseFont();
		invoiceFont = new Font(bft, 15, Font.NORMAL, BaseColor.BLACK);
		
	    FontFactory.register("font/src/calibri.ttf", "my_calibri");
		Font myCalibri = FontFactory.getFont("my_calibri");
		BaseFont bfc = myCalibri.getBaseFont();
	    bigFont = new Font(bfc , 10, Font.NORMAL, BaseColor.BLACK);
		smallFont = new Font(bfc , 8, Font.NORMAL, BaseColor.BLACK);
		invoiceTotal = new Font(bfc , 8, Font.BOLD, BaseColor.BLACK);
		regFont = new Font(bfc , 6, Font.NORMAL, BaseColor.BLACK);
		
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
		if( (ArrayList<String>)quoteInfo.get(3) != null){
			site_details = (ArrayList<String>)quoteInfo.get(3);
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
		int returnVal = saver.showSaveDialog(null);	
		
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		String pathFile = saver.getCurrentDirectory() + "\\" + quote;
		try{
			if(returnVal == JFileChooser.APPROVE_OPTION){
			File files = saver.getSelectedFile().getCanonicalFile();
			writer = PdfWriter.getInstance(document, new FileOutputStream(files));
		
			document.open();
	
			PdfContentByte canvas = writer.getDirectContent();
		
			createHearder(document, canvas);
		
			createFirstTable().writeSelectedRows(0, -1, 15, 560, canvas);
			
			if(datas.size() - 1 > listCounter){
				createSecoundTable(document, canvas);
			}
			
			
			else{
				createFooter(canvas);
			}
			document.close();
			JOptionPane.showMessageDialog(null, "File Saved: " + pathFile,   
                        "Success!", JOptionPane.INFORMATION_MESSAGE, ul.iconImage("images/src/gui_icon.png"));
			}						
		    }   
            catch (Exception e)   
            {   
           // JOptionPane.showMessageDialog(null, "Quote PDF not generated!", "Error!", JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(null, "Quote PDF not generated!" +e.toString(), 
			"Error!", JOptionPane.INFORMATION_MESSAGE, ul.iconImage("images/src/gui_icon.png"));			
         }  
	}
	
	public BaseColor hearderColor(){
	 	Integer intval = Integer.decode("#394C86");
		int i = intval.intValue();
		return new BaseColor((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
	 }
      public PdfPTable createFirstTable()throws DocumentException, IOException {
    	// a table with three columns
        tableOne = new PdfPTable(5);
		tableOne.setTotalWidth(565);
		tableOne.getDefaultCell().setUseAscender(true);
        tableOne.getDefaultCell().setUseDescender(true);

		int colorhelper = 0;
        // the cell object
		documentTable.add(tableOne);
		Integer alter = Integer.decode("#F0F0F0");
		BaseColor alter_color = new BaseColor((alter.intValue() >> 16) & 0xFF, (alter.intValue() >> 8) & 0xFF, alter.intValue() & 0xFF);
		
		Font color = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.WHITE); 
		int rows = 33;
		tableOne.getDefaultCell().setMinimumHeight(10f);
		tableOne.setWidths(new float[] {40.4f,296f,50.9f,50.9f,50.9f});

		tableOne.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		tableOne.addCell(new Phrase("#", bigFont));
		tableOne.addCell(new Phrase("Description", bigFont));
		tableOne.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);	
		tableOne.addCell(new Phrase("Qty", bigFont));		
		tableOne.addCell(new Phrase("Unit Price", bigFont));		
		//tableOne.addCell(new Phrase("VAT", bigFont));
		//tableOne.addCell(new Phrase("VAT %", bigFont));			
		tableOne.addCell(new Phrase("Amount", bigFont));	

		tableOne.setHeaderRows(1);
		tableOne.getDefaultCell().setBorder(Rectangle.TOP);
		tableOne.getDefaultCell().setBorder(Rectangle.BOTTOM);
		tableOne.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		//tableOneOne.getDefaultCell().setBorder(Rectangle.RIGHT);// site vertical line border
		//tableOneOne.getDefaultCell().enableBorderSide(Rectangle.LEFT);// site vertical line border
		Integer boader = Integer.decode("#616158");
		tableOne.getDefaultCell().setBorderColor(new BaseColor((boader.intValue() >> 16) & 0xFF, (boader.intValue() >> 8) & 0xFF, boader.intValue() & 0xFF));
		tableOne.getDefaultCell().setBackgroundColor(null);

		Font colordata = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.BLACK);
		int r = 0;
		do {
			
			if(datas.get(listCounter).get(0).length() > 75){
				ArrayList<String> strtruncate = listPhrase(datas.get(listCounter).get(0), 74, 74);
				for(int k = 0; k < strtruncate.size(); k++){
					if(k != strtruncate.size() - 1){
						tableOne.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
						if(k == 0){
							//tableOne.addCell(new Phrase(formatNumner((datas.get(listCounter).get(2))), smallFont));
							tableOne.addCell(new Phrase(String.valueOf(listCounter+1), smallFont));
						}
						else{
							tableOne.addCell("");
						}
						tableOne.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
						tableOne.addCell(new Phrase(strtruncate.get(k), smallFont));
						tableOne.addCell("");
						tableOne.addCell("");		
						tableOne.addCell("");		
						//tableOne.addCell("");
					}
					else{
						tableOne.addCell("");
						tableOne.addCell(new Phrase(strtruncate.get(k), smallFont));
						tableOne.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableOne.addCell(new Phrase(formatNumner((datas.get(listCounter).get(2))), smallFont));		
						tableOne.addCell(new Phrase(formatNumner((datas.get(listCounter).get(1))), smallFont));//vatValue(Integer each)			
						//tableOne.addCell(new Phrase("20", smallFont));							
						tableOne.addCell(new Phrase(formatNumner(totalValue(datas.get(listCounter).get(1), datas.get(listCounter).get(2))), smallFont));//totalValue(String each, String qty)
					}
					if((k + colorhelper) % 2 == 0 ){
						
						tableOne.getDefaultCell().setBackgroundColor(null);
						
					}
					else{
						
						tableOne.getDefaultCell().setBackgroundColor(null);
					}
					colorhelper = k + colorhelper;
					if(tableOne.size()  == tableCounter ){

						tableOne.getDefaultCell().enableBorderSide(Rectangle.BOTTOM);

					}					
				}


			}
			else{
				tableOne.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				//tableOne.addCell(new Phrase(formatNumner((datas.get(listCounter).get(2))), smallFont));
				tableOne.addCell(new Phrase(String.valueOf(listCounter+1), smallFont));
				tableOne.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				tableOne.addCell(new Phrase(datas.get(listCounter).get(0), smallFont));
				tableOne.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);				
				tableOne.addCell(new Phrase(formatNumner((datas.get(listCounter).get(2))), smallFont));		
				tableOne.addCell(new Phrase(formatNumner((datas.get(listCounter).get(1)).trim()), smallFont));	
				//tableOne.addCell(new Phrase("20", smallFont));		
				tableOne.addCell(new Phrase(formatNumner(totalValue(datas.get(listCounter).get(1), datas.get(listCounter).get(2))), smallFont));
			}
			if(colorhelper++  % 2 == 0){

				tableOne.getDefaultCell().setBackgroundColor(null);
			}
			else{
				tableOne.getDefaultCell().setBackgroundColor(null);
			}
			if(tableOne.size()  == tableCounter ){
	
				tableOne.getDefaultCell().enableBorderSide(Rectangle.BOTTOM);
			
			}

			//datas.remove(datas.get(r));int y = datas.size(); y < rows; y++
			listCounter++;
		}while(listCounter < datas.size() && tableOne.size() <= tableCounter);
		if(tableOne.size()  == 36 && !datas.isEmpty()){
			addPage = true;
		}
		if(tableOne.size() < tableCounter){
			for(int y = tableOne.size() ; y < tableCounter; y++){
				tableOne.addCell("");
				tableOne.addCell("");		
				tableOne.addCell("");		
				tableOne.addCell("");		
				tableOne.addCell("");		
				//tableOne.addCell("");
				if(y % 2 == 0){

					tableOne.getDefaultCell().setBackgroundColor(null);
				}
				else{
					tableOne.getDefaultCell().setBackgroundColor(null);
				}
				if(y == tableCounter - 2){
					tableOne.getDefaultCell().enableBorderSide(Rectangle.BOTTOM);
				}

			}
		
		}

		//tableOne.setHeaderRows(2);
        return tableOne;
    }
	
	public void createSecoundTable(Document document, PdfContentByte canvas)throws DocumentException, IOException {
    	// a table with three columns
		document.newPage();
		int y_axix = 790;
		int x_left = 15;
		Phrase com_name = new Phrase("J W Eletrical Services Ltd ", bigFont);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, com_name, x_left , y_axix , 0);
		Phrase inv_name = new Phrase("Quote", invoiceFont);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, inv_name, x_left + 525, y_axix , 0);
		
		LineSeparator com_name_line = new LineSeparator();
		com_name_line.setLineWidth(0.2f);
		com_name_line.drawLine(canvas, x_left, x_left + 565, 788) ;
			
		//tableCounter = tableCounter + 36;
        tableTwo = new PdfPTable(5);
		
		documentTable.add(tableTwo);
		tableTwo.setTotalWidth(565);
		tableTwo.getDefaultCell().setUseAscender(true);
        tableTwo.getDefaultCell().setUseDescender(true);

		int colorhelper = 0;
        // the cell object
		Integer alter = Integer.decode("#F0F0F0");
		BaseColor alter_color = new BaseColor((alter.intValue() >> 16) & 0xFF, (alter.intValue() >> 8) & 0xFF, alter.intValue() & 0xFF);
		
		Font color = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.WHITE); 
		int rows = 45;
		tableTwo.getDefaultCell().setMinimumHeight(10f);
		tableTwo.setWidths(new float[] {40.4f,296f,50.9f,50.9f,50.9f});
	//	table.getDefaultCell().setBorder(Rectangle.NO_BORDER);//use if no border needed
		tableTwo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		tableTwo.addCell(new Phrase("#", bigFont));
		tableTwo.addCell(new Phrase("Description", bigFont));
		tableTwo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);		
		tableTwo.addCell(new Phrase("Qty", bigFont));		
		tableTwo.addCell(new Phrase("Unit Price", bigFont));
		//tableTwo.addCell(new Phrase("VAT %", bigFont));	
		tableTwo.addCell(new Phrase("Amount", bigFont));	


		tableTwo.setHeaderRows(1);
		tableTwo.getDefaultCell().setBorder(Rectangle.TOP);
		tableTwo.getDefaultCell().setBorder(Rectangle.BOTTOM);
		tableTwo.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		//table.getDefaultCell().setBorder(Rectangle.RIGHT);// site vertical line border
		//table.getDefaultCell().enableBorderSide(Rectangle.LEFT);// site vertical line border
		Integer boader = Integer.decode("#616158");
		tableTwo.getDefaultCell().setBorderColor(new BaseColor((boader.intValue() >> 16) & 0xFF, (boader.intValue() >> 8) & 0xFF, boader.intValue() & 0xFF));
		tableTwo.getDefaultCell().setBackgroundColor(null);

		Font colordata = new Font(BaseFont.createFont(), 8, Font.NORMAL, BaseColor.BLACK);
		//for(int r = listCounter; r < datas.size(); r++){
		do{
			//while(listCounter < datas.size() && tableTwo.size() + extralSizeCounter <= tableCounter1){
			if(datas.get(listCounter).get(0).length() > 75){
			
				//testInt.add(Integer.valueOf(extralSizeCounter++));
				ArrayList<String> strtruncate = listPhrase(datas.get(listCounter).get(0), 74, 74);
				for(int k = 0; k < strtruncate.size(); k++){
					if(k != strtruncate.size() - 1){
						tableTwo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
						if(k == 0){
							//tableTwo.addCell(new Phrase(formatNumner((datas.get(listCounter).get(2))), smallFont));
							tableTwo.addCell(new Phrase(String.valueOf(listCounter+1), smallFont));
						}
						else{
							tableTwo.addCell("");
						}
						tableTwo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
						tableTwo.addCell(new Phrase(strtruncate.get(k), smallFont));
						tableTwo.addCell("");
						tableTwo.addCell("");		
						tableTwo.addCell("");		
						//tableTwo.addCell("");
					}
					else{
						tableTwo.addCell("");
						tableTwo.addCell(new Phrase(strtruncate.get(k), smallFont));
						tableTwo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableTwo.addCell(new Phrase(formatNumner((datas.get(listCounter).get(2))), smallFont));		
						tableTwo.addCell(new Phrase(formatNumner((datas.get(listCounter).get(1))), smallFont));//vatValue(Integer each)			
						//tableTwo.addCell(new Phrase("20", smallFont));							
						tableTwo.addCell(new Phrase(formatNumner(totalValue(datas.get(listCounter).get(1), datas.get(listCounter).get(2))), smallFont));
						//datas.remove(datas.get(r));
					}
					if((k + colorhelper) % 2 == 0 ){
						
						tableTwo.getDefaultCell().setBackgroundColor(null);
						
					}
					else{
						
						tableTwo.getDefaultCell().setBackgroundColor(null);
					}
					colorhelper = k + colorhelper;
					if(tableTwo.size()  == tableCounter1 ){

						tableTwo.getDefaultCell().enableBorderSide(Rectangle.BOTTOM);

					}				
				}


			}
			else{
				tableTwo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				//tableTwo.addCell(new Phrase(formatNumner((datas.get(listCounter).get(2))), smallFont));
				tableTwo.addCell(new Phrase(String.valueOf(listCounter+1), smallFont));
				tableTwo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				tableTwo.addCell(new Phrase(datas.get(listCounter).get(0), smallFont));
				tableTwo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);				
				tableTwo.addCell(new Phrase(formatNumner((datas.get(listCounter).get(2))), smallFont));		
				tableTwo.addCell(new Phrase(formatNumner((datas.get(listCounter).get(1))), smallFont));			
				//tableTwo.addCell(new Phrase("20", smallFont));					
				tableTwo.addCell(new Phrase(formatNumner(totalValue(datas.get(listCounter).get(1), datas.get(listCounter).get(2))), smallFont));
			}

			if(tableTwo.size() == tableCounter1  ){
				tableTwo.getDefaultCell().setBorder(Rectangle.BOTTOM);	
			}
		
		  listCounter++;	
		//}
		}while(listCounter < datas.size()  && tableTwo.size()   <= tableCounter1  );
	
		if( tableTwo.size() < tableCounter1 ){
			for(int y = tableTwo.size()+1 ; y < tableCounter1; y++){
				tableTwo.addCell("");
				tableTwo.addCell("");		
				tableTwo.addCell("");		
				tableTwo.addCell("");		
				tableTwo.addCell("");		
				//tableTwo.addCell("");
				if(y % 2 == 0){

					tableTwo.getDefaultCell().setBackgroundColor(null);
				}
				else{
					tableTwo.getDefaultCell().setBackgroundColor(null);
				}
				if(y == tableCounter1 -2){ //tableCounter1 - 2 if work must be removed
					tableTwo.getDefaultCell().enableBorderSide(Rectangle.BOTTOM);
				}

			}
		
		}

        tableTwo.writeSelectedRows(0, -1, 15, 780, canvas);
		if(datas.size() - 1 > listCounter){
			
			createSecoundTable(document, canvas);
			
		}

		else{
			createFooter(canvas);
			
		}
    }
	
	public void createHearder(Document document, PdfContentByte canvas)throws DocumentException, IOException{
	
		//Image img = null;
		int space = 27; int x_left = 15; int x_middle1 = 120; int x_middle2 = 160; int x_middle3 = 455; 
		int x_rightdata = 504; int x_rightrec = 470;
		
		/*try{
			img = Image.getInstance("images/logo_Derry.png");
		}
		catch(FileNotFoundException fx){}
		if(img != null){
			img.setAbsolutePosition(x_left, 770);
			img.scalePercent(60, 80);
			document.add(img);
			space = 27;
		}*/
	   Phrase com_logo = new Phrase("J W Electrical Services Ltd ", logoFont);//global quote data
	   ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, com_logo, x_left, 770 + space, 0);
		/*else{
			Paragraph comp_name = new Paragraph("J W Eletrical", new Font(FontFamily.HELVETICA, 23,Font.NORMAL, BaseColor.BLACK));
			ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, comp_name, x_left, 780, 0);
			space = 37;
		}*/
		Font main_font = new Font(FontFamily.HELVETICA, 6);
		Paragraph address = new Paragraph();
		//if(img == null){
		//address.add(new Phrase("J W Eletrical"));
			//Phrase com_name = new Phrase("J W Eletrical Services Ltd ", bigFont);
			//ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, com_name, x_left, 730 + space, 0);
			LineSeparator com_name_line = new LineSeparator();
			com_name_line.setLineWidth(0.2f);
			com_name_line.drawLine(canvas, x_left, x_left + 250, 755) ;
		//}
		
		int add_one1 = 720 + space;
		client_details1.add("24 Spencer Road");//global quote data
		client_details1.add("Langley Green");
		client_details1.add("Crawley");
		client_details1.add("West Sussex");
		client_details1.add("RH11 7SN");

		for(int j = 0; j < client_details1.size(); j++){
			Phrase client1_add = new Phrase(client_details1.get(j), smallFont);
			ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, client1_add, x_left, add_one1, 0);
			add_one1-=12;
		}
		
		client_details3.add("Tel:");//global quote data
		client_details3.add("Mobile:");
		client_details3.add("Email:");
		client_details3.add("Web-Site:");

		int add_one2 = 720 + space;
	
		for(int g = 0; g < client_details3.size(); g++){
			Phrase client3_add = new Phrase(client_details3.get(g), smallFont);
			ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, client3_add, x_middle1, add_one2, 0);
			add_one2-=12;
		}
		
		client_details2.add("01293 452452");//global quote data
		client_details2.add("07885426541");
		client_details2.add("j.w.eletrical@yahoo.com");
		client_details2.add("www.jwelectrical.com");

		int add_one3 = 720 + space;
		for(int k = 0; k < client_details2.size(); k++){
			Phrase client2_add = new Phrase(client_details2.get(k), smallFont);
			ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, client2_add, x_middle2, add_one3, 0);
			add_one3-=12;
		}
		
		Paragraph quote = new Paragraph("Quote", invoiceFont);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, quote, 540, 780, 0);

	
		//customer info
		Phrase com_name1 = new Phrase("Quote To:", bigFont);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, com_name1, x_left, 631 + space, 0);
		LineSeparator com_line1 = new LineSeparator();
		com_line1.setLineWidth(0.2f);
		com_line1.drawLine(canvas, x_left, x_left + 120, 657) ;

		int add_left = 650;
		/*customer_details.add("Mr Solomon Smith");
		customer_details.add("Runtime Collection LTD");
		customer_details.add("2A Hamalton Avenue");
		customer_details.add("Redhill");
		customer_details.add("Surrey");
		customer_details.add("RH1 6SC");*/

		for(int j = 0; j < customer_details.size(); j++){
			Phrase customer_add = new Phrase(customer_details.get(j), smallFont);
			ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, customer_add, x_left, add_left, 0);
			add_left-=12;
		}
		
		Phrase site_name = new Phrase("Site:", bigFont);
		ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, site_name, x_left+320, 631 + space, 0);
		LineSeparator com_line2 = new LineSeparator();
		com_line2.setLineWidth(0.2f);
		com_line2.drawLine(canvas, x_left+440, x_left + 320, 657) ;
		
		int add_right = 650;
		//site_details.add("Mr Solomon Smith");
	/*	site_details.add("Runtime Collection LTD");
		site_details.add("2A Hamalton Avenue");
		site_details.add("Redhill");
		site_details.add("Surrey");
		site_details.add("RH1 6SC");*/

		for(int j = 0; j < site_details.size(); j++){
			Phrase site_add = new Phrase(site_details.get(j), smallFont);
			ColumnText.showTextAligned(canvas , Element.ALIGN_LEFT, site_add, x_left + 320, add_right, 0);
			add_right-=12;
		}

		//quote informations descriptor
		PdfPTable quotebox = new PdfPTable(2);
		quotebox.setWidths(new float[] {24.4f,35f});

			
			//quote pdfptable information details
		quotebox.setTotalWidth(160);
		quotebox.getDefaultCell().setMinimumHeight(15f);
		quotebox.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		quotebox.addCell(new Phrase("Quote No:", smallFont));
		quotebox.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		quotebox.addCell(new Phrase("0000000"+quoteSummary[1], smallFont));
		quotebox.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT );
		quotebox.addCell(new Phrase("Quote Date:", smallFont));
		quotebox.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		quotebox.addCell(new Phrase(ul.usDateString(quoteSummary[0]), smallFont));
		quotebox.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT );
		quotebox.addCell(new Phrase("Customer Id:", smallFont));
		quotebox.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		quotebox.addCell(new Phrase("0000"+quoteSummary[2], smallFont));
		quotebox.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		quotebox.addCell(new Phrase("Valid Till:", smallFont));
		quotebox.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		quotebox.addCell(new Phrase(ul.usDateString(quoteSummary[3]), smallFont));
		quotebox.writeSelectedRows(0, -1, 420, 770, canvas);

	
	}
	
	public void createFooter(PdfContentByte canvas)throws DocumentException, IOException{
		int reg_details = 432; int vat_rate = 516;
		int name = 510; int value = 588;

		//terms and condition header
		ArrayList<String> terms = new ArrayList<String>();//global quote variable
		terms.add("Invoices must be paid by 30 days from the invoice date " +
		"by cash, cheque or directly into bank account");
		terms.add("In the event of changes to the specification or "+
		"scope of the project, a revised quote should be agreed before work commences");
		terms.add("Please feel free to contact us on 01293 7854 412 if you have any questions about our quote.");
		terms.add("We trust that we have interpreted your requirements correctly as every effort has been made to accurately estimate your project.");
		
		int startPoint = 160;

		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT,
		  new Paragraph("TERMS AND CONDITIONS", bigFont), 117, 180, 0);
		  

	
		//terms and conditions details
		PdfPTable messageTable = new PdfPTable(1);
		messageTable.setTotalWidth(246f);
		messageTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		messageTable.getDefaultCell().setMinimumHeight(8.5f);
	
		for(String str : terms){
			Paragraph paragraph = new ListItem("\u2022   ", smallFont);
			if(str.length() > 65){
				ArrayList<String> strList = listPhrase(str, 65, 65);
				int counter = 0;
				for(String strL : strList){
					Chunk chunk = new Chunk(strL);
					if(counter == 0){
						paragraph.add(chunk);
						messageTable.addCell(paragraph);
					}
					else{
						messageTable.addCell(new Paragraph(strL, smallFont));
					}
					counter++;
				}
			}
			
			else{
				Chunk chunk = new Chunk(str);
				paragraph.add(chunk);
				messageTable.addCell(paragraph);
			}
			messageTable.addCell("");
		  }
		
		messageTable.writeSelectedRows(0, -1, 15, 175, canvas);
		canvas.saveState();
		canvas.setLineWidth(0.05f);
		
		canvas.rectangle(15, 35, 246, 140);
		canvas.stroke();
		canvas.restoreState();
		

		String reg1 = "Registered in England and Wales, company number 948470. Registered address: MORI House,"+
						"79-81 Borough Road, London SE1 1FY. VAT Registration Number: 833 2781 27";

		ColumnText.showTextAligned(canvas , Element.ALIGN_RIGHT,	
			new Paragraph(reg1, regFont), reg_details, 15, 0);


		PdfPTable amountbox = new PdfPTable(2);
		amountbox.setTotalWidth(110f);
		amountbox.getDefaultCell().setMinimumHeight(20f);
		amountbox.setWidths(new float[] {40.4f,40.4f});
		amountbox.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		amountbox.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		amountbox.addCell(new Paragraph("Sup Total :", smallFont));
		amountbox.addCell(new Phrase(NumberFormat.getCurrencyInstance(locale).format(subTotal(documentTable)), smallFont));

		amountbox.addCell(new Paragraph("VAT @20.00% :", smallFont));
		amountbox.addCell(new Phrase(NumberFormat.getCurrencyInstance(locale).format(vatTotal(documentTable)), smallFont));
		

		amountbox.addCell(new Paragraph("Total :", invoiceTotal));
		amountbox.addCell(new Phrase(NumberFormat.getCurrencyInstance(locale).format(totalValue(documentTable)), invoiceTotal));
		
		amountbox.writeSelectedRows(0, -1, 470, 200, canvas);
		
		
	
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
	

	
	private String formatNumner(String number){
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		return String.valueOf(nf.format((Double.valueOf(number)).doubleValue()));
	}
	
	private ArrayList<String> listPhrase(String truncate, int srtchunk, int initialValue){
			ArrayList<String> data = new ArrayList<String>();
			//int srtchunk = 74;
			//int initialValue = 74;
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
	private String vatValue(String each){
		return String.valueOf(new BigDecimal("0").add(
		((new BigDecimal(each.trim()).multiply(
		new BigDecimal("20")).divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE)))));
	}
	
	private String totalValue(String each, String qty){

		return String.valueOf((new BigDecimal(each.trim()).multiply(new BigDecimal(qty.trim()))));
	}
	

	
	private BigDecimal culumnValue(PdfPTable table){
		ArrayList<PdfPRow> rowList = table.getRows();
		BigDecimal total = BigDecimal.ZERO;
		if(!rowList.isEmpty()){
			for(PdfPRow row : rowList){
				if(NumberFormat.getNumberInstance().parseObject(row.getCells()[2].getPhrase().getContent(), new ParsePosition(0)) != null){

					total = total.add((new BigDecimal(String.valueOf(NumberFormat.getNumberInstance().parseObject(
					row.getCells()[2].getPhrase().getContent(), new ParsePosition(0))))).multiply(
					new BigDecimal(String.valueOf(NumberFormat.getNumberInstance().parseObject(
					row.getCells()[3].getPhrase().getContent(), new ParsePosition(0))))));

				}
					
			}
	
		}
		return total;
	}
	
	private BigDecimal vatTotal(ArrayList<PdfPTable> table){
		return new BigDecimal(String.valueOf(subTotal(table))).multiply(
		new BigDecimal("20").divide(new BigDecimal("100"), DECIMALS, ROUNDING_MODE));
	}
	
	private BigDecimal totalValue(ArrayList<PdfPTable> table){
	
		return new BigDecimal(String.valueOf(vatTotal(table))).add(new BigDecimal(String.valueOf(subTotal(table))));
	}
	
   private BigDecimal subTotal(ArrayList<PdfPTable> tables){
		BigDecimal total = BigDecimal.ZERO;
		for(PdfPTable table : tables){
			total = total.add(culumnValue(table));
		}
		return total;
	}

}
  
