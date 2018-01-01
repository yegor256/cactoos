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

import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Logical disjunction.
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use {@link UncheckedScalar} or {@link IoCheckedScalar} decorators.</p>
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.8
 */
public final class Or implements Scalar<Boolean> {

    /**
     * The iterator.
     */
    private final Iterable<Scalar<Boolean>> origin;

    /**
     * Ctor.
     * @param proc Proc to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> Or(final Proc<X> proc, final X... src) {
        this(new FuncOf<>(proc, false), src);
    }

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> Or(final Func<X, Boolean> func, final X... src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param proc Proc to use
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> Or(final Proc<X> proc, final Iterator<X> src) {
        this(proc, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param proc Proc to use
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> Or(final Proc<X> proc, final Iterable<X> src) {
        this(new FuncOf<>(proc, false), src);
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param func Func to map
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> Or(final Func<X, Boolean> func, final Iterator<X> src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param func Func to map
     * @param <X> Type of items in the iterable
     */
    public <X> Or(final Func<X, Boolean> func, final Iterable<X> src) {
        this(
            new Mapped<>(
                item -> (Scalar<Boolean>) () -> func.apply(item), src
            )
        );
    }

    /**
     * Ctor.
     * @param scalar The Scalar.
     */
    @SafeVarargs
    public Or(final Scalar<Boolean>... scalar) {
        this(new IterableOf<>(scalar));
    }

    /**
     * Ctor.
     * @param iterable The iterable.
     * @since 0.24
     */
    public Or(final Iterator<Scalar<Boolean>> iterable) {
        this(new IterableOf<>(iterable));
    }

    /**
     * Ctor.
     * @param iterable The iterable.
     */
    public Or(final Iterable<Scalar<Boolean>> iterable) {
        this.origin = iterable;
    }

    @Override
    public Boolean value() throws Exception {
        boolean result = false;
        for (final Scalar<Boolean> item : this.origin) {
            if (item.value()) {
                result = true;
                break;
            }
        }
        return result;
    }
}
