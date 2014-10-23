package com.impactalert.settings;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.bike.R;
import com.impactalert.main.MainMenuActivity;
import com.impactalert.utils.Constants;
import com.impactalert.utils.Contact;

public class MessageConfigActivity extends ActionBarActivity {

	public String name;
	public ArrayList<Contact> contacts;
	public String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_config);
		
		Intent intent = getIntent();
		contacts = (ArrayList<Contact>) intent.getSerializableExtra(Constants.CONTACTS);
		
		assert intent.getExtras() != null;
		name = intent.getExtras().getString(Constants.NAME);
		
		((EditText)findViewById(R.id.editText1)).setText(getText(R.string.distress_message_default_first)
				+ " "  + Constants.INSERT_NAME
				+ " "  + getText(R.string.distress_message_default_cont));
	}

	public void insertGPS(View view){
		insertTag(Constants.INSERT_GPS);
	}
	
	public void insertName(View view){
		insertTag(Constants.INSERT_NAME);
	}
	
	public void insertTag(String tag){
		EditText editText = (EditText) findViewById(R.id.editText1);
		int start = editText.getSelectionStart();
		int end = editText.getSelectionEnd();
		String message = editText.getText().toString();
		
		message = message.substring(0, start) + tag + message.substring(end, message.length());
		editText.setText(message);
		editText.setSelection(start + tag.length());
	}
	
	public void finishConfig(View view){
		this.message = ((EditText)findViewById(R.id.editText1)).getText().toString();
		ConfirmationFragment fragment = new ConfirmationFragment(contacts, name, message, this, selectFragmentString());
		fragment.show(getSupportFragmentManager(), "ConfirmationFragment");
	}
	
	public String selectFragmentString(){
		if(message.contains(Constants.INSERT_GPS)){
			if(message.contains(Constants.INSERT_NAME)){
				//missing none
				return getString(R.string.missing_none);
			}
			else{
				//missing name
				return getString(R.string.missing_name);
			}
		}
		else{
			if(message.contains(Constants.INSERT_NAME)){
				//missing GPS
				return getString(R.string.missing_gps);
			}
			else{
				//missing both
				return getString(R.string.missing_both);
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_config, menu);
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
	
	private static class ConfirmationFragment extends DialogFragment{
		
		static ArrayList<Contact> contacts = null;
		static String name = "";
		static String message = "";
		static Context context = null;
		
		String messageToDisplay;
		
		public ConfirmationFragment(ArrayList<Contact> contacts,
				String name, String message, Context context, String messageToDisplay){
			super();
			
			ConfirmationFragment.contacts = contacts;
			ConfirmationFragment.name = name;
			ConfirmationFragment.message = message;
			ConfirmationFragment.context = context;
			
			this.messageToDisplay = messageToDisplay;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(this.messageToDisplay)
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id){
						
						SettingsWrapper settings = new SettingsWrapper(ConfirmationFragment.name);
						settings.setEmergencyMessageText(ConfirmationFragment.message);
						settings.setEmergencyMessageContacts(ConfirmationFragment.contacts);
						
						try{
							SettingsFileManager.writeSettingsToFile(settings);
						}
						catch(Exception e){
							SettingsFileManager.configureManager(context.getFilesDir().getAbsolutePath());
							try{SettingsFileManager.writeSettingsToFile(settings);}catch(Exception ex){}
						}
						
						Intent intent = new Intent(context, MainMenuActivity.class);
						startActivity(intent);
					}
				}).
				setNegativeButton(R.string.disconfirm, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						
					}
				});
			
			return builder.create();
		}
	}
}
