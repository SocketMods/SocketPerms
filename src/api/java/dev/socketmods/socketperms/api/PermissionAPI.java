package dev.socketmods.socketperms.api;

import com.google.common.base.Preconditions;
import com.mojang.authlib.GameProfile;
import dev.socketmods.socketperms.api.context.PermissionContext;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PermissionAPI {
    private static final Logger LOGGER = LogManager.getLogger();
    private static IPermissionHandler handler = DefaultPermissionHandler.INSTANCE;

    public static void setHandler(IPermissionHandler newHandler) {
        Preconditions.checkNotNull(newHandler, "New permission handler must not be null");
        LOGGER.info("Replacing permission handler {} with {}", handler.getClass().getName(), newHandler.getClass().getName());
        handler = newHandler;
    }

    public static IPermissionHandler getHandler() {
        return handler;
    }

    public static IPermissionValue getPermissionValue(ResourceLocation node, GameProfile user, PermissionContext context) {
        return handler.getPermissionValue(node, user, context);
    }
}
