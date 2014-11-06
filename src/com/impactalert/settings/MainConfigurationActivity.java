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
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bike.R;
import com.impactalert.main.MainMenuActivity;
import com.impactalert.utils.Constants;
import com.impactalert.utils.Contact;

public class MainConfigurationActivity extends ActionBarActivity {

	public ArrayList<Contact> contacts;
	public String name;
	public String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main_configuration);
		
		((EditText)findViewById(R.id.messageText)).setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
			    if(hasFocus){
			    	ScrollView sv = (ScrollView)findViewById(R.id.scroll_view);
			    	sv.scrollTo(0, sv.getBottom() + 40);
			    }
			}
		});
		
		Intent intent = getIntent();
		
		SettingsWrapper settings = (SettingsWrapper)intent.getSerializableExtra(Constants.SETTINGS);
		
		if(settings == null){
		
			contacts = (ArrayList<Contact>)intent.getSerializableExtra(Constants.CONTACTS);
			
			//if there are no contacts in the intent, this is the first time the program is run
			if(contacts == null){
				FirstRunDialogFragment fragment = new FirstRunDialogFragment();
				fragment.show(getSupportFragmentManager(), "NoticeDialogFragment");
			}
			//if there are contacts, the continue button must be enabled and the tag's text must be setted
			else{
				Button b = (Button)findViewById(R.id.continue_button);
				b.setEnabled(true);
				((TextView)findViewById(R.id.textView3)).setText(getString(R.string.contacts_selected));
			}
			
			if (intent.getExtras() != null){			
				name = intent.getExtras().getString(Constants.NAME);
				if(name == null){
					name = "";
				}
				
				message = intent.getExtras().getString(Constants.MESSAGE);
				if(message == null){
					message = getText(R.string.distress_message_default_first)
							+ " "  + Constants.INSERT_NAME
							+ " "  + getText(R.string.distress_message_default_cont);
				}
			}
			else{
				name = "";
				message = getText(R.string.distress_message_default_first)
						+ " "  + Constants.INSERT_NAME
						+ " "  + getText(R.string.distress_message_default_cont);
			}
			
			((EditText)findViewById(R.id.nameText)).setText(name);
			((EditText)findViewById(R.id.messageText)).setText(message);
		}
		else{//the settings have been received from MainMenuActivity, just need to set everything from the info in there
			this.contacts = settings.getEmergencyMessageContacts();
			this.name = settings.getName();
			this.message = settings.getEmergencyMessageText();
			
			if(this.contacts != null && this.contacts.size() > 0){
				((TextView)findViewById(R.id.textView3)).setText(getString(R.string.contacts_selected));
				
				if(this.name != null && !"".equals(this.name) &&
						this.message != null && !"".equals(this.message)){
					Button b = (Button)findViewById(R.id.continue_button);
					b.setEnabled(true);
				}
			}
			((EditText)findViewById(R.id.nameText)).setText(name);
			((EditText)findViewById(R.id.messageText)).setText(message);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first_run, menu);
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
	
	public void insertGPS(View view){
		insertTag(Constants.INSERT_GPS);
	}
	
	public void insertName(View view){
		insertTag(Constants.INSERT_NAME);
	}
	
	public void insertTag(String tag){
		EditText editText = (EditText) findViewById(R.id.messageText);
		int start = editText.getSelectionStart();
		int end = editText.getSelectionEnd();
		String message = editText.getText().toString();
		
		message = message.substring(0, start) + tag + message.substring(end, message.length());
		editText.setText(message);
		editText.setSelection(start + tag.length());
	}
	
	public void gotoContactList(View view) {
		Intent intent = new Intent(this, ContactListActivity.class);
		
		String name = ((EditText)findViewById(R.id.nameText)).getText().toString();
		intent.putExtra(Constants.NAME, name);
		
		String message = ((EditText)findViewById(R.id.messageText)).getText().toString();
		intent.putExtra(Constants.MESSAGE, message);
		
		intent.putExtra(Constants.CONTACTS, contacts);
		
		startActivity(intent);
	}
	
	public void finishConfig(View view){
		this.message = ((EditText)findViewById(R.id.messageText)).getText().toString();
		this.name = ((EditText)findViewById(R.id.nameText)).getText().toString();
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
	
	private class FirstRunDialogFragment extends DialogFragment{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(R.string.first_time_presentation)
				.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id){
						FirstRunDialogContinuationFragment fragment = new FirstRunDialogContinuationFragment();
						fragment.show(getSupportFragmentManager(), "Continuation");
					}
				});
			
			return builder.create();
		}
	}
	
	private class FirstRunDialogContinuationFragment extends DialogFragment{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(R.string.first_time_presentation_continue)
				.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id){
						
					}
				});
			
			return builder.create();
		}
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
