package dev.socketmods.socketperms.api;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

/**
 * Event for collecting known permission nodes from mods.
 * <p>
 * This is used by mods to collect all known permission nodes from either all mods or a specific modid, for use in e.g.
 * displaying these nodes in an interface to users.
 * <p>
 * <strong>Note: </strong> This event will only hold the known permission nodes that mods willing add to the event. It does not
 * hold <em>all</em> valid permission nodes which are recognized by mods, which can be due to being dynamically recognized
 * permissions.
 *
 * @author SciWhiz12 [SocketMods]
 * @see PermissionAPI#collectKnownNodes(String)
 */
public class PermissionCollectionEvent extends Event {
    @Nullable
    private final String modid;
    private final List<ResourceLocation> permissionNodes = new ArrayList<>(100);

    public PermissionCollectionEvent(@Nullable String modid) {
        this.modid = modid;
    }

    /**
     * Return the filtering modid for this event, or {@code null} if none is found.
     * <p>
     * If this modid is non-{@code null}, then it is expected that event listeners which will add permission nodes to this
     * event
     * will only add if their modid matches this stored modid.
     * <p>
     * However, this is not a hard requirement; event listeners may choose to add their permission nodes even if their modid
     * does not match the stored modid. This is useful for situations where a family of mods add their permission nodes under
     * one single namespace.
     *
     * @return The filter modid
     */
    @Nullable
    public String getModid() {
        return modid;
    }

    /**
     * Add the given permission node to the list of known permission nodes in this event.
     *
     * @param node The permission node to add
     */
    public void addNode(ResourceLocation node) {
        permissionNodes.add(node);
    }

    /**
     * Add the given permission node only if the given modid matches the {@linkplain #getModid() filter modid}, or either the
     * filter modid or the given modid is {@code null}.
     *
     * @param modid The modid, may be {@code null}
     * @param node  The permission node to add
     */
    public void addNodeIfMatching(@Nullable String modid, ResourceLocation node) {
        if (this.modid == null || modid == null || this.modid.equals(modid)) {
            permissionNodes.add(node);
        }
    }

    /**
     * Return an unmodifiable view of the list of known permission nodes from this event.
     *
     * @return An unmodifiable view of the list of known permission nodes
     */
    public List<ResourceLocation> getNodes() {
        return Collections.unmodifiableList(permissionNodes);
    }
}
