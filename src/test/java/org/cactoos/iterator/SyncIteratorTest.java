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
import java.util.Iterator;
import java.util.List;
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
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final MockIterator<String> mock = new MockIterator<>();
        final SyncIterator<String> iterator = new SyncIterator<>(mock, lock);
        Thread.currentThread().setName("thisThread");
        lock.writeLock().lock();
        final Thread other = new Thread(iterator::next, "otherThread");
        other.start();
        iterator.next();
        lock.writeLock().unlock();
        other.join(100);
        MatcherAssert.assertThat(
            "Unexpected calls order to next() from iterator.",
            mock.getNextCalls(),
            Matchers.contains("thisThread", "otherThread")
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public final void testHasNextNotBlocksDifferentThread() throws Exception {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final MockIterator<String> mock = new MockIterator<>();
        final SyncIterator<String> iterator = new SyncIterator<>(mock, lock);
        Thread.currentThread().setName("thisThread");
        lock.readLock().lock();
        final Thread other = new Thread(iterator::hasNext, "otherThread");
        other.start();
        other.join(100);
        iterator.hasNext();
        lock.readLock().unlock();
        MatcherAssert.assertThat(
            "Unexpected calls order to hasNext() from iterator.",
            mock.getHasNextCalls(),
            Matchers.contains("otherThread", "thisThread")
        );
    }

    /**
     * Mock iterator to track calls to next and hasNext methods.
     * @param <T> Type of the Iterator
     * @checkstyle MemberNameCheck (500 lines)
     */
    private class MockIterator<T> implements Iterator<T> {
        /**
         * List of thread names called hasNext method.
         */
        private final List<String> hasNextCalls = new ArrayList<>(0);
        /**
         * List of thread names called next method.
         */
        private final List<String> nextCalls = new ArrayList<>(0);

        @Override
        public boolean hasNext() {
            this.hasNextCalls.add(Thread.currentThread().getName());
            return true;
        }

        @Override
        public T next() {
            this.nextCalls.add(Thread.currentThread().getName());
            return null;
        }

        public List<String> getHasNextCalls() {
            return this.hasNextCalls;
        }

        public List<String> getNextCalls() {
            return this.nextCalls;
        }
    }
}
