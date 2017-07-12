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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import org.cactoos.InputHasContent;
import org.cactoos.TextHasString;
import org.cactoos.func.FuncAsMatcher;
import org.cactoos.text.BytesAsText;
import org.cactoos.text.StringAsText;
import org.cactoos.text.TextAsBytes;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link InputOf}.
 * These cases were extracted from {@link FileAsInputTest},
 * {@link InputWithFallbackTest}, {@link ResourceAsInputTest},
 * {@link InputAsBytesTest} by Ix (ixmanuel@yahoo.com)
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class InputOfTest {

    @Test
    public void readsSimpleFileContent() throws IOException {
        final Path temp = Files.createTempFile("cactoos-1", "txt-1");
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        MatcherAssert.assertThat(
            "Can't read file content",
            new InputOf(temp),
            new InputHasContent(content)
        );
    }

    @Test
    public void readsAlternativeInput() {
        MatcherAssert.assertThat(
            "Can't read alternative source",
            new BytesAsText(
                new InputAsBytes(
                    new InputWithFallback(
                        new InputOf(
                            new File("/this-file-is-absent-for-sure.txt")
                        ),
                        new InputOf(new StringAsText("hello, world!"))
                    )
                )
            ),
            new TextHasString(Matchers.endsWith("world!"))
        );
    }

    @Test
    public void readsBinaryResource() throws Exception {
        MatcherAssert.assertThat(
            "Can't read bytes from a classpath resource",
            Arrays.copyOfRange(
                new InputAsBytes(
                    new InputOf(
                        "org/cactoos/io/ResourceAsInputTest.class"
                    )
                ).asBytes(),
                // @checkstyle MagicNumber (2 lines)
                0,
                4
            ),
            Matchers.equalTo(
                new byte[]{
                    // @checkstyle MagicNumber (1 line)
                    (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE,
                }
            )
        );
    }

    @Test
    public void readAbsentResourceTest() throws Exception {
        MatcherAssert.assertThat(
            "Can't replace an absent resource with a text",
            new BytesAsText(
                new InputAsBytes(
                    new InputOf(
                        "foo/this-resource-is-definitely-absent.txt",
                        "the replacement"
                    )
                )
            ).asString(),
            Matchers.endsWith("replacement")
        );
    }

    @Test
    public void readsInputIntoBytes() throws IOException {
        MatcherAssert.assertThat(
            "Can't read bytes from Input",
            new String(
                new InputAsBytes(
                    new InputOf(
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
    public void closesInputStream() throws IOException {
        final AtomicBoolean closed = new AtomicBoolean();
        final InputStream input = new ByteArrayInputStream(
            "how are you?".getBytes()
        );
        MatcherAssert.assertThat(
            "Can't close InputStream correctly",
            new BytesAsText(
                new InputAsBytes(
                    new InputOf(
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
