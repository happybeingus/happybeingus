/**
 * 
 *//*
package com.neu.msd.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.neu.msd.dao.AdminDao;
import com.neu.msd.dao.UserDao;
import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.ActivityType;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Answer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.entities.Version;
import com.neu.msd.exception.AdminException;
import com.neu.msd.service.AdminService;

*//**
 * @author Harsh
 *
 *//*
@Service("adminService")
public class AdminServiceImpl implements AdminService {
	
	Logger LOGGER = Logger.getLogger(AdminServiceImpl.class);

	@Autowired
	private AdminDao adminDao;
	
	
	private final String IMAGE_ABSOLUTE_PATH ="/usr/hbu/resources/images/";
	private final String IMAGE_RELATIVE_PATH ="resources/images/";
	private final String VIDEO_ABSOLUTE_PATH ="/usr/hbu/resources/videos/";
	private final String VIDEO_RELATIVE_PATH ="resources/videos/";
	private final int VIDEO_TEMPLATE_ID =1;
	private final int IMAGE_TEMPLATE_ID =2;
	private final int MCQ_TEMPLATE_ID =3;
	private final int INFORMATION_TEMPLATE_ID =4;
	private final int FLIP_TEMPLATE_ID =5;
	private final int MAX_FLIP_OPTION =6;
	
	 (non-Javadoc)
	 * @see com.neu.msd.service.AdminServie#loadTopics()
	 
	@Transactional
	public List<Topic> loadTopics(Map<Integer, ActivityContainer> containerMap) throws AdminException {
		LOGGER.debug("AdminServiceImpl: loadTopics: START");
		
		List<Topic> allTopics = adminDao.loadTopics();
		
		loadTopicsWithActivityContainers(containerMap, allTopics, -1);
		
		LOGGER.debug("AdminServiceImpl: loadTopics: END");
		return allTopics;
	}

	*//**
	 * @param containerActivitiesMap 
	 * @param containerMap 
	 * @param allTopics
	 * @throws AdminException
	 *//*
	public void loadTopicsWithActivityContainers(Map<Integer, ActivityContainer> containerMap, List<Topic> allTopics, int versionId) throws AdminException {
		LOGGER.debug("AdminServiceImpl: loadTopicsWithActivityContainers: START");
			
		for(Topic topic : allTopics){
			List<ActivityContainer> activityContainers = adminDao.loadActivityContainersByTopicId(topic.getId());
			//If versionId is -1, then the activity containers are not filtered by the version Id. If versionId is
			//not -1, then the activity containers are filtered by versionId's
			if(versionId != -1){
				List<ActivityContainer> activityContainersByVersion = adminDao.filterActivityContainers(activityContainers, versionId);
				topic.setActivityContainers(activityContainersByVersion);
				loadActivityContainersWithActivities(containerMap, activityContainersByVersion);
			}
			else
			{
				topic.setActivityContainers(activityContainers);
				loadActivityContainersWithActivities(containerMap, activityContainers);			
			}
																				
		}
		LOGGER.debug("AdminServiceImpl: loadTopicsWithActivityContainers: END");
	}

	*//**
	 * @param containerMap 
	 * @param activityContainers
	 * @throws AdminException
	 *//*
	private void loadActivityContainersWithActivities(Map<Integer, ActivityContainer> containerMap, List<ActivityContainer> activityContainers) throws AdminException {
		LOGGER.debug("AdminServiceImpl: loadActivityContainersWithActivities: START");

		for(ActivityContainer activityContainer : activityContainers){
			List<Activity> activities = adminDao.loadActivitiesByActivityContainerId(activityContainer.getActivityContainerId());
			activityContainer.setActivities(activities);
			containerMap.put(activityContainer.getActivityContainerId(), activityContainer);
		}
		LOGGER.debug("AdminServiceImpl: loadActivityContainersWithActivities: END");
	}

	 (non-Javadoc)
	 * @see com.neu.msd.service.AdminServie#getActivityContainerById(int)
	 
	public ActivityContainer getActivityContainerById(int activityContainerId) throws AdminException {
		
		return adminDao.loadActivityContainerById(activityContainerId);
	}

	public List<ActivityTemplate> getAllActivityTemplates() throws AdminException {
		
		return adminDao.getAllActivityTemplates();
	}

	public int renameTopic(String topicName, int topicId) throws AdminException {
		return adminDao.renameTopic(topicName, topicId);
	}
	
	public User adminAuthenticate(UserAuthentication userAuthentication) throws AdminException {
		return adminDao.authenticateAdminByUsernamePassword(userAuthentication);
	}

	public int addNewTopic(String topicName,String isMothers) throws AdminException {
		return adminDao.addTopic(topicName, isMothers); 
	}

	public int deleteTopic(int deletableId) throws AdminException {
		return adminDao.deleteTopic(deletableId);
	}
	
	public int deleteActivityContainer(int deletableId) throws AdminException {
		return adminDao.deleteActivityContainer(deletableId);
	}

	public ActivityContainer addNewActivityContainer(String containerName, int topicId) throws AdminException {
		return adminDao.addNewActivityContainer(containerName, topicId);
	}

	public int deleteActivity(Integer deletableId) throws AdminException {
		adminDao.deleteFromUserTopicContainerActivity(deletableId);
		adminDao.deleteFromAdminActivityAnswer(deletableId);
		return adminDao.deleteActivity(deletableId);
	}
	
	public int renameActivityContainer(String containerName, int containerId) throws AdminException {
		return adminDao.renameActivityContainer(containerName, containerId);
	}

	public List<Version> loadAllVersion() throws AdminException {
		return adminDao.loadAllVersion();
	}

//	public void assignTopicToVersion(int topicId, String[] versionIds) throws AdminException {
//		for(int i = 0; i< versionIds.length; i++){
//			adminDao.assignTopicToVersion(topicId, Integer.valueOf(versionIds[i]));
//		}
//	}
	
	// ---------- Changes to add version to Activity Container ----------
	public void assignActivityContainerToVersion(int actConId, String[] versionIds, int topicId) throws AdminException {
	for(int i = 0; i< versionIds.length; i++){
	adminDao.assignActivityContainerToVersion(actConId, Integer.valueOf(versionIds[i]), topicId);
	}
	}

	// ---------- Changes to add version to Activity Container end here----------


	
	// Adding AssignTopictoUsers here: Neha and Vinay
	public int assignTopicToUsers(int topicId) throws AdminException
	{
		int numRecords = adminDao.assignTopicToUsers(topicId);
		return numRecords;		
	}
	
	public AdminActivityAnswer saveAdminActivityAnswer(AdminActivityAnswer adminActivityAnswer) throws AdminException {
		
		Activity activity = adminDao.saveActivity(adminActivityAnswer.getActivity());
		
		List<Answer> answers = new ArrayList<Answer>();
		for(Answer a : adminActivityAnswer.getAnswers()){
			Answer answer = adminDao.saveAnswer(a);
			answers.add(answer);
			adminDao.saveAdminActivityAnswer(activity.getId(), answer.getId(), answer.getIsCorrect());
		}
		
		adminActivityAnswer.setActivity(activity);
		adminActivityAnswer.setAnswers(answers);
		
		// TODO Auto-generated method stub
		return adminActivityAnswer;
	}

	public AdminActivityAnswer getAdminActivityAnswerByActivityId(int activityId) throws AdminException {
		
		Activity activity = adminDao.loadActivityById(activityId);
		
		LOGGER.debug("=====================" + activity.getId() +" " + activity.getActivityText());
		
		List<Answer> answers = new ArrayList<Answer>();
		if(activity.getActivityTemplate().getId() != INFORMATION_TEMPLATE_ID)
			answers = adminDao.loadAnswersByActivityId(activityId);
		
		return new AdminActivityAnswer(activity, answers);
	}

	public AdminActivityAnswer updateAdminActivityAnswer(AdminActivityAnswer adminActivityAnswer)
			throws AdminException {
		
		Activity activity = adminDao.updateActivity(adminActivityAnswer.getActivity());
		
		if((activity.getActivityTemplate().getId() == VIDEO_TEMPLATE_ID || 
				activity.getActivityTemplate().getId() == IMAGE_TEMPLATE_ID)
				&& adminActivityAnswer.getAnswers().size() == 1){
			Answer answer = adminDao.loadAnswersByActivityId(activity.getId()).get(0);
			adminActivityAnswer.getAnswers().add(answer);
		}else if(activity.getActivityTemplate().getId() == FLIP_TEMPLATE_ID && adminActivityAnswer.getAnswers().size() != MAX_FLIP_OPTION){
			List<Answer> answers = adminDao.loadAnswersByActivityId(activity.getId());
			List<Answer> currentAnswers = new ArrayList<Answer>(adminActivityAnswer.getAnswers());
			for(Answer answer : currentAnswers){
				answers.remove(answer);
			}
			adminActivityAnswer.getAnswers().addAll(answers);
			Collections.sort(adminActivityAnswer.getAnswers(), new Comparator<Answer>() {
				@Override
				public int compare(Answer answer1, Answer answer2) {
					return answer1.getOrderNo()-answer2.getOrderNo();
				}
			});
		}
		adminDao.deleteFromUserTopicContainerActivity(adminActivityAnswer.getActivity().getId());
		adminDao.deleteFromAdminActivityAnswer(adminActivityAnswer.getActivity().getId());
		
		List<Answer> answers = new ArrayList<Answer>();
		for(Answer a : adminActivityAnswer.getAnswers()){
			Answer answer = adminDao.saveAnswer(a);
			answers.add(answer);
			adminDao.saveAdminActivityAnswer(activity.getId(), answer.getId(), answer.getIsCorrect());
		}
		
		adminActivityAnswer.setActivity(activity);
		adminActivityAnswer.setAnswers(answers);
		
		// TODO Auto-generated method stub
		return adminActivityAnswer;
	}

	public String generateFilePath(MultipartFile uploadFile, String fileType) throws AdminException {
		
		if(null != uploadFile && !uploadFile.isEmpty()){
			StringBuilder fileName = new StringBuilder();
			fileName.append(new Date().getTime());
			fileName.append("_");
			fileName.append(uploadFile.getOriginalFilename());
			
			StringBuilder absolutePath = new StringBuilder();
			absolutePath.append(fileType.equalsIgnoreCase("image")?IMAGE_ABSOLUTE_PATH:VIDEO_ABSOLUTE_PATH);
			absolutePath.append(fileName.toString());
			
			StringBuilder relativePath = new StringBuilder();
			relativePath.append(fileType.equalsIgnoreCase("image")?IMAGE_RELATIVE_PATH:VIDEO_RELATIVE_PATH);
			relativePath.append(fileName.toString());
			
			File fileOnServer = new File(absolutePath.toString());
			try {
				FileUtils.writeByteArrayToFile(fileOnServer, uploadFile.getBytes());
			} catch (IOException e) {
				throw new AdminException(e);
			}
			
			return relativePath.toString();
		}
		return null;
	}

	@Override
	public int registerAdmin(UserAuthentication userAuthentication) throws AdminException {
		int userId = adminDao.registerAdmin(userAuthentication.getUser());
		return adminDao.registerAdminAuthentication(userId, userAuthentication);
	}

	// @author Sanil And Vinay
	@Override
	public List<AdminActivityAnswer> getDiagnosticQuestions() {
		// TODO Auto-generated method stub
		
		try {
			
		ActivityType diagnosticActivity = adminDao.getDiagnosticActivityId();
		ActivityTemplate mcqTemplate = adminDao.getDiagnosticTemplateId();
	
		List<Activity> questionList = adminDao.getActivitiesByType(diagnosticActivity, mcqTemplate);
		List<AdminActivityAnswer> quesAnsList = new ArrayList<AdminActivityAnswer>();
		
		for(Activity ac : questionList){
			AdminActivityAnswer temp = new AdminActivityAnswer();
			temp.setActivity(ac);	
			AdminActivityAnswer ans = adminDao.getAdminActivityAnswerForDiagnostic(ac.getId());
			temp.setAnswers(ans.getAnswers());	
			quesAnsList.add(temp);
		}
			
			return quesAnsList;
		} catch (AdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public int addDiagnosticQuestion(String questionText, List<Answer> options) throws AdminException
	{
		if(questionText == null || questionText.length() == 0 || options == null || options.isEmpty())
			return 0;
		
		//Prepare Activity(Question)
		//TODO: Retrieve activity type from DB as object. 
		ActivityType activityType = new ActivityType();
		activityType.setId(1);
		activityType.setName("Diagnostic");
		//TODO: Retrieve activity template from DB as object.
		ActivityTemplate activityTemplate = new ActivityTemplate();
		activityTemplate.setId(3);
		activityTemplate.setTemplateName("MCQ");
		int orderNo = adminDao.getMaxOrderNumberForDiagnosticQuestion(activityType.getId());
		Activity question = new Activity();
		question.setOrderNo(orderNo);
		question.setActivityText(questionText);
		question.setActivityType(activityType);
		question.setActivityTemplate(activityTemplate);
		
		
		int recentActivityId = adminDao.saveDiagnosticQuestion(question);
		
		for(Answer ans : options)
		{
			Answer temp = adminDao.saveAnswer(ans);
			adminDao.saveAdminActivityAnswer(recentActivityId, temp.getId(), temp.getIsCorrect());
		}
		
		
		return recentActivityId;
	}
	
	
	
	public int deleteDiagnosticQuestion(int activityId) throws AdminException{
		
		if(activityId < 0 )
			return 0;
		
		int adminActivityRowsAffected = adminDao.deleteFromAdminActivityAnswer(activityId);
		
		int activityRowsAffected = adminDao.deleteDiagnosticQuestionById(activityId);
		
		
		return adminActivityRowsAffected + activityRowsAffected;
		
	}
	
	public int updateDiagnosticQuestion(AdminActivityAnswer adminActivity) throws AdminException{
		
		if(adminActivity == null)
			return 0;
		
		LOGGER.debug("========================ActivityID=================================" + adminActivity.getActivity().getId());
		int i = deleteDiagnosticQuestion(adminActivity.getActivity().getId());
		LOGGER.debug("========================i=================================" + i);
		
		if(i > 0){
			int rows = addDiagnosticQuestion(adminActivity.getActivity().getActivityText(), adminActivity.getAnswers());
			if (rows > 0)
				return rows + i;
			else
				return 0;
		
		}
		else
			return 0;
	}

	@Override
	public List<Topic> filterTopicForUsers(List<Topic> topics, User user) throws AdminException {
		// TODO Auto-generated method stub
		return adminDao.filterTopicForUsers(topics, user);
		
	}
	
}	*/

