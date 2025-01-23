/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsIterableContaining;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsEntry;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link NoNulls}.
 *
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods",
    "PMD.NonStaticInitializer",
    "serial",
    "PMD.JUnitTestsShouldIncludeAssert",
    "PMD.DoubleBraceInitialization"
})
final class NoNullsTest {

    @Test
    void getSize() {
        MatcherAssert.assertThat(
            "Can't calculate size",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            ).size(),
            new IsEqual<>(2)
        );
    }

    @Test
    void isEmptyTrue() {
        MatcherAssert.assertThat(
            "Can't get is empty true",
            new NoNulls<>(
                new MapOf<Integer, Integer>()
            ).isEmpty(),
            new IsEqual<>(true)
        );
    }

    @Test
    void isEmptyFalse() {
        MatcherAssert.assertThat(
            "Can't get is empty false",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).isEmpty(),
            new IsEqual<>(false)
        );
    }

    @Test
    void containsKeyTrue() {
        MatcherAssert.assertThat(
            "Can't get #containsKey() true",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsKey(0),
            new IsEqual<>(true)
        );
    }

    @Test
    void containsKeyFalse() {
        MatcherAssert.assertThat(
            "Can't get #containsKey() false",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsKey(-1),
            new IsEqual<>(false)
        );
    }

    @Test
    void containsKeyException() {
        new Assertion<>(
            "Could no throw an IllegalStateException for null key",
            () -> new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsKey(null),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void containsValueFalse() {
        MatcherAssert.assertThat(
            "Can't get #containsValue() false",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsValue(0),
            new IsEqual<>(false)
        );
    }

    @Test
    void containsValueTrue() {
        MatcherAssert.assertThat(
            "Can't get #containsValue() true",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsValue(-1),
            new IsEqual<>(true)
        );
    }

    @Test
    void containsValueException() {
        new Assertion<>(
            "Can't get #containsValue() exception",
            () -> new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsValue(null),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

    @Test
    void getValue() {
        MatcherAssert.assertThat(
            "Can't call #get()",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).get(0),
            new IsEqual<>(-1)
        );
    }

    @Test
    void getValueByNullKey() {
        new Assertion<>(
            "Can't call #get() with key null",
            () -> new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).get(null),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void getValueByNullValue() {
        new Assertion<>(
            "Can't call #get() with null value",
            () -> new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, null)
                )
            ).get(0),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void put() {
        new Assertion<>(
            "Can't call #put()",
            new NoNulls<>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new PutUpdatesValues<>(0, 1)
        ).affirm();
    }

    @Test
    void putWithNullKey() {
        new Assertion<>(
            "Can't call #put() with Null key",
            () -> new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    void putWithNullValue() {
        new Assertion<>(
            "Can't call #put() with Null value",
            () -> new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    @Disabled
    void putWithNoMapping() {
        MatcherAssert.assertThat(
            "Can't call #put() with no mapping",
            new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new PutUpdatesValues<Integer, Integer>(1, 1)
        );
    }

    @Test
    void remove() {
        MatcherAssert.assertThat(
            "Can't call #remove()",
            new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new RemoveDeletesValues<Integer, Integer>(0, 0)
        );
    }

    @Test
    void removeWithNullKey() {
        new Assertion<>(
            "Can't call #remove() with Null key",
            () -> new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new Throws<>(IllegalStateException.class)
        );
    }

    @Test
    @Disabled
    void removeWithNoMapping() {
        MatcherAssert.assertThat(
            "Can't call #remove() with no mapping",
            new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new RemoveDeletesValues<Integer, Integer>(1, 0)
        );
    }

    @Test
    void putAll() {
        MatcherAssert.assertThat(
            "Can't call #putAll()",
            new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new PutAllUpdatesValues<Integer, Integer>(0, 1)
        );
    }

    @Test
    void clear() {
        MatcherAssert.assertThat(
            "Can't call #clear()",
            new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                        put(1, 1);
                        put(2, 2);
                    }
                }
            ),
            new ClearDeletesAllValues<Integer, Integer>()
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void entrySet() {
        new Assertion<Set<Map.Entry<Integer, Integer>>>(
            "Must call #entrySet()",
            new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(1, 1);
                        put(0, 0);
                    }
                }
            ).entrySet(),
            new AllOf<>(
                new IsIterableContaining<>(new IsEntry<>(1, 1)),
                new IsIterableContaining<>(new IsEntry<>(0, 0))
            )
        ).affirm();
    }

    @Test
    void putThrowsErrorIfValueNull() {
        new Assertion<>(
            "Should throws an error if value is null",
            () -> new NoNulls<Integer, Integer>(
                new HashMap<>()
            ).put(1, null),
            new Throws<>(
                "Value at #put(1,V) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void putThrowsErrorIfPreviousValueNull() {
        new Assertion<>(
            "Should throws an error if previous value is null",
            () -> new NoNulls<>(
                new HashMap<Integer, Integer>() {
                    {
                        put(1, null);
                    }
                }
            ).put(1, 2),
            new Throws<>(
                "Value returned by #put(1,2) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void putThrowsErrorIfKeyIsNull() {
        new Assertion<>(
            "Should throw an error if key is null",
            () -> new NoNulls<Integer, Integer>(
                new HashMap<>()
            ).put(null, 1),
            new Throws<>(
                "Key at #put(K,1) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void removeThrowsErrorIfKeyIsNull() {
        new Assertion<>(
            "Should throw an error if key is null",
            () -> new NoNulls<Integer, Integer>(
                new HashMap<>()
            ).remove(null),
            new Throws<>(
                "Key at #remove(K) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void removeThrowsErrorIfValueIsNull() {
        new Assertion<>(
            "Should throws an error if removed value is null",
            () -> new NoNulls<>(
                new HashMap<Integer, Integer>() {
                    {
                        put(1, null);
                    }
                }
            ).remove(1),
            new Throws<>(
                "Value returned by #remove(1) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }
}
