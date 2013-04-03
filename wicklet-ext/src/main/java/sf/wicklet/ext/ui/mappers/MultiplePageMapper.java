/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.ui.mappers;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.apache.wicket.RequestListenerInterface;
import org.apache.wicket.core.request.handler.ListenerInterfaceRequestHandler;
import org.apache.wicket.core.request.mapper.AbstractBookmarkableMapper;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.info.ComponentInfo;
import org.apache.wicket.request.mapper.info.PageComponentInfo;
import org.apache.wicket.request.mapper.info.PageInfo;
import org.apache.wicket.request.mapper.parameter.IPageParametersEncoder;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.mapper.parameter.PageParametersEncoder;
import org.apache.wicket.util.ClassProvider;
import org.apache.wicket.util.lang.Args;
import sf.blacksun.util.text.TextUtil;

/**
 * Hack of MountedMapper to map the simple name of multiple classes onto a mount path.
 * No placeholder encoding.
 */
public class MultiplePageMapper extends AbstractBookmarkableMapper {
	private final IPageParametersEncoder pageParametersEncoder;

	private final String mountPath;

	private final List<String> mountSegments;

	private final Map<String, ClassProvider<? extends IRequestablePage>> pageClassProvider;

	@SafeVarargs
	public MultiplePageMapper(final String mountPath, final Class<? extends IRequestablePage>...pages) {
		Args.notEmpty(mountPath, "mountPath");
		this.mountPath = mountPath;
		mountSegments = TextUtil.split(new StringTokenizer(mountPath, "/"));
		pageParametersEncoder = new PageParametersEncoder();
		pageClassProvider = new TreeMap<String, ClassProvider<? extends IRequestablePage>>();
		for (final Class<? extends IRequestablePage> page: pages) {
			final String name = toAlias(page);
			if (pageClassProvider.put(name, ClassProvider.of(page)) != null) {
				throw new RuntimeException("ERROR: Duplicated mapping: " + page.getName());
		}}
	}

	@Override
	protected UrlInfo parseRequest(final Request request) {
		final Url url = request.getUrl();
		// when redirect to buffer/render is active and redirectFromHomePage returns true
		// check mounted class against the home page class. if it matches let wicket redirect
		// to the mounted URL
		if (redirectFromHomePage() && checkHomePage(url)) {
			return new UrlInfo(null, getContext().getHomePageClass(), newPageParameters());
		} else if (url.getPath().startsWith(mountPath)) {
			// check if the URL starts with the proper segments
			// try to extract page and component information from URL
			final PageComponentInfo info = getPageComponentInfo(url);
			final Class<? extends IRequestablePage> pageClass = getpageclass(getrpath(url.getPath()));
			if (pageClass != null) {
				final PageParameters pageParameters = extractPageParameters(request, url);
				return new UrlInfo(info, pageClass, pageParameters);
		}}
		return null;
	}

	private PageParameters extractPageParameters(final Request request, final Url url) {
		return extractPageParameters(request, url.getSegments().size(), pageParametersEncoder);
	}

	protected PageParameters newPageParameters() {
		return new PageParameters();
	}

	@Override
	public Url mapHandler(final IRequestHandler requestHandler) {
		Url url = super.mapHandler(requestHandler);
		if (url == null && requestHandler instanceof ListenerInterfaceRequestHandler) {
			final ListenerInterfaceRequestHandler handler = (ListenerInterfaceRequestHandler)requestHandler;
			final IRequestablePage page = handler.getPage();
			if (checkPageInstance(page)) {
				final String componentPath = handler.getComponentPath();
				final RequestListenerInterface listenerInterface = handler.getListenerInterface();
				Integer renderCount = null;
				if (listenerInterface.isIncludeRenderCount()) {
					renderCount = page.getRenderCount();
				}
				final PageInfo pageInfo = new PageInfo(page.getPageId());
				final ComponentInfo componentInfo = new ComponentInfo(
					renderCount,
					requestListenerInterfaceToString(listenerInterface),
					componentPath,
					handler.getBehaviorIndex());
				final PageComponentInfo pageComponentInfo
					= new PageComponentInfo(pageInfo, componentInfo);
				final UrlInfo urlInfo = new UrlInfo(
					pageComponentInfo, page.getClass(), handler.getPageParameters());
				url = buildUrl(urlInfo);
		}}
		return url;
	}

