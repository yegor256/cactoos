/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link ForEachWithIndex}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ForEachWithIndexTest {

    @Test
    void testBiProcIterable() throws Exception {
        final StringBuilder builder = new StringBuilder();
        new ForEachWithIndex<>(
            (input, index) -> builder.append(String.format("%d: '%s' ", index + 1, input))
        ).exec(
            new IterableOf<>(
                "Mary", "John", "William", "Napkin"
            )
        );
        new Assertion<>(
            "String must contain mapped Iterable elements",
            builder.toString(),
            new IsEqual<>(
                "1: 'Mary' 2: 'John' 3: 'William' 4: 'Napkin' "
            )
        ).affirm();
    }

}
