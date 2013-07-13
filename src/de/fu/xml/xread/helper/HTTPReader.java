package de.fu.xml.xread.helper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import de.fu.xml.xread.main.transformer.TemplateType;

public class HTTPReader {

	private static final int BUFFER_SIZE = 1024;

	private TemplateType _type;
	private String _url;
	private String _rawHTML;

	public HTTPReader(String url, TemplateType type)
			throws MalformedURLException, IOException, URISyntaxException {
		_url = new URI(url).normalize().toString();
		if (!(type == TemplateType.TWITTER)) {
			URLConnection openConnection = new URL(_url).openConnection();
			_type = WebHelper.decideContentType(_url,
					openConnection.getContentType());
			_rawHTML = streamToString(openConnection.getInputStream());
		}
	}

	public TemplateType getType() {
		return _type;
	}

	public String getRawHTMLData() throws MalformedURLException, IOException {
		return _rawHTML;
	}

	public InputStream getRDFData() throws MalformedURLException, IOException {
		return new URL(WebHelper.getAny23URI(_url)).openConnection()
				.getInputStream();
	}

	private String streamToString(InputStream inputStream)
			throws UnsupportedEncodingException, IOException {
		return copyToOutputStream(inputStream).toString("UTF-8");
	}

	private ByteArrayOutputStream copyToOutputStream(InputStream input)
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