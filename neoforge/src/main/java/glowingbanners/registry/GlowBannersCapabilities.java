package glowingbanners.registry;

import me.ultrusmods.glowingbanners.GlowBannersMod;
import me.ultrusmods.glowingbanners.attachment.capi.block.BannerGlowBlockCapi;
import me.ultrusmods.glowingbanners.attachment.capi.BannerGlowItemCapi;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class GlowBannersCapabilities {
    public static final BlockCapability<BannerGlowBlockCapi, Void> BANNER_GLOW_BLOCK = BlockCapability.createVoid(
            GlowBannersMod.asResource("banner_glow"),
            BannerGlowBlockCapi.class
    );
    public static final ItemCapability<BannerGlowItemCapi, Void> BANNER_GLOW_ITEM = ItemCapability.createVoid(
            GlowBannersMod.asResource("banner_glow"),
            BannerGlowItemCapi.class
    );
}
