/**
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
 *  <p>This class is thread safe.
 *
 *  <p>
 *  Result interpretation:
 *
 *   0 -> classes are identical. (ex. matching IOException with IOException)
 *   1 -> single level inheritance. (ex. matching FileNotFoundException with
 *        IOException)
 *   2 -> two inheritance levels. (ex. matching FileNotFoundException with
 *        Exception)
 *   ...
 *   999 -> classes are not related.
 *      (ex. matching FileNotFoundException with RuntimeException)
 *  </p>
 *
 * @author Vedran Vatavuk (123vgv@gmail.com)
 * @version $Id$
 * @since 0.30
 */
public final class InheritanceLevel implements Scalar<Integer> {

    /**
     * Classes are identical.
     */
    private static final int IDENTICAL = 0;

    /**
     * Classes are not related.
     */
    private static final int NOT_RELATED = 999;

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
            level = InheritanceLevel.IDENTICAL;
        } else {
            level = InheritanceLevel.calculateLevel(this.derived, this.base);
        }
        return level;
    }

    /**
     * Calculates inheritance level.
     * @param cbase Base class
     * @param cderived Derived class
     * @return Integer Level
     */
    private static int calculateLevel(final Class<?> cderived,
        final Class<?> cbase) {
        int level = InheritanceLevel.NOT_RELATED;
        Class<?> sclass = cderived.getSuperclass();
        int idx = 0;
        while (!sclass.equals(Object.class)) {
            idx += 1;
            if (sclass.equals(cbase)) {
                level = idx;
                break;
            }
            sclass = sclass.getSuperclass();
        }
        return level;
    }
}
