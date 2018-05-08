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

package org.cactoos.func;

import org.cactoos.matchers.RunsInThreads;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link SyncProc}.
 *
 * @author Alexander Menshikov (sharplermc@gmail.com)
 * @version $Id$
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class SyncProcTest {
    @Test
    public void procWorksInThreads() {
        final int threads = 100;
        final int[] counter = new int[]{0};
        MatcherAssert.assertThat(
            "Sync func with proc can't work well in multiple threads",
            func -> func.apply(1),
            new RunsInThreads<>(
                new FuncOf<>(
                    new SyncProc<Integer>(
                        new ProcOf<>(
                            input -> counter[0] = counter[0] + input
                        )
                    ),
                    true
                ),
                threads
            )
        );
        MatcherAssert.assertThat(counter[0], Matchers.equalTo(threads));
    }

    @Test
    public void runnableWorksInThreads() {
        final int threads = 100;
        final int[] counter = new int[]{0};
        MatcherAssert.assertThat(
            "Sync func with runnable can't work well in multiple threads",
            func -> func.apply(1),
            new RunsInThreads<>(
                new FuncOf<>(
                    new SyncProc<Integer>(
                        () -> counter[0] = counter[0] + 1
                    ),
                    true
                ),
                threads
            )
        );
        MatcherAssert.assertThat(counter[0], Matchers.equalTo(threads));
    }
}
