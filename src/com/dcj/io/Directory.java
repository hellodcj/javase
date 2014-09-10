package com.dcj.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class Directory {

	@Test
	public void listDirecotry() throws Exception{
		 BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));  
         System.out.print("请输入文件夹目录：");  
         String dir = strin.readLine(); 
         File file = null;
         if (dir.trim()!="" && dir!=null){
        	 file = new File(dir);
         }else{
        	 file = new File(".");
         }
         
         if(!file.isDirectory()){
        	 System.out.println("不是文件夹");
        	 System.exit(0);
         }
         
         File[] files = file.listFiles();
         for (File f :files){
        	 Calendar c = new GregorianCalendar();
        	 long millis = f.lastModified(); 
        	 System.out.println(millis);
        	 c.setTimeInMillis(millis);
        	 System.out.println("last modified:"+c.get(Calendar.YEAR)+"-"+(1+c.get(Calendar.MONTH))+"-"+c.get(Calendar.DATE));
         }
         
	}
}
