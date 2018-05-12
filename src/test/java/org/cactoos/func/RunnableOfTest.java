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

import java.util.concurrent.atomic.AtomicBoolean;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.MatcherOf;

/**
 * Test case for {@link RunnableOf}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class RunnableOfTest {

    @Test
    public void convertsFuncIntoRunnable() {
        final AtomicBoolean done = new AtomicBoolean();
        MatcherAssert.assertThat(
            "Can't execute Runnable with Func",
            new RunnableOf<>(
                input -> {
                    done.set(true);
                    return 1;
                }
            ),
            new MatcherOf<Runnable>(
                input -> {
                    input.run();
                    return done.get();
                }
            )
        );
    }

    @Test
    public void convertsProcIntoRunnable() {
        final AtomicBoolean done = new AtomicBoolean();
        MatcherAssert.assertThat(
            "Can't execute Runnable with ProcOf",
            new RunnableOf<>(
                new ProcOf<>(
                    input -> {
                        done.set(true);
                        return 1;
                    }
                )
            ),
            new MatcherOf<Runnable>(
                input -> {
                    input.run();
                    return done.get();
                }
            )
        );
    }

    @Test
    public void convertsCallableIntoRunnable() {
        final AtomicBoolean done = new AtomicBoolean();
        MatcherAssert.assertThat(
            "Can't execute Runnable with Callable",
            new RunnableOf<>(
                () -> {
                    done.set(true);
                    return null;
                }
            ),
            new MatcherOf<Runnable>(
                input -> {
                    input.run();
                    return done.get();
                }
            )
        );
    }

}
