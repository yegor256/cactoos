/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.security.SecureRandom;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link Solid}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SolidTest {

    @Test
    void cachesScalarResults() throws Exception {
        final Scalar<Integer> scalar = new Solid<>(
            () -> new SecureRandom().nextInt()
        );
        new Assertion<>(
            "must compute value only once",
            scalar.value() + scalar.value(),
            new IsEqual<>(scalar.value() + scalar.value())
        ).affirm();
    }

    @Test
    void worksInThreads() {
        new Assertion<>(
            "must work well in multiple threads",
            scalar -> {
                new Assertion<>(
                    "must compute value once",
                    scalar,
                    new HasValue<>(scalar.value())
                ).affirm();
                return true;
            },
            new RunsInThreads<>(
                new Unchecked<>(
                    new Solid<>(() -> new ListOf<>(1, 2))
                )
            )
        ).affirm();
    }

}
