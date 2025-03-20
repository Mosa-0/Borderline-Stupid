package com.gmail.pokepuff37.bls.commands;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

public class BorderlineStupidClientCommands implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("eventInfo").executes(context -> {
                context.getSource().sendFeedback(Text.literal("Called /eventInfo with no arguments."));
                return 1;
            }));
        });
        // :::1
    }
}