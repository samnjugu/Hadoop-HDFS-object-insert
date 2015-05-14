package com.muthii.Services.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class Tools {
	/**
	 * Remove all non digit characters from string 
	 * @param date
	 * @return
	 */
	public String removeAllNonDigits(String date)
    {
    	return date.replaceAll("\\D+","");
    }
    
    
    /**
	 * 
	 * @param fname file name to read from 
	 * @return
     * @throws IOException	 
	 */
	public String getStringFromFile(String fname) throws IOException 
    {
        //Read stores from file 
        String filePath = "/somepath/"+fname;
        File htmlFile = new File(filePath);
        StringBuilder html = new StringBuilder();
        BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(htmlFile)));
        String line;
        while ((line = br.readLine()) != null)
        {            
            html.append(line+"\n");                           
        }
        br.close();
        return html.toString();        
    }
	
	/*
	 * Html String to passworded encrypted PDF
	 * */
	public ByteArrayOutputStream htmlToPdf(String html)
    {
    	ByteArrayOutputStream bytesOut = null;
    	try
    	{    		
        	Document document = new Document();
    		
    		//document header attributes
		   document.addAuthor("muthii");
		   document.addCreationDate();
		   document.addProducer();
		   document.addCreator("Someone");
		   document.addTitle("PDF created from Html");
		   document.setPageSize(PageSize.LETTER);
            
    		bytesOut = new ByteArrayOutputStream(4096);
        	PdfWriter writer = PdfWriter.getInstance(document, bytesOut);
        	writer.setInitialLeading(12.5f);
        	writer.setEncryption("user".getBytes(), "secretPass".getBytes(),PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
            // step 3
            document.open();
            // step 4
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(html.getBytes("UTF-8")));	
            //step 5
             document.close();

            System.out.println( "PDF Created!" );           
            
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return bytesOut;
    	
    }            
}
