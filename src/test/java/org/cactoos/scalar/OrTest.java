/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
import java.util.LinkedList;
import java.util.List;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.matchers.ScalarHasValue;
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
 * @checkstyle MagicNumber (500 line)
 */
public final class OrTest {

    @Test
    public void allFalse() throws Exception {
        MatcherAssert.assertThat(
            new Or(
                new False(),
                new False(),
                new False(),
                new False(),
                new False()
            ),
            new ScalarHasValue<>(false)
        );
    }

    @Test
    public void oneTrue() throws Exception {
        MatcherAssert.assertThat(
            new Or(
                new False(),
                new True(),
                new False(),
                new False(),
                new False()
            ),
            new ScalarHasValue<>(true)
        );
    }

    @Test
    public void allTrue() throws Exception {
        MatcherAssert.assertThat(
            new Or(
                new IterableOf<Scalar<Boolean>>(
                    new True(),
                    new True(),
                    new True(),
                    new True(),
                    new True()
                )
            ),
            new ScalarHasValue<>(true)
        );
    }

    @Test
    public void emptyIterator() throws Exception {
        MatcherAssert.assertThat(
            new Or(Collections.emptyList()),
            new ScalarHasValue<>(false)
        );
    }

    @Test
    public void testProc() throws Exception {
        final List<Integer> list = new LinkedList<>();
        new Or(
            (Proc<Integer>) list::add,
            new IterableOf<>(1, 2, 3, 4)
        ).value();
        MatcherAssert.assertThat(
            list.size(),
            Matchers.equalTo(4)
        );
    }

    @Test
    public void testProcVarargs() throws Exception {
        final List<Integer> list = new LinkedList<>();
        new Or(
            (Proc<Integer>) list::add,
            2, 3, 4
        ).value();
        MatcherAssert.assertThat(
            list.size(),
            Matchers.equalTo(3)
        );
    }

    @Test
    public void testFunc() throws Exception {
        MatcherAssert.assertThat(
            new Or(
                input -> input > 0,
                new IterableOf<>(-1, 1, 0)
            ),
            new ScalarHasValue<>(true)
        );
    }

    @Test
    public void testFuncVarargs() throws Exception {
        MatcherAssert.assertThat(
            new Or(
                input -> input > 0,
                -1, -2, 0
            ),
            new ScalarHasValue<>(false)
        );
    }
}
