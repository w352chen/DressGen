package com.uwaterloo.dressgen.converters;

import org.springframework.core.convert.converter.Converter;

import com.uwaterloo.dressgen.services.OrderType;

public class StringToOrderTypeConverter implements Converter<String, OrderType>{

	@Override
	public OrderType convert(String orderTypeStr) {
		return OrderType.parse(orderTypeStr);
	}

}
