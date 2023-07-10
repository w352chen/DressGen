package com.uwaterloo.dressgen.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uwaterloo.dressgen.model.DressGUIElementType;
import com.uwaterloo.dressgen.model.DressGenRequest;
import com.uwaterloo.dressgen.model.GUIInfo;

@Repository
public class DressGenDaoInMemoryImpl implements DressGenDao {
	
	static private Map<DressGUIElementType, GUIInfo> guiInfo = new HashMap<>();

	public DressGenDaoInMemoryImpl() {
		guiInfo.put(DressGUIElementType.Color, 
				new GUIInfo(1, DressGUIElementType.Color.getGuiElementName(), "OK, so how do we go about discovering the right colours? <p> Well, first we need to look at our individual features, including eye and hair colour and skin tone. In order to tell your skin tone you need to look at it under natural light, checking to see whether there is a golden or rosy hint. Traces of apricot or golden undertones mean you're \"warm\" toned and being slightly pink or rosy makes you \"cool\" toned.\n"+ 
				"The general rule is that cool-toned people look best in blue-based colours while warm-toned people will look best in yellow-based colours. Here is a rough guide of colours to suit your skin type:<p>"
								+ "<ul><li>Warm tones = Natural Earth ColoursBrowns,"+ "bronze and green are most complementary for you.</li>"
								+ "<li>If you like soft, feminine summer colours try peach and apricot.</li>"
								+ "<li>Try brick red, dark tomato or a burnt orange hue if you’re into reds.</li>"
								+ "<li>Earth greens like olive or jade enhance warm skin tones.</li>"
								+ "<li>Clean ivory or oyster whites also enhance the warm skin tone type.</li>"
								+ "<li>If dressing for work, look for clothes in taupe or bright navy. You can mix them up with brown and gold accessories for maximum impact.</li></ul>"));
		guiInfo.put(DressGUIElementType.Silhouette, 
				new GUIInfo(2, DressGUIElementType.Silhouette.getGuiElementName(), "Add description ..."));
		guiInfo.put(DressGUIElementType.Material, 
				new GUIInfo(1, DressGUIElementType.Material.getGuiElementName(), "Add description ..."));
		guiInfo.put(DressGUIElementType.Neckline, 
				new GUIInfo(1, DressGUIElementType.Neckline.getGuiElementName(), "Add description ..."));
		guiInfo.put(DressGUIElementType.Pattern, 
				new GUIInfo(1, DressGUIElementType.Pattern.getGuiElementName(), "Add description ..."));
		guiInfo.put(DressGUIElementType.Sleeves, 
				new GUIInfo(1, DressGUIElementType.Sleeves.getGuiElementName(), "Add description ..."));
		guiInfo.put(DressGUIElementType.SleeveLength, 
				new GUIInfo(1, DressGUIElementType.SleeveLength.getGuiElementName(), "Add description ..."));
		guiInfo.put(DressGUIElementType.Skirt, 
				new GUIInfo(1, DressGUIElementType.Skirt.getGuiElementName(), "Add description ..."));
		guiInfo.put(DressGUIElementType.SkirtLength, 
				new GUIInfo(1, DressGUIElementType.SkirtLength.getGuiElementName(), "Add description ..."));
		guiInfo.put(DressGUIElementType.Info, 
				new GUIInfo(1, DressGUIElementType.Info.getGuiElementName(), "Everyone is an artist. We know our blanks are not enough for you to be creative, so we would like to hear from you. Please enter additional information ..."));
		
//		loadDatabase();
	}
	
	@Override
	public GUIInfo getGUIInfo(DressGUIElementType dressGUIElementType) {
		return guiInfo.get(dressGUIElementType);
	}
	
	@Override
	public GUIInfo getGUIInfo(String dressGUIElementTypeName) {
		for (DressGUIElementType dressGUIElementType : DressGUIElementType.values()) {
			if (dressGUIElementType.getGuiElementName().equals(dressGUIElementTypeName)) {
				return getGUIInfo(dressGUIElementType);
			}
		}
		return null;
	}
	
	private static Map<String, List<DressGenRequest>> DATABASE_SUBMISSION_TABLE = new HashMap<>();
	
	static private int submissionId = 1;
	
	@Override
	public void save(DressGenRequest dressGenRequest) {
		dressGenRequest.setSubmissionId(submissionId++);
		System.out.println("submissionId = " + submissionId);

		List<DressGenRequest> requests = DATABASE_SUBMISSION_TABLE.get(dressGenRequest.getUsername());
		if (requests == null) {
			requests = new ArrayList<>();
		}
		requests.add(dressGenRequest);
		DATABASE_SUBMISSION_TABLE.put(dressGenRequest.getUsername(), requests);
		persistDatabase();
	}
	
	private void printAllSubmissionRecords() {
		System.out.println("---------------- All Submission Records ------------------");
		for (Map.Entry<String, List<DressGenRequest>> entry : DATABASE_SUBMISSION_TABLE.entrySet()) {
			System.out.println("--- User: " + entry.getKey() + " ---");
			for (DressGenRequest request : entry.getValue()) {
				System.out.println(request);
			}
		}
		System.out.println("----------------------------------------------------------");
	}
	
	@Override
	public List<DressGenRequest> retrieveSubmissionRecords(String username, int numOfLastRecords) {
		printAllSubmissionRecords();
		List<DressGenRequest> requests = new ArrayList<>();
		List<DressGenRequest> savedRequests = DATABASE_SUBMISSION_TABLE.get(username);
		if (savedRequests != null) {
			int numRecords = savedRequests.size();
			int firstRecordIndex = numOfLastRecords <= 0 ? 0 : numRecords - numOfLastRecords;
			if (firstRecordIndex < 0) {
				firstRecordIndex = 0;
			}
			for (int i = firstRecordIndex; i < numRecords; i ++) {
				requests.add(savedRequests.get(i));
			}
		}
		return requests;
	}
	
