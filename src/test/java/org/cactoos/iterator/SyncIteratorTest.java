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
package org.cactoos.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link SyncIterator}.
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public class SyncIteratorTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public final void testNextBlocksDifferentThread() throws Exception {
        final ReentrantReadWriteLock lock =
            new ReentrantReadWriteLock();
        final Iterator<String> strings =
            Arrays.asList("a", "a").iterator();
        final SyncIterator<String> iterator =
            new SyncIterator<>(strings, lock);
        final ExecutorService executor =
                Executors.newFixedThreadPool(1);

        lock.writeLock().lock();
        final List<String> calls = new ArrayList<>(0);
        final Future<?> other = executor.submit(
            () -> {
                iterator.next();
                calls.add("otherThread");
            }
        );
        iterator.next();
        calls.add("thisThread");
        lock.writeLock().unlock();
        executor.shutdown();
        other.get();
        MatcherAssert.assertThat(
            "Unexpected result from iterator.",
            calls,
            Matchers.contains("thisThread", "otherThread")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public final void testHasNextNotBlocksDifferentThread() throws Exception {
        final ReentrantReadWriteLock lock =
            new ReentrantReadWriteLock();
        final Iterator<String> strings =
            Arrays.asList("a", "a").iterator();
        final SyncIterator<String> iterator =
            new SyncIterator<>(strings, lock);
        final ExecutorService executor =
                Executors.newFixedThreadPool(1);

        lock.readLock().lock();
        final List<String> calls = new ArrayList<>(0);
        final Future<?> other = executor.submit(
            () -> {
                iterator.hasNext();
                calls.add("otherThread");
            }
        );
        Thread.sleep(100);
        iterator.hasNext();
        calls.add("thisThread");
        lock.readLock().unlock();
        executor.shutdown();
        other.get();
        MatcherAssert.assertThat(
            "Unexpected result from iterator.",
            calls,
            Matchers.contains("otherThread", "thisThread")
        );
    }

}
