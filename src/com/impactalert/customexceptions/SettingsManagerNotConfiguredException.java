package com.impactalert.customexceptions;

public class SettingsManagerNotConfiguredException extends Exception{
	
	public SettingsManagerNotConfiguredException(){ }
	
	public SettingsManagerNotConfiguredException(String message){
		super(message);
	}
}
