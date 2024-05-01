package me.ultrusmods.glowingbanners.network.s2c;

import me.ultrusmods.glowingbanners.GlowBannersMod;
import me.ultrusmods.glowingbanners.component.BannerGlowComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

public record SyncBannerGlowS2CPacket(BlockPos pos, BannerGlowComponent attachment) implements CustomPacketPayload {
    public static final Type<SyncBannerGlowS2CPacket> TYPE = new Type<>(GlowBannersMod.asResource("sync_banner_glow"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncBannerGlowS2CPacket> STREAM_CODEC = StreamCodec.of(SyncBannerGlowS2CPacket::write, SyncBannerGlowS2CPacket::new);

    public SyncBannerGlowS2CPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), BannerGlowComponent.CODEC.decode(NbtOps.INSTANCE, buf.readNbt()).getOrThrow().getFirst());
    }

    public static void write(FriendlyByteBuf buf, SyncBannerGlowS2CPacket packet) {
        buf.writeBlockPos(packet.pos);
        buf.writeNbt(BannerGlowComponent.CODEC.encodeStart(NbtOps.INSTANCE, packet.attachment).getOrThrow());
    }

    public void handle() {
        Minecraft.getInstance().execute(() -> {
            BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(this.pos());
            if (!(blockEntity instanceof BannerBlockEntity banner)) {
                return;
            }
            GlowBannersMod.getHelper().setData(banner, attachment);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
