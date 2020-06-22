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
package org.cactoos.func;

import java.util.LinkedList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link SyncFunc}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class SyncFuncTest {

    @Test
    public void funcWorksInThreads() {
        final List<Integer> list = new LinkedList<>();
        final int threads = 100;
        new Assertion<>(
            "Sync func can't work well in multiple threads",
            func -> func.apply(true),
            new RunsInThreads<SyncFunc<Boolean, Boolean>>(
                new SyncFunc<Boolean, Boolean>(
                    input -> {
                        return list.add(1);
                    }
                ),
                threads
            )
        ).affirm();
        new Assertion<>(
            "",
            list.size(),
            Matchers.equalTo(threads)
        ).affirm();
    }

    @Test
    public void procWorksInThreads() {
        final int threads = 100;
        final int[] counter = new int[]{0};
        new Assertion<>(
            "Sync func with proc can't work well in multiple threads",
            func -> func.apply(1),
            new RunsInThreads<SyncFunc<Integer, Boolean>>(
                new SyncFunc<Integer, Boolean>(
                    new ProcOf<>(
                        input -> counter[0] = counter[0] + input
                    ),
                    true
                ),
                threads
            )
        ).affirm();
        new Assertion<>(
            "",
            counter[0],
            Matchers.equalTo(threads)
        ).affirm();
    }

    @Test
    public void callableWorksInThreads() {
        final int threads = 100;
        final int[] counter = new int[]{0};
        new Assertion<>(
            "Sync func with callable can't work well in multiple threads",
            func -> func.apply(1),
            new RunsInThreads<SyncFunc<Integer, Boolean>>(
                new SyncFunc<Integer, Boolean>(
                    () -> {
                        counter[0] = counter[0] + 1;
                        return true;
                    }
                ),
                threads
            )
        ).affirm();
        new Assertion<>(
            "",
            counter[0],
            Matchers.equalTo(threads)
        ).affirm();
    }

    @Test
    public void runnableWorksInThreads() {
        final int threads = 100;
        final int[] counter = new int[]{0};
        new Assertion<>(
            "Sync func with runnable can't work well in multiple threads",
            func -> func.apply(1),
            new RunsInThreads<SyncFunc<Integer, Boolean>>(
                new SyncFunc<Integer, Boolean>(
                    () -> counter[0] = counter[0] + 1,
                    true
                ),
                threads
            )
        ).affirm();
        new Assertion<>(
            "",
            counter[0],
            Matchers.equalTo(threads)
        ).affirm();
    }
}
