package dev.socketmods.socketperms.api.context;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

/**
 * Standard context keys.
 * <p>
 * These context keys must be recognized by all {@linkplain dev.socketmods.socketperms.api.IPermissionHandler permission
 * handler} implementations, and can be used in {@linkplain PermissionContext permission contexts} by mods with the expectation
 * that permission handler implementors will be able to understand these.
 * <p>
 * All context keys under the {@code forge} namespace are reserved for use as standard context keys. If a mod wishes to add a
 * standard context key, they must use their own namespace and implement compatibility with in other permission handler
 * implementations, or add the context key to this class through the proper review and contribution channels for Forge.
 *
 * @author SciWhiz12, SocketMods
 */
public class StandardContextKeys {
    public static final ContextKey<World> WORLD = createKey("world", World.class);
    public static final ContextKey<Entity> THIS_ENTITY = createKey("this_entity", Entity.class);
    public static final ContextKey<Vector3d> THIS_POSITION = createKey("this_position", Vector3d.class);
    public static final ContextKey<Entity> TARGET_ENTITY = createKey("target_entity", Entity.class);
    public static final ContextKey<BlockPos> TARGET_BLOCK_POS = createKey("target_block_pos", BlockPos.class);
    public static final ContextKey<BlockState> TARGET_BLOCK_STATE = createKey("target_block_state", BlockState.class);
    public static final ContextKey<Direction> TARGET_BLOCK_FACE = createKey("target_block_face", Direction.class);

    private static <T> ContextKey<T> createKey(String id, Class<T> typeClass) {
        return new ContextKey<>(typeClass, new ResourceLocation("forge", id));
    }
}
