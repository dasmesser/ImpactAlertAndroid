package com.impactalert.ontheroad;

import com.example.bike.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.impactalert.settings.SettingsWrapper;

public class OnTheRoadActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_on_the_road);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recorrido, menu);
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
	
	public void stopTravel(View view){
		StopTravelDialogFragment fragment = new StopTravelDialogFragment();
		fragment.show(getSupportFragmentManager(), "StopTravel");
	}
	
	public void sendDistressMsg(View view){
		SendDistressMessageDialogFragment fragment = new SendDistressMessageDialogFragment();
		fragment.show(getSupportFragmentManager(), "DistressMessage");
	}
	
	private class StopTravelDialogFragment extends DialogFragment{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(R.string.stop_travel_confirmation)
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id){
						
					}
				})
				.setNegativeButton(R.string.disconfirm, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						
					}
				});
			
			return builder.create();
		}
	}
	
	private class SendDistressMessageDialogFragment extends DialogFragment{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(R.string.send_distress_msg_confirmation)
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id){
						
					}
				})
				.setNegativeButton(R.string.disconfirm, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						
					}
				});
			
			return builder.create();
		}
	}
	
	private class ImpactDetector extends AsyncTask<SettingsWrapper, Void, Boolean>{

		@Override
		protected Boolean doInBackground(SettingsWrapper... params) {
			//Algoritmo de deteccion de impacto

			//If impact detected
			if(false){
				//ImpactDetectedDialogFragment fragment = new ImpactDetectedDialogFragment();
				//fragment.show(getSupportFragmentManager(), "ConfirmationMessage");
			}
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			//Probablemente no se necesite
			
		}
	}
	
	private static class ImpactDetectedDialogFragment extends DialogFragment{
		
		static SettingsWrapper settings = null;
		
		public ImpactDetectedDialogFragment(SettingsWrapper settings){
			ImpactDetectedDialogFragment.settings = settings;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(R.string.stop_travel_confirmation)
				.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id){
						//Intent intent = new Intent(OnTheRoadActivity.this, DistressMessageSender.class);
						//intent.putExtra()
					}
				})
				.setNegativeButton(R.string.disconfirm, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						
					}
				});
			
			return builder.create();
		}
	}
}


