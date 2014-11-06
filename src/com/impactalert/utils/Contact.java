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
	
	public String toString(){
		String result = "[" + this.name + ", " + this.numbers + "]";
		return result;
	}
	
	public static Contact contactFromString(String s){
		s = s.replace("[", "");
		s = s.replace("]", "");
		
		String [] arr = s.split(", ");
		Contact c = null;
		
		if(arr.length > 0){
			String name = arr[0];
			ArrayList<String> numbers = new ArrayList<>();
			
			for(int i = 1; i < arr.length; i++){
				numbers.add(arr[i]);
			}
			
			c = new Contact(name, numbers); 
		}
		return c;
	}
}
