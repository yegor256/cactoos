/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.cactoos.BiFunc;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link UncheckedBiFunc}.
 *
 * @since 0.13
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class UncheckedBiFuncTest {

    @Test
    void rethrowsCheckedToUncheckedException() {
        MatcherAssert.assertThat(
            "Checked exception does not rethrown as unchecked",
            () -> new UncheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IOException("intended");
                }
            ).apply(1, 2),
            new Throws<>(UncheckedIOException.class)
        );
    }

    @Test
    void appliesBiFunc() {
        MatcherAssert.assertThat(
            "Must return value",
            new UncheckedBiFunc<>(
                (fst, scd) -> true
            ).apply(1, 2),
            new IsTrue()
        );
    }

    @Test
    void runtimeExceptionGoesOut() {
        MatcherAssert.assertThat(
            "Runtime exception rethrown without changes",
            () -> new UncheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IllegalStateException("intended to fail");
                }
            ).apply(1, 2),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void acceptsBiFuncOfSupertypeInputs() {
        final BiFunc<Object, Object, String> origin =
            (first, second) -> first.toString();
        MatcherAssert.assertThat(
            "Must accept bi-func of supertype inputs",
            new UncheckedBiFunc<CharSequence, CharSequence, CharSequence>(
                origin
            ).apply("a", "b"),
            new IsEqual<>("a")
        );
    }
}
