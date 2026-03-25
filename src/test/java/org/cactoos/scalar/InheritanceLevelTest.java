/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.FileNotFoundException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link InheritanceLevel}.
 *
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class InheritanceLevelTest {

    @Test
    void twoInheritanceLevelsBetweenClasses() {
        MatcherAssert.assertThat(
            "level should be calculated for inherited classes",
            new InheritanceLevel(
                FileNotFoundException.class,
                Exception.class
            ),
            new HasValue<>(2)
        );
    }

    @Test
    void classesAreNotRelated() {
        MatcherAssert.assertThat(
            "level should be calculated for not related classes",
            new InheritanceLevel(
                FileNotFoundException.class,
                RuntimeException.class
            ),
            new HasValue<>(Integer.MIN_VALUE)
        );
    }

    @Test
    void classesAreIdentical() {
        MatcherAssert.assertThat(
            "0 level should be calculated for equal classes",
            new InheritanceLevel(
                FileNotFoundException.class,
                FileNotFoundException.class
            ),
            new HasValue<>(0)
        );
    }
}
