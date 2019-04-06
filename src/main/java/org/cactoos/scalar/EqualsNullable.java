package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Checks 2 objects for equality.
 * Nullable values are enabled.
 *
 * <p>This class implements {@link Scalar}, which throws first checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * <p>There is no thread-safety guarantee.
 */
public class EqualsNullable implements Scalar<Boolean> {
    private final Object first;
    private final Object second;

    public EqualsNullable(final Object first, final Object second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public Boolean value() throws Exception {
        return (this.first == this.second) || (this.first != null && this.first.equals(this.second));
    }
}
