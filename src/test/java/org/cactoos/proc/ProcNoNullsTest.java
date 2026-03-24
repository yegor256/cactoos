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
 * Test case for {@link ProcNoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ProcNoNullsTest {

    @Test
    void failForNullProc() {
        MatcherAssert.assertThat(
            "Doesn't fail for null proc",
            () -> {
                new ProcNoNulls<>(null).exec(new Object());
                return 1;
            },
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void failForNullInput() {
        MatcherAssert.assertThat(
            "Doesn't fail for null input",
            () -> {
                new ProcNoNulls<>(input -> { }).exec(null);
                return 1;
            },
            new Throws<>(IllegalArgumentException.class)
        );
    }

    @Test
    void okForNoNulls() throws Exception {
        final AtomicInteger counter = new AtomicInteger();
        new ProcNoNulls<>(AtomicInteger::incrementAndGet)
            .exec(counter);
        MatcherAssert.assertThat(
            "Can't involve the \"Proc.exec(X input)\" method",
            counter.get(),
            new IsEqual<>(1)
        );
    }
}
