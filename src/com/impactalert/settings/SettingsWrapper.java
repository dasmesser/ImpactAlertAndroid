package com.impactalert.settings;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.impactalert.utils.Contact;

public class SettingsWrapper implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int sensitivity;
	private String name;
	private ArrayList<Contact> emergencyMessageContacts;
	private String emergencyMessageText;
	private ArrayList<Contact> emergencyMailContacts;
	private String emergencyMailText;
	
	private static final String NAME = "name";
	private static final String MESSAGE_CONTACTS = "messageContacts";
	private static final String MESSAGE_TEXT = "messageText";
	private static final String MAIL_CONTACTS = "mailContacts";
	private static final String MAIL_TEXT = "mailText";
	
	public SettingsWrapper(String name){
		this.name = name;
	}
	
	public SettingsWrapper(JSONObject json){
		try{
			this.name = (json.has(NAME) ? 	(String) json.get(NAME) : null);
			//this.emergencyMessageContacts = (json.has(MESSAGE_CONTACTS) ? (ArrayList<Contact>) json.get(MESSAGE_CONTACTS) : null);
			this.emergencyMessageText = 	((String) (json.has(MESSAGE_TEXT) ? (String) json.get(MESSAGE_TEXT) : null));
			//this.emergencyMailContacts = 	(json.has(MAIL_CONTACTS) ? (ArrayList<Contact>) json.get(MAIL_CONTACTS) : null);
			this.emergencyMailText = 		((String) (json.has(MAIL_TEXT) ? (String) json.get(MAIL_TEXT) : null));
			
			this.emergencyMessageContacts = new ArrayList<>();
			if(json.has(MESSAGE_CONTACTS)){
				JSONArray arr = (JSONArray) json.get(MESSAGE_CONTACTS);
				
				for(int i = 0; i < arr.length(); i++){
					String s = arr.optString(i);
					this.emergencyMessageContacts.add(Contact.contactFromString(s));
				}
			}
			
			this.emergencyMailContacts = new ArrayList<>();
			if(json.has(MAIL_CONTACTS)){
				JSONArray arr = (JSONArray) json.get(MAIL_CONTACTS);
				
				for(int i = 0; i < arr.length(); i++){
					this.emergencyMailContacts.add(Contact.contactFromString(arr.optString(i)));
				}
			}
		}
		catch(JSONException ax){
			ax.printStackTrace();
		}
	}

	public JSONObject toJSONObject(){
		JSONObject json = new JSONObject();
		try{
			if(name != null){
				json.put(NAME, name);
			}
			
			if(emergencyMessageContacts != null){
				json.put(MESSAGE_CONTACTS, new JSONArray(emergencyMessageContacts));
			}
			
			if(emergencyMessageText != null){
				json.put(MESSAGE_TEXT, emergencyMessageText);
			}
			
			if(emergencyMailContacts != null){
				json.put(MAIL_CONTACTS, new JSONArray(emergencyMailContacts));
			}
			
			if(emergencyMailText != null){
				json.put(MAIL_TEXT, emergencyMailText);
			}
		}
		catch(JSONException ax){
			ax.printStackTrace();
		}
		return json;
	}
	
	private JSONArray arrayListToJSONArray(ArrayList<Contact> contacts){
		JSONArray result = new JSONArray();
		
		return result;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public ArrayList<Contact> getEmergencyMessageContacts() {
		return emergencyMessageContacts;
	}

	public void setEmergencyMessageContacts(ArrayList<Contact> emergencyMessageContacts) {
		this.emergencyMessageContacts = emergencyMessageContacts;
	}

	public String getEmergencyMessageText() {
		return emergencyMessageText;
	}

	public void setEmergencyMessageText(String emergencyMessageText) {
		this.emergencyMessageText = emergencyMessageText;
	}

	public ArrayList<Contact> getEmergencyMailContacts() {
		return emergencyMailContacts;
	}

	public void setEmergencyMailContacts(ArrayList<Contact> emergencyMailContacts) {
		this.emergencyMailContacts = emergencyMailContacts;
	}

	public String getEmergencyMailText() {
		return emergencyMailText;
	}

	public void setEmergencyMailsText(String emergencyMailText) {
		this.emergencyMailText = emergencyMailText;
	}
}
