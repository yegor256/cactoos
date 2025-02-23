/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.text;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for  {@link Base64Encoded}.
 * @since 0.20.2
 */
final class Base64EncodedTest {

    /**
     * Check text encodes using the Base64 encoding scheme.
     */
    @Test
    void checkEncode() {
        new Assertion<>(
            "Can't encodes text using the Base64 encoding scheme",
            new Base64Encoded(
                "Hello!"
            ),
            new HasString(
                "SGVsbG8h"
            )
        ).affirm();
    }
}
