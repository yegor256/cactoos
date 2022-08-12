/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
package org.cactoos.text;

import org.cactoos.Text;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.object.HasToString;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Sticky}.
 * @since 0.47
 */
final class StickyTest {
    @Test
    void cachesResult() {
        final Text sticky = new Sticky(new Randomized());
        new Assertion<>(
            "must be the same",
            sticky,
            new IsText(sticky)
        ).affirm();
    }

    @Test
    void equalsItself() {
        final Text random = new Randomized();
        final Text sticky = new Sticky(random);
        new Assertion<>(
            "must be the same as itself",
            sticky,
            new AllOf<Text>(
                new IsEqual<>(sticky),
                new IsNot<>(new IsEqual<>(random))
            )
        ).affirm();
    }

    @Test
    void hasProperToString() {
        final String str = "Hello";
        new Assertion<>(
            "must have toString method",
            new Sticky(() -> str),
            new HasToString<>(new IsEqual<>(str))
        ).affirm();
    }
}
