/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Logical truth.
 *
 * <p>This class is thread-safe.
 *
 * @since 0.7
 */
public final class True implements Scalar<Boolean> {

    @Override
    public Boolean value() {
        return true;
    }
}
