package glowingbanners.registry;

import com.mojang.serialization.MapCodec;
import glowingbanners.loot.GlowBannerLootModifier;
import me.ultrusmods.glowingbanners.registry.RegistrationCallback;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class GlowBannersGlobalLootModifierSerializers {
    public static void registerAll(RegistrationCallback<MapCodec<? extends IGlobalLootModifier>> callback) {
        callback.register(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, GlowBannerLootModifier.ID, GlowBannerLootModifier.CODEC);
    }
}
