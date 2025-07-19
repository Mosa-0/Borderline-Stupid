package org.mosa0.bls.loot;

import org.mosa0.bls.BorderlineStupidInitializer;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class BorderlineLootTables {
    public static RegistryKey<LootTable> BATTLE_ROYALE_CHEST_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(BorderlineStupidInitializer.MOD_ID, "chests/battle_royale_loot"));
}
