/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You should have received a copy of  the license along with this library.
 * You may also obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0.
 */
package sf.wicklet.ext.application;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.crypt.Base64;
import org.apache.wicket.util.crypt.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sf.blacksun.util.FileUtil;
import sf.blacksun.util.text.TextUtil;

public class WickletSupport implements IWickletSupport {

    private static final int DEF_HTTP_PORT = 8080;
    private static final int DEF_HTTPS_PORT = 8443;
    private static Logger logger = LoggerFactory.getLogger(WickletSupport.class);

    ////////////////////////////////////////////////////////////////////////

    private int httpPort = DEF_HTTP_PORT;
    private int httpsPort = DEF_HTTPS_PORT;
    private File contextRoot;

    ////////////////////////////////////////////////////////////////////////

    public static String randomString(final Random random, final int min, final int max) {
        final byte[] a = new byte[max];
        random.nextBytes(a);
        int len = min;
        if (max != min) {
            len += random.nextInt(max - min + 1);
        }
        String ret = StringUtils.newStringUsAscii(Base64.encodeBase64(a, false, true));
        if (ret.length() != len) {
            ret = ret.substring(0, len);
        }
        return ret;
    }

    ////////////////////////////////////////////////////////////////////////

    public WickletSupport() {
        httpPort = getInitParameter("httpPort", DEF_HTTP_PORT);
        httpsPort = getInitParameter("httpsPort", DEF_HTTPS_PORT);
    }

    @Override
    public ServletContext getServletContext() {
        return WebApplication.get().getServletContext();
    }

    @Override
    public String getContextPath() {
        return getServletContext().getContextPath();
    }

    @Override
    public String getContextPath(final String path) {
        return getContextPath() + path;
    }

    @Override
    public File getContextFile(final String path) {
        try {
            final File ret = new File(getServletContext().getRealPath(path)).getCanonicalFile();
            if (FileUtil.isBaseDir(getContextFile(), ret)) {
                return ret;
            }
        } catch (final IOException e) {
            return null;
        }
        return null;
    }

    @Override
    public String getHttpsUrl(final String path) {
        final HttpServletRequest req = (HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest();
        return "https://" + req.getServerName() + ':' + getHttpsPort() + getContextPath() + path;
    }

    @Override
    public int getHttpPort() {
        return httpPort;
    }

    @Override
    public int getHttpsPort() {
        return httpsPort;
    }

    @Override
    public IWickletConfiguration getConfiguration(final String contextparamkey, final String defpath)
        throws IOException {
        String path = getServletContext().getInitParameter(contextparamkey);
        if (path == null) {
            path = defpath;
        }
        final File file = getContextFile(path);
        IWickletConfiguration ret = null;
        if (file.exists()) {
            try {
                ret = WickletConfiguration.parse(file);
            } catch (final JSONException e) {
                throw new IOException("ERROR: Reading file: " + file, e);
            }
        }
        if (ret == null) {
            ret = new WickletConfiguration();
        }
        return ret;
    }

    ////////////////////////////////////////////////////////////////////////

    public int getInitParameter(final String name, final int def) {
        final String value = getServletContext().getInitParameter(name);
        if (!TextUtil.isEmpty(value)) {
            try {
                return Integer.parseInt(value);
            } catch (final NumberFormatException e) {
                // ignore
            }
        }
        return def;
    }

    ////////////////////////////////////////////////////////////////////////

    private File getContextFile() {
        if (contextRoot == null) {
            final String path = getServletContext().getRealPath("/");
            final File file = new File(path);
            try {
                contextRoot = file.getCanonicalFile();
            } catch (final IOException e) {
                logger.error("getCanonicalFile of context root failed: " + path, e);
                contextRoot = file;
            }
        }
        return contextRoot;
    }

    ////////////////////////////////////////////////////////////////////////
}
