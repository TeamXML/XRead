package de.fu.xml.xread.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.transform.stream.StreamSource;

import android.content.Context;
import android.util.Log;
import de.fu.xml.xread.helper.HTTPReader;
import de.fu.xml.xread.main.transformer.DBPediaTransformer;
import de.fu.xml.xread.main.transformer.DefaultTransformer;
import de.fu.xml.xread.main.transformer.GeoDataTransformer;
import de.fu.xml.xread.main.transformer.XSLTTransformer;

public class Transformer {

	private GeoDataTransformer _geoDataTransformer;
	private DefaultTransformer _defaultTransFormer;
	private DBPediaTransformer _dbPediaTransformer;

	public Transformer(Context context) {
		_geoDataTransformer = new GeoDataTransformer(context);
		_defaultTransFormer = new DefaultTransformer(context);
		_dbPediaTransformer = new DBPediaTransformer(context);
	}

	public String transformData(String uri) throws IOException{
		String result = null;
		HTTPReader reader = new HTTPReader(uri);
		switch (reader.getType()) {
		case GEO:
			result = transformGeoData(new StreamSource(reader.getRDFData()));
			break;
		case STACKOVERFLOW:
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
		return result;
	}

	private String transFormDBPedia(StreamSource data) throws UnsupportedEncodingException, IOException {
		String transform = XSLTTransformer.transform(data,_dbPediaTransformer.GetTemplate());
		Log.i("Transformer", transform);
		return transform;
	}

	private String transformGeoData(StreamSource data) throws IOException {
		return XSLTTransformer.transform(data,_geoDataTransformer.GetTemplate());
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
