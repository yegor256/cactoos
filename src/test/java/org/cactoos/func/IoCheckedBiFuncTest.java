/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import org.cactoos.BiFunc;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedBiFunc}.
 *
 * @since 0.13
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class IoCheckedBiFuncTest {

    @Test
    void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        MatcherAssert.assertThat(
            "Must rethrow IOException",
            () -> new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw exception;
                }
            ).apply(1, 2),
            new Throws<>(
                exception.getMessage(),
                exception.getClass()
            )
        );
    }

    @Test
    void rethrowsCheckedToIoException() {
        MatcherAssert.assertThat(
            "IOException should be rethrown",
            () -> new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IOException("intended to fail");
                }
            ).apply(1, 2),
            new Throws<>(IOException.class)
        );
    }

    @Test
    void runtimeExceptionGoesOut() {
        MatcherAssert.assertThat(
            "Runtime exception should be rethrown",
            () -> new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IllegalStateException("intended to fail here");
                }
            ).apply(1, 2),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void acceptsBiFuncOfSupertypeInputs() throws Exception {
        final BiFunc<Object, Object, String> origin =
            (first, second) -> first.toString();
        MatcherAssert.assertThat(
            "Must accept bi-func of supertype inputs",
            new IoCheckedBiFunc<CharSequence, CharSequence, CharSequence>(
                origin
            ).apply("a", "b"),
            new IsEqual<>("a")
        );
    }
}
