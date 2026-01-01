/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import org.cactoos.bytes.BytesOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.HasString;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.MatchesRegex;
import org.llorllale.cactoos.matchers.Satisfies;
import org.llorllale.cactoos.matchers.StartsWith;
import org.takes.http.FtRemote;
import org.takes.tk.TkHtml;

/**
 * Test case for {@link InputOf}.
 *
 * @since 0.1
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessiveImports",
    "unchecked", "PMD.JUnitTestsShouldIncludeAssert"})
final class InputOfTest {
    @Test
    void readsAlternativeInputForFileCase() {
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
    void readsSimpleFileContent(final @TempDir Path folder) throws IOException {
        final Path temp = folder.resolve("cactoos-1.txt-1");
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        new Assertion<>(
            "must read file content",
            new InputOf(temp),
            new HasContent(content)
        ).affirm();
    }

    @Test
    void closesInputStream() throws Exception {
        final AtomicBoolean closed = new AtomicBoolean();
        final InputStream input = new ByteArrayInputStream(
            "how are you?".getBytes()
        );
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
        ).asString();
        new Assertion<>(
            "must close InputStream correctly",
            closed.get(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void readsFileContent() throws Exception {
        new Assertion<>(
            "must read bytes from a file-system URL",
            new BytesOf(
                new InputOf(
                    this.getClass().getResource(
                        "/org/cactoos/io/InputOf.class"
                    )
                )
            ).asBytes(),
            new Satisfies<>(arr -> arr.length > 0)
        ).affirm();
    }

    @Test
    void readsRealUrl() throws Exception {
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
    void readsStringUrl() throws IOException, URISyntaxException {
        new Assertion<>(
            "must fetch bytes from the HTTPS URL",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        new URI(
                            "file:src/test/resources/org/cactoos/large-text.txt"
                        ).toURL()
                    )
                )
            ),
            new HasString("Lorem ipsum")
        ).affirm();
    }

    @Test
    void readsStringIntoBytes() {
        new Assertion<>(
            "must read bytes from Input",
            new TextOf(
                new BytesOf(
                    new InputOf("Hello, друг!")
                ),
                StandardCharsets.UTF_8
            ),
            new AllOf<>(
                new StartsWith("Hello, "),
                new EndsWith("друг!")
            )
        ).affirm();
    }

    @Test
    void readsStringBuilder() {
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
                new StartsWith(starts),
                new EndsWith(ends)
            )
        ).affirm();
    }

    @Test
    void readsStringBuffer() {
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
                new StartsWith(starts),
                new EndsWith(ends)
            )
        ).affirm();
    }

    @Test
    void readsArrayOfChars() {
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
                new StartsWith("Hold "),
                new EndsWith("infinity")
            )
        ).affirm();
    }

    @Test
    void readsEncodedArrayOfChars() {
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
                new StartsWith("O que sera"),
                new EndsWith(" que sera")
            )
        ).affirm();
    }

    @Test
    void readsStringFromReader() {
        final String source = "hello, source!";
        new Assertion<>(
            "must read string through a reader",
            new TextOf(
                new InputOf(
                    new StringReader(source)
                )
            ),
            new IsText(source)
        ).affirm();
    }

    @Test
    void readsEncodedStringFromReader() {
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
            ),
            new IsText(source)
        ).affirm();
    }

    @Test
    void readsAnArrayOfBytes() throws Exception {
        final byte[] bytes = {(byte) 0xCA, (byte) 0xFE};
        new Assertion<>(
            "must read array of bytes",
            new BytesOf(
                new SyncInput(new InputOf(bytes))
            ).asBytes(),
            new IsEqual<>(bytes)
        ).affirm();
    }

    @Test
    void makesDataAvailable() throws Exception {
        final String content = "Hello,חבר!";
        new Assertion<>(
            "must show that data is available",
            new InputOf(content).stream(),
            new Satisfies<>(s -> s.available() > 0)
        ).affirm();
    }

}
