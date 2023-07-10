package com.uwaterloo.dressgen.model;

public class DressGenInfoSubmissionResponse {
	
	private int id;
	private String code;
	private String info;
	private DressGenRequest submission;
	private String prompt; 
	
	public DressGenInfoSubmissionResponse() { }

	public DressGenInfoSubmissionResponse(int id, String code, String info) {
		super();
		this.id = id;
		this.code = code;
		this.info = info;
	}

	public DressGenInfoSubmissionResponse(int id, String code, String info, DressGenRequest submission) {
		this(id, code, info);
		this.submission = submission;
	}

	public DressGenInfoSubmissionResponse(int id, String code, String info, DressGenRequest submission, String prompt) {
		this(id, code, info, submission);
		this.prompt = prompt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public DressGenRequest getSubmission() {
		return submission;
	}

	public void setSubmission(DressGenRequest submission) {
		this.submission = submission;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public String toString() {
		return "DressGenInfoSubmissionResponse [id=" + id + ", code=" + code + ", info=" + info + ", submission="
				+ submission + ", prompt=" + prompt + "]";
	}

}
