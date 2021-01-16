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

import java.util.Collections;
import org.cactoos.scalar.ItemAt;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test Case for {@link Cycled}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class CycledTest {

    @Test
    void repeatIterableTest() {
        final String expected = "two";
        new Assertion<>(
            "Can't repeat iterable",
            new ItemAt<>(
                // @checkstyle MagicNumberCheck (1 line)<
                7, new Cycled<>(
                    new IterableOf<>(
                        "one", expected, "three"
                    )
                )
            ),
            new ScalarHasValue<>(
                expected
            )
        ).affirm();
    }

    @Test
    void notCycledEmptyTest() throws Exception {
        MatcherAssert.assertThat(
            "Can't generate an empty iterable",
            new LengthOf(
                new Cycled<>(
                    Collections::emptyIterator
                )
            ).value(),
            Matchers.equalTo(0)
        );
    }
}
