/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.application;

import org.apache.wicket.util.crypt.ICrypt;

public interface IWickletCrypt extends ICrypt {
	/**
	 * Preliminary check to see if parameter is encrypted page component info.
	 * @return true if input may be an encrypted page component info, false if input is definitely not.
	 */
	boolean isEncrypt(String s);
}
