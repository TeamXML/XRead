package de.fu.xml.xread.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.transform.stream.StreamSource;

import android.content.Context;
import de.fu.xml.xread.helper.HTTPReader;
import de.fu.xml.xread.helper.WebHelper;
import de.fu.xml.xread.main.transformer.DBPediaTransformer;
import de.fu.xml.xread.main.transformer.DefaultTransformer;
import de.fu.xml.xread.main.transformer.GeoDataTransformer;
import de.fu.xml.xread.main.transformer.TemplateType;
import de.fu.xml.xread.main.transformer.TwitterTransformer;
import de.fu.xml.xread.main.transformer.XSLTTransformer;

public class Transformer {

	private GeoDataTransformer _geoDataTransformer;
	private DefaultTransformer _defaultTransFormer;
	private DBPediaTransformer _dbPediaTransformer;
	private TwitterTransformer _twitterTransformer;
	
	public Transformer(Context context) {
		_geoDataTransformer = new GeoDataTransformer(context);
		_defaultTransFormer = new DefaultTransformer(context);
		_dbPediaTransformer = new DBPediaTransformer(context);
		_twitterTransformer = new TwitterTransformer(context);
	}

	public String transformData(String uri) throws IOException{
		String result = null;
		
		if (!WebHelper.isTwitter()){
		HTTPReader reader = new HTTPReader(uri, TemplateType.DEFAULT);
		switch (reader.getType()) {
		case GEO:
			result = transformGeoData(new StreamSource(reader.getRDFData()));
			break;
		case STACKOVERFLOW:
			reader.getRDFData();
			result = reader.getRawHTMLData();
			break;
		case DBPEDIA:
			result = transFormDBPedia(new StreamSource(reader.getRDFData()));
			break;
		case DEFAULT:
			result = transFormDefault(reader.getRDFData());
			break;
		case HTML:
			result = reader.getRawHTMLData();
			break;
		default:
			result = transFormDefault(reader.getRDFData());
			break;
		}
		} else {
			HTTPReader reader = new HTTPReader(uri, TemplateType.TWITTER);
			result = transformTwitter(reader.getRDFData());
		}
		return result;
	}

	private String transformTwitter(InputStream rdfData) throws IOException {
		String transform = XSLTTransformer.transform(new StreamSource(rdfData),_twitterTransformer.GetTemplate());
		return transform;
	}

	private String transFormDBPedia(StreamSource data) throws UnsupportedEncodingException, IOException {
		String transform = XSLTTransformer.transform(data,_dbPediaTransformer.GetTemplate());
		return transform;
	}

	private String transformGeoData(StreamSource data) throws IOException {		
		String transform = XSLTTransformer.transform(data,_geoDataTransformer.GetTemplate());
		return transform;
	}

	private String transFormDefault(InputStream data) throws IOException {
		ArrayList<String> items = new ArrayList<String>();
		
		// Detect & store ITEMS from the XML file
		items = _defaultTransFormer.DetectXML(data);

		// Update XSLT & transform from XML to HTML
		return XSLTTransformer.transform(new StreamSource(data),_defaultTransFormer.GetTemplate(items));
		// return _defaultTransFormer.UpdateHTML(XSLTTransformer.transform(new
		// StreamSource(data), _defaultTransFormer.GetTemplate(items)));

	}
}
