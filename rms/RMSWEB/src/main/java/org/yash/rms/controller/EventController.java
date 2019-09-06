package org.yash.rms.controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.yash.rms.domain.Event;
import org.yash.rms.domain.Resource;
import org.yash.rms.form.EventDataForm;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.EventService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping(value = "/events")
public class EventController {

	@Autowired
	@Qualifier("eventService")
	EventService eventServiceImpl;

	/*
	 * @Autowired @Qualifier("EventJsonMapper") EventJsonMapper jsonMapper;
	 */

	@Autowired
	JsonObjectMapper<Event> jsonMapper;

	private static final Logger logger = LoggerFactory
			.getLogger(EventController.class);

	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping(method = RequestMethod.POST )
	public ModelAndView createFromJson(@ModelAttribute EventDataForm eventDataForm,
			@RequestParam(value = "myEventId", required = false) String eventId,
			//@RequestParam(value = "eventTxt", required = false) String event,
			HttpServletRequest request, Model uiModel,
			BindingResult bindingResult) throws Exception {
		logger.info("------eventController createFromJsonArray method start------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		ServletContext context = request.getSession().getServletContext();
		eventDataForm.setId(eventId);

		Event event = eventServiceImpl.findById(Integer.parseInt(eventId));
		if(event!=null){
		eventDataForm.setEvent(event.getEvent());
		eventDataForm.setDescription(event.getDescription());
		}
		try {
			if(event!=null){
				eventDataForm.setCreatedId((String) context.getAttribute("userName"));
				//eventDataForm.setId(eventId);
				if (!eventServiceImpl.saveOrupdateEvent(eventDataForm)) {
					return null;
				}
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in createFromJsonArray method of event controller:"
					+ runtimeException);
			if (runtimeException instanceof DataIntegrityViolationException) {
				return null;
			}
		} catch (Exception exception) {
			logger.error("Exception occured in createFromJsonArray method of event controller:"
					+ exception);
			throw exception;
		}

		logger.info("------eventController createFromJson  method end------");
		return listOfEvent();

	}

	// headers = "Accept=application/json"
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listOfEvent() throws Exception {
		logger.info("------eventController list method start------");
		HttpHeaders headers = new HttpHeaders();
		ModelAndView modelAndView = new ModelAndView("events/list");
		List<Event> eventList = new ArrayList<Event>();
		try {
			eventList = eventServiceImpl.findAllEvents();
			modelAndView.addObject(Constants.EVENT, eventList);

			// Set Header values...
			Resource resource = userUtil.getLoggedInResource();
			try{
				if(resource
					      .getUploadImage()!=null && resource.getUploadImage().length>0){
					byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
					String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
					modelAndView.addObject("UserImage", base64EncodedUser);
				}else{
					modelAndView.addObject("UserImage", "");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
    		modelAndView.addObject("firstName", resource.getFirstName()
    				+ " " + resource.getLastName());
    		modelAndView.addObject("designationName", resource.getDesignationId().getDesignationName());
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(resource.getDateOfJoining());
    		int m = cal.get(Calendar.MONTH) + 1;
    		String months = new DateFormatSymbols().getMonths()[m - 1];
    		int year = cal.get(Calendar.YEAR);
    		modelAndView.addObject("DOJ", months + "-" + year);
    		modelAndView.addObject("ROLE", resource.getUserRole());
    		// End 
    		
		} catch (Exception exception) {
			logger.error("RuntimeException occured in list method of event controller:"
					+ exception);
			throw exception;
		}

		logger.info("------eventController list method end------");
		// return new ResponseEntity<String>(Event.toJsonArray(eventList),
		// headers, HttpStatus.OK);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> updateFromJson(@RequestBody String json,
			HttpServletRequest request) throws Exception {
		logger.info("------eventController updateFromJson method start------");
		ResponseEntity response = null;
		HttpHeaders headers = new HttpHeaders();
		ServletContext context = request.getSession().getServletContext();
		context.getAttribute("userName");
		headers.add("Content-Type", "application/json");
		// Convert Json Object to Domain Object
		org.yash.rms.domain.Event event = jsonMapper.fromJsonToObject(json,
				org.yash.rms.domain.Event.class);
		event.setLastUpdatedId((String) context.getAttribute("userName"));
		event.setLastupdatedTimestamp(new Date(System.currentTimeMillis()));
		if (event.getId() != 0) {
			Event eventRules = eventServiceImpl.findById(event.getId());
			populateEventRules(event, eventRules);
		}
		try {
			// Update the Event
			if (!eventServiceImpl.saveOrupdate(event)) {
				return new ResponseEntity<String>(headers,
						HttpStatus.EXPECTATION_FAILED);
			}
		} catch (RuntimeException runtimeException) {
			logger.error("RuntimeException occured in updateFromJson method of event controller:"
					+ runtimeException);
			throw runtimeException;
		} catch (Exception exception) {
			logger.error("Exception occured in updateFromJson method of event controller:"
					+ exception);
			throw exception;
		}
		
		String name=event.getEvent();
		event = eventServiceImpl.findByName(name);
		logger.info("------eventController updateFromJson method end------");
		response = new ResponseEntity<String>(jsonMapper.toJson(event),
				headers, HttpStatus.OK);
		return response;
		//return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Integer id)
			throws Exception {
		logger.info("------eventController createFromJsonArray method start------");
		
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		// Delete based on ID
		try {

			if (!eventServiceImpl.delete(id.intValue())) {
				jsonObject.put("status", "false");
				jsonArray.put(jsonObject);
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}

		} catch (Exception exception) {
			logger.error("Exception occured in createFromJsonArray method of event controller:"
					+ exception);
			return new ResponseEntity<String>("{ \"status\":\"FALSE\"}",headers, HttpStatus.EXPECTATION_FAILED);
//			throw exception;

		}
		logger.info("------eventController deleteFromJson  method end------");
		jsonObject.put("status", "true");
		jsonArray.put(jsonObject);
		return new ResponseEntity<String>(jsonArray.toString(),headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/loadEventData", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> loadEventData(
			@RequestParam(value = "eventId") int eventId) throws Exception {
		logger.info("------EventController loadEventData method start------");
		ResponseEntity response = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Event event = new Event();

		event = eventServiceImpl.findById(eventId);
		
		response = new ResponseEntity<String>(jsonMapper.toJson(event),
				headers, HttpStatus.OK);
		return response;
	}

	private void populateEventRules(Event event, Event eventRules) {
		// TODO Auto-generated method stub
		//Added by Pratyoosh//
		if (eventRules.getEmployeeId() == null) {
			event.setEmployeeId('N');
		}
			else{
				event.setEmployeeId(eventRules.getEmployeeId());
			}
		 
		if (eventRules.gethRCommentsTimestamp() == null) {
			event.sethRCommentsTimestamp('N');
		} 
		else{
			event.sethRCommentsTimestamp(eventRules.gethRCommentsTimestamp());
		}
		if (eventRules.gethRName() == null) {
			event.sethRName('N');
		}
		else{
			event.sethRName(eventRules.gethRName());
		}
		if (eventRules.gethRComments() == null) {
			event.sethRComments('N');
		}
		else{
			event.sethRComments(eventRules.gethRComments());
		}
		if (eventRules.getbUCommentsTimestamp() == null) {
			event.setbUCommentsTimestamp('N');
		}
		else{
			event.setbUCommentsTimestamp(eventRules.getbUCommentsTimestamp());
		}
		if (eventRules.getbUHComments() == null) {
			event.setbUHComments('N');
		}
		else{
			event.setbUHComments(eventRules.getbUHComments());
		}
		if (eventRules.getbUHName() == null) {
			event.setbUHName('N');
		}
		else{
			event.setbUHName(eventRules.getbUHName());
		}
		if (eventRules.getbGCommentsTimestamp() == null) {
			event.setbGCommentsTimestamp('N');
		}
		else{
			event.setbGCommentsTimestamp(eventRules.getbGCommentsTimestamp());
		}
		if (eventRules.getbGHComments() == null) {
			event.setbGHComments('N');
		}
		else{
			event.setbGHComments(eventRules.getbGHComments());
		}
		if (eventRules.getbGHName() == null) {
			event.setbGHName('N');
		}
		else{
			event.setbGHName(eventRules.getbGHName());
		}
		if (eventRules.getTransferDate() == null) {
			event.setTransferDate('N');
		}
		else{
			event.setTransferDate(eventRules.getTransferDate());
		}
		if (eventRules.getReleaseDate() == null) {
			event.setReleaseDate('N');
		}
		if (eventRules.getPenultimateAppraisal() == null) {
			event.setPenultimateAppraisal('N');
		}
		else{
			event.setPenultimateAppraisal(eventRules.getPenultimateAppraisal());
		}
		if (eventRules.getLastAppraisal() == null) {
			event.setLastAppraisal('N');
		}
		else{
			event.setLastAppraisal(eventRules.getLastAppraisal());
		}
		if (eventRules.getConfirmationDate() == null) {
			event.setConfirmationDate('N');
		}
		else{
			event.setConfirmationDate(eventRules.getConfirmationDate());
		}
		if (eventRules.getDateOfJoining() == null) {
			event.setDateOfJoining('N');
		}
		else{
			event.setDateOfJoining(eventRules.getDateOfJoining());
		}
		if (eventRules.getContactNumberTwo() == null) {
			event.setContactNumberTwo('N');
		}
		else{
			event.setContactNumberTwo(eventRules.getContactNumberTwo());
		}
		if (eventRules.getEmailId() == null) {
			event.setEmailId('N');
		}
		else{
			event.setEmailId(eventRules.getEmailId());
		}
		if (eventRules.getCurrentBuId() == null) {
			event.setCurrentBuId('N');
		}
		else{
			event.setCurrentBuId(eventRules.getCurrentBuId());
		}
		if (eventRules.getBuId() == null) {
			event.setBuId('N');
		}
		else{
			event.setBuId(eventRules.getBuId());
		}
		if (eventRules.getPayrollLocation() == null) {
			event.setPayrollLocation('N');
		}
		else{
			event.setPayrollLocation(eventRules.getPayrollLocation());
		}
		if (eventRules.getYashEmpId() == null) {
			event.setYashEmpId('N');
		}
		else{
			event.setYashEmpId(eventRules.getYashEmpId() );
		}
		if (eventRules.getContactNumber() == null ) {
			event.setContactNumber('N');
		}
		else{
			event.setContactNumber(eventRules.getContactNumber());
		}
		if (eventRules.getCurrentReportingManager() == null) {
			event.setCurrentReportingManager('N');
		}
		else{
			event.setCurrentReportingManager(eventRules.getCurrentReportingManager());
		}
		if (eventRules.getCurrentReportingTwo() == null) {
			event.setCurrentReportingTwo('N');
		}
		else{
			event.setCurrentReportingTwo(eventRules.getCurrentReportingTwo());
		}
		if (eventRules.getOwnership() == null) {
			event.setOwnership('N');
		}
		else{
			event.setOwnership(eventRules.getOwnership());
		}
		if (eventRules.getDesignationId() == null) {
			event.setDesignationId('N');
		}
		else{
			event.setDesignationId(eventRules.getDesignationId());
		}
		if (eventRules.getGradeId() == null) {
			event.setGradeId('N');
		}
		else{
			event.setGradeId(eventRules.getGradeId());
		}
		if (eventRules.getLocationId() == null) {
			event.setLocationId('N');
		}
		else{
			event.setLocationId(eventRules.getLocationId());
		}
		if (eventRules.getEmployeeCategory() == null) {
			event.setEmployeeCategory('N');
		}
		else{
			event.setEmployeeCategory(eventRules.getEmployeeCategory());
		}
		if (eventRules.getEndTransferDate() == null) {
			event.setEndTransferDate('N');
		}
		else{
			event.setEndTransferDate(eventRules.getEndTransferDate());
		}
		if (eventRules.getReleaseDate() == null) {
			event.setReleaseDate('N');
		}
		else{
			event.setReleaseDate(eventRules.getReleaseDate());
		}
		
	}

}
//Added by Pratyoosh//
