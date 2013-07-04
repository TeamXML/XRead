package de.fu.xml.xread.helper;

import java.util.Locale;

import de.fu.xml.xread.main.transformer.TemplateType;

/**
 * This class provides helper methods to handle the calls to the websites we
 * support.
 * 
 * @author Nemo Nessuno
 */

public class WebHelper {
	
	protected static final String HTTP = "http://";
	private static String _uri;
	private static String _baseuri;

	// TODO: replace hard-coded IP (lol if ever)
	private static final String ANY23_PREFIX = "http://10.0.2.2:8080/apache-any23-service/history/";

	// Website top-level domain strings
	private static final String STACK_OVERFLOW = "http://stackoverflow.com";
	private static final String DBPEDIA = "http://dbpedia.org";
	private static final String GOOGLE_MAPS = "http://maps.googleapis.com";

	// Website API calls
	// Stackoverflow
	private static final String STACK_OVERFLOW_QUESTION = STACK_OVERFLOW
			+ "/search?q=";

	// Googlemaps
	private static final String GOOGLE_MAPS_ADRESS_SEARCH = GOOGLE_MAPS
			+ "/maps/api/geocode/xml?address=";
	private static final String GOOGLE_MAPS_SUFFIX = "&sensor=false";
	
	//DBPedia
	private static final String DBPEDIA_RESOURCE = DBPEDIA+"/resource/";

	public static String getMapsAdressSearch(String searchString) {
		return GOOGLE_MAPS_ADRESS_SEARCH + searchString.replaceAll("\\s", "+") + GOOGLE_MAPS_SUFFIX;
	}

	public static String getStackOverflowSearch(String searchString) {
		return STACK_OVERFLOW_QUESTION + searchString;
	}
	
	public static String getDBPediaSearch(String string) {
		String replaceAll = string.replaceAll("\\s", "_");
		return DBPEDIA_RESOURCE + replaceAll;
	}

	public static String getAny23URI(String url) {
		return ANY23_PREFIX + url;
	}

	public static TemplateType DecideTemplate(String url, String mimeType) {
		TemplateType result = TemplateType.DEFAULT;

		if (urlStartsWith(url, GOOGLE_MAPS)) {
			result = TemplateType.GEO;
		} else if (urlStartsWith(url, STACK_OVERFLOW)) {
			result = TemplateType.STACKOVERFLOW;
		} else if (urlStartsWith(url, DBPEDIA)){
			result = TemplateType.DBPEDIA;
		} else if (mimeType.toLowerCase(Locale.US).contains("html")){
			result = TemplateType.HTML;
		}

		return result;
	}
	
	public static String getUri() {
		return _uri;
	}

	/**
	 * Normalizes the given URI
	 * If the URI contains a base URI it is set
	 * if not the given URI is extended
	 * @param uri
	 */
	public static void setUri(String uri) {
		if (uri.startsWith("/")){
			_uri = _baseuri + uri;
		} else {
			if (!uri.startsWith(HTTP)){
				uri = HTTP + uri;
			}
			_baseuri = HTTP + extractBaseURI(uri);
			_uri = uri;
		}		
	}

	protected static String extractBaseURI(String uri) {
		String uriwoHTTP = uri.substring(HTTP.length(), uri.length());
		int indexOfSlash = uriwoHTTP.indexOf("/");
		return uriwoHTTP.substring(0, indexOfSlash > 0 ? indexOfSlash : uriwoHTTP.length());
	}
	
	private static boolean urlStartsWith(String url, String comparedWith) {
		return url.toLowerCase(Locale.US).startsWith(
				comparedWith.toLowerCase(Locale.US));
	}

}
