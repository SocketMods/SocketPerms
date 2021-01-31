package dev.socketmods.socketperms;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.mojang.authlib.GameProfile;
import dev.socketmods.socketperms.api.IPermissionHandler;
import dev.socketmods.socketperms.api.IPermissionValue;
import dev.socketmods.socketperms.api.OptionalBoolean;
import dev.socketmods.socketperms.api.PermissionAPI;
import dev.socketmods.socketperms.api.context.PermissionContext;
import dev.socketmods.socketperms.values.PermissionValue;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import javax.annotation.Nullable;

public class SocketPermissionHandler implements IPermissionHandler {
    private final CommentedConfig permissions = TomlFormat.newConfig();
    private final List<ResourceLocation> knownNodes = new ArrayList<>(100);

    public SocketPermissionHandler() {
        // FIXME: this is only for testing

        permissions.set("socketperms.command", true);
        permissions.set("socketperms.interact.entity", 1.0d);
        permissions.set("socketperms.interact.block", 2L);
    }

    public List<ResourceLocation> getKnownNodes() {
        if (knownNodes.isEmpty()) {
            knownNodes.addAll(PermissionAPI.collectKnownNodes(null));
        }
        return knownNodes;
    }

    public void invalidateKnownNodes() {
        knownNodes.clear();
    }

    @Override
    public IPermissionValue getPermissionValue(ResourceLocation node, GameProfile user, PermissionContext context) {
        if (!permissions.contains(node.getNamespace())) {
            permissions.set(node.getNamespace(), permissions.createSubConfig());
        }
        Object value = permissions.<UnmodifiableConfig>get(node.getNamespace()).get(node.getPath());
        IPermissionValue permValue = wrapValue(value);
        if (value != null && permValue.isEmpty()) {
            permissions.set(node.getPath(), null);
        }
        return permValue;
    }

    static <T> IPermissionValue wrapValue(@Nullable T obj) {
        OptionalBoolean optBool = OptionalBoolean.empty();
        OptionalInt optInt = OptionalInt.empty();
        OptionalLong optLong = OptionalLong.empty();
        OptionalDouble optDouble = OptionalDouble.empty();
        if (obj instanceof Number) {
            Number num = (Number) obj;
            optInt = OptionalInt.of(num.intValue());
            optLong = OptionalLong.of(num.longValue());
            optDouble = OptionalDouble.of(num.doubleValue());
        } else if (obj instanceof Boolean) {
            optBool = OptionalBoolean.of((Boolean) obj);
        }
        return new PermissionValue(optBool, optInt, optLong, optDouble);
    }
}
