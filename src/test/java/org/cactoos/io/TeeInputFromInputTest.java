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
 * {@link org.cactoos.Input} as an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (200 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (200 lines)
 */
public final class TeeInputFromInputTest {

    /**
     * Temporary files generator.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void copiesFromInputToPath() throws Exception {
        final String input = "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                output.toPath()
            )
        ).value();
        new Assertion<>(
            "Must copy from input to the output path",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromInputToFile() throws Exception {
        final String input = "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                output
            )
        ).value();
        new Assertion<>(
            "Must copy from input to the output file",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromInputToWriter() throws Exception {
        final String input = "Hello, товарищ write #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output)
            )
        ).value();
        new Assertion<>(
            "Must copy from input to the output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromInputWithSizeToWriter() throws Exception {
        final String input = "Hello, товарищ writer #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output),
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from input with size to the output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromInputWithCharsetToWriter() throws Exception {
        final String input = "Hello, товарищ writer #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output),
                StandardCharsets.UTF_8
            )
        ).value();
        new Assertion<>(
            "Must copy from input with charset to output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromInputWithCharsetAndSizeToWriter() throws Exception {
        final String input = "Hello, товарищ writer #4 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output),
                StandardCharsets.UTF_8,
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from input with charset and size to output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromInputWithCharsetByNameToWriter() throws Exception {
        final String input = "Hello, товарищ writer #5 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output),
                StandardCharsets.UTF_8.name()
            )
        ).value();
        new Assertion<>(
            "Must copy from input with charset by name to output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromInputWithCharsetByNameAndSizeToWriter()
        throws Exception {
        final String input = "Hello, товарищ writer #6 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output),
                StandardCharsets.UTF_8.name(),
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from input with charset by name and size to output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }
}
