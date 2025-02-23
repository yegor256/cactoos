/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link BiProcNoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BiProcNoNullsTest {

    @Test
    void failForNullProc() {
        new Assertion<>(
            "Fails in case of null proc",
            () -> {
                new BiProcNoNulls<>(null).exec(
                    new Object(), new Object()
                );
                return 1;
            },
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void failForNullFirstArg() {
        new Assertion<>(
            "Fails in case of null first arg",
            () -> {
                new BiProcNoNulls<>(
                    (first, second) -> { }
                ).exec(null, new Object());
                return 1;
            },
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void failForNullSecondArg() {
        new Assertion<>(
            "Fails in case of null second arg",
            () -> {
                new BiProcNoNulls<>(
                    (first, second) -> { }
                ).exec(new Object(), null);
                return 1;
            },
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void okForNoNulls() throws Exception {
        final AtomicInteger counter = new AtomicInteger();
        new BiProcNoNulls<>(
            (AtomicInteger ctr, Object second) -> ctr.incrementAndGet()
        ).exec(counter, new Object());
        new Assertion<>(
            "Can't invoke the \"BiProc.exec\" method",
            counter.get(),
            new IsEqual<>(1)
        ).affirm();
    }
}
