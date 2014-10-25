package com.impactalert.settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bike.R;
import com.impactalert.utils.Constants;
import com.impactalert.utils.Contact;

public class ContactListActivity extends ListActivity {

	String name;
	ArrayList<ContactSelect> contacts;
	String message;
	MyCustomAdapter dataAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		
		Intent intent = getIntent();
		
		contacts = getContactList((ArrayList<Contact>)intent.getSerializableExtra(Constants.CONTACTS));
	    
		name = (String)intent.getStringExtra(Constants.NAME);
	    name = (name == null ? "" : name);
	    
	    message = (String)intent.getStringExtra(Constants.MESSAGE);
	    message = (message == null ? "" : message);
		
		if(contacts.size() == 0){
			setCheckButtonNoContactListener();
		}
		else{
			setAdapter();
			setCheckButtonListener();
		}
	}

	public ArrayList<ContactSelect> getContactList(ArrayList<Contact> selectedContacts) {
		ArrayList<ContactSelect> contacts = new ArrayList<ContactSelect>();

		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);

					List<String> numbers = new ArrayList<>();

					// Solo toma en cuenta el primer numero del contacto
					while (pCur.moveToNext()) {
						numbers.add(pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
					}

					if (numbers.size() > 0)
						contacts.add(new ContactSelect(name, numbers, isContactSelected(selectedContacts, name)));

					pCur.close();
				}
			}
		}

		cur.close();
		Collections.sort(contacts, new Comparator<ContactSelect>(){
			@Override
			public int compare(ContactSelect c1, ContactSelect c2) {
				return c1.name.compareTo(c2.name);
			}
		});
		
		return contacts;
	}

	public boolean isContactSelected(ArrayList<Contact> selectedContacts, String name){
		if(selectedContacts == null)
			return false;
		
		for(Contact c : selectedContacts)
			if(c.name.equals(name))
				return true;
		
		return false;
	}
	
	public void setCheckButtonNoContactListener(){
		Button myButton = (Button) findViewById(R.id.contactsSelected);
		myButton.setText(getString(R.string.close_app));
		TextView myTextView = (TextView) findViewById(R.id.contacts_banner);
		myTextView.setText(getString(R.string.no_data));
		myButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goHome(); 
			}
		});
	}
	
	public void goHome(){
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
	}
	
	public void setAdapter() {
		dataAdapter = new MyCustomAdapter(this,
				R.layout.activity_contact_list_aux, contacts);
		ListView listView = (ListView) getListView();
		listView.setAdapter(dataAdapter);

		/*listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ContactSelect contact = (ContactSelect) parent.getItemAtPosition(position);
				
				Toast.makeText(getApplicationContext(),
						"Click",
						Toast.LENGTH_SHORT).show();
				
				contact.selected = !(contact.selected);
			}
		});*/
	}

	public void setCheckButtonListener() {
		Button myButton = (Button) findViewById(R.id.contactsSelected);
		myButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ArrayList<ContactSelect> contactList = dataAdapter.contactList;
				ArrayList<ContactSelect> contactsSelected = new ArrayList<>();
				for (int i = 0; i < contactList.size(); i++) {
					ContactSelect contact = contactList.get(i);
					if (contact.selected) {
						contactsSelected.add(contact);
					}
				}
				
				if(contactsSelected.size() == 0){
					Toast.makeText(getApplicationContext(),
							getString(R.string.no_contacts_checked),
							Toast.LENGTH_SHORT).show();
				}
				else{
					Intent intent = new Intent(ContactListActivity.this, MainConfigurationActivity.class);
					intent.putExtra(Constants.CONTACTS, contactSelectedToContacts(contactsSelected));
					intent.putExtra(Constants.NAME, name);
					startActivity(intent);
				}
			}
		});
	}

	public ArrayList<Contact> contactSelectedToContacts(ArrayList<ContactSelect> contacts){
		ArrayList<Contact> res = new ArrayList<>();
		
		for(int i = 0; i < contacts.size(); i++){
			ContactSelect c = contacts.get(i);
			res.add(new Contact(c.name, c.numbers));
		}
		return res;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
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
	
	private class ContactSelect {

		String name;
		List<String> numbers;
		boolean selected;

		ContactSelect(String name, List<String> numbers) {
			this.name = name;
			this.numbers = numbers;
			this.selected = false;
		}
		
		ContactSelect(String name, List<String> numbers, boolean selected){
			this.name = name;
			this.numbers = numbers;
			this.selected = selected;
		}
	}

	private class MyCustomAdapter extends ArrayAdapter<ContactSelect> {

		private ArrayList<ContactSelect> contactList;

		public MyCustomAdapter(Context context, int textViewResourceId,
				ArrayList<ContactSelect> contactList) {
			super(context, textViewResourceId, contactList);
			this.contactList = new ArrayList<ContactSelect>();
			this.contactList.addAll(contactList);
		}

		private class RowContent {
			CheckBox checkBox;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			RowContent holder = null;

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.activity_contact_list_aux,
						null);

				holder = new RowContent();
				holder.checkBox = (CheckBox) convertView
						.findViewById(R.id.checkBox1);
				convertView.setTag(holder);

				holder.checkBox.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						ContactSelect contact = (ContactSelect) cb.getTag();
						/*
						 * Toast.makeText( getApplicationContext(),
						 * "Clicked on Checkbox: " + cb.getText() + " is " +
						 * cb.isChecked(), Toast.LENGTH_LONG) .show();
						 */
						contact.selected = cb.isChecked();
					}
				});
			} else {
				holder = (RowContent) convertView.getTag();
			}

			ContactSelect contact = contactList.get(position);
			holder.checkBox.setText(contact.name);
			holder.checkBox.setChecked(contact.selected);
			holder.checkBox.setTag(contact);

			return convertView;
		}
	}
}
