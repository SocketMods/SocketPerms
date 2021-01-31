package dev.socketmods.socketperms.api;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

/**
 * A container object which may or may not contain a {@code boolean} value. If a value is present, {@link #isPresent()} will
 * return {@code true} and {@link #} will return the value.
 * <p>
 * Additional methods that depend on the presence or absence of a contained value are provided, such as {@link #map(Function)
 * map()} (maps a present value to another value) and {@link #ifPresent(BooleanConsumer) ifPresent()} (execute a block of code
 * if the value is present).
 * <p>
 * Because there is only three possible states for an {@code OptionalBoolean} (present value of {@code true}, present value of
 * {@code false}, no present value), there are three common instances which are stored and always used.
 * <p>
 * This is a value-based class; use of identity-sensitive operations (including reference equality ({@code ==}), identity hash
 * code, or synchronization) on instances of {@code OptionalBoolean} may have unpredictable results and should be avoided.
 *
 * @author LatvianModder, SciWhiz12 [SocketMods]
 * @see Optional
 */
public class OptionalBoolean {
    /**
     * Common instance for a present value of {@code true}.
     */
    private static final OptionalBoolean TRUE = new OptionalBoolean(true, true);
    /**
     * Common instance for a present value of {@code false}.
     */
    private static final OptionalBoolean FALSE = new OptionalBoolean(true, false);
    /**
     * Common instance for {@link #empty()}.
     */
    private static final OptionalBoolean EMPTY = new OptionalBoolean(false, false);

    /**
     * Returns an empty {@code OptionalBoolean} instance.  No value is present for this
     * OptionalBoolean.
     *
     * @return an empty {@code OptionalBoolean}.
     *
     * @apiNote Though it may be tempting to do so, avoid testing if an object
     * is empty by comparing with {@code ==} against instances returned by
     * {@code OptionalBoolean.empty()}. There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     */
    public static OptionalBoolean empty() {
        return EMPTY;
    }

    /**
     * Returns an {@code OptionalBoolean} with the specified value present.
     *
     * @param value the value to be present
     *
     * @return an {@code OptionalBoolean} with the value present
     */
    public static OptionalBoolean of(boolean value) {
        return value ? TRUE : FALSE;
    }

    /**
     * Returns an {@code OptionalBoolean} describing the specified value if non-null,
     * otherwise returns an empty {@code OptionalBoolean}.
     *
     * @param value the possibly-null {@code boolean} value
     *
     * @return an {@code OptionalBoolean} with a present value if the specified value is non-null, otherwise an empty {@code
     * OptionalBoolean}
     */
    public static OptionalBoolean ofNullable(Boolean value) {
        return value != null ? of(value) : EMPTY;
    }

    private final boolean isPresent;
    private final boolean value;

    private OptionalBoolean(boolean isPresent, boolean value) {
        this.isPresent = isPresent;
        this.value = value;
    }

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * @return {@code true} if there is a value present, otherwise {@code false}
     */
    public boolean isPresent() {
        return isPresent;
    }

    /**
     * If a value is present in this {@code OptionalBoolean}, returns the value,
     * otherwise throws {@code NoSuchElementException}.
     *
     * @return the value held by this {@code OptionalBoolean}
     *
     * @throws NoSuchElementException if there is no value present
     * @see OptionalBoolean#isPresent()
     */
    public boolean getAsBoolean() {
        if (!isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public <T> Optional<T> map(Function<Boolean, T> function) {
        if (isPresent()) {
            return Optional.ofNullable(function.apply(value));
        }
        return Optional.empty();
    }

    public <T> Optional<T> flatMap(Function<Boolean, Optional<T>> function) {
        if (isPresent()) {
            return function.apply(value);
        }
        return Optional.empty();
    }

    public void ifPresent(BooleanConsumer consumer) {
        if (isPresent()) {
            consumer.accept(value);
        }
    }
}
