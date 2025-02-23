/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Text;
import org.cactoos.text.TextOf;
import org.cactoos.text.Upper;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Mapped}.
 *
 * @since 0.43
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class MappedTest {
    @Test
    void transformsScalar() {
        new Assertion<>(
            "must transform scalar",
            new Mapped<Text>(
                input -> new Upper(new TextOf(input)),
                () -> "hello"
            ),
            new HasValue<>(new IsText("HELLO"))
        ).affirm();
    }
}
