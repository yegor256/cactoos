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
import java.nio.file.Files;

import org.cactoos.text.TextOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.InputHasContent;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use
 * {@link org.cactoos.Text} as an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (215 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (215 lines)
 */
public final class TeeInputFromTextTest {

    /**
     * Temporary files generator.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void copiesFromTextToPath() throws IOException {
        final String input =
            "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        Files.write(
                output.toPath(),
                input.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "text must be copied to the path",
            new TeeInput(
                new TextOf(input),
                output.toPath()
            ),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromTextWithCharsetToPath() throws IOException {
        final String input =
            "Hello, товарищ path #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        Files.write(
                output.toPath(),
                input.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "text must be copied to the path with UTF_8 charset",
            new TeeInput(
                new TextOf(input),
                output.toPath(),
                StandardCharsets.UTF_8
            ),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromTextWithCharsetByNameToPath() throws IOException {
        final String input =
            "Hello, товарищ path #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        Files.write(
                output.toPath(),
                input.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "text must be copied to the path with UTF_8 charset's name",
            new TeeInput(
                new TextOf(input),
                output.toPath(),
                StandardCharsets.UTF_8.name()
            ),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromTextToFile() throws IOException {
        final String input =
            "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        Files.write(
                output.toPath(),
                input.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "text must be copied to the file",
            new TeeInput(
                new TextOf(input),
                output
            ),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromTextWithCharsetToFile() throws IOException {
        final String input =
            "Hello, товарищ file #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        Files.write(
                output.toPath(),
                input.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "text must be copied to the file with UTF_8 charset",
            new TeeInput(
                new TextOf(input),
                output,
                StandardCharsets.UTF_8
            ),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromTextWithCharsetByNameToFile() throws IOException {
        final String input =
            "Hello, товарищ file #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        Files.write(
                output.toPath(),
                input.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "text must be copied to the file with UTF_8 charset's name",
            new TeeInput(
                new TextOf(input),
                output,
                StandardCharsets.UTF_8.name()
            ),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromTextToOutput() throws IOException {
        final String input =
            "Hello, товарищ output #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        Files.write(
                output.toPath(),
                input.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "text must be copied to the output",
            new TeeInput(
                new TextOf(input),
                new OutputTo(output)
            ),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromTextWithCharsetToOutput() throws IOException {
        final String input =
            "Hello, товарищ output #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        Files.write(
                output.toPath(),
                input.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "text must be copied to the output with UTF_8 charset",
            new TeeInput(
                new TextOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8
            ),
            new InputHasContent(input)
        ).affirm();
    }

    @Test
    public void copiesFromTextWithCharsetByNameToOutput() throws IOException {
        final String input =
            "Hello, товарищ output #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        Files.write(
                output.toPath(),
                input.getBytes(StandardCharsets.UTF_8)
        );
        new Assertion<>(
            "text must be copied to the output with UTF_8 charset's name",
            new TeeInput(
                new TextOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8.name()
            ),
            new InputHasContent(input)
        ).affirm();
    }
}
