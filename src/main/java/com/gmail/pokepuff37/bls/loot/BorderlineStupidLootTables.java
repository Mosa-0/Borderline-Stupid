package com.gmail.pokepuff37.bls.loot;

import com.gmail.pokepuff37.bls.BorderlineStupid;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class BorderlineStupidLootTables {
    public static RegistryKey<LootTable> FORTNITE_CHEST_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(BorderlineStupid.MOD_ID, "chests/fortnite_loot"));
}
