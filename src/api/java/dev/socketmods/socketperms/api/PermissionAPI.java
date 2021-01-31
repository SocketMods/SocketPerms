package dev.socketmods.socketperms.api;

import com.google.common.base.Preconditions;
import com.mojang.authlib.GameProfile;
import dev.socketmods.socketperms.api.context.PermissionContext;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.annotation.Nullable;

/**
 * The main API class for the Forge permissions API.
 * <p>
 * This holds the current global permission handler (through {@link #getHandler()}), and methods to replace the global
 * permission handler ({@link #setHandler(IPermissionHandler)}) and to collect known permission nodes from mods (through {@link
 * #collectKnownNodes(String)}).
 * <p>
 * The default permission handler is the {@link DefaultPermissionHandler}.
 *
 * @author SciWhiz12 [SocketMods]
 */
public class PermissionAPI {
    private static final Logger LOGGER = LogManager.getLogger();
    private static volatile IPermissionHandler handler = DefaultPermissionHandler.INSTANCE;

    /**
     * Set the new global permission handler.
     * <p>
     * This will also log a message informing the replacement of the old permission handler with the new permission handler.
     * <p>
     * If the {@code newHandler} is the same instance as the {@linkplain #getHandler() current permission handler}, then this
     * will have no effect.
     * <p>
     * This method is safe to call from {@linkplain net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent parallel
     * modloading events}.
     *
     * @param newHandler The new permission handler
     *
     * @throws NullPointerException If {@code newHandler} is {@code null}
     */
    public static synchronized void setHandler(IPermissionHandler newHandler) {
        Preconditions.checkNotNull(newHandler, "New permission handler must not be null");
        if (handler == newHandler) return;
        LOGGER.info("Replacing permission handler {} with {}", handler.getClass().getName(), newHandler.getClass().getName());
        handler = newHandler;
    }

    /**
     * Return the current global permission handler.
     *
     * @return The global permission handler
     *
     * @see #setHandler(IPermissionHandler)
     * @see DefaultPermissionHandler
     */
    public static IPermissionHandler getHandler() {
        return handler;
    }

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
     *
     * @see IPermissionHandler#getPermissionValue(ResourceLocation, GameProfile, PermissionContext)
     */
    public static IPermissionValue getPermissionValue(ResourceLocation node, GameProfile user, PermissionContext context) {
        return handler.getPermissionValue(node, user, context);
    }

    /**
     * Collect all known permissions nodes and returns them in a list.
     * <p>
     * This fires off an instance of {@link PermissionCollectionEvent} on the {@linkplain MinecraftForge#EVENT_BUS main Forge
     * event bus}. The method should be called infrequently, and the results of this method should be cached by callers, as it
     * is expected (but not required) that permission-having mods will not change their known permission nodes too often.
     * <p>
     * If the given {@code modid} parameter is non-null, it is expected that event listeners will only add their permission
     * nodes if their modid matches the given modid, though this is not a requirement (such as mods who share one namespace for
     * all permission nodes).
     *
     * @param modid The modid to filter nodes for, may be {@code null}
     *
     * @return The list of known permission nodes
     */
    public static List<ResourceLocation> collectKnownNodes(@Nullable String modid) {
        PermissionCollectionEvent event = new PermissionCollectionEvent(modid);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getNodes();
    }
}
