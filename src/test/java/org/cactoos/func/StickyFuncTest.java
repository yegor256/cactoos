/*
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
package org.cactoos.func;

import java.security.SecureRandom;
import org.cactoos.Func;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link StickyFunc}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class StickyFuncTest {

    @Test
    public void cachesFuncResults() throws Exception {
        final Func<Boolean, Integer> func = new StickyFunc<>(
            input -> new SecureRandom().nextInt()
        );
        MatcherAssert.assertThat(
            func.apply(true) + func.apply(true),
            Matchers.equalTo(func.apply(true) + func.apply(true))
        );
    }

    @Test
    public void cachesWithLimitedBuffer() throws Exception {
        final Func<Integer, Integer> func = new StickyFunc<>(
            input -> new SecureRandom().nextInt(), 2
        );
        final int first = func.apply(0);
        final int second = func.apply(1);
        MatcherAssert.assertThat(
            first + second,
            Matchers.equalTo(func.apply(0) + func.apply(1))
        );
        final int third = func.apply(-1);
        MatcherAssert.assertThat(
            second + third,
            Matchers.equalTo(func.apply(1) + func.apply(-1))
        );
    }

    @Test
    public void cachesWithZeroBuffer() throws Exception {
        final Func<Boolean, Integer> func = new StickyFunc<>(
            input -> new SecureRandom().nextInt(), 0
        );
        MatcherAssert.assertThat(
            func.apply(true) + func.apply(true),
            Matchers.not(Matchers.equalTo(func.apply(true) + func.apply(true)))
        );
    }

}
