package com.uwaterloo.dressgen.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Value;

public class DressGenFixedPrompt {
	
	private DressGenRequest dressGen;
	private String prompt;
	
	@Override
	public String toString() {
		return "DressGenFixedPrompt [dressGen=" + dressGen + ", prompt=" + prompt + "]";
	}


	public DressGenFixedPrompt() { }

	
	public DressGenFixedPrompt(DressGenRequest dressGen, String prompt) {
		super();
		this.dressGen = dressGen;
		this.prompt = prompt;
	}


	public DressGenRequest getDressGen() {
		return dressGen;
	}

	public void setDressGen(DressGenRequest dressGen) {
		this.dressGen = dressGen;
	}


	public String getPrompt() {
		return prompt;
	}


	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

}