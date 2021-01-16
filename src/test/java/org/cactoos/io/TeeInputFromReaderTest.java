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
import java.nio.charset.StandardCharsets;
import org.cactoos.scalar.LengthOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use
 * {@link java.io.Reader} as an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (400 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (400 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class TeeInputFromReaderTest {

    /**
     * Temporary files generator.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void copiesFromReaderToFile() throws Exception {
        final String input =
            "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(new ReaderOf(input), output)
        ).value();
        new Assertion<>(
            "Must copy from reader to file.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithSizeToFile() throws Exception {
        final String input =
            "Hello, товарищ file #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                output,
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with size to file.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetToFile() throws Exception {
        final String input =
            "Hello, товарищ file #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                output,
                StandardCharsets.UTF_8
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset to file.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetAndSizeToFile() throws Exception {
        final String input =
            "Hello, товарищ file #4 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                output,
                StandardCharsets.UTF_8,
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset and size to file.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetByNameToFile() throws Exception {
        final String input =
            "Hello, товарищ file #5 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                output,
                StandardCharsets.UTF_8.name()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset by name to file.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetByNameAndSizeToFile()
        throws Exception {
        final String input =
            "Hello, товарищ file #6 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                output,
                StandardCharsets.UTF_8.name(),
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset by name and size to file.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderToPath() throws Exception {
        final String input =
            "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                output.toPath()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader to path.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithSizeToPath() throws Exception {
        final String input =
            "Hello, товарищ path #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                output.toPath(),
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with size to path",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetToPath() throws Exception {
        final String input =
            "Hello, товарищ path #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                output.toPath(),
                StandardCharsets.UTF_8
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset to path.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetAndSizeToPath() throws Exception {
        final String input =
            "Hello, товарищ path #4 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                output.toPath(),
                StandardCharsets.UTF_8,
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset and size to path.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetByNameToPath() throws Exception {
        final String input =
            "Hello, товарищ path #5 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                output.toPath(),
                StandardCharsets.UTF_8.name()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset by name to path.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetByNameAndSizeToPath()
        throws Exception {
        final String input =
            "Hello, товарищ path #6 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                output.toPath(),
                StandardCharsets.UTF_8.name(),
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset by name and size to path.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderToOutput() throws Exception {
        final String input =
            "Hello, товарищ output #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output)
            )
        ).value();
        new Assertion<>(
            "Must copy from reader to output.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithSizeToOutput() throws Exception {
        final String input =
            "Hello, товарищ output #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output),
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with size to output.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetToOutput() throws Exception {
        final String input =
            "Hello, товарищ output #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset to output.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetAndSizeToOutput()
        throws Exception {
        final String input =
            "Hello, товарищ output #4 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8,
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset and size to output.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetByNameToOutput() throws Exception {
        final String input =
            "Hello, товарищ output #5 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8.name()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset by name to output.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromReaderWithCharsetByNameAndSizeToOutput()
        throws Exception {
        final String input =
            "Hello, товарищ output #6 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8.name(),
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from reader with charset by name and size to output.",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }
}
