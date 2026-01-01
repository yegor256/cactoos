/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.scalar.Unchecked;

/**
 * Filtered iterator.
 *
 * <p>You can use it in order to create a declarative/lazy
 * version of a filtered collection/iterable. For example,
 * this code will create a list of two strings "hello" and "world":</p>
 *
 * <pre> Iterator&lt;String&gt; list = new Filtered&lt;&gt;(
 *   new ArrayOf&lt;&gt;(
 *     "hey", "hello", "world"
 *   ).iterator(),
 *   input -&gt; input.length() &gt; 4
 * );
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @see Filtered
 * @since 0.1
 */
public final class Filtered<X> implements Iterator<X> {

    /**
     * Iterator.
     */
    private final Iterator<? extends X> iterator;

    /**
     * Predicate.
     */
    private final Func<? super X, Scalar<Boolean>> func;

    /**
     * The buffer storing the objects of the iterator.
     */
    private final Queue<X> buffer;

    /**
     * Ctor.
     * @param fnc Predicate
     * @param src Source iterable
     */
    public Filtered(
        final Func<? super X, Boolean> fnc,
        final Iterator<? extends X> src
    ) {
        this(src, input -> () -> fnc.apply(input));
    }

    /**
     * Ctor.
     * @param src Source iterable
     * @param fnc Predicate
     */
    public Filtered(
        final Iterator<? extends X> src,
        final Func<? super X, Scalar<Boolean>> fnc
    ) {
        this.iterator = src;
        this.func = fnc;
        this.buffer = new LinkedList<>();
    }

    @Override
    public boolean hasNext() {
        final UncheckedFunc<? super X, Scalar<Boolean>> fnc =
            new UncheckedFunc<>(this.func);
        if (this.buffer.isEmpty()) {
            while (this.iterator.hasNext()) {
                final X object = this.iterator.next();
                if (new Unchecked<>(fnc.apply(object)).value()) {
                    this.buffer.add(object);
                    break;
                }
            }
        }
        return !this.buffer.isEmpty();
    }

    @Override
    public X next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "No more elements that fit the condition"
            );
        }
        return this.buffer.poll();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
            "#remove() is not supported"
        );
    }

}
