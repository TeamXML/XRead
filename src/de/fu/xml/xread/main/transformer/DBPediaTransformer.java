package de.fu.xml.xread.main.transformer;

import javax.xml.transform.stream.StreamSource;

import android.annotation.SuppressLint;
import android.content.Context;
import de.fu.xml.xread.R;

/**
 * @author Nemo Nessuno
 *
 */

@SuppressLint("NewApi")
public class DBPediaTransformer {

	private static Context context;

	/**
	 * Konstruktor mit uebergebenem ApplicationContext, damit ein Zugriff auf die Ressourcen
	 * in raw erfolgen kann.
	 * 
	 * @param context - Context
	 */
	public DBPediaTransformer(Context context) {
		DBPediaTransformer.context = context;
	}

	/**
	 * Greift auf die Ressource geodata_template (XSLT-Datei zur Umwandlung
	 * von DBPedia) zu und erzeugt einen StreamSource daraus.
	 * 
	 * @return - StreamSource
	 */
	public StreamSource GetTemplate() {
		return new StreamSource(context.getResources().openRawResource(R.raw.dbpedia_template));
	}
}