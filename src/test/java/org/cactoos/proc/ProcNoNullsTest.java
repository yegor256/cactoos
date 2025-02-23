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
 * Test case for {@link ProcNoNulls}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class ProcNoNullsTest {

    @Test
    void failForNullProc() {
        new Assertion<>(
            "Doesn't fail for null proc",
            () -> {
                new ProcNoNulls<>(null).exec(new Object());
                return 1;
            },
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void failForNullInput() {
        new Assertion<>(
            "Doesn't fail for null input",
            () -> {
                new ProcNoNulls<>(input -> { }).exec(null);
                return 1;
            },
            new Throws<>(IllegalArgumentException.class)
        ).affirm();
    }

    @Test
    void okForNoNulls() throws Exception {
        final AtomicInteger counter = new AtomicInteger();
        new ProcNoNulls<>(AtomicInteger::incrementAndGet)
            .exec(counter);
        new Assertion<>(
            "Can't involve the \"Proc.exec(X input)\" method",
            counter.get(),
            new IsEqual<>(1)
        ).affirm();
    }
}
