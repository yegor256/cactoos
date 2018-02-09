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
 * Logical conjunction.
 *
 * <p>This class can be effectively used to iterate through
 * a collection, just like
 * {@link java.util.stream.Stream#forEach(java.util.function.Consumer)}
 * works:</p>
 *
 * <pre>
 * new And(
 *    new ProcOf<>(input -> System.out.printf("\'%s\' ", input) ),
 *    new IterableOf<>("Mary", "John", "William", "Napkin")
 * ).value(); // will print 'Mary' 'John' 'William' 'Napkin' to standard output
 * </pre>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use {@link UncheckedScalar} or {@link IoCheckedScalar} decorators.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @see UncheckedScalar
 * @see IoCheckedScalar
 * @since 0.8
 */
public final class And implements Scalar<Boolean> {

    /**
     * The iterator.
     */
    private final Iterable<Scalar<Boolean>> iterable;

    /**
     * Ctor.
     * @param proc Proc to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> And(final Proc<X> proc, final X... src) {
        this(new FuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> And(final Func<X, Boolean> func, final X... src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param proc Proc to use
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> And(final Proc<X> proc, final Iterator<X> src) {
        this(new FuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param proc Proc to use
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> And(final Proc<X> proc, final Iterable<X> src) {
        this(new FuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param func Func to map
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> And(final Func<X, Boolean> func, final Iterator<X> src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param func Func to map
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> And(final Func<X, Boolean> func, final Iterable<X> src) {
        this(
            new Mapped<>(
                item -> (Scalar<Boolean>) () -> func.apply(item), src
            )
        );
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    @SafeVarargs
    public And(final Scalar<Boolean>... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     * @since 0.24
     */
    public And(final Iterator<Scalar<Boolean>> src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    public And(final Iterable<Scalar<Boolean>> src) {
        this.iterable = src;
    }

    @Override
    public Boolean value() throws Exception {
        boolean result = true;
        for (final Scalar<Boolean> item : this.iterable) {
            if (!item.value()) {
                result = false;
                break;
            }
        }
        return result;
    }

}
