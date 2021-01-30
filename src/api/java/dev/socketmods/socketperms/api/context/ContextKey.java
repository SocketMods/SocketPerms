package dev.socketmods.socketperms.api.context;

import com.google.common.base.Preconditions;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

/**
 * A context key for a {@link PermissionContext permission context holder}.
 * <p>
 *
 * <p>
 * A context key has an associated {@link ResourceLocation} as an ID. This may be used by permission handlers to identify
 * specific context keys and values, such as for handling based on dynamic permission rules.
 *
 * @param <T> The type of the value for this context key
 *
 * @author SciWhiz12 [SocketMods]
 */
public class ContextKey<T> {
    private final Class<T> typeClass;
    private final ResourceLocation id;

    /**
     * Constructs a new {@code ContextKey}.
     *
     * @param typeClass The class of the type of the value for the context key
     * @param id        The ID of the context key
     *
     * @throws NullPointerException If either of the given class and ID is null
     */
    public ContextKey(Class<T> typeClass, ResourceLocation id) {
        Preconditions.checkNotNull(typeClass, "Type class must not be null");
        Preconditions.checkNotNull(id, "ID must not be null");
        this.typeClass = typeClass;
        this.id = id;
    }

    /**
     * Returns the {@link Class} of the type of the value that can be associated with this key.
     *
     * @return The class of the value for the context key
     */
    public Class<T> getTypeClass() {
        return typeClass;
    }

    /**
     * Returns the ID of this context key.
     *
     * @return The ID of this key.
     */
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContextKey<?> that = (ContextKey<?>) o;
        return typeClass.equals(that.typeClass) && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeClass, id);
    }
}
