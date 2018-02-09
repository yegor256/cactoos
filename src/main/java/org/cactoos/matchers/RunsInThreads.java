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
package org.cactoos.matchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.cactoos.Func;
import org.cactoos.scalar.And;
import org.cactoos.scalar.UncheckedScalar;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher for {@link Func} that must run in multiple threads.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <T> Type of input
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class RunsInThreads<T> extends TypeSafeMatcher<Func<T, Boolean>> {

    /**
     * Input.
     */
    private final T input;

    /**
     * Total cid of threads to run.
     */
    private final int total;

    /**
     * Ctor.
     */
    public RunsInThreads() {
        this(null);
    }

    /**
     * Ctor.
     * @param object Input object
     */
    public RunsInThreads(final T object) {
        // @checkstyle MagicNumber (1 line)
        this(object, Runtime.getRuntime().availableProcessors() << 4);
    }

    /**
     * Ctor.
     * @param object Input object
     */
    public RunsInThreads(final T object, final int threads) {
        super();
        this.input = object;
        this.total = threads;
    }

    @Override
    public boolean matchesSafely(final Func<T, Boolean> func) {
        final ExecutorService service = Executors.newFixedThreadPool(
            this.total
        );
        final CountDownLatch latch = new CountDownLatch(1);
        final Collection<Future<Boolean>> futures = new ArrayList<>(this.total);
        final Callable<Boolean> task = () -> {
            latch.await();
            return func.apply(this.input);
        };
        for (int thread = 0; thread < this.total; ++thread) {
            futures.add(service.submit(task));
        }
        latch.countDown();
        final boolean matches = new UncheckedScalar<>(
            new And(
                (Func<Future<Boolean>, Boolean>) Future::get,
                futures
            )
        ).value();
        service.shutdown();
        return matches;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("failed");
    }
}
