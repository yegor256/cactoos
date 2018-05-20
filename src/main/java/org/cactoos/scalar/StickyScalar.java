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

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.StickyFunc;

/**
 * Cached version of a Scalar.
 *
 * <p>This {@link Scalar} decorator technically is an in-memory
 * cache.</p>
 *
 * <p>Pay attention that this class is not thread-safe. It is highly
 * recommended to always decorate it with {@link SyncScalar}.</p>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use {@link UncheckedScalar} or {@link IoCheckedScalar} decorators.</p>
 *
 * <pre>{@code
 * final Scalar<Integer> scalar = new StickyScalar<>(
 *     () -> {
 *         System.out.println("Will be printed only once");
 *         return new SecureRandom().nextInt();
 *     }
 * ).value()
 * }</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
 * @see StickyFunc
 * @since 0.3
 */
public final class StickyScalar<T> implements Scalar<T> {

    /**
     * Func.
     */
    private final Func<Boolean, T> func;

    /**
     * Ctor.
     * @param scalar The Scalar to cache
     */
    public StickyScalar(final Scalar<T> scalar) {
        this.func = new StickyFunc<>(
            input -> scalar.value()
        );
    }

    @Override
    public T value() throws Exception {
        return this.func.apply(true);
    }
}
