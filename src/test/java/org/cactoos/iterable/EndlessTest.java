/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.NoSuchElementException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link Endless}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class EndlessTest {

    @Test
    void endlessIterableTest() {
        MatcherAssert.assertThat(
            "Must get unique endless iterable item",
            new Endless<>(1),
            Matchers.hasItem(1)
        );
    }

    @Test
    void elementsIsNullTest() {
        MatcherAssert.assertThat(
            "Must get sliced iterable of elements",
            () -> new Endless<>(null).iterator().next(),
            new Throws<>(NoSuchElementException.class)
        );
    }
}
