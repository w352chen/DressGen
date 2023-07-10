package com.uwaterloo.dressgen.model;

public enum DressGUIElementType {

	Color("Color"), 
	Pattern("Pattern"), 
	Silhouette("Silhouette"), 
	Neckline("Neckline"), 
	Sleeves("Sleeves"), 
	SleeveLength("Sleeve Length"), 
	Skirt("Skirt"), 
	SkirtLength("Skirt Length"), 
	Material("Material"), 
	Info("Info");
	
	String guiElementName;

	private DressGUIElementType(String guiElementName) {
		this.guiElementName = guiElementName;
	}

	public String getGuiElementName() {
		return guiElementName;
	}
	
}
    