/**
 * 
 */
package com.neu.msd.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.neu.msd.dao.AdminDao;
import com.neu.msd.dao.UserDao;
import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.ActivityType;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Answer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.entities.Version;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;
import com.neu.msd.service.AdminService;

/**
 * @author Harsh
 *
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {
	
	Logger LOGGER = Logger.getLogger(AdminServiceImpl.class);

	@Autowired
	private AdminDao adminDao;
	
	
	private final String IMAGE_ABSOLUTE_PATH ="/usr/hbu/resources/images/";
	private final String IMAGE_RELATIVE_PATH ="resources/images/";
	private final String VIDEO_ABSOLUTE_PATH ="/usr/hbu/resources/videos/";
	private final String VIDEO_RELATIVE_PATH ="resources/videos/";
	private final int VIDEO_TEMPLATE_ID =1;
	private final int IMAGE_TEMPLATE_ID =2;
	private final int MCQ_TEMPLATE_ID =3;
	private final int INFORMATION_TEMPLATE_ID =4;
	private final int FLIP_TEMPLATE_ID =5;
	private final int MAX_FLIP_OPTION =6;
	
	/* (non-Javadoc)
	 * @see com.neu.msd.service.AdminServie#loadTopics()
	 */
	@Transactional
	public List<Topic> loadTopics(Map<Integer, ActivityContainer> containerMap) throws AdminException {
		LOGGER.debug("AdminServiceImpl: loadTopics: START");
		
		List<Topic> allTopics = adminDao.loadTopics();
		
		loadTopicsWithActivityContainers(containerMap, allTopics, -1);
		
		LOGGER.debug("AdminServiceImpl: loadTopics: END");
		return allTopics;
	}

	/**
	 * @param containerActivitiesMap 
	 * @param containerMap 
	 * @param allTopics
	 * @throws AdminException
	 */
	public void loadTopicsWithActivityContainers(Map<Integer, ActivityContainer> containerMap, List<Topic> allTopics, int versionId) throws AdminException {
		LOGGER.debug("AdminServiceImpl: loadTopicsWithActivityContainers: START");
			
		for(Topic topic : allTopics){
			List<ActivityContainer> activityContainers = adminDao.loadActivityContainersByTopicId(topic.getId());
			//If versionId is -1, then the activity containers are not filtered by the version Id. If versionId is
			//not -1, then the activity containers are filtered by versionId's
			if(versionId != -1){
				List<ActivityContainer> activityContainersByVersion = adminDao.filterActivityContainers(activityContainers, versionId);
				topic.setActivityContainers(activityContainersByVersion);
				loadActivityContainersWithActivities(containerMap, activityContainersByVersion);
			}
			else
			{
				topic.setActivityContainers(activityContainers);
				loadActivityContainersWithActivities(containerMap, activityContainers);			
			}
																				
		}
		LOGGER.debug("AdminServiceImpl: loadTopicsWithActivityContainers: END");
	}

	/**
	 * @param containerMap 
	 * @param activityContainers
	 * @throws AdminException
	 */
	private void loadActivityContainersWithActivities(Map<Integer, ActivityContainer> containerMap, List<ActivityContainer> activityContainers) throws AdminException {
		LOGGER.debug("AdminServiceImpl: loadActivityContainersWithActivities: START");

		for(ActivityContainer activityContainer : activityContainers){
			List<Activity> activities = adminDao.loadActivitiesByActivityContainerId(activityContainer.getActivityContainerId());
			activityContainer.setActivities(activities);
			containerMap.put(activityContainer.getActivityContainerId(), activityContainer);
		}
		LOGGER.debug("AdminServiceImpl: loadActivityContainersWithActivities: END");
	}

	/* (non-Javadoc)
	 * @see com.neu.msd.service.AdminServie#getActivityContainerById(int)
	 */
	public ActivityContainer getActivityContainerById(int activityContainerId) throws AdminException {
		
		return adminDao.loadActivityContainerById(activityContainerId);
	}

	public List<ActivityTemplate> getAllActivityTemplates() throws AdminException {
		
		return adminDao.getAllActivityTemplates();
	}

	public int renameTopic(String topicName, int topicId) throws AdminException {
		return adminDao.renameTopic(topicName, topicId);
	}
	
	public User adminAuthenticate(UserAuthentication userAuthentication) throws AdminException {
		return adminDao.authenticateAdminByUsernamePassword(userAuthentication);
	}

	public int addNewTopic(String topicName,String isMothers) throws AdminException {
		return adminDao.addTopic(topicName,isMothers); 
	}

	public int deleteTopic(int deletableId) throws AdminException {
		return adminDao.deleteTopic(deletableId);
	}
	
	public int deleteActivityContainer(int deletableId) throws AdminException {
		return adminDao.deleteActivityContainer(deletableId);
	}

	public ActivityContainer addNewActivityContainer(String containerName, int topicId) throws AdminException {
		return adminDao.addNewActivityContainer(containerName, topicId);
	}

	public int deleteActivity(Integer deletableId) throws AdminException {
		adminDao.deleteFromUserTopicContainerActivity(deletableId);
		adminDao.deleteFromAdminActivityAnswer(deletableId);
		return adminDao.deleteActivity(deletableId);
	}
	
	public int renameActivityContainer(String containerName, int containerId) throws AdminException {
		return adminDao.renameActivityContainer(containerName, containerId);
	}

	public List<Version> loadAllVersion() throws AdminException {
		return adminDao.loadAllVersion();
	}

