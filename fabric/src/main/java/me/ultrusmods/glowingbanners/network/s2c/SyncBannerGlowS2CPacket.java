package me.ultrusmods.glowingbanners.network.s2c;

import me.ultrusmods.glowingbanners.GlowBannersMod;
import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import me.ultrusmods.glowingbanners.registry.GlowBannersAttachmentTypes;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

public record SyncBannerGlowS2CPacket(BlockPos pos, BannerGlowAttachment attachment) implements FabricPacket {
    public static final PacketType<SyncBannerGlowS2CPacket> TYPE = PacketType.create(GlowBannersMod.asResource("sync_banner_glow"), SyncBannerGlowS2CPacket::read);

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos());
        buf.writeNbt(BannerGlowAttachment.CODEC.encodeStart(NbtOps.INSTANCE, this.attachment).getOrThrow(false, GlowBannersMod.LOGGER::error));
    }

    public static SyncBannerGlowS2CPacket read(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        BannerGlowAttachment attachment = BannerGlowAttachment.CODEC.decode(NbtOps.INSTANCE, buf.readNbt()).getOrThrow(false, GlowBannersMod.LOGGER::error).getFirst();
        return new SyncBannerGlowS2CPacket(pos, attachment);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public void handle() {
        Minecraft.getInstance().execute(() -> {
            BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(this.pos());
            if (blockEntity == null) {
                return;
            }
            blockEntity.getAttachedOrCreate(GlowBannersAttachmentTypes.BANNER_GLOW).setFromOther(this.attachment());
        });
    }
}
