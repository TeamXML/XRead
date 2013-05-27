package de.fu.xml.xread;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class VerlaufDataSource {

	private SQLiteDatabase db;
	private MySQLHelper dbHelper;
	private String[] spalten = {"URI"};
	
	public VerlaufDataSource(Context context){
		dbHelper = new MySQLHelper(context);
	}
	
	public void open() throws SQLException{
		db = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		db.close();
	}
	
	public History createEntry(String uri){
		ContentValues values = new ContentValues();
		values.put("URI", uri);
		
		long insertID = db.insert("VERLAUF", null, values);
		
		Cursor cursor = db.query("VERLAUF", spalten, "ID = "+insertID, null, null, null, null);
//		cursor.moveToFirst();
		cursor.moveToLast();
		return cursorToEntry(cursor);
	}

	private History cursorToEntry(Cursor cursor) {
		History entry = new History();
		String uri = new String("");
		entry.setUri(uri);
		
		return entry;
		
	}
	
	public List<History> getAllEntries(){
		List<History> list = new ArrayList<History>();
		list = new ArrayList<History>();
		Cursor cursor = db.query("VERLAUF", spalten, null, null, null, null, null);
//		cursor.moveToFirst();
		cursor.moveToLast();
		if(cursor.getCount() == 0) return list;
		
//		while(!cursor.isAfterLast()){ 
//			Entry entry = cursorToEntry(cursor);
//			list.add(entry);
//			cursor.moveToNext();
//		}
		
		while(!cursor.isBeforeFirst()){
			History entry = cursorToEntry(cursor);
			list.add(entry);
			cursor.moveToPrevious();
		}
		
		cursor.close();
		
		return list;
	}
}