//	public void assignTopicToVersion(int topicId, String[] versionIds) throws AdminException {
//		for(int i = 0; i< versionIds.length; i++){
//			adminDao.assignTopicToVersion(topicId, Integer.valueOf(versionIds[i]));
//		}
//	}

	// ---------- Changes to add version to Activity Container ----------
	public void assignActivityContainerToVersion(int actConId, String[] versionIds, int topicId) throws AdminException {
		for(int i = 0; i< versionIds.length; i++){
			adminDao.assignActivityContainerToVersion(actConId, Integer.valueOf(versionIds[i]), topicId);
		}
	}

	// ---------- Changes to add version to Activity Container end here----------


	
	// Adding AssignTopictoUsers here: Neha and Vinay
	public int assignTopicToUsers(int topicId,int user_type_id) throws AdminException
	{
		int numRecords = adminDao.assignTopicToUsers(topicId,user_type_id);
		return numRecords;		
	}
	
	public AdminActivityAnswer saveAdminActivityAnswer(AdminActivityAnswer adminActivityAnswer) throws AdminException {
		
		Activity activity = adminDao.saveActivity(adminActivityAnswer.getActivity());
		
		List<Answer> answers = new ArrayList<Answer>();
		for(Answer a : adminActivityAnswer.getAnswers()){
			Answer answer = adminDao.saveAnswer(a);
			answers.add(answer);
			adminDao.saveAdminActivityAnswer(activity.getId(), answer.getId(), answer.getIsCorrect());
		}
		
		adminActivityAnswer.setActivity(activity);
		adminActivityAnswer.setAnswers(answers);
		
		// TODO Auto-generated method stub
		return adminActivityAnswer;
	}

	public AdminActivityAnswer getAdminActivityAnswerByActivityId(int activityId) throws AdminException {
		
		Activity activity = adminDao.loadActivityById(activityId);
		
		LOGGER.debug("=====================" + activity.getId() +" " + activity.getActivityText());
		
		List<Answer> answers = new ArrayList<Answer>();
		if(activity.getActivityTemplate().getId() != INFORMATION_TEMPLATE_ID)
			answers = adminDao.loadAnswersByActivityId(activityId);
		
		return new AdminActivityAnswer(activity, answers);
	}

	public AdminActivityAnswer updateAdminActivityAnswer(AdminActivityAnswer adminActivityAnswer)
			throws AdminException {
		
		Activity activity = adminDao.updateActivity(adminActivityAnswer.getActivity());
		
		if((activity.getActivityTemplate().getId() == VIDEO_TEMPLATE_ID || 
				activity.getActivityTemplate().getId() == IMAGE_TEMPLATE_ID)
				&& adminActivityAnswer.getAnswers().size() == 1){
			Answer answer = adminDao.loadAnswersByActivityId(activity.getId()).get(0);
			adminActivityAnswer.getAnswers().add(answer);
		}else if(activity.getActivityTemplate().getId() == FLIP_TEMPLATE_ID && adminActivityAnswer.getAnswers().size() != MAX_FLIP_OPTION){
			List<Answer> answers = adminDao.loadAnswersByActivityId(activity.getId());
			List<Answer> currentAnswers = new ArrayList<Answer>(adminActivityAnswer.getAnswers());
			for(Answer answer : currentAnswers){
				answers.remove(answer);
			}
			adminActivityAnswer.getAnswers().addAll(answers);
			Collections.sort(adminActivityAnswer.getAnswers(), new Comparator<Answer>() {
				@Override
				public int compare(Answer answer1, Answer answer2) {
					return answer1.getOrderNo()-answer2.getOrderNo();
				}
			});
		}
		adminDao.deleteFromUserTopicContainerActivity(adminActivityAnswer.getActivity().getId());
		adminDao.deleteFromAdminActivityAnswer(adminActivityAnswer.getActivity().getId());
		
		List<Answer> answers = new ArrayList<Answer>();
		for(Answer a : adminActivityAnswer.getAnswers()){
			Answer answer = adminDao.saveAnswer(a);
			answers.add(answer);
			adminDao.saveAdminActivityAnswer(activity.getId(), answer.getId(), answer.getIsCorrect());
		}
		
		adminActivityAnswer.setActivity(activity);
		adminActivityAnswer.setAnswers(answers);
		
		// TODO Auto-generated method stub
		return adminActivityAnswer;
	}

	public String generateFilePath(MultipartFile uploadFile, String fileType) throws AdminException {
		
		if(null != uploadFile && !uploadFile.isEmpty()){
			StringBuilder fileName = new StringBuilder();
			fileName.append(new Date().getTime());
			fileName.append("_");
			fileName.append(uploadFile.getOriginalFilename());
			
			StringBuilder absolutePath = new StringBuilder();
			absolutePath.append(fileType.equalsIgnoreCase("image")?IMAGE_ABSOLUTE_PATH:VIDEO_ABSOLUTE_PATH);
			absolutePath.append(fileName.toString());
			
			StringBuilder relativePath = new StringBuilder();
			relativePath.append(fileType.equalsIgnoreCase("image")?IMAGE_RELATIVE_PATH:VIDEO_RELATIVE_PATH);
			relativePath.append(fileName.toString());
			
			File fileOnServer = new File(absolutePath.toString());
			try {
				FileUtils.writeByteArrayToFile(fileOnServer, uploadFile.getBytes());
			} catch (IOException e) {
				throw new AdminException(e);
			}
			
			return relativePath.toString();
		}
		return null;
	}

	@Override
	public int registerAdmin(UserAuthentication userAuthentication) throws AdminException {
		int userId = adminDao.registerAdmin(userAuthentication.getUser());
		return adminDao.registerAdminAuthentication(userId, userAuthentication);
	}

	// @author Sanil And Vinay
	@Override
	public List<AdminActivityAnswer> getDiagnosticQuestions() {
		// TODO Auto-generated method stub
		
		try {
			
		ActivityType diagnosticActivity = adminDao.getDiagnosticActivityId();
		ActivityTemplate mcqTemplate = adminDao.getDiagnosticTemplateId();
	
		List<Activity> questionList = adminDao.getActivitiesByType(diagnosticActivity, mcqTemplate);
		List<AdminActivityAnswer> quesAnsList = new ArrayList<AdminActivityAnswer>();
		
		for(Activity ac : questionList){
			AdminActivityAnswer temp = new AdminActivityAnswer();
			temp.setActivity(ac);	
			AdminActivityAnswer ans = adminDao.getAdminActivityAnswerForDiagnostic(ac.getId());
			temp.setAnswers(ans.getAnswers());	
			quesAnsList.add(temp);
		}
			
			return quesAnsList;
		} catch (AdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public int addDiagnosticQuestion(String questionText, List<Answer> options) throws AdminException
	{
		if(questionText == null || questionText.length() == 0 || options == null || options.isEmpty())
			return 0;
		
		//Prepare Activity(Question)
		//TODO: Retrieve activity type from DB as object. 
		ActivityType activityType = new ActivityType();
		activityType.setId(1);
		activityType.setName("Diagnostic");
		//TODO: Retrieve activity template from DB as object.
		ActivityTemplate activityTemplate = new ActivityTemplate();
		activityTemplate.setId(3);
		activityTemplate.setTemplateName("MCQ");
		int orderNo = adminDao.getMaxOrderNumberForDiagnosticQuestion(activityType.getId());
		Activity question = new Activity();
		question.setOrderNo(orderNo);
		question.setActivityText(questionText);
		question.setActivityType(activityType);
		question.setActivityTemplate(activityTemplate);
		
		
		int recentActivityId = adminDao.saveDiagnosticQuestion(question, options.size());
		
		for(Answer ans : options)
		{
			Answer temp = adminDao.saveAnswer(ans);
			adminDao.saveAdminActivityAnswer(recentActivityId, temp.getId(), temp.getIsCorrect());
		}
		
		
		return recentActivityId;
	}
	
	
	
	public int deleteDiagnosticQuestion(int activityId) throws AdminException{
		
		if(activityId < 0 )
			return 0;
		
		int adminActivityRowsAffected = adminDao.deleteFromAdminActivityAnswer(activityId);
		
		int activityRowsAffected = adminDao.deleteDiagnosticQuestionById(activityId);
		
		
		return adminActivityRowsAffected + activityRowsAffected;
		
	}
	
	public int updateDiagnosticQuestion(AdminActivityAnswer adminActivity) throws AdminException{
		
		if(adminActivity == null)
			return 0;
		
		LOGGER.debug("========================ActivityID=================================" + adminActivity.getActivity().getId());
		int i = deleteDiagnosticQuestion(adminActivity.getActivity().getId());
		LOGGER.debug("========================i=================================" + i);
		
		if(i > 0){
			int rows = addDiagnosticQuestion(adminActivity.getActivity().getActivityText(), adminActivity.getAnswers());
			if (rows > 0)
				return rows;
			else
				return -1;
		
		}
		else
			return -1;
	}
	
	


	@Override
	public List<Topic> filterTopicForUsers(List<Topic> topics, User user) throws AdminException {
		// TODO Auto-generated method stub
		return adminDao.filterTopicForUsers(topics, user);	
	}
}	