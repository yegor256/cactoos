/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.EOFException;
import java.io.IOException;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link CheckedBiFunc}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class CheckedBiFuncTest {

    @Test
    void runtimeExceptionIsNotWrapped() {
        new Assertion<>(
            "Must fail if function is null.",
            () -> new CheckedBiFunc<>(
                (first, second) -> {
                    throw new IllegalStateException("runtime1");
                },
                IOException::new
            ).apply(true, true),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void checkedExceptionIsWrapped() {
        new Assertion<>(
            "Must fail if function is null.",
            () -> new CheckedBiFunc<>(
                (first, second) -> {
                    throw new EOFException("runtime2");
                },
                IOException::new
            ).apply(true, true),
            new Throws<>(IOException.class)
        ).affirm();
    }

    @Test
    void extraWrappingIgnored() {
        try {
            new CheckedBiFunc<>(
                (first, second) -> {
                    throw new IOException("runtime3");
                },
                IOException::new
            ).apply(true, true);
        } catch (final IOException exp) {
            new Assertion<>(
                "Extra wrapping of IOException has been added",
                exp.getCause(),
                new IsNull<>()
            ).affirm();
        }
    }

    @Test
    void noExceptionThrown() throws Exception {
        new Assertion<>(
            "Must not throw an exception",
            new CheckedBiFunc<>(
                (first, second) -> true,
                exp -> exp
            ).apply(false, false),
            new IsEqual<>(true)
        ).affirm();
    }

}
