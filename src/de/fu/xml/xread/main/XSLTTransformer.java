package de.fu.xml.xread.main;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import android.annotation.SuppressLint;

/**
 * @author Monia
 *
 */

@SuppressLint("NewApi")
public class XSLTTransformer {
	
	/**
	 * Transformit XML durch XSLT zu HTML. Das Ergebnis wird als ein String zurück gegeben. 
	 * 
	 * @param xml - StreamSource
	 * @param xsl - StreamSource
	 * @return - String
	 * @throws IOException
	 */
	public static String transform(StreamSource xml, StreamSource xsl) throws IOException {
		
		String htmlString = null;

		//Writer um aus dem StreamResult einen String zu erstellen
		Writer outWriter = new StringWriter();
		StreamResult result = new StreamResult(outWriter);
		
		try {

			TransformerFactory tFactory = TransformerFactory.newInstance();

			Transformer transformer = tFactory.newTransformer(xsl);

			transformer.transform(xml, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Umwandlung des StreamResults in einen String
		outWriter.flush();

		StringWriter sw = (StringWriter) result.getWriter();
		StringBuffer sb = sw.getBuffer();
		htmlString = sb.toString();
		
		return htmlString;
	}
}