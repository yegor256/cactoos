/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import org.cactoos.BiProc;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link UncheckedBiProc}.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class UncheckedBiProcTest {

    @Test
    void worksWithGenerics() {
        final Map<Object, Object> map = new HashMap<>();
        final BiProc<Object, Object> callback = map::put;
        new UncheckedBiProc<Number, String>(callback).exec(1, "one");
        MatcherAssert.assertThat(
            "Must accept a proc consuming supertypes of the inputs",
            map.values(),
            new HasValues<>("one")
        );
    }

    @Test
    void rethrowsCheckedAsUnchecked() {
        MatcherAssert.assertThat(
            "Must rethrow checked exception as unchecked",
            () -> {
                new UncheckedBiProc<Number, String>(
                    (first, second) -> {
                        throw new IOException("intended");
                    }
                ).exec(1, "one");
                return true;
            },
            new Throws<>(UncheckedIOException.class)
        );
    }
}
