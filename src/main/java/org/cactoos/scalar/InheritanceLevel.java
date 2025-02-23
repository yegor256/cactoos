/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * The number of superclasses between base and derived class.
 *
 * <p>This class is thread safe.
 *
 * <p>Result interpretation:
 * <ul>
 *     <li>{@link Integer#MIN_VALUE} -&gt; classes are not related.
 *     (ex. matching FileNotFoundException with RuntimeException);
 *     <li>0 -&gt; classes are identical. (ex. matching IOException with
 *     IOException);
 *     <li>1 -&gt; single level inheritance. (ex. matching
 *     FileNotFoundException with IOException);
 *     <li>2 -&gt; two inheritance levels. (ex. matching
 *     FileNotFoundException with Exception).
 * </ul>
 *
 * @since 0.30
 */
public final class InheritanceLevel implements Scalar<Integer> {

    /**
     * Base class.
     */
    private final Class<?> base;

    /**
     * Derived class.
     */
    private final Class<?> derived;

    /**
     * Ctor.
     * @param cderived Derived class
     * @param cbase Base class
     */
    public InheritanceLevel(final Class<?> cderived, final Class<?> cbase) {
        this.derived = cderived;
        this.base = cbase;
    }

    @Override
    public Integer value() {
        final int level;
        if (this.base.equals(this.derived)) {
            level = 0;
        } else {
            level = this.calculateLevel();
        }
        return level;
    }

    /**
     * Calculates inheritance level.
     * @return Integer Level
     */
    private int calculateLevel() {
        int level = Integer.MIN_VALUE;
        Class<?> sclass = this.derived.getSuperclass();
        int idx = 0;
        while (!sclass.equals(Object.class)) {
            idx += 1;
            if (sclass.equals(this.base)) {
                level = idx;
                break;
            }
            sclass = sclass.getSuperclass();
        }
        return level;
    }
}
