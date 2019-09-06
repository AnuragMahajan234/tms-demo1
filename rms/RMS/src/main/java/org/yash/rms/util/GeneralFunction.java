package org.yash.rms.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang3.StringUtils;

public class GeneralFunction {
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();

	public static String randomString( int length ) 
	{
	   StringBuilder sb = new StringBuilder( length );
	   for( int i = 0; i < length; i++ ) {
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   }
	   System.out.println("Random String : " + sb.toString());
	   return sb.toString();
	}
	
	public static Collection<Integer> convertStringListToIntList(Collection<String> collection) {
		Collection<Integer> integerList = null;
		if (!collection.isEmpty()) {
			if (collection instanceof HashSet<?>) {
				integerList = new HashSet<Integer>();
			} else {
				integerList = new ArrayList<Integer>();
			}
			Iterator<String> iterator = collection.iterator();
			while (iterator.hasNext()) {
				
				String convertIntoInterger = iterator.next().trim();
				if(convertIntoInterger.length() > 0)
					{	
						try {
						integerList.add(Integer.parseInt((convertIntoInterger).trim()));
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
					}
			}
			return integerList;
		} else
			return new ArrayList<Integer>();
	}

	public static File createTempFile(String prefix, String suffix) {
	    String tempDir = System.getProperty("java.io.tmpdir");
		String fileName = (prefix != null ? prefix : "" ) + (suffix != null ? suffix : "" );
		File file = new File(tempDir, fileName);
		return file;
	}
	
	public static String getSystemIP() {
		String systemIP = "";
		try {
			systemIP = InetAddress.getLocalHost().getHostAddress();
			System.out.println("System IP address : " +systemIP); //gives only host address
		} catch (UnknownHostException ex) {
			systemIP = "Error";
			System.err.println("Error while getting IP address : " +ex.getMessage());
			ex.printStackTrace();
		}
		return systemIP;
	}
	
}
