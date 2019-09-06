package org.yash.rms.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.yash.rms.dao.ProjectAllocationDao;
import org.yash.rms.dao.ResourceDao;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.TimehrsEmployeeIdProjectView;
import org.yash.rms.dto.UserContextDetails;
import org.yash.rms.service.TimeHoursService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

@Component
public class ProjectHelper {

  @Autowired
  @Qualifier("ResourceDao")
  ResourceDao resourceDao;

  @Autowired
  @Qualifier("TimeHoursService")
  TimeHoursService timeHoursService;

  @Autowired
  ProjectAllocationDao projectAllocationDao;

  public Set<Project> getCurrentProjectForDeliveryManager(String activeOrAll,
      Integer currentLoggedInUserId) {


    List<Integer> addedResourceList = new ArrayList<Integer>();
    Set<Project> projectSet = new HashSet<Project>();
    UserContextDetails currentResource = UserUtil.getCurrentResource();
    List projectAllocatedList = new ArrayList<Integer>();
    List<Integer> empIdList = new ArrayList<Integer>();
    List<Project> projectNameList = new ArrayList<Project>();

    int id = currentResource.getEmployeeId();
    empIdList = resourceDao.findResourceIdByRM2RM1(id);
    addedResourceList.addAll(empIdList);
    projectNameList.addAll(
        projectAllocationDao.getProjectNameForManagerOnProject("active", currentLoggedInUserId));
    List<List> list = timeHoursService.managerViewForDelManager(empIdList, true, projectNameList);

    projectAllocatedList = list.get(1);
    for (Iterator i = projectAllocatedList.iterator(); i.hasNext();) {
      TimehrsEmployeeIdProjectView employeeIdProjectView = (TimehrsEmployeeIdProjectView) i.next();
      Project project = new Project();
      project = projectAllocationDao
          .findProjectsByProjectNameEquals(employeeIdProjectView.getProjectName()).get(0);
      if (project.getOffshoreDelMgr().getEmployeeId().intValue() == id) {
        project.setManagerReadonly(true);
      }
      projectSet.addAll(projectAllocationDao
          .findProjectsByProjectNameEquals(employeeIdProjectView.getProjectName()));

    }
    return projectSet;
  }

  public static void sortProjects(final String referenceColumn, final String sortOrder,
      List<org.yash.rms.dto.ProjectDTO> projectDisplay) {
    Collections.sort(projectDisplay, new Comparator<org.yash.rms.dto.ProjectDTO>() {
      public int compare(org.yash.rms.dto.ProjectDTO project1, org.yash.rms.dto.ProjectDTO project2) {
        if (("customerName").equalsIgnoreCase(referenceColumn)) {
          return compareProjectByFieldType(project1.getCustomerName(), project2.getCustomerName(),
              sortOrder);
        } else if (("offshoreDelMgr").equalsIgnoreCase(referenceColumn)) {
          return compareProjectByFieldType(project1.getOffshoreManager(),
              project2.getOffshoreManager(), sortOrder);
        } else if (("engagementMode").equalsIgnoreCase(referenceColumn)) {
          return compareProjectByFieldType(project1.getEngagementMode(),
              project2.getEngagementMode(), sortOrder);
        } else if (("onsiteDelMgr").equalsIgnoreCase(referenceColumn)) {
          return compareProjectByFieldType(project1.getOnsiteManager(), project2.getOnsiteManager(),
              sortOrder);
        } else if (("projectKickOff").equalsIgnoreCase(referenceColumn)) {
          return compareProjectByFieldType(project1.getProjectKickOffDate(),
              project2.getProjectKickOffDate(), sortOrder);
        } else if (("projectName").equalsIgnoreCase(referenceColumn)) {
          return compareProjectByFieldType(project1.getProjectName(), project2.getProjectName(),
              sortOrder);
        } else if (("plannedProjectSize").equalsIgnoreCase(referenceColumn)) {
          Integer plannedProjectSize1 = project1.getPlannedProjectSize();
          Integer plannedProjectSize2 = project2.getPlannedProjectSize();
          if (plannedProjectSize1 == null) {
            plannedProjectSize1 = 0;
          }

          if (plannedProjectSize2 == null) {
            plannedProjectSize2 = 0;
          }

          int compare = plannedProjectSize1 > plannedProjectSize2 ? 1
              : (plannedProjectSize1 < plannedProjectSize2 ? -1 : 0);
          return getResult(compare, sortOrder);
        }
        return 0;
      }

      private int compareProjectByFieldType(String field1, String field2, String sortOrder) {
        if (field1 == null)
          field1 = Constants.EMPTY;
        if (field2 == null)
          field2 = Constants.EMPTY;
        int compare = field1.compareToIgnoreCase(field2);
        return getResult(compare, sortOrder);
      }

      private int getResult(int compare, String sortOrder) {
        if (sortOrder.equalsIgnoreCase("asc")) {
          return compare;
        } else {
          return compare * (-1);
        }
      }

    });
  }

}
