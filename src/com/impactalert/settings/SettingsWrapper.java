package com.impactalert.settings;

import java.util.List;

import com.impactalert.customexceptions.SettingsWrapperFactoryException;

public class SettingsWrapper {

	public int sensitivity;
	public List<String> emergencyMessageNumbers;
	public String emergencyMessasgeText;
	public List<String> emergencyMails;
	public String emergencyMailsText;
	
	private static int settingsWrappersConstructed = 0;
	
	private SettingsWrapper(){
		
	}
	
	public SettingsWrapper SettingsWrapperFactory() throws SettingsWrapperFactoryException{
		if(settingsWrappersConstructed++ == 0)
			return new SettingsWrapper();
		throw new SettingsWrapperFactoryException();
	}
	
}
