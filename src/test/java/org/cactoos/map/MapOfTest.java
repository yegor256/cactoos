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
package org.cactoos.map;

import java.io.IOException;
import java.util.Map;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Constant;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringStartsWith;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasEntry;

/**
 * Test case for {@link MapOf}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class MapOfTest {

    @Test
    void createsMapFromSingleKeyAndValue() {
        new Assertion<>(
            "Must create a map from single key and value",
            new MapOf<Integer, Integer>(-1, 1),
            new HasEntry<>(-1, 1)
        ).affirm();
    }

    @Test
    void behavesAsMap() {
        new Assertion<>(
            "Must behave as a map",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            ),
            new BehavesAsMap<>(1, 1)
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void createsMapFromIterable() {
        new Assertion<>(
            "Must behave as a map when created from iterable",
            new MapOf<>(
                new IterableOf<>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            ),
            new BehavesAsMap<>(1, 1)
        ).affirm();
    }

    @Test
    void convertsIterableToMap() {
        new Assertion<>(
            "Must convert iterable to map",
            new MapOf<Integer, String>(
                new MapEntry<>(0, "hello, "),
                new MapEntry<>(1, "world!")
            ),
            new IsMapContaining<>(
                new IsEqual<>(0),
                new StringStartsWith("hello")
            )
        ).affirm();
    }

    @Test
    void createsMapWithFunctions() {
        new Assertion<>(
            "Must create a map with functions as values",
            new MapOf<Integer, Scalar<Boolean>>(
                new MapEntry<>(0, () -> true),
                new MapEntry<>(
                    1,
                    () -> {
                        throw new IOException("oops");
                    }
                )
            ),
            new IsMapContaining<>(new IsEqual<>(0), new IsAnything<>())
        ).affirm();
    }

    @Test
    void integersToString() {
        new Assertion<>(
            "Must convert map of integers to string",
            new MapOf<Integer, Integer>(
                new MapEntry<>(-1, 0),
                new MapEntry<>(1, 2)
            ).toString(),
            new IsEqual<>("{-1=0, 1=2}")
        ).affirm();
    }

    @Test
    void mapsToString() {
        new Assertion<>(
            "Must convert map op maps to string",
            new MapOf<Integer, Map<String, String>>(
                new MapEntry<Integer, Map<String, String>>(
                    -1,
                    new MapOf<String, String>(
                        new MapEntry<String, String>("first", "second"),
                        new MapEntry<String, String>("4", "7")
                    )
                ),
                new MapEntry<Integer, Map<String, String>>(
                    1,
                    new MapOf<String, String>(
                        new MapEntry<String, String>("green", "red"),
                        new MapEntry<String, String>("2.7", "3.1")
                    )
                )
            ).toString(),
            new IsEqual<>("{-1={4=7, first=second}, 1={green=red, 2.7=3.1}}")
        ).affirm();
    }

    @Test
    void emptyToString() {
        new Assertion<>(
            "Can't convert empty map to string",
            new MapOf<Integer, Map<String, String>>().toString(),
            new IsEqual<>("{}")
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void createsMapFromMapAndSingleKeyAndValue() {
        new Assertion<MapOf<Integer, Integer>>(
            "Must create a map from map and single key and value",
            new MapOf<Integer, Integer>(
                new MapOf<Integer, Integer>(
                    new MapEntry<Integer, Integer>(0, 0)
                ),
                -1, 1
            ),
            new AllOf<>(
                new HasEntry<>(0,  0),
                new HasEntry<>(-1,  1)
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void createsMapFromMapAndMapEntries() {
        new Assertion<MapOf<Integer, Integer>>(
            "Must create a map from map and map entries",
            new MapOf<Integer, Integer>(
                new MapOf<Integer, Integer>(
                    new MapEntry<Integer, Integer>(0, 0)
                ),
                new MapEntry<Integer, Integer>(1, 1)
            ),
            new AllOf<>(
                new HasEntry<>(0,  0),
                new HasEntry<>(1,  1)
            )
        ).affirm();
    }

    @Test
    void createsMapFromFunctionsAndIterable() {
        new Assertion<>(
            "Must create a map from functions and iterable.",
            new MapOf<Integer, Integer>(
                new FuncOf<Integer, Integer>(new Constant<>(0)),
                new FuncOf<Integer, Integer>(new Constant<>(0)),
                new IterableOf<Integer>(0)
            ),
            new HasEntry<>(0, 0)
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void createsMapFromMapFunctionsAndIterable() {
        new Assertion<MapOf<Integer, Integer>>(
            "Must create a map from map, functions and iterable.",
            new MapOf<Integer, Integer>(
                new FuncOf<Integer, Integer>(new Constant<>(0)),
                new FuncOf<Integer, Integer>(new Constant<>(0)),
                new MapOf<Integer, Integer>(
                    new MapEntry<Integer, Integer>(1, 1)
                ),
                new IterableOf<>(0)
            ),
            new AllOf<>(
                new HasEntry<>(0,  0),
                new HasEntry<>(1,  1)
            )
        ).affirm();
    }

}
