/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.cactoos.matchers.MatcherOf;
import org.cactoos.matchers.TextHasString;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link TeeOutput}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class TeeOutputTest {

    /**
     * The CONTENT for copying of TeeOutput.
     */
    private static final String CONTENT = "Hello, товарищ!";

    /**
     * The TeeOutputTest.DESCRIPTION of failure test.
     */
    private static final String
        DESCRIPTION = "Can't copy Output to Output and return Input";

    @Test
    public void copiesContent() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ByteArrayOutputStream copy = new ByteArrayOutputStream();
        MatcherAssert.assertThat(
            TeeOutputTest.DESCRIPTION,
            new TextOf(
                new TeeInput(
                    new InputOf(TeeOutputTest.CONTENT),
                    new TeeOutput(
                        new OutputTo(baos),
                        new OutputTo(copy)
                    )
                )
            ),
            new TextHasString(
                new MatcherOf<>(
                    str -> {
                        return new String(
                            copy.toByteArray(), StandardCharsets.UTF_8
                        ).equals(str);
                    }
                )
            )
        );
    }

    @Test
    public void copiesWithWriter() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ByteArrayOutputStream copy = new ByteArrayOutputStream();
        MatcherAssert.assertThat(
            TeeOutputTest.DESCRIPTION,
            new TextOf(
                new TeeInput(
                    new InputOf(TeeOutputTest.CONTENT),
                    new TeeOutput(
                        new OutputTo(baos),
                        new WriterTo(copy)
                    )
                )
            ),
            new TextHasString(
                new MatcherOf<>(
                    str -> {
                        return new String(
                            copy.toByteArray(), StandardCharsets.UTF_8
                        ).equals(str);
                    }
                )
            )
        );
    }

    @Test
    public void copiesWithWriterAndCharset() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ByteArrayOutputStream copy = new ByteArrayOutputStream();
        MatcherAssert.assertThat(
            TeeOutputTest.DESCRIPTION,
            new TextOf(
                new TeeInput(
                    new InputOf(TeeOutputTest.CONTENT),
                    new TeeOutput(
                        new OutputTo(baos),
                        new WriterTo(copy),
                        StandardCharsets.UTF_8
                    )
                )
            ),
            new TextHasString(
                new MatcherOf<>(
                    str -> {
                        return new String(
                            copy.toByteArray(), StandardCharsets.UTF_8
                        ).equals(str);
                    }
                )
            )
        );
    }

    @Test
    public void copiesWithPath() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final TempFile file = new TempFile();
        MatcherAssert.assertThat(
            TeeOutputTest.DESCRIPTION,
            new TextOf(
                new TeeInput(
                    new InputOf(TeeOutputTest.CONTENT),
                    new TeeOutput(
                        new OutputTo(baos),
                        file.value()
                    )
                )
            ),
            new TextHasString(
                new MatcherOf<>(
                    str -> {
                        return new String(
                            new TextOf(file.value()).asString()
                        ).equals(str);
                    }
                )
            )
        );
    }

    @Test
    public void copiesWithFile() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final TempFile file = new TempFile();
        MatcherAssert.assertThat(
            TeeOutputTest.DESCRIPTION,
            new TextOf(
                new TeeInput(
                    new InputOf(TeeOutputTest.CONTENT),
                    new TeeOutput(
                        new OutputTo(baos),
                        file.value().toFile()
                    )
                )
            ),
            new TextHasString(
                new MatcherOf<>(
                    str -> {
                        return new String(
                            new TextOf(file.value()).asString()
                        ).equals(str);
                    }
                )
            )
        );
    }

    @Test
    public void copiesWithOutputStream() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ByteArrayOutputStream copy = new ByteArrayOutputStream();
        MatcherAssert.assertThat(
            TeeOutputTest.DESCRIPTION,
            new TextOf(
                new TeeInput(
                    new InputOf(TeeOutputTest.CONTENT),
                    new TeeOutput(
                        new OutputTo(baos),
                        copy
                    )
                )
            ),
            new TextHasString(
                new MatcherOf<>(
                    str -> {
                        return new String(
                            copy.toByteArray(), StandardCharsets.UTF_8
                        ).equals(str);
                    }
                )
            )
        );
    }

}
