/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.Collections;
import java.util.List;

/**
 * Synchronized list.
 *
 * <p>Objects of this class are thread-safe.</p>
 *
 * @param <X> Type of item
 * @since 0.24
 */
public final class Synced<X> extends ListEnvelope<X> {
    /**
     * Ctor.
     * @param list The underlying list
     */
    public Synced(final List<X> list) {
        super(Collections.synchronizedList(list));
    }
}
