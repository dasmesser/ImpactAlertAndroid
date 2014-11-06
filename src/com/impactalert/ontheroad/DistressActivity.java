package com.impactalert.ontheroad;

import java.util.ArrayList;

import com.example.bike.R;
import com.example.bike.R.id;
import com.example.bike.R.layout;
import com.example.bike.R.menu;
import com.impactalert.settings.SettingsFileManager;
import com.impactalert.settings.SettingsWrapper;
import com.impactalert.utils.Constants;
import com.impactalert.utils.Contact;

import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class DistressActivity extends ActionBarActivity {

	MessageSender messageSender;
	static boolean messageSenderFinished;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distress);
		
		if(!SettingsFileManager.isManagerConfigured())
			SettingsFileManager.configureManager(getFilesDir().getAbsolutePath());
		
		SettingsWrapper settings = SettingsFileManager.getSettingsFile();
		
		messageSender.execute(settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.distress, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void cancelDistressMessage(View view){
		
	}
	
	private class MessageSender extends AsyncTask<SettingsWrapper, Void, Boolean>{

		private boolean isCanceled;
		
		@Override
		protected Boolean doInBackground(SettingsWrapper... params) {
			SettingsWrapper settings = params[0];
			ArrayList<Contact> arr = settings.getEmergencyMessageContacts();
			String message = settings.getEmergencyMessageText();
			
			message.replace(Constants.INSERT_NAME, settings.getName());
			//message.replace(Constants.INSERT_GPS, GPS);
			SmsManager smsManager = SmsManager.getDefault();
			
			for(Contact c : arr){
				
				if(!this.isCanceled){
					for(String phoneNumber : c.numbers){
						try {
							smsManager.sendTextMessage(phoneNumber, null, message, null, null);
						} 
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			DistressActivity.messageSenderFinished = true;
		}
		
		public void cancelTask(boolean cancel){
			this.isCanceled = cancel;
		}
	}

	private class MailSender extends AsyncTask<SettingsWrapper, Void, Boolean>{

		@Override
		protected Boolean doInBackground(SettingsWrapper... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			//Probablemente no se necesite
			
		}
	}
}
