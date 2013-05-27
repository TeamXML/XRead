package de.fu.xml.xread;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "historyVerlauf2.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_CREATE = "create table VERLAUF("+
			"URI String)";
			
	public MySQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + 
				newVersion + ". Old data will be destroyed");
		db.execSQL("DROP TABLE IF EXISTS SCANITEM");
		onCreate(db);
	}
}

	