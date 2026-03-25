/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link LoggingInput}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.UnnecessaryLocalRule"})
final class LoggingInputTest {

    @Test
    void logReadFromDeadInput() throws Exception {
        final Logger logger = new FakeLogger();
        new LengthOf(
            new LoggingInput(
                new DeadInput(),
                "dead input",
                logger
            )
        ).value();
        MatcherAssert.assertThat(
            "Must log zero byte read from dead input",
            new TextOf(logger.toString()),
            new HasString("Read 0 byte(s) from dead input in")
        );
    }

    @Test
    void logReadFromOneByte() throws Exception {
        final Logger logger = new FakeLogger();
        new LengthOf(
            new LoggingInput(
                new InputOf("a"),
                "memory",
                logger
            )
        ).value();
        MatcherAssert.assertThat(
            "Must log one byte read from memory",
            new TextOf(logger.toString()),
            new HasString("Read 1 byte(s) from memory in")
        );
    }

    @Test
    void logReadFromText() throws Exception {
        final Logger logger = new FakeLogger();
        new LengthOf(
            new LoggingInput(
                new InputOf("Hello, товарищ!"),
                "memory",
                logger
            )
        ).value();
        MatcherAssert.assertThat(
            "Must log 22 bytes read from memory",
            new TextOf(logger.toString()),
            new HasString("Read 22 byte(s) from memory in")
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void logReadFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger();
        new LengthOf(
            new LoggingInput(
                new ResourceOf("org/cactoos/large-text.txt"),
                "text file",
                logger
            )
        ).value();
        MatcherAssert.assertThat(
            "Must log 74536 bytes read from text file",
            new TextOf(logger.toString()),
            new AllOf<>(
                new IsNot<>(
                    new HasString("Read 16384 byte(s) from text file")
                ),
                new HasString("Read 74536 byte(s) from text file in"),
                new HasString("Closed input stream from text file")
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void logAllFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger(Level.WARNING);
        new LengthOf(
            new LoggingInput(
                new ResourceOf("org/cactoos/large-text.txt"),
                "text file",
                logger
            )
        ).value();
        MatcherAssert.assertThat(
            "Must log all read and close operations from text file",
            new TextOf(logger.toString()),
            new AllOf<>(
                new HasString("Read 16384 byte(s) from text file"),
                new HasString("Read 32768 byte(s) from text file"),
                new HasString("Read 49152 byte(s) from text file"),
                new HasString("Read 65536 byte(s) from text file"),
                new HasString("Read 74536 byte(s) from text file"),
                new HasString("Closed input stream from text file")
            )
        );
    }

    @Test
    void logSkipFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger();
        final long skipped = new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        ).stream().skip(100L);
        MatcherAssert.assertThat(
            String.format("Must log skip of %d bytes from text file", skipped),
            new TextOf(logger.toString()),
            new HasString("Skipped 100 byte(s) from text file.")
        );
    }

    @Test
    void logAvailableFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger();
        new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        ).stream().available();
        MatcherAssert.assertThat(
            "Must log available byte(s) from text file",
            new TextOf(logger.toString()),
            new HasString(
                "There is(are) 74536 byte(s) available from text file"
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void logResetFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger();
        try (InputStream input = new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        ).stream()) {
            input.mark(150);
            input.reset();
            MatcherAssert.assertThat(
                "Must log mark and reset from text file",
                new TextOf(logger.toString()),
                new AllOf<>(
                    new HasString("Marked position 150 from text file"),
                    new HasString("Reset input stream from text file")
                )
            );
        }
    }

    @Test
    void logMarkSupportedFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger();
        new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        ).stream().markSupported();
        MatcherAssert.assertThat(
            "Must log mark and reset are not supported from text file",
            new TextOf(logger.toString()),
            new HasString(
                "Mark and reset are supported from text file"
            )
        );
    }

    @Test
    void logIntoCreatedLogger() throws Exception {
        final FakeHandler handler = new FakeHandler();
        final String src = "my source";
        final Logger logger = Logger.getLogger(src);
        logger.addHandler(handler);
        try {
            new LengthOf(
                new LoggingInput(
                    new InputOf("Hi there"),
                    src
                )
            ).value();
            MatcherAssert.assertThat(
                "",
                new TextOf(handler.toString()),
                new HasString("Read 8 byte(s)")
            );
        } finally {
            logger.removeHandler(handler);
        }
    }

}
