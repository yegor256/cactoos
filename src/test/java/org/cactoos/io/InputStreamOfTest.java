/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link InputStreamOf}.
 *
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class InputStreamOfTest {
    /**
     * Temporary files and folders generator.
     */
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void readsSimpleFileContent() throws IOException {
        final Path temp = this.folder.newFile("cactoos-1.txt-1")
            .toPath();
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        new Assertion<>(
            "Must read file content",
            new InputOf(new InputStreamOf(temp)),
            new HasContent(content)
        ).affirm();
    }

    @Test
    public void readsFromReader() {
        final String content = "Hello, дорогой товарищ!";
        new Assertion<>(
            "Must read from reader",
            new InputOf(new InputStreamOf(new StringReader(content))),
            new HasContent(content)
        ).affirm();
    }

    @Test
    public void readsFromReaderThroughSmallBuffer() {
        final String content = "Hello, صديق!";
        new Assertion<>(
            "Must read from reader through small buffer",
            new InputOf(new InputStreamOf(new StringReader(content), 1)),
            new HasContent(content)
        ).affirm();
    }

    @Test
    public void makesDataAvailable() throws IOException {
        final String content = "Hello,חבר!";
        new Assertion<>(
            "Must show that data is available",
            new InputStreamOf(content).available(),
            new Satisfies<>(x -> x > 0)
        ).affirm();
    }

    @Test
    public void readsFileContent() throws Exception {
        final File file = this.folder.newFile("readFileContent.txt-2");
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
    public void readsBytes() {
        final String content = "Bytes content";
        new Assertion<>(
            "Must read from bytes",
            new TextOf(new InputStreamOf(new BytesOf(content))),
            new IsText(content)
        ).affirm();
    }

    @Test
    public void readsBytesArray() throws Exception {
        final String content = "Bytes array content";
        final byte[] bytes = new BytesOf(content).asBytes();
        new Assertion<>(
            "Must read from byte array",
            new TextOf(new InputStreamOf(bytes)),
            new IsText(content)
        ).affirm();
    }

    @Test
    public void readsText() {
        final String content = "Text content";
        new Assertion<>(
            "Must read from text",
            new TextOf(new InputStreamOf(new TextOf(content))),
            new IsText(content)
        ).affirm();
    }

    @Test
    public void readsFromUri() throws Exception {
        final String content = "Content for reading through URI";
        final File file = this.folder.newFile("readFromUri.txt-3");
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
    public void readsFromUrl() throws Exception {
        final String content = "Content for reading through URL";
        final File file = this.folder.newFile("readFromUrl.txt-4");
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
    public void readsFromReaderWithMax() {
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
    public void readsFromReaderWithCharsetWithMax() {
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
    public void readsFromReaderWithCharset() {
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
    public void readsFromTextWithCharset() throws Exception {
        final File file = this.folder.newFile("readTextWithCharset.txt-5");
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
    public void readsFromCharSequenceWithCharsetName() {
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
    public void readsFromCharSequenceWithCharset() {
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
