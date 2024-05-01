package me.ultrusmods.glowingbanners.mixin;

import me.ultrusmods.glowingbanners.GlowBannersMod;
import me.ultrusmods.glowingbanners.component.BannerGlowComponent;
import me.ultrusmods.glowingbanners.registry.GlowBannersDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BannerBlockEntity.class)
public class BannerBlockEntityMixin extends BlockEntity {
    public BannerBlockEntityMixin(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void glowBanners$convertGlowingNbt(CompoundTag tag, HolderLookup.Provider provider, CallbackInfo ci) {
        if (tag.contains("isGlowing", Tag.TAG_BYTE) && tag.getBoolean("isGlowing")) {
            tag.remove("isGlowing");
            GlowBannersMod.getHelper().setData((BannerBlockEntity)(Object)this, new BannerGlowComponent(true, List.of()));
        }
    }

    @Inject(method = "applyImplicitComponents", at = @At("TAIL"))
    private void glowBanners$applyGlowComponent(DataComponentInput input, CallbackInfo ci) {
        BannerGlowComponent component = input.get(GlowBannersDataComponents.BANNER_GLOW);
        if (component == null) {
            GlowBannersMod.getHelper().removeData((BannerBlockEntity)(Object)this);
            return;
        }
        GlowBannersMod.getHelper().setData((BannerBlockEntity)(Object)this, component);
        if (level == null || level.isClientSide())
            return;
        GlowBannersMod.getHelper().syncBlockEntity((BannerBlockEntity)(Object)this);
    }

    @Inject(method = "collectImplicitComponents", at = @At("TAIL"))
    private void glowBanners$applyGlowComponent(DataComponentMap.Builder builder, CallbackInfo ci) {
        BannerGlowComponent attachment = GlowBannersMod.getHelper().getData((BannerBlockEntity)(Object)this);
        if (attachment == null)
            return;
        builder.set(GlowBannersDataComponents.BANNER_GLOW, attachment);
    }
}
