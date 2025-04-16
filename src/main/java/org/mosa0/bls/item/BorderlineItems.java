package org.mosa0.bls.item;

import org.mosa0.bls.BorderlineStupid;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BorderlineItems {

    public static final RegistryKey<Item> USELESS_ITEM_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(BorderlineStupid.MOD_ID, "useless_item"));
    public static final Item USELESS_ITEM = register(
            new Item(new Item.Settings().registryKey(USELESS_ITEM_KEY)),
            USELESS_ITEM_KEY
    );

    public static final RegistryKey<Item> RED_PILL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(BorderlineStupid.MOD_ID, "red_pill"));
    public static final Item RED_PILL_ITEM = register(
            new Item(new Item.Settings().registryKey(RED_PILL_KEY)),
            RED_PILL_KEY
    );

    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(BorderlineStupid.MOD_ID, "item_group"));
    public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BorderlineItems.USELESS_ITEM))
            .displayName(Text.translatable("itemGroup.borderline_items"))
            .build();

    public static void initialize() {
        // Register the group.
        Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

// Register items to the custom item group.
        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(BorderlineItems.USELESS_ITEM);
            itemGroup.add(BorderlineItems.RED_PILL_ITEM);

        });
    }

    public static Item register(Item item, RegistryKey<Item> registryKey) {
        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, registryKey.getValue(), item);

        // Return the registered item!
        return registeredItem;
    }
}