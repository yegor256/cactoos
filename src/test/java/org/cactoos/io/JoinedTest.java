/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.cactoos.Input;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Repeated;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Unit tests for {@link Joined}.
 *
 * @since 0.36
 */
final class JoinedTest {

    @Test
    void joinsOk() {
        MatcherAssert.assertThat(
            "Cannot properly join inputs",
            new Joined(
                new InputOf("first"),
                new InputOf("second"),
                new InputOf("third")
            ),
            new HasContent("firstsecondthird")
        );
    }

    /**
     * Must join inputs of the iterable in the given order.
     */
    @Test
    void fromIterable() {
        MatcherAssert.assertThat(
            "Can't join iterable of inputs",
            new Joined(
                new IterableOf<>(
                    new InputOf("ab"),
                    new InputOf("cde"),
                    new InputOf("fghi")
                )
            ),
            new HasContent("abcdefghi")
        );
    }

    /**
     * Must join many inputs without overflowing the stack on the
     * first read, no matter how many of them are joined together.
     */
    @Test
    void joinsManyInputs() {
        MatcherAssert.assertThat(
            "Can't join a large number of inputs",
            new Joined(
                new Repeated<Input>(
                    20_000,
                    () -> new ByteArrayInputStream(new byte[] {57})
                )
            ),
            new HasContent("9".repeat(20_000))
        );
    }

    /**
     * Must return the sole stream as is, so that it supports mark.
     */
    @Test
    void supportsMarkForSoleStream() throws Exception {
        MatcherAssert.assertThat(
            "Sole stream must support mark",
            new Joined(
                new IterableOf<>(
                    new InputOf(new ByteArrayInputStream(new byte[] {1, 2, 3}))
                )
            ).stream().markSupported(),
            new IsEqual<>(true)
        );
    }

    /**
     * Must return the sole stream as is, so that mark() followed by
     * reset() keeps working on it.
     */
    @Test
    void keepsMarkResetForSoleStream() throws Exception {
        final byte[] bytes = {1, 2, 3};
        final InputStream stream = new Joined(
            new IterableOf<>(new InputOf(new ByteArrayInputStream(bytes)))
        ).stream();
        stream.mark(bytes.length);
        stream.read();
        stream.reset();
        final byte[] reread = new byte[bytes.length];
        stream.read(reread);
        MatcherAssert.assertThat(
            "Must reread identical bytes after reset",
            reread,
            new IsEqual<>(bytes)
        );
    }

    /**
     * Must let the original {@link IOException} of an input that cannot
     * be opened reach the caller, instead of wrapping it into an
     * {@link java.io.UncheckedIOException}.
     */
    @Test
    void surfacesIoExceptionForMissingInput() {
        MatcherAssert.assertThat(
            "Missing input must surface as IOException",
            new Joined(
                new InputOf("abc"),
                new InputOf(new File("nonexistent-joined-input.txt"))
            )::stream,
            new Throws<>(IOException.class)
        );
    }
}
