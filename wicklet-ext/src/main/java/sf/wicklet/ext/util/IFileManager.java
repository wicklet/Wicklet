/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public interface IFileManager extends Serializable {
	String read(File file) throws IOException;
	void write(File file, boolean append, String content) throws IOException;
}
