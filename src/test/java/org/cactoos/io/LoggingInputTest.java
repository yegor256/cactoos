/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
import java.util.logging.Logger;
import org.cactoos.Bytes;
import org.cactoos.text.TextAsBytes;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LoggingInput}.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.MoreThanOneLogger")
public final class LoggingInputTest {

    @Test
    public void logReadZeroByte() throws IOException {
        final Logger logger = new FakeLogger();
        new LoggingInput(
            new DeadInput(),
            "zero",
            logger
        ).stream().read();
        MatcherAssert.assertThat(
            "Can't log zero byte read from zero",
            logger.toString(),
            Matchers.containsString("Read 0 byte(s) from zero in")
        );
    }

    @Test
    public void logReadOneByte() throws IOException {
        final Logger logger = new FakeLogger();
        new LoggingInput(
            new BytesAsInput(new TextAsBytes("ú").asBytes()),
            "memory",
            logger
        ).stream().read();
        MatcherAssert.assertThat(
            "Can't log one byte read from memory",
            logger.toString(),
            Matchers.containsString("Read 1 byte(s) from memory in")
        );
    }

    @Test
    public void logReadTwentyTwoBytes() throws IOException {
        final Logger logger = new FakeLogger();
        final Bytes content = new TextAsBytes("Hello, товарищ!");
        final byte[] buf = new byte[content.asBytes().length];
        new LoggingInput(
            new BytesAsInput(content.asBytes()),
            "content",
            logger
        ).stream().read(buf);
        MatcherAssert.assertThat(
            "Can't log 22 bytes read from content",
            logger.toString(),
            Matchers.containsString("Read 22 byte(s) from content in")
        );
    }

    @Test
    public void logReadlargeFile() throws IOException {
        final Logger logger = new FakeLogger();
        // @checkstyle MagicNumber (1 line)
        final byte[] buf = new byte[73471];
        new LoggingInput(
            new ResourceAsInput("org/cactoos/large-text.txt"),
            "large file",
            logger
        ).stream().read(buf);
        MatcherAssert.assertThat(
            "Can't log 73471 bytes read from large file",
            logger.toString(),
            Matchers.containsString("Read 73471 byte(s) from large file in")
        );
    }

}
