/**
 * The root package for the Forge permissions API.
 *
 * <h1>Permission Nodes</h1>
 * Permission nodes are in the form of {@link net.minecraft.util.ResourceLocation}s, where the path is a dot-separated ('{@code
 * .}') list of parts. There is no hard limit to node paths (except as imposed by the JVM or the {@code ResourceLocation}), but
 * it is expected that all node paths must not exceed 255 characters. Permission handler implementations are free to throw
 * exceptions when they encounter a permission node with 256 or more characters in the path.
 * <p>
 * The namespace of the permission node should be corresponding to a modid, though it is not required (such as for families of
 * mods adding their permission nodes to one shared namespace).
 * <p>
 * It is intended that parts of a node's path form a tree-like structure, where a node part may have sub-parts within. For
 * example, the permission node {@code examplemod:a.b.x} is from the mod {@code examplemod}, which has three parts: the root
 * {@code a}, the subpart of which is {@code b}, and the final subpart of which is {@code x}.
 *
 * <h2>Regarding Bukkit Syntax</h2>
 * The most popular syntax for permission nodes used by other server programs is the Bukkit syntax, where the permission node
 * is
 * a dot-separated ('{@code .}') list of parts, without any defined namespace. This syntax is from before the concept of
 * resource locations were introduced into the game, and has not changed since its inception.
 * <p>
 * It is a deliberate choice to partially break apart from that syntax and use {@code ResourceLocation}s for permission nodes;
 * the main reason is to keep with the current convention of Minecraft code where everything is identified with a {@code
 * ResourceLocation}.
 * <p>
 * Permission handler implementations are free to implement support Bukkit-syntax and handle the conversion of such to and from
 * {@code ResourceLocation}s. It is up to them to define the conversion logic, especially in the case of ambiguity whether
 * {@code first.second.third} corresponds to the node {@code first:second.third} or {@code minecraft:first.second.third}.
 *
 * @see dev.socketmods.socketperms.api.IPermissionHandler
 * @see dev.socketmods.socketperms.api.IPermissionValue
 */
// @do-not-replace
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
package dev.socketmods.socketperms.api;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;