package dev.socketmods.socketperms.api.context;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import dev.socketmods.socketperms.api.IPermissionHandler;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import javax.annotation.Nullable;

/**
 * A permissions context holder.
 * <p>
 * This is used when querying permissions through the Forge permissions API, so permission handlers may do special actions
 * depending on the context/situation surrounding the permission query.
 * <p>
 * A context consists of {@link ContextKey}s to their associated values. A list of standard context keys can be found at {@link
 * StandardContextKeys}.
 * <p>
 * This class is immutable and thread-safe to access.
 *
 * @author SciWhiz12 [SocketMods]
 * @see ContextKey
 * @see IPermissionHandler#getPermissionValue(ResourceLocation, GameProfile, PermissionContext)
 */
public class PermissionContext {
    /**
     * An empty permissions context holder.
     */
    public static final PermissionContext EMPTY = new PermissionContext(ImmutableMap.of());

    private final Map<ContextKey<?>, Object> contextValues;

    private PermissionContext(Map<ContextKey<?>, Object> contextValues) {
        this.contextValues = ImmutableMap.copyOf(contextValues);
    }

    /**
     * Return {@code true} whether there is a stored value for the given context key, otherwise {@code false}.
     *
     * @param key The context key
     *
     * @return {@code true} if there is a value for the given key, otherwise {@code false}
     */
    public boolean has(ContextKey<?> key) {
        return contextValues.containsKey(key);
    }

    /**
     * Return the stored value for the given context key, otherwise {@code null} if none is stored.
     *
     * @param key The context key
     * @param <T> The type of the value
     *
     * @return The value for the given key, or {@code null} if none is stored
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T getNullable(ContextKey<T> key) {
        return (T) contextValues.get(key);
    }

    /**
     * Return a {@link Optional} containing the value for the given context key, otherwise an {@linkplain Optional#empty()} if
     * none is stored.
     *
     * @param key The context key
     * @param <T> The type of the value
     *
     * @return The possibly-empty {@code Optional} containing the value for the key
     *
     * @see #getNullable(ContextKey)
     */
    public <T> Optional<T> get(ContextKey<T> key) {
        return Optional.ofNullable(getNullable(key));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionContext that = (PermissionContext) o;
        return contextValues.equals(that.contextValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contextValues);
    }

    @Override
    public String toString() {
        final StringJoiner joiner = new StringJoiner(", ", "PermissionContext[", "]");
        for (Map.Entry<ContextKey<?>, Object> entry : contextValues.entrySet()) {
            joiner.add(entry.getKey().getId().toString() + "=" + entry.getValue());
        }
        return joiner.toString();
    }

    /**
     * Return a new {@link Builder} for a permissions context.
     *
     * @return A new context builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for the permissions context object.
     * <p>
     * <strong>Note:</strong> This builder is <em>not</em> thread-safe, but the resulting {@code PermissionContext} object is
     * immutable
     * and thread-safe.
     *
     * @author SciWhiz12 [SocketMods]
     * @see PermissionContext
     */
    public static class Builder {
        private final Map<ContextKey<?>, Object> contextValues;

        Builder() {
            contextValues = new HashMap<>();
        }

        /**
         * Add the given {@linkplain ContextKey context key} and value pair to this context builder.
         * <p>
         * If there is a previous value associated with the given context key, it will be replaced.
         *
         * @param key   The context key
         * @param value The context value
         * @param <T>   The type of the context key and value
         *
         * @return This builder instance, for chaining
         *
         * @throws NullPointerException if either of the given context key and the given value is null
         */
        public <T> Builder add(ContextKey<T> key, T value) {
            Preconditions.checkNotNull(key, "Context key must not be null");
            Preconditions.checkNotNull(value, "Context value must not be null");
            contextValues.put(key, value);
            return this;
        }

        /**
         * Build the {@link PermissionContext} object from the values in this builder.
         * <p>
         * If this builder is empty, then this will return {@link PermissionContext#EMPTY}.
         *
         * @return The built permission context object
         */
        public PermissionContext build() {
            if (contextValues.isEmpty()) return PermissionContext.EMPTY;
            return new PermissionContext(contextValues);
        }
    }
}
