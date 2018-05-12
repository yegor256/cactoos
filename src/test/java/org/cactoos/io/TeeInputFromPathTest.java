/*
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
import java.nio.file.Path;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.TeeInputHasResult;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use {@link Path} as
 * an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (120 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (120 lines)
 */
public final class TeeInputFromPathTest {

    /**
     * Temporary files generator.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void copiesFromPathToPath() throws IOException {
        final String message =
            "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File input = this.folder.newFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                input.toPath(),
                output.toPath()
            ),
            new TeeInputHasResult(
                message,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromPathToFile() throws IOException {
        final String message =
            "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File input = this.folder.newFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                input.toPath(),
                output
            ),
            new TeeInputHasResult(
                message,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromPathToOutput() throws IOException {
        final String message =
            "Hello, товарищ output #1 äÄ üÜ öÖ and ß";
        final File input = this.folder.newFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                input.toPath(),
                new OutputTo(output)
            ),
            new TeeInputHasResult(
                message,
                new TextOf(output)
            )
        );
    }
}
