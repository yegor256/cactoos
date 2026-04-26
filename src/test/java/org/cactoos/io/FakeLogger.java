/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Fake logger.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
public final class FakeLogger extends Logger {

    /**
     * Captured records handler.
     */
    private final FakeHandler handler;

    /**
     * Threshold level.
     */
    private final Level threshold;

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
        this.handler = new FakeHandler();
        this.threshold = lvl;
    }

    @Override
    public Level getLevel() {
        return this.threshold;
    }

    @Override
    public boolean getUseParentHandlers() {
        return false;
    }

    @Override
    public void log(final LogRecord record) {
        if (record.getLevel().intValue() >= this.threshold.intValue()) {
            this.handler.publish(record);
        }
    }

    @Override
    public String toString() {
        return this.handler.toString();
    }
}
