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
 * A decorator of {@link Map} that prevents any mutations.
 *
 * <p>This decorator wraps an existing map and blocks all write operations
 * by throwing {@link UnsupportedOperationException}. Read operations are
 * delegated to the wrapped map. The views returned by {@link #keySet()},
 * {@link #values()}, and {@link #entrySet()} are also immutable.</p>
 *
 * <p>This class addresses the need for an immutable map decorator that
 * follows Cactoos object-oriented patterns, similar to the existing
 * {@link org.cactoos.list.Immutable}, {@link org.cactoos.set.Immutable},
 * and {@link org.cactoos.collection.Immutable} decorators.</p>
 *
 * <h3>Protected Operations</h3>
 * <p>The following operations throw {@link UnsupportedOperationException}:</p>
 * <ul>
 *   <li>{@link #put(Object, Object)}</li>
 *   <li>{@link #remove(Object)}</li>
 *   <li>{@link #putAll(Map)}</li>
 *   <li>{@link #clear()}</li>
 *   <li>All mutation operations on {@link #keySet()}, {@link #values()},
 *       and {@link #entrySet()}</li>
 *   <li>{@link Entry#setValue(Object)} on entries from {@link #entrySet()}</li>
 *   <li>{@link Iterator#remove()} on all returned iterators</li>
 * </ul>
 *
 * <h3>Important Note</h3>
 * <p>This is a <strong>decorator</strong>, not a copy. If the underlying map
 * is modified through a reference that bypasses this decorator, those changes
 * will be visible through this wrapper. For truly immutable snapshots, first
 * copy the map contents or use with {@link MapOf} which creates a copy.</p>
 *
 * <h3>Example Usage</h3>
 * <pre>{@code
 * // Create an immutable map
 * Map<String, Integer> map = new Immutable<>(
 *     new MapOf<>(
 *         new MapEntry<>("one", 1),
 *         new MapEntry<>("two", 2)
 *     )
 * );
 *
 * // Read operations work normally
 * map.get("one");           // returns 1
 * map.containsKey("two");   // returns true
 * map.size();               // returns 2
 *
 * // Write operations are blocked
 * map.put("three", 3);      // throws UnsupportedOperationException
 * map.remove("one");        // throws UnsupportedOperationException
 * map.clear();              // throws UnsupportedOperationException
 *
 * // Views are also immutable
 * map.keySet().add("four"); // throws UnsupportedOperationException
 * map.values().clear();     // throws UnsupportedOperationException
 *
 * // Safe to pass to untrusted code
 * public Map<String, Config> getConfig() {
 *     return new Immutable<>(this.configMap);
 * }
 * }</pre>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <K> Type of key
 * @param <V> Type of value
 * @see org.cactoos.list.Immutable
 * @see org.cactoos.set.Immutable
 * @see org.cactoos.collection.Immutable
 * @see MapOf
 * @since 0.67
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class Immutable<K, V> implements Map<K, V> {

    /**
     * The wrapped map.
     *
     * <p>We use {@code Map<? extends K, ? extends V>} to allow
     * covariant map types to be wrapped (e.g., a {@code Map<String, Integer>}
     * can be wrapped as {@code Immutable<Object, Number>}).</p>
     */
    private final Map<? extends K, ? extends V> map;

    /**
     * Primary constructor.
     *
     * @param origin The map to wrap
     */
    public Immutable(final Map<? extends K, ? extends V> origin) {
        this.map = origin;
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

    /**
     * Always throws {@link UnsupportedOperationException}.
     *
     * <p>This map is read-only. To "add" an entry, create a new
     * {@link MapOf} that includes the original entries plus the new one.</p>
     *
     * @param key Key with which the value is to be associated
     * @param value Value to be associated with the key
     * @return Never returns normally
     * @throws UnsupportedOperationException Always
     */
    @Override
    public V put(final K key, final V value) {
        throw new UnsupportedOperationException(
            "#put(K,V): the map is read-only"
        );
    }

    /**
     * Always throws {@link UnsupportedOperationException}.
     *
     * <p>This map is read-only. To "remove" an entry, create a new
     * map that excludes the unwanted key.</p>
     *
     * @param key Key whose mapping is to be removed
     * @return Never returns normally
     * @throws UnsupportedOperationException Always
     */
    @Override
    public V remove(final Object key) {
        throw new UnsupportedOperationException(
            "#remove(Object): the map is read-only"
        );
    }

    /**
     * Always throws {@link UnsupportedOperationException}.
     *
     * <p>This map is read-only. To merge maps, create a new
     * {@link MapOf} or use {@link Merged}.</p>
     *
     * @param entries Mappings to be stored in this map
     * @throws UnsupportedOperationException Always
     */
    @Override
    public void putAll(final Map<? extends K, ? extends V> entries) {
        throw new UnsupportedOperationException(
            "#putAll(Map): the map is read-only"
        );
    }

    /**
     * Always throws {@link UnsupportedOperationException}.
     *
     * <p>This map is read-only. For an empty map, use
     * {@code new MapOf<>()}.</p>
     *
     * @throws UnsupportedOperationException Always
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException(
            "#clear(): the map is read-only"
        );
    }

    /**
     * Returns an immutable view of the keys.
     *
     * <p>The returned set does not support {@code add()}, {@code remove()},
     * or any other mutating operation. Its iterator also does not support
     * {@code remove()}.</p>
     *
     * @return An immutable set of keys
     */
    @Override
    public Set<K> keySet() {
        return new org.cactoos.set.Immutable<>(
            new MappedKeys<>(this.map.keySet())
        );
    }

    /**
     * Returns an immutable view of the values.
     *
     * <p>The returned collection does not support {@code add()},
     * {@code remove()}, or any other mutating operation.</p>
     *
     * @return An immutable collection of values
     */
    @Override
    public Collection<V> values() {
        return new org.cactoos.collection.Immutable<>(
            new MappedValues<>(this.map.values())
        );
    }

    /**
     * Returns an immutable view of the entries.
     *
     * <p>The returned set does not support {@code add()}, {@code remove()},
     * or any other mutating operation. Each entry's {@code setValue()}
     * method will throw {@link UnsupportedOperationException}.</p>
     *
     * @return An immutable set of map entries
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new org.cactoos.set.Immutable<>(
            new ImmutableEntrySet<>(this.map.entrySet())
        );
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
     * Adapter that presents a {@code Set<? extends K>} as a {@code Set<K>}.
     *
     * <p>This is safe because the set is only used for reading.</p>
     *
     * @param <K> Key type
     * @since 0.67
     */
    private static final class MappedKeys<K> implements Set<K> {

        /**
         * The original key set.
         */
        private final Set<? extends K> keys;

        /**
         * Ctor.
         * @param origin The original key set
         */
        MappedKeys(final Set<? extends K> origin) {
            this.keys = origin;
        }

        @Override
        public int size() {
            return this.keys.size();
        }

        @Override
        public boolean isEmpty() {
            return this.keys.isEmpty();
        }

        @Override
        public boolean contains(final Object obj) {
            return this.keys.contains(obj);
        }

        @Override
        public Iterator<K> iterator() {
            return new org.cactoos.iterator.Immutable<>(this.keys.iterator());
        }

        @Override
        public Object[] toArray() {
            return this.keys.toArray();
        }

        @Override
        @SuppressWarnings("PMD.UseVarargs")
        public <T> T[] toArray(final T[] arr) {
            return this.keys.toArray(arr);
        }

        @Override
        public boolean add(final K key) {
            throw new UnsupportedOperationException(
                "#add(K): the key set is read-only"
            );
        }

        @Override
        public boolean remove(final Object obj) {
            throw new UnsupportedOperationException(
                "#remove(Object): the key set is read-only"
            );
        }

        @Override
        public boolean containsAll(final Collection<?> col) {
            return this.keys.containsAll(col);
        }

        @Override
        public boolean addAll(final Collection<? extends K> col) {
            throw new UnsupportedOperationException(
                "#addAll(Collection): the key set is read-only"
            );
        }

        @Override
        public boolean retainAll(final Collection<?> col) {
            throw new UnsupportedOperationException(
                "#retainAll(Collection): the key set is read-only"
            );
        }

        @Override
        public boolean removeAll(final Collection<?> col) {
            throw new UnsupportedOperationException(
                "#removeAll(Collection): the key set is read-only"
            );
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException(
                "#clear(): the key set is read-only"
            );
        }
    }

    /**
     * Adapter that presents a {@code Collection<? extends V>} as
     * a {@code Collection<V>}.
     *
     * <p>This is safe because the collection is only used for reading.</p>
     *
     * @param <V> Value type
     * @since 0.67
     */
    private static final class MappedValues<V> implements Collection<V> {

        /**
         * The original values collection.
         */
        private final Collection<? extends V> vals;

        /**
         * Ctor.
         * @param origin The original values collection
         */
        MappedValues(final Collection<? extends V> origin) {
            this.vals = origin;
        }

        @Override
        public int size() {
            return this.vals.size();
        }

        @Override
        public boolean isEmpty() {
            return this.vals.isEmpty();
        }

        @Override
        public boolean contains(final Object obj) {
            return this.vals.contains(obj);
        }

        @Override
        public Iterator<V> iterator() {
            return new org.cactoos.iterator.Immutable<>(this.vals.iterator());
        }

        @Override
        public Object[] toArray() {
            return this.vals.toArray();
        }

        @Override
        @SuppressWarnings("PMD.UseVarargs")
        public <T> T[] toArray(final T[] arr) {
            return this.vals.toArray(arr);
        }

        @Override
        public boolean add(final V val) {
            throw new UnsupportedOperationException(
                "#add(V): the values collection is read-only"
            );
        }

        @Override
        public boolean remove(final Object obj) {
            throw new UnsupportedOperationException(
                "#remove(Object): the values collection is read-only"
            );
        }

        @Override
        public boolean containsAll(final Collection<?> col) {
            return this.vals.containsAll(col);
        }

        @Override
        public boolean addAll(final Collection<? extends V> col) {
            throw new UnsupportedOperationException(
                "#addAll(Collection): the values collection is read-only"
            );
        }

        @Override
        public boolean retainAll(final Collection<?> col) {
            throw new UnsupportedOperationException(
                "#retainAll(Collection): the values collection is read-only"
            );
        }

        @Override
        public boolean removeAll(final Collection<?> col) {
            throw new UnsupportedOperationException(
                "#removeAll(Collection): the values collection is read-only"
            );
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException(
                "#clear(): the values collection is read-only"
            );
        }
    }

    /**
     * An immutable view of the entry set.
     *
     * <p>Each entry returned by this set's iterator is wrapped in
     * {@link ImmutableEntry} to prevent mutations via
     * {@link Entry#setValue(Object)}.</p>
     *
     * @param <K> Key type
     * @param <V> Value type
     * @since 0.67
     */
    private static final class ImmutableEntrySet<K, V>
        implements Set<Entry<K, V>> {

        /**
         * The original entry set.
         */
        private final Set<? extends Entry<? extends K, ? extends V>> entries;

        /**
         * Ctor.
         * @param origin The original entry set
         */
        ImmutableEntrySet(
            final Set<? extends Entry<? extends K, ? extends V>> origin
        ) {
            this.entries = origin;
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
        public boolean contains(final Object obj) {
            return this.entries.contains(obj);
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new ImmutableEntryIterator<>(this.entries.iterator());
        }

        @Override
        public Object[] toArray() {
            return this.entries.toArray();
        }

        @Override
        @SuppressWarnings("PMD.UseVarargs")
        public <T> T[] toArray(final T[] arr) {
            return this.entries.toArray(arr);
        }

        @Override
        public boolean add(final Entry<K, V> entry) {
            throw new UnsupportedOperationException(
                "#add(Entry): the entry set is read-only"
            );
        }

        @Override
        public boolean remove(final Object obj) {
            throw new UnsupportedOperationException(
                "#remove(Object): the entry set is read-only"
            );
        }

        @Override
        public boolean containsAll(final Collection<?> col) {
            return this.entries.containsAll(col);
        }

        @Override
        public boolean addAll(final Collection<? extends Entry<K, V>> col) {
            throw new UnsupportedOperationException(
                "#addAll(Collection): the entry set is read-only"
            );
        }

        @Override
        public boolean retainAll(final Collection<?> col) {
            throw new UnsupportedOperationException(
                "#retainAll(Collection): the entry set is read-only"
            );
        }

        @Override
        public boolean removeAll(final Collection<?> col) {
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
    }

    /**
     * An iterator that wraps each entry in {@link ImmutableEntry}.
     *
     * <p>This iterator does not support {@code remove()}.</p>
     *
     * @param <K> Key type
     * @param <V> Value type
     * @since 0.67
     */
    private static final class ImmutableEntryIterator<K, V>
        implements Iterator<Entry<K, V>> {

        /**
         * The original iterator.
         */
        private final Iterator<? extends Entry<? extends K, ? extends V>> iter;

        /**
         * Ctor.
         * @param origin The original iterator
         */
        ImmutableEntryIterator(
            final Iterator<? extends Entry<? extends K, ? extends V>> origin
        ) {
            this.iter = origin;
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public Entry<K, V> next() {
            return new ImmutableEntry<>(this.iter.next());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                "#remove(): the entry iterator is read-only"
            );
        }
    }

    /**
     * A decorator for {@link Entry} that blocks {@link #setValue(Object)}.
     *
     * <p>This ensures that even if the original map's entries support
     * mutation, this wrapper prevents it.</p>
     *
     * @param <K> Key type
     * @param <V> Value type
     * @since 0.67
     */
    private static final class ImmutableEntry<K, V> implements Entry<K, V> {

        /**
         * The wrapped entry.
         */
        private final Entry<? extends K, ? extends V> entry;

        /**
         * Ctor.
         * @param origin The entry to wrap
         */
        ImmutableEntry(final Entry<? extends K, ? extends V> origin) {
            this.entry = origin;
        }

        @Override
        public K getKey() {
            return this.entry.getKey();
        }

        @Override
        public V getValue() {
            return this.entry.getValue();
        }

        @Override
        public V setValue(final V value) {
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
