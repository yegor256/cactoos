/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import java.util.stream.Stream;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.MethodSource;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link NumberOfScalars}.
 * @since 1.0.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class NumberOfScalarsTest implements ArgumentsProvider {

    @ParameterizedTest
    @MethodSource("arguments")
    void floatValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must implement floatValue",
            new NumberOfScalars(() -> nbr).floatValue(),
            new IsEqual<>(nbr.floatValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void intValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must implement intValue",
            new NumberOfScalars(() -> nbr).intValue(),
            new IsEqual<>(nbr.intValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void doubleValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must implement doubleValue",
            new NumberOfScalars(() -> nbr).doubleValue(),
            new IsEqual<>(nbr.doubleValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void longValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must implement longValue",
            new NumberOfScalars(() -> nbr).longValue(),
            new IsEqual<>(nbr.longValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void byteValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must implement byteValue",
            new NumberOfScalars(() -> nbr).byteValue(),
            new IsEqual<>(nbr.byteValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void shortValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must implement shortValue",
            new NumberOfScalars(() -> nbr).shortValue(),
            new IsEqual<>(nbr.shortValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void hashCode(final Number nbr) {
        MatcherAssert.assertThat(
            "Must implement hashCode via doubleValue",
            new NumberOfScalars(() -> nbr).hashCode(),
            new IsEqual<>(Double.hashCode(nbr.doubleValue()))
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    @SuppressWarnings("unlikely-arg-type")
    void equalsNumber(final Number nbr) {
        MatcherAssert.assertThat(
            "Must implement equals doubleValue",
            new NumberOfScalars(() -> nbr).equals(nbr.doubleValue()),
            new IsTrue()
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void notEquals(final Number nbr) {
        MatcherAssert.assertThat(
            "Must implement not equals Object",
            new NumberOfScalars(() -> nbr).equals(new Object()),
            new IsNot<>(new IsTrue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    @SuppressWarnings("unlikely-arg-type")
    void notEqualsObject(final Number nbr) {
        MatcherAssert.assertThat(
            "Must implement not equals incorrect value",
            new NumberOfScalars(() -> nbr).equals(-1.0),
            new IsNot<>(new IsTrue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void toString(final Number nbr) {
        MatcherAssert.assertThat(
            "Must implement toString via doubleValue",
            new NumberOfScalars(() -> nbr).toString(),
            new IsEqual<>(Double.toString(nbr.doubleValue()))
        );
    }

    private static Stream<Arguments> arguments() {
        return Stream.of(
            Arguments.of(4),
            Arguments.of(8.2d),
            Arguments.of(5.1f),
            Arguments.of(8.2f),
            Arguments.of((short) 2),
            Arguments.of((byte) 9)
        );
    }
}
