/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.security.SecureRandom;
import org.cactoos.Func;
import org.cactoos.list.ListOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link SolidFunc}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class SolidFuncTest {

    @Test
    void cachesFuncResults() throws Exception {
        final Func<Boolean, Integer> func = new SolidFunc<>(
            input -> new SecureRandom().nextInt()
        );
        new Assertion<>(
            "Must cache results",
            func.apply(true) + func.apply(true),
            new IsEqual<>(func.apply(true) + func.apply(true))
        ).affirm();
    }

    @Test
    void worksInThreads() {
        new Assertion<>(
            "Must work well in multiple threads",
            func -> {
                new Assertion<>(
                    "Result must be cached",
                    func.apply(true),
                    new IsEqual<>(func.apply(true))
                ).affirm();
                return true;
            },
            new RunsInThreads<>(
                new SolidFunc<>(x -> new ListOf<>(1, 2))
            )
        ).affirm();
    }

}
