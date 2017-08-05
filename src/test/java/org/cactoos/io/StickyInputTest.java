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
import java.net.MalformedURLException;
import java.net.URL;
import org.cactoos.Input;
import org.cactoos.ScalarHasValue;
import org.cactoos.TextHasString;
import org.cactoos.func.MatcherOf;
import org.cactoos.func.RepeatedFunc;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link StickyInput}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.6
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class StickyInputTest {

    @Test
    public void readsFileContent() {
        MatcherAssert.assertThat(
            "Can't read bytes from a file",
            new StickyInput(
                new ResourceOf(
                    "org/cactoos/large-text.txt"
                )
            ),
            new MatcherOf<>(
                new RepeatedFunc<Input, Boolean>(
                    input -> new BytesOf(
                        new TeeInput(input, new DeadOutput())
                    // @checkstyle MagicNumber (2 lines)
                    ).asBytes().length == 74536,
                    10
                )
            )
        );
    }

    @Test
    public void readsRealUrl() throws MalformedURLException {
        MatcherAssert.assertThat(
            "Can't fetch text page from the URL",
            new TextOf(
                new StickyInput(
                    new InputOf(
                        new URL(
                            // @checkstyle LineLength (1 line)
                            "file:src/test/resources/org/cactoos/large-text.txt"
                        )
                    )
                )
            ),
            new TextHasString(
                Matchers.endsWith("est laborum.\n")
            )
        );
    }

    @Test
    public void readsFileContentSlowlyAndCountsLength() {
        final long size = 100_000L;
        MatcherAssert.assertThat(
            "Can't read bytes from a large source slowly and count length",
            new LengthOf(
                new StickyInput(
                    new SlowInput(size)
                )
            ),
            new ScalarHasValue<>(size)
        );
    }

    @Test
    public void readsFileContentSlowly() throws IOException {
        final int size = 130_000;
        MatcherAssert.assertThat(
            "Can't read bytes from a large source slowly",
            new BytesOf(
                new StickyInput(
                    new SlowInput(size)
                )
            ).asBytes().length,
            Matchers.equalTo(size)
        );
    }

}
