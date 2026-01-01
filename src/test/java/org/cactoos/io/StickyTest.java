/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import org.cactoos.bytes.BytesOf;
import org.cactoos.func.Repeated;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link Sticky}.
 *
 * @since 0.6
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class StickyTest {

    @Test
    void readsFileContent() {
        new Assertion<>(
            "Must read bytes from a file",
            new Sticky(
                new ResourceOf(
                    "org/cactoos/large-text.txt"
                )
            ),
            new Satisfies<>(
                new Repeated<>(
                    input -> new BytesOf(
                        new TeeInput(input, new DeadOutput())
                    ).asBytes().length == 74_536,
                    10
                )
            )
        ).affirm();
    }

    @Test
    void readsRealUrl() throws MalformedURLException, URISyntaxException {
        new Assertion<>(
            "Must fetch text page from the URL",
            new TextOf(
                new Sticky(
                    new InputOf(
                        new URI(
                            "file:src/test/resources/org/cactoos/large-text.txt"
                        ).toURL()
                    )
                )
            ),
            new EndsWith("est laborum.\n")
        ).affirm();
    }

    @Test
    void readsFileContentSlowlyAndCountsLength() throws Exception {
        final long size = 100_000L;
        new Assertion<>(
            "Must read bytes from a large source slowly and count length",
            new LengthOf(
                new Sticky(
                    new SlowInput(size)
                )
            ).value(),
            new IsEqual<>(size)
        ).affirm();
    }

    @Test
    void readsFileContentSlowly() throws Exception {
        final int size = 130_000;
        new Assertion<>(
            "Must read bytes from a large source slowly",
            new BytesOf(
                new Sticky(
                    new SlowInput(size)
                )
            ).asBytes().length,
            new IsEqual<>(size)
        ).affirm();
    }

}
