/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.BiProc;
import org.cactoos.Proc;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link ForEachWithIndex}.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class ForEachWithIndexTest {

    @Test
    void iteratesWithIndex() throws Exception {
        final StringBuilder builder = new StringBuilder();
        new ForEachWithIndex<>(
            (input, index) -> builder.append(String.format("%d: '%s' ", index + 1, input))
        ).exec(
            new IterableOf<>(
                "Mary", "John", "William", "Napkin"
            )
        );
        MatcherAssert.assertThat(
            "String must contain mapped Iterable elements",
            builder.toString(),
            new IsEqual<>(
                "1: 'Mary' 2: 'John' 3: 'William' 4: 'Napkin' "
            )
        );
    }

    @Test
    void worksWithGenerics() throws Exception {
        final StringBuilder builder = new StringBuilder();
        final BiProc<Object, Integer> callback =
            (input, index) -> builder.append(
                String.format("%d: '%s' ", index + 1, input)
            );
        final Proc<Iterable<Number>> proc =
            new ForEachWithIndex<>(callback);
        proc.exec(new ListOf<>(1, 2));
        MatcherAssert.assertThat(
            "String must contain sub-type elements with indexes",
            builder.toString(),
            new IsEqual<>("1: '1' 2: '2' ")
        );
    }
}
