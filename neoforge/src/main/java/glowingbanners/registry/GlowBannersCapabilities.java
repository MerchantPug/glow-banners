package glowingbanners.registry;

import me.ultrusmods.glowingbanners.GlowBannersMod;
import me.ultrusmods.glowingbanners.attachment.capi.BannerGlowItemCapi;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class GlowBannersCapabilities {
    public static final ItemCapability<BannerGlowItemCapi, Void> BANNER_GLOW_ITEM = ItemCapability.createVoid(
            GlowBannersMod.asResource("banner_glow"),
            BannerGlowItemCapi.class
    );
}
