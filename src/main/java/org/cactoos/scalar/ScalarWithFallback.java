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

import org.cactoos.Func;
import org.cactoos.Scalar;

/**
 * Scalar with a fallback plan.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Roman Proshin (roman@proshin.org)
 * @version $Id$
 * @param <T> Type of result
 * @since 1.0
 */
public final class ScalarWithFallback<T> implements Scalar<T> {

    /**
     * The origin scalar.
     */
    private final Scalar<T> origin;

    /**
     * The fallback.
     */
    private final Func<Throwable, T> fallback;

    /**
     * The follow up.
     */
    private final Func<T, T> follow;

    /**
     * Ctor.
     * @param orig The origin scalar
     * @param fbk The fallback
     */
    public ScalarWithFallback(final Scalar<T> orig,
        final Func<Throwable, T> fbk) {
        this(orig, fbk, input -> input);
    }

    /**
     * Ctor.
     * @param orig The origin scalar
     * @param fbk The fallback
     * @param flw The follow up function
     */
    public ScalarWithFallback(final Scalar<T> orig,
        final Func<Throwable, T> fbk, final Func<T, T> flw) {
        this.origin = orig;
        this.fallback = fbk;
        this.follow = flw;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    public T value() throws Exception {
        T result;
        try {
            result = this.origin.value();
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            result = this.fallback.apply(ex);
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Throwable ex) {
            result = this.fallback.apply(ex);
        }
        return this.follow.apply(result);
    }
}
