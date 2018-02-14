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
package org.cactoos.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import junit.framework.TestCase;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link SyncIterator}.
 *
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle TodoCommentCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class SyncIteratorTest {

    @Test
    public void syncIteratorReturnsCorrectValuesWithExternalLock() {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        MatcherAssert.assertThat(
            "Unexpected value found.",
            new ListOf<>(
                new SyncIterator<>(
                    Arrays.asList("a", "b").iterator(), lock
                )
            ).toArray(),
            Matchers.equalTo(new Object[]{"a", "b"})
        );
    }

    @Test
    public void syncIteratorReturnsCorrectValuesWithInternalLock() {
        MatcherAssert.assertThat(
            "Unexpected value found.",
            new ListOf<>(
                new SyncIterator<>(
                    Arrays.asList("a", "b").iterator()
                )
            ).toArray(),
            Matchers.equalTo(new Object[]{"a", "b"})
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void correctValuesForConcurrentNextNext()
        throws InterruptedException {
        for (int iter = 0; iter < 5000; iter += 1) {
            final List<String> list = Arrays.asList("a", "b");
            final SyncIterator<String> iterator = new SyncIterator<>(
                list.iterator()
            );
            final List<Object> sync =
                Collections.synchronizedList(
                    new ArrayList<>(list.size())
                );
            final Runnable first = () -> {
                sync.add(iterator.next());
            };
            final Runnable second = () -> {
                sync.add(iterator.next());
            };
            final List<Runnable> test = new ArrayList<>(list.size());
            test.add(first);
            test.add(second);
            new Concurrent(test).launch();
            MatcherAssert.assertThat(
                "Missing the list items(s) (next()).",
                sync,
                Matchers.containsInAnyOrder("a", "b")
            );
        }
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void correctValuesForConcurrentNextHasNext()
        throws InterruptedException {
        for (int iter = 0; iter < 5000; iter += 1) {
            final List<String> list = Arrays.asList("a", "b");
            final SyncIterator<String> iterator = new SyncIterator<>(
                list.iterator()
            );
            final List<Object> sync =
                Collections.synchronizedList(
                    new ArrayList<>(list.size())
                );
            final Runnable first = () -> {
                sync.add(iterator.next());
            };
            final Runnable second = () -> {
                sync.add(iterator.next());
            };
            final Runnable third = () -> {
                sync.add(iterator.hasNext());
            };
            final List<Runnable> test = new ArrayList<>(list.size() + 1);
            test.add(first);
            test.add(second);
            test.add(third);
            new Concurrent(test).launch();
            MatcherAssert.assertThat(
                "Missing the list items(s) (next()).",
                sync,
                Matchers.allOf(
                    Matchers.hasItem("a"),
                    Matchers.hasItem("b")
                )
            );
            MatcherAssert.assertThat(
                "Missing hasNext() value.",
                sync,
                Matchers.anyOf(
                    Matchers.hasItem(true),
                    Matchers.hasItem(false)
                )
            );
        }
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void correctValuesForConcurrentHasNextHasNext()
        throws InterruptedException {
        for (int iter = 0; iter < 5000; iter += 1) {
            final List<String> list = Arrays.asList("a", "b");
            final SyncIterator<String> iterator = new SyncIterator<>(
                list.iterator()
            );
            final List<Object> sync =
                Collections.synchronizedList(
                    new ArrayList<>(list.size())
                );
            final Runnable first = () -> {
                sync.add(iterator.hasNext());
            };
            final Runnable second = () -> {
                sync.add(iterator.hasNext());
            };
            final List<Runnable> test = new ArrayList<>(list.size());
            test.add(first);
            test.add(second);
            new Concurrent(test).launch();
            MatcherAssert.assertThat(
                "Missing hasNext() value(s).",
                sync,
                Matchers.contains(true, true)
            );
        }
    }

    /**
     * Tests runnables for concurrency issues.
     */
    private final class Concurrent {

        /**
         * Runnables to run in different threads.
         */
        private final List<? extends Runnable> runnables;

        /**
         * Collected exceptions.
         */
        private final List<Throwable> exceptions;

        /**
         * Thread pool.
         */
        private final ExecutorService pool;

        /**
         * All executor threads are ready.
         */
        private final CountDownLatch ready;

        /**
         * Start countdown with first thread.
         */
        private final CountDownLatch init;

        /**
         * All threads ready.
         */
        private final CountDownLatch done;

        Concurrent(final List<? extends Runnable> runnables) {
            this.runnables = runnables;
            this.exceptions =  Collections.synchronizedList(
                new ArrayList<Throwable>(
                    runnables.size()
                )
            );
            this.pool = Executors.newFixedThreadPool(runnables.size());
            this.ready = new CountDownLatch(runnables.size());
            this.init = new CountDownLatch(1);
            this.done = new CountDownLatch(runnables.size());
        }

        //@checkstyle IllegalCatchCheck (100 lines)
        @SuppressWarnings({"PMD.ProhibitPlainJunitAssertionsRule",
            "PMD.AvoidCatchingThrowable"})
        public void launch() throws InterruptedException {
            try {
                for (final Runnable runnable : this.runnables) {
                    this.pool.submit(
                        () -> {
                            this.ready.countDown();
                            try {
                                this.init.await();
                                runnable.run();
                            } catch (final Throwable ex) {
                                this.exceptions.add(ex);
                            } finally {
                                this.done.countDown();
                            }
                        });
                }
                TestCase.assertTrue(
                    "Timeout initializing threads! Perform longer thread init.",
                    this.ready.await(
                        this.runnables.size() * 20,
                        TimeUnit.MILLISECONDS
                    )
                );
                this.init.countDown();
                TestCase.assertTrue(
                    String.format(
                        "Timeout! More than %d seconds",
                        10
                    ),
                    this.done.await(100, TimeUnit.SECONDS)
                );
            } finally {
                this.pool.shutdownNow();
            }
            TestCase.assertTrue(
                String.format(
                    "%s failed with exception(s) %s",
                    "Error",
                    this.exceptions.toString()
                ),
                this.exceptions.isEmpty()
            );
        }

    }

}
