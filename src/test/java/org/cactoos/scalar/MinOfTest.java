/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
package org.cactoos.scalar;

import java.util.Collections;
import java.util.NoSuchElementException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link MinOf}.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.10
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class MinOfTest {

    @Test(expected = NoSuchElementException.class)
    public void minAmongEmptyTest() throws Exception {
        new MinOf<>(() -> Collections.emptyIterator()).value();
    }

    @Test
    public void minAmongOneTest() throws Exception {
        final int num = 10;
        MatcherAssert.assertThat(
            "Can't find the smaller among one",
            new MinOf<Integer>(() -> new Integer(num)).value(),
            Matchers.equalTo(num)
        );
    }

    @Test
    public void minAmongManyTest() throws Exception {
        final int num = -1;
        MatcherAssert.assertThat(
            "Can't find the smaller among many",
            new MinOf<Integer>(
                () -> new Integer(1),
                () -> new Integer(0),
                () -> new Integer(num),
                () -> new Integer(2)
             ).value(),
            Matchers.equalTo(num)
        );
    }

    @Test
    public void withListOfNumbers() throws Exception {
        final int ivalue = 1;
        MatcherAssert.assertThat(
            "Can't find the smallest in the integer array",
            new MinOf.Integers(ivalue, 2, 3).value(),
            Matchers.equalTo(ivalue)
        );
        final double dvalue = 0.95d;
        MatcherAssert.assertThat(
            "Can't find the smallest in the double array",
            new MinOf.Doubles(1.0d, dvalue, 3.0d).value(),
            Matchers.equalTo(dvalue)
        );
        final long lvalue = -30000000000L;
        MatcherAssert.assertThat(
            "Can't find the smallest in the long array",
            new MinOf.Longs(
                10000000000L,
                20000000000L,
                lvalue
            )
            .value(),
            Matchers.equalTo(lvalue)
        );
    }
}
