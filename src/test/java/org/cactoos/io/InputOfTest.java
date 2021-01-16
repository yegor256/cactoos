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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.cactoos.bytes.BytesOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.InputHasContent;
import org.llorllale.cactoos.matchers.MatcherOf;
import org.llorllale.cactoos.matchers.MatchesRegex;
import org.llorllale.cactoos.matchers.StartsWith;
import org.llorllale.cactoos.matchers.TextHasString;
import org.takes.http.FtRemote;
import org.takes.tk.TkHtml;

/**
 * Test case for {@link InputOf}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 * @checkstyle ClassFanOutComplexityCheck (500 lines)
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveImports", "unchecked" })
public final class InputOfTest {
    /**
     * Temporary files and folders generator.
     */
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void readsAlternativeInputForFileCase() {
        new Assertion<>(
            "must read alternative source from file not found",
            new TextOf(
                new InputWithFallback(
                    new InputOf(
                        new File("/this-file-does-not-exist.txt")
                    ),
                    new InputOf(new TextOf("Alternative text!"))
                )
            ),
            new EndsWith("text!")
        ).affirm();
    }

    @Test
    public void readsSimpleFileContent() throws IOException {
        final Path temp = this.folder.newFile("cactoos-1.txt-1")
            .toPath();
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        new Assertion<>(
            "must read file content",
            new InputOf(temp),
            new InputHasContent(content)
        ).affirm();
    }

    @Test
    public void closesInputStream() throws Exception {
        final AtomicBoolean closed = new AtomicBoolean();
        final InputStream input = new ByteArrayInputStream(
            "how are you?".getBytes()
        );
        new Assertion<>(
            "must close InputStream correctly",
            new TextOf(
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
            ).asString(),
            new MatcherOf<>(
                text -> {
                    return closed.get();
                }
            )
        ).affirm();
    }

    @Test
    public void readsFileContent() throws Exception {
        new Assertion<>(
            "must read bytes from a file-system URL",
            new BytesOf(
                new InputOf(
                    this.getClass().getResource(
                        "/org/cactoos/io/InputOf.class"
                    )
                )
            ).asBytes().length,
            new MatcherOf<>(len -> len > 0)
        ).affirm();
    }

    @Test
    public void readsRealUrl() throws IOException {
        new FtRemote(new TkHtml("<html>How are you?</html>")).exec(
            home -> new Assertion<>(
                "must fetch bytes from the URL",
                new TextOf(
                    new InputOf(home)
                ),
                new MatchesRegex("<html.*html>")
            ).affirm()
        );
    }

    @Test
    public void readsStringUrl() throws IOException {
        new Assertion<>(
            "must fetch bytes from the HTTPS URL",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        new URL(
                            // @checkstyle LineLength (1 line)
                            "file:src/test/resources/org/cactoos/large-text.txt"
                        )
                    )
                )
            ),
            new TextHasString("Lorem ipsum")
        ).affirm();
    }

    @Test
    public void readsStringIntoBytes() throws Exception {
        new Assertion<>(
            "must read bytes from Input",
            new TextOf(
                new BytesOf(
                    new InputOf("Hello, друг!")
                ),
                StandardCharsets.UTF_8
            ),
            new AllOf<>(
                new IterableOf<>(
                    new StartsWith("Hello, "),
                    new EndsWith("друг!")
                )
            )
        ).affirm();
    }

    @Test
    public void readsStringBuilder() throws Exception {
        final String starts = "Name it, ";
        final String ends = "then it exists!";
        new Assertion<>(
            "must receive a string builder",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        new StringBuilder(starts)
                            .append(ends)
                    )
                )
            ),
            new AllOf<>(
                new IterableOf<>(
                    new StartsWith(starts),
                    new EndsWith(ends)
                )
            )
        ).affirm();
    }

    @Test
    public void readsStringBuffer() throws Exception {
        final String starts = "The future ";
        final String ends = "is now!";
        new Assertion<>(
            "must receive a string buffer",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        new StringBuffer(starts)
                            .append(ends)
                    )
                )
            ),
            new AllOf<>(
                new IterableOf<>(
                    new StartsWith(starts),
                    new EndsWith(ends)
                )
            )
        ).affirm();
    }

    @Test
    public void readsArrayOfChars() throws Exception {
        new Assertion<>(
            "must read array of chars.",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        'H', 'o', 'l', 'd', ' ',
                        'i', 'n', 'f', 'i', 'n', 'i', 't', 'y'
                    )
                )
            ),
            new AllOf<>(
                new IterableOf<>(
                    new StartsWith("Hold "),
                    new EndsWith("infinity")
                )
            )
        ).affirm();
    }

    @Test
    public void readsEncodedArrayOfChars() throws Exception {
        new Assertion<>(
            "must read array of encoded chars.",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        new char[]{
                            'O', ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a',
                            ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a',
                        },
                        StandardCharsets.UTF_8
                    )
                ),
                StandardCharsets.UTF_8
            ),
            new AllOf<>(
                new IterableOf<>(
                    new StartsWith("O que sera"),
                    new EndsWith(" que sera")
                )
            )
        ).affirm();
    }

    @Test
    public void readsStringFromReader() throws Exception {
        final String source = "hello, source!";
        new Assertion<>(
            "must read string through a reader",
            new TextOf(
                new InputOf(
                    new StringReader(source)
                )
            ).asString(),
            new IsEqual<>(source)
        ).affirm();
    }

    @Test
    public void readsEncodedStringFromReader() throws Exception {
        final String source = "hello, друг!";
        new Assertion<>(
            "must read encoded string through a reader",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        new StringReader(source),
                        StandardCharsets.UTF_8
                    )
                )
            ).asString(),
            new IsEqual<>(source)
        ).affirm();
    }

    @Test
    public void readsAnArrayOfBytes() throws Exception {
        final byte[] bytes = new byte[] {(byte) 0xCA, (byte) 0xFE};
        new Assertion<>(
            "must read array of bytes",
            new BytesOf(
                new SyncInput(new InputOf(bytes))
            ).asBytes(),
            new IsEqual<>(bytes)
        ).affirm();
    }

    @Test
    public void makesDataAvailable() throws Exception {
        final String content = "Hello,חבר!";
        new Assertion<>(
            "must show that data is available",
            new InputOf(content).stream().available(),
            new MatcherOf<>(avl -> avl > 0)
        ).affirm();
    }

    @Test
    // @checkstyle MethodBodyCommentsCheck (50 lines)
    public void readsSecureUrlContent() throws Exception {
        final TrustManager[] managers = {
            new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(
                    final X509Certificate[] cert, final String arg) {
                    // nothing to do
                }

                @Override
                public void checkServerTrusted(
                    final X509Certificate[] cert, final String arg) {
                    // nothing to do
                }
            },
        };
        final SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, managers, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(
            context.getSocketFactory()
        );
        new Assertion<>(
            "Can't read bytes from HTTPS URL",
            new BytesOf(
                new InputOf(
                    new URL(
                        "https://www.yegor256.com/robots.txt"
                    )
                )
            ).asBytes().length,
            new MatcherOf<>(len -> len > 0)
        ).affirm();
    }

}
