package com.uwaterloo.dressgen.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DressGenRequest implements Serializable {
	private String username;
	private int submissionId;
	private Date requestTimestamp;
	private DressGenDetail color;
	private DressGenDetail pattern;
	private DressGenDetail silhouette;
	private DressGenDetail neckline;
	private DressGenDetail sleeves;
	private DressGenDetail sleeveLength;
	private DressGenDetail skirt;
	private DressGenDetail skirtLength;
	private DressGenDetail material;
	private String info;
	
	public DressGenRequest() {
		this.requestTimestamp = new Date(System.currentTimeMillis());
	}
	

	public DressGenRequest(String username, DressGenDetail color, DressGenDetail pattern, DressGenDetail silhouette, DressGenDetail neckline, DressGenDetail sleeves,
			DressGenDetail sleeveLength, DressGenDetail skirt, DressGenDetail skirtLength, DressGenDetail material, String info) {
		this();
		this.username = username;
		this.color = color;
		this.pattern = pattern;
		this.silhouette = silhouette;
		this.neckline = neckline;
		this.sleeves = sleeves;
		this.sleeveLength = sleeveLength;
		this.skirt = skirt;
		this.skirtLength = skirtLength;
		this.material = material;
		this.info = info;
	}
	
	public DressGenRequest(int submissionId, String username, DressGenDetail color, DressGenDetail pattern, DressGenDetail silhouette, DressGenDetail neckline, DressGenDetail sleeves,
			DressGenDetail sleeveLength, DressGenDetail skirt, DressGenDetail skirtLength, DressGenDetail material, String info) {
		this(username, color, pattern, silhouette, neckline, sleeves, sleeveLength, skirt, skirtLength, material, info);
		this.submissionId = submissionId;
	}


	public int getSubmissionId() {
		return submissionId;
	}


	public void setSubmissionId(int submissionId) {
		this.submissionId = submissionId;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public Date getRequestTimestamp() {
		return requestTimestamp;
	}


	public void setRequestTimestamp(Date requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
	}


	public DressGenDetail getColor() {
		return color;
	}


	public void setColor(DressGenDetail color) {
		this.color = color;
	}


	public DressGenDetail getPattern() {
		return pattern;
	}


	public void setPattern(DressGenDetail pattern) {
		this.pattern = pattern;
	}


	public DressGenDetail getSilhouette() {
		return silhouette;
	}


	public void setSilhouette(DressGenDetail silhouette) {
		this.silhouette = silhouette;
	}


	public DressGenDetail getNeckline() {
		return neckline;
	}


	public void setNeckline(DressGenDetail neckline) {
		this.neckline = neckline;
	}


	public DressGenDetail getSleeves() {
		return sleeves;
	}


	public void setSleeves(DressGenDetail sleeves) {
		this.sleeves = sleeves;
	}

//	@JsonProperty("sleeve_length")
	public DressGenDetail getSleeveLength() {
		return sleeveLength;
	}


	public void setSleeveLength(DressGenDetail sleeveLength) {
		this.sleeveLength = sleeveLength;
	}


	public DressGenDetail getSkirt() {
		return skirt;
	}


	public void setSkirt(DressGenDetail skirt) {
		this.skirt = skirt;
	}


//	@JsonProperty("skirt_length")
	public DressGenDetail getSkirtLength() {
		return skirtLength;
	}


	public void setSkirtLength(DressGenDetail skirtLength) {
		this.skirtLength = skirtLength;
	}


	public DressGenDetail getMaterial() {
		return material;
	}


	public void setMaterial(DressGenDetail material) {
		this.material = material;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}
	
	public boolean contains(String searchStr) {
		String searchStrInLowerCase = searchStr.toLowerCase();
		return pattern.getName().toLowerCase().contains(searchStrInLowerCase) ||
				silhouette.getName().toLowerCase().contains(searchStrInLowerCase) ||
				color.getName().toLowerCase().contains(searchStrInLowerCase) ||
				skirt.getName().toLowerCase().contains(searchStrInLowerCase) ||
				skirtLength.getName().toLowerCase().contains(searchStrInLowerCase) ||
				sleeves.getName().toLowerCase().contains(searchStrInLowerCase) ||
				sleeveLength.getName().toLowerCase().contains(searchStrInLowerCase) ||
				neckline.getName().toLowerCase().contains(searchStrInLowerCase) ||
				material.getName().toLowerCase().contains(searchStrInLowerCase) ||
				info.toLowerCase().contains(searchStrInLowerCase);
	}
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public String getFormattedTimestamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");          
		return format.format(this.requestTimestamp);
	}

//	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public String getDescription() {
		StringBuffer output = new StringBuffer("A");
//		if (!StringUtils.isEmpty(length) && !StringUtils.isWhitespace(length)) {
//			output.append(" [" + length + "]");
//		}
//		if (silhouette != null && !StringUtils.isEmpty(silhouette.getName()) && !StringUtils.isWhitespace(silhouette.getName())) {
//			output.append(" " + silhouette.getName());
//		}
//		if (color != null && !StringUtils.isEmpty(color.getName()) && !StringUtils.isWhitespace(color.getName())) {
//			output.append(" " + color.getName());
//		}
//		if (pattern != null && !StringUtils.isEmpty(pattern.getName()) && !StringUtils.isWhitespace(pattern.getName())) {
//			output.append(" " + pattern.getName());
//		}
//		if (skirtLength != null && !StringUtils.isEmpty(skirtLength.getName()) && !StringUtils.isWhitespace(skirtLength.getName())) {
//			output.append(" " + skirtLength.getName());
//		}
//		if (skirt != null && !StringUtils.isEmpty(skirt.getName()) && !StringUtils.isWhitespace(skirt.getName())) {
//			output.append(" " + skirt.getName());
//		}
		if (color != null && !StringUtils.isEmpty(color.getName()) && !StringUtils.isWhitespace(color.getName())) {
			output.append(" " + formatField(color.getName()));
		}
		if (pattern != null && !StringUtils.isEmpty(pattern.getName()) && !StringUtils.isWhitespace(pattern.getName())) {
			output.append(" " + formatField(pattern.getName()));
		}
		if (material != null && !StringUtils.isEmpty(material.getName()) && !StringUtils.isWhitespace(material.getName())) {
			output.append(" " + formatField(material.getName()));
		}
		if (skirtLength != null && !StringUtils.isEmpty(skirtLength.getName()) && !StringUtils.isWhitespace(skirtLength.getName())) {
			output.append(" " + formatField(skirtLength.getName()) + "-length");
		}
		if (silhouette != null && !StringUtils.isEmpty(silhouette.getName()) && !StringUtils.isWhitespace(silhouette.getName())) {
			output.append(" " + formatField(silhouette.getName()));
			if (!silhouette.getName().equals("ball gown")) {
				output.append(" dress");
			}
		} else {
			output.append(" dress");		
		}
		// output.append(" dress");
//		boolean sleeveLengthOptionProvided = sleeveLength != null && !StringUtils.isEmpty(sleeveLength.getName()) && !StringUtils.isWhitespace(sleeveLength.getName());
//		boolean sleeveOptionProvided = sleeves != null && !StringUtils.isEmpty(sleeves.getName()) && !StringUtils.isWhitespace(sleeves.getName());
//		if (sleeveLengthOptionProvided) {
//			output.append(" with " + sleeveLength.getName());
//			if (!sleeveOptionProvided) {
//				output.append(" sleeve length");
//			}
//		}
//		if (sleeveOptionProvided) {
//			if (!sleeveLengthOptionProvided) {
//				output.append(" with");
//			}
//			output.append(" " + sleeves.getName() + " sleeves");
//		}
//		if (material != null && !StringUtils.isEmpty(material.getName()) && !StringUtils.isWhitespace(material.getName())) {
//			output.append(" made of " + material.getName());
//		}
//		boolean necklineOptionProvided = neckline != null && !StringUtils.isEmpty(neckline.getName()) && !StringUtils.isWhitespace(neckline.getName());
//		if (necklineOptionProvided) {
//			output.append(" with " + neckline.getName() + " neckline");
//		}
		boolean necklineOptionProvided = neckline != null && !StringUtils.isEmpty(neckline.getName()) && !StringUtils.isWhitespace(neckline.getName());
		if (necklineOptionProvided) {
			output.append(" ; " + formatField(neckline.getName()) + " neckline");
		}
		
//		if (skirtLength != null && !StringUtils.isEmpty(skirtLength.getName()) && !StringUtils.isWhitespace(skirtLength.getName())) {
//			output.append(" " + formatField(skirtLength.getName()));
//		}
//		if (skirt != null && !StringUtils.isEmpty(skirt.getName()) && !StringUtils.isWhitespace(skirt.getName())) {
//			output.append(" " + formatField(skirt.getName()));
//		}
		// output.append(" dress");
		boolean sleeveLengthOptionProvided = sleeveLength != null && !StringUtils.isEmpty(sleeveLength.getName()) && !StringUtils.isWhitespace(sleeveLength.getName());
		boolean sleeveOptionProvided = sleeves != null && !StringUtils.isEmpty(sleeves.getName()) && !StringUtils.isWhitespace(sleeves.getName());
		if (sleeveLengthOptionProvided) {
			output.append(" ; " + formatField(sleeveLength.getName()));
			if (!sleeveOptionProvided) {
				output.append(" sleeve length");
			}
		}
		if (sleeveOptionProvided) {
			if (!sleeveLengthOptionProvided) {
				output.append(" ;");
			}
			output.append(" " + formatField(sleeves.getName()) + " sleeves");
		}
		if (skirt != null && !StringUtils.isEmpty(skirt.getName()) && !StringUtils.isWhitespace(skirt.getName())) {
			output.append(" ; " + formatField(skirt.getName()) + " skirt");
		}
		if (!StringUtils.isEmpty(info) && !StringUtils.isWhitespace(info)) {
//			output.append(necklineOptionProvided ? " and" : " with");
			output.append(" ; " + info);
		}
		return output.toString();
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public String getSubject() {
		StringBuffer output = new StringBuffer("A");
//		if (!StringUtils.isEmpty(length) && !StringUtils.isWhitespace(length)) {
//			output.append(" [" + length + "]");
//		}
		if (color != null && !StringUtils.isEmpty(color.getName()) && !StringUtils.isWhitespace(color.getName())) {
			output.append(" " + formatField(color.getName()));
		}
		if (pattern != null && !StringUtils.isEmpty(pattern.getName()) && !StringUtils.isWhitespace(pattern.getName())) {
			output.append(" " + formatField(pattern.getName()));
		}
		if (material != null && !StringUtils.isEmpty(material.getName()) && !StringUtils.isWhitespace(material.getName())) {
			output.append(" " + formatField(material.getName()));
		}
		if (skirtLength != null && !StringUtils.isEmpty(skirtLength.getName()) && !StringUtils.isWhitespace(skirtLength.getName())) {
			output.append(" " + formatField(skirtLength.getName()) + "-length");
		}
		if (silhouette != null && !StringUtils.isEmpty(silhouette.getName()) && !StringUtils.isWhitespace(silhouette.getName())) {
			output.append(" " + formatField(silhouette.getName()));
			if (!silhouette.getName().equals("ball gown")) {
				output.append(" dress");
			}
		} else {
			output.append(" dress");		
		}
//		output.append(" dress");
		boolean necklineOptionProvided = neckline != null && !StringUtils.isEmpty(neckline.getName()) && !StringUtils.isWhitespace(neckline.getName());
		if (necklineOptionProvided) {
			output.append(" ; " + formatField(neckline.getName()) + " neckline");
		}
		
//		if (skirtLength != null && !StringUtils.isEmpty(skirtLength.getName()) && !StringUtils.isWhitespace(skirtLength.getName())) {
//			output.append(" " + formatField(skirtLength.getName()));
//		}
//		if (skirt != null && !StringUtils.isEmpty(skirt.getName()) && !StringUtils.isWhitespace(skirt.getName())) {
//			output.append(" ; " + formatField(skirt.getName()));
//		}
		// output.append(" dress");
		boolean sleeveLengthOptionProvided = sleeveLength != null && !StringUtils.isEmpty(sleeveLength.getName()) && !StringUtils.isWhitespace(sleeveLength.getName());
		boolean sleeveOptionProvided = sleeves != null && !StringUtils.isEmpty(sleeves.getName()) && !StringUtils.isWhitespace(sleeves.getName());
		if (sleeveLengthOptionProvided) {
			output.append(" ; " + formatField(sleeveLength.getName()));
			if (!sleeveOptionProvided) {
				output.append(" sleeve length");
			}
		}
		if (sleeveOptionProvided) {
			if (!sleeveLengthOptionProvided) {
				output.append(" ;");
			}
			output.append(" " + formatField(sleeves.getName()) + " sleeves");
		}
//		if (material != null && !StringUtils.isEmpty(material.getName()) && !StringUtils.isWhitespace(material.getName())) {
//			output.append(" made of " + formatField(material.getName()));
//		}
//		boolean necklineOptionProvided = neckline != null && !StringUtils.isEmpty(neckline.getName()) && !StringUtils.isWhitespace(neckline.getName());
//		if (necklineOptionProvided) {
//			output.append(" with " + formatField(neckline.getName()) + " neckline");
//		}
		if (skirt != null && !StringUtils.isEmpty(skirt.getName()) && !StringUtils.isWhitespace(skirt.getName())) {
			output.append(" ; " + formatField(skirt.getName()) + " skirt");
		}
		if (!StringUtils.isEmpty(info) && !StringUtils.isWhitespace(info)) {
//			output.append(necklineOptionProvided ? " and" : " with");
			output.append(" ; " + formatField(info));
		}
		return output.toString();
	}
	
	private String formatField(String field) {
		return String.format("<span class='historySubjectField'>%s</span>", field); 
	}

	
	@Override
	public String toString() {
//	a [maxi] [fitted] [orange] [plaid] dress with [short sleeves] made of [satin] [with black trim]
//		return String.format( "A [%s] [%s] [%s] [%s] dress with [%s] made of [%s] with [%s]", length, type, color, pattern, sleeve, material, info);
		StringBuffer output = new StringBuffer(getDescription());
		String timestampStr = getFormattedTimestamp();
		output.append("<div className='submissionNameAndTS'> - submitted by " + username + " at " + timestampStr + "</div>");
		return output.toString();
//		return "DressGenInfo [color=" + color + ", pattern=" + pattern + ", type=" + type + ", neckline=" + neckline
//				+ ", sleeve=" + sleeve + ", length=" + length + ", material=" + material + ", info=" + info + "]";
	}
	
	
	
}