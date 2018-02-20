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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.cactoos.Input;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link ReaderOf}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class ReaderOfTest {

    /**
     * Test content for all the tests in this class.
     */
    private static final String CONTENT =
        "Hello, товарищ äÄ üÜ öÖ and ß";

    @Test(expected = NullPointerException.class)
    public void readsNull() {
        this.assertThat(
            new ReaderOf((Input) null),
            ""
        );
    }

    @Test
    public void readsEmpty() {
        this.assertThat(
            new ReaderOf(""),
            ""
        );
    }

    @Test
    public void readsCharVarArg() {
        this.assertThat(
            new ReaderOf('a', 'b', 'c'),
            "abc"
        );
    }

    @Test
    public void readsCharArrayWithCharset() {
        this.assertThat(
            new ReaderOf(
                ReaderOfTest.CONTENT.toCharArray(),
                StandardCharsets.UTF_8
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsCharArrayWithCharsetByName() {
        this.assertThat(
            new ReaderOf(
                ReaderOfTest.CONTENT.toCharArray(),
                StandardCharsets.UTF_8.name()
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsByteArray() {
        this.assertThat(
            new ReaderOf(
                ReaderOfTest.CONTENT.getBytes(StandardCharsets.UTF_8)
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsByteArrayWithCharset() {
        this.assertThat(
            new ReaderOf(
                ReaderOfTest.CONTENT.getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsByteArrayWithCharsetByName() {
        this.assertThat(
            new ReaderOf(
                ReaderOfTest.CONTENT.getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8.name()
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsPath() throws IOException {
        final Path input = Files.createTempFile("cactoos-1", "txt-1");
        Files.write(
            input,
            ReaderOfTest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        this.assertThat(
            new ReaderOf(input),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsFile() throws IOException {
        final Path input = Files.createTempFile("cactoos-2", "txt-2");
        Files.write(
            input,
            ReaderOfTest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        this.assertThat(
            new ReaderOf(input.toFile()),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsUrl() throws IOException {
        final Path input = Files.createTempFile("cactoos-3", "txt-3");
        Files.write(
            input,
            ReaderOfTest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        this.assertThat(
            new ReaderOf(
                input
                    .toUri()
                    .toURL()
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsUri() throws IOException {
        final Path input = Files.createTempFile("cactoos-4", "txt-4");
        Files.write(
            input,
            ReaderOfTest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        this.assertThat(
            new ReaderOf(input.toUri()),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsBytes() {
        this.assertThat(
            new ReaderOf(new BytesOf(ReaderOfTest.CONTENT)),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsText() {
        this.assertThat(
            new ReaderOf(new TextOf(ReaderOfTest.CONTENT)),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsTextWithCharset() {
        this.assertThat(
            new ReaderOf(
                new TextOf(ReaderOfTest.CONTENT),
                StandardCharsets.UTF_8
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsTextWithCharsetByName() {
        this.assertThat(
            new ReaderOf(
                new TextOf(ReaderOfTest.CONTENT),
                StandardCharsets.UTF_8.name()
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsCharSequence() {
        this.assertThat(
            new ReaderOf(ReaderOfTest.CONTENT),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsCharSequenceWithCharset() {
        this.assertThat(
            new ReaderOf(
                ReaderOfTest.CONTENT,
                StandardCharsets.UTF_8
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsCharSequenceWithCharsetByName() {
        this.assertThat(
            new ReaderOf(
                ReaderOfTest.CONTENT,
                StandardCharsets.UTF_8.name()
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsInput() {
        this.assertThat(
            new ReaderOf(new InputOf(ReaderOfTest.CONTENT)),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsInputWithCharset() {
        this.assertThat(
            new ReaderOf(
                new InputOf(ReaderOfTest.CONTENT),
                StandardCharsets.UTF_8
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsInputWithCharsetByName() {
        this.assertThat(
            new ReaderOf(
                new InputOf(ReaderOfTest.CONTENT),
                StandardCharsets.UTF_8.name()
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsInputWithCharsetDecoder() {
        this.assertThat(
            new ReaderOf(
                new InputOf(ReaderOfTest.CONTENT),
                StandardCharsets.UTF_8.newDecoder()
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsInputStream() {
        this.assertThat(
            new ReaderOf(
                new InputStreamOf(ReaderOfTest.CONTENT)
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsInputStreamWithCharset() {
        this.assertThat(
            new ReaderOf(
                new InputStreamOf(ReaderOfTest.CONTENT),
                StandardCharsets.UTF_8
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsInputStreamWithCharsetByName() throws IOException {
        this.assertThat(
            new ReaderOf(
                new InputStreamOf(ReaderOfTest.CONTENT),
                StandardCharsets.UTF_8.name()
            ),
            ReaderOfTest.CONTENT
        );
    }

    @Test
    public void readsInputStreamWithCharsetDecoder() {
        this.assertThat(
            new ReaderOf(
                new InputStreamOf(ReaderOfTest.CONTENT),
                StandardCharsets.UTF_8.newDecoder()
            ),
            ReaderOfTest.CONTENT
        );
    }

    private void assertThat(final Reader actual, final String expected) {
        MatcherAssert.assertThat(
            new BufferedReader(actual)
                .lines()
                .collect(Collectors.joining()),
            Matchers.is(expected)
        );
    }
}
