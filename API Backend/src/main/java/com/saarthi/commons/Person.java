package com.saarthi.commons;

import java.util.List;

import lombok.Data;

@Data
public class Person {
	private Relation relation;
	private List<Document>docs;
}
