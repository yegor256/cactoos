/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
import java.nio.file.NoSuchFileException;
import org.cactoos.text.Joined;
import org.cactoos.text.Randomized;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.InputHasContent;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link AppendTo}.
 *
 * @since 1.0
 * @checkstyle ClassDataAbstractionCouplingCheck (100 lines)
 */
public final class AppendToTest {

    /**
     * Temporary files and folders generator.
     */
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    /**
     * Ensures that AppendTo is failing on a negative predicate result.
     */
    @Test
    public void failsIfFileDoesNotExist() {
        final File source = new File(
            new Randomized(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'
            ).asString()
        );
        new Assertion<>(
            "Can't throw exception with proper message",
            () -> new AppendTo(source).stream(),
            new Throws<>(
                source.getPath(),
                NoSuchFileException.class
            )
        ).affirm();
    }

    /**
     * Ensures that AppendTo is appending to a given file.
     * @throws Exception if fails
     */
    @Test
    public void appendsToFile() throws Exception {
        final File source = this.folder.newFile();
        final String first = "abdcd";
        new OutputTo(source).stream().write(first.getBytes());
        final String second = "efgh";
        new AppendTo(source).stream().write(second.getBytes());
        new Assertion<>(
            "Does not contain expected text",
            new InputOf(source),
            new InputHasContent(new Joined("", first, second))
        ).affirm();
    }

    /**
     * Ensures that AppendTo is appending unicode text to a given file.
     * @throws Exception if fails
     */
    @Test
    public void appendsUnicodeToFile() throws Exception {
        final File source = this.folder.newFile();
        final String first = "Hello, товарищ output #3 äÄ ";
        new OutputTo(source)
            .stream()
            .write(first.getBytes(StandardCharsets.UTF_8));
        final String second = "#4 äÄ üÜ öÖ and ß";
        new AppendTo(source)
            .stream()
            .write(second.getBytes(StandardCharsets.UTF_8));
        new Assertion<>(
            "Can't find expected unicode text content",
            new InputOf(source),
            new InputHasContent(new Joined("", first, second))
        ).affirm();
    }
}
