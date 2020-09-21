package com.inlacou.fivedaysapp.contacts;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

import timber.log.Timber;

public class ContactsCtrl {
	private Long start = System.currentTimeMillis();
	private ArrayList<Result> response = new ArrayList<>();
	
	public void work(Activity activity) {
		start = System.currentTimeMillis();
		
		Cursor cur = activity.getContentResolver().query(
				ContactsContract.Data.CONTENT_URI,
				new String[]{
						ContactsContract.Data.DATA1,
						ContactsContract.Contacts.DISPLAY_NAME,
						ContactsContract.Contacts.PHOTO_URI,
						ContactsContract.Data.MIMETYPE },
				null, null, null);
		
		if(cur==null) return;
		
		cur.moveToFirst();
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				if(cur.getString(cur.getColumnIndex(ContactsContract.Data.MIMETYPE)).equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)){
					String phoneNumber = cur.getString(cur.getColumnIndex(ContactsContract.Data.DATA1));
					String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					String photoUri = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
					if (!phoneNumber.equals("")) {
						// Remove all whitespaces
						addNumber(phoneNumber, name, photoUri==null ? "" : photoUri);
					}
				}
			}
		}
		
		cur.close();
		
		Timber.d("phone retrieval | time spent: " + (System.currentTimeMillis()-start) + "ms");
	}
	
	private void addNumber(String phoneNo, String name, String photoUri) {
		String phone = phoneNo.replace("\\s+", "");
		Timber.d("adding phone: " + phone + " with name: " + name);
		response.add(new Result(phone, name, photoUri));
	}
	
	static class Result {
		String phoneNumber;
		String name;
		String photoUri;
		
		public Result(String name, String phoneNumber, String photoUri) {
			this.phoneNumber = phoneNumber;
			this.name = name;
			this.photoUri = photoUri;
		}
	}
}
