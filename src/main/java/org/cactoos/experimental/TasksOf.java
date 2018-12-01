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
package org.cactoos.experimental;

import java.util.Iterator;
import java.util.concurrent.Callable;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Tasks.
 *
 * @param <X> Type of tasks.
 * @since 1.0.0
 */
public final class TasksOf<X> implements Tasks<X> {

    /**
     * Origin.
     */
    private final Iterable<Scalar<X>> tasks;

    /**
     * Ctor.
     * @param tasks Origin.
     */
    @SafeVarargs
    public TasksOf(final Scalar<X>... tasks) {
        this(new IterableOf<>(tasks));
    }

    /**
     * Ctor.
     * @param tasks Origin.
     */
    @SafeVarargs
    public TasksOf(final Callable<X>... tasks) {
        this(new Mapped<>(t -> (Scalar<X>) t::call, tasks));
    }

    /**
     * Ctor.
     * @param tasks Origin.
     */
    public TasksOf(final Iterable<Scalar<X>> tasks) {
        this.tasks = tasks;
    }

    @Override
    public Iterator<Scalar<X>> iterator() {
        return this.tasks.iterator();
    }
}
