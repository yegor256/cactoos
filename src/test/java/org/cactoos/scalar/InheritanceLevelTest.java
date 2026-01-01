/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
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
        new Assertion<>(
            "level should be calculated for inherited classes",
            new InheritanceLevel(
                FileNotFoundException.class,
                Exception.class
            ),
            new HasValue<>(2)
        ).affirm();
    }

    @Test
    void classesAreNotRelated() {
        new Assertion<>(
            "level should be calculated for not related classes",
            new InheritanceLevel(
                FileNotFoundException.class,
                RuntimeException.class
            ),
            new HasValue<>(Integer.MIN_VALUE)
        ).affirm();
    }

    @Test
    void classesAreIdentical() {
        new Assertion<>(
            "0 level should be calculated for equal classes",
            new InheritanceLevel(
                FileNotFoundException.class,
                FileNotFoundException.class
            ),
            new HasValue<>(0)
        ).affirm();
    }
}
