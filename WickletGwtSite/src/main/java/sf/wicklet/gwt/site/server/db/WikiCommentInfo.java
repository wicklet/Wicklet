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
import sf.blacksun.util.DailyCounter;
import sf.blacksun.util.FileUtil;
import sf.wicklet.gwt.site.server.Config;

public class WikiCommentInfo {

	private final DailyCounter counter = new DailyCounter("%1$tY%1$tm%1$td", "%1$s-%2$04d.txt");
	private final String user;
	private final String path;
	private final String subject;
	private final String content;

	public WikiCommentInfo(
		final String user, final String path, final StringValue subject, final StringValue content) {
		this(user, path, subject.toString(), content.toString());
	}

	public WikiCommentInfo(final String user, final String path, final String subject, final String content) {
		this.user = user == null ? "" : user;
		this.path = path == null ? "" : path;
		this.subject = subject == null ? "" : subject;
		this.content = content == null ? "" : content;
	}

	public synchronized boolean persist() {
		final String fname = String.format("WEB-INF/incoming/wiki/comment-" + counter.get());
		final File file = new File(WebApplication.get().getServletContext().getRealPath(fname));
		PrintWriter out = null;
		try {
			out = new PrintWriter(file);
			out.println("User: " + user);
			out.println("Date: " + counter.getDate());
			out.println("Path: " + path);
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
