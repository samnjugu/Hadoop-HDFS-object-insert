package com.muthii.Services.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class Hadoop {
	private Tools tools = new Tools();	
	
    
    public void htmlToHdfs(String html, String filename) throws IOException
    {
    	ByteArrayOutputStream byout =  tools.htmlToPdf(html);
    	
    	// This one line removes need of using UserGroupInformation which needs further config to work. Basically set user to connect as "hadoopUser"
    	System.setProperty("HADOOP_USER_NAME", "hadoopUser");    

    	//1. Get the instance of COnfiguration
        Configuration configuration = new Configuration();             
        configuration.set("fs.default.name", "hdfs://localhost:9000");
        //2. Create an InputStream to read the data 
        InputStream inputStream = new ByteArrayInputStream(byout.toByteArray());
        //3. Get the HDFS instance        
        FileSystem hdfs = FileSystem.get(configuration);
        //4. Open a OutputStream to write the data, this can be obtained from the FileSytem
        OutputStream outputStream = hdfs.create(new Path("hdfs://localhost:9000/location/"+filename),
        new Progressable() {  
                public void progress() {
           System.out.println("....");
                }
                      });
        try
        {
          IOUtils.copyBytes(inputStream, outputStream, 4096, false); 
        }
        finally
        {
          IOUtils.closeStream(inputStream);
          IOUtils.closeStream(outputStream);
        }
        System.out.println("File added to hdfs");
        
    }      
}
