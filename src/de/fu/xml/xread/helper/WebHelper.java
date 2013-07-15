package de.fu.xml.xread.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import de.fu.xml.xread.main.transformer.TemplateType;

/**
 * This class provides helper methods to handle the calls to the websites we
 * support.
 * 
 * @author Nemo Nessuno
 */

public class WebHelper {
	
	private static String SERVER_ADRESS = "10.0.2.2:8080";
	protected static final String HTTP = "http://";
	private static String _uri;
	private static String _baseuri;

	// TODO: replace hard-coded IP (lol if ever)
	private static String ANY23_PREFIX = "http://" + SERVER_ADRESS+"/apache-any23-service/xread/";

	// Website top-level domain strings
	private static final String STACK_OVERFLOW = "stackoverflow.com";
	private static final String DBPEDIA = "dbpedia.org";
	private static final String GOOGLE_MAPS = "maps.googleapis.com";
	private static final String TWITTER = "https://api.twitter.com";

	// Website API calls
	// Stackoverflow
	private static final String STACK_OVERFLOW_QUESTION = HTTP + STACK_OVERFLOW
			+ "/search?q=";

	// Googlemaps
	private static final String GOOGLE_MAPS_ADRESS_SEARCH = HTTP + GOOGLE_MAPS
			+ "/maps/api/geocode/xml?address=";
	private static final String GOOGLE_MAPS_SUFFIX = "&sensor=false";

	// DBPedia
	private static final String DBPEDIA_RESOURCE = HTTP + DBPEDIA
			+ "/resource/";
	private static final String QUERY = "query/";
	
	public static void SetServerAdress(String serveradress){
		SERVER_ADRESS = serveradress;
	}
	
	public static String getMapsAdressSearch(String searchString) {
		return GOOGLE_MAPS_ADRESS_SEARCH + searchString.replaceAll("\\s", "+")
				+ GOOGLE_MAPS_SUFFIX;
	}

	public static String getStackOverflowSearch(String searchString) {
		return STACK_OVERFLOW_QUESTION + searchString;
	}

	public static String getDBPediaSearch(String string) {
		String replaceAll = string.replaceAll("\\s", "_");
		return DBPEDIA_RESOURCE + replaceAll;
	}

	public static String getTwitterTimeLine(String string) {
		return TWITTER + "?name=" + string;
	}

	public static String getSPARQLSearch(String string)
			throws UnsupportedEncodingException {
		return ANY23_PREFIX + QUERY + URLEncoder.encode(string, "UTF-8");
	}

	public static String getAny23URI(String url) {
		String prefix = url.startsWith(ANY23_PREFIX) ? "" : ANY23_PREFIX;
		return prefix + url;
	}

	public static TemplateType decideContentType(String url, String mimeType) {
		TemplateType result = TemplateType.DEFAULT;
		
		if (urlContains(url, ANY23_PREFIX + QUERY)) {
			result = TemplateType.SPARQL;
		} else if (urlContains(url, GOOGLE_MAPS)) {
			result = TemplateType.GEO;
		} else if (urlContains(url, STACK_OVERFLOW)) {
			result = TemplateType.STACKOVERFLOW;
		} else if (urlContains(url, DBPEDIA)) {
			result = TemplateType.DBPEDIA;
		} else if (urlContains(url, TWITTER)) {
			result = TemplateType.TWITTER;
		} else if (mimeType.toLowerCase(Locale.US).contains("html")) {
			result = TemplateType.HTML;
		}

		return result;
	}

	public static String getUri() {
		return _uri;
	}

	/**
	 * Normalizes the given URI If the URI contains a base URI it is set if not
	 * the given URI is extended
	 * 
	 * @param uri
	 */
	public static void setUri(String uri) {
		if (uri.startsWith("/")) {
			_uri = _baseuri + uri;
		} else {
			if (!uri.startsWith(HTTP) && !uri.startsWith("https://")) {
				uri = HTTP + uri;
			}
			_baseuri = HTTP + extractBaseURI(uri);
			_uri = uri;
		}
	}

	protected static String extractBaseURI(String uri) {
		String uriwoHTTP = uri.substring(HTTP.length(), uri.length());
		int indexOfSlash = uriwoHTTP.indexOf("/");
		return uriwoHTTP.substring(0, indexOfSlash > 0 ? indexOfSlash
				: uriwoHTTP.length());
	}

	private static boolean urlContains(String url, String comparedWith) {
		return url.toLowerCase(Locale.US).contains(
				comparedWith.toLowerCase(Locale.US));
	}

	public static boolean isTwitter() {
		return urlContains(_uri, TWITTER);
	}
}
