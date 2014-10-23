package com.impactalert.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import com.impactalert.customexceptions.SettingsManagerNotConfiguredException;

import android.content.Context;

public class SettingsFileManager {

	private static final String settingsFile = "settings.json"; 
	private static String appDir = null;
	
	public static final String SENSITIVITY = "sensitivity";
	private static final int SENSITIVITY_DEFAULT = 2;
	
	public static boolean isFirstRun(){
		return !new File(appDir, settingsFile).exists();
	}
	
	public static void configureManager(String appDir){
		SettingsFileManager.appDir = appDir;
	}
	
	public static boolean isManagerConfigured(){
		return SettingsFileManager.appDir != null;
	}
	
	public static void writeDefaultSettings(){
		JSONObject object = new JSONObject();
		try{
			object.put(SENSITIVITY, 2);
			//object.put();
		}
		catch(JSONException ex){
			
		}
	}
	
	public static void writeSettingsToFile(SettingsWrapper settings) throws SettingsManagerNotConfiguredException{
		if(!isManagerConfigured())
			throw new SettingsManagerNotConfiguredException();
		
		JSONObject json = settings.toJSONObject();
		
		File file = new File(appDir, settingsFile);
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(json.toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(writer != null){
				try{
					writer.close();
				}
				catch(IOException e){}
			}
		}
	}

	public static SettingsWrapper readSettingsFile(){
		File file = new File(appDir, settingsFile);
		
		if(!file.exists())
			return null;
		
		StringBuilder text = new StringBuilder();
		BufferedReader br = null;
		JSONObject json = null;

		try {
		    br = new BufferedReader(new FileReader(file));
		    String line;

		    while ((line = br.readLine()) != null) {
		        text.append(line);
		        text.append('\n');
		    }
		    
		    json = new JSONObject(text.toString());
		} 
		catch (IOException a) {
		    a.printStackTrace();
		}
		catch(JSONException e){
			e.printStackTrace();
		}
		finally {
		    try { br.close(); } catch (Exception e) { }
		}
		
		return new SettingsWrapper(json);
	}
}
