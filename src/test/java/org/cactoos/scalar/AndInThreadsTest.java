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
package org.cactoos.scalar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.cactoos.list.Synced;
import org.cactoos.proc.ProcNoNulls;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.MatcherOf;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link AndInThreads}.
 * @since 0.25
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
final class AndInThreadsTest {

    @Test
    void allTrue() throws Exception {
        new Assertion<>(
            "Each object must be True",
            new AndInThreads(
                new True(),
                new True(),
                new True()
            ),
            new ScalarHasValue<>(true)
        ).affirm();
    }

    @Test
    void oneFalse() throws Exception {
        new Assertion<>(
            "One object must be False",
            new AndInThreads(
                new True(),
                new False(),
                new True()
            ),
            new ScalarHasValue<>(false)
        ).affirm();
    }

    @Test
    void allFalse() throws Exception {
        new Assertion<>(
            "Each object must be False",
            new AndInThreads(
                new IterableOf<Scalar<Boolean>>(
                    new False(),
                    new False(),
                    new False()
                )
            ),
            new ScalarHasValue<>(false)
        ).affirm();
    }

    @Test
    void emptyIterator() throws Exception {
        new Assertion<>(
            "Iterator must be empty",
            new AndInThreads(new IterableOf<Scalar<Boolean>>()),
            new ScalarHasValue<>(true)
        ).affirm();
    }

    @Test
    void iteratesList() {
        final List<String> list = new Synced<>(new ListOf<>());
        new Assertion<>(
            "Must iterate a list with a procedure",
            new AndInThreads(
                new Mapped<>(
                    new FuncOf<>(list::add, () -> true),
                    new IterableOf<>("hello", "world")
                )
            ),
            new ScalarHasValue<>(true)
        ).affirm();
        new Assertion<>(
            "Iterable must contain elements in any order",
            list,
            new IsIterableContainingInAnyOrder<String>(
                new ListOf<Matcher<? super String>>(
                    new MatcherOf<>(
                        text -> {
                            return "hello".equals(text);
                        }
                    ),
                    new MatcherOf<>(
                        text -> {
                            return "world".equals(text);
                        }
                    )
                )
            )
        ).affirm();
    }

    @Test
    void iteratesEmptyList() {
        final List<String> list = new Synced<>(
            new ArrayList<>(2)
        );
        new Assertion<>(
            "Must iterate an empty list",
            new AndInThreads(
                new Mapped<Scalar<Boolean>>(
                    new FuncOf<>(list::add, () -> true), 
                    new IterableOf<String>()
                )
            ),
            new ScalarHasValue<>(
                Matchers.allOf(
                    Matchers.equalTo(true),
                    new MatcherOf<>(
                        value -> {
                            return list.isEmpty();
                        }
                    )
                )
            )
        ).affirm();
    }

    @Test
    void worksWithFunc() throws Exception {
        MatcherAssert.assertThat(
            new AndInThreads(
                input -> input > 0,
                1, -1, 0
            ),
            new ScalarHasValue<>(false)
        );
    }

    @Test
    void worksWithIterableScalarBoolean() throws Exception {
        MatcherAssert.assertThat(
            new AndInThreads(
                new ListOf<Scalar<Boolean>>(
                    new Constant<Boolean>(true),
                    new Constant<Boolean>(true)
                )
            ).value(),
            Matchers.equalTo(true)
        );
    }

    @Test
    void worksWithExecServiceProcValues() throws Exception {
        final List<Integer> list = new Synced<>(new ListOf<>());
        final ExecutorService service = Executors.newSingleThreadExecutor();
        new AndInThreads(
            service,
            new ProcNoNulls<Integer>(list::add),
            1, 2
        ).value();
        MatcherAssert.assertThat(
            list,
            new IsIterableContainingInAnyOrder<Integer>(
                new ListOf<Matcher<? super Integer>>(
                    new MatcherOf<>(
                        value -> {
                            return value.equals(1);
                        }
                    ),
                    new MatcherOf<>(
                        value -> {
                            return value.equals(2);
                        }
                    )
                )
            )
        );
    }

    @Test
    void worksWithExecServiceProcIterable() throws Exception {
        final List<Integer> list = new Synced<>(new ListOf<>());
        final ExecutorService service = Executors.newSingleThreadExecutor();
        new AndInThreads(
            service,
            new ProcNoNulls<Integer>(list::add),
            new ListOf<>(1, 2)
        ).value();
        MatcherAssert.assertThat(
            list,
            new IsIterableContainingInAnyOrder<Integer>(
                new ListOf<Matcher<? super Integer>>(
                    new MatcherOf<>(
                        value -> {
                            return value.equals(1);
                        }
                    ),
                    new MatcherOf<>(
                        value -> {
                            return value.equals(2);
                        }
                    )
                )
            )
        );
    }

    @Test
    void worksWithExecServiceScalarBooleans() throws Exception {
        MatcherAssert.assertThat(
            new AndInThreads(
                Executors.newSingleThreadExecutor(),
                new Constant<Boolean>(false),
                new Constant<Boolean>(false)
            ).value(),
            Matchers.equalTo(false)
        );
    }

    @Test
    void worksWithExecServiceIterableScalarBoolean() throws Exception {
        MatcherAssert.assertThat(
            new AndInThreads(
                Executors.newSingleThreadExecutor(),
                new ListOf<Scalar<Boolean>>(
                    new Constant<Boolean>(true),
                    new Constant<Boolean>(false)
                )
            ).value(),
            Matchers.equalTo(false)
        );
    }

    @Test
    void worksWithEmptyIterableScalarBoolean() throws Exception {
        MatcherAssert.assertThat(
            new AndInThreads(
                new ListOf<Scalar<Boolean>>()
            ).value(),
            Matchers.equalTo(true)
        );
    }

}
