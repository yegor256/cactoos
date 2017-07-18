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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import org.cactoos.text.TextAsBytes;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LoggingOutput}.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.MoreThanOneLogger")
public final class LoggingOutputTest {

    @Test
    public void logWriteOneByte() throws IOException {
        final Logger logger = new FakeLogger();
        new LoggingOutput(
            () -> new ByteArrayOutputStream(),
            "memory",
            logger
        // @checkstyle MagicNumber (1 line)
        ).stream().write(255);
        MatcherAssert.assertThat(
            "Can't log one byte written to memory",
            logger.toString(),
            Matchers.containsString("Written 1 byte(s) to memory in")
        );
    }

    @Test
    public void logWriteTwentyTwoBytes() throws IOException {
        final Logger logger = new FakeLogger();
        new LoggingOutput(
            () -> new ByteArrayOutputStream(),
            "content",
            logger
        ).stream().write(new TextAsBytes("Hello, товарищ!").asBytes());
        MatcherAssert.assertThat(
            "Can't log twenty two bytes written to content",
            logger.toString(),
            Matchers.containsString("Written 22 byte(s) to content in")
        );
    }

}
