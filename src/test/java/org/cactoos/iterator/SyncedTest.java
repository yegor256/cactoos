/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test for {@link Synced}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle TodoCommentCheck (500 lines)
 * @todo #1115:30min Continue speeding up the `mvn test` goal until it
 *  executes in less than 10 seconds, or as fast as reasonably possible.
 *  A good solution would be reducing the number of tries to at most 10
 *  in the two tests below with the for loop. After some manual testing
 *  to evaluate actual overlaps of threads (see code details in
 *  https://www.yegor256.com/2018/03/27/how-to-test-thread-safety.html),
 *  it seems most of the tries results in 0 overlapping thread. This is
 *  why so much tries are needed in the for loop for the tests to be sound.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class SyncedTest {

    @Test
    public void syncIteratorReturnsCorrectValuesWithExternalLock() {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        new Assertion<>(
            "Unexpected value found.",
            new ListOf<>(
                new Synced<>(
                    lock, new IteratorOf<>("a", "b")
                )
            ).toArray(),
            new IsEqual<>(new Object[]{"a", "b"})
        ).affirm();
    }

    @Test
    public void syncIteratorReturnsCorrectValuesWithInternalLock() {
        new Assertion<>(
            "Unexpected value found.",
            new ListOf<>(
                new Synced<>(
                    new IteratorOf<>("a", "b")
                )
            ).toArray(),
            new IsEqual<>(new Object[]{"a", "b"})
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void correctValuesForConcurrentNextNext() {
        for (int iter = 0; iter < 5000; iter += 1) {
            new Assertion<>(
                "",
                map -> {
                    new Assertion<>(
                        "",
                        map.next(),
                        Matchers.anyOf(
                            new IsEqual<>("a"),
                            new IsEqual<>("b")
                        )
                    ).affirm();
                    return true;
                },
                new RunsInThreads<>(
                    new Synced<>(
                        new IteratorOf<>("a", "b")
                    ),
                    2
                )
            ).affirm();
        }
    }

    @Test
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void correctValuesForConcurrentNextHasNext() {
        for (int iter = 0; iter < 5000; iter += 1) {
            new Assertion<>(
                "",
                map -> {
                    new Assertion<>(
                        "",
                        map.hasNext(),
                        Matchers.anyOf(
                            new IsEqual<>(true),
                            new IsEqual<>(true)
                        )
                    ).affirm();
                    new Assertion<>(
                        "",
                        map.next(),
                        Matchers.anyOf(
                            new IsEqual<>("a"),
                            new IsEqual<>("b")
                        )
                    ).affirm();
                    new Assertion<>(
                        "",
                        map.hasNext(),
                        Matchers.anyOf(
                            new IsEqual<>(true),
                            new IsEqual<>(false)
                        )
                    ).affirm();
                    return true;
                },
                new RunsInThreads<>(
                    new Synced<>(
                        new IteratorOf<>("a", "b")
                    ),
                    2
                )
            ).affirm();
        }
    }
}
