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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import org.cactoos.func.FuncAsMatcher;
import org.cactoos.text.BytesAsText;
import org.cactoos.text.StringAsText;
import org.cactoos.text.TextAsBytes;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link InputAsBytes}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class InputAsBytesTest {

    @Test
    public void readsInputIntoBytes() throws IOException {
        MatcherAssert.assertThat(
            "Can't read bytes from Input",
            new String(
                new InputAsBytes(
                    new BytesAsInput(
                        new TextAsBytes(
                            new StringAsText("Hello, друг!")
                        )
                    )
                ).asBytes(),
                StandardCharsets.UTF_8
            ),
            Matchers.allOf(
                Matchers.startsWith("Hello, "),
                Matchers.endsWith("друг!")
            )
        );
    }

    @Test
    public void readsInputIntoBytesWithSmallBuffer() throws IOException {
        MatcherAssert.assertThat(
            "Can't read bytes from Input with a small reading buffer",
            new String(
                new InputAsBytes(
                    new BytesAsInput(
                        new TextAsBytes(
                            new StringAsText("Hello, товарищ!")
                        )
                    ),
                    2
                ).asBytes(),
                StandardCharsets.UTF_8
            ),
            Matchers.allOf(
                Matchers.startsWith("Hello,"),
                Matchers.endsWith("товарищ!")
            )
        );
    }

    @Test
    public void closesInputStream() throws IOException {
        final AtomicBoolean closed = new AtomicBoolean();
        final InputStream input = new ByteArrayInputStream(
            "how are you?".getBytes()
        );
        MatcherAssert.assertThat(
            "Can't close InputStream correctly",
            new BytesAsText(
                new InputAsBytes(
                    new InputStreamAsInput(
                        new InputStream() {
                            @Override
                            public int read() throws IOException {
                                return input.read();
                            }
                            @Override
                            public void close() throws IOException {
                                input.close();
                                closed.set(true);
                            }
                        }
                    )
                ).asBytes(),
                StandardCharsets.UTF_8
            ).asString(),
            new FuncAsMatcher<>(
                text -> closed.get()
            )
        );
    }

}
