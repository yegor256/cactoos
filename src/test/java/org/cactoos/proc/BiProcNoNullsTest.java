/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link BiProcNoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BiProcNoNullsTest {

    @Test
    void failForNullProc() {
        MatcherAssert.assertThat(
            "Fails in case of null proc",
            () -> {
                new BiProcNoNulls<>(null).exec(
                    new Object(), new Object()
                );
                return 1;
            },
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void failForNullFirstArg() {
        MatcherAssert.assertThat(
            "Fails in case of null first arg",
            () -> {
                new BiProcNoNulls<>(
                    (first, second) -> { }
                ).exec(null, new Object());
                return 1;
            },
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void failForNullSecondArg() {
        MatcherAssert.assertThat(
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
        MatcherAssert.assertThat(
            "Can't invoke the \"BiProc.exec\" method",
            counter.get(),
            new IsEqual<>(1)
        );
    }
}
