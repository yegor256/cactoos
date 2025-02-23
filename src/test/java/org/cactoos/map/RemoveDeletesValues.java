/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Map;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsIterableContaining;
import org.hamcrest.core.IsNot;
import org.llorllale.cactoos.matchers.Assertion;

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
        new Assertion<>(
            "Must not the key/value after remove",
            map,
            new IsNot<>(
                new IsMapContaining<>(
                    new IsEqual<>(this.key),
                    new IsEqual<>(this.value)
                )
            )
        ).affirm();
        new Assertion<>(
            "Must not the key in #keySet() after remove",
            map.keySet(),
            new IsNot<>(
                new IsIterableContaining<>(new IsEqual<>(this.key))
            )
        ).affirm();
        new Assertion<>(
            "Must not the value in #values() after remove",
            map.values(),
            new IsNot<>(
                new IsIterableContaining<>(new IsEqual<>(this.value))
            )
        ).affirm();
        return true;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("not a valid map");
    }
}
