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
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Result}.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class ResultTest {

    @Test
    public void value() throws Exception {
        MatcherAssert.assertThat(
            new Result<>(
                new ConstFunc<>("result"),
                (Scalar<Boolean>) () -> true
            ).value(),
            Matchers.equalTo("result")
        );
    }

    @Test
    public void withResultAsCtorArg() throws Exception {
        MatcherAssert.assertThat(
            new Ternary<>(
                new True(),
                new Result<>(
                    new ConstFunc<>(1),
                    "first"
                ),
                new Result<>(
                    new ConstFunc<>(2),
                    "second"
                )
            ).value(),
            Matchers.equalTo(1)
        );
    }

    @Test
    public void withFuncAsCtorArg() throws Exception {
        MatcherAssert.assertThat(
            new Ternary<>(
                String::isEmpty,
                new ConstFunc<>(3),
                new ConstFunc<>(4),
                "input"
            ).value(),
            Matchers.equalTo(4)
        );
    }
}
