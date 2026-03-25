/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.LinkedList;
import java.util.List;
import org.cactoos.func.BiFuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.proc.BiProcOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link AndWithIndex}.
 *
 * @since 0.8
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class AndWithIndexTest {

    @Test
    void iteratesListWithIndexFromBiFunc() {
        final List<String> list = new LinkedList<>();
        MatcherAssert.assertThat(
            "Must iterate a list with a function",
            new AndWithIndex(
                new BiFuncOf<>(
                    (text, index) -> list.add(index, text),
                    true
                ),
                "hello", "world"
            ),
            new HasValue<>(true)
        );
    }

    @Test
    void populatesListWithIndexFromBiFunc() throws Exception {
        final List<String> list = new LinkedList<>();
        new AndWithIndex(
            new BiFuncOf<>(
                (text, index) -> list.add(index, text),
                true
            ),
            "hello", "world"
        ).value();
        MatcherAssert.assertThat(
            "Must have populated the list via BiFuncOf",
            list,
            new HasSize(2)
        );
    }

    @Test
    void iteratesListWithIndexFromBiProc() {
        final List<String> list = new LinkedList<>();
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void populatesListWithIndexFromBiProc() throws Exception {
        final List<String> list = new LinkedList<>();
        new AndWithIndex(
            new BiProcOf<>(
                (text, index) -> {
                    list.add(index, text);
                }
            ),
            new IterableOf<>("hello", "world")
        ).value();
        MatcherAssert.assertThat(
            "Must have populated the list via BiProcOf",
            list,
            new HasSize(2)
        );
    }
}
