/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link IterableOfChars}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IterableOfCharsTest {

    @Test
    void convertsCharactersToIterable() {
        MatcherAssert.assertThat(
            "Must create Iterable from Text",
            new IterableOfChars(new TextOf("txt")),
            new HasValues<>('t', 'x', 't')
        );
    }
}
