package de.fu.xml.xread.activities.sqlAndHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "history.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_CREATE = "create table HISTORY("+
			"ID integer primary key autoincrement, "+
			"DATUM text, "+
			"ZEIT text, "+
			"URL text)";
			
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