	@Override
	public List<DressGenRequest> retrieveSubmissionRecordsSince(String username, Date sinceDate) {
		printAllSubmissionRecords();
		List<DressGenRequest> requests = new ArrayList<>();
		List<DressGenRequest> savedRequests = DATABASE_SUBMISSION_TABLE.get(username);
		if (savedRequests != null) {
			for (DressGenRequest request : savedRequests) {
				if (request.getRequestTimestamp().after(sinceDate)) {
					requests.add(request);
				}
			}
		}
		return requests;
	}
	
	@Override
	public DressGenRequest retrieveSubmissionRecordById(int submissionId) {
		for (Map.Entry<String, List<DressGenRequest>> entry : DATABASE_SUBMISSION_TABLE.entrySet()) {
			for (DressGenRequest request : entry.getValue()) {
				if (request.getSubmissionId() == submissionId) {
					return request;
				}
			}
		}
		return null;
	}
	
//	@Value("${DressGen.DB}")
//	String dressGenDB;
	
	@Autowired
	private Environment env;

	@Override
	public List<DressGenRequest> queryDressGenSubmission(String username, Date fromDate, Date toDate,
			String containedText, int numRec) {
		List<DressGenRequest> requests = new ArrayList<>();
		List<DressGenRequest> finalRequests = new ArrayList<>();
		List<DressGenRequest> savedRequests = DATABASE_SUBMISSION_TABLE.get(username);

		if (savedRequests != null) {
			for (DressGenRequest request : savedRequests) {
				if (request.getRequestTimestamp().after(fromDate) && request.getRequestTimestamp().before(toDate) && 
						request.contains(containedText)) {
					requests.add(request);
				}
			}

			int numRecords = requests.size();
			int firstRecordIndex = numRec <= 0 ? 0 : numRecords - numRec;
			if (firstRecordIndex < 0) {
				firstRecordIndex = 0;
			}
			for (int i = firstRecordIndex; i < numRecords; i ++) {
				finalRequests.add(requests.get(i));
			}
		}
		System.out.println("dressGenDB = " + env.getProperty("DressGenDB"));
		return finalRequests;

	}

	private void persistDatabase() {
		String dressGenDBPath = env.getProperty("DressGenDB");
		if (dressGenDBPath != null) {
//			try (FileOutputStream fileOut = new FileOutputStream(dressGenDBPath);
//	            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);) {
//				 
//	            objectOut.writeObject(DATABASE_SUBMISSION_TABLE);
//	            System.out.println("The Object  was succesfully written to: " + dressGenDBPath);
//	 
//	        } catch (Exception ex) {
//	            ex.printStackTrace();
//	        }
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				objectMapper
					.writer().withoutAttribute("formattedTimestamp").withoutAttribute("description")
					.writeValue(new File(dressGenDBPath + ".json"), DATABASE_SUBMISSION_TABLE);
				System.out.println("The Object  was succesfully written to: " + dressGenDBPath);
			} catch (StreamWriteException e) {
				e.printStackTrace();
			} catch (DatabindException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("ERROR: DressGenDB is null");
			throw new RuntimeException("ERROR: DressGenDB is null");
		}
	}


	@PostConstruct
	private void loadDatabase() {
		String dressGenDBPath = env.getProperty("DressGenDB");
		if (dressGenDBPath != null) {
//			try (FileInputStream fileOut = new FileInputStream(dressGenDBPath);
//	            ObjectInputStream objectOut = new ObjectInputStream(fileOut);) {
//				 
//				submissionId = 0;
//				DATABASE_SUBMISSION_TABLE = (Map<String, List<DressGenRequest>>) objectOut.readObject();
//				
//				for (List<DressGenRequest> recordList: DATABASE_SUBMISSION_TABLE.values()) {
//					for (DressGenRequest record: recordList) {
//						if (record.getSubmissionId() > submissionId) {
//							submissionId = record.getSubmissionId();
//						}
//					}
//				}
//				
//				ObjectMapper objectMapper = new ObjectMapper();
//				objectMapper
//					.writer().withoutAttribute("formattedTimestamp").withoutAttribute("description")
//					.writeValue(new File(dressGenDBPath + ".json"), DATABASE_SUBMISSION_TABLE);				
//				submissionId ++;
//	            System.out.println("The Data  was succesfully loaded from: " + dressGenDBPath);
//	 
//	        } catch (Throwable ex) {
//	            ex.printStackTrace();
//	        }
			submissionId = 1;
			
			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.addMixIn(String[].class, MyMixInForIgnoreType.class);
			try {
				DATABASE_SUBMISSION_TABLE = objectMapper
//						.reader().withoutAttribute("formattedTimestamp").withoutAttribute("description")
						.readValue(new File(dressGenDBPath + ".json"), new TypeReference<Map<String, List<DressGenRequest>>>(){});

				for (List<DressGenRequest> recordList: DATABASE_SUBMISSION_TABLE.values()) {
					for (DressGenRequest record: recordList) {
						if (record.getSubmissionId() > submissionId) {
							submissionId = record.getSubmissionId();
						}
					}
				}
				submissionId ++;
				System.out.println("The Data  was succesfully written to: " + dressGenDBPath);
			} catch (StreamWriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println("ERROR: DressGenDB is null");
			throw new RuntimeException("ERROR: DressGenDB is null");
		}
	}
}

//@JsonIgnoreType
//class MyMixInForIgnoreType {}
