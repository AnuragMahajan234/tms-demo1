package org.yash.rms.controller;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yash.rms.domain.Activity;
import org.yash.rms.domain.EngagementModel;
import org.yash.rms.domain.Phase;
import org.yash.rms.domain.Resource;
import org.yash.rms.domain.SEPGPhases;
import org.yash.rms.json.mapper.JsonObjectMapper;
import org.yash.rms.service.ActivityService;
import org.yash.rms.service.EngagementModelService;
import org.yash.rms.service.PhaseService;
import org.yash.rms.service.SEPGPhasesService;
import org.yash.rms.util.Constants;
import org.yash.rms.util.UserUtil;

@Controller
@RequestMapping("/sepgPhases")
public class SEPGPhasesController {

	@Autowired
	@Qualifier("SEPGPhasesService")
	private SEPGPhasesService sepgPhasesService;

	@Autowired
	@Qualifier("PhaseService")
	private PhaseService phasesService;

	@Autowired
	JsonObjectMapper<Phase> jsonPhaseMapper;

	@Autowired
	@Qualifier("EngagementModelService")
	EngagementModelService engagementModelService;

	@Autowired
	@Qualifier("ActivityService")
	ActivityService activityService;

	private static final Logger logger = LoggerFactory
			.getLogger(SEPGPhasesController.class);

	@Autowired
	private UserUtil userUtil;
	
