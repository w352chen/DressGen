package com.uwaterloo.dressgen.services;

public enum OrderType {
	
	Ascending ("a", "Asc"), 
	Descending ("d", "Desc");
	
	private String code;
	private String shortName;
	

	private OrderType(String code, String shortName) {
		this.code = code;
		this.shortName = shortName;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public static OrderType fromCode(String code) {
		for (OrderType o : OrderType.values()) {
			if (o.code.equalsIgnoreCase(code)) {
				return o;
			}
		}
		return null;
	}
	
	public static OrderType fromShortName(String shortName) {
		for (OrderType o : OrderType.values()) {
			if (o.shortName.equalsIgnoreCase(shortName)) {
				return o;
			}
		}
		return null;
	}
	
	public static OrderType fromName(String name) {
		for (OrderType o : OrderType.values()) {
			if (o.name().equalsIgnoreCase(name)) {
				return o;
			}
		}
		return null;
	}
	
	public static OrderType parse(String orderTypeStr) {
		OrderType ot = fromCode(orderTypeStr);
		if (ot == null) {
			ot = fromShortName(orderTypeStr);
			if (ot == null) {
				ot = fromName(orderTypeStr);
			}
		}
		return ot;
	}

}
