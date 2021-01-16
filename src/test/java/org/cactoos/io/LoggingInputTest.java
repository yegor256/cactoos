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

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link LoggingInput}.
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
        new Assertion<>(
            "Can't log zero byte read from dead input",
            logger.toString(),
            Matchers.containsString("Read 0 byte(s) from dead input in")
        ).affirm();
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
        new Assertion<>(
            "Can't log one byte read from memory",
            logger.toString(),
            Matchers.containsString("Read 1 byte(s) from memory in")
        ).affirm();
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
        new Assertion<>(
            "Can't log 22 bytes read from memory",
            logger.toString(),
            Matchers.containsString("Read 22 byte(s) from memory in")
        ).affirm();
    }

    @Test
    void logReadFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger();
        new LengthOf(
            new LoggingInput(
                new ResourceOf("org/cactoos/large-text.txt"),
                "text file",
                logger
            )
        ).value();
        new Assertion<>(
            "Can't log 74536 bytes read from text file",
            logger.toString(),
            Matchers.allOf(
                Matchers.not(
                    Matchers.containsString("Read 16384 byte(s) from text file")
                ),
                Matchers.containsString("Read 74536 byte(s) from text file in"),
                Matchers.containsString("Closed input stream from text file")
            )
        ).affirm();
    }

    @Test
    void logAllFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger(Level.WARNING);
        new LengthOf(
            new LoggingInput(
                new ResourceOf("org/cactoos/large-text.txt"),
                "text file",
                logger
            )
        ).value();
        new Assertion<>(
            "Can't log all read and close operations from text file",
            logger.toString(),
            Matchers.allOf(
                Matchers.containsString("Read 16384 byte(s) from text file"),
                Matchers.containsString("Read 32768 byte(s) from text file"),
                Matchers.containsString("Read 49152 byte(s) from text file"),
                Matchers.containsString("Read 65536 byte(s) from text file"),
                Matchers.containsString("Read 74536 byte(s) from text file"),
                Matchers.containsString("Closed input stream from text file")
            )
        ).affirm();
    }

    @Test
    void logSkipFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger();
        new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        // @checkstyle MagicNumber (1 line)
        ).stream().skip(100);
        new Assertion<>(
            "Can't log skip from text file",
            logger.toString(),
            Matchers.containsString("Skipped 100 byte(s) from text file.")
        ).affirm();
    }

    @Test
    void logAvailableFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger();
        new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        ).stream().available();
        new Assertion<>(
            "Can't log avaliable byte(s) from text file",
            logger.toString(),
            Matchers.containsString(
                "There is(are) 74536 byte(s) available from text file"
            )
        ).affirm();
    }

    @Test
    void logResetFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger();
        final InputStream input = new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        ).stream();
        // @checkstyle MagicNumber (1 line)
        input.mark(150);
        input.reset();
        new Assertion<>(
            "Can't log mark and reset from text file",
            logger.toString(),
            Matchers.allOf(
                Matchers.containsString("Marked position 150 from text file"),
                Matchers.containsString("Reset input stream from text file")
            )
        ).affirm();
    }

    @Test
    void logMarkSupportedFromLargeTextFile() throws Exception {
        final Logger logger = new FakeLogger();
        new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        ).stream().markSupported();
        new Assertion<>(
            "Can't log mark and reset are not supported from text file",
            logger.toString(),
            Matchers.containsString(
                "Mark and reset are supported from text file"
            )
        ).affirm();
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
            new Assertion<>(
                "",
                handler.toString(),
                Matchers.containsString("Read 8 byte(s)")
            ).affirm();
        } finally {
            logger.removeHandler(handler);
        }
    }

}
