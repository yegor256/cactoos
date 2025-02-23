/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.LinkedList;
import java.util.List;
import org.cactoos.func.BiFuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.proc.BiProcOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link AndWithIndex}.
 *
 * @since 0.8
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class AndWithIndexTest {

    @Test
    void iteratesListWithIndexFromBiFunc() {
        final List<String> list = new LinkedList<>();
        new Assertion<>(
            "Must iterate a list with a function",
            new AndWithIndex(
                new BiFuncOf<>(
                    (text, index) -> list.add(index, text),
                    true
                ),
                "hello", "world"
            ),
            new HasValue<>(true)
        ).affirm();
        new Assertion<>(
            "Must have populated the list",
            list,
            new HasSize(2)
        ).affirm();
    }

    @Test
    void iteratesListWithIndexFromBiProc() {
        final List<String> list = new LinkedList<>();
        new Assertion<>(
            "Must iterate a list with a procedure",
            new AndWithIndex(
                new BiProcOf<>(
                    (text, index) -> {
                        list.add(index, text);
                    }
                ),
                new IterableOf<>("hello", "world")
            ),
            new HasValue<>(true)
        ).affirm();
        new Assertion<>(
            "Must have populated the list",
            list,
            new HasSize(2)
        ).affirm();
    }
}
