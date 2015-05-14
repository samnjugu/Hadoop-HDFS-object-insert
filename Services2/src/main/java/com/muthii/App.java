package com.muthii;

import com.muthii.Services.util.Hadoop;
import com.muthii.Services.util.Tools;

/**
 * Test hadoop HDFS object insertion
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
    		Hadoop hd = new Hadoop();
    		hd.htmlToHdfs(new Tools().getStringFromFile("51201592821"),"51201592821");
    	} catch (Exception e) {
    		System.out.println("Hadoop HDFS object insertion failed");
			e.printStackTrace();
		}
    }
}
