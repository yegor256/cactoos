/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Merged}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class MergedTest {

    @Test
    public void behavesAsMap() {
        new Assertion<>(
            "Must behave as a map",
            new Merged<Integer, Integer>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            ),
            new BehavesAsMap<>(1, 1)
        ).affirm();
    }

    @Test
    public void createsMapFromMaps() {
        new Assertion<>(
            "Must merge a few maps",
            new Merged<Integer, Integer>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, 0)
                ),
                new MapOf<Integer, Integer>(
                    new MapEntry<>(1, 1)
                )
            ),
            new IsEqual<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, 0),
                    new MapEntry<>(1, 1)
                )
            )
        ).affirm();
    }

    @Test
    public void overridesValues() {
        new Assertion<>(
            "Must override values",
            new Merged<Integer, Integer>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1)
                ),
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, 1)
                )
            ),
            new IsEqual<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, 1)
                )
            )
        ).affirm();
    }

    @Test
    public void mergesEmptyMaps() {
        new Assertion<>(
            "Must merge empty maps",
            new Merged<Integer, Integer>(
                new MapOf<Integer, Integer>(),
                new MapOf<Integer, Integer>()
            ),
            new IsEqual<>(
                new MapOf<Integer, Integer>()
            )
        ).affirm();
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerForNullMap() {
        new Merged<Integer, Integer>(
            new MapOf<Integer, Integer>(),
            null
        ).size();
    }
}
