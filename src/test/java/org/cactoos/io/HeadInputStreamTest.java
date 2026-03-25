/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

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
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class HeadInputStreamTest {

    @Test
    void skipsLessThanTotal() throws Exception {
        try (HeadInputStream stream = new HeadInputStream(
            new InputOf("testSkippingLessThanTotal").stream(),
            5
        )) {
            MatcherAssert.assertThat(
                "Incorrect number of bytes skipped",
                stream.skip(3L),
                new IsEqual<>(3L)
            );
        }
    }

    @Test
    void readsHeadAfterSkip() throws Exception {
        try (HeadInputStream stream = new HeadInputStream(
            new InputOf("testSkippingLessThanTotal").stream(),
            5
        )) {
            stream.skip(3L);
            MatcherAssert.assertThat(
                "Incorrect head of the input stream has been read",
                new InputOf(stream),
                new HasContent("tS")
            );
        }
    }

    @Test
    void skipsMoreThanTotal() throws Exception {
        try (HeadInputStream stream = new HeadInputStream(
            new InputOf("testSkippingMoreThanTotal").stream(),
            5
        )) {
            MatcherAssert.assertThat(
                "Incorrect number of bytes skipped",
                stream.skip(7L),
                new IsEqual<>(5L)
            );
        }
    }

    @Test
    void readsEmptyAfterSkipMoreThanTotal() throws Exception {
        try (HeadInputStream stream = new HeadInputStream(
            new InputOf("testSkippingMoreThanTotal").stream(),
            5
        )) {
            stream.skip(7L);
            MatcherAssert.assertThat(
                "The result text wasn't empty",
                new TextOf(new TextOf(stream).asString()),
                new IsText("")
            );
        }
    }

    @Test
    void skipsBeforeReset() throws Exception {
        try (HeadInputStream stream = new HeadInputStream(
            new InputOf("testResetting").stream(),
            5
        )) {
            MatcherAssert.assertThat(
                "Incorrect number of bytes skipped",
                stream.skip(7L),
                new IsEqual<>(5L)
            );
        }
    }

    @Test
    void resetRestoresStream() throws Exception {
        try (HeadInputStream stream = new HeadInputStream(
            new InputOf("testResetting").stream(),
            5
        )) {
            stream.skip(7L);
            stream.reset();
            MatcherAssert.assertThat(
                "Reset didn't change the state",
                new InputOf(stream),
                new HasContent("testR")
            );
        }
    }

    @Test
    void testAvailableLessThanTotal() throws Exception {
        try (HeadInputStream stream = new HeadInputStream(
            new InputOf("testAvailableLessThanTotal").stream(),
            5
        )) {
            MatcherAssert.assertThat(
                "must count available bytes",
                stream.available(),
                new IsEqual<>(5)
            );
        }
    }
}
