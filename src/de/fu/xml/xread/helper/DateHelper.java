package de.fu.xml.xread.helper;

import java.util.Calendar;

public class DateHelper{
	 
	public static String getDate() {
    	//Zeitstempel
    	Calendar cal = Calendar.getInstance();
    	int tag = cal.get(Calendar.DAY_OF_MONTH);
		int monat = cal.get(Calendar.MONTH);
		int jahr = cal.get(Calendar.YEAR);
		String date = tag+"."+monat+"."+jahr;
		return date;
	}
   
    public static String getTime(){
    	Calendar cal = Calendar.getInstance();
    	int stunde = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		String time = stunde+":"+minute+" Uhr";
		return time;
    }
	
}
