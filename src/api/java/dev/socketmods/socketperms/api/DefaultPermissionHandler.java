package dev.socketmods.socketperms.api;

import com.mojang.authlib.GameProfile;
import dev.socketmods.socketperms.api.context.PermissionContext;
import net.minecraft.util.ResourceLocation;

public class DefaultPermissionHandler implements IPermissionHandler {
    public static final DefaultPermissionHandler INSTANCE = new DefaultPermissionHandler();

    private DefaultPermissionHandler() {}

    @Override
    public IPermissionValue getPermissionValue(ResourceLocation node, GameProfile user, PermissionContext context) {
        return IPermissionValue.EMPTY;
    }
}
