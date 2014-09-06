package com.impactalert.main;

import com.example.bike.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class OnTheRoadActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recorrido);
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
}
