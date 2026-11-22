/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.security.SecureRandom;
import org.cactoos.BiFunc;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link StickyBiFunc}.
 *
 * @since 0.13
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class StickyBiFuncTest {

    @Test
    void cachesFuncResults() throws Exception {
        final BiFunc<Boolean, Boolean, Integer> func = new StickyBiFunc<>(
            (first, second) -> new SecureRandom().nextInt()
        );
        MatcherAssert.assertThat(
            "Must cache results",
            func.apply(true, true) + func.apply(true, true),
            new IsEqual<>(func.apply(true, true) + func.apply(true, true))
        );
    }

    @Test
    void acceptsBiFuncOfSupertypeInputs() throws Exception {
        final BiFunc<Object, Object, String> origin =
            (first, second) -> first.toString();
        MatcherAssert.assertThat(
            "Must accept bi-func of supertype inputs",
            new StickyBiFunc<CharSequence, CharSequence, CharSequence>(
                origin
            ).apply("a", "b"),
            new IsEqual<>("a")
        );
    }

    @Test
    void acceptsBiFuncOfSupertypeInputsWithLimit() throws Exception {
        final BiFunc<Object, Object, String> origin =
            (first, second) -> first.toString();
        MatcherAssert.assertThat(
            "Must accept bi-func of supertype inputs with a limit",
            new StickyBiFunc<CharSequence, CharSequence, CharSequence>(
                origin, 10
            ).apply("a", "b"),
            new IsEqual<>("a")
        );
    }
}
