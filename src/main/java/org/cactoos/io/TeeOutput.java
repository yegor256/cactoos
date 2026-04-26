/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
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
     * @param mirror The copy destination
     * @param charset The charset
     */
    public TeeOutput(final Output tgt, final Writer mirror,
        final Charset charset) {
        this(tgt, new OutputTo(mirror, charset));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param mirror The copy destination
     */
    public TeeOutput(final Output tgt, final Writer mirror) {
        this(tgt, new OutputTo(mirror));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param mirror The copy destination
     */
    public TeeOutput(final Output tgt, final Path mirror) {
        this(tgt, new OutputTo(mirror));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param mirror The copy destination
     */
    public TeeOutput(final Output tgt, final File mirror) {
        this(tgt, new OutputTo(mirror));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param mirror The copy destination
     */
    public TeeOutput(final Output tgt, final OutputStream mirror) {
        this(tgt, new OutputTo(mirror));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param mirror The copy destination
     */
    public TeeOutput(final Output tgt, final Output mirror) {
        this.target = tgt;
        this.copy = mirror;
    }

    @Override
    public OutputStream stream() throws Exception {
        return new TeeOutputStream(
            this.target.stream(), this.copy.stream()
        );
    }
}
