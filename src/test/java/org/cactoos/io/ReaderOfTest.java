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
        MatcherAssert.assertThat(
            "Must read empty string",
            new TextOf(new ReaderOf("")),
            new IsText("")
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
        MatcherAssert.assertThat(
            "Must read chars var args with charset",
            new TextOf(
                new ReaderOf(
                    "char array on äÄ üÜ öÖ ß жш".toCharArray(),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText("char array on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsCharArrayWithCharsetByName() {
        MatcherAssert.assertThat(
            "Must read chars array with charset",
            new TextOf(
                new ReaderOf(
                    "char array with charset on äÄ üÜ öÖ ß жш".toCharArray(),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText("char array with charset on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsByteArray() {
        MatcherAssert.assertThat(
            "Must read bytes array",
            new TextOf(
                new ReaderOf(
                    "byte array on äÄ üÜ öÖ ß жш".getBytes(StandardCharsets.UTF_8)
                )
            ),
            new IsText("byte array on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsByteArrayWithCharset() {
        MatcherAssert.assertThat(
            "Must read bytes array with charset",
            new TextOf(
                new ReaderOf(
                    "byte array with charset on äÄ üÜ öÖ ß жш".getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText("byte array with charset on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsByteArrayWithCharsetByName() {
        MatcherAssert.assertThat(
            "Must read bytes array with charset by name",
            new TextOf(
                new ReaderOf(
                    "bte array with charset by name on äÄ üÜ öÖ ß жш".getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText("bte array with charset by name on äÄ üÜ öÖ ß жш")
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
        MatcherAssert.assertThat(
            "Must read from bytes",
            new TextOf(new ReaderOf(new BytesOf("Bytes on äÄ üÜ öÖ ß жш"))),
            new IsText("Bytes on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsText() {
        MatcherAssert.assertThat(
            "Must read from text",
            new TextOf(new ReaderOf(new TextOf("Text on äÄ üÜ öÖ ß жш"))),
            new IsText("Text on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsTextWithCharset() {
        MatcherAssert.assertThat(
            "Must read from text with charset",
            new TextOf(
                new ReaderOf(
                    new TextOf("Text with charset on äÄ üÜ öÖ ß жш"),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText("Text with charset on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsTextWithCharsetByName() {
        MatcherAssert.assertThat(
            "Must read from text with charset by name",
            new TextOf(
                new ReaderOf(
                    new TextOf("Text with charset by name on äÄ üÜ öÖ ß жш"),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText("Text with charset by name on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsCharSequence() {
        MatcherAssert.assertThat(
            "Must read from char sequence",
            new TextOf(new ReaderOf("char sequence on äÄ üÜ öÖ ß жш")),
            new IsText("char sequence on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsCharSequenceWithCharset() {
        MatcherAssert.assertThat(
            "Must read from char sequence with charset",
            new TextOf(
                new ReaderOf(
                    "char sequence with charset on äÄ üÜ öÖ ß жш",
                    StandardCharsets.UTF_8
                )
            ),
            new IsText("char sequence with charset on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsCharSequenceWithCharsetByName() {
        MatcherAssert.assertThat(
            "Must read from char sequence with charset by name",
            new TextOf(
                new ReaderOf(
                    "char sequence with charset by name on äÄ üÜ öÖ ß жш",
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText("char sequence with charset by name on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsInput() {
        MatcherAssert.assertThat(
            "Must read from input",
            new TextOf(new ReaderOf(new InputOf("Input on äÄ üÜ öÖ ß жш"))),
            new IsText("Input on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsInputWithCharset() {
        MatcherAssert.assertThat(
            "Must read from input with charset",
            new TextOf(
                new ReaderOf(
                    new InputOf("Input with charset on äÄ üÜ öÖ ß жш"),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText("Input with charset on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsInputWithCharsetByName() {
        MatcherAssert.assertThat(
            "Must read from input with charset by name",
            new TextOf(
                new ReaderOf(
                    new InputOf("Input with charset by name on äÄ üÜ öÖ ß жш"),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText("Input with charset by name on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsInputWithCharsetDecoder() {
        MatcherAssert.assertThat(
            "Must read from input with charset decoder",
            new TextOf(
                new ReaderOf(
                    new InputOf("Input with charset decoder on äÄ üÜ öÖ ß жш"),
                    StandardCharsets.UTF_8.newDecoder()
                )
            ),
            new IsText("Input with charset decoder on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsInputStream() {
        MatcherAssert.assertThat(
            "Must read from stream",
            new TextOf(new ReaderOf(new InputStreamOf("InputStream on äÄ üÜ öÖ ß жш"))),
            new IsText("InputStream on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsInputStreamWithCharset() {
        MatcherAssert.assertThat(
            "Must read from stream with charset",
            new TextOf(
                new ReaderOf(
                    new InputStreamOf("InputStream with charset on äÄ üÜ öÖ ß жш"),
                    StandardCharsets.UTF_8
                )
            ),
            new IsText("InputStream with charset on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsInputStreamWithCharsetByName() throws Exception {
        MatcherAssert.assertThat(
            "Must read from stream with charset by name",
            new TextOf(
                new ReaderOf(
                    new InputStreamOf("InputStream with charset by name on äÄ üÜ öÖ ß жш"),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText("InputStream with charset by name on äÄ üÜ öÖ ß жш")
        );
    }

    @Test
    void readsInputStreamWithCharsetDecoder() {
        MatcherAssert.assertThat(
            "Must read from stream with charset decoder",
            new TextOf(
                new ReaderOf(
                    new InputStreamOf("InputStream with charset decoder on äÄ üÜ öÖ ß жш"),
                    StandardCharsets.UTF_8.newDecoder()
                )
            ),
            new IsText("InputStream with charset decoder on äÄ üÜ öÖ ß жш")
        );
    }
}
