/*
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
import java.io.File;
import java.nio.charset.StandardCharsets;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.TextIs;

/**
 * Test case for {@link TeeOutput}.
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class TeeOutputTest {
    /**
     * Temporary files generator.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void copiesContent() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new Assertion<>(
            "Can't copy Output to Output and return Input",
            () -> new TextOf(
                new Sticky(
                    new TeeInput(
                        new InputOf("Hello, товарищ!"),
                            new TeeOutput(
                            new OutputTo(baos),
                            new OutputTo(new ByteArrayOutputStream())
                        )
                    )
                )
            ),
            new TextIs(
                new TextOf(baos::toByteArray, StandardCharsets.UTF_8)
            )
        ).affirm();
    }

    @Test
    public void copiesWithWriter() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new Assertion<>(
            "Can't copy Output with writer",
            () -> new TextOf(
                new Sticky(
                    new TeeInput(
                        new InputOf("Hello, товарищ! writer"),
                        new TeeOutput(
                            new OutputTo(baos),
                            new WriterTo(new ByteArrayOutputStream())
                        )
                    )
                )
            ),
            new TextIs(
                new TextOf(baos::toByteArray, StandardCharsets.UTF_8)
            )
        ).affirm();
    }

    @Test
    public void copiesWithWriterAndCharset() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new Assertion<>(
            "Can't copy Output with writer and charset",
            () -> new TextOf(
                new Sticky(
                    new TeeInput(
                        new InputOf(
                            "Hello, товарищ! writer and charset"
                        ),
                        new TeeOutput(
                            new OutputTo(baos),
                            new WriterTo(new ByteArrayOutputStream()),
                            StandardCharsets.UTF_8
                        )
                    )
                )
            ),
            new TextIs(
                new TextOf(baos::toByteArray, StandardCharsets.UTF_8)
            )
        ).affirm();
    }

    @Test
    public void copiesWithPath() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final File file = this.folder.newFile();
        MatcherAssert.assertThat(
            "Can't copy Output with path",
            new TextOf(
                new TeeInput(
                    new InputOf("Hello, товарищ! with path"),
                    new TeeOutput(
                        new OutputTo(baos),
                        file.toPath()
                    )
                )
            ),
            new TextIs(
                new TextOf(file.toPath())
            )
        );
    }

    @Test
    public void copiesWithFile() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final File file = this.folder.newFile();
        MatcherAssert.assertThat(
            "Can't copy Output with file",
            new TextOf(
                new TeeInput(
                    new InputOf("Hello, товарищ! with file"),
                    new TeeOutput(
                        new OutputTo(baos),
                        file
                    )
                )
            ),
            new TextIs(
                new TextOf(file.toPath())
            )
        );
    }

    @Test
    public void copiesWithOutputStream() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new Assertion<>(
            "Can't copy Output with output stream",
            () -> new TextOf(
                new Sticky(
                    new TeeInput(
                        new InputOf(
                            "Hello, товарищ! with output stream"
                        ),
                        new TeeOutput(
                            new OutputTo(baos),
                            new ByteArrayOutputStream()
                        )
                    )
                )
            ),
            new TextIs(
                new TextOf(baos::toByteArray, StandardCharsets.UTF_8)
            )
        ).affirm();
    }

}
