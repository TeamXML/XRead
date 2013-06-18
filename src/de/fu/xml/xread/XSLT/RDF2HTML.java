import java.io.*;
import java.net.URISyntaxException;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class RDF2HTML {

    public static void main(String[] args) throws IOException, URISyntaxException, TransformerException, TransformerConfigurationException {
    	
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();

            /*
             * /Users/j1/workspace/RDF2HTML/src/in.txt
             */
            
            Source xslt = new StreamSource("src/test.xsl");
            Source xml = new StreamSource("src/test.xml");

            String outputFile = "src/test.html";
            OutputStream htmlFile = new FileOutputStream(outputFile);

            Transformer transformer = tFactory.newTransformer(xslt);
            transformer.transform(xml, new StreamResult(htmlFile));
             
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
	}
}