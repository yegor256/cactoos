/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.concurrent.Callable;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link CallableOf}.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class CallableOfTest {

    @Test
    void worksWithGenerics() throws Exception {
        final Callable<String> callable = () -> "done";
        MatcherAssert.assertThat(
            "Must accept a callable providing subtypes of the result",
            new CallableOf<Object>(callable).call(),
            new IsEqual<>("done")
        );
    }
}
