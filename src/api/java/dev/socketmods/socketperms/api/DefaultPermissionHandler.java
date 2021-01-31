package dev.socketmods.socketperms.api;

import com.mojang.authlib.GameProfile;
import dev.socketmods.socketperms.api.context.PermissionContext;
import net.minecraft.util.ResourceLocation;

/**
 * Default permission handler for the Forge permissions API.
 * <p>
 * This will always return {@link IPermissionValue#EMPTY} for all queries.
 */
public class DefaultPermissionHandler implements IPermissionHandler {
    /**
     * The singleton instance of the default permission handler.
     */
    public static final DefaultPermissionHandler INSTANCE = new DefaultPermissionHandler();

    private DefaultPermissionHandler() {} // Prevent instantiation

    /**
     * {@inheritDoc}
     *
     * @implNote This default permission handler will always return {@link IPermissionValue#EMPTY} for all queries.
     */
    @Override
    public IPermissionValue getPermissionValue(ResourceLocation node, GameProfile user, PermissionContext context) {
        return IPermissionValue.EMPTY;
    }
}
