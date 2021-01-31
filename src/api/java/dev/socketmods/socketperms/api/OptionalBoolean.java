package dev.socketmods.socketperms.api;

import com.google.common.base.Preconditions;
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
 * Because there are only three possible states for an {@code OptionalBoolean} (present value of {@code true}, present value of
 * {@code false}, no present value), there are three common instances which are stored and always reused.
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

    /**
     * If a value is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code Optional} describing the
     * result.  Otherwise return an empty {@code Optional}.
     *
     * @param <T>    The type of the result of the mapping function
     * @param mapper a mapping function to apply to the value, if present
     *
     * @return an {@code Optional} describing the result of applying a mapping
     * function to the value of this {@code OptionalBoolean}, if a value is present,
     * otherwise an empty {@code Optional}
     *
     * @throws NullPointerException if the mapping function is null
     */
    public <T> Optional<T> map(Function<Boolean, ? extends T> mapper) {
        Preconditions.checkNotNull(mapper, "Mapping function must not be null");
        if (isPresent()) {
            return Optional.ofNullable(mapper.apply(value));
        }
        return Optional.empty();
    }

    /**
     * If a value is present, apply the provided {@code Optional}-bearing
     * mapping function to it, return that result, otherwise return an empty
     * {@code Optional}.  This method is similar to {@link #map(Function)},
     * but the provided mapper is one whose result is already an {@code Optional},
     * and if invoked, {@code flatMap} does not wrap it with an additional
     * {@code Optional}.
     *
     * @param <T>    The type parameter to the {@code Optional} returned by
     * @param mapper a mapping function to apply to the value, if present
     *               the mapping function
     *
     * @return the result of applying an {@code Optional}-bearing mapping
     * function to the value of this {@code Optional}, if a value is present,
     * otherwise an empty {@code Optional}
     *
     * @throws NullPointerException if the mapping function is null or returns
     *                              a null result
     */
    public <T> Optional<T> flatMap(Function<Boolean, Optional<T>> mapper) {
        Preconditions.checkNotNull(mapper, "Mapping function must not be null");
        if (isPresent()) {
            final Optional<T> ret = mapper.apply(value);
            Preconditions.checkNotNull(ret, "Returned Optional from mapping function must not be null");
            return ret;
        }
        return Optional.empty();
    }

    /**
     * Have the specified consumer accept the value if a value is present,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     *
     * @throws NullPointerException if value is present and {@code consumer} is
     *                              null
     */
    public void ifPresent(BooleanConsumer consumer) {
        if (isPresent()) {
            consumer.accept(value);
        }
    }

    /**
     * {@inheritDoc}
     * Returns a non-empty string representation of this object suitable for
     * debugging.
     *
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return isPresent
            ? String.format("OptionalBoolean[%s]", value)
            : "OptionalBoolean.empty";
    }
}
