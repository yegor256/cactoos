/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
package org.cactoos.proc;

import org.cactoos.BiProc;
import org.cactoos.Proc;
import org.cactoos.scalar.AndWithIndex;

/**
 * Executes a {@link BiProc} for each element of an
 * {@link Iterable}
 *
 * <p>
 * This class can be effectively used to iterate through a collection similar to how
 * {@link java.util.stream.Stream#forEach(java.util.function.Consumer)} works except
 * this class also passes the index of the current element to the proc:
 * </p>
 *
 * {@code
 * new ForEachWithIndex(
 *    new BiProcOf<>((input, index) -> System.out.printf("%d: \'%s\' ", index + 1, input)),
 * ).execute(
 *    new IterableOf<>("Mary", "John", "William", "Napkin")
 * ); // will print 1: 'Mary' 2: 'John' 3: 'William' 4: 'Napkin' to standard output
 * }
 * <p>
 * There is no thread-safety guarantee.
 *
 * @param <X> The type to iterate over
 * @since 1.0
 */
public final class ForEachWithIndex<X> implements Proc<Iterable<X>> {

    /**
     * The proc.
     */
    private final BiProc<X, Integer> proc;

    /**
     * Ctor.
     *
     * @param proc The proc to execute
     */
    public ForEachWithIndex(final BiProc<X, Integer> proc) {
        this.proc = proc;
    }

    @Override
    public void exec(final Iterable<X> input) throws Exception {
        new AndWithIndex(
            this.proc, input
        ).value();
    }
}
