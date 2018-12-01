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

import java.util.ArrayList;
import java.util.Collection;
import org.cactoos.Scalar;

/**
 * Detect root cause of the particular exception.
 *
 * @since 1.0.0
 */
public final class RootCauseOf implements Scalar<Throwable> {

    /**
     * The root cause exception.
     */
    private final Scalar<Throwable> cause;

    /**
     * Ctor.
     * @param cause The root exception for hierarchical search.
     */
    public RootCauseOf(final Exception cause) {
        this(() -> {
            Throwable rcause = cause.getCause();
            final Collection<Throwable> visited = new ArrayList<>(5);
            while (rcause.getCause() != null
                && !visited.contains(rcause.getCause())) {
                rcause = rcause.getCause();
                visited.add(rcause);
            }
            return rcause;
        });
    }

    /**
     * Ctor.
     * @param cause The root cause exception.
     */
    private RootCauseOf(final Scalar<Throwable> cause) {
        this.cause = cause;
    }

    @Override
    public Throwable value() throws Exception {
        return this.cause.value();
    }
}
