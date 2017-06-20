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
package org.cactoos.func;

import org.cactoos.Scalar;
import org.cactoos.list.ArrayAsIterable;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Or}.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class OrTest {

    @Test
    public void allFalse() throws Exception {
        MatcherAssert.assertThat(
            new Or(
                new ArrayAsIterable<Scalar<Boolean>>(
                    new False(),
                    new False(),
                    new False(),
                    new False(),
                    new False()
                )
            ).asValue(),
            Matchers.equalTo(false)
        );
    }

    @Test
    public void oneTrue() throws Exception {
        MatcherAssert.assertThat(
            new Or(
                new ArrayAsIterable<Scalar<Boolean>>(
                    new False(),
                    new True(),
                    new False(),
                    new False(),
                    new False()
                )
            ).asValue(),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void allTrue() throws Exception {
        MatcherAssert.assertThat(
            new Or(
                new ArrayAsIterable<Scalar<Boolean>>(
                    new True(),
                    new True(),
                    new True(),
                    new True(),
                    new True()
                )
            ).asValue(),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void emptyIterator() throws Exception {
        MatcherAssert.assertThat(
            new Or(
                new ArrayAsIterable<Scalar<Boolean>>()
            ).asValue(),
            Matchers.equalTo(false)
        );
    }
}
