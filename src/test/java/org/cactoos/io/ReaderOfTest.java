/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link ReaderOf}.
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class ReaderOfTest {

    @Test
    void readsEmpty() {
        final String empty = "";
        new Assertion<>(
            "Must read empty string",
            new TextOf(new ReaderOf(empty)),
            new IsText(empty)
        ).affirm();
    }

    @Test
    void readsCharVarArg() throws Exception {
        new Assertion<>(
            "Must read chars var args",
            new TextOf(new ReaderOf('a', 'b', 'c')),
            new IsText("abc")
        ).affirm();
    }

    @Test
    void readsCharArrayWithCharset() throws Exception {
        final String message =
            "char array on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read chars var args with charset",
            new TextOf(
                new ReaderOf(
                    message.toCharArray(),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(message)
        ).affirm();
    }

    @Test
    void readsCharArrayWithCharsetByName() throws Exception {
        final String message =
            "char array with charset on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read chars array with charset",
            new TextOf(
                new ReaderOf(
                    message.toCharArray(),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(message)
        ).affirm();
    }

    @Test
    void readsByteArray() throws Exception {
        final String message =
            "byte array on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read bytes array",
            new TextOf(
                new ReaderOf(
                    message.getBytes(StandardCharsets.UTF_8)
                )
            ),
            new IsText(message)
        ).affirm();
    }

    @Test
    void readsByteArrayWithCharset() throws Exception {
        final String message =
            "byte array with charset on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read bytes array with charset",
            new TextOf(
                new ReaderOf(
                    message.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(message)
        ).affirm();
    }

    @Test
    void readsByteArrayWithCharsetByName() throws Exception {
        final String message =
            "bte array with charset by name on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read bytes array with charset by name",
            new TextOf(
                new ReaderOf(
                    message.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(message)
        ).affirm();
    }

    @Test
    void readsPath(@TempDir final Path wdir) throws Exception {
        final String message =
            "path on äÄ üÜ öÖ ß жш";
        final File input = wdir.resolve("reader1.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "Must read from path",
            new TextOf(new ReaderOf(input)),
            new IsText(message)
        ).affirm();
    }

    @Test
    void readsFile(@TempDir final Path wdir) throws Exception {
        final String message =
            "file on äÄ üÜ öÖ ß жш";
        final File input = wdir.resolve("reader2.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "Must read from file",
            new TextOf(new ReaderOf(input)),
            new IsText(message)
        ).affirm();
    }

    @Test
    void readsUrl(@TempDir final Path wdir) throws Exception {
        final String message =
            "URL on äÄ üÜ öÖ ß жш";
        final File input = wdir.resolve("reader3.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "Must read from url",
            new TextOf(
                new ReaderOf(
                    input
                        .toURI()
                        .toURL()
                )
            ),
            new IsText(message)
        ).affirm();
    }

    @Test
    void readsUri(@TempDir final Path wdir) throws Exception {
        final String message =
            "URI on äÄ üÜ öÖ ß жш";
        final File input = wdir.resolve("reader4.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "Must read from uri",
            new TextOf(new ReaderOf(input.toURI())),
            new IsText(message)
        ).affirm();
    }

    @Test
    void readsBytes() throws Exception {
        final String input =
            "Bytes on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from bytes",
            new TextOf(new ReaderOf(new BytesOf(input))),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsText() throws Exception {
        final String input =
            "Text on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from text",
            new TextOf(new ReaderOf(new TextOf(input))),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsTextWithCharset() throws Exception {
        final String input =
            "Text with charset on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from text with charset",
            new TextOf(
                new ReaderOf(
                    new TextOf(input),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsTextWithCharsetByName() throws Exception {
        final String input =
            "Text with charset by name on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from text with charset by name",
            new TextOf(
                new ReaderOf(
                    new TextOf(input),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsCharSequence() throws Exception {
        final String input =
            "char sequence on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from char sequence",
            new TextOf(new ReaderOf(input)),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsCharSequenceWithCharset() throws Exception {
        final String input =
            "char sequence with charset on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from char sequance with charset",
            new TextOf(
                new ReaderOf(
                    input,
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsCharSequenceWithCharsetByName() throws Exception {
        final String input =
            "char sequence with charset by name on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from char sequence with charset by name",
            new TextOf(
                new ReaderOf(
                    input,
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsInput() throws Exception {
        final String input =
            "Input on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from input",
            new TextOf(new ReaderOf(new InputOf(input))),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsInputWithCharset() throws Exception {
        final String input =
            "Input with charset on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from input with charset",
            new TextOf(
                new ReaderOf(
                    new InputOf(input),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsInputWithCharsetByName() throws Exception {
        final String input =
            "Input with charset by name on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from input with charset by name",
            new TextOf(
                new ReaderOf(
                    new InputOf(input),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsInputWithCharsetDecoder() throws Exception {
        final String input =
            "Input with charset decoder on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from input with charset decoder",
            new TextOf(
                new ReaderOf(
                    new InputOf(input),
                    StandardCharsets.UTF_8.newDecoder()
                )
            ),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsInputStream() throws Exception {
        final String input =
            "InputStream on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from stream",
            new TextOf(new ReaderOf(new InputStreamOf(input))),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsInputStreamWithCharset() throws Exception {
        final String input =
            "InputStream with charset on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from stream with charset",
            new TextOf(
                new ReaderOf(
                    new InputStreamOf(input),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsInputStreamWithCharsetByName() throws Exception {
        final String input =
            "InputStream with charset by name on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from stream with charset by name",
            new TextOf(
                new ReaderOf(
                    new InputStreamOf(input),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(input)
        ).affirm();
    }

    @Test
    void readsInputStreamWithCharsetDecoder() throws Exception {
        final String input =
            "InputStream with charset decoder on äÄ üÜ öÖ ß жш";
        new Assertion<>(
            "Must read from stream with charset decoder",
            new TextOf(
                new ReaderOf(
                    new InputStreamOf(input),
                    StandardCharsets.UTF_8.newDecoder()
                )
            ),
            new IsText(input)
        ).affirm();
    }
}
