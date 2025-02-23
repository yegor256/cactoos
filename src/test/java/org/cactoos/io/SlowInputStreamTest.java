/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.cactoos.bytes.BytesOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test for {@link SlowInputStream}.
 *
 * @since 0.47
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class SlowInputStreamTest {

    @Test
    void readsSigned() throws Exception {
        new Assertion<>(
            "must correctly convert signed bytes to int",
            new SlowInputStream(
                new InputStreamOf(new BytesOf((byte) -100))
            ).read(),
            new IsEqual<>(156)
        ).affirm();
    }

    @Test
    void readsUnsigned() throws Exception {
        new Assertion<>(
            "must correctly convert unsigned bytes to int",
            new SlowInputStream(
                new InputStreamOf(new BytesOf((byte) 65))
            ).read(),
            new IsEqual<>(65)
        ).affirm();
    }
}
