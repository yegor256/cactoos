/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.security.SecureRandom;
import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.iterator.IteratorOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Repeated}.
 * @since 0.13.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class RepeatedTest {

    @Test
    void runsFuncMultipleTimes() throws Exception {
        final Iterator<Integer> iter = new IteratorOf<>(1, 2, 5, 6);
        MatcherAssert.assertThat(
            "Must be applied 3 times",
            new Repeated<Boolean, Integer>(
                input -> iter.next(),
                3
            ).apply(iter.hasNext()),
            new IsEqual<>(5)
        );
    }

    @Test
    void repeatsNullsResults() throws Exception {
        MatcherAssert.assertThat(
            "Must repeat NULL",
            new Repeated<Boolean, Integer>(
                input -> null,
                2
            ).apply(true),
            new IsNull<>()
        );
    }

    @Test
    void doesntRepeatAny() {
        MatcherAssert.assertThat(
            "Must throw if parameter is not correct",
            () -> new Repeated<>(
                (Func<Boolean, Integer>) input -> new SecureRandom().nextInt(),
                0
            ).apply(true),
            new Throws<>(
                IllegalArgumentException.class
            )
        );
    }
}
