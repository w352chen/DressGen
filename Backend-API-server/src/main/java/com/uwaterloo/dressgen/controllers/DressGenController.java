package com.uwaterloo.dressgen.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uwaterloo.dressgen.model.DressGenRequest;
import com.uwaterloo.dressgen.model.DressGenFixedPrompt;
import com.uwaterloo.dressgen.model.DressGenInfoSubmissionResponse;
import com.uwaterloo.dressgen.model.GUIInfo;
import com.uwaterloo.dressgen.services.DressGenService;
import com.uwaterloo.dressgen.services.OrderType;

@CrossOrigin(allowCredentials="true", originPatterns = {"http://*"})
@RestController
@RequestMapping("/api")
public class DressGenController {
	
	@Autowired
	private DressGenService dressGenService;
	
	@GetMapping(value = {"/", "/info"})
	String info() {
		return "DressGen API";
	}
	
	@GetMapping("/gui-info/{elementType}")
	public GUIInfo getGuiInfo(@PathVariable String elementType) {
		System.out.println("elementType = " + elementType);
		return dressGenService.getGUIInfo(elementType);
	}
	
//	static private int submissionId = 1;
	
	@Autowired
	HttpSession session;
	
	@PostMapping(value = {"/dressGen"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DressGenInfoSubmissionResponse submitDressGenInfo(
			@RequestBody DressGenRequest dressGenInfo,
			@CookieValue(value = "DressGenToken", defaultValue = "NONE") String dressGenToken,
			HttpServletResponse response) {
		System.out.println("dressGenInfo = " + dressGenInfo);
//		return new DressGenInfoSubmissionResponse(submissionId++, "0", "Submission has been received successfully.");
		System.out.println("sessionId = " + session.getId());
		dressGenToken = handleToken(dressGenToken, response);
		dressGenInfo.setUsername(dressGenToken);
//		dressGenInfo.setUsername(session.getId());
		return dressGenService.submitDressGenRequest(dressGenInfo);
	}
	
	@GetMapping(value = {"/dressGenSubmissions/last/{username}/{numOfLastRecords}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DressGenRequest> getLastDressGenSubmissions(
			@PathVariable("username") String username, @PathVariable("numOfLastRecords") int numOfLastRecords,
			@CookieValue(value = "DressGenToken", defaultValue = "NONE") String dressGenToken,
			HttpServletResponse response) {
		dressGenToken = handleToken(dressGenToken, response);
		username = dressGenToken;
//		username = session.getId();
		return this.dressGenService.getLastDressGenRequestSubmissions(username, numOfLastRecords);
	}

	@GetMapping(value = {"/dressGenSubmissions/since/{username}/{sinceDate}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DressGenRequest> getDressGenSubmissionsSince(
			@PathVariable("username") String username, @PathVariable("sinceDate") String sinceDate,
			@CookieValue(value = "DressGenToken", defaultValue = "NONE") String dressGenToken,
			HttpServletResponse response) throws ParseException {
		dressGenToken = handleToken(dressGenToken, response);
		username = dressGenToken;
//		username = session.getId();
		return this.dressGenService.getDressGenRequestSubmissionsSince(username, sinceDate);
	}

	@GetMapping(value = {"/dressGenSubmissions/{submissionId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public DressGenRequest getDressGenSubmissionById(
			@PathVariable("submissionId") int submissionId,
			@CookieValue(value = "DressGenToken", defaultValue = "NONE") String dressGenToken,
			HttpServletResponse response) {
		dressGenToken = handleToken(dressGenToken, response);
		return this.dressGenService.getDressGenRequestSubmissionById(submissionId);
	}


	@GetMapping(value = {"/dressGenQuerySubmissions"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DressGenRequest> queryDressGenSubmission(
			@RequestParam("username") String username,
			@RequestParam("from") String fromDate,
			@RequestParam("to") String toDate,
			@RequestParam("text") String containedText,
			@RequestParam("numRec") String numRec,
			@RequestParam("order") OrderType orderType,
			@CookieValue(value = "DressGenToken", defaultValue = "NONE") String dressGenToken,
			HttpServletResponse response) throws ParseException {
		System.out.println(String.format("from=%s, to=%s, text=%s, numRec=%s, order=%s", fromDate, toDate, containedText, numRec, orderType));
		System.out.println("dressGenToken = " + dressGenToken);
		dressGenToken = handleToken(dressGenToken, response);
		username = dressGenToken;
//		username = session.getId();
		return this.dressGenService.queryDressGenSubmission(username, fromDate, toDate, containedText, numRec, orderType);
	}
	
	@GetMapping(value = {"/dressGenFixedPrompt/{submissionId}"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public DressGenFixedPrompt getDressGenFixedPrompt(@PathVariable("submissionId") int submissionId) {
		System.out.println("getDressGenFixedPrompt(): submissionId = " + submissionId);
		System.out.println("sessionId = " + session.getId());

		return this.dressGenService.generateDressGenFixedPrompt(submissionId);
	}
	
	private String handleToken(String dressGenToken, HttpServletResponse response) {
		dressGenToken = this.dressGenService.validateToken(dressGenToken);
		Cookie cookie = new Cookie("DressGenToken", dressGenToken);
		cookie.setMaxAge(3600*24*365);
	    response.addCookie(cookie);
	    return dressGenToken;
	}
}




