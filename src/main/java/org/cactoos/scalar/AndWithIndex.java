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
import org.cactoos.BiFunc;
import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.func.BiFuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Logical conjunction, with index.
 *
 * <p>This class can be effectively used to iterate through
 * a collection, just like
 * {@link java.util.stream.Stream#forEach(java.util.function.Consumer)}
 * works, but with an index provided for each item:</p>
 *
 * <pre>
 * new UncheckedScalar<>(
 *     new AndWithIndex(
 *         new BiFuncOf<>((text, index) ->
 *             System.out.printf("| idx #%d: name: %s ", index, text), true),
 *         new IterableOf<>("Mary", "John", "William", "Napkin")
 *     )
 * ).value();
 * // will print "| idx #0: name: Mary | idx #1: name: John |
 * idx #2: name: William | idx #3: name: Napkin " to console
 * </pre>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use {@link UncheckedScalar} or {@link IoCheckedScalar} decorators.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.20
 */
public final class AndWithIndex implements Scalar<Boolean> {

    /**
     * The iterator.
     */
    private final Iterable<Func<Integer, Boolean>> iterable;

    /**
     * Ctor.
     * @param proc Proc to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> AndWithIndex(final Proc<X> proc, final X... src) {
        this(new BiFuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> AndWithIndex(final BiFunc<X, Integer, Boolean> func,
        final X... src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param proc Proc to use
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> AndWithIndex(final BiProc<X, Integer> proc,
        final Iterable<X> src) {
        this(new BiFuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param src The iterable
     * @param func Func to map
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> AndWithIndex(final BiFunc<X, Integer, Boolean> func,
        final Iterable<X> src) {
        this(
            new Mapped<>(
                item -> (Func<Integer, Boolean>) input
                    -> func.apply(item, input), src
            )
        );
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    @SafeVarargs
    public AndWithIndex(final Func<Integer, Boolean>... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterator
     * @since 0.24
     */
    public AndWithIndex(final Iterator<Func<Integer, Boolean>> src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    public AndWithIndex(final Iterable<Func<Integer, Boolean>> src) {
        this.iterable = src;
    }

    @Override
    public Boolean value() throws Exception {
        boolean result = true;
        int pos = 0;
        for (final Func<Integer, Boolean> item : this.iterable) {
            if (!item.apply(pos)) {
                result = false;
                break;
            }
            ++pos;
        }
        return result;
    }

}
