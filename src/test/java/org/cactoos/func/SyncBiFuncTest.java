/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.BiFunc;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link SyncBiFunc}.
 *
 * @since 0.38
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class SyncBiFuncTest {

    @Test
    void acceptsBiFuncOfSupertypeInputs() throws Exception {
        final BiFunc<Object, Object, String> origin =
            (first, second) -> first.toString();
        MatcherAssert.assertThat(
            "Must accept bi-func of supertype inputs",
            new SyncBiFunc<CharSequence, CharSequence, CharSequence>(
                origin
            ).apply("a", "b"),
            new IsEqual<>("a")
        );
    }

    @Test
    void acceptsBiFuncOfSupertypeInputsWithLock() throws Exception {
        final BiFunc<Object, Object, String> origin =
            (first, second) -> first.toString();
        MatcherAssert.assertThat(
            "Must accept bi-func of supertype inputs with a lock",
            new SyncBiFunc<CharSequence, CharSequence, CharSequence>(
                origin, new Object()
            ).apply("a", "b"),
            new IsEqual<>("a")
        );
    }
}
