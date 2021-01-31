package dev.socketmods.socketperms;

import dev.socketmods.socketperms.api.IPermissionHandler;
import dev.socketmods.socketperms.api.PermissionAPI;
import dev.socketmods.socketperms.api.PermissionCollectionEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SocketPerms.MODID)
public class SocketPerms {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "socketperms";

    public SocketPerms() {
        new SocketPermissionHandler();
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        MinecraftForge.EVENT_BUS.addListener(PermissionCommand::onRegisterCommands);
        MinecraftForge.EVENT_BUS.addListener(this::onPermissionCollection);
    }

    void onServerStarting(FMLServerStartingEvent event) {
        PermissionAPI.setHandler(new SocketPermissionHandler());
        // TODO: define when the permission handler should be set
    }

    void onServerStarted(FMLServerStartedEvent event) {
        final IPermissionHandler handler = PermissionAPI.getHandler();
        if (handler instanceof SocketPermissionHandler) {
            ((SocketPermissionHandler) handler).getKnownNodes();
            // Collect all the known permission nodes
        }
    }

    void onPermissionCollection(PermissionCollectionEvent event) {
        event.addNode(new ResourceLocation("socketperms:command"));
        event.addNode(new ResourceLocation("socketperms:interact.entity"));
        event.addNode(new ResourceLocation("socketperms:interact.block"));
    }
}
