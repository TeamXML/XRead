package de.fu.xml.xread.activities.sqlAndHelper;


public class Entry {
	
	private String date;
	private String time;
	private String url;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return  "\nLink: " + url+"\n"+
				"besucht am: " + date + "\n"+
				"um: " + time + "\n";
	}
	
	
}
