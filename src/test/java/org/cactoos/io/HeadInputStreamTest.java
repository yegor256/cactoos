/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test cases for {@link HeadInputStream}.
 *
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class HeadInputStreamTest {

    @Test
    void testSkippingLessThanTotal() throws Exception {
        try (HeadInputStream stream = new HeadInputStream(
            new InputOf("testSkippingLessThanTotal").stream(),
            5
        )) {
            final long skipped = stream.skip(3L);
            new Assertion<>(
                "Incorrect number of bytes skipped",
                skipped,
                new IsEqual<>(3L)
            ).affirm();
            new Assertion<>(
                "Incorrect head of the input stream has been read",
                new InputOf(stream),
                new HasContent("tS")
            ).affirm();
        }
    }

    @Test
    void testSkippingMoreThanTotal() throws Exception {
        try (HeadInputStream stream = new HeadInputStream(
            new InputOf("testSkippingMoreThanTotal").stream(),
            5
        )) {
            final long skipped = stream.skip(7L);
            new Assertion<>(
                "Incorrect number of bytes skipped",
                skipped,
                new IsEqual<>(5L)
            ).affirm();
            final String input = new TextOf(stream).asString();
            new Assertion<>(
                "The result text wasn't empty",
                new TextOf(input),
                new IsText("")
            ).affirm();
        }
    }

    @Test
    void testResetting() throws Exception {
        try (HeadInputStream stream = new HeadInputStream(
            new InputOf("testResetting").stream(),
            5
        )) {
            final long skipped = stream.skip(7L);
            new Assertion<>(
                "Incorrect number of bytes skipped",
                skipped,
                new IsEqual<>(5L)
            ).affirm();
            stream.reset();
            new Assertion<>(
                "Reset didn't change the state",
                new InputOf(stream),
                new HasContent("testR")
            ).affirm();
        }
    }

    @Test
    void testAvailableLessThanTotal() throws Exception {
        try (HeadInputStream stream = new HeadInputStream(
            new InputOf("testAvailableLessThanTotal").stream(),
            5
        )) {
            new Assertion<>(
                "must count available bytes",
                stream.available(),
                new IsEqual<>(5)
            ).affirm();
        }
    }
}
