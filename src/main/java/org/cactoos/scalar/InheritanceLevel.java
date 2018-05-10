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
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Calculates number of superclasses between base and derived class.
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
