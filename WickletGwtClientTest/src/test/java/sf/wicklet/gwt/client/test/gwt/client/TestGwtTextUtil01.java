package sf.wicklet.gwt.client.test.gwt.client;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sf.wicklet.gwt.client.util.GwtTextUtil;

public class TestGwtTextUtil01 {

    @Test
    public void test() {
        assertEquals(0, GwtTextUtil.tokenize("/", "").size());
        assertEquals(1, GwtTextUtil.tokenize("/", "a").size());
        assertEquals(1, GwtTextUtil.tokenize("/", "a/").size());
        assertEquals(1, GwtTextUtil.tokenize("/", "/a/").size());
        assertEquals(1, GwtTextUtil.tokenize("/", "/a/").size());
        assertEquals(2, GwtTextUtil.tokenize("/", "/a/b").size());
        assertEquals(2, GwtTextUtil.tokenize("/", "a/b").size());
        assertEquals(3, GwtTextUtil.tokenize("/", "/a/b/c/").size());
        assertEquals(3, GwtTextUtil.tokenize("/", "//a/b/c//").size());
        assertEquals(3, GwtTextUtil.tokenize("/", "a/b/c//").size());
        assertEquals(3, GwtTextUtil.tokenize("/", "a/b/c").size());
    }
}
