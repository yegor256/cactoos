/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.bytes.BytesOf;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link LoggingOutput}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.MoreThanOneLogger",
    "PMD.AvoidDuplicateLiterals",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class LoggingOutputTest {

    @Test
    void logWriteZero() throws Exception {
        final Logger logger = new FakeLogger();
        new LengthOf(
            new TeeInput(
                new InputOf(""),
                new LoggingOutput(
                    ByteArrayOutputStream::new,
                    "memory",
                    logger
                )
            )
        ).value();
        new Assertion<>(
            "Must log write zero byte written to memory",
            logger.toString(),
            new StringContains("Written 0 byte(s) to memory in ")
        ).affirm();
    }

    @Test
    void logWriteOneByte() throws Exception {
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
            "Must log one byte written to memory",
            logger.toString(),
            new StringContains("Written 1 byte(s) to memory in")
        ).affirm();
    }

    @Test
    void logWriteText() throws Exception {
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
            "Must log 22 bytes written to memory",
            logger.toString(),
            new StringContains("Written 22 byte(s) to memory in")
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void logWriteToLargeTextFile(@TempDir final Path wdir) throws Exception {
        final Logger logger = new FakeLogger();
        final Path temp = wdir.resolve("ccts-1");
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
            "Must log write and close operations to text file",
            logger.toString(),
            new AllOf<>(
                new IsNot<>(
                    new StringContains(
                        "Written 16384 byte(s) to text file"
                    )
                ),
                new StringContains("Written 74536 byte(s) to text file"),
                new StringContains("Closed output stream from text file")
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void logAllWriteToLargeTextFile(@TempDir final Path wdir) throws Exception {
        final Logger logger = new FakeLogger(Level.WARNING);
        final Path temp = wdir.resolve("ccts-2");
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
            "Must log all write and close operations to text file",
            logger.toString(),
            new AllOf<>(
                new StringContains("Written 16384 byte(s) to text file"),
                new StringContains("Written 32768 byte(s) to text file"),
                new StringContains("Written 49152 byte(s) to text file"),
                new StringContains("Written 65536 byte(s) to text file"),
                new StringContains("Written 74536 byte(s) to text file"),
                new StringContains("Closed output stream from text file")
            )
        ).affirm();
    }

}
