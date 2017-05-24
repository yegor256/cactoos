package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

public class BlankTextTest {
    @Test
    public void determinesEmptyText() throws IOException {
        MatcherAssert.assertThat(
                new BlankText(
                        new StringAsText("")
                ).asValue(),
                Matchers.is(Boolean.TRUE)
        );
    }

    @Test
    public void determinesBlankText() throws IOException {
        MatcherAssert.assertThat(
                new BlankText(
                        new StringAsText("   ")
                ).asValue(),
                Matchers.is(Boolean.TRUE)
        );
    }

    @Test
    public void determinesNotBlankText() throws IOException {
        MatcherAssert.assertThat(
                new BlankText(
                        new StringAsText("full")
                ).asValue(),
                Matchers.is(Boolean.FALSE)
        );
    }
}
