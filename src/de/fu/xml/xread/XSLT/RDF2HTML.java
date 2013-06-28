package de.fu.xml.xread.XSLT;

import java.io.*;
import java.net.URISyntaxException;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * This class 'RDF2HTML' transforms RDF into HTML
 * and erases redundant text in HTML file. 
 * 
 * Output : a new HTML file
 * 
 * @author Jaehwan Ji
 *
 */


public class RDF2HTML {
	
    public static void main(String[] args) throws IOException, URISyntaxException, TransformerException, TransformerConfigurationException {

    	FileReader readFile; 
    	BufferedReader br; 
    	
    	FileWriter writeFile;


    	// Transform from RDF to HTML
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();

            /*
             * /Users/j1/workspace/RDF2HTML/src/in.txt
             */
            
            // TODO : Does it work with this path?
            
            Source xslt = new StreamSource("src/xsl.xsl");
            Source xml = new StreamSource("src/xml.xml");

            String outputFile = "src/htmlTemp.html";
            OutputStream htmlFile = new FileOutputStream(outputFile);

            Transformer transformer = tFactory.newTransformer(xslt);
            transformer.transform(xml, new StreamResult(htmlFile));
             
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        
        
		// Modify HTML file
        // Add text of <head>, <body>, etc.
		try {
		
            // TODO : Does it work with this path?

			readFile = new FileReader("src/htmlTemp.html"); 
			br = new BufferedReader(readFile);
			
			writeFile = new FileWriter("src/html.html");

			boolean writable = true;

			String getLine = "";
			
			// Write missing text
			String data = "<!DOCTYPE html>\n<html>\n<head><title></title></head>\n<body>\n";
			
			writeFile.write(data);


			// Delete redundant text
			while ( (getLine = br.readLine()) != null) { 

				// detect redundancy
				if (getLine.contains("</table>")) {
					writable = false;
				}

				if (getLine.contains("</table>") && writable == false) {
					data = getLine + "\n";
				}

				else if (getLine.contains("<tr>") && writable == false) {
					writable = true;
					data = getLine + "\n";
					writeFile.write(data);
				}

				// Write not-redundant-text
				else if (writable == true) {
					data = getLine + "\n";
					writeFile.write(data);
				}
			}

			// Write missing text
			data += "</body>\n</html>";
			
			writeFile.write(data);

			
			writeFile.flush();
			writeFile.close();
			
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
        
	}
}