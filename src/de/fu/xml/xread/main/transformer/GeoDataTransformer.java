package de.fu.xml.xread.main.transformer;

import javax.xml.transform.stream.StreamSource;

import android.content.Context;
import de.fu.xml.xread.R;

/**
 * @author Monia
 *
 */

public class GeoDataTransformer {

	private static Context context;

	/**
	 * Konstruktor mit uebergebenem ApplicationContext, damit ein Zugriff auf die Ressourcen
	 * in raw erfolgen kann.
	 * 
	 * @param context - Context
	 */
	public GeoDataTransformer(Context context) {
		GeoDataTransformer.context = context;
	}

	/**
	 * Greift auf die Ressource geodata_template (XSLT-Datei zur Umwandlung
	 * von Geodaten) zu und erzeugt einen StreamSource daraus.
	 * 
	 * @return - StreamSource
	 */
	public StreamSource GetTemplate() {
			
		StreamSource xslt_geo = new StreamSource(context.getResources().openRawResource(R.raw.geodata_template));
		
		return xslt_geo;
		
	}
}