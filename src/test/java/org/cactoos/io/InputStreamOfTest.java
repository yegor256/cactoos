/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link InputStreamOf}.
 *
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class InputStreamOfTest {

    @Test
    void readsSimpleFileContent(@TempDir final Path wdir) throws IOException {
        final Path temp = wdir.resolve("cactoos-1.txt-1");
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        new Assertion<>(
            "Must read file content",
            new InputOf(new InputStreamOf(temp)),
            new HasContent(content)
        ).affirm();
    }

    @Test
    void readsFromReader() {
        final String content = "Hello, дорогой товарищ!";
        new Assertion<>(
            "Must read from reader",
            new InputOf(new InputStreamOf(new StringReader(content))),
            new HasContent(content)
        ).affirm();
    }

    @Test
    void readsFromReaderThroughSmallBuffer() {
        final String content = "Hello, صديق!";
        new Assertion<>(
            "Must read from reader through small buffer",
            new InputOf(new InputStreamOf(new StringReader(content), 1)),
            new HasContent(content)
        ).affirm();
    }

    @Test
    void makesDataAvailable() throws IOException {
        final String content = "Hello,חבר!";
        new Assertion<>(
            "Must show that data is available",
            new InputStreamOf(content).available(),
            new Satisfies<>(x -> x > 0)
        ).affirm();
    }

    @Test
    void readsFileContent(@TempDir final Path wdir) throws Exception {
        final File file = wdir.resolve("readFileContent.txt-2").toFile();
        final String content = "Content in a file";
        new LengthOf(
            new TeeInput(content, file)
        ).value();
        new Assertion<>(
            "Must read from file",
            new TextOf(new InputStreamOf(file)),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsBytes() {
        final String content = "Bytes content";
        new Assertion<>(
            "Must read from bytes",
            new TextOf(new InputStreamOf(new BytesOf(content))),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsBytesArray() throws Exception {
        final String content = "Bytes array content";
        final byte[] bytes = new BytesOf(content).asBytes();
        new Assertion<>(
            "Must read from byte array",
            new TextOf(new InputStreamOf(bytes)),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsText() {
        final String content = "Text content";
        new Assertion<>(
            "Must read from text",
            new TextOf(new InputStreamOf(new TextOf(content))),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsFromUri(@TempDir final Path wdir) throws Exception {
        final String content = "Content for reading through URI";
        final File file = wdir.resolve("readFromUri.txt-3").toFile();
        new LengthOf(
            new TeeInput(content, file)
        ).value();
        new Assertion<>(
            "Must read from URI",
            new TextOf(new InputStreamOf(file.toURI())),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsFromUrl(@TempDir final Path wdir) throws Exception {
        final String content = "Content for reading through URL";
        final File file = wdir.resolve("readFromUrl.txt-4").toFile();
        new LengthOf(
            new TeeInput(content, file)
        ).value();
        new Assertion<>(
            "Must read from URL",
            new TextOf(new InputStreamOf(file.toURI().toURL())),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsFromReaderWithMax() {
        final String content = "Reading with charset name and buffer size";
        final int max = 3;
        new Assertion<>(
            "Must read from reader with charset name and buffer size",
            new TextOf(
                new InputStreamOf(
                    new StringReader(content),
                    StandardCharsets.UTF_8.name(),
                    max
                )
            ),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsFromReaderWithCharsetWithMax() {
        final String content = "Reading with charset and buffer size";
        new Assertion<>(
            "Must read from reader with charset and buffer size",
            new TextOf(
                new InputStreamOf(
                    new StringReader(content),
                    StandardCharsets.UTF_8,
                    1
                )
            ),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsFromReaderWithCharset() {
        final String content = "Content for reading with charset";
        new Assertion<>(
            "Must read from reader with charset name",
            new TextOf(
                new InputStreamOf(
                    new StringReader(content),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsFromTextWithCharset(@TempDir final Path wdir) throws Exception {
        final File file = wdir.resolve("readTextWithCharset.txt-5").toFile();
        final String content = "Content for reading text with charset";
        new LengthOf(
            new TeeInput(content, file)
        ).value();
        new Assertion<>(
            "Must read from text with charset",
            new TextOf(
                new InputStreamOf(
                    new TextOf(file),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsFromCharSequenceWithCharsetName() {
        final String content = "Simple content";
        new Assertion<>(
            "Must read from char sequence with charset name",
            new TextOf(
                new InputStreamOf(
                    content,
                    StandardCharsets.UTF_8.name()
                )
            ),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsFromCharSequenceWithCharset() {
        final String content = "Another simple content";
        new Assertion<>(
            "Must read from char sequence with charset",
            new TextOf(
                new InputStreamOf(
                    content,
                    StandardCharsets.UTF_8
                )
            ),
            new IsText(content)
        ).affirm();
    }
}
