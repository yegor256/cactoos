/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Map;
import org.hamcrest.Description;
import org.hamcrest.MatcherAssert;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

/**
 * Check a clear method.
 * @param <K> Type of key
 * @param <V> Type of value
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ClearDeletesAllValues<K, V> extends
    TypeSafeMatcher<Map<K, V>>  {

    @Override
    public boolean matchesSafely(final Map<K, V> map) {
        map.clear();
        MatcherAssert.assertThat(
            "Can't be cleared",
            map.isEmpty(), new IsEqual<>(true)
        );
        return true;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("not a valid map");
    }
}
