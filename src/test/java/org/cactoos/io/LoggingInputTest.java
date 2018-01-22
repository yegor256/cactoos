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

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LoggingInput}.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.MoreThanOneLogger",
        "PMD.AvoidDuplicateLiterals"
    }
)
public final class LoggingInputTest {

    @Test
    public void logReadFromDeadInput() throws IOException {
        final Logger logger = new FakeLogger();
        new LengthOf(
            new LoggingInput(
                new DeadInput(),
                "dead input",
                logger
            )
        ).intValue();
        MatcherAssert.assertThat(
            "Can't log zero byte read from dead input",
            logger.toString(),
            Matchers.containsString("Read 0 byte(s) from dead input in")
        );
    }

    @Test
    public void logReadFromOneByte() throws IOException {
        final Logger logger = new FakeLogger();
        new LengthOf(
            new LoggingInput(
                new InputOf("a"),
                "memory",
                logger
            )
        ).intValue();
        MatcherAssert.assertThat(
            "Can't log one byte read from memory",
            logger.toString(),
            Matchers.containsString("Read 1 byte(s) from memory in")
        );
    }

    @Test
    public void logReadFromText() throws IOException {
        final Logger logger = new FakeLogger();
        new LengthOf(
            new LoggingInput(
                new InputOf("Hello, товарищ!"),
                "memory",
                logger
            )
        ).intValue();
        MatcherAssert.assertThat(
            "Can't log 22 bytes read from memory",
            logger.toString(),
            Matchers.containsString("Read 22 byte(s) from memory in")
        );
    }

    @Test
    public void logReadFromLargeTextFile() throws IOException {
        final Logger logger = new FakeLogger();
        new LengthOf(
            new LoggingInput(
                new ResourceOf("org/cactoos/large-text.txt"),
                "text file",
                logger
            )
        ).intValue();
        MatcherAssert.assertThat(
            "Can't log 74536 bytes read from text file",
            logger.toString(),
            Matchers.allOf(
                Matchers.not(
                    Matchers.containsString("Read 16384 byte(s) from text file")
                ),
                Matchers.containsString("Read 74536 byte(s) from text file in"),
                Matchers.containsString("Closed input stream from text file")
            )
        );
    }

    @Test
    public void logAllFromLargeTextFile() throws IOException {
        final Logger logger = new FakeLogger(Level.WARNING);
        new LengthOf(
            new LoggingInput(
                new ResourceOf("org/cactoos/large-text.txt"),
                "text file",
                logger
            )
        ).intValue();
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    public void logSkipFromLargeTextFile() throws IOException {
        final Logger logger = new FakeLogger();
        new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        // @checkstyle MagicNumber (1 line)
        ).stream().skip(100);
        MatcherAssert.assertThat(
            "Can't log skip from text file",
            logger.toString(),
            Matchers.containsString("Skipped 100 byte(s) from text file.")
        );
    }

    @Test
    public void logAvailableFromLargeTextFile() throws IOException {
        final Logger logger = new FakeLogger();
        new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        ).stream().available();
        MatcherAssert.assertThat(
            "Can't log avaliable byte(s) from text file",
            logger.toString(),
            Matchers.containsString(
                "There is(are) 74536 byte(s) available from text file"
            )
        );
    }

    @Test
    public void logResetFromLargeTextFile() throws IOException {
        final Logger logger = new FakeLogger();
        final InputStream input = new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        ).stream();
        // @checkstyle MagicNumber (1 line)
        input.mark(150);
        input.reset();
        MatcherAssert.assertThat(
            "Can't log mark and reset from text file",
            logger.toString(),
            Matchers.allOf(
                Matchers.containsString("Marked position 150 from text file"),
                Matchers.containsString("Reset input stream from text file")
            )
        );
    }

    @Test
    public void logMarkSupportedFromLargeTextFile() throws IOException {
        final Logger logger = new FakeLogger();
        new LoggingInput(
            new ResourceOf("org/cactoos/large-text.txt"),
            "text file",
            logger
        ).stream().markSupported();
        MatcherAssert.assertThat(
            "Can't log mark and reset are not supported from text file",
            logger.toString(),
            Matchers.containsString(
                "Mark and reset are supported from text file"
            )
        );
    }

}
