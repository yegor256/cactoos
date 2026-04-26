/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.security.SecureRandom;
import org.cactoos.Func;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link SolidFunc}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SolidFuncTest {

    @Test
    void cachesFuncResults() throws Exception {
        final Func<Boolean, Integer> func = new SolidFunc<>(
            input -> new SecureRandom().nextInt()
        );
        MatcherAssert.assertThat(
            "Must cache results",
            func.apply(true) + func.apply(true),
            new IsEqual<>(func.apply(true) + func.apply(true))
        );
    }

    @Test
    void worksInThreads() {
        MatcherAssert.assertThat(
            "Must work well in multiple threads",
            func -> {
                MatcherAssert.assertThat(
                    "Result must be cached",
                    func.apply(true),
                    new IsEqual<>(func.apply(true))
                );
                return true;
            },
            new RunsInThreads<>(
                new SolidFunc<>(x -> new ListOf<>(1, 2))
            )
        );
    }
}
