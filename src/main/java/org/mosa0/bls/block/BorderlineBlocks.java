package org.mosa0.bls.block;

import org.mosa0.bls.BorderlineStupidInitializer;
import org.mosa0.bls.item.BorderlineItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BorderlineBlocks {
    public static final RegistryKey<Block> BATTLE_ROYALE_DIRT_KEY = RegistryKey.of(
            RegistryKeys.BLOCK,
            Identifier.of(BorderlineStupidInitializer.MOD_ID, "battle_royale_dirt")
    );

    public static final Block BATTLE_ROYALE_DIRT = register(
            new Block(AbstractBlock.Settings.create().registryKey(BATTLE_ROYALE_DIRT_KEY).sounds(BlockSoundGroup.GRAVEL).mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.GUITAR).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning(Blocks::never)),
            BATTLE_ROYALE_DIRT_KEY,
            true
    );
    public static final RegistryKey<Block> BATTLE_ROYALE_GRASS_KEY = RegistryKey.of(
            RegistryKeys.BLOCK,
            Identifier.of(BorderlineStupidInitializer.MOD_ID   , "battle_royale_grass")
    );

    public static final Block BATTLE_ROYALE_GRASS = register(
            new Block(AbstractBlock.Settings.create().registryKey(BATTLE_ROYALE_GRASS_KEY).sounds(BlockSoundGroup.GRASS).mapColor(MapColor.LIME).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning(Blocks::never)),
            BATTLE_ROYALE_GRASS_KEY,
            true
    );

    public static Block register(Block block, RegistryKey<Block> blockKey, boolean shouldRegisterItem) {

//        if (shouldRegisterItem) {
//            // Items need to be registered with a different type of registry key, but the ID
//            // can be the same.
//            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
//
//            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
//            Registry.register(Registries.ITEM, itemKey, blockItem);
//        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    public static void initialize() {
        setupItemGroups();
    }

    public static void setupItemGroups() {
        // :::6
        ItemGroupEvents.modifyEntriesEvent(BorderlineItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
            itemGroup.add(BorderlineBlocks.BATTLE_ROYALE_DIRT.asItem());
            itemGroup.add(BorderlineBlocks.BATTLE_ROYALE_GRASS.asItem());
        });

    }
}