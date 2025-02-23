/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.security.SecureRandom;
import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.iterator.IteratorOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Repeated}.
 *
 * @since 0.13.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class RepeatedTest {

    @Test
    void runsFuncMultipleTimes() throws Exception {
        final Iterator<Integer> iter = new IteratorOf<>(1, 2, 5, 6);
        final Func<Boolean, Integer> func = new Repeated<>(
            input -> iter.next(),
            3
        );
        new Assertion<>(
            "Must be applied 3 times",
            func.apply(true),
            new IsEqual<>(5)
        ).affirm();
    }

    @Test
    void repeatsNullsResults() throws Exception {
        final Func<Boolean, Integer> func = new Repeated<>(
            input -> null,
            2
        );
        new Assertion<>(
            "Must repeat NULL",
            func.apply(true),
            new IsNull<>()
        ).affirm();
    }

    @Test
    void doesntRepeatAny() {
        final Func<Boolean, Integer> func = new Repeated<>(
            input -> new SecureRandom().nextInt(),
            0
        );
        new Assertion<>(
            "Must throw if parameter is not correct",
            () -> func.apply(true),
            new Throws<>(
                IllegalArgumentException.class
            )
        ).affirm();
    }
}
