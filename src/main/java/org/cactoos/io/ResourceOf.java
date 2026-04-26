/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import org.cactoos.Func;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.bytes.BytesOf;
import org.cactoos.func.IoCheckedFunc;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;

/**
 * Classpath resource.
 *
 * <p>Pay attention that the name of resource must always be
 * global, <strong>not</strong> starting with a leading slash. Thus,
 * if you want to load a text file from {@code /com/example/Test.txt},
 * you must provide this name: {@code "com/example/Test.txt"}.</p>
 *
 * @see ClassLoader#getResource(String)
 * @since 0.1
 */
public final class ResourceOf implements Input {

    /**
     * Resource name.
     */
    private final Text path;

    /**
     * Fallback.
     */
    private final Func<Text, Input> fallback;

    /**
     * Resource class loader, deferred.
     */
    private final Scalar<ClassLoader> loader;

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     */
    public ResourceOf(final CharSequence res) {
        this(res, (Scalar<ClassLoader>) () -> Thread.currentThread().getContextClassLoader());
    }

    /**
     * New resource input with {@link ClassLoader} from the specified {@link Class}.
     * @param res Resource name
     * @param cls Resource class loader
     * @since 0.49
     */
    @SuppressWarnings("PMD.UseProperClassLoader")
    public ResourceOf(final CharSequence res, final Class<?> cls) {
        this(res, (Scalar<ClassLoader>) cls::getClassLoader);
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param ldr Resource class loader
     */
    public ResourceOf(final CharSequence res, final ClassLoader ldr) {
        this(res, (Scalar<ClassLoader>) () -> ldr);
    }

    /**
     * New resource input with deferred {@link ClassLoader}.
     * @param res Resource name
     * @param ldr Resource class loader, deferred
     */
    private ResourceOf(final CharSequence res, final Scalar<ClassLoader> ldr) {
        this(new TextOf(res), ldr);
    }

    /**
     * New resource input with {@link ClassLoader} from the specified {@link Class}.
     * @param res Resource name
     * @param fbk Fallback
     * @param cls Resource class loader
     * @since 0.49
     */
    @SuppressWarnings("PMD.UseProperClassLoader")
    public ResourceOf(final CharSequence res,
        final Func<CharSequence, Input> fbk, final Class<?> cls) {
        this(res, fbk, (Scalar<ClassLoader>) cls::getClassLoader);
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     * @param ldr Resource class loader
     */
    public ResourceOf(final CharSequence res,
        final Func<CharSequence, Input> fbk, final ClassLoader ldr) {
        this(res, fbk, (Scalar<ClassLoader>) () -> ldr);
    }

    /**
     * New resource input with deferred {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     * @param ldr Resource class loader, deferred
     */
    private ResourceOf(final CharSequence res,
        final Func<CharSequence, Input> fbk, final Scalar<ClassLoader> ldr) {
        this(new TextOf(res), input -> fbk.apply(input.asString()), ldr);
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final CharSequence res, final CharSequence fbk) {
        this(res, input -> new InputOf(new BytesOf(fbk)));
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final CharSequence res,
        final Func<CharSequence, Input> fbk) {
        this(
            res, fbk,
            (Scalar<ClassLoader>) () -> Thread.currentThread().getContextClassLoader()
        );
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final CharSequence res, final Input fbk) {
        this(res, input -> fbk);
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     */
    public ResourceOf(final Text res) {
        this(res, (Scalar<ClassLoader>) () -> Thread.currentThread().getContextClassLoader());
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param ldr Resource class loader
     */
    public ResourceOf(final Text res, final ClassLoader ldr) {
        this(res, (Scalar<ClassLoader>) () -> ldr);
    }

    /**
     * New resource input with deferred {@link ClassLoader}.
     * @param res Resource name
     * @param ldr Resource class loader, deferred
     */
    private ResourceOf(final Text res, final Scalar<ClassLoader> ldr) {
        this(
            res,
            input -> {
                throw new IOException(
                    new FormattedText(
                        "The resource \"%s\" was not found in %s (%s)",
                        input,
                        new Unchecked<>(ldr).value(),
                        new Unchecked<>(ldr).value().getClass().getCanonicalName()
                    ).asString()
                );
            },
            ldr
        );
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final Text res, final Text fbk) {
        this(res, input -> new InputOf(fbk));
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final Text res, final Input fbk) {
        this(res, input -> fbk);
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final Text res,
        final Func<Text, Input> fbk) {
        this(
            res, fbk,
            (Scalar<ClassLoader>) () -> Thread.currentThread().getContextClassLoader()
        );
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     * @param ldr Resource class loader
     */
    public ResourceOf(final Text res,
        final Func<Text, Input> fbk, final ClassLoader ldr) {
        this(res, fbk, (Scalar<ClassLoader>) () -> ldr);
    }

    /**
     * New resource input with deferred {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     * @param ldr Resource class loader, deferred
     */
    private ResourceOf(final Text res,
        final Func<Text, Input> fbk, final Scalar<ClassLoader> ldr) {
        this.path = res;
        this.loader = ldr;
        this.fallback = fbk;
    }

    @Override
    public InputStream stream() throws Exception {
        if (this.path == null) {
            throw new IllegalArgumentException(
                "The \"path\" of the resource is NULL, which is not allowed"
            );
        }
        final ClassLoader ldr = new Unchecked<>(this.loader).value();
        if (ldr == null) {
            throw new IllegalArgumentException(
                "The \"classloader\" is NULL, which is not allowed"
            );
        }
        InputStream input = ldr.getResourceAsStream(
            this.path.asString()
        );
        if (input == null) {
            if (this.fallback == null) {
                throw new IllegalArgumentException(
                    "The \"fallback\" is NULL, which is not allowed"
                );
            }
            input = new IoCheckedFunc<>(this.fallback)
                .apply(this.path)
                .stream();
        }
        return input;
    }
}
