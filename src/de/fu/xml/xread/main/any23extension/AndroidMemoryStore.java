package de.fu.xml.xread.main.any23extension;

import java.io.File;
import java.io.IOException;

import org.openrdf.sail.SailException;
import org.openrdf.sail.memory.MemoryStore;

import android.content.Context;

/**
 * Provides a way to initialize the openRDF MemoryStore to use the File IO
 * provided by Android.
 * 
 * @author NemoNessuno
 * 
 */

public class AndroidMemoryStore extends MemoryStore {

	public AndroidMemoryStore(Context context, String repositoryFile) {
		super(new File(context.getFilesDir() + File.separator + repositoryFile));
	}

	@Override
	protected void initializeInternal() throws SailException {
		logger.debug("Initializing Android MemoryStore...");

		resetCurrentSnapshot();

		if (persist) {
			File dataDir = getDataDir();
			dataFile = new File(dataDir, DATA_FILE_NAME);
			syncFile = new File(dataDir, SYNC_FILE_NAME);

			if (dataFile.exists()) {
				logger.debug("Reading data from {}...", dataFile);

				// Initialize persistent store from file
				if (!dataFile.canRead()) {
					logger.error("Data file is not readable: {}", dataFile);
					throw new SailException("Can't read data file: " + dataFile);
				}

				// Don't try to read empty files: this will result in an
				// IOException, and the file doesn't contain any data anyway.
				if (dataFile.length() == 0L) {
					logger.warn("Ignoring empty data file: {}", dataFile);
				} else {
					try {
						readFromDataFile();
						logger.debug("Data file read successfully");
					} catch (IOException e) {
						logger.error("Failed to read data file", e);
						throw new SailException(e);
					}
				}
			} else {
				// file specified that does not exist yet, create it
				try {
					File dir = dataFile.getParentFile();
					if (dir != null && !dir.exists()) {
						logger.debug("Creating directory for data file...");
						if (!dir.mkdirs()) {
							logger.debug(
									"Failed to create directory for data file: {}",
									dir);
							throw new SailException(
									"Failed to create directory for data file: "
											+ dir);
						}
					}
					logger.debug("Initializing data file...");
					syncWithFileIO();
					logger.debug("Data file initialized");
				} catch (IOException e) {
					logger.debug("Failed to initialize data file", e);
					throw new SailException("Failed to initialize data file "
							+ dataFile, e);
				} catch (SailException e) {
					logger.debug("Failed to initialize data file", e);
					throw new SailException("Failed to initialize data file "
							+ dataFile, e);
				}
			}
		}

		contentsChanged = false;

		logger.debug("Android MemoryStore initialized");
	}

}
