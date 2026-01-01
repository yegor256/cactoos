/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Map;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasEntry;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Matcher for collection.
 * @param <K> Type of key
 * @param <V> Type of value
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class BehavesAsMap<K, V> extends TypeSafeMatcher<Map<K, V>>  {

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
    public BehavesAsMap(final K akey, final V val) {
        super();
        this.key = akey;
        this.value = val;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean matchesSafely(final Map<K, V> map) {
        new Assertion<>(
            "Must contain the key/value entry",
            map,
            new HasEntry<>(this.key, this.value)
        ).affirm();
        new Assertion<>(
            "Must contain the key in #keySet()",
            map.keySet(),
            new HasValues<>(this.key)
        ).affirm();
        new Assertion<>(
            "Must contain the value in #values()",
            map.values(),
            new HasValues<>(this.value)
        ).affirm();
        return true;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("not a valid map");
    }
}
