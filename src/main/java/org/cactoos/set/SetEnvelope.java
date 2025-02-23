/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.Set;
import org.cactoos.collection.CollectionEnvelope;

/**
 * Set envelope.
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.49.2
 * @checkstyle AbstractClassNameCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.AbstractNaming"
    }
)
public abstract class SetEnvelope<T> extends CollectionEnvelope<T> implements
    Set<T> {

    /**
     * Ctor.
     *
     * @param src Source
     */
    public SetEnvelope(final Set<T> src) {
        super(src);
    }
}
