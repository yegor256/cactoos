/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Objects;
import org.cactoos.list.ListOf;
import org.cactoos.proc.ForEach;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.collection.IsIterableWithSize;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Matched}.
 *
 * @since 0.39
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class MatchedTest {

    @Test
    void iterator() {
        new Assertion<>(
            "All elements have correlation function as `equal`",
            new Matched<>(
                new IterableOf<>(1, 2, 3),
                new IterableOf<>(1, 2, 3)
            ),
            new HasValues<>(1, 2, 3)
        ).affirm();
    }

    @Test
    void noCorrelationWithBiggerSecondIterable() {
        new Assertion<>(
            "All elements have correlation function as 'endsWith'",
            () -> new ListOf<>(
                new Matched<>(
                    (fst, snd) -> fst.endsWith("elem") && snd.endsWith("elem"),
                    new IterableOf<>("1st elem", "2nd elem"),
                    new IterableOf<>("'A' elem", "'B' elem", "'C' elem")
                )
            ),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void noCorrelationWithSmallerSecondIterable() {
        new Assertion<>(
            "All elements have correlation function as `endsWith`",
            () -> new ListOf<>(
                new Matched<>(
                    (fst, snd) -> fst.endsWith("elem") && snd.endsWith("elem"),
                    new IterableOf<>("1st elem", "2nd elem", "3rd elem"),
                    new IterableOf<>("`A` elem", "`B` elem")
                )
            ),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void endsWith() {
        new Assertion<>(
            "All elements have correlation function as `endsWith`",
            new Matched<>(
                (fst, snd) -> fst.endsWith("elem") && snd.endsWith("elem"),
                new IterableOf<>("1st elem", "2nd elem", "3rd elem"),
                new IterableOf<>("`A` elem", "`B` elem", "'C' elem")
            ),
            new IsIterableWithSize<>(
                new IsEqual<>(3)
            )
        ).affirm();
    }

    @Test
    void matchedAsNumbers() {
        new Assertion<>(
            "All elements must be treated as Number",
            new Matched<>(
                (Number fst, Number snd) -> fst.intValue() == snd.intValue(),
                new IterableOf<>(1d, 2d, 3d),
                new IterableOf<>(1L, 2L, 3L)
            ),
            new IsIterableWithSize<>(
                new IsEqual<>(3)
            )
        ).affirm();
    }

    @Test
    void noCorrelation() {
        new Assertion<>(
            "Should fail if there is no correlation",
            () -> new LengthOf(
                new Matched<>(
                    (fst, snd) -> fst.endsWith("elem") && snd.endsWith("elem"),
                    new IterableOf<>("1st elem", "2nd"),
                    new IterableOf<>("`A` elem", "`B` elem")
                )
            ).value(),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void nonNullCorrelation() {
        new Assertion<>(
            "Should fail if parameter is null",
            () -> new LengthOf(
                new Matched<>(
                    (fst, snd) -> fst != null && snd != null,
                    new IterableOf<>("1st elem", "2nd elem", "3rd elem"),
                    new IterableOf<>("`A` elem", null, "'C' elem")
                )
            ).value(),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void iterablesOfDifferentTypes() {
        new Assertion<>(
            "All elements must be treated according to their type",
            new Matched<>(
                (Number fst, String snd) -> fst.intValue() == Integer.parseInt(snd),
                new IterableOf<>(1, 2, 3),
                new IterableOf<>("1", "2", "3")
            ),
            new IsIterableWithSize<>(
                new IsEqual<>(3)
            )
        ).affirm();
    }

    @Test
    void shouldNotChangeAfterTraversing() throws Exception {
        final Iterable<Integer> matched = new Matched<>(
            Objects::equals,
            new IterableOf<>(1, 2, 3),
            new IterableOf<>(1, 2, 3)
        );
        final Iterable<Integer> copy = new ListOf<>(matched);
        new ForEach<>(
            (Integer ignored) -> {
            }
        ).exec(matched);
        new Assertion<>(
            "Elements must not be removed",
            matched,
            new IsEqual<>(
                copy
            )
        ).affirm();
    }

}
