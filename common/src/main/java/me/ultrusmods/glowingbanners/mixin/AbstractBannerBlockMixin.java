package me.ultrusmods.glowingbanners.mixin;

import me.ultrusmods.glowingbanners.attachment.BannerGlowAttachment;
import me.ultrusmods.glowingbanners.platform.services.IGlowBannersPlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBannerBlock.class)
public class AbstractBannerBlockMixin {
    @Inject(method = "setPlacedBy", at = @At("RETURN"))
    private void glowBanners$setPlacedGlowData(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack, CallbackInfo ci) {
        if (level.isClientSide()) return;
        level.getBlockEntity(pos, BlockEntityType.BANNER).ifPresent(blockEntity -> {
            BannerGlowAttachment prevData = IGlowBannersPlatformHelper.INSTANCE.getData(stack);
            if (prevData != null) {
                BannerGlowAttachment glowData = IGlowBannersPlatformHelper.INSTANCE.getData(blockEntity);
                if (glowData != null) {
                    glowData.setFromOther(prevData);

                    if (stack.getOrCreateTagElement("BlockEntityTag").contains("isGlowing") && stack.getOrCreateTagElement("BlockEntityTag").getBoolean("isGlowing")) {
                        glowData.setAllGlow(true);
                    }
                }
            }
        });
    }
}
