package com.uwaterloo.dressgen.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.uwaterloo.dressgen.dao.DressGenDao;
import com.uwaterloo.dressgen.model.DressGUIElementType;
import com.uwaterloo.dressgen.model.DressGenFixedPrompt;
import com.uwaterloo.dressgen.model.DressGenInfoSubmissionResponse;
import com.uwaterloo.dressgen.model.DressGenRequest;
import com.uwaterloo.dressgen.model.GUIInfo;

@Service
public class DressGenService {
	
	static private String ApiKey = "A [Tee] [Beige] [123] dress";
	
	@Autowired
	private DressGenDao dressgenDao;
	
	@Value("${FixedPromptFile}")
	private String templateFilename;
	private String fixedPromptTemplate;
	
	public DressGenService() { }
	
	@PostConstruct
	private void init() {
		System.out.println("templateFilename = " + templateFilename);
		try {
			File file = new File(templateFilename);
			if (file.exists()) {
				System.out.println("template file exists");
			} else {
				System.out.println("template file does not exist");
			}
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				this.fixedPromptTemplate = sb.toString();
				System.out.println("tempalte = " + fixedPromptTemplate);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public GUIInfo getGUIInfo(DressGUIElementType dressGUIElementType) {
		return this.dressgenDao.getGUIInfo(dressGUIElementType);
	}

	public GUIInfo getGUIInfo(String dressGUIElementTypeName) {
		return this.dressgenDao.getGUIInfo(dressGUIElementTypeName);
	}
	
//	static private int submissionId = 1;
	
	public DressGenInfoSubmissionResponse submitDressGenRequest(DressGenRequest dressGenRequest) {
		// processing the submission goes here . . .

//		dressGenRequest.setSubmissionId(submissionId++);
//		System.out.println("submissionId = " + submissionId);
		
		this.dressgenDao.save(dressGenRequest);
//		return new DressGenInfoSubmissionResponse(dressGenRequest.getSubmissionId(), "0", dressGenRequest.toString(), dressGenRequest);
		DressGenFixedPrompt prompt = generateDressGenFixedPrompt(dressGenRequest.getSubmissionId());
		return new DressGenInfoSubmissionResponse(
				dressGenRequest.getSubmissionId(), 
				"0", 
				dressGenRequest.getDescription().toString(), 
				dressGenRequest,
				prompt.getPrompt());
	}
	
	public List<DressGenRequest> getLastDressGenRequestSubmissions(String username, int numOfLastRecords) {
		return this.dressgenDao.retrieveSubmissionRecords(username, numOfLastRecords);
	}
	
	public List<DressGenRequest> getDressGenRequestSubmissionsSince(String username, Date sinceDate) {
		return this.dressgenDao.retrieveSubmissionRecordsSince(username, sinceDate);
	}
	
	public List<DressGenRequest> getDressGenRequestSubmissionsSince(String username, String sinceDateStr) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date sinceDate = formatter.parse(sinceDateStr);
		return this.dressgenDao.retrieveSubmissionRecordsSince(username, sinceDate);
	}
	
	public DressGenRequest getDressGenRequestSubmissionById(int submissionId) {
		return this.dressgenDao.retrieveSubmissionRecordById(submissionId);
	}

	public List<DressGenRequest> queryDressGenSubmission(String username, String fromDateStr, String toDateStr,
			String containedText, String numRecStr, OrderType orderType) throws ParseException {
		
//		System.out.println("dressGenToken = " + dressGenToken);
//		username = validateToken(dressGenToken);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
		Date fromDate = formatter.parse(fromDateStr + " 00:00:00");
		Date toDate = formatter.parse(toDateStr + " 23:59:59");
		int numRec = Integer.parseInt(numRecStr);
		List<DressGenRequest> submissions = queryDressGenSubmission(username, fromDate, toDate, containedText, numRec);
		Collections.sort(submissions, (s1, s2) -> (s1.getSubmissionId() - s2.getSubmissionId()) * (orderType == OrderType.Ascending ? 1 : -1) );
		return submissions;
	}
	
	public String validateToken(String token) {
		if ("NONE".equals(token)) {
			token = UUID.randomUUID().toString();
//			System.out.println("s = " + token);
		}
		return token;
	}

	public List<DressGenRequest> queryDressGenSubmission(String username, Date fromDate, Date toDate,
			String containedText, int numRec) throws ParseException {
		

		return this.dressgenDao.queryDressGenSubmission(username, fromDate, toDate, containedText, numRec);
	}
	
	public DressGenFixedPrompt generateDressGenFixedPrompt(int submissionId) {
		DressGenRequest dressGen = getDressGenRequestSubmissionById(submissionId);
//		System.out.println("templateFilename = " + templateFilename);
//		try {
//			File file = new File(templateFilename);
//			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//				StringBuffer sb = new StringBuffer();
//				String line;
//				while ((line = reader.readLine()) != null) {
//					sb.append(line);
//				}
//				String template = sb.toString();
//				System.out.println("tempalte = " + template);
//				return new DressGenFixedPrompt(dressGen, processFixedPrompt(dressGen, template));
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//		String s = processFixedPrompt(dressGen, this.fixedPromptTemplate);
		
		return new DressGenFixedPrompt(dressGen, processFixedPrompt(dressGen, this.fixedPromptTemplate));
	}
	
	private String processFixedPrompt(DressGenRequest dressGen, String template) {
		Map<String, String> valuesMap = new HashMap<>();
		valuesMap.put("dressGenInfo", dressGen.getDescription());
		StringSubstitutor sub = new StringSubstitutor(valuesMap);
		
		System.out.println("-----------------------------------");
		System.out.println(template);
		System.out.println(sub.replace(template));
		System.out.println("-----------------------------------");
		
		return sub.replace(template);
	}
}
