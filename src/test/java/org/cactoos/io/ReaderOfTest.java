/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link ReaderOf}.
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class ReaderOfTest {

    @Test
    void readsEmpty() {
        final String empty = "";
        MatcherAssert.assertThat(
            "Must read empty string",
            new TextOf(new ReaderOf(empty)),
            new IsText(empty)
        );
    }

    @Test
    void readsCharVarArg() {
        MatcherAssert.assertThat(
            "Must read chars var args",
            new TextOf(new ReaderOf('a', 'b', 'c')),
            new IsText("abc")
        );
    }

    @Test
    void readsCharArrayWithCharset() {
        final String message =
            "char array on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read chars var args with charset",
            new TextOf(
                new ReaderOf(
                    message.toCharArray(),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(message)
        );
    }

    @Test
    void readsCharArrayWithCharsetByName() {
        final String message =
            "char array with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read chars array with charset",
            new TextOf(
                new ReaderOf(
                    message.toCharArray(),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(message)
        );
    }

    @Test
    void readsByteArray() {
        final String message =
            "byte array on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read bytes array",
            new TextOf(
                new ReaderOf(
                    message.getBytes(StandardCharsets.UTF_8)
                )
            ),
            new IsText(message)
        );
    }

    @Test
    void readsByteArrayWithCharset() {
        final String message =
            "byte array with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read bytes array with charset",
            new TextOf(
                new ReaderOf(
                    message.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(message)
        );
    }

    @Test
    void readsByteArrayWithCharsetByName() {
        final String message =
            "bte array with charset by name on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read bytes array with charset by name",
            new TextOf(
                new ReaderOf(
                    message.getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(message)
        );
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
        MatcherAssert.assertThat(
            "Must read from path",
            new TextOf(new ReaderOf(input)),
            new IsText(message)
        );
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
        MatcherAssert.assertThat(
            "Must read from file",
            new TextOf(new ReaderOf(input)),
            new IsText(message)
        );
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
        MatcherAssert.assertThat(
            "Must read from url",
            new TextOf(
                new ReaderOf(
                    input
                        .toURI()
                        .toURL()
                )
            ),
            new IsText(message)
        );
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
        MatcherAssert.assertThat(
            "Must read from uri",
            new TextOf(new ReaderOf(input.toURI())),
            new IsText(message)
        );
    }

    @Test
    void readsBytes() {
        final String input =
            "Bytes on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from bytes",
            new TextOf(new ReaderOf(new BytesOf(input))),
            new IsText(input)
        );
    }

    @Test
    void readsText() {
        final String input =
            "Text on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from text",
            new TextOf(new ReaderOf(new TextOf(input))),
            new IsText(input)
        );
    }

    @Test
    void readsTextWithCharset() {
        final String input =
            "Text with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from text with charset",
            new TextOf(
                new ReaderOf(
                    new TextOf(input),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(input)
        );
    }

    @Test
    void readsTextWithCharsetByName() {
        final String input =
            "Text with charset by name on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from text with charset by name",
            new TextOf(
                new ReaderOf(
                    new TextOf(input),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(input)
        );
    }

    @Test
    void readsCharSequence() {
        final String input =
            "char sequence on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from char sequence",
            new TextOf(new ReaderOf(input)),
            new IsText(input)
        );
    }

    @Test
    void readsCharSequenceWithCharset() {
        final String input =
            "char sequence with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from char sequence with charset",
            new TextOf(
                new ReaderOf(
                    input,
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(input)
        );
    }

    @Test
    void readsCharSequenceWithCharsetByName() {
        final String input =
            "char sequence with charset by name on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from char sequence with charset by name",
            new TextOf(
                new ReaderOf(
                    input,
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(input)
        );
    }

    @Test
    void readsInput() {
        final String input =
            "Input on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from input",
            new TextOf(new ReaderOf(new InputOf(input))),
            new IsText(input)
        );
    }

    @Test
    void readsInputWithCharset() {
        final String input =
            "Input with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from input with charset",
            new TextOf(
                new ReaderOf(
                    new InputOf(input),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(input)
        );
    }

    @Test
    void readsInputWithCharsetByName() {
        final String input =
            "Input with charset by name on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from input with charset by name",
            new TextOf(
                new ReaderOf(
                    new InputOf(input),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(input)
        );
    }

    @Test
    void readsInputWithCharsetDecoder() {
        final String input =
            "Input with charset decoder on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from input with charset decoder",
            new TextOf(
                new ReaderOf(
                    new InputOf(input),
                    StandardCharsets.UTF_8.newDecoder()
                )
            ),
            new IsText(input)
        );
    }

    @Test
    void readsInputStream() {
        final String input =
            "InputStream on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from stream",
            new TextOf(new ReaderOf(new InputStreamOf(input))),
            new IsText(input)
        );
    }

    @Test
    void readsInputStreamWithCharset() {
        final String input =
            "InputStream with charset on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from stream with charset",
            new TextOf(
                new ReaderOf(
                    new InputStreamOf(input),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(input)
        );
    }

    @Test
    void readsInputStreamWithCharsetByName() throws Exception {
        final String input =
            "InputStream with charset by name on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from stream with charset by name",
            new TextOf(
                new ReaderOf(
                    new InputStreamOf(input),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(input)
        );
    }

    @Test
    void readsInputStreamWithCharsetDecoder() {
        final String input =
            "InputStream with charset decoder on äÄ üÜ öÖ ß жш";
        MatcherAssert.assertThat(
            "Must read from stream with charset decoder",
            new TextOf(
                new ReaderOf(
                    new InputStreamOf(input),
                    StandardCharsets.UTF_8.newDecoder()
                )
            ),
            new IsText(input)
        );
    }
}
