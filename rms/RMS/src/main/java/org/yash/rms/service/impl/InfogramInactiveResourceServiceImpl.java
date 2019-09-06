package org.yash.rms.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTimeComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yash.rms.dao.InfogramInactiveResourceDao;
import org.yash.rms.domain.InfogramActiveResource;
import org.yash.rms.domain.InfogramInactiveResource;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.ResourceAllocation;
import org.yash.rms.dto.InfogramActiveResourceDTO;
import org.yash.rms.dto.InfogramInactiveResourceDTO;
import org.yash.rms.exception.DAOException;
import org.yash.rms.exception.RMSServiceException;
import org.yash.rms.service.InfogramInactiveResourceService;
import org.yash.rms.service.ResourceAllocationService;
import org.yash.rms.service.ResourceService;
import org.yash.rms.util.AppContext;
import org.yash.rms.util.Constants;
import org.yash.rms.util.DataTableParser;
import org.yash.rms.util.DozerMapperUtility;
import org.yash.rms.util.InfogramProcessStatusConstants;
import org.yash.rms.util.SearchCriteriaGeneric;

/**
 * InfogramInactiveResourceServiceImpl class is service implementation for
 * InfogramInactiveResourceService interface.
 * 
 * @author samiksha.sant
 *
 */
@Service
public class InfogramInactiveResourceServiceImpl implements InfogramInactiveResourceService {
	
	private static final Logger logger = LoggerFactory.getLogger(InfogramInactiveResourceService.class);
	

	@Autowired
	InfogramInactiveResourceDao infogramInactiveResourceDao;

	@Autowired
	ResourceService resourceService;
	
	@Autowired
	@Qualifier("ResourceAllocationService")
	ResourceAllocationService resourceAllocationService;

	@Autowired
	DozerMapperUtility dozerMapperUtility;

