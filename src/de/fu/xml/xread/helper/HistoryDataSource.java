package de.fu.xml.xread.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class HistoryDataSource {
	
	private SQLiteDatabase db;
	private MySQLHelper dbHelper;
	private String[] spalten = {"DATUM", "ZEIT", "URL"};
	
	public HistoryDataSource(Context context){
		dbHelper = new MySQLHelper(context);
	}
	
	public void open() throws SQLException{
		db = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		db.close();
	}
	
	public Entry createEntry(String date, String time, String url){
		ContentValues values = new ContentValues();
		values.put("DATUM", date.toString());
		values.put("ZEIT", time.toString());
		values.put("URL", url);
		
		long insertID = db.insert("History", null, values);
		
		Cursor cursor = db.query("History", spalten, "ID = "+insertID, null, null, null, null);
//		cursor.moveToFirst();
		cursor.moveToLast();
		return cursorToEntry(cursor);
	}

	private Entry cursorToEntry(Cursor cursor) {
		Entry entry = new Entry();
		entry.setDate(cursor.getString(0));
		entry.setTime(cursor.getString(1));
		entry.setUrl(cursor.getString(2));
		return entry;
		
	}
	
	public List<Entry> getAllEntries(){
		List<Entry> list = new ArrayList<Entry>();
		list = new ArrayList<Entry>();
		Cursor cursor = db.query("History", spalten, null, null, null, null, null);
//		cursor.moveToFirst();
		cursor.moveToLast();
		if(cursor.getCount() == 0) return list;
		
//		while(!cursor.isAfterLast()){ 
//			Entry entry = cursorToEntry(cursor);
//			list.add(entry);
//			cursor.moveToNext();
//		}
		
		while(!cursor.isBeforeFirst()){
			Entry entry = cursorToEntry(cursor);
			list.add(entry);
			cursor.moveToPrevious();
		}
		
		cursor.close();
		
		return list;
	}
}
