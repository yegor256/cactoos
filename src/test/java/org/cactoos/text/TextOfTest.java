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
package org.cactoos.text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.matchers.TextHasString;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link TextOf}.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class TextOfTest {

    @Test
    public void readsInputIntoText() throws IOException {
        MatcherAssert.assertThat(
            "Can't read text from Input",
            new SyncText(
                new TextOf(
                    new InputOf("привет, друг!"),
                    StandardCharsets.UTF_8
                )
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith("привет, "),
                Matchers.endsWith("друг!")
            )
        );
    }

    @Test
    public void readsInputIntoTextWithDefaultCharset() throws IOException {
        MatcherAssert.assertThat(
            "Can't read text from Input with default charset",
            new TextOf(
                new InputOf("Hello, друг! with default charset")
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith("Hello, "),
                Matchers.endsWith("друг! with default charset")
            )
        );
    }

    @Test
    public void readsInputIntoTextWithSmallBuffer() throws IOException {
        MatcherAssert.assertThat(
            "Can't read text with a small reading buffer",
            new TextOf(
                new InputOf("Hi, товарищ! with small buffer"),
                2,
                StandardCharsets.UTF_8
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith("Hi,"),
                Matchers.endsWith("товарищ! with small buffer")
            )
        );
    }

    @Test
    public void readsInputIntoTextWithSmallBufferAndDefaultCharset()
        throws IOException {
        MatcherAssert.assertThat(
            "Can't read text with a small reading buffer and default charset",
            new TextOf(
                new InputOf("Hello, товарищ! with default charset"),
                2
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith("Hello,"),
                Matchers.endsWith("товарищ! with default charset")
            )
        );
    }

    @Test
    public void readsFromReader() throws Exception {
        final String source = "hello, друг!";
        MatcherAssert.assertThat(
            "Can't read string through a reader",
            new TextOf(
                new StringReader(source),
                StandardCharsets.UTF_8
            ).asString(),
            Matchers.equalTo(
                new String(
                    new BytesOf(source).asBytes(),
                    StandardCharsets.UTF_8
                )
            )
        );
    }

    @Test
    public void readsFromReaderWithDefaultEncoding() throws Exception {
        final String source = "hello, друг! with default encoding";
        MatcherAssert.assertThat(
            "Can't read string with default encoding through a reader",
            new TextOf(new StringReader(source)).asString(),
            Matchers.equalTo(
                new String(
                    new BytesOf(source).asBytes(),
                    StandardCharsets.UTF_8
                )
            )
        );
    }

    @Test
    public void readsEncodedArrayOfCharsIntoText() throws IOException {
        MatcherAssert.assertThat(
            "Can't read array of encoded chars into text.",
            new TextOf(
                'O', ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a',
                ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a'
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith("O que sera"),
                Matchers.endsWith(" que sera")
            )
        );
    }

    @Test
    public void readsAnArrayOfBytes() throws Exception {
        final byte[] bytes = new byte[] {(byte) 0xCA, (byte) 0xFE};
        MatcherAssert.assertThat(
            "Can't read array of bytes",
            new TextOf(
                bytes
            ).asString(),
            Matchers.equalTo(new String(bytes, StandardCharsets.UTF_8))
        );
    }

    @Test
    public void readsStringBuilder() throws IOException {
        final String starts = "Name it, ";
        final String ends = "then it exists!";
        MatcherAssert.assertThat(
            "Can't process a string builder",
            new TextOf(
                new StringBuilder(starts).append(ends)
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith(starts),
                Matchers.endsWith(ends)
            )
        );
    }

    @Test
    public void readsStringBuffer() throws IOException {
        final String starts = "In our daily life, ";
        final String ends = "we can smile!";
        MatcherAssert.assertThat(
            "Can't process a string builder hahahaha",
            new TextOf(
                new StringBuffer(starts).append(ends)
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith(starts),
                Matchers.endsWith(ends)
            )
        );
    }

    @Test
    public void printsStackTrace() {
        MatcherAssert.assertThat(
            "Can't print exception stacktrace",
            new TextOf(
                new IOException(
                    "It doesn't work at all"
                )
            ),
            new TextHasString(
                Matchers.allOf(
                    Matchers.containsString("java.io.IOException"),
                    Matchers.containsString("doesn't work at all"),
                    Matchers.containsString(
                        "\tat org.cactoos.text.TextOfTest"
                    )
                )
            )
        );
    }

    @Test
    public void readsFromInputStream() throws Exception {
        final String content = "line1";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        MatcherAssert.assertThat(
            "Can't read inputStream",
            new TextOf(stream).asString(),
            Matchers.equalTo(
                new String(content.getBytes(), StandardCharsets.UTF_8)
            )
        );
    }

    @Test
    public void readsMultilineInputStream() throws Exception {
        final String content = "line1-\nline2";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        MatcherAssert.assertThat(
            "Can't read multiline inputStream",
            new TextOf(stream).asString(),
            Matchers.equalTo(content)
        );
    }

    @Test
    public void readsMultilineInputStreamWithCarriageReturn() throws Exception {
        final String content = "line1-\rline2";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        MatcherAssert.assertThat(
            "Can't read multiline inputStream with carriage return",
            new TextOf(stream).asString(),
            Matchers.equalTo(content)
        );
    }

    @Test
    public void readsClosedInputStream() throws Exception {
        final String content = "content";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        stream.close();
        MatcherAssert.assertThat(
            "Can't read closed input stream",
            new TextOf(stream).asString(),
            Matchers.equalTo(content)
        );
    }

    @Test
    public void readsEmptyInputStream() throws Exception {
        final String content = "";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        MatcherAssert.assertThat(
            "Can't read empty input stream",
            new TextOf(stream).asString(),
            Matchers.equalTo(content)
        );
    }

    @Test
    public void printsStackTraceFromArray() {
        MatcherAssert.assertThat(
            "Can't print exception stacktrace from array",
            new TextOf(
                new IOException("").getStackTrace()
            ),
            new TextHasString(
                Matchers.containsString("org.cactoos.text.TextOfTest")
            )
        );
    }
}
