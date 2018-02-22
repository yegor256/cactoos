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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.cactoos.Input;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test case for {@link ReaderOf}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Roman Proshin (roman@proshin.org)
 * @version $Id$
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class ReaderOfTest {

    /**
     * Temporary files generator.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test(expected = NullPointerException.class)
    public void readsNull() throws IOException {
        MatcherAssert.assertThat(
            new TextOf(new ReaderOf((Input) null)).asString(),
            new IsEqual<>("")
        );
    }

    @Test
    public void readsEmpty() throws IOException {
        MatcherAssert.assertThat(
            new TextOf(new ReaderOf("")).asString(),
            new IsEqual<>("")
        );
    }

    @Test
    public void readsCharVarArg() throws IOException {
        MatcherAssert.assertThat(
            new TextOf(new ReaderOf('a', 'b', 'c')).asString(),
            new IsEqual<>("abc")
        );
    }

    @Test
    public void readsCharArrayWithCharset() throws IOException {
        final String message =
            "char array on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    message.toCharArray(),
                    StandardCharsets.UTF_8
                )
            ).asString(),
            new IsEqual<>(message)
        );
    }

    @Test
    public void readsCharArrayWithCharsetByName() throws IOException {
        final String message =
            "char array with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    message.toCharArray(),
                    StandardCharsets.UTF_8.name()
                )
            ).asString(),
            new IsEqual<>(message)
        );
    }

    @Test
    public void readsByteArray() throws IOException {
        final String message =
            "byte array on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    message.getBytes(StandardCharsets.UTF_8)
                )
            ).asString(),
            new IsEqual<>(message)
        );
    }

    @Test
    public void readsByteArrayWithCharset() throws IOException {
        final String message =
            "byte array with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    message.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8
                )
            ).asString(),
            new IsEqual<>(message)
        );
    }

    @Test
    public void readsByteArrayWithCharsetByName() throws IOException {
        final String message =
            "bte array with charset by name on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    message.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8.name()
                )
            ).asString(),
            new IsEqual<>(message)
        );
    }

    @Test
    public void readsPath() throws IOException {
        final String message =
            "path on äÄ üÜ öÖ ß жш";
        final File input = this.folder.newFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        MatcherAssert.assertThat(
            new TextOf(new ReaderOf(input)).asString(),
            new IsEqual<>(message)
        );
    }

    @Test
    public void readsFile() throws IOException {
        final String message =
            "file on äÄ üÜ öÖ ß жш";
        final File input = this.folder.newFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        MatcherAssert.assertThat(
            new TextOf(new ReaderOf(input)).asString(),
            new IsEqual<>(message)
        );
    }

    @Test
    public void readsUrl() throws IOException {
        final String message =
            "URL on äÄ üÜ öÖ ß жш";
        final File input = this.folder.newFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    input
                        .toURI()
                        .toURL()
                )
            ).asString(),
            new IsEqual<>(message)
        );
    }

    @Test
    public void readsUri() throws IOException {
        final String message =
            "URI on äÄ üÜ öÖ ß жш";
        final File input = this.folder.newFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        MatcherAssert.assertThat(
            new TextOf(new ReaderOf(input.toURI())).asString(),
            new IsEqual<>(message)
        );
    }

    @Test
    public void readsBytes() throws IOException {
        final String input =
            "Bytes on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(new ReaderOf(new BytesOf(input))).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsText() throws IOException {
        final String input =
            "Text on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(new ReaderOf(new TextOf(input))).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsTextWithCharset() throws IOException {
        final String input =
            "Text with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    new TextOf(input),
                    StandardCharsets.UTF_8
                )
            ).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsTextWithCharsetByName() throws IOException {
        final String input =
            "Text with charset by name on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    new TextOf(input),
                    StandardCharsets.UTF_8.name()
                )
            ).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsCharSequence() throws IOException {
        final String input =
            "char sequence on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(new ReaderOf(input)).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsCharSequenceWithCharset() throws IOException {
        final String input =
            "char sequence with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    input,
                    StandardCharsets.UTF_8
                )
            ).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsCharSequenceWithCharsetByName() throws IOException {
        final String input =
            "char sequence with charset by name on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    input,
                    StandardCharsets.UTF_8.name()
                )
            ).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsInput() throws IOException {
        final String input =
            "Input on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(new ReaderOf(new InputOf(input))).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsInputWithCharset() throws IOException {
        final String input =
            "Input with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    new InputOf(input),
                    StandardCharsets.UTF_8
                )
            ).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsInputWithCharsetByName() throws IOException {
        final String input =
            "Input with charset by name on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    new InputOf(input),
                    StandardCharsets.UTF_8.name()
                )
            ).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsInputWithCharsetDecoder() throws IOException {
        final String input =
            "Input with charset decoder on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    new InputOf(input),
                    StandardCharsets.UTF_8.newDecoder()
                )
            ).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsInputStream() throws IOException {
        final String input =
            "InputStream on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(new ReaderOf(new InputStreamOf(input))).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsInputStreamWithCharset() throws IOException {
        final String input =
            "InputStream with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    new InputStreamOf(input),
                    StandardCharsets.UTF_8
                )
            ).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsInputStreamWithCharsetByName() throws IOException {
        final String input =
            "InputStream with charset by name on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    new InputStreamOf(input),
                    StandardCharsets.UTF_8.name()
                )
            ).asString(),
            new IsEqual<>(input)
        );
    }

    @Test
    public void readsInputStreamWithCharsetDecoder() throws IOException {
        final String input =
            "InputStream with charset decoder on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            new TextOf(
                new ReaderOf(
                    new InputStreamOf(input),
                    StandardCharsets.UTF_8.newDecoder()
                )
            ).asString(),
            new IsEqual<>(input)
        );
    }
}
