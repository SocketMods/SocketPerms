package dev.socketmods.socketperms.api;

import com.mojang.authlib.GameProfile;
import dev.socketmods.socketperms.api.context.PermissionContext;
import net.minecraft.util.ResourceLocation;

/**
 * Interface for a permission handler.
 * <p>
 * A <em>permission handler</em> is responsible for returning a {@link IPermissionValue} based on a permission node (in
 * the form of a {@link ResourceLocation}), the user who triggered the permission check (in the form of a {@link GameProfile}),
 * and a {@link PermissionContext} object.
 * <p>
 * This interface only implements a <strong>read-only</strong> view for permissions. It is up to the individual implementation
 * and interacting mods to define their own interfaces for modification of permission values. For the sake of simplicity, the
 * Forge permissions API will only show a read-only view.
 *
 * @author SciWhiz12 [SocketMods]
 */
public interface IPermissionHandler {
    /**
     * Return a permission value for the given permission node, user, and context, or an {@link IPermissionValue#isEmpty() empty
     * permission value} if there is no such value for these parameters.
     *
     * @param node    The permission value
     * @param user    The user who triggered the permission check
     * @param context The permission context
     *
     * @return The permission value for the given parameters if such a value exists, otherwise an {@link
     * IPermissionValue#isEmpty() empty permission value}
     */
    IPermissionValue getPermissionValue(ResourceLocation node, GameProfile user, PermissionContext context);
}
