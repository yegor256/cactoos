/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.cactoos.Output;

/**
 * Output to Output copying pipe.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.16
 */
public final class TeeOutput implements Output {

    /**
     * The target.
     */
    private final Output target;

    /**
     * The copy.
     */
    private final Output copy;

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     * @param charset The charset
     */
    public TeeOutput(final Output tgt, final Writer cpy,
        final Charset charset) {
        this(tgt, new OutputTo(cpy, charset));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     */
    public TeeOutput(final Output tgt, final Writer cpy) {
        this(tgt, new OutputTo(cpy));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     */
    public TeeOutput(final Output tgt, final Path cpy) {
        this(tgt, new OutputTo(cpy));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     */
    public TeeOutput(final Output tgt, final File cpy) {
        this(tgt, new OutputTo(cpy));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     */
    public TeeOutput(final Output tgt, final OutputStream cpy) {
        this(tgt, new OutputTo(cpy));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     */
    public TeeOutput(final Output tgt, final Output cpy) {
        this.target = tgt;
        this.copy = cpy;
    }

    @Override
    public OutputStream stream() throws Exception {
        return new TeeOutputStream(
            this.target.stream(), this.copy.stream()
        );
    }

}
