package demo;

import java.util.ArrayList;
import java.util.List;

public class demomethod {
	
	Map<String, List<DropdownRequestTms>> fetchRequestFormData = new ArrayList();
	
	List<DropdownRequestTms> technologyList = new ArrayList();
	List<TechnologyTMS> techList=TechnologyTMSDao.findAll();
	for(TechnologyTMS technologyTMS:techList){
		DropdownRequestTms technology = new DropdownRequestTms();
		technology.setId(technologyTMS.getTechnologyId());
		technology.setName(technologyTMS.getTechName());
		technologyList.add(technology);
	}
	
	List<DropdownRequestTms> locationList = new ArrayList();
	List<Location> locList=LocationDao.findAll();
	for(Location loc:locList){
		DropdownRequestTms location = new DropdownRequestTms();
		location.setId(locList.getLocationId);
		location.setName(locList.getLocation());
		locationList.add(location);
	}
	
	List<DropdownRequestTms> resourceList = new ArrayList();
	List<Resource> resList=ResourceDao.findAll();
	for(Resource res:resList){
		DropdownRequestTms resource = new DropdownRequestTms();
		resource.setId(resList.getEmployeeId());
		resource.setName(resList.getEmployeeName());
		resourceList.add(resource);
	}
	
	List<DropdownRequestTms> statusList = new ArrayList();
	List<Status> satList=StatusServiceDao.findAll();
	for(Status sat:satList){
		DropdownRequestTms status = new DropdownRequestTms();
		status.setId(satList.getRequestId());
		status.setName(satList.getTypeAction());
		statusList.add(status);
	}
	
	List<DropdownRequestTms> projectList = new ArrayList();
	List<Project> proList=ProjectService.findAll();
	for(Project pro:proList){
		DropdownRequestTms project = new DropdownRequestTms();
		project.setId(proList.getId());
		project.setName(proList.getProjectName());
		projectList.add(status);
	}
	
	List<DropdownRequestTms> trainingMode = new ArrayList();
	DropdownRequestTms mode1 = new DropdownRequestTms();
	mode.setId(1);
	mode.setName("ONLINE");
	
	DropdownRequestTms mode2 = new DropdownRequestTms();
	mode.setId(2);
	mode.setName("OFFLINE");
	
	trainingMode.add(mode1);	
	trainingMode.add(mode2);	
	
	List<DropdownRequestTms> trainingType = new ArrayList();
	DropdownRequestTms type1 = new DropdownRequestTms();
	mode.setId(1);
	mode.setName("OPEN");
	
	DropdownRequestTms type2 = new DropdownRequestTms();
	mode.setId(2);
	mode.setName("CLOSED");
	
	trainingMode.add(type1);	
	trainingMode.add(type2);
	
	List<DropdownRequestTms> trainingPriority = new ArrayList();
	DropdownRequestTms p1 = new DropdownRequestTms();
	mode.setId(1);
	mode.setName("HIGH");
	
	DropdownRequestTms p2 = new DropdownRequestTms();
	mode.setId(2);
	mode.setName("MEDIUM");
	
	DropdownRequestTms p3 = new DropdownRequestTms();
	mode.setId(3);
	mode.setName("LOW");
	
	trainingMode.add(p1);	
	trainingMode.add(p2);	
	trainingMode.add(p3);
	
	fetchRequestFormData.add(technologyList);
	fetchRequestFormData.add(locationList);
	fetchRequestFormData.add(resourceList);
	fetchRequestFormData.add(statusList);
	fetchRequestFormData.add(projectList);
	fetchRequestFormData.add(trainingMode);
	fetchRequestFormData.add(trainingType);
	fetchRequestFormData.add(trainingPriority);
	
	
	}
