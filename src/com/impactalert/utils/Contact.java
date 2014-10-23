package com.impactalert.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contact implements Serializable{

	private static final long serialVersionUID = 1L;
	public String name;
	public List<String> numbers;
	
	public Contact(){
		numbers = new ArrayList<>();
	}
	
	public Contact(String name, List<String> numbers){
		this.name = name;
		this.numbers = numbers;
	}
}
