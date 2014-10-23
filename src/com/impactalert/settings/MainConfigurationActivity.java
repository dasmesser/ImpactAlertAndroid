package com.impactalert.settings;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bike.R;
import com.impactalert.utils.Constants;
import com.impactalert.utils.Contact;

public class MainConfigurationActivity extends ActionBarActivity {

	public ArrayList<Contact> contacts;
	public String name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_first_run);
		
		Intent intent = getIntent();
		contacts = (ArrayList<Contact>)intent.getSerializableExtra(Constants.CONTACTS);
		if(contacts == null){
			FirstRunDialogFragment fragment = new FirstRunDialogFragment();
			fragment.show(getSupportFragmentManager(), "NoticeDialogFragment");
		}
		else{
			Button b = (Button)findViewById(R.id.continue_button);
			b.setEnabled(true);
			((TextView)findViewById(R.id.textView3)).setText(getString(R.string.contacts_selected));
		}
		
		if (intent.getExtras() != null){
			name = intent.getExtras().getString(Constants.NAME);
			((EditText)findViewById(R.id.editText1)).setText(name);
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
	
	public void gotoMessageConfig(View view) {
		Intent intent = new Intent(this, MessageConfigActivity.class);
		intent.putExtra(Constants.CONTACTS, contacts);
		intent.putExtra(Constants.NAME, name);
		startActivity(intent);
	}
	
	public void gotoContactList(View view) {
		Intent intent = new Intent(this, ContactListActivity.class);
		String n = ((EditText)findViewById(R.id.editText1)).getText().toString();
		intent.putExtra(Constants.NAME, n);
		startActivity(intent);
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
}
