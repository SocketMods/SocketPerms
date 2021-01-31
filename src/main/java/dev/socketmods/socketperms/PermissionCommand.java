package dev.socketmods.socketperms;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.socketmods.socketperms.api.IPermissionHandler;
import dev.socketmods.socketperms.api.IPermissionValue;
import dev.socketmods.socketperms.api.PermissionAPI;
import dev.socketmods.socketperms.api.context.PermissionContext;
import dev.socketmods.socketperms.api.context.StandardContextKeys;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class PermissionCommand {
    static void onRegisterCommands(RegisterCommandsEvent event) {
        PermissionCommand.register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(literal("socketperms")
            .then(argument("permission", ResourceLocationArgument.resourceLocation())
                .suggests(PermissionCommand::suggestPermissions)
                .executes(PermissionCommand::showPermission)
            )
        );
    }

    static CompletableFuture<Suggestions> suggestPermissions(CommandContext<CommandSource> ctx, SuggestionsBuilder builder) {
        final IPermissionHandler handler = PermissionAPI.getHandler();
        if (handler instanceof SocketPermissionHandler) {
            ((SocketPermissionHandler) handler).getKnownNodes()
                .stream()
                .distinct()
                .map(ResourceLocation::toString)
                .forEach(builder::suggest);
        }
        return builder.buildFuture();
    }

    static int showPermission(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        final CommandSource source = ctx.getSource();
        final ResourceLocation node = ResourceLocationArgument.getResourceLocation(ctx, "permission");

        final PermissionContext permContext = PermissionContext.builder()
            .add(StandardContextKeys.WORLD, source.getWorld())
            .addNullable(StandardContextKeys.THIS_ENTITY, source.getEntity())
            .add(StandardContextKeys.THIS_POSITION, source.getPos())
            .build();

        final IPermissionValue value = PermissionAPI
            .getPermissionValue(node, ctx.getSource().asPlayer().getGameProfile(), permContext);

        source.sendFeedback(new TranslationTextComponent("Value for permission node %s: %s", node, value), false);

        return 1;
    }
}