	public List<InfogramInactiveResource> getAllInfogramInactiveResources(HttpServletRequest httpRequest) {
		SearchCriteriaGeneric searchCriteriaGeneric=DataTableParser.getSerchCriteria(httpRequest,new InfogramInactiveResource());
		List<InfogramInactiveResource> inactiveResources = infogramInactiveResourceDao.getAllInfogramInactiveResources(searchCriteriaGeneric);
		return inactiveResources;
		//return dozerMapperUtility.convertInfogramInactiveResourceDomainToDTO(inactiveResources);
	}
	@Transactional
	public Boolean updateExitDetails(Integer id, Resource resource)  throws RMSServiceException{
		logger.info("------------------updateExitDetails method start-----------");
		Boolean isSuccess = true;
		RMSServiceException serviceException = new RMSServiceException();
		InfogramInactiveResource inactiveResource  = null;
		try {
			inactiveResource = infogramInactiveResourceDao.getInfogramInactiveResourceById(id);
			/* converting infogram resource object to RMS object.*/
			Resource rmsResource = new Resource();
			if(inactiveResource!=null){
				rmsResource = resourceService.findResourcesByYashEmpIdEquals(inactiveResource.getEmployeeId());
			}
			/*service call to save or update the resource object received.*/
			if(rmsResource!=null){ 
				rmsResource.setReleaseDate(inactiveResource.getReleasedDate());
				rmsResource.setResignationDate(inactiveResource.getResignedDate());
				isSuccess = resourceService.saveOrupdate(rmsResource);
				
				//Call a method to set reliving date for projects and NO for all project
				if(rmsResource.getReleaseDate()!=null) {
					setProjectEndDateAndNoStatus(rmsResource);
				}
			}
			if(isSuccess && rmsResource!=null) {
				inactiveResource.setProcessStatus(InfogramProcessStatusConstants.SUCCESS.toString());
				inactiveResource.setModifiedBy(resource.getEmployeeId().toString());
				String middleName=resource.getMiddleName().length()>0?resource.getMiddleName()+" ":"";
				String name = resource.getFirstName()+" "+middleName+resource.getLastName();
				inactiveResource.setModifiedName(name);
				inactiveResource.setModifiedTime(new Date());
				updateInfogramInactiveResource(inactiveResource);
			 } else {
				 isSuccess = false;
				 inactiveResource.setProcessStatus(InfogramProcessStatusConstants.FAILURE.toString());
				 inactiveResource.setFailureReason("Error: Resource not available in rms");
				 inactiveResource.setModifiedName(Constants.SYSTEM);
				 inactiveResource.setModifiedTime(new Date());
					
				 updateInfogramInactiveResource(inactiveResource);
			 }
		}catch (Exception exception) {
			isSuccess = false;
	           
			if(exception instanceof DAOException) {
                  serviceException.setErrCode(((DAOException) exception).getErrCode());
            }else {
                  ((RMSServiceException) serviceException).setErrCode("404");
            }
                  
            ((RMSServiceException) serviceException).setMessage(exception.getMessage());
            inactiveResource.setProcessStatus(InfogramProcessStatusConstants.FAILURE.toString());
            inactiveResource.setFailureReason("Error : "+exception.getMessage());
         	inactiveResource.setModifiedName(Constants.SYSTEM);
       	 	inactiveResource.setModifiedTime(new Date());
       	    //InfogramInactiveResourceService service= AppContext.getApplicationContext().getBean(InfogramInactiveResourceService.class);
           // service.updateInfogramInactiveResource(inactiveResource);
            updateInfogramInactiveResource(inactiveResource);
            throw serviceException;
		} 
		 
		logger.info("------------------updateExitDetails method end-----------");
		return isSuccess;
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateInfogramInactiveResource(InfogramInactiveResource inactiveResource) throws RMSServiceException{
		try{
				infogramInactiveResourceDao.updateInfogramInactiveResource(inactiveResource);
		}
		catch(DAOException exception){
			throw new RMSServiceException(exception.getErrCode(), exception.getMessage());
		}
	}
	@Transactional
	public void discardInfogramInactiveResource(Integer id, Resource resource) {
		InfogramInactiveResource inactiveResource = infogramInactiveResourceDao.getInfogramInactiveResourceById(id);
		try{
		if(inactiveResource!=null){
			inactiveResource.setModifiedBy(resource.getEmployeeId().toString());
			String middleName=resource.getMiddleName().length()>0?resource.getMiddleName()+" ":"";
			String name = resource.getFirstName()+" "+middleName+resource.getLastName();
			inactiveResource.setModifiedName(name);
			inactiveResource.setModifiedTime(new Date());
			inactiveResource.setProcessStatus(InfogramProcessStatusConstants.DISCARD.toString());
			infogramInactiveResourceDao.updateInfogramInactiveResource(inactiveResource);
		}
		else{
			throw new RMSServiceException("404","Employee not found for Employee Id in Infogram ");
		}
		}
		catch(DAOException exception){
			throw new RMSServiceException(exception.getErrCode(), exception.getMessage());
		}

	}
	public Long getTotalCountInactResources(HttpServletRequest httpRequest) throws Exception{
		Long count = 0L;
		SearchCriteriaGeneric searchCriteriaGeneric=DataTableParser.getSerchCriteria(httpRequest,new InfogramInactiveResource());
		try {
		count =  infogramInactiveResourceDao.getTotalCountInactResources(searchCriteriaGeneric);
		
		}catch (Exception e) {

			count = 0L;
			e.printStackTrace();
			logger.error("Exception Occurred while counting resources in infogram and yash "+ e.getMessage());
			throw e;
		}
		return count;
	}
	
	public List<InfogramInactiveResource> getAllInfogramInactiveResources(){
		return infogramInactiveResourceDao.getAllInfogramInactiveResources();
	}


	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateProcessStatus(Integer id)
	{
		logger.info("updateProcessStatus method Start ");
		InfogramInactiveResource InfogramInactiveResource=null;
	
		try{
			InfogramInactiveResource= infogramInactiveResourceDao.getInfogramInactiveResourceById(id);
			InfogramInactiveResource.setModifiedName(Constants.SYSTEM);
			InfogramInactiveResource.setModifiedTime(new Date());
			InfogramInactiveResource.setProcessStatus("DISCARD");
			logger.info("Updated Duplicate record........."+InfogramInactiveResource.getEmployeeId());
			infogramInactiveResourceDao.updateInfogramInactiveResource(InfogramInactiveResource);
		}
		catch(DAOException ex)
		{
			logger.error("Excption occurred in updateProcessStatus : "+ex.getMessage());
		}
		logger.info("updateProcessStatus method End ");
		
	}	

	private Boolean setProjectEndDateAndNoStatus(Resource rmsResource) throws RMSServiceException{
		logger.info("------------------setProjectEndDateAndNoStatus method start-----------");
		Boolean isSuccess = true;
		RMSServiceException serviceException = new RMSServiceException();
		Date today = new Date();
		try {
			Date relivingDate = rmsResource.getReleaseDate();
				List<ResourceAllocation>  resAlloc = resourceAllocationService.findResourceAllocationByEmployeeId(rmsResource.getEmployeeId());
				Iterator<ResourceAllocation> resAllocIterator=resAlloc.iterator();
			
					while(resAllocIterator.hasNext()) {
						ResourceAllocation resAllocObj = resAllocIterator.next();
						
						DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
						
						Date resAllocEndDate = resAllocObj.getAllocEndDate();
						int endDate = 	dateTimeComparator.compare(resAllocEndDate, relivingDate);
						
						
					    //Code For current Project					    
					    
						int date = 	dateTimeComparator.compare(relivingDate, today);
						System.out.println("date"+date);
						if(date<0)//if reliving date past/before date from today's date
						{
							logger.info("Reliving date past/before date from today's date :: " +
						"relivingDate - "+ relivingDate + " CurrentDate - "+ today);
							resAllocObj.setCurProj(false);
							resAllocObj.setAllocEndDate(relivingDate);
						}else if(date==0) {
							// if reliving date is equal to today's date
							logger.info("Reliving date is equal to today's date :: " + 
									"relivingDate - "+ relivingDate + " CurrentDate - "+ today);
							resAllocObj.setAllocEndDate(relivingDate);
						}else { // if reliving date is greater then today's date
							logger.info("if reliving date is greater then today's date");
							if((resAllocEndDate==null) || (endDate>0)){			//  if reliving date is greater then today's date && Either allocation end date is null or if allocation end date is greater then reliving date			
								
								logger.info(" Reliving date is greater than today's date && Either "
										+ "allocation end date is null or if allocation end date is "
										+ "greater then reliving date :: "+
										"resourceAllocEndDate - "+ resAllocEndDate + " relivingDate - "+ relivingDate);
								resAllocObj.setAllocEndDate(relivingDate);
							}
							
						}
						
						resourceAllocationService.saveOrupdate(resAllocObj);
					}
				
			
			logger.info("------------------setProjectEndDateAndNoStatus method end-----------");
		}catch(Exception exception) {
			isSuccess = false;
	           
			if(exception instanceof DAOException) {
                  serviceException.setErrCode(((DAOException) exception).getErrCode());
            }else {
                  ((RMSServiceException) serviceException).setErrCode("404");
            }
                  
            ((RMSServiceException) serviceException).setMessage(exception.getMessage());
            throw serviceException;
			
		}
		
		return isSuccess;

	}
	
	public List<InfogramInactiveResourceDTO> getInfogramInActiveResourceReport() {
		logger.info("InfogramActiveResourceServiceImpl :: getInfogramActiveResourceReport [Start] ");
		List<InfogramInactiveResourceDTO> inActiveResourceList =  infogramInactiveResourceDao.getAllInfogramInActiveResourcesReport();
		logger.info("InfogramActiveResourceServiceImpl getInfogramActiveResourceReport [End] ");
		return inActiveResourceList;
	}

}
