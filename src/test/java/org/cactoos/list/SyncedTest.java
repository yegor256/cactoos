/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link Synced}.
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods",
    "PMD.JUnitTestsShouldIncludeAssert"})
final class SyncedTest {

    @Test
    void behavesAsCollection() {
        new Assertion<>(
            "Can't behave as a list",
            new Synced<>(new ListOf<>(1, 0, -1, -1, 2)),
            new BehavesAsList<>(0)
        ).affirm();
    }

    @Test
    void worksInThreads() {
        new Assertion<>(
            "should be run in threads",
            list -> !list.iterator().hasNext(),
            new RunsInThreads<>(new Synced<>(Collections.emptyList()))
        ).affirm();
        new Assertion<>(
            "should work as list",
            list -> {
                MatcherAssert.assertThat(
                    "should behave as a list",
                    list,
                    new BehavesAsList<>(0)
                );
                return true;
            },
            new RunsInThreads<>(new Synced<>(new ListOf<>(1, 0, -1, -1, 2)))
        ).affirm();
    }
}
