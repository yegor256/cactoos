/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test Case for {@link Base64Decoded}.
 *
 * @since 0.20.2
 */
final class Base64DecodedTest {

    /**
     * Check text decodes using the Base64 encoding scheme.
     */
    @Test
    void checkDecode() {
        new Assertion<>(
            "Can't decodes text using the Base64 encoding scheme",
            new Base64Decoded(
                "SGVsbG8h"
            ),
            new HasString(
                "Hello!"
            )
        ).affirm();
    }
}
