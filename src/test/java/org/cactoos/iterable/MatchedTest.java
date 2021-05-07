/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.iterable;

import java.util.Objects;
import org.cactoos.list.ListOf;
import org.cactoos.proc.ForEach;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.collection.IsIterableWithSize;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Matched}.
 *
 * @since 0.39
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class MatchedTest {

    @Test
    public void iterator() {
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
    public void noCorrelationWithBiggerSecondIterable() {
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
    public void noCorrelationWithSmallerSecondIterable() {
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
    public void endsWith() {
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
    public void matchedAsNumbers() {
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

    @Test(expected = IllegalStateException.class)
    public void noCorrelation() throws Exception {
        new LengthOf(
            new Matched<>(
                (fst, snd) -> fst.endsWith("elem") && snd.endsWith("elem"),
                new IterableOf<>("1st elem", "2nd"),
                new IterableOf<>("`A` elem", "`B` elem")
            )
        ).value();
        Assert.fail("There is no 'endsWith'correlation between 2nd elements");
    }

    @Test(expected = IllegalStateException.class)
    public void nonNullCorrelation() throws Exception {
        new LengthOf(
            new Matched<>(
                (fst, snd) -> fst != null && snd != null,
                new IterableOf<>("1st elem", "2nd elem", "3rd elem"),
                new IterableOf<>("`A` elem", null, "'C' elem")
            )
        ).value();
        Assert.fail("There is no 'non-null' correlation between 2nd elements");
    }

    @Test
    public void iterablesOfDifferentTypes() {
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
    public void shouldNotChangeAfterTraversing() throws Exception {
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
