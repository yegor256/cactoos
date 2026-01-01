/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Scalar that wraps an original checked exception thrown by the origin using
 * the given wrapping function.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
 * @param <E> Type of exception
 * @since 0.30
 */
public final class Checked<T, E extends Exception> implements Scalar<T> {

    /**
     * Function that wraps exception.
     */
    private final Func<? super Exception, ? extends E> func;

    /**
     * Original scalar.
     */
    private final Scalar<? extends T> origin;

    /**
     * Ctor.
     * @param scalar Encapsulated scalar
     * @param fnc Func that wraps exception
     */
    public Checked(final Scalar<? extends T> scalar,
        final Func<? super Exception, ? extends E> fnc) {
        this.func = fnc;
        this.origin = scalar;
    }

    @Override
    @SuppressWarnings(
        {
            "PMD.AvoidCatchingGenericException",
            "PMD.AvoidRethrowingException",
            "PMD.PreserveStackTrace"
        }
    )
    public T value() throws E {
        try {
            return this.origin.value();
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final RuntimeException ex) {
            throw ex;
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw this.wrappedException(ex);
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw this.wrappedException(ex);
        }
    }

    /**
     * Wraps exception.
     * Skips unnecessary wrapping of exceptions of the same type.
     * Allows wrapping of exceptions of the same type if the error message
     * has been changed.
     *
     * @param exp Exception
     * @return E Wrapped exception
     */
    @SuppressWarnings("unchecked")
    private E wrappedException(final Exception exp) {
        E wrapped = new UncheckedFunc<>(this.func).apply(exp);
        final int level = new InheritanceLevel(
            exp.getClass(), wrapped.getClass()
        ).value();
        final String message = wrapped.getMessage()
            .replaceFirst(
                new UncheckedText(
                    new FormattedText(
                        "%s: ",
                        exp.getClass().getName()
                    )
                ).asString(),
                ""
            );
        if (level >= 0 && message.equals(exp.getMessage())) {
            wrapped = (E) exp;
        }
        return wrapped;
    }
}
