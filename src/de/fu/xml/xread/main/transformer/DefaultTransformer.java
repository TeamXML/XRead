package de.fu.xml.xread.main.transformer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import javax.xml.transform.stream.StreamSource;

import android.content.Context;
import de.fu.xml.xread.R;

public class DefaultTransformer {

	Context _context;
	String _xsl;
//	private String updatedXSLT;
	
	
	public DefaultTransformer(Context context) {
		_context = context;
	}
	

	/**
	 * Detect & store ITEMS from RDF file
	 * 
 	 * @param items
	 * @return stored items in a array list
	 * @throws IOException
	 * 
	 * author: Jaehwan Ji
	 */
	public ArrayList<String> DetectXML(InputStream xml) {
		InputStreamReader _xml = new InputStreamReader(xml);
		BufferedReader br = new BufferedReader(_xml);

/*
		InputStream default_xsl = _context.getResources().openRawResource(
															R.raw.default_xsl);
		InputStreamReader _default_xsl = new InputStreamReader(default_xsl);
		BufferedReader br2 = new BufferedReader(_default_xsl);

		StringBuilder sb = new StringBuilder();
*/

		ArrayList<String> itemList = new ArrayList<String>();
		String getLine = "";
		String data = "";

		boolean writable = false;
		boolean alreadyWritten = false;

		
		try { 			
			while ( (getLine = br.readLine()) != null) { 
				
				if (alreadyWritten == false && getLine.contains("<item>")) {
					writable = true;
					continue;		// <item> itself will be ignored.
				}

				if (getLine.contains("</item>")) {
					alreadyWritten = true;
					writable = false;
				}
				
				if (alreadyWritten == false && writable == true) {
					data = getLine.substring( (getLine.indexOf("<") +1), 
														getLine.indexOf(">") );
					itemList.add(data);
				}
			}
			
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
		
		return itemList; 
	}
	

	/**
	 * This method does NOT modify the source XML file.
	 * But XSLT file.
	 * 
	 * @param items
	 * @return updated XSLT as StreamSource
	 * @throws IOException
	 * 
	 * author : Jaehwan Ji
	 */
	public StreamSource GetTemplate(ArrayList<String> items) throws IOException{
		ArrayList<String> itemList = items;

		InputStreamReader xslt = new InputStreamReader(
					_context.getResources().openRawResource(R.raw.default_xsl));
		BufferedReader br2 = new BufferedReader(xslt);

		StringBuilder sb = new StringBuilder();
		String getLine = "";
		String str = "";
		String capital = "";

		// Modify(update) XSLT file by adding items
		while ( (getLine = br2.readLine()) != null) { 
			
			if (getLine.contains("<tr bgcolor")) {
				sb.append(getLine + "\n");
				
				// add namespaces for the first row
				for (int i = 0; i < itemList.size(); i++) {
					capital = itemList.get(i).substring(0, 1).toUpperCase(Locale.US) + itemList.get(i).substring(1);		// capitalize the first letter
					
					sb.append("<th>" + capital + "</th>\n");
				}
			}

			else if (getLine.contains("<tr>")) {
				sb.append(getLine + "\n");
				
				// add namespaces for the contents
				for (int i = 0; i < itemList.size(); i++) {
					sb.append("<td><xsl:value-of select=\"rss:" + 
										itemList.get(i) + "\" /><br /></td>\n");
				}
			}

			else {
				sb.append(getLine + "\n");
			}
			
			str = sb.toString();
		}

		InputStream updated_xslt_ = new ByteArrayInputStream(str.getBytes());
		StreamSource updated_xslt = new StreamSource(updated_xslt_);
				
		return updated_xslt;
	}
	
	// Add background image on the HTML file.
	public String UpdateHTML(String str) {
		String updatedHTML = str;
		
		if (str.contains("<Author>") || str.contains("<author>")) {
//			"<body background=\"res/raw/book.jpg\"></body>\n".concat(updatedHTML);
			"<div style = \"background-image: url(res/raw/book.jpg); background-repeat: no-repeat; height:100%; width:100%; \">".concat(updatedHTML);
			updatedHTML.concat("\n</div>");

			
		}
		else if (str.contains("<Artist>") || str.contains("<artist>")) {
//			"<body background=\"res/raw/cd.jpg\"></body>\n".concat(updatedHTML);
			"<div style = \"background-image: url(res/raw/cd.jpg); background-repeat: no-repeat; height:100%; width:100%; \">".concat(updatedHTML);
			updatedHTML.concat("\n</div>");
			
		}
		
		return updatedHTML;
	}
}