package org.yash.rms.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.util.SearchCriteriaGeneric.SearchColumn;

public class DataTableParser {

	
 public  static SearchCriteriaGeneric getSerchCriteria(HttpServletRequest httpRequest,Object object)
 {
	 
	 SearchCriteriaGeneric searchCriteriaGeneric= new SearchCriteriaGeneric();
	 
	 Integer page=0;
	 Integer size=10;
	
	 try {
		size=getSize(httpRequest);
		page = getPageNumber(httpRequest);
		if(page!=0) {
			page = (page/size)+1;
		}else {
			page =1;
		}
	} catch (Exception e) {
		
	}
	 String iSortColumn=httpRequest.getParameter("iSortCol_0");
	 String iSortDir=httpRequest.getParameter("sSortDir_0");
	 searchCriteriaGeneric.setSize(size);
	 searchCriteriaGeneric.setPage(page);
	 searchCriteriaGeneric.setSearchColumns(getSearchColumnValues(httpRequest,object));
	 searchCriteriaGeneric.setISortColumn(getSortColumnName(iSortColumn,httpRequest));
	 searchCriteriaGeneric.setiSortDir(iSortDir);
	 
	return searchCriteriaGeneric;
	 
 }
 
 
 private static Integer getSize(HttpServletRequest httpRequest) throws Exception {
	 try {
		 return Integer.parseInt(httpRequest.getParameter("iDisplayLength"));
		 }catch(Exception ex)
		 {
			 throw new Exception();
		 }
}


private static Integer getPageNumber(HttpServletRequest httpRequest) throws Exception
 {
	 try {
	 return Integer.parseInt(httpRequest.getParameter("iDisplayStart"));
	 }catch(Exception ex)
	 {
		 throw new Exception();
	 }
 }
 
private static Set<SearchColumn> getSearchColumnValues(HttpServletRequest httpRequest,Object object)
{
		Set<SearchColumn> searchColumns= new HashSet<SearchCriteriaGeneric.SearchColumn>();
	Integer numberOfColumn=Integer.parseInt(httpRequest.getParameter("iColumns"));
	for(int i=0;i<numberOfColumn;i++)
	{
		String value=httpRequest.getParameter("sSearch_"+i);
		if(value!=null && !value.isEmpty() && value.length()!=0)
		{
			
			SearchColumn searchColumn = new SearchCriteriaGeneric().new SearchColumn();
			String columnName=httpRequest.getParameter("mDataProp_"+i);
			String typeOfColumn=null;
				
			typeOfColumn = getSearchColumnType(columnName, object);				
				if(typeOfColumn!=null && typeOfColumn.equalsIgnoreCase(Constants.DATE)) 
				{
					value = getValueForDate(value);
					
				}else if(typeOfColumn!=null && typeOfColumn.equalsIgnoreCase(Constants.BOOLEAN)) {
					
				}
				searchColumn.setType(typeOfColumn);
			searchColumn.setValue(value);
			searchColumn.setColumnName(columnName);
			searchColumns.add(searchColumn);
			
			System.out.println("typeOfColumn "+ typeOfColumn);
		}	
		
	}
	return searchColumns;
	
	}

private static String getSortColumnName(String colIndex,HttpServletRequest httpRequest)
{
	
	String columnName=httpRequest.getParameter("mDataProp_"+colIndex);
	return columnName;
	}

private static String getSearchColumnType(String columnName,Object object)  {
	String simpleName = null;
	try {
		if(!columnName.contains("."))
		{
		simpleName = object.getClass().getDeclaredField(columnName).getType().getSimpleName();
		}else
		{
			simpleName="CHILD_OBJECT";
		}
	} catch (NoSuchFieldException e) {
		e.printStackTrace();
	} catch (SecurityException e) {
		e.printStackTrace();
	}
	return simpleName;
}

private static String getValueForDate(String value) {
	SimpleDateFormat   outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat   inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
	String outputDateToStr = null;
	
	try {
		Date date = inputFormat.parse(value);					
		
		outputDateToStr = outputFormat.format(date);
		
	} catch (ParseException e) {
		e.printStackTrace();
	}
	
	return outputDateToStr;
}
/*public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, SecurityException {
	String typeOfColumn=null;
	  Class c1 = Class.forName("org.yash.rms.domain.InfogramActiveResource"); 
	Field fi = c1.getDeclaredField("dateOfBirth");
	System.out.println("c1 "+fi.getGenericType());
	System.out.println("c2 "+fi.getType());
	Date d = new Date();
	if (fi.getType() == Date.class) {
        System.out.println("date "+ d.toString()); 
        System.out.println(Date.class.toString());
	}
	typeOfColumn=c1.getDeclaredField("dateOfBirth").getType().getSimpleName();
	System.out.println("typeOfColumn"+typeOfColumn);
	if(typeOfColumn.equalsIgnoreCase(Constants.DATE)) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String DateToStr = format.format(d);
		try {
			System.out.println("DateToStr :: "+DateToStr);
			Date strToDate = format.parse(DateToStr);
			System.out.println("strToDate :: "+strToDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

	
}*/

}
