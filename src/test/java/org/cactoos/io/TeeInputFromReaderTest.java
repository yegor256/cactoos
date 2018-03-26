/**
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
import org.cactoos.matchers.TeeInputHasResult;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use
 * {@link java.io.Reader} as an input.
 * @author Roman Proshin (roman@proshin.org)
 * @version $Id$
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
    public void copiesFromReaderToFile() throws IOException {
        final String input =
            "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithSizeToFile() throws IOException {
        final String input =
            "Hello, товарищ file #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output,
                input.length()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetToFile() throws IOException {
        final String input =
            "Hello, товарищ file #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output,
                StandardCharsets.UTF_8
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetAndSizeToFile() throws IOException {
        final String input =
            "Hello, товарищ file #4 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output,
                StandardCharsets.UTF_8,
                input.length()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetByNameToFile() throws IOException {
        final String input =
            "Hello, товарищ file #5 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output,
                StandardCharsets.UTF_8.name()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetByNameAndSizeToFile()
        throws IOException {
        final String input =
            "Hello, товарищ file #6 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output,
                StandardCharsets.UTF_8.name(),
                input.length()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderToPath() throws IOException {
        final String input =
            "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output.toPath()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithSizeToPath() throws IOException {
        final String input =
            "Hello, товарищ path #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output.toPath(),
                input.length()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetToPath() throws IOException {
        final String input =
            "Hello, товарищ path #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output.toPath(),
                StandardCharsets.UTF_8
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetAndSizeToPath() throws IOException {
        final String input =
            "Hello, товарищ path #4 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output.toPath(),
                StandardCharsets.UTF_8,
                input.length()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetByNameToPath() throws IOException {
        final String input =
            "Hello, товарищ path #5 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output.toPath(),
                StandardCharsets.UTF_8.name()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetByNameAndSizeToPath()
        throws IOException {
        final String input =
            "Hello, товарищ path #6 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                output.toPath(),
                StandardCharsets.UTF_8.name(),
                input.length()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderToOutput() throws IOException {
        final String input =
            "Hello, товарищ output #1 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output)
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithSizeToOutput() throws IOException {
        final String input =
            "Hello, товарищ output #2 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output),
                input.length()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetToOutput() throws IOException {
        final String input =
            "Hello, товарищ output #3 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetAndSizeToOutput()
        throws IOException {
        final String input =
            "Hello, товарищ output #4 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8,
                input.length()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetByNameToOutput() throws IOException {
        final String input =
            "Hello, товарищ output #5 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8.name()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetByNameAndSizeToOutput()
        throws IOException {
        final String input =
            "Hello, товарищ output #6 äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        MatcherAssert.assertThat(
            new TeeInput(
                new ReaderOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8.name(),
                input.length()
            ),
            new TeeInputHasResult(
                input,
                new TextOf(output)
            )
        );
    }
}
