/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.gwt.site.server.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.string.StringValue;
import sf.blacksun.util.FileUtil;
import sf.wicklet.gwt.site.server.Config;

public class CommentInfo {

	private static long counter = 0;
	private final String user;
	private final String subject;
	private final String content;

	public CommentInfo(final String user, final StringValue subject, final StringValue content) {
		this.user = user;
		this.subject = subject.toString();
		this.content = content.toString();
	}

	public boolean persist() {
		final String fname = String.format(
			"WEB-INF/incoming/feedback/%1$tY%1$tm%1$td-%2$04d.txt", System.currentTimeMillis(), ++counter);
		final File file = new File(WebApplication.get().getServletContext().getRealPath(fname));
		if (!file.getParentFile().mkdirs()) {
			Config.get().error("ERROR: Creating comment directory: " + file.getParent());
		}
		PrintWriter out = null;
		try {
			out = new PrintWriter(file);
			out.println("User: " + user);
			out.println("Subject: " + subject);
			out.println();
			out.println(content);
		} catch (final FileNotFoundException e) {
			Config.get().error("ERROR: Writing " + file, e);
			return false;
		} finally {
			FileUtil.close(out);
		}
		return true;
	}
}
