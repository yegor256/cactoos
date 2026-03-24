/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link LSInputOf}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle AbbreviationAsWordInNameCheck (5 lines)
 */
final class LSInputOfTest {

    @Test
    void readsSimpleInput() {
        MatcherAssert.assertThat(
            "Can't read simple input",
            new LSInputOf(
                new InputOf("hello, world!")
            ).getStringData(),
            new StringContains("world!")
        );
    }

    @Test
    void readsBiggerInput() {
        final int size = 400_000;
        MatcherAssert.assertThat(
            "Can't read bigger input",
            new LSInputOf(
                new InputOf(
                    new SlowInputStream(size)
                )
            ).getStringData().length(),
            new IsEqual<>(size)
        );
    }

    @Test
    void countsBytesInBiggerInput() {
        final int size = 300_000;
        MatcherAssert.assertThat(
            "Can't count bytes in a bigger input",
            new LSInputOf(
                new InputOf(
                    new SlowInputStream(size)
                )
            ).getStringData().length(),
            new IsEqual<>(size)
        );
    }

}
