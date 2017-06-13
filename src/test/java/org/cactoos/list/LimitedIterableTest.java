package org.cactoos.list;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LimitedIterable}
 *
 * @author Dusan Rychnovsky (dusan.rychnovsky@gmail.com)
 * @since 0.4
 */
public final class LimitedIterableTest {

    @Test
    public void iteratesOverPrefixOfGivenLength() {
        MatcherAssert.assertThat(
            "Can't limit an iterable with more items",
            new LimitedIterable<>(
                new ArrayAsIterable<>(0, 1, 2, 3, 4),
                3
            ),
            Matchers.contains(0, 1, 2)
        );
    }

    @Test
    public void iteratesOverWholeIterableIfThereAreNotEnoughItems() {
        MatcherAssert.assertThat(
            "Can't limit an iterable with less items",
            new LimitedIterable<>(
                new ArrayAsIterable<>(0, 1, 2, 3, 4),
                10
            ),
            Matchers.contains(0, 1, 2, 3, 4)
        );
    }
  
    @Test
    public void limitOfZeroProducesEmptyIterable() {
        MatcherAssert.assertThat(
            "Can't limit an iterable to zero items",
            new LimitedIterable<>(
                new ArrayAsIterable<>(0, 1, 2, 3, 4),
                0
            ),
            Matchers.iterableWithSize(0)
        );
    }

    @Test
    public void negativeLimitProducesEmptyIterable() {
        MatcherAssert.assertThat(
            "Can't limit an iterable to negative number of items",
            new LimitedIterable<>(
                new ArrayAsIterable<>(0, 1, 2, 3, 4),
                -1
            ),
            Matchers.iterableWithSize(0)
        );
    }

    @Test
    public void emptyIterableProducesEmptyIterable() {
        MatcherAssert.assertThat(
            "Can't limit an empty iterable",
            new LimitedIterable<>(
                new ArrayAsIterable<>(),
                10
            ),
            Matchers.iterableWithSize(0)
        );
    }
}