	/**
	 * @see org.apache.wicket.request.mapper.AbstractBookmarkableMapper#buildUrl(org.apache.wicket.request.mapper.AbstractBookmarkableMapper.UrlInfo)
	 */
	@Override
	protected Url buildUrl(final UrlInfo info) {
		final Url url = new Url();
		url.getSegments().addAll(mountSegments);
		url.getSegments().add(toAlias(info.getPageClass()));
		encodePageComponentInfo(url, info.getPageComponentInfo());
		return encodePageParameters(url, info.getPageParameters(), pageParametersEncoder);
	}

	/**
	 * Check if the URL is for home page and the home page class match mounted class. If so,
	 * redirect to mounted URL.
	 *
	 * @param url
	 * @return request handler or <code>null</code>
	 */
	private boolean checkHomePage(final Url url) {
		if (url.getSegments().isEmpty() && url.getQueryParameters().isEmpty()) {
			// this is home page
			final Class<? extends IRequestablePage> page = getpageclass(getrpath(url.getPath()));
			if (page != null && page.equals(getContext().getHomePageClass()) && redirectFromHomePage()) {
				return true;
		}}
		return false;
	}

	/**
	 * If this method returns <code>true</code> and application home page class is same as the class
	 * mounted with this encoder, request to home page will result in a redirect to the mounted
	 * path.
	 *
	 * @return whether this encode should respond to home page request when home page class is same
	 *         as mounted class.
	 */
	protected boolean redirectFromHomePage() {
		return true;
	}

	/**
	 * @see org.apache.wicket.request.mapper.AbstractBookmarkableMapper#pageMustHaveBeenCreatedBookmarkable()
	 */
	@Override
	protected boolean pageMustHaveBeenCreatedBookmarkable() {
		return false;
	}

	/**
	 * @see org.apache.wicket.request.mapper.AbstractBookmarkableMapper#getCompatibilityScore(Request)
	 */
	@Override
	public int getCompatibilityScore(final Request request) {
		final int size = mountSegments.size();
		final List<String> segments = request.getUrl().getSegments();
		if (segments.size() <= size) {
			return 0;
		}
		for (int i = 0; i < size; ++i) {
			if (!mountSegments.get(i).equals(segments.get(i))) {
				return 0;
		}}
		return size;
	}

	@Override
	protected boolean checkPageClass(final Class<? extends IRequestablePage> pageClass) {
		final ClassProvider<? extends IRequestablePage> p = pageClassProvider.get(toAlias(pageClass));
		if (p != null) {
			final Class<? extends IRequestablePage> pp = p.get();
			return pp != null && pp.getName().equals(pageClass.getName());
		}
		return false;
	}

	protected String toAlias(final Class<? extends IRequestablePage> page) {
		return page.getSimpleName();
	}

	protected String fromAlias(final String pagename) {
		return pagename;
	}

	private Class<? extends IRequestablePage> getpageclass(final String rpath) {
		if (rpath != null) {
			final ClassProvider<? extends IRequestablePage> p = pageClassProvider.get(fromAlias(rpath));
			if (p != null) {
				return p.get();
		}}
		return null;
	}

	private String getrpath(final String urlpath) {
		if (urlpath.startsWith(mountPath) && urlpath.length() > mountPath.length() + 1) {
			return urlpath.substring(mountPath.length() + 1);
		}
		return null;
	}

	@Override
	public String toString() {
		return "MountedMapper [" + mountPath + "]";
	}
}
