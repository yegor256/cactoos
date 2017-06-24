/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.io;

import java.io.IOException;
import org.cactoos.TextHasString;
import org.cactoos.text.BytesAsText;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.takes.http.FtRemote;
import org.takes.tk.TkHtml;

/**
 * Test case for {@link UrlAsInput}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class UrlAsInputTest {

    @Test
    public void readsFileContent() throws IOException {
        MatcherAssert.assertThat(
            "Can't read bytes from a file-system URL",
            new InputAsBytes(
                new UrlAsInput(
                    this.getClass().getResource(
                        "/org/cactoos/io/UrlAsInput.class"
                    )
                )
            ).asBytes().length,
            Matchers.greaterThan(0)
        );
    }

    @Test
    public void readsRealUrl() throws IOException {
        new FtRemote(new TkHtml("<html>How are you?</html>")).exec(
            home -> MatcherAssert.assertThat(
                "Can't fetch bytes from the URL",
                new BytesAsText(
                    new InputAsBytes(
                        new UrlAsInput(home)
                    )
                ),
                new TextHasString(
                    Matchers.allOf(
                        Matchers.startsWith("<html"),
                        Matchers.endsWith("html>")
                    )
                )
            )
        );
    }

    @Test
    public void readsStringUrl() {
        MatcherAssert.assertThat(
            "Can't fetch bytes from the HTTPS URL",
            new BytesAsText(
                new InputAsBytes(
                    new UrlAsInput(
                        // @checkstyle LineLength (1 line)
                        "file:src/test/resources/org/cactoos/large-text.txt"
                    )
                )
            ),
            new TextHasString(Matchers.containsString("Lorem ipsum"))
        );
    }

}
