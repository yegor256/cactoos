/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
package org.cactoos.number;

import java.io.IOException;
import java.util.stream.Stream;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link NumberOfScalars}.
 *
 * @since 1.0.0
 * @checkstyle MagicNumber (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class NumberOfScalarsTest implements ArgumentsProvider {

    @Override
    public Stream<Arguments> provideArguments(final ExtensionContext ctx) {
        return Stream.of(
            Arguments.of(4),
            Arguments.of(8.2d),
            Arguments.of(5.1f),
            Arguments.of(8.2f),
            Arguments.of((short) 2),
            Arguments.of((byte) 9)
        );
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void floatValue(final Number nbr) throws IOException {
        new Assertion<>(
            "Must implement floatValue",
            new NumberOfScalars(() -> nbr).floatValue(),
            new IsEqual<>(nbr.floatValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void intValue(final Number nbr) throws IOException {
        new Assertion<>(
            "Must implement intValue",
            new NumberOfScalars(() -> nbr).intValue(),
            new IsEqual<>(nbr.intValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void doubleValue(final Number nbr) throws IOException {
        new Assertion<>(
            "Must implement doubleValue",
            new NumberOfScalars(() -> nbr).doubleValue(),
            new IsEqual<>(nbr.doubleValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void longValue(final Number nbr) throws IOException {
        new Assertion<>(
            "Must implement longValue",
            new NumberOfScalars(() -> nbr).longValue(),
            new IsEqual<>(nbr.longValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void byteValue(final Number nbr) throws IOException {
        new Assertion<>(
            "Must implement byteValue",
            new NumberOfScalars(() -> nbr).byteValue(),
            new IsEqual<>(nbr.byteValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void shortValue(final Number nbr) throws IOException {
        new Assertion<>(
            "Must implement shortValue",
            new NumberOfScalars(() -> nbr).shortValue(),
            new IsEqual<>(nbr.shortValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void hashCode(final Number nbr) throws IOException {
        new Assertion<>(
            "Must implement hashCode via doubleValue",
            new NumberOfScalars(() -> nbr).hashCode(),
            new IsEqual<>(Double.hashCode(nbr.doubleValue()))
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    @SuppressWarnings("unlikely-arg-type")
    void equalsNumber(final Number nbr) throws IOException {
        new Assertion<>(
            "Must implement equals doubleValue",
            new NumberOfScalars(() -> nbr).equals(nbr.doubleValue()),
            new IsTrue()
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void notEquals(final Number nbr) throws IOException {
        new Assertion<>(
            "Must implement not equals Object",
            new NumberOfScalars(() -> nbr).equals(new Object()),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    @SuppressWarnings("unlikely-arg-type")
    void notEqualsObject(final Number nbr) throws IOException {
        new Assertion<>(
            "Must implement not equals incorrect value",
            new NumberOfScalars(() -> nbr).equals(-1.0),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void toString(final Number nbr) throws IOException {
        new Assertion<>(
            "Must implement toString via doubleValue",
            new NumberOfScalars(() -> nbr).toString(),
            new IsEqual<>(Double.toString(nbr.doubleValue()))
        ).affirm();
    }
}
