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
 */
public class SyncIteratorTest {
    /**
     * The lock to sync threads.
     */
    private final ReentrantReadWriteLock lock =
        new ReentrantReadWriteLock();
    /**
     * The {@link Iterator} containing data to test.
     */
    private final Iterator<String> strings =
        Arrays.asList("a", "a").iterator();
    /**
     * {@link SyncIterator} to test.
     */
    private final SyncIterator<String> iterator =
        new SyncIterator<>(this.strings, this.lock);

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public final void testNextBlocksDifferentThread() {
        final ExecutorService executor = Executors.newFixedThreadPool(1);
        this.lock.writeLock().lock();
        final List<String> callsToIterator = new ArrayList<>(0);
        executor.submit(
            () -> {
                this.iterator.next();
                callsToIterator.add("otherThread");
            }
        );
        this.iterator.next();
        callsToIterator.add("thisThread");
        this.lock.writeLock().unlock();
        executor.shutdown();
        MatcherAssert.assertThat(
            "Unexpected result from iterator.",
            callsToIterator,
            Matchers.contains("thisThread", "otherThread")
        );
    }

}
