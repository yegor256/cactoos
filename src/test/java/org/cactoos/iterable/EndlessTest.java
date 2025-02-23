/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.NoSuchElementException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link Endless}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class EndlessTest {

    @Test
    void endlessIterableTest() {
        new Assertion<>(
            "Must get unique endless iterable item",
            new Endless<>(1),
            Matchers.hasItem(1)
        ).affirm();
    }

    @Test
    void elementsIsNullTest() {
        new Assertion<>(
            "Must get sliced iterable of elements",
            () -> new Endless<>(null).iterator().next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }
}