	@RequestMapping(method = RequestMethod.GET)
	public String sepgGetList(Model uiModel) {
		logger.info("----------start sepgGetList-----------");
		List<Phase> phaseList = phasesService.findAll();
		List<SEPGPhases> sepgPhasesList = null;
		Map<Integer, Integer> engagementSize = new HashMap<Integer, Integer>();
		Map<Integer, Integer> activitySize = new HashMap<Integer, Integer>();
		Resource resource = userUtil.getLoggedInResource();
		List<Integer> engagementCount = new ArrayList<Integer>();
		List<Integer> activityCount = new ArrayList<Integer>();

		for (Phase phase : phaseList) {

			Set<Integer> engagementIdList = new HashSet<Integer>();
			Set<Integer> activityIdList = new HashSet<Integer>();
			sepgPhasesList = sepgPhasesService.findByPhaseId((phase.getId()));

			if (sepgPhasesList == null || sepgPhasesList.isEmpty()) {
				engagementSize.put(phase.getId(), engagementIdList.size());
				activitySize.put(phase.getId(), activityIdList.size());

				engagementCount.add(engagementIdList.size());
				activityCount.add(activityIdList.size());
			}

			if (!sepgPhasesList.isEmpty() && sepgPhasesList != null) {

				for (SEPGPhases sepgPhases : sepgPhasesList) {
					EngagementModel engagementModel = sepgPhases
							.getEngagementModel();
					if (engagementModel != null) {
						Integer engagementId = sepgPhases.getEngagementModel()
								.getId();
						engagementIdList.add(engagementId);
					}
					Activity activity = sepgPhases.getActivity();
					if (activity != null) {
						Integer activityId = sepgPhases.getActivity().getId();
						activityIdList.add(activityId);
					}
				}
				engagementSize.put(phase.getId(), engagementIdList.size());
				activitySize.put(phase.getId(), activityIdList.size());
				engagementCount.add(engagementIdList.size());
				activityCount.add(activityIdList.size());
			}
		}
		// List<Integer> engagementCount = new
		// ArrayList<Integer>(engagementSize.values());
		// List<Integer> activityCount = new
		// ArrayList<Integer>(activitySize.values());
		uiModel.addAttribute(Constants.ENGAGEMENTMODAL_COUNT, engagementCount);
		uiModel.addAttribute(Constants.ACTIVITY_COUNT, activityCount);
		uiModel.addAttribute(Constants.ENGAGEMENTMODEL,
				engagementModelService.findAll());
		uiModel.addAttribute(Constants.ACTIVITIES,
				activityService.findSepgActivity(Constants.SEPG));
		uiModel.addAttribute(Constants.SEPG_PHASES, phasesService.findAll());

		// Set Header values...
		
		try{
			if(resource
				      .getUploadImage()!=null && resource.getUploadImage().length>0){
				byte[] encodeBase64UserImage = Base64.encodeBase64(resource.getUploadImage());
				String base64EncodedUser = new String(encodeBase64UserImage, "UTF-8");
				uiModel.addAttribute("UserImage", base64EncodedUser);
			}else{
				uiModel.addAttribute("UserImage", "");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
				uiModel.addAttribute("firstName", resource.getFirstName()
						+ " " + resource.getLastName());
				uiModel.addAttribute("designationName", resource.getDesignationId().getDesignationName());
				Calendar cal = Calendar.getInstance();
				cal.setTime(resource.getDateOfJoining());
				int m = cal.get(Calendar.MONTH) + 1;
				String months = new DateFormatSymbols().getMonths()[m - 1];
				int year = cal.get(Calendar.YEAR);
				uiModel.addAttribute("DOJ", months + "-" + year);
				uiModel.addAttribute("ROLE", resource.getUserRole());
				// End 
				
		logger.info("----------end sepgGetList-----------");
		return "sepgPhases/sepgGetList";
	}

	@RequestMapping(value = "/saveSEPG", method = RequestMethod.GET, produces = "text/html")
	public @ResponseBody
	String saveSEPG(@RequestParam(value = "phaseId") Integer phaseId,
			@RequestParam(value = "engagementIdList") int[] engagementId,
			@RequestParam(value = "activityIdList") int[] activityId) {

		logger.info("------------- SEPGPhasesController start saveSEPG method --------------");
		List<SEPGPhases> sepgPhasesList = new ArrayList<SEPGPhases>();
		if (phaseId != null) {
			for (int engagId : engagementId) {
				if (activityId.length > 0) {
					for (int activeId : activityId) {
						SEPGPhases sepgPhases = new SEPGPhases();
						Phase phase = new Phase();
						EngagementModel engagementModel = new EngagementModel();
						Activity activity = new Activity();
						phase.setId(phaseId);
						engagementModel.setId(engagId);
						activity.setId(activeId);
						sepgPhases.setPhase(phase);
						sepgPhases.setActivity(activity);
						sepgPhases.setEngagementModel(engagementModel);
						sepgPhasesList.add(sepgPhases);

					}
				} else {
					SEPGPhases sepgPhases = new SEPGPhases();
					Phase phase = new Phase();
					EngagementModel engagementModel = new EngagementModel();
					// Activity activity = new Activity();
					phase.setId(phaseId);
					engagementModel.setId(engagId);
					// activity.setId(0);
					sepgPhases.setPhase(phase);
					// sepgPhases.setActivity(activity);
					sepgPhases.setEngagementModel(engagementModel);
					sepgPhasesList.add(sepgPhases);
				}

			}
		}
		sepgPhasesService.save(sepgPhasesList);
		sepgPhasesService.findAll();
		logger.info("------------- SEPGPhasesController end saveSEPG method --------------");
		return "sepgPhases/sepgGetList";

	}

	@RequestMapping(value = "/savePhase/{phaseName}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String savePhase(@PathVariable String phaseName) {
		logger.info("------------- SEPGPhasesController start savePhase method --------------");
		Phase phase = null;
		boolean isDuplicate = phasesService.isAlreadyExist(phaseName);
		Serializable id = null;
		if (!isDuplicate) {
			phase = new Phase();
			phase.setPhasesName(phaseName);
			id = phasesService.save(phase);
		}
		JSONObject jsonObject = new JSONObject();
		if (phase != null) {

			// jsonObject.put("phaseName", phase.getPhasesName());
			List<Phase> phaseList = phasesService.findAll();
			jsonObject = getModelByPhaseId(Integer.parseInt(id + ""));
			jsonObject.put("result", true);
			jsonObject.put("phaseId", id);
			jsonObject.put("phaseName", phaseName);
			jsonObject.put("rawCount", phaseList.size());
		} else {
			jsonObject.put("result", false);
			jsonObject.put("phaseName", phaseName);
		}

		logger.info("------------- SEPGPhasesController end savePhase method --------------");
		// return jsonPhaseMapper.toJson(phase);
		return jsonObject.toString();

	}

	// updateSEPG
	@RequestMapping(value = "/editSEPG/{id}/{phaseName}", method = RequestMethod.POST)
	public @ResponseBody
	String editSEPG(@PathVariable Integer id, @PathVariable String phaseName,
			Model uiModel) {
		// JSONObject jsonObject = new JSONObject();
		// JSONObject json = new JSONObject();
		Phase phase = null;
		boolean isDuplicate = false;
		try {
			logger.info("------------- SEPGPhasesController start editSEPG method --------------");
			if (id != null) {

				isDuplicate = phasesService.isAlreadyExist(phaseName);
				if (!isDuplicate) {
					phase = phasesService.getPhase(id);
					phase.setPhasesName(phaseName);
					phasesService.update(phase);
				}
				// List<Integer> engagementIdList = new ArrayList<Integer>();
				// List<Integer> activityIdList = new ArrayList<Integer>();
				// List<SEPGPhases> sepgPhasesList = sepgPhasesService
				// .findByPhaseId(id);
				// if (!sepgPhasesList.isEmpty() && sepgPhasesList != null) {
				// for (SEPGPhases sepgPhases : sepgPhasesList) {
				// Integer engagementId = sepgPhases.getEngagementModel()
				// .getId();
				// engagementIdList.add(engagementId);
				// Integer activityId = sepgPhases.getActivity().getId();
				// activityIdList.add(activityId);
				// }
				//
				// //
				// sepgPhaseModel.setLeftSepgEngagement(engagementModelService
				// // .findLeftSepgEngagement(engagementIdList));
				// JSONObject engageFromJson = new JSONObject();
				// for (EngagementModel engagementModel : engagementModelService
				// .findLeftSepgEngagement(engagementIdList)) {
				//
				// engageFromJson.put(engagementModel.getId() + "",
				// engagementModel.getEngagementModelName());
				//
				// }
				// json.put("engageFrom", engageFromJson);
				//
				// JSONObject engageToJson = new JSONObject();
				// for (EngagementModel engagementModel : engagementModelService
				// .findSelectSepgEngagement(engagementIdList)) {
				// engageToJson.put(engagementModel.getId() + "",
				// engagementModel.getEngagementModelName());
				//
				// }
				// json.put("engageTo", engageToJson);
				//
				// JSONObject activityFromJson = new JSONObject();
				// for (Activity activity : activityService
				// .findLeftSepgActivity(Constants.SEPG,
				// activityIdList)) {
				//
				// activityFromJson.put(activity.getId() + "",
				// activity.getActivityName());
				//
				// }
				// json.put("activityFrom", activityFromJson);
				//
				// JSONObject activityToJson = new JSONObject();
				// for (Activity activity : activityService
				// .findSelectSepgActivity(activityIdList)) {
				//
				// activityToJson.put(activity.getId() + "",
				// activity.getActivityName());
				//
				// }
				// json.put("activityTo", activityToJson);
				// }
			}
			// sepgPhasesService.findAll();
			// logger.info("------------- SEPGPhasesController end editSEPG method --------------");
			// return "sepgPhases/sepgGetList";
			JSONObject jsonObject = new JSONObject();
			if (phase != null) {
				jsonObject.put("result", true);
				jsonObject.put("phaseName", phase.getPhasesName());
			} else {
				jsonObject.put("result", false);
				jsonObject.put("phaseName", phaseName);
			}

			// jsonObject.put("sepgPhases", json);
			// System.out.println("jsonObject.toString(): "+jsonObject.toString());
			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// Get SEPG List by phase id
	@RequestMapping(value = "/sepgListByPhaseId/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String getSEPGPhasesList(@PathVariable Integer id, Model uiModel) {

		return getModelByPhaseId(id).toString();
	}

	@RequestMapping(value = "/deleteSEPG/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String deleteSEPG(@PathVariable Integer id) {

		logger.info("------------- SEPGPhasesController start deleteSEPG method --------------");
		if (id != null) {
			List<SEPGPhases> sepgPhasesList = sepgPhasesService
					.findByPhaseId(id);
			if (!sepgPhasesList.isEmpty() && sepgPhasesList != null) {
				for (SEPGPhases sepgPhases : sepgPhasesList) {
					sepgPhasesService.delete(sepgPhases);
				}
			}
			// if(id!=null){
			Phase phase = phasesService.getPhase(id);
			phasesService.delete(phase);
		}
		sepgPhasesService.findAll();
		logger.info("------------- SEPGPhasesController end deleteSEPG method --------------");
		return "sepgPhases/sepgGetList";

	}

	@RequestMapping(value = "/updateSEPG", method = RequestMethod.GET, produces = "text/html")
	public @ResponseBody
	String updateSEPG(@RequestParam(value = "phaseId") Integer phaseId,
			@RequestParam(value = "engagementIdList") int[] engagementId,
			@RequestParam(value = "activityIdList") int[] activityId) {

		logger.info("------------- SEPGPhasesController start updateSEPG method --------------");
		String phaseName = null;
		if (phaseId != null) {
			List<SEPGPhases> sepgPhasesList = sepgPhasesService
					.findByPhaseId(phaseId);
			if (!sepgPhasesList.isEmpty() && sepgPhasesList != null) {
				for (SEPGPhases sepgPhases : sepgPhasesList) {
					sepgPhasesService.delete(sepgPhases);
				}

			}
			sepgPhasesList = null;
			sepgPhasesList = new ArrayList<SEPGPhases>();
			for (int engagId : engagementId) {
				if (activityId.length > 0) {
					for (int activeId : activityId) {
						SEPGPhases sepgPhases = new SEPGPhases();
						Phase phase = new Phase();
						EngagementModel engagementModel = new EngagementModel();
						Activity activity = new Activity();
						phase.setId(phaseId);
						engagementModel.setId(engagId);
						activity.setId(activeId);
						sepgPhases.setPhase(phase);
						sepgPhases.setActivity(activity);
						sepgPhases.setEngagementModel(engagementModel);
						sepgPhasesList.add(sepgPhases);

					}
				} else {
					SEPGPhases sepgPhases = new SEPGPhases();
					Phase phase = new Phase();
					EngagementModel engagementModel = new EngagementModel();
					// Activity activity = new Activity();
					phase.setId(phaseId);
					engagementModel.setId(engagId);
					// activity.setId(0);
					sepgPhases.setPhase(phase);
					// sepgPhases.setActivity(activity);
					sepgPhases.setEngagementModel(engagementModel);
					sepgPhasesList.add(sepgPhases);
				}
			}
			sepgPhasesService.save(sepgPhasesList);

		}
		sepgPhasesService.findAll();
		phasesService.findAll();
		logger.info("------------- SEPGPhasesController end updateSEPG method --------------");
		return "sepgPhases/sepgGetList";

	}

	@RequestMapping(value = "/cancelSepg", method = RequestMethod.GET)
	public @ResponseBody
	String cancelSepg(@RequestParam(value = "phaseId") Integer phaseId) {
		logger.info("------------- SEPGPhasesController start cancelSepg method --------------");
		logger.info("------------- SEPGPhasesController end cancelSepg method --------------");
		return "sepgPhases/sepgGetList";
	}

	public JSONObject getModelByPhaseId(Integer phaseId) {
		Phase phase = new Phase();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			logger.info("------------- SEPGPhasesController start SEPG Phase List method --------------");
			if (phaseId != null) {
				phase = phasesService.getPhase(phaseId);

				List<Integer> engagementIdList = new ArrayList<Integer>();
				List<Integer> activityIdList = new ArrayList<Integer>();
				List<SEPGPhases> sepgPhasesList = sepgPhasesService
						.findByPhaseId(phaseId);
				if (!sepgPhasesList.isEmpty() && sepgPhasesList != null) {
					for (SEPGPhases sepgPhases : sepgPhasesList) {
						Integer engagementId = sepgPhases.getEngagementModel()
								.getId();
						engagementIdList.add(engagementId);
						if (sepgPhases.getActivity() != null) {
							Integer activityId = sepgPhases.getActivity()
									.getId();
							activityIdList.add(activityId);
						}
					}

					// sepgPhaseModel.setLeftSepgEngagement(engagementModelService
					// .findLeftSepgEngagement(engagementIdList));
					JSONObject engageFromJson = new JSONObject();
					for (EngagementModel engagementModel : engagementModelService
							.findLeftSepgEngagement(engagementIdList)) {

						engageFromJson.put(engagementModel.getId() + "",
								engagementModel.getEngagementModelName());

					}
					json.put("engageFrom", engageFromJson);

					JSONObject engageToJson = new JSONObject();
					for (EngagementModel engagementModel : engagementModelService
							.findSelectSepgEngagement(engagementIdList)) {
						engageToJson.put(engagementModel.getId() + "",
								engagementModel.getEngagementModelName());

					}
					json.put("engageTo", engageToJson);
					if (activityIdList.size() > 0) {
						JSONObject activityFromJson = new JSONObject();
						for (Activity activity : activityService
								.findLeftSepgActivity(Constants.SEPG,
										activityIdList)) {

							activityFromJson.put(activity.getId() + "",
									activity.getActivityName());

						}
						json.put("activityFrom", activityFromJson);

						JSONObject activityToJson = new JSONObject();
						for (Activity activity : activityService
								.findSelectSepgActivity(activityIdList)) {

							activityToJson.put(activity.getId() + "",
									activity.getActivityName());

						}
						json.put("activityTo", activityToJson);
					} else {
						JSONObject activityFromJson = new JSONObject();
						for (Activity activity : activityService
								.findSepgActivity(Constants.SEPG)) {

							activityFromJson.put(activity.getId() + "",
									activity.getActivityName());

						}
						json.put("activityFrom", activityFromJson);

						JSONObject activityToJson = new JSONObject();
						json.put("activityTo", activityToJson);
					}
				} else {
					JSONObject engageFromJson = new JSONObject();
					for (EngagementModel engagementModel : engagementModelService
							.findAll()) {

						engageFromJson.put(engagementModel.getId() + "",
								engagementModel.getEngagementModelName());

					}
					json.put("engageFrom", engageFromJson);

					JSONObject engageToJson = new JSONObject();
					json.put("engageTo", engageToJson);

					JSONObject activityFromJson = new JSONObject();
					for (Activity activity : activityService
							.findSepgActivity(Constants.SEPG)) {

						activityFromJson.put(activity.getId() + "",
								activity.getActivityName());

					}
					json.put("activityFrom", activityFromJson);

					JSONObject activityToJson = new JSONObject();
					json.put("activityTo", activityToJson);

				}
			}
			// sepgPhasesService.findAll();
			logger.info("------------- SEPGPhasesController end SEPG Phase List method --------------");
			// return "sepgPhases/sepgGetList";
			// JSONObject jsonObject = new JSONObject();
			// jsonObject.put("sepgPhaseModel", sepgPhaseModel);
			jsonObject.put("phaseName", phase.getPhasesName());
			jsonObject.put("sepgPhases", json);
			System.out.println("jsonObject.toString(): "
					+ jsonObject.toString());
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
