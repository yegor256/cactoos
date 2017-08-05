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
package org.cactoos.scalar;

import java.io.IOException;
import org.cactoos.Scalar;
import org.cactoos.text.FormattedText;
import org.cactoos.text.JoinedText;

/**
 * It provides guaranteed half-intervals.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @param <T> Type of result
 * @see <a href="http://en.wikipedia.org/wiki/Interval_(mathematics)">terms</a>
 * @since 0.13.1
 */
public final
    class ValidBound<T extends Comparable<T>> implements Scalar<T> {

    /**
     * The type of endpoint, either bounded or unbounded.
     */
    private final String symbol;

    /**
     * The endpoint of the interval, either left or right.
     */
    private final T endpoint;

    /**
     * The expected value in the interval.
     */
    private final T val;

    /**
     * Ctor.
     * @param value The value expected in the interval
     * @param type Bounded or unbounded symbol type
     * @param bound The value of the endpoint
     */
    public ValidBound(final T value, final String type, final T bound) {
        this.val = value;
        this.symbol = type;
        this.endpoint = bound;
    }

    @Override
    public T value() throws IOException {
        Boolean valid = false;
        if (this.symbol.compareTo(">=") == 0) {
            valid = this.val.compareTo(this.endpoint) >= 0;
        } else if (this.symbol.compareTo("<=") == 0) {
            valid = this.val.compareTo(this.endpoint) <= 0;
        } else if (this.symbol.compareTo(">") == 0) {
            valid = this.val.compareTo(this.endpoint) > 0;
        } else if (this.symbol.compareTo("<") == 0) {
            valid = this.val.compareTo(this.endpoint) < 0;
        } else {
            throw new IllegalArgumentException(
                new FormattedText(
                    new JoinedText(
                        "\n",
                        "\nInvalid symbol \"%s\":",
                        "    - Expected: \">=\", \"<=\", \">\", \"<\"\n"
                    ),
                    this.symbol
                ).asString()
            );
        }
        if (!valid) {
            throw new IndexOutOfBoundsException(
                new FormattedText(
                    new JoinedText(
                        "\n",
                        "\nUnexpected value=%d:",
                        "    - Expected a value %s %d\n"
                    ),
                    this.val, this.symbol, this.endpoint
                ).asString()
            );
        }
        return this.val;
    }

}
