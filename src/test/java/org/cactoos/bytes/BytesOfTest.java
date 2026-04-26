/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import org.cactoos.io.InputOf;
import org.cactoos.io.Sticky;
import org.cactoos.iterable.Endless;
import org.cactoos.iterable.HeadOf;
import org.cactoos.iterable.IterableOfBytes;
import org.cactoos.iterator.IteratorOfBytes;
import org.cactoos.text.Concatenated;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.HasString;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.StartsWith;

/**
 * Test case for {@link BytesOf}.
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BytesOfTest {

    @Test
    void readsLargeInMemoryContent() throws Exception {
        MatcherAssert.assertThat(
            "must read large content from in-memory Input",
            new BytesOf(
                new InputOf(
                    new Concatenated(
                        new HeadOf<>(
                            5_000, new Endless<>(new TextOf("1234567890"))
                        )
                    )
                )
            ).asBytes().length,
            new IsEqual<>("1234567890".length() * 5_000)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void readsInputIntoBytes() {
        MatcherAssert.assertThat(
            "must read bytes from Input",
            new TextOf(
                new BytesOf(
                    new InputOf("Hello, друг!")
                )
            ),
            new AllOf<>(
                new StartsWith("Hello, "),
                new EndsWith("друг!")
            )
        );
    }

    @Test
    void readsFromReader() {
        MatcherAssert.assertThat(
            "must read string through a reader",
            new TextOf(
                new Sticky(
                    new InputOf(
                        new BytesOf(
                            new StringReader("hello, друг!"),
                            StandardCharsets.UTF_8,
                            16 << 10
                        )
                    )
                )
            ),
            new IsText("hello, друг!")
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void readsInputIntoBytesWithSmallBuffer() {
        MatcherAssert.assertThat(
            "must read bytes from Input with a small reading buffer",
            new TextOf(
                new BytesOf(
                    new InputOf("Hello, товарищ!"),
                    2
                )
            ),
            new AllOf<>(
                new StartsWith("Hello,"),
                new EndsWith("товарищ!")
            )
        );
    }

    @Test
    void closesInputStream() throws Exception {
        final AtomicBoolean closed = new AtomicBoolean();
        final InputStream input = new ByteArrayInputStream(
            "how are you?".getBytes(StandardCharsets.UTF_8)
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
            ),
            StandardCharsets.UTF_8
        ).asString();
        MatcherAssert.assertThat(
            "must close InputStream correctly",
            closed.get(),
            new IsTrue()
        );
    }

    @Test
    void asBytes() throws Exception {
        MatcherAssert.assertThat(
            "Can't convert text into bytes",
            new BytesOf(
                new InputOf(new TextOf("Hello!"))
            ).asBytes(),
            new IsEqual<>(
                new BytesOf(new TextOf("Hello!").asString()).asBytes()
            )
        );
    }

    @Test
    void asBytesFromIterator() throws Exception {
        MatcherAssert.assertThat(
            "Can't convert iterator into bytes",
            new BytesOf(
                new IteratorOfBytes(new TextOf("Good bye!"))
            ).asBytes(),
            new IsEqual<>(
                new BytesOf(new TextOf("Good bye!")).asBytes()
            )
        );
    }

    @Test
    void asBytesFromIterable() throws Exception {
        MatcherAssert.assertThat(
            "Must convert iterable into bytes",
            new BytesOf(
                new IterableOfBytes(new TextOf("Good bye"))
            ).asBytes(),
            new IsEqual<>(new BytesOf(new TextOf("Good bye")).asBytes())
        );
    }

    @Test
    void printsStackTrace() {
        MatcherAssert.assertThat(
            "Can't print exception stacktrace",
            new TextOf(
                new BytesOf(
                    new IOException(
                        "It doesn't work at all"
                    )
                )
            ),
            new HasString(
                new Joined(
                    System.lineSeparator(),
                    "java.io.IOException: It doesn't work at all",
                    "\tat org.cactoos.bytes.BytesOfTest"
                )
            )
        );
    }

    @Test
    void printsStackTraceFromArray() {
        MatcherAssert.assertThat(
            "Can't print exception stacktrace from array",
            new TextOf(
                new BytesOf(
                    new IOException("").getStackTrace()
                )
            ),
            new HasString("org.cactoos.bytes.BytesOfTest")
        );
    }
}
