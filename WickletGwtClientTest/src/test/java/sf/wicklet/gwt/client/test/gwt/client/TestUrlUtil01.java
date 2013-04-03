package sf.wicklet.gwt.client.test.gwt.client;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sf.wicklet.gwt.client.util.GwtUrlUtil.UrlParts;

public class TestUrlUtil01 {

    @Test
    public void testUrlParts01() {
        check(
            "http://localhost:8080/wiki/home.html",
            "http://localhost:8080/wiki/home.html",
            new UrlParts("http", null, null, "localhost", "8080", "/wiki/home.html", null, null));
        check(
            "https://admin:tomcat6@localhost:8080/wiki/home.html?p=test#123",
            "https://admin:tomcat6@localhost:8080/wiki/home.html?p=test#123",
            new UrlParts("https", "admin", "tomcat6", "localhost", "8080", "/wiki/home.html", "p=test", "123"));
        check(
            "https://admin@localhost:8080/wiki/home.html?p=test#123",
            "https://admin@localhost:8080/wiki/home.html?p=test#123",
            new UrlParts("https", "admin", null, "localhost", "8080", "/wiki/home.html", "p=test", "123"));
        check(
            "https://:tomcat6@localhost:8080/wiki/home.html?p=test#123",
            "https://:tomcat6@localhost:8080/wiki/home.html?p=test#123",
            new UrlParts("https", null, "tomcat6", "localhost", "8080", "/wiki/home.html", "p=test", "123"));
        check(
            "https://@localhost:8080/wiki/home.html?p=test#123",
            "https://localhost:8080/wiki/home.html?p=test#123",
            new UrlParts("https", null, null, "localhost", "8080", "/wiki/home.html", "p=test", "123"));
        check(
            "https://localhost/?p=test#123",
            "https://localhost/?p=test#123",
            new UrlParts("https", null, null, "localhost", null, "/", "p=test", "123"));
        check(
            "//localhost/?p=test#123",
            "//localhost/?p=test#123",
            new UrlParts(null, null, null, "localhost", null, "/", "p=test", "123"));
        check(
            "/?p=test#123",
            "/?p=test#123", //
            new UrlParts(null, null, null, null, null, "/", "p=test", "123"));
        check(
            "?p=test#123",
            "?p=test#123", //
            new UrlParts(null, null, null, null, null, null, "p=test", "123"));
        check(
            "?p=test",
            "?p=test", //
            new UrlParts(null, null, null, null, null, null, "p=test", null));
        check(
            "#123",
            "#123", //
            new UrlParts(null, null, null, null, null, null, null, "123"));
        check(
            "a",
            "a", //
            new UrlParts(null, null, null, null, null, "a", null, null));
        check(
            "",
            "", //
            new UrlParts(null, null, null, null, null, null, null, null));
    }

    private void check(final String url, final String asstring, final UrlParts expected) {
        final UrlParts parts = UrlParts.parse(url);
        assertEquals(expected.getScheme(), parts.getScheme());
        assertEquals(expected.getUser(), parts.getUser());
        assertEquals(expected.getPass(), parts.getPass());
        assertEquals(expected.getHost(), parts.getHost());
        assertEquals(expected.getPort(), parts.getPort());
        assertEquals(expected.getPath(), parts.getPath());
        assertEquals(expected.getQuery(), parts.getQuery());
        assertEquals(expected.getFragment(), parts.getFragment());
        assertEquals(asstring, parts.toString());
    }

    @Test
    public void testResolve01() {
        final UrlParts base = UrlParts.parse("http://localhost/wiki/home.html");
        assertEquals(
            "http://localhost/wiki/a?p=123#wiki/projects",
            base.resolve(UrlParts.parse("a?p=123#wiki/projects")).toString());
        assertEquals(
            "http://localhost/wiki/?p=123#wiki/projects",
            base.resolve(UrlParts.parse("?p=123#wiki/projects")).toString());
        assertEquals(
            "http://localhost/assets?p=123#wiki/projects",
            base.resolve(UrlParts.parse("../assets?p=123#wiki/projects")).toString());
        assertEquals(
            "http://localhost/assets?p=123#wiki/projects",
            base.resolve(UrlParts.parse("../../assets?p=123#wiki/projects")).toString());
        assertEquals(
            "http://localhost/wiki/a?p=123#wiki/projects",
            base.resolve(UrlParts.parse("assets/../a?p=123#wiki/projects")).toString());
        assertEquals(
            "http://localhost/a",
            base.resolve(UrlParts.parse("assets/.././../a")).toString());
        assertEquals(
            "http://localhost/?123",
            base.resolve(UrlParts.parse("assets/.././../?123")).toString());
        assertEquals(
            "http://localhost/#123",
            base.resolve(UrlParts.parse("assets/.././../#123")).toString());
    }

    @Test
    public void testResolve02() {
        final UrlParts base = UrlParts.parse("http://localhost/wiki/");
        assertEquals(
            "http://localhost/wiki/a?p=123#wiki/projects",
            base.resolve(UrlParts.parse("a?p=123#wiki/projects")).toString());
        assertEquals(
            "http://localhost/wiki/?p=123#wiki/projects",
            base.resolve(UrlParts.parse("?p=123#wiki/projects")).toString());
        assertEquals(
            "http://localhost/assets?p=123#wiki/projects",
            base.resolve(UrlParts.parse("../assets?p=123#wiki/projects")).toString());
        assertEquals(
            "http://localhost/assets?p=123#wiki/projects",
            base.resolve(UrlParts.parse("../../assets?p=123#wiki/projects")).toString());
    }
}
