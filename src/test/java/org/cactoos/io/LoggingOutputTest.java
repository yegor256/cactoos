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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.bytes.BytesOf;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link LoggingOutput}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.MoreThanOneLogger",
        "PMD.AvoidDuplicateLiterals"
    }
)
public final class LoggingOutputTest {
    /**
     * Temporary files and folders generator.
     */
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void logWriteZero() throws Exception {
        final Logger logger = new FakeLogger();
        new LengthOf(
            new TeeInput(
                new InputOf(""),
                new LoggingOutput(
                    () -> new ByteArrayOutputStream(),
                    "memory",
                    logger
                )
            )
        ).value();
        new Assertion<>(
            "Can't log zero byte written to memory",
            logger.toString(),
            Matchers.containsString("Written 0 byte(s) to memory in ")
        ).affirm();
    }

    @Test
    public void logWriteOneByte() throws Exception {
        final Logger logger = new FakeLogger();
        try (
            OutputStream out = new LoggingOutput(
                ByteArrayOutputStream::new,
                "memory",
                logger
            ).stream()
        ) {
            out.write(new BytesOf("a").asBytes());
        }
        new Assertion<>(
            "Can't log one byte written to memory",
            logger.toString(),
            Matchers.containsString("Written 1 byte(s) to memory in")
        ).affirm();
    }

    @Test
    public void logWriteText() throws Exception {
        final Logger logger = new FakeLogger();
        try (
            OutputStream out = new LoggingOutput(
                ByteArrayOutputStream::new,
                "memory",
                logger
            ).stream()
        ) {
            out.write(new BytesOf("Hello, товарищ!").asBytes());
        }
        new Assertion<>(
            "Can't log 22 bytes written to memory",
            logger.toString(),
            Matchers.containsString("Written 22 byte(s) to memory in")
        ).affirm();
    }

    @Test
    public void logWriteToLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger();
        final Path temp = this.folder.newFolder("ccts-1").toPath();
        final Path path = temp.resolve("x/y/z/file.txt");
        try (OutputStream output = new LoggingOutput(
            new OutputTo(path),
            "text file",
            logger
        ).stream()
        ) {
            new LengthOf(
                new TeeInput(
                    new ResourceOf("org/cactoos/large-text.txt"),
                    new OutputTo(output)
                )
            ).value();
        }
        new Assertion<>(
            "Can't log write and close operations to text file",
            logger.toString(),
            Matchers.allOf(
                Matchers.not(
                    Matchers.containsString(
                        "Written 16384 byte(s) to text file"
                    )
                ),
                Matchers.containsString("Written 74536 byte(s) to text file"),
                Matchers.containsString("Closed output stream from text file")
            )
        ).affirm();
    }

    @Test
    public void logAllWriteToLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger(Level.WARNING);
        final Path temp = this.folder.newFolder("ccts-2").toPath();
        final Path path = temp.resolve("a/b/c/file.txt");
        try (OutputStream output = new LoggingOutput(
            new OutputTo(path),
            "text file",
            logger
        ).stream()
        ) {
            new LengthOf(
                new TeeInput(
                    new ResourceOf("org/cactoos/large-text.txt"),
                    new OutputTo(output)
                )
            ).value();
        }
        new Assertion<>(
            "Can't log all write and close operations to text file",
            logger.toString(),
            Matchers.allOf(
                Matchers.containsString("Written 16384 byte(s) to text file"),
                Matchers.containsString("Written 32768 byte(s) to text file"),
                Matchers.containsString("Written 49152 byte(s) to text file"),
                Matchers.containsString("Written 65536 byte(s) to text file"),
                Matchers.containsString("Written 74536 byte(s) to text file"),
                Matchers.containsString("Closed output stream from text file")
            )
        ).affirm();
    }

}
