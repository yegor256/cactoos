/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
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
 * Test case for {@link CheckedFunc}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class CheckedFuncTest {

    @Test
    void runtimeExceptionIsNotWrapped() {
        new Assertion<>(
            "Runtime exception should be rethrown",
            () -> new CheckedFunc<>(
                input -> {
                    throw new IllegalStateException("runtime1");
                },
                IOException::new
            ).apply(true),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void checkedExceptionIsWrapped() {
        new Assertion<>(
            "Checked exception was not wrapped",
            () -> new CheckedFunc<>(
                input -> {
                    throw new EOFException("runtime2");
                },
                IOException::new
            ).apply(true),
            new Throws<>(IOException.class)
        ).affirm();
    }

    @Test
    void extraWrappingIgnored() {
        try {
            new CheckedFunc<>(
                input -> {
                    throw new IOException("runtime3");
                },
                IOException::new
            ).apply(true);
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
            new CheckedFunc<>(
                input -> true,
                exp -> exp
            ).apply(false),
            new IsEqual<>(true)
        ).affirm();
    }
}
