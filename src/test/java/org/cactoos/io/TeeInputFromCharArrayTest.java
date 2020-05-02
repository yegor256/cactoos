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
import java.nio.charset.StandardCharsets;
import org.cactoos.scalar.LengthOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.InputHasContent;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use char array as an
 * input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (215 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (215 lines)
 */
public final class TeeInputFromCharArrayTest {

    /**
     * Temporary files generator.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void copiesFromCharArrayWithCharsetToFile() throws IOException {
        final String input =
            "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
          new TeeInput(input.toCharArray(), new OutputTo(output))
        ).intValue();
        new Assertion<>(
            "char array must be copied to the file with charset UTF_8",
            new InputOf(output),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromCharArrayWithCharsetByNameToFile()
        throws IOException {
        final String input =
            "Hello, товарищ file #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(input.toCharArray(), new OutputTo(output), StandardCharsets.UTF_8.name())
        ).intValue();
        new Assertion<>(
            "char array must be copied to the file with UTF_8 charset's name",
            new InputOf(output),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromCharArrayToOutput() throws IOException {
        final String input =
            "Hello, товарищ output #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(input.toCharArray(), new OutputTo(output), StandardCharsets.UTF_8.name())
        ).intValue();
        new Assertion<>(
            "char array must be copied to the output",
            new InputOf(output),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromCharArrayWithCharsetToOutput() throws IOException {
        final String input =
            "Hello, товарищ output #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(input.toCharArray(), new OutputTo(output), StandardCharsets.UTF_8.name())
        ).intValue();
        new Assertion<>(
            "char array must be copied to the output with UTF_8 charset",
            new InputOf(output),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromCharArrayWithCharsetByNameToOutput()
        throws IOException {
        final String input =
            "Hello, товарищ output #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(input.toCharArray(), new OutputTo(output), StandardCharsets.UTF_8.name())
        ).intValue();
        new Assertion<>(
            "char array must be copied to the output with UTF_8 charset's name",
            new InputOf(output),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromCharArrayToPath() throws IOException {
        final String input =
            "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(input.toCharArray(), new OutputTo(output), StandardCharsets.UTF_8.name())
        ).intValue();
        new Assertion<>(
            "char array must be copied to the path",
            new InputOf(output),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromCharArrayWithCharsetToPath() throws IOException {
        final String input =
            "Hello, товарищ path #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(input.toCharArray(), new OutputTo(output), StandardCharsets.UTF_8.name())
        ).intValue();
        new Assertion<>(
            "char array must be copied to the path with UTF_8 charset",
            new InputOf(output),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromCharArrayWithCharsetByNameToPath()
        throws IOException {
        final String input =
            "Hello, товарищ path #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new LengthOf(
            new TeeInput(input.toCharArray(), new OutputTo(output), StandardCharsets.UTF_8.name())
        ).intValue();
        new Assertion<>(
            "char array must be copied to the path with UTF_8 charset's name",
            new InputOf(output),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromCharArrayToFile() throws IOException {
        final File output = this.folder.newFile();
        final String input =
            "Hello, товарищ file äÄ üÜ öÖ and ß";
        new LengthOf(
            new TeeInput(input.toCharArray(), new OutputTo(output), StandardCharsets.UTF_8.name())
        ).intValue();
        new Assertion<>(
            "char array must be copied to the file",
            new InputOf(output),
            new InputHasContent(input)
        ).affirm();
    }
}
