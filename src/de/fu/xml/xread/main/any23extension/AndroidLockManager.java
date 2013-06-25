package de.fu.xml.xread.main.any23extension;

import java.io.File;

import org.openrdf.sail.helpers.DirectoryLockManager;

import android.content.Context;

	/**
	 * 
	 * LockManager using Android context to enable locking of files
	 * @author NemoNessuno
	 *
	 */

	public class AndroidLockManager extends DirectoryLockManager{
		
		private Context _context;
		
		public AndroidLockManager(File dir, Context context) {
			super(dir);
			_context = context;
		}

		@Override
		protected String getProcessName() {
			return _context.getApplicationInfo().processName;
		}
}
