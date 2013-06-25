import java.io.File;
import javax.xml.transform.TransformerException;

public class TestXsl {
     public static void main(String[] args) throws TransformerException {
         final File xmlFile = new File("src/test.xml");
         final File xsltFile = new File("src/test.xsl");
         System.out.println(XmlTransform.getTransformedHtml(xmlFile, xsltFile));
     }
}