import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * 
 * This class detects namespaces from XML file 
 * and modifies XSLT file by adding namespaces.
 * 
 * Output : a new XSLT file
 * 
 * @author Jaehwan Ji
 *
 */

public class ItemDetector {

	public static void main(String[] args) {
		
		FileReader readFile; 
		BufferedReader br; 
		
		FileWriter writeFile;
		   
//		ArrayList<String> listList = new ArrayList<String>();
		ArrayList<String> itemList = new ArrayList<String>();
		
		String getLine = "";
		String data = "";
		String capital = "";

		boolean writable = false;
		boolean alreadyWritten = false;

		
		// Detect & Store items
		try { 
			
            // TODO : Does it work with this path?
			
			readFile = new FileReader("src/xml.xml"); 
			br = new BufferedReader(readFile);
			
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
					data = getLine.substring( (getLine.indexOf("<") +1), getLine.indexOf(">") );
					itemList.add(data);
				}
			}
			
				
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		
		
		writable = false;
		alreadyWritten = false;

		
		// Modify XSLT file
		try {
		
			readFile = new FileReader("src/xslTemp.xsl"); 
			br = new BufferedReader(readFile);
			
			writeFile = new FileWriter("src/xsl.xsl");
	

			while ( (getLine = br.readLine()) != null) { 
				
				if (getLine.contains("<tr bgcolor")) {
					data = getLine + "\n";
					
					// add namespaces for the first row
					for (int i = 0; i < itemList.size(); i++) {
						capital = itemList.get(i).substring(0, 1).toUpperCase() + itemList.get(i).substring(1);		// capitalize the first letter
						data += "<th>" + capital + "</th>\n";
					}
				}

				else if (getLine.contains("<tr>")) {
					data = getLine + "\n";
					
					// add namespaces for the contents
					for (int i = 0; i < itemList.size(); i++)
						data += "<td><xsl:value-of select=\"rss:" + itemList.get(i) + "\" /><br /></td>\n";
				}

				else
					data = getLine + "\n";
				
				writeFile.write(data);
			}
			
			writeFile.flush();
			writeFile.close();
			
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		
				
	}
}
