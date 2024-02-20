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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.scalar.LengthOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use {@link Path} as
 * an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (120 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeInputFromPathTest {

    @Test
    void copiesFromPathToPath(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("copytest11.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("copytest12.txt").toFile();
        new LengthOf(
            new TeeInput(input.toPath(), output.toPath())
        ).value();
        new Assertion<>(
            "Must copy from input path to output path",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }

    @Test
    void copiesFromPathToFile(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("copytest21.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("copytest22.txt").toFile();
        new LengthOf(
            new TeeInput(input.toPath(), output)
        ).value();
        new Assertion<>(
            "Must copy from input path to output file",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }

    @Test
    void copiesFromPathToOutput(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ output #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("copytest31.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("copytest32.txt").toFile();
        new LengthOf(
            new TeeInput(input.toPath(), new OutputTo(output))
        ).value();
        new Assertion<>(
            "Must copy from input path to output",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }
}
