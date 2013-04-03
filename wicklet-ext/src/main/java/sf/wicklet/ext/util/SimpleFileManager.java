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
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import sf.blacksun.util.FileUtil;

/**
 * All read/write to the writable web site files must goes through an IFileManager to make sure
 * concurrent read/write are serialized.
 */
public class SimpleFileManager implements IFileManager {

	private static final long serialVersionUID = 1L;
	private final ReadWriteLock rwlock = new ReentrantReadWriteLock(true);

	public SimpleFileManager() {
	}

	@Override
	public String read(final File file) throws IOException {
		rwlock.readLock().lock();
		try {
			return FileUtil.asString(file);
		} finally {
			rwlock.readLock().unlock();
	}}

	@Override
	public void write(final File file, final boolean append, final String content) throws IOException {
		rwlock.writeLock().lock();
		try {
			FileUtil.writeFile(file, append, content);
		} finally {
			rwlock.writeLock().unlock();
	}}
}
