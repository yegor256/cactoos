package org.cactoos.scalar;

import org.cactoos.Scalar;

public class EqualsNullable implements Scalar<Boolean> {
    private final Object a, b;

    public EqualsNullable (Object a, Object b) {
        this.a = a;
        this.b = b;
    }
    @Override
    public Boolean value() throws Exception {
        return (a == b) || (a != null && a.equals(b));
    }
}
