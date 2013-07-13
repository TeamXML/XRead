package de.fu.xml.xread.main.transformer;

import javax.xml.transform.stream.StreamSource;

import android.content.Context;
import de.fu.xml.xread.R;

/**
 * @author NemoNessuno
 *
 */

public class TwitterTransformer {

	private static Context context;

	/**
	 * Konstruktor mit uebergebenem ApplicationContext, damit ein Zugriff auf die Ressourcen
	 * in raw erfolgen kann.
	 * 
	 * @param context - Context
	 */
	public TwitterTransformer(Context context) {
		TwitterTransformer.context = context;
	}

	/**
	 * Greift auf die Ressource geodata_template (XSLT-Datei zur Umwandlung
	 * von Geodaten) zu und erzeugt einen StreamSource daraus.
	 * 
	 * @return - StreamSource
	 */
	public StreamSource GetTemplate() {
			
		return new StreamSource(context.getResources().openRawResource(R.raw.twitter_template));
		
	}
}