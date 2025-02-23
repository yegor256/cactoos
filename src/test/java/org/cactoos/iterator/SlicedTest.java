/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Constant;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test for {@link Sliced}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SlicedTest {

    @Test
    void sliceTheMiddle() {
        new Assertion<>(
            "Must return sliced iterator",
            new IterableOf<>(
                new Sliced<>(
                    3,
                    2,
                    new IteratorOf<>(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
                    )
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    4, 5
                )
            )
        ).affirm();
    }

    @Test
    void sliceTheHead() {
        new Assertion<>(
            "Must return iterator with the head elements",
            new IterableOf<>(
                new Sliced<>(
                    0,
                    5,
                    new IteratorOf<>(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
                    )
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    1, 2, 3, 4, 5
                )
            )
        ).affirm();
    }

    @Test
    void sliceTheWholeTail() {
        new Assertion<>(
            "Must return the iterator of tail elements",
            new IterableOf<>(
                new Sliced<>(
                    5,
                    new IteratorOf<>(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
                    )
                )
            ),
            new IsEqual<>(
                new IterableOf<>(
                    6, 7, 8, 9, 0
                )
            )
        ).affirm();
    }

    @Test
    void failSlicing() {
        new Assertion<>(
            "Must fail on slicing",
            () -> new Constant<>(
                new Sliced<>(
                    0, 0,
                    new IteratorOf<>(
                        1
                    )
                ).next()
            ).value(),
            new Throws<>(
                "The iterator doesn't have items any more",
                NoSuchElementException.class
            )
        ).affirm();
    }
}
