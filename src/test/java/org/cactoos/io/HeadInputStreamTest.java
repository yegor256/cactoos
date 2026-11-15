/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test cases for {@link HeadInputStream}.
 *
 * @since 0.31
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class HeadInputStreamTest {

    @Test
    void skipsLessThanTotal() throws Exception {
        try (
            HeadInputStream stream = new HeadInputStream(
                new InputOf("testSkippingLessThanTotal").stream(),
                5
            )
        ) {
            MatcherAssert.assertThat(
                "Incorrect number of bytes skipped",
                stream.skip(3L),
                new IsEqual<>(3L)
            );
        }
    }

    @Test
    void readsHeadAfterSkip() throws Exception {
        try (
            HeadInputStream stream = new HeadInputStream(
                new InputOf("testSkippingLessThanTotal").stream(),
                5
            )
        ) {
            final long skipped = stream.skip(3L);
            MatcherAssert.assertThat(
                String.format(
                    "Incorrect head after skipping %d bytes",
                    skipped
                ),
                new InputOf(stream),
                new HasContent("tS")
            );
        }
    }

    @Test
    void skipsMoreThanTotal() throws Exception {
        try (
            HeadInputStream stream = new HeadInputStream(
                new InputOf("testSkippingMoreThanTotal").stream(),
                5
            )
        ) {
            MatcherAssert.assertThat(
                "Incorrect number of bytes skipped",
                stream.skip(7L),
                new IsEqual<>(5L)
            );
        }
    }

    @Test
    void readsEmptyAfterSkipMoreThanTotal() throws Exception {
        try (
            HeadInputStream stream = new HeadInputStream(
                new InputOf("testSkippingMoreThanTotal").stream(),
                5
            )
        ) {
            final long skipped = stream.skip(7L);
            MatcherAssert.assertThat(
                String.format(
                    "The result text wasn't empty after skipping %d bytes",
                    skipped
                ),
                new TextOf(new TextOf(stream).asString()),
                new IsText("")
            );
        }
    }

    @Test
    void skipsBeforeReset() throws Exception {
        try (
            HeadInputStream stream = new HeadInputStream(
                new InputOf("testResetting").stream(),
                5
            )
        ) {
            MatcherAssert.assertThat(
                "Incorrect number of bytes skipped",
                stream.skip(7L),
                new IsEqual<>(5L)
            );
        }
    }

    @Test
    void resetRestoresStream() throws Exception {
        try (
            HeadInputStream stream = new HeadInputStream(
                new InputOf("testResetting").stream(),
                5
            )
        ) {
            final long skipped = stream.skip(7L);
            stream.reset();
            MatcherAssert.assertThat(
                String.format(
                    "Reset didn't change the state after skipping %d bytes",
                    skipped
                ),
                new InputOf(stream),
                new HasContent("testR")
            );
        }
    }

    @Test
    void countsAvailableBytesLessThanTotal() throws Exception {
        try (
            HeadInputStream stream = new HeadInputStream(
                new InputOf("testAvailableLessThanTotal").stream(),
                5
            )
        ) {
            MatcherAssert.assertThat(
                "must count available bytes",
                stream.available(),
                new IsEqual<>(5)
            );
        }
    }

    @Test
    void readsOnlyHeadAfterResetToNonZeroMark() throws Exception {
        try (
            HeadInputStream stream = new HeadInputStream(
                new ByteArrayInputStream(new byte[] {'a', 'b', 'c', 'd', 'e'}),
                3
            )
        ) {
            stream.read();
            stream.mark(10);
            stream.read();
            stream.reset();
            MatcherAssert.assertThat(
                "read after reset must replay the byte at the mark",
                stream.read(),
                new IsEqual<>((int) 'b')
            );
            MatcherAssert.assertThat(
                "second read after reset must replay the last head byte",
                stream.read(),
                new IsEqual<>((int) 'c')
            );
            MatcherAssert.assertThat(
                "reads past the head must return EOF",
                stream.read(),
                new IsEqual<>(-1)
            );
        }
    }
}
