/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
package org.cactoos.func;

import org.cactoos.Scalar;

/**
 * Ternary operation.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @param <T> Type of item.
 * @since 0.8
 */
public final class Ternary<T> implements Scalar<T> {

    /**
     * The condition.
     */
    private final Scalar<Boolean> condition;

    /**
     * The consequent.
     */
    private final T cons;

    /**
     * The alternative.
     */
    private final T alter;

    /**
     * Ctor.
     * @param condition The condition
     * @param cons The consequent
     * @param alter The alternative
     */
    public Ternary(
        final Scalar<Boolean> condition,
        final T cons,
        final T alter
    ) {
        this.condition = condition;
        this.cons = cons;
        this.alter = alter;
    }

    @Override
    public T asValue() throws Exception {
        final T result;
        if (this.condition.asValue()) {
            result = this.cons;
        } else {
            result = this.alter;
        }
        return result;
    }
}
