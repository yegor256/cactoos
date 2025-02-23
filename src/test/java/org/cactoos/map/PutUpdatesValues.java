/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Map;
import org.hamcrest.Description;
import org.hamcrest.MatcherAssert;
import org.hamcrest.TypeSafeMatcher;

/**
 * Check a put method.
 * @param <K> Type of key
 * @param <V> Type of value
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class PutUpdatesValues<K, V> extends
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
    public PutUpdatesValues(final K akey, final V val) {
        super();
        this.key = akey;
        this.value = val;
    }

    @Override
    public boolean matchesSafely(final Map<K, V> map) {
        map.put(this.key, this.value);
        MatcherAssert.assertThat(
            "Can't behave as a map after put",
            map,
            new BehavesAsMap<>(this.key, this.value)
        );
        return true;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("not a valid map");
    }
}
