/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.security.SecureRandom;
import org.cactoos.BiFunc;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link StickyBiFunc}.
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class StickyBiFuncTest {

    @Test
    void cachesFuncResults() throws Exception {
        final BiFunc<Boolean, Boolean, Integer> func = new StickyBiFunc<>(
            (first, second) -> new SecureRandom().nextInt()
        );
        new Assertion<>(
            "Must cache results",
            func.apply(true, true) + func.apply(true, true),
            new IsEqual<>(func.apply(true, true) + func.apply(true, true))
        ).affirm();
    }

}
