package com.impactalert.settings;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.impactalert.utils.Contact;

public class SettingsWrapper {

	private int sensitivity;
	private String name;
	private ArrayList<Contact> emergencyMessageContacts;
	private String emergencyMessageText;
	private ArrayList<Contact> emergencyMailContacts;
	private String emergencyMailText;
	
	public static final String NAME = "name";
	public static final String MESSAGE_CONTACTS = "messageContacts";
	public static final String MESSAGE_TEXT = "messageText";
	public static final String MAIL_CONTACTS = "mailContacts";
	public static final String MAIL_TEXT = "mailText";
	
	public SettingsWrapper(String name){
		this.name = name;
	}
	
	public SettingsWrapper(JSONObject json){
		try{
			this.name = (json.has(NAME) ? (String) json.get(NAME) : null);
			this.emergencyMessageContacts = (json.has(MESSAGE_CONTACTS) ? (ArrayList<Contact>) json.get(MESSAGE_CONTACTS) : null);
			this.emergencyMessageText = (json.has(MESSAGE_TEXT) ? (String) json.get(MESSAGE_TEXT) : null);
			this.emergencyMailContacts = (json.has(MAIL_CONTACTS) ? (ArrayList<Contact>) json.get(MAIL_CONTACTS) : null);
			this.emergencyMailText = (json.has(MAIL_TEXT) ? (String) json.get(MAIL_TEXT) : null);
		}
		catch(JSONException ax){
			ax.printStackTrace();
		}
	}

	public JSONObject toJSONObject(){
		JSONObject json = new JSONObject();
		try{
			json.putOpt(NAME, name);
			json.putOpt(MESSAGE_CONTACTS, emergencyMessageContacts);
			json.putOpt(MESSAGE_TEXT, emergencyMessageText);
			json.putOpt(MAIL_CONTACTS, emergencyMailContacts);
			json.putOpt(MAIL_TEXT, emergencyMailText);
		}
		catch(JSONException ax){
			ax.printStackTrace();
		}
		return json;
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
