package org.mosa0.bls.datagen;

import org.mosa0.bls.loot.BorderlineLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class BorderlineChestLootProvider extends SimpleFabricLootTableProvider {

    public BorderlineChestLootProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.CHEST);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(BorderlineLootTables.BATTLE_ROYALE_CHEST_LOOT, LootTable.builder()
		.pool(LootPool.builder() // One pool
				.rolls(ConstantLootNumberProvider.create(2.0f)) // That has two rolls
				.with(ItemEntry.builder(Items.DIAMOND) // With an entry that has diamond(s)
						.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))) // One diamond
				.with(ItemEntry.builder(Items.DIAMOND_SWORD) // With an entry that has a plain diamond sword
				)
		));
    }
}