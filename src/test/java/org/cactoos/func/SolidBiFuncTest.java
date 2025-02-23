/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.security.SecureRandom;
import org.cactoos.BiFunc;
import org.cactoos.Func;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link SolidBiFunc}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class SolidBiFuncTest {
    @Test
    void testThatFuncIsSynchronized() {
        final int threads = 100;
        final int[] shared = {0};
        final BiFunc<Integer, Integer, Boolean> testable =
            new SolidBiFunc<>(
                (first, second) -> {
                    shared[0] += 1;
                    return true;
                }
            );
        new Assertion<>(
            "SolidBiFunc can't work properly in concurrent threads.",
            func -> func.apply(true),
            new RunsInThreads<Func<Boolean, Boolean>>(
                input -> testable.apply(1, 1),
                threads
            )
        ).affirm();
        new Assertion<>(
            "Shared resource has been modified by multiple threads.",
            shared[0],
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void testThatFuncResultCacheIsLimited() throws Exception {
        final BiFunc<Integer, Integer, Integer> func =
            new SolidBiFunc<>(
                (first, second) -> new SecureRandom().nextInt(),
                1
            );
        new Assertion<>(
            "Result of (0, 0) call wasn't invalidated.",
            func.apply(0, 0) + func.apply(1, 1),
            new IsNot<>(
                new IsEqual<>(
                    func.apply(1, 1) + func.apply(0, 0)
                )
            )
        ).affirm();
    }
}
