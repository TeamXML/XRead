package de.fu.xml.xread.main.transformer;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.stream.StreamSource;

import de.fu.xml.xread.main.XSLTTransformer;
import android.content.Context;
import android.util.Log;

public class Transformer {
	
	private GeoDataTransformer _geoDataTransformer;
	private DefaultTransformer _defaultTransFormer;
	
	public Transformer(Context context) {
		_geoDataTransformer = new GeoDataTransformer(context);
		_defaultTransFormer = new DefaultTransformer(context);
	}
	
	public String transformGeoData(StreamSource data) throws IOException{
		return XSLTTransformer.transform(data, _geoDataTransformer.GetTemplate());
	}
	
	public String transFormDefault(InputStream data) throws IOException{		
		_defaultTransFormer.UpdateXSL(data);
		return XSLTTransformer.transform(new StreamSource(data), _defaultTransFormer.GetTemplate());
	}
	
}
