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

import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.cactoos.list.ListOf;
import org.cactoos.matchers.RunsInThreads;
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
                    new ListOf<>("a", "b").iterator(), lock
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
                    new ListOf<>("a", "b").iterator()
                )
            ).toArray(),
            Matchers.equalTo(new Object[]{"a", "b"})
        );
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void correctValuesForConcurrentNextNext() {
        for (int iter = 0; iter < 5000; iter += 1) {
            MatcherAssert.assertThat(
                "",
                map -> {
                    MatcherAssert.assertThat(
                        map.next(),
                        Matchers.anyOf(
                            Matchers.equalTo("a"),
                            Matchers.equalTo("b")
                        )
                    );
                    return true;
                },
                new RunsInThreads<>(
                    new SyncIterator<>(
                        new ListOf<>("a", "b").iterator()
                    ),
                    2
                )
            );
        }
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void correctValuesForConcurrentNextHasNext() {
        for (int iter = 0; iter < 5000; iter += 1) {
            MatcherAssert.assertThat(
                "",
                map -> {
                    MatcherAssert.assertThat(
                        map.hasNext(),
                        Matchers.anyOf(
                            Matchers.equalTo(true),
                            Matchers.equalTo(true)
                        )
                    );
                    MatcherAssert.assertThat(
                        map.next(),
                        Matchers.anyOf(
                            Matchers.equalTo("a"),
                            Matchers.equalTo("b")
                        )
                    );
                    MatcherAssert.assertThat(
                        map.hasNext(),
                        Matchers.anyOf(
                            Matchers.equalTo(true),
                            Matchers.equalTo(false)
                        )
                    );
                    return true;
                },
                new RunsInThreads<>(
                    new SyncIterator<>(
                        new ListOf<>("a", "b").iterator()
                    ),
                    2
                )
            );
        }
    }

}
