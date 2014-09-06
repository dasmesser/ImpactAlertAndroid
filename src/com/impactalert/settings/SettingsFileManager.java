package com.impactalert.settings;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsFileManager {

	private static final String settingsFilePath = "settings.json"; 
	
	public static final String SENSITIVITY = "sensitivity";
	private static final int SENSITIVITY_DEFAULT = 2;
	
	public static void initialValidation(){
		File file = new File(settingsFilePath);
		if(file.exists()){
			
		}
		else{
			
		}
	}
	
	public static void writeDefaultSettings(){
		JSONObject object = new JSONObject();
		try{
			object.put(SENSITIVITY, 2);
		}
		catch(JSONException ex){
			
		}
	}
}
