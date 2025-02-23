/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.security.SecureRandom;
import org.cactoos.Func;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link StickyFunc}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class StickyFuncTest {

    @Test
    void cachesFuncResults() throws Exception {
        final Func<Boolean, Integer> func = new StickyFunc<>(
            input -> new SecureRandom().nextInt()
        );
        new Assertion<>(
            "Must cache results",
            func.apply(true) + func.apply(true),
            new IsEqual<>(
                func.apply(true) + func.apply(true)
            )
        ).affirm();
    }

    @Test
    void cachesWithLimitedBuffer() throws Exception {
        final Func<Integer, Integer> func = new StickyFunc<>(
            input -> new SecureRandom().nextInt(), 2
        );
        final int first = func.apply(0);
        final int second = func.apply(1);
        new Assertion<>(
            "Must cache two results",
            first + second,
            new IsEqual<>(func.apply(0) + func.apply(1))
        ).affirm();
        final int third = func.apply(-1);
        new Assertion<>(
            "Must cache next two results",
            second + third,
            new IsEqual<>(func.apply(1) + func.apply(-1))
        ).affirm();
    }

    @Test
    void cachesWithZeroBuffer() throws Exception {
        final Func<Boolean, Integer> func = new StickyFunc<>(
            input -> new SecureRandom().nextInt(), 0
        );
        new Assertion<>(
            "Must be not be cached",
            func.apply(true) + func.apply(true),
            new IsNot<>(
                new IsEqual<>(
                    func.apply(true) + func.apply(true)
                )
            )
        ).affirm();
    }

}
