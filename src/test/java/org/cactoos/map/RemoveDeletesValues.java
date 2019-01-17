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

import java.util.Map;
import org.hamcrest.Description;
import org.hamcrest.MatcherAssert;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;

/**
 * Check a remove method.
 * @param <K> Type of key
 * @param <V> Type of value
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class RemoveDeletesValues<K, V> extends
    TypeSafeMatcher<Map<K, V>>  {

    /**
     * Sample key.
     */
    private final K key;

    /**
     * Sample value.
     */
    private final V value;

    /**
     * Ctor.
     * @param akey Sample key
     * @param val Sample value
     */
    public RemoveDeletesValues(final K akey, final V val) {
        super();
        this.key = akey;
        this.value = val;
    }

    @Override
    public boolean matchesSafely(final Map<K, V> map) {
        map.remove(this.key);
        MatcherAssert.assertThat(
            "Contains the key/value after remove",
            map,
            new IsNot<>(
                new IsMapContaining<>(
                    new IsEqual<>(this.key),
                    new IsEqual<>(this.value)
                )
            )
        );
        MatcherAssert.assertThat(
            "Contains the key in #keySet() after remove",
            map.keySet(),
            new IsNot<>(
                new IsCollectionContaining<>(new IsEqual<>(this.key))
            )
        );
        MatcherAssert.assertThat(
            "Contains the value in #values() after remove",
            map.values(),
            new IsNot<>(
                new IsCollectionContaining<>(new IsEqual<>(this.value))
            )
        );
        return true;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("not a valid map");
    }
}
