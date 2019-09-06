package org.yash.rms.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.rms.dao.CATicketDao;
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
import org.yash.rms.service.CATicketService;
import org.yash.rms.util.CAViewTicketSearchCriteria;
import org.yash.rms.util.DozerMapperUtility;

@Service("CATicketService")
public class CATicketServiceImpl implements CATicketService {

	@Autowired
	CATicketDao caTicketDao;

	@Autowired
	private DozerMapperUtility mapperUtility;

	public Serializable save(CATicket caTicket) {
		return caTicketDao.save(caTicket);
	}

	public CATicket saveCATicket(CATicketForm form, Integer discrepencyId) {

		return caTicketDao.saveCATicket(
				(mapperUtility.convertCATicketFormDTOCATicketDomain(form)),
				discrepencyId);
	}

	public boolean editCATicket(CATicketForm form) {

		return caTicketDao.editCATicket((mapperUtility
				.convertCATicketFormDTOCATicketDomain(form)));
	}

	public List<CATicket> findAllTickets() {
		return caTicketDao.findAllTickets();
	}

	public List<CATicket> getTicketsByPhase() {
		return caTicketDao.getTicketsByPhase();
	}

	public List<CATicket> findActiveTickets(Integer page, Integer size,
			CAViewTicketSearchCriteria searchCriteria, String activeOrAll,
			String sSearch) {
		return null;
	}

	public CATicket findTicketById(int id) {
		return caTicketDao.findTicketById(id);
	}

	public List<CATicketProcess> findProcess() {
		return caTicketDao.findProcess();

	}

	public List<Project> findModuleNameProjects() {
		return caTicketDao.findModuleNameProjects();

	}

	public List<CATicketProcess> findProcessByModuleId(int moduleid) {
		return caTicketDao.findProcessByModuleId(moduleid);

	}

	public List<CATicket> findUserWiseTicket(int currentUser) {

		return caTicketDao.findUserWiseTicket(currentUser);
	}

	public List<T3Contribution> saveOrUpdateT3(T3Contribution t3Contribution) {
		return caTicketDao.saveOrUpdateT3(t3Contribution);
	}

	public Boolean saveSolReview(SolutionReview review) {
		return caTicketDao.saveSolReview(review);
	}

	public List<DefectLog> saveOrUpdateDefectLog(DefectLog log) {
		return caTicketDao.saveOrUpdateDefectLog(log);
	}

	public List<Crop> saveOrUpdateCrop(Crop crop) {
		return caTicketDao.saveOrUpdateCrop(crop);
	}

	public List<Rework> saveOrUpdateRework(Rework rework) {
		return caTicketDao.saveOrUpdateRework(rework);
	}

	public List<CATicket> getEmployeeProjectName(List<Integer> projectId,
			int employeeId) {
		return caTicketDao.getEmployeeProjectName(projectId, employeeId);

	}

	public List<CATicket> getReveiwerProjectName(List<Integer> projectId,
			int employeeId) {
		return caTicketDao.getReveiwerProjectName(projectId, employeeId);
	}

	public List<CATicket> getTicketDetailsbyPhase(int empId, int moduleId,
			String phase, int loggedInId) {
		return caTicketDao.getTicketDetailsbyPhase(empId, moduleId, phase, loggedInId);
	}

	public List<Object[]> findTicketStatusCountByEmployeeId(String employeeId,
			dashboardFilter dashboardFilter) {
		return caTicketDao.findTicketStatusCountByEmployeeId(employeeId,
				dashboardFilter);
	}

	public List<Landscape> getAllLandscape() {
		return caTicketDao.getAllLandscape();
	}

	public List<Region> getAllRegion() {
		return caTicketDao.getAllRegion();
	}

	public List<Unit> getAllUnit() {
		return caTicketDao.getAllUnit();
	}

	public List<Object[]> findTeamTicketStatusCountByEmployeeId(
			String employeeId, dashboardFilter dashboardFilter) {
		return caTicketDao.findTeamTicketStatusCountByEmployeeId(employeeId,
				dashboardFilter);
	}

	public List<Object[]> findMyPerformance(String employeeId) {
		return caTicketDao.findMyPerformance(employeeId);
	}

	public List<Object[]> findMyTeamPerformance(String employeeId) {
		return caTicketDao.findMyTeamPerformance(employeeId);
	}

	public List<CATicket> getIRMReveiwerProjectName(List<Integer> projectId,
			List<Integer> employeeId) {
		return caTicketDao.getIRMReveiwerProjectName(projectId, employeeId);
	}

	public List<CATicket> getIRMAssigneeReviewerList(List<Integer> projectIds) {
		return caTicketDao.getIRMAssigneeReviewerList(projectIds);
	}

	public List<CATicketSubProcess> findSubprocess(int processId) {
		return caTicketDao.findSubProcesses(processId);
	}

	public List<Region> findRegionByUnit(int unitId) {
		return caTicketDao.findRegionByUnit(unitId);
	}

	public List<CATicket> findMyTickets(int employeeId,
			dashboardFilter dashboardFilter) {
		return caTicketDao.findMyTickets(employeeId, dashboardFilter);
	}

