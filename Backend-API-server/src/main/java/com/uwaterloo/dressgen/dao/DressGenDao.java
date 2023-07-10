package com.uwaterloo.dressgen.dao;

import java.util.Date;
import java.util.List;

import com.uwaterloo.dressgen.model.DressGUIElementType;
import com.uwaterloo.dressgen.model.DressGenRequest;
import com.uwaterloo.dressgen.model.GUIInfo;

public interface DressGenDao {

	GUIInfo getGUIInfo(DressGUIElementType dressGUIElementType);

	GUIInfo getGUIInfo(String dressGUIElementTypeName);

	void save(DressGenRequest dressGenRequest);

	List<DressGenRequest> retrieveSubmissionRecords(String username, int numOfLastRecords);

	List<DressGenRequest> retrieveSubmissionRecordsSince(String username, Date sinceDate);

	DressGenRequest retrieveSubmissionRecordById(int submissionId);

	List<DressGenRequest> queryDressGenSubmission(String username, Date fromDate, Date toDate, String containedText,
			int numRec);

}
