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

import java.math.BigDecimal;
import java.math.MathContext;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.LengthOf;
import org.cactoos.iterable.Mapped;

/**
 * Average of numbers.
 *
 * <p>Here is how you can use it to fine mathematical average of numbers:</p>
 *
 * <pre>
 * int sum = new AvgOf(1, 2, 3, 4).intValue();
 * long sum = new AvgOf(1L, 2L, 3L).longValue();
 * int sum = new AvgOf(numbers.toArray(new Integer[numbers.size()])).intValue();
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
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.24
 * @checkstyle ExecutableStatementCountCheck (500 lines)
 * @checkstyle NPathComplexityCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.CallSuperInConstructor",
        "PMD.OnlyOneConstructorShouldDoInitialization",
        "PMD.ConstructorOnlyInitializesOrCallOtherConstructors"
    }
)
public final class AvgOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 3624862553221697558L;

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Integer... src) {
        this(
            new Mapped<>(
                number -> () -> number,
                new IterableOf<>(src)
            )
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Long... src) {
        this(
            new Mapped<>(
                number -> () -> number,
                new IterableOf<>(src)
            )
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Double... src) {
        this(
            new Mapped<>(
                number -> () -> number,
                new IterableOf<>(src)
            )
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    public AvgOf(final Float... src) {
        this(
            new Mapped<>(
                number -> () -> number,
                new IterableOf<>(src)
            )
        );
    }

    /**
     * Ctor.
     * @param src Numbers
     */
    @SafeVarargs
    public AvgOf(final Scalar<Number>... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     * @checkstyle ExecutableStatementCountCheck (150 lines)
     */
    public AvgOf(final Iterable<Scalar<Number>> src) {
        super(
            new Ternary<>(
                new LengthOf(src).longValue(),
                len -> len > 0,
                len -> new Folded<>(
                    BigDecimal.ZERO,
                    (sum, value) -> sum.add(value, MathContext.DECIMAL128),
                    new Mapped<>(
                        number -> BigDecimal.valueOf(
                            number.value().doubleValue()
                        ),
                        src
                    )
                ).value().divide(
                    BigDecimal.valueOf(len),
                    MathContext.DECIMAL128
                ).doubleValue(),
                len -> 0.0
            )
        );
    }
}