	public List<CATicket> findMyTeamTickets(int employeeId,
			dashboardFilter dashboardFilter) {
		return caTicketDao.findMyTeamTickets(employeeId, dashboardFilter);
	}

	public List<RootCause> getAllRootCause() {
		return caTicketDao.getAllRootCause();
	}

	public List<SolutionReview> saveOrUpdateSolutionReview(
			SolutionReview solutionReview) {
		return caTicketDao.saveOrUpdateSolutionReview(solutionReview);
	}

	public void saveChangeFunctionality(int ticketId, int assigneeName,
			String priority, String reviewer) {
		caTicketDao.saveChangeFunctionality(ticketId, assigneeName, priority,
				reviewer);
	}

	public void saveTransferFunctionality(int ticketId, String assigneeName,
			int moduleId, String iRMName, String justified, String reason) {
		caTicketDao.saveTransferFunctionality(ticketId, assigneeName, moduleId,
				iRMName, justified, reason);

	}

	public List<Project> findModuleByEmployee(int employeeId) {
		return caTicketDao.findModuleByEmployee(employeeId);
	}

	public SolutionReview getLatestReviewDateById(int caTicketId) {
		return caTicketDao.getLatestReviewDateById(caTicketId);
	}

	public boolean deleteT3(int id) {
		return caTicketDao.deleteT3(id);
	}

	public boolean deleteSolutionReview(int id) {
		return caTicketDao.deleteSolutionReview(id);
	}

	public boolean deleteDefectLog(int id) {
		return caTicketDao.deleteDefectLog(id);
	}

	public boolean deleteCrop(int id) {
		return caTicketDao.deleteCrop(id);
	}

	public boolean deleteRework(int id) {
		return caTicketDao.deleteRework(id);
	}

	public List<CATicket> getTicketsForReview(int currentLoggedInUserId) {
		return caTicketDao.getTicketsForReview(currentLoggedInUserId);
	}

	public CATicket getTicketByTicketNumber(int ticketId) {
		return caTicketDao.getTicketByTicketNumber(ticketId);
	}

	public boolean isTicketAlreadyExist(BigInteger ticketNumber) {
		return caTicketDao.isTicketAlreadyExist(ticketNumber);
	}

	public List<Resource> getEmployeeByModule(int projectId) {
		return caTicketDao.getEmployeeByModule(projectId);
	}

	public CATicket getTicketByTicketNumber(BigInteger caticketNumber) {

		return caTicketDao.getTicketByTicketNumber(caticketNumber);
	}

	public List<String> getDistinctRegionNames() {

		return caTicketDao.getDistinctRegionNames();
	}

	public Unit getUnit(int id) {
		return caTicketDao.getUnit(id);
	}

	public Region getRegion(int id) {
		return caTicketDao.getRegion(id);
	}

	public Landscape getLandscape(int id) {
		return caTicketDao.getLandscape(id);
	}

	public String getSolutionReadyReviewFlag(BigInteger caTicketNo) {

		return caTicketDao.getSolutionReadyReviewFlag(caTicketNo);
	}

	public String getDescription(BigInteger caTicketNo) {
		return caTicketDao.getDescription(caTicketNo);
	}

	public int getAssingeeId(BigInteger caTicketNo) {

		return caTicketDao.getAssingeeId(caTicketNo);
	}

	public int getReviewerId(BigInteger caTicketNo) {

		return caTicketDao.getReviewerId(caTicketNo);
	}

	public String getAging(BigInteger caTicketNo) {

		return caTicketDao.getAging(caTicketNo);
	}

	public Date getCreationDate(BigInteger caTicketNo) {

		return caTicketDao.getCreationDate(caTicketNo);
	}

	public int getModule(BigInteger caTicketNo) {

		return caTicketDao.getModule(caTicketNo);
	}

	public int getUnit(BigInteger caTicketNo) {

		return caTicketDao.getUnitId(caTicketNo);
	}

	public int getLandscape(BigInteger caTicketNo) {
		return caTicketDao.getLandscapeId(caTicketNo);
	}

	public int getRegion(BigInteger caTicketNo) {
		return caTicketDao.getRegionId(caTicketNo);
	}

	public List<ReasonHopping> getAllReasonForHopping() {
		// TODO Auto-generated method stub
		return caTicketDao.getAllReasonForHopping();
	}

	public List<ReasonReopen> getAllReasonForReopen() {
		// TODO Auto-generated method stub
		return caTicketDao.getAllReasonForReopen();
	}

	public List<ReasonForSLAMissed> getAllReasonForSLAMissed() {
		// TODO Auto-generated method stub
		return caTicketDao.getAllReasonForSLAMissed();
	}

	public Crop getLatestCropById(int caTicketId) {
		// TODO Auto-generated method stub
		return caTicketDao.getLatestCropById(caTicketId);
	}

	public List<Object[]> findForMyReviewTicketStatusCountByEmployeeId(
			String employeeId, dashboardFilter dashboardFilter) {
		// TODO Auto-generated method stub
		return caTicketDao.findForMyReviewTicketStatusCountByEmployeeId(
				employeeId, dashboardFilter);
	}
}
