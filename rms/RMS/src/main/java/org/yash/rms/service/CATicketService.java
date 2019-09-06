package org.yash.rms.service;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.yash.rms.domain.CATicket;
import org.yash.rms.domain.CATicketProcess;
import org.yash.rms.domain.CATicketSubProcess;
import org.yash.rms.domain.Crop;
import org.yash.rms.domain.DefectLog;
import org.yash.rms.domain.Landscape;
import org.yash.rms.domain.Project;
import org.yash.rms.domain.ReasonForSLAMissed;
import org.yash.rms.domain.ReasonHopping;
import org.yash.rms.domain.ReasonReopen;
import org.yash.rms.domain.Region;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.Rework;
import org.yash.rms.domain.RootCause;
import org.yash.rms.domain.SolutionReview;
import org.yash.rms.domain.T3Contribution;
import org.yash.rms.domain.Unit;
import org.yash.rms.form.CATicketForm;
import org.yash.rms.report.dto.dashboardFilter;
import org.yash.rms.util.CAViewTicketSearchCriteria;

@Service("CATicketService")
public interface CATicketService {
	public Serializable save(CATicket caTicket);

	public CATicket saveCATicket(CATicketForm form, Integer discrepencyId);

	public boolean editCATicket(CATicketForm form);

	public List<CATicket> findAllTickets();

	public List<CATicket> findUserWiseTicket(int currentUser);

	public List<CATicket> findActiveTickets(Integer page, Integer size,
			CAViewTicketSearchCriteria searchCriteria, String activeOrAll,
			String sSearch);

	public List<CATicket> getTicketsByPhase();

	public CATicket findTicketById(int id);

	public List<CATicketProcess> findProcess();

	public List<CATicketProcess> findProcessByModuleId(int moduleid);

	public List<Project> findModuleNameProjects();

	public List<T3Contribution> saveOrUpdateT3(T3Contribution t3Contribution);

	public List<SolutionReview> saveOrUpdateSolutionReview(
			SolutionReview solutionReview);

	public Boolean saveSolReview(SolutionReview review);

	public List<DefectLog> saveOrUpdateDefectLog(DefectLog log);

	public List<Crop> saveOrUpdateCrop(Crop crop);

	public List<Rework> saveOrUpdateRework(Rework rework);

	public List<CATicket> getEmployeeProjectName(List<Integer> projectId,
			int employeeId);

	public List<CATicket> getReveiwerProjectName(List<Integer> projectId,
			int employeeId);

	public List<CATicket> getTicketDetailsbyPhase(int empId, int moduleId,
			String phase,int loggedInId);

	public List<Object[]> findTicketStatusCountByEmployeeId(String employeeId,
			dashboardFilter dashboardFilter);

	public List<Landscape> getAllLandscape();

	public List<RootCause> getAllRootCause();

	public List<Region> getAllRegion();

	public List<Unit> getAllUnit();

	public List<CATicket> getIRMReveiwerProjectName(List<Integer> projectId,
			List<Integer> employeeId);

	public List<CATicket> getIRMAssigneeReviewerList(List<Integer> projectIds);

	public List<Object[]> findTeamTicketStatusCountByEmployeeId(
			String employeeId, dashboardFilter dashboardFilter);

	public List<Object[]> findMyPerformance(String employeeId);

	public List<Object[]> findMyTeamPerformance(String employeeId);

	public List<CATicketSubProcess> findSubprocess(int processId);

	public List<Region> findRegionByUnit(int unitId);

	public List<CATicket> findMyTickets(int employeeId,
			dashboardFilter dashboardFilter);

	public List<CATicket> findMyTeamTickets(int employeeId,
			dashboardFilter dashboardFilter);

	public void saveChangeFunctionality(int ticketId, int assigneeName,
			String priority, String reviewer);

	public void saveTransferFunctionality(int ticketId, String assigneeName,
			int moduleId, String iRMName, String justified, String reason);

	public List<Project> findModuleByEmployee(int employeeId);

	public SolutionReview getLatestReviewDateById(int caTicketId);

	public Crop getLatestCropById(int caTicketId);

	public boolean deleteT3(int id);

	public boolean deleteSolutionReview(int id);

	public boolean deleteDefectLog(int id);

	public boolean deleteCrop(int id);

	public boolean deleteRework(int id);

	public List<CATicket> getTicketsForReview(int currentLoggedInUserId);

	public CATicket getTicketByTicketNumber(int ticketId);

	public boolean isTicketAlreadyExist(BigInteger ticketNumber);

	public List<Resource> getEmployeeByModule(int projectId);

	public CATicket getTicketByTicketNumber(BigInteger caticketNumber);

	public List<String> getDistinctRegionNames();

	public Unit getUnit(int id);

	public Region getRegion(int id);

	public Landscape getLandscape(int id);

	public String getSolutionReadyReviewFlag(BigInteger caTicketNo);

	public String getDescription(BigInteger caTicketNo);

	public int getAssingeeId(BigInteger caTicketNo);

	public int getReviewerId(BigInteger caTicketNo);

	public String getAging(BigInteger caTicketNo);

	public Date getCreationDate(BigInteger caTicketNo);

	public int getModule(BigInteger caTicketNo);

	public int getUnit(BigInteger caTicketNo);

	public int getLandscape(BigInteger caTicketNo);

	public int getRegion(BigInteger caTicketNo);

	public List<ReasonHopping> getAllReasonForHopping();

	public List<ReasonReopen> getAllReasonForReopen();

	public List<ReasonForSLAMissed> getAllReasonForSLAMissed();

	public List<Object[]> findForMyReviewTicketStatusCountByEmployeeId(
			String employeeId, dashboardFilter dashboardFilter);
}
