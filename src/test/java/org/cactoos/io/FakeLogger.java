/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fake logger.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
@SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
public final class FakeLogger extends Logger {

    /**
     * Ctor.
     */
    public FakeLogger() {
        this(Level.INFO);
    }

    /**
     * Ctor.
     * @param lvl Logging level
     */
    public FakeLogger(final Level lvl) {
        this("FakeLogger", lvl);
    }

    /**
     * Ctor.
     * @param name Logger name
     */
    public FakeLogger(final String name) {
        this(name, Level.INFO);
    }

    /**
     * Ctor.
     * @param name Logger name
     * @param lvl Logging level
     */
    public FakeLogger(final String name, final Level lvl) {
        super(name, null);
        this.setUseParentHandlers(false);
        this.addHandler(new FakeHandler());
        this.setLevel(lvl);
    }

    @Override
    public String toString() {
        return this.getHandlers()[0].toString();
    }
}
