/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Unchecked}.
 *
 * @since 0.3
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class UncheckedTest {

    @Test
    void rethrowsCheckedToUncheckedException() {
        new Assertion<>(
            "Checked exception should be rethrown as unchecked",
            () -> new Unchecked<>(
                () -> {
                    throw new IOException("intended");
                }
            ).value(),
            new Throws<>(UncheckedIOException.class)
        ).affirm();
    }

    @Test
    void rethrowsUncheckedException() {
        new Assertion<>(
            "Unchecked exception should be rethrown as is",
            () -> new Unchecked<>(
                () -> {
                    throw new IllegalStateException("");
                }
            ).value(),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void returnUncheckedValue() {
        new Assertion<>(
            "Must return value without exceptions",
            new Unchecked<>(
                () -> true
            ).value(),
            new IsTrue()
        ).affirm();
    }

}
