
package dev.chatchunk;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.network.chat.Component;

import java.util.Optional;
import java.util.regex.Pattern;

public class ChatChunkReplacer implements ModInitializer {

    private static final Pattern BLOCK_ID =
        Pattern.compile("[a-z0-9_.-]+(?::[a-z0-9_./-]+)?");

    @Override
    public void onInitialize() {

        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register(
            (message, sender, boundChatType) -> {

                String input = message.decoratedContent()
                    .getString().trim();

                if (!BLOCK_ID.matcher(input).matches()) {
                    return true;
                }

                ResourceLocation id;

                try {
                    id = ResourceLocation.parse(
                        input.contains(":")
                            ? input
                            : "minecraft:" + input
                    );
                } catch (IllegalArgumentException e) {
                    return true;
                }

                Optional<Block> block =
                    BuiltInRegistries.BLOCK.getOptional(id);

                if (block.isEmpty()) {
                    return true;
                }

                if (!sender.createCommandSourceStack()
                    .hasPermission(2)) {
                    sender.sendSystemMessage(Component.literal(
                        "You need operator permission!"
                    ));
                    return false;
                }

                ServerLevel level = sender.serverLevel();
                ChunkPos chunk =
                    new ChunkPos(sender.blockPosition());

                BlockState replacement =
                    block.get().defaultBlockState();

                sender.sendSystemMessage(Component.literal(
                    "Replacing chunk with " + id + "..."
                ));

                level.getServer().execute(() -> {

                    for (int x = chunk.getMinBlockX();
                         x < chunk.getMaxBlockX(); x++) {

                        for (int z = chunk.getMinBlockZ();
                             z < chunk.getMaxBlockZ(); z++) {

                            for (int y = level.getMinY();
                                 y < level.getMaxY(); y++) {

                                level.setBlock(
                                    new BlockPos(x, y, z),
                                    replacement,
                                    3
                                );
                            }
                        }
                    }

                    sender.sendSystemMessage(Component.literal(
                        "Chunk replacement complete!"
                    ));
                });

                return false;
            }
        );
    }
}
