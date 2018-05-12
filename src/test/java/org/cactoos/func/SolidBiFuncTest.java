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
import org.cactoos.BiFunc;
import org.cactoos.Func;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link SolidBiFunc}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class SolidBiFuncTest {
    @Test
    public void testThatFuncIsSynchronized() {
        final int threads = 100;
        final int[] shared = new int[]{0};
        final BiFunc<Integer, Integer, Boolean> testable =
            new SolidBiFunc<>(
                (first, second) -> {
                    shared[0] = shared[0] + 1;
                    return true;
                }
            );
        MatcherAssert.assertThat(
            "SolidBiFunc can't work properly in concurrent threads.",
            func -> func.apply(true),
            new RunsInThreads<>(
                (Func<Boolean, Boolean>) input -> testable.apply(1, 1),
                threads
            )
        );
        MatcherAssert.assertThat(
            "Shared resource has been modified by multiple threads.",
            shared[0],
            Matchers.is(1)
        );
    }

    @Test
    public void testThatFuncResultCacheIsLimited() throws Exception {
        final BiFunc<Integer, Integer, Integer> func =
            new SolidBiFunc<>(
                (first, second) -> new SecureRandom().nextInt(),
                1
            );
        MatcherAssert.assertThat(
            "Result of (0, 0) call wasn't invalidated.",
            func.apply(0, 0) + func.apply(1, 1),
            Matchers.not(func.apply(1, 1) + func.apply(0, 0))
        );
    }
}
