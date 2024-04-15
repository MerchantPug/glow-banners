package me.ultrusmods.glowingbanners.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import me.ultrusmods.glowingbanners.platform.services.IGlowBannersPlatformHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BannerBlockEntity.class)
public class BannerBlockEntityMixin {
    @Inject(method = "load", at = @At("TAIL"))
    private void glowBanners$convertGlowingNbt(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("isGlowing", Tag.TAG_BYTE) && tag.getBoolean("isGlowing")) {
            BannerGlowAttachment data = IGlowBannersPlatformHelper.INSTANCE.getData((BannerBlockEntity)(Object)this);
            data.setAllGlow(true);
            IGlowBannersPlatformHelper.INSTANCE.syncBlockEntity((BannerBlockEntity)(Object)this);
        }
    }

    @Inject(method = "fromItem(Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
    private void glowBanners$setGlowDataFromStack(ItemStack stack, CallbackInfo ci) {
        BannerGlowAttachment prevData = IGlowBannersPlatformHelper.INSTANCE.getData(stack);
        if (prevData != null) {
            BannerGlowAttachment glowData = IGlowBannersPlatformHelper.INSTANCE.getData((BannerBlockEntity)(Object)this);
            if (glowData != null) {
                glowData.setFromOther(prevData);
            }
        }
    }

    @ModifyReturnValue(method = "getItem", at = @At("RETURN"))
    private ItemStack glowBanners$setGlowDataGetStack(ItemStack original) {
        BannerGlowAttachment prevData = IGlowBannersPlatformHelper.INSTANCE.getData((BannerBlockEntity)(Object)this);
        if (prevData != null) {
            BannerGlowAttachment glowData = IGlowBannersPlatformHelper.INSTANCE.getData(original);
            if (glowData != null) {
                glowData.setFromOther(prevData);
            }
        }
        return original;
    }
}
