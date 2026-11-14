/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Decorator that doesn't allow mutations of the wrapped {@link Map}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <K> Type of key
 * @param <V> Type of value
 * @since 0.67
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class Immutable<K, V> implements Map<K, V> {

    /**
     * Encapsulated map.
     */
    private final Map<K, V> map;

    /**
     * Ctor.
     * @param src Source map
     */
    @SuppressWarnings("unchecked")
    public Immutable(final Map<? extends K, ? extends V> src) {
        this.map = (Map<K, V>) src;
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public V get(final Object key) {
        return this.map.get(key);
    }

    @Override
    public V put(final K key, final V value) {
        throw new UnsupportedOperationException(
            "#put(K,V): the map is read-only"
        );
    }

    @Override
    public V remove(final Object key) {
        throw new UnsupportedOperationException(
            "#remove(Object): the map is read-only"
        );
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> entries) {
        throw new UnsupportedOperationException(
            "#putAll(Map): the map is read-only"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
            "#clear(): the map is read-only"
        );
    }

    @Override
    public Set<K> keySet() {
        return new org.cactoos.set.Immutable<>(this.map.keySet());
    }

    @Override
    public Collection<V> values() {
        return new org.cactoos.collection.Immutable<>(this.map.values());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new Immutable.ImmutableEntries<>(this.map.entrySet());
    }

    @Override
    public boolean equals(final Object other) {
        return this.map.equals(other);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public String toString() {
        return this.map.toString();
    }

    /**
     * Immutable set of map entries.
     * @param <X> Key type
     * @param <Y> Value type
     * @since 0.67
     */
    @SuppressWarnings("PMD.TooManyMethods")
    private static final class ImmutableEntries<X, Y> implements Set<Entry<X, Y>> {

        /**
         * Original entries.
         */
        private final Set<Entry<X, Y>> entries;

        /**
         * Ctor.
         * @param src Source entries
         */
        ImmutableEntries(final Set<Entry<X, Y>> src) {
            this.entries = src;
        }

        @Override
        public int size() {
            return this.entries.size();
        }

        @Override
        public boolean isEmpty() {
            return this.entries.isEmpty();
        }

        @Override
        public boolean contains(final Object item) {
            return this.entries.contains(item);
        }

        @Override
        public Iterator<Entry<X, Y>> iterator() {
            return new Immutable.ImmutableEntryIterator<>(this.entries.iterator());
        }

        @Override
        public Object[] toArray() {
            return this.entries.toArray();
        }

        @Override
        public <Z> Z[] toArray(final Z[] array) {
            return this.entries.toArray(array);
        }

        @Override
        public boolean add(final Entry<X, Y> item) {
            throw new UnsupportedOperationException(
                "#add(Entry): the entry set is read-only"
            );
        }

        @Override
        public boolean remove(final Object item) {
            throw new UnsupportedOperationException(
                "#remove(Object): the entry set is read-only"
            );
        }

        @Override
        public boolean containsAll(final Collection<?> items) {
            return this.entries.containsAll(items);
        }

        @Override
        public boolean addAll(final Collection<? extends Entry<X, Y>> items) {
            throw new UnsupportedOperationException(
                "#addAll(Collection): the entry set is read-only"
            );
        }

        @Override
        public boolean retainAll(final Collection<?> items) {
            throw new UnsupportedOperationException(
                "#retainAll(Collection): the entry set is read-only"
            );
        }

        @Override
        public boolean removeAll(final Collection<?> items) {
            throw new UnsupportedOperationException(
                "#removeAll(Collection): the entry set is read-only"
            );
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException(
                "#clear(): the entry set is read-only"
            );
        }

        @Override
        public boolean equals(final Object other) {
            return this.entries.equals(other);
        }

        @Override
        public int hashCode() {
            return this.entries.hashCode();
        }

        @Override
        public String toString() {
            return this.entries.toString();
        }
    }

    /**
     * Iterator over immutable map entries.
     * @param <X> Key type
     * @param <Y> Value type
     * @since 0.67
     */
    private static final class ImmutableEntryIterator<X, Y> implements
        Iterator<Entry<X, Y>> {

        /**
         * Original iterator.
         */
        private final Iterator<Entry<X, Y>> iterator;

        /**
         * Ctor.
         * @param src Source iterator
         */
        ImmutableEntryIterator(final Iterator<Entry<X, Y>> src) {
            this.iterator = src;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public Entry<X, Y> next() {
            return new Immutable.ImmutableEntry<>(this.iterator.next());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                "#remove(): the iterator is read-only"
            );
        }
    }

    /**
     * Immutable map entry.
     * @param <X> Key type
     * @param <Y> Value type
     * @since 0.67
     */
    private static final class ImmutableEntry<X, Y> implements Entry<X, Y> {

        /**
         * Original entry.
         */
        private final Entry<X, Y> entry;

        /**
         * Ctor.
         * @param src Source entry
         */
        ImmutableEntry(final Entry<X, Y> src) {
            this.entry = src;
        }

        @Override
        public X getKey() {
            return this.entry.getKey();
        }

        @Override
        public Y getValue() {
            return this.entry.getValue();
        }

        @Override
        public Y setValue(final Y value) {
            throw new UnsupportedOperationException(
                "#setValue(V): the entry is read-only"
            );
        }

        @Override
        public boolean equals(final Object other) {
            return this.entry.equals(other);
        }

        @Override
        public int hashCode() {
            return this.entry.hashCode();
        }

        @Override
        public String toString() {
            return this.entry.toString();
        }
    }
}
