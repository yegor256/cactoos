/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.Text;
import org.cactoos.text.FormattedText;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsApplicable;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link FuncEnvelope}.
 *
 * @since 0.41
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class FuncEnvelopeTest {

    @Test
    void envelopeDelegatesCalls() {
        new Assertion<>(
            "must delegate calls to apply",
            new Append(" stuff"),
            new IsApplicable<>(2, new IsText("2 stuff"))
        ).affirm();
    }

    private static final class Append extends FuncEnvelope<Integer, Text> {
        Append(final String suffix) {
            super(input -> new FormattedText("%d%s", input, suffix));
        }
    }
}
