package org.codehaus.mojo.license.fetchlicenses;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Text_Test {

    @Test
    public void returnTheContentInToString() throws Exception {
        assertThat(new Text("the content").toString(), is("the content"));
    }
}
