/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

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
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link AndInThreads}.
 * @since 0.25
 */
@SuppressWarnings({"unchecked", "PMD.TooManyMethods"})
final class AndInThreadsTest {

    @Test
    void allTrue() {
        MatcherAssert.assertThat(
            "Each object must be True",
            new AndInThreads(
                new True(),
                new True(),
                new True()
            ),
            new HasValue<>(true)
        );
    }

    @Test
    void oneFalse() {
        MatcherAssert.assertThat(
            "One object must be False",
            new AndInThreads(
                new True(),
                new False(),
                new True()
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void allFalse() {
        MatcherAssert.assertThat(
            "Each object must be False",
            new AndInThreads(
                new IterableOf<Scalar<Boolean>>(
                    new False(),
                    new False(),
                    new False()
                )
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void emptyIterable() {
        MatcherAssert.assertThat(
            "Must iterate over empty iterable",
            new AndInThreads(new IterableOf<>()),
            new HasValue<>(true)
        );
    }

    @Test
    void iteratesList() {
        MatcherAssert.assertThat(
            "Must iterate a list with a function",
            new AndInThreads(
                new Mapped<>(
                    s -> new True(),
                    new IterableOf<>("hello", "world")
                )
            ),
            new HasValue<>(true)
        );
    }

    @Test
    void populatesListInAnyOrder() throws Exception {
        final List<String> list = new Synced<>(new ListOf<>());
        new AndInThreads(
            new Mapped<>(
                new FuncOf<String, Scalar<Boolean>>(list::add, new True()),
                new IterableOf<>("hello", "world")
            )
        ).value();
        MatcherAssert.assertThat(
            "Iterable must contain elements in any order",
            list,
            new IsIterableContainingInAnyOrder<>(
                new ListOf<>(
                    new IsEqual<>("hello"),
                    new IsEqual<>("world")
                )
            )
        );
    }

    @Test
    void worksWithFunc() {
        MatcherAssert.assertThat(
            "Result should be calculated for integers",
            new AndInThreads(
                input -> input > 0,
                1, -1, 0
            ),
            new HasValue<>(false)
        );
    }

    @Test
    void worksWithIterableScalarBoolean() {
        MatcherAssert.assertThat(
            "Result should be calculated from booleans",
            new AndInThreads(
                new ListOf<Scalar<Boolean>>(
                    new Constant<>(true),
                    new Constant<>(true)
                )
            ),
            new HasValue<>(true)
        );
    }

    @Test
    void worksWithExecServiceProcValues() throws Exception {
        final List<Integer> list = new Synced<>(new ListOf<>());
        final ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            new AndInThreads(
                service,
                new ProcNoNulls<Integer>(list::add),
                1, 2
            ).value();
            MatcherAssert.assertThat(
                "Result should be calculated for proc values",
                list,
                new IsIterableContainingInAnyOrder<>(
                    new ListOf<>(
                        new IsEqual<>(1),
                        new IsEqual<>(2)
                    )
                )
            );
        } finally {
            service.shutdown();
        }
    }

    @Test
    void worksWithExecServiceProcIterable() throws Exception {
        final List<Integer> list = new Synced<>(new ListOf<>());
        final ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            new AndInThreads(
                service,
                new ProcNoNulls<Integer>(list::add),
                new ListOf<>(1, 2)
            ).value();
            MatcherAssert.assertThat(
                "Result should be calculated for proc iterables",
                list,
                new IsIterableContainingInAnyOrder<>(
                    new ListOf<>(
                        new IsEqual<>(1),
                        new IsEqual<>(2)
                    )
                )
            );
        } finally {
            service.shutdown();
        }
    }

    @Test
    void worksWithExecServiceScalarBooleans() throws Exception {
        MatcherAssert.assertThat(
            "Result should be calculated for threads booleans",
            new AndInThreads(
                Executors.newSingleThreadExecutor(),
                new Constant<>(false),
                new Constant<>(false)
            ).value(),
            Matchers.equalTo(false)
        );
    }

    @Test
    void worksWithExecServiceIterableScalarBoolean() throws Exception {
        MatcherAssert.assertThat(
            "Result should be calculated for threads iterable booleans",
            new AndInThreads(
                Executors.newSingleThreadExecutor(),
                new ListOf<Scalar<Boolean>>(
                    new Constant<>(true),
                    new Constant<>(false)
                )
            ).value(),
            Matchers.equalTo(false)
        );
    }

    @Test
    void worksWithEmptyIterableScalarBoolean() throws Exception {
        MatcherAssert.assertThat(
            "Result should be calculated for empty iterable",
            new AndInThreads(
                new ListOf<>()
            ).value(),
            Matchers.equalTo(true)
        );
    }

}
