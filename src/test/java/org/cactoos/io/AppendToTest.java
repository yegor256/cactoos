/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.cactoos.text.Concatenated;
import org.cactoos.text.Randomized;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link AppendTo}.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class AppendToTest {

    /**
     * Ensures that AppendTo is failing on a negative predicate result.
     */
    @Test
    void failsIfFileDoesNotExist() throws Exception {
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
     * @param wdir TempDir to work in
     * @throws Exception if fails
     * @todo #1586:2h Tests failed on Windows due to not closed streams.
     *  It will be good to have feature to autoclose streams
     */
    @Test
    void appendsToFile(@TempDir final Path wdir) throws Exception {
        final File source = wdir.resolve("apptest.txt").toFile();
        final String first = "abdcd";
        try (OutputStream out = new OutputTo(source).stream()) {
            out.write(first.getBytes());
            out.flush();
        }
        final String second = "efgh";
        try (OutputStream out = new AppendTo(source).stream()) {
            out.write(second.getBytes());
            out.flush();
        }
        new Assertion<>(
            "Does not contain expected text",
            new InputOf(source),
            new HasContent(new Concatenated(first, second))
        ).affirm();
    }

    /**
     * Ensures that AppendTo is appending unicode text to a given file.
     * @param wdir TempDir to work in
     * @throws Exception if fails
     */
    @Test
    void appendsUnicodeToFile(@TempDir final Path wdir) throws Exception {
        final File source = wdir.resolve("appunitest.txt").toFile();
        final String first = "Hello, товарищ output #3 äÄ ";
        try (OutputStream out = new OutputTo(source)
            .stream()) {
            out.write(first.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
        final String second = "#4 äÄ üÜ öÖ and ß";
        try (OutputStream out = new AppendTo(source)
            .stream()) {
            out.write(second.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
        new Assertion<>(
            "Can't find expected unicode text content",
            new InputOf(source),
            new HasContent(new Concatenated(first, second))
        ).affirm();
    }
}
