package com.impactalert.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.impactalert.ontheroad.OnTheRoadActivity;
import com.impactalert.settings.*;
import com.impactalert.utils.Constants;
import com.example.bike.R;

public class MainMenuActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main_menu);
		
		if(!SettingsFileManager.isManagerConfigured())
			SettingsFileManager.configureManager(getFilesDir().getAbsolutePath());
		
		if(SettingsFileManager.isFirstRun()){
			Intent mainIntent = new Intent(MainMenuActivity.this, MainConfigurationActivity.class);
			startActivity(mainIntent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
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
	
	public void gotoConfiguration(View view) {
		Intent intent = new Intent(this, MainConfigurationActivity.class);
		
		if(!SettingsFileManager.isManagerConfigured())
			SettingsFileManager.configureManager(getFilesDir().getAbsolutePath());
		
		SettingsWrapper settings = SettingsFileManager.getSettingsFile();
		
		intent.putExtra(Constants.SETTINGS, settings);
		startActivity(intent);
	}
	
	public void gotoAbout(View view) {
		startActivity(new Intent(this, AboutActivity.class));
	}
	
	public void gotoLegalNote(View view) {
		startActivity(new Intent(this, LegalNoteActivity.class));
	}
	
	public void gotoIniciar(View view) {
		startActivity(new Intent(this, OnTheRoadActivity.class));
	}
}
