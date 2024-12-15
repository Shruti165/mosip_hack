package com.saarthi.utils;

import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.google.gson.Gson;

@Converter
public class ListToJsonConverter implements AttributeConverter<List<Object>, String>{

	@Override
	public String convertToDatabaseColumn(List<Object> attribute) {
		return new Gson().toJson(attribute);
	}

	@Override
	public List<Object> convertToEntityAttribute(String dbData) {
		return new Gson().fromJson(dbData, List.class);
	}

}
