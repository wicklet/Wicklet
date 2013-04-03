/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.components.include;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sf.blacksun.util.FileUtil;
import sf.wicklet.ext.util.IFileManager;

/** Include content of a local file as content of this component. */
public class IncludeLocal extends WebComponent {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(IncludeLocal.class);
	private IFileManager fileManager;

	/** @param rpath Absolute context relative path, eg. /WEB-INF/wiki/Home. */
	public IncludeLocal(final Object wid, final IModel<String> rpath) {
		super(wid.toString(), rpath);
	}

	/** @param rpath Absolute context relative path, eg. /WEB-INF/wiki/Home. */
	public IncludeLocal(final Object wid, final IFileManager filemanager, final IModel<String> rpath) {
		super(wid.toString(), rpath);
		fileManager = filemanager;
	}

	@Override
	public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
		String content;
		final String rpath = getDefaultModelObjectAsString();
		final ServletContext context = ((WebApplication)getApplication()).getServletContext();
		final File file = new File(context.getRealPath(rpath));
		if (!FileUtil.isBaseDir(new File(context.getRealPath("")), file)) {
			logger.warn("Error include file not under servlet context: " + file.getAbsolutePath());
			content = "ERROR";
		} else {
			try {
				if (fileManager != null) {
					content = fileManager.read(file);
				} else {
					content = FileUtil.asString(file);
			}} catch (final IOException e) {
				logger.warn("Error reading include file: " + file.getAbsolutePath());
				content = "ERROR";
		}}
		replaceComponentTagBody(markupStream, openTag, content);
	}
}
