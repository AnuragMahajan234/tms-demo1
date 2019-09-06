package org.yash.rms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.util.SearchCriteriaGeneric.SearchColumn;

public class GenericCriteria {
	
	
	public static Criteria createCriteria(SearchCriteriaGeneric searchCriteriaGeneric, Criteria criteria) throws ParseException
	{
		
	
		Integer page=searchCriteriaGeneric.getPage();
		Integer size=searchCriteriaGeneric.getSize();
		Set<SearchColumn> searchColumns=searchCriteriaGeneric.getSearchColumns();
		String iSortColName=searchCriteriaGeneric.getISortColumn();
		String iSortDir=searchCriteriaGeneric.getISortDir();
		List<String> createdAlias= new ArrayList<String>();
		
		Iterator<SearchColumn> seachColumnItr= searchColumns.iterator();
		SimpleDateFormat   outputFormat = new SimpleDateFormat(Constants.DATE_PATTERN_5);
		
		while(seachColumnItr.hasNext())
		{
			
			SearchColumn searchColumn= seachColumnItr.next();
			System.out.println("searchColumn.getType()-----------------"+searchColumn.getType()+"....searchColumn.getColumnName() ---------------"+searchColumn.getColumnName()+" searchColumn.getValue() -----------"+searchColumn.getValue());
			
			if(searchColumn.getType().equalsIgnoreCase(Constants.DATE)) {
				Date passedDate = null;
				try {
					passedDate = outputFormat.parse(searchColumn.getValue());
					if(searchColumn.getColumnName().equalsIgnoreCase(InfogramActiveResource.CREATEDTIMESTAMP)){
						criteria.add(Restrictions.ge(InfogramActiveResource.CREATEDTIMESTAMP, getFormattedFromDateTime(passedDate)));
						criteria.add(Restrictions.le(InfogramActiveResource.CREATEDTIMESTAMP, getFormattedToDateTime(passedDate)));
					}
					else
					criteria.add(Restrictions.eq(searchColumn.getColumnName(), passedDate));
				} catch (ParseException e) {
					throw e;
				}
				
			}else if(searchColumn.getType().equalsIgnoreCase(Constants.INTEGER)) {
			Integer value= Integer.parseInt(searchColumn.getValue());
			System.out.println("inside INteger check"+value);
		
				criteria.add(Restrictions.eq(searchColumn.getColumnName(),value));
			}else if(searchColumn.getType().equalsIgnoreCase(Constants.CHILD_OBJECT))
				{
			
				String ForeignKeyName=searchColumn.getColumnName();
				System.out.println("ForeignKeyName=============="+ForeignKeyName);
				String[] alias=searchColumn.getColumnName().split("\\.");
				System.out.println("alias=============="+alias.toString()+" length "+alias.length);
				String aliasName=alias[(alias.length-1)-1];
				System.out.println("aliasName"+aliasName);
				//String aliasName1=searchColumn.getColumnName().substring(0,searchColumn.getColumnName().lastIndexOf(".")-1);
				String aliasName1=searchColumn.getColumnName().substring(0,searchColumn.getColumnName().lastIndexOf("."));
				System.out.println("aliasName1 "+ aliasName1);
				
				String ss=ForeignKeyName.substring(ForeignKeyName.lastIndexOf(".")+1,ForeignKeyName.length());
				if(!createdAlias.contains(aliasName))
				{
					criteria.createAlias(aliasName1, aliasName);
					createdAlias.add(aliasName);
				}
				criteria.add(Restrictions.eq(aliasName+"."+ss,searchColumn.getValue()));
				}else {
			
			criteria.add(Restrictions.ilike(searchColumn.getColumnName(), "%"+searchColumn.getValue()+"%"));
			}
		}
		//For sorting or order By
		if(iSortColName!=null) {
			if(iSortDir.equalsIgnoreCase(Constants.ASC))
			{
			criteria.addOrder(Order.asc(iSortColName));
			}else
			{
			criteria.addOrder(Order.desc(iSortColName));
			}
		}
		
		//For number of records display
		if(page!=null)
		{
		criteria.setFirstResult((page-1)*size);
		}
		if(size!=null)
		{
		criteria.setMaxResults(size);
		}
		return criteria;
		
	}
	private static Date getFormattedFromDateTime(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    return cal.getTime();
	}

	private static Date getFormattedToDateTime(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 23);
	    cal.set(Calendar.MINUTE, 59);
	    cal.set(Calendar.SECOND, 59);
	    return cal.getTime();
	}

	public static void main(String[] args) {
		String searchColumn="designation.designationName.id";
		System.out.println(searchColumn.lastIndexOf("."));
		String ss=searchColumn.substring(searchColumn.lastIndexOf(".")+1,searchColumn.length());
	System.out.println("ss===="+ss);
	
	String[] alias="designation.designationName.id".split("\\.");
	System.out.println("alias=============="+alias.toString()+" length "+alias.length);
	System.out.println("(alias.length-1)" + (alias.length-1));
	String aliasName=alias[(alias.length-1)-1];
	System.out.println("aliasName "+ aliasName);
	
	String searchColumn1="designation.designationName";
	String ForeignKeyName=searchColumn1;
	System.out.println("ForeignKeyName=============="+ForeignKeyName);
	String[] alias_searchColumn1=searchColumn1.split("\\.");
	System.out.println("alias=============="+alias_searchColumn1.toString()+" length "+alias_searchColumn1.length);
	String aliasName_searchColumn1=alias_searchColumn1[(alias_searchColumn1.length-1)-1];
	System.out.println("aliasName"+aliasName_searchColumn1);
	System.out.println("searchColumn1.lastIndexOf(\".\") "+ searchColumn1.lastIndexOf("."));
	String aliasName1=searchColumn1.substring(0,searchColumn1.lastIndexOf("."));
	System.out.println("aliasName1 "+aliasName1);
	String ss1=ForeignKeyName.substring(ForeignKeyName.lastIndexOf(".")+1,ForeignKeyName.length());
	System.out.println("ss1 "+ss1);
	
	}
	
}
