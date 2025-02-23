/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link CallableOf}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class CallableOfTest {
    @Test
    void convertsScalar() throws Exception {
        new Assertion<>(
            "must return the value of scalar",
            new CallableOf<>(
                new Constant<>(1)
            ).call(),
            new IsEqual<>(1)
        ).affirm();
    }

}
