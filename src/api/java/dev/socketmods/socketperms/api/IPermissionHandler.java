package dev.socketmods.socketperms.api;

import com.mojang.authlib.GameProfile;
import dev.socketmods.socketperms.api.context.PermissionContext;
import net.minecraft.util.ResourceLocation;

public interface IPermissionHandler {
    IPermissionValue getPermissionValue(ResourceLocation node, GameProfile user, PermissionContext context);
}
