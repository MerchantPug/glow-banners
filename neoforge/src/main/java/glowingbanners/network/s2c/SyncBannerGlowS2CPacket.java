package glowingbanners.network.s2c;

import glowingbanners.GlowBannersModNeoForge;
import glowingbanners.registry.GlowBannersAttachmentTypes;
import me.ultrusmods.glowingbanners.GlowBannersMod;
import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPlayPayloadHandler;
import net.neoforged.neoforge.network.registration.IDirectionAwarePayloadHandlerBuilder;

public record SyncBannerGlowS2CPacket(BlockPos pos, BannerGlowAttachment attachment) implements CustomPacketPayload {
    public static final ResourceLocation ID = GlowBannersMod.asResource("sync_banner_glow");

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos());
        buf.writeNbt(BannerGlowAttachment.CODEC.encodeStart(NbtOps.INSTANCE, this.attachment()).getOrThrow(false, GlowBannersMod.LOGGER::error));
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static SyncBannerGlowS2CPacket read(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        BannerGlowAttachment attachment = BannerGlowAttachment.CODEC.decode(NbtOps.INSTANCE, buf.readNbt()).getOrThrow(false, GlowBannersMod.LOGGER::error).getFirst();
        return new SyncBannerGlowS2CPacket(pos, attachment);
    }

    public static void handle(IDirectionAwarePayloadHandlerBuilder<SyncBannerGlowS2CPacket, IPlayPayloadHandler<SyncBannerGlowS2CPacket>> builder) {
        builder.client(
                (packet, context) -> {
                    Minecraft.getInstance().execute(() -> {
                        BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(packet.pos());
                        if (blockEntity == null) {
                            return;
                        }
                        blockEntity.getData(GlowBannersAttachmentTypes.BANNER_GLOW).getAttachment().setFromOther(packet.attachment());
                    });
                }
        );
    }
}
