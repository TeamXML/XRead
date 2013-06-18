package de.fu.xml.xread;

import java.util.Calendar;

public class ButtonMethods{
	 
	static String uri;
	static boolean mainIsOpen;
	static boolean webIsOpen;
	
	public static boolean getMainIsOpen() {
		return mainIsOpen;
	}

	public static void setMainIsOpen(boolean mainIsOpen) {
		ButtonMethods.mainIsOpen = mainIsOpen;
	}

	public static boolean getWebIsOpen() {
		return webIsOpen;
	}

	public static void setWebIsOpen(boolean webIsOpen) {
		ButtonMethods.webIsOpen = webIsOpen;
	}

	public static String getUri() {
		return uri;
	}

	public static void setUri(String uri) {
		ButtonMethods.uri = uri;
	}
	
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
