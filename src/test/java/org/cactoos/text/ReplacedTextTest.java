package org.cactoos.text;

import org.cactoos.TextHasString;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link ReplacedText}.
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ReplacedTextTest {

    @Test
    public void replaceText() {
        MatcherAssert.assertThat(
                "Can't replace a text",
                new ReplacedText(new StringAsText("Hello!"), "ello", "i"),
                new TextHasString("Hi!")
        );
    }
}
