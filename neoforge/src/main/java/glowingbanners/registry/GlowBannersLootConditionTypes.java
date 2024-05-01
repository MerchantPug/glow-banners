package glowingbanners.registry;

import glowingbanners.loot.IsBannerBlockLootCondition;
import me.ultrusmods.glowingbanners.registry.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class GlowBannersLootConditionTypes {
    public static void registerAll(RegistrationCallback<LootItemConditionType> callback) {
        callback.register(BuiltInRegistries.LOOT_CONDITION_TYPE, IsBannerBlockLootCondition.ID, new LootItemConditionType(IsBannerBlockLootCondition.CODEC));
    }
}
