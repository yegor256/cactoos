/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test case for {@link NoNulls}.
 *
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.NonStaticInitializer",
        "serial"
    }
)
public final class NoNullsTest {

    /**
     * A rule for handling an exception.
     */
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getSize() {
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
    public void isEmptyTrue() {
        MatcherAssert.assertThat(
            "Can't get is empty true",
            new NoNulls<>(
                new MapOf<Integer, Integer>()
            ).isEmpty(),
            new IsEqual<>(true)
        );
    }

    @Test
    public void isEmptyFalse() {
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
    public void containsKeyTrue() {
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
    public void containsKeyFalse() {
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

    @Test(expected = IllegalStateException.class)
    public void containsKeyException() {
        MatcherAssert.assertThat(
            "Could no throw an IllegalStateException for null key",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsKey(null),
            new IsEqual<>(true)
        );
    }

    @Test
    public void containsValueFalse() {
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
    public void containsValueTrue() {
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

    @Test(expected = IllegalStateException.class)
    public void containsValueException() {
        MatcherAssert.assertThat(
            "Can't get #containsValue() exception",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).containsValue(null),
            new IsEqual<>(true)
        );
    }

    @Test
    public void getValue() {
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

    @Test(expected = IllegalStateException.class)
    public void getValueByNullKey() {
        MatcherAssert.assertThat(
            "Can't call #get() with key null",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                )
            ).get(null),
            new IsEqual<>(-1)
        );
    }

    @Test(expected = IllegalStateException.class)
    public void getValueByNullValue() {
        MatcherAssert.assertThat(
            "Can't call #get() with null value",
            new NoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, null)
                )
            ).get(0),
            new IsEqual<>(-1)
        );
    }

    @Test
    public void put() {
        MatcherAssert.assertThat(
            "Can't call #put()",
            new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new PutUpdatesValues<Integer, Integer>(0, 1)
        );
    }

    @Test(expected = IllegalStateException.class)
    public void putWithNullKey() {
        MatcherAssert.assertThat(
            "Can't call #put() with Null key",
            new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new PutUpdatesValues<Integer, Integer>(null, 1)
        );
    }

    @Test(expected = IllegalStateException.class)
    public void putWithNullValue() {
        MatcherAssert.assertThat(
            "Can't call #put() with Null value",
            new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new PutUpdatesValues<Integer, Integer>(0, null)
        );
    }

    @Test
    @Ignore
    public void putWithNoMapping() {
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
    public void remove() {
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

    @Test(expected = IllegalStateException.class)
    public void removeWithNullKey() {
        MatcherAssert.assertThat(
            "Can't call #remove() with Null key",
            new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(0, 0);
                    }
                }
            ),
            new RemoveDeletesValues<Integer, Integer>(null, 0)
        );
    }

    @Test
    @Ignore
    public void removeWithNoMapping() {
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
    public void putAll() {
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
    public void clear() {
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
    public void entrySet() {
        MatcherAssert.assertThat(
            "Can't call #entrySet()",
            new NoNulls<Integer, Integer>(
                new HashMap<Integer, Integer>() {
                    {
                        put(1, 1);
                        put(0, 0);
                    }
                }
            ),
            new AllOf<>(
                new IterableOf<>(
                    new IsMapContaining<>(new IsEqual<>(1), new IsEqual<>(1)),
                    new IsMapContaining<>(new IsEqual<>(0), new IsEqual<>(0))
                )
            )
        );
    }

    @Test
    public void putThrowsErrorIfValueNull() {
        this.exception.expect(IllegalStateException.class);
        this.exception.expectMessage(
            "Value at #put(1,V) is NULL"
        );
        new NoNulls<Integer, Integer>(
            new HashMap<>()
        ).put(1, null);
    }

    @Test
    public void putThrowsErrorIfPreviousValueNull() {
        this.exception.expect(IllegalStateException.class);
        this.exception.expectMessage(
            "Value returned by #put(1,2) is NULL"
        );
        new NoNulls<>(
            new HashMap<Integer, Integer>() {
                {
                    put(1, null);
                }
            }
        ).put(1, 2);
    }
}
