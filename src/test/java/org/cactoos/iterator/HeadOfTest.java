/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test Case for {@link HeadOf}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class HeadOfTest {

    @Test
        void headIterator() {
        MatcherAssert.assertThat(
            "Must skip elements in iterator",
            new IterableOf<>(
                new HeadOf<>(
                    2,
                    new IteratorOf<>(
                        "alpha", "beta", "gamma", "delta"
                    )
                )
            ),
            new HasValues<>(
                "alpha",
                "beta"
            )
        );
    }

    @Test
    void returnsIntactIterator() {
        MatcherAssert.assertThat(
            "Must return an intact iterator",
            new IterableOf<>(
                new HeadOf<>(
                    3,
                    new IteratorOf<>(
                        "epsilon", "zeta"
                    )
                )
            ),
            new HasSize(2)
        );
    }

    @Test
    void returnsEmptyIterator() {
        MatcherAssert.assertThat(
            "Must throw an exception if empty",
            () -> new HeadOf<>(
                0,
                new IteratorOf<>(
                    "eta", "theta"
                )
            ).next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void emptyIteratorForNegativeSize() {
        MatcherAssert.assertThat(
            "Must throw an exception for negative size",
            () -> new HeadOf<>(
                -1,
                new IteratorOf<>(
                    "iota", "kappa"
                )
            ).next(),
            new Throws<>(NoSuchElementException.class)
        );
    }

    @Test
    void iteratesForEachRemaining() {
        final List<String> lst = new ArrayList<>(2);
        new HeadOf<>(
            2,
            new IteratorOf<>(
                "lambda", "mu", "nu", "xi"
            )
        ).forEachRemaining(
            lst::add
        );
        MatcherAssert.assertThat(
            "Should iterate over 2 head elements",
            lst,
            new HasValues<>(
                "lambda",
                "mu"
            )
        );
    }

    @Test
    void removeNotSupported() {
        MatcherAssert.assertThat(
            "Remove should not be supported",
            () -> {
                new HeadOf<>(
                    1,
                    new IteratorOf<>(
                        "omicron", "pi", "rho", "sigma"
                    )
                ).remove();
                return "Should have thrown exception";
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }

}
