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

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPOutputStream;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link GzipOutput}.
 * @checkstyle JavadocMethodCheck (500 lines)
 * @since 0.29
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class GzipOutputTest {

    @Test
    void writeToGzipOutput() throws Exception {
        final String content = "Hello!";
        final ByteArrayOutputStream expected = new ByteArrayOutputStream();
        try (
            Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                    new GZIPOutputStream(expected)
                )
            )
        ) {
            writer.write(content);
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (OutputStream output = new GzipOutput(
            new OutputTo(baos)
        ).stream()
        ) {
            new LengthOf(
                new TeeInput(
                    content,
                    new OutputTo(output)
                )
            ).value();
        }
        new Assertion<>(
            "Can't write to a gzip output",
            baos.toByteArray(),
            new IsEqual<>(expected.toByteArray())
        ).affirm();
    }

    @Test
    void writeToClosedGzipOutput(@TempDir final Path wdir) throws Exception {
        final OutputStream stream =
            Files.newOutputStream(
                wdir.resolve("cactoos.txt")
            );
        stream.close();
        new Assertion<>(
            "Cann't write to closed stream",
            () -> new LengthOf(
                new TeeInput(
                    "Hello!",
                    new GzipOutput(new OutputTo(stream))
                )
            ).value(),
            new Throws<>(IOException.class)
        ).affirm();
    }
}
