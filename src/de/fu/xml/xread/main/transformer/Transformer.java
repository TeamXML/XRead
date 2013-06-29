package de.fu.xml.xread.main.transformer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.transform.stream.StreamSource;

import android.content.Context;
import de.fu.xml.xread.helper.WebHelper;
import de.fu.xml.xread.main.XSLTTransformer;

public class Transformer {

	private static final int BUFFER_SIZE = 1024;
	private GeoDataTransformer _geoDataTransformer;
	private DefaultTransformer _defaultTransFormer;

	public Transformer(Context context) {
		_geoDataTransformer = new GeoDataTransformer(context);
		_defaultTransFormer = new DefaultTransformer(context);
	}

	public String transformData(String uri, InputStream data) throws IOException{
		String result = null;
		switch (WebHelper.DecideTemplate(uri)) {
		case DEFAULT:
			result = transFormDefault(data);
			break;
		case GEO:
			result = transformGeoData(new StreamSource(data));
			break;
		case STACKOVERFLOW:
			result = transFormDefault(data);
			break;
		default:
			result = copyToOutputStream(data).toString("UTF-8");
			break;
		}
		
		return result;
	}

	private String transformGeoData(StreamSource data) throws IOException {
		return XSLTTransformer.transform(data,_geoDataTransformer.GetTemplate());
	}

	private String transFormDefault(InputStream data) throws IOException {
		ArrayList<String> items = new ArrayList<String>();

		// Detect & store ITEMS from the XML file
		items = _defaultTransFormer.DetectXML(data);

		// Update XSLT & transform from XML to HTML
		return XSLTTransformer.transform(new StreamSource(data),
				_defaultTransFormer.GetTemplate(items));
		// return _defaultTransFormer.UpdateHTML(XSLTTransformer.transform(new
		// StreamSource(data), _defaultTransFormer.GetTemplate(items)));

	}

	private static ByteArrayOutputStream copyToOutputStream(InputStream input)
			throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];

		BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		int n = 0;
		try {
			while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
				out.write(buffer, 0, n);
			}
			out.flush();
		} finally {
			out.close();
			in.close();
		}
		return out;
	}
}
