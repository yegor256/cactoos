/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test case for {@link Matched}.
 *
 * @since 0.39
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class MatchedTest {

    @Test
    public void iterator() {
        MatcherAssert.assertThat(
            "All elements have correlation function as `equal`",
            new Matched<>(
                new IterableOf<>(1, 2, 3),
                new IterableOf<>(1, 2, 3)
            ),
            Matchers.hasItems(1, 2, 3)
        );
    }

    @Test
    public void endsWith() {
        MatcherAssert.assertThat(
            "All elements have correlation function as `endsWith`",
            new Matched<>(
                (fst, snd) -> fst.endsWith("elem") && snd.endsWith("elem"),
                new IterableOf<>("1st elem", "2nd elem", "3rd elem"),
                new IterableOf<>("`A` elem", "`B` elem", "'C' elem")
            ),
            Matchers.iterableWithSize(3)
        );
    }

    @Test(expected = IllegalStateException.class)
    public void noCorrelation() {
        new LengthOf(
            new Matched<>(
                (fst, snd) -> fst.endsWith("elem") && snd.endsWith("elem"),
                new IterableOf<>("1st elem", "2nd"),
                new IterableOf<>("`A` elem", "`B` elem")
            )
        ).intValue();
        Assert.fail("There is no 'endsWith'correlation between 2nd elements");
    }

    @Test(expected = IllegalStateException.class)
    public void nonNullCorrelation() {
        new LengthOf(
            new Matched<>(
                (fst, snd) -> fst != null && snd != null,
                new IterableOf<>("1st elem", "2nd elem", "3rd elem"),
                new IterableOf<>("`A` elem", null, "'C' elem")
            )
        ).intValue();
        Assert.fail("There is no 'non-null' correlation between 2nd elements");
    }

}
