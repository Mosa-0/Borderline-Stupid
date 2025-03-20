package com.gmail.pokepuff37.bls.commands;

import com.gmail.pokepuff37.bls.events.Setup;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.gmail.pokepuff37.bls.events.Setup.runSetup;
import static com.gmail.pokepuff37.bls.events.Timer.*;

public class BorderLineStupidCommands implements ModInitializer {
    private static boolean wasSetUp = false;

    private static int executeTimerChange(CommandContext<ServerCommandSource> context) {
        int minutes = IntegerArgumentType.getInteger(context, "minutes");
        int seconds = IntegerArgumentType.getInteger(context, "seconds");
        boolean newMax = BoolArgumentType.getBool(context, "newMax");
        if (minutes < 0 || minutes > 99) {
            context.getSource().sendFeedback(() -> Text.literal("Minutes must be in the range of 0 to 99."), false);
            return 0;
        } else if ((seconds > 59 || seconds < 0)) {
            context.getSource().sendFeedback(() -> Text.literal("Seconds must be in the range of 0 to 59."), false);
            return 0;
        } else {
            timerMinutes = minutes;
            timerSeconds = seconds;
            timerTicks = (minutes * 1200L) + (seconds * 20L);
            if (newMax) {
                timerMinutesMax = timerMinutes;
                timerSecondsMax = timerSeconds;
                timerTicksMax = timerTicks;
                newMax = false;
                if (minutes < 10 && seconds < 10) {
                    context.getSource().sendFeedback(() -> Text.literal("Changed max length of timer to be 0%s:0%s.".formatted(minutes, seconds)), true);
                } else if (minutes < 10) {
                    context.getSource().sendFeedback(() -> Text.literal("Changed max length of timer to be 0%s:%s.".formatted(minutes, seconds)), true);
                } else if (seconds < 10) {
                    context.getSource().sendFeedback(() -> Text.literal("Changed max length of timer to be %s:0%s.".formatted(minutes, seconds)), true);
                } else {
                    context.getSource().sendFeedback(() -> Text.literal("Changed max length of timer to be %s:%s.".formatted(minutes, seconds)), true);
                }
            } else {
                if (minutes < 10 && seconds < 10) {
                    context.getSource().sendFeedback(() -> Text.literal("Changed length of timer to be 0%s:0%s.".formatted(minutes, seconds)), true);
                } else if (minutes < 10) {
                    context.getSource().sendFeedback(() -> Text.literal("Changed length of timer to be 0%s:%s.".formatted(minutes, seconds)), true);
                } else if (seconds < 10) {
                    context.getSource().sendFeedback(() -> Text.literal("Changed length of timer to be %s:0%s.".formatted(minutes, seconds)), true);
                } else {
                    context.getSource().sendFeedback(() -> Text.literal("Changed length of timer to be %s:%s.".formatted(minutes, seconds)), true);
                }

            }
            return 1;
        }
    }
    private static int executeSetup(CommandContext<ServerCommandSource> context){
        if (!wasSetUp) {
            runSetup();
            context.getSource().sendFeedback(() -> Text.literal("Setup complete"), true);
            wasSetUp = true;
            return 1;
        } else {
            context.getSource().sendFeedback(() -> Text.literal("Setup was done already."), false);
            return 0;
        }
    }

    private static int executeRun(CommandContext<ServerCommandSource> context){
        if (wasSetUp) {
            timerMinutes = timerMinutesMax;
            timerSeconds = timerSecondsMax;
            timerTicks = timerTicksMax;
            context.getSource().sendFeedback(() -> Text.literal("Started Borderline Stupid"), true);
            return 1;
        } else {
            return 0;
        }
    }

        @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LiteralCommandNode<ServerCommandSource> blsNode = CommandManager
                    .literal("bls")
                    .build();

            LiteralCommandNode<ServerCommandSource> timerChangeNode = CommandManager
                    .literal("timerChange")
                    .then(CommandManager.argument("minutes",IntegerArgumentType.integer())
                            .then(CommandManager.argument("seconds", IntegerArgumentType.integer())
                                    .then(CommandManager.argument("newMax", BoolArgumentType.bool())
                                            .requires(source -> source.hasPermissionLevel(2))
                                            .executes(BorderLineStupidCommands::executeTimerChange))))
                    .build();

            LiteralCommandNode<ServerCommandSource> setupNode = CommandManager
                    .literal("setup")
                    .requires(source -> source.hasPermissionLevel(2))
                    .executes(BorderLineStupidCommands::executeSetup)
                    .build();
            LiteralCommandNode<ServerCommandSource> runNode = CommandManager
                    .literal("run")
                    .requires(source -> source.hasPermissionLevel(2))
                    .executes(BorderLineStupidCommands::executeRun)
                    .build();

            // Collecting minors
            dispatcher.getRoot().addChild(blsNode);
            blsNode.addChild(timerChangeNode);
            blsNode.addChild(setupNode);
            blsNode.addChild(runNode);
        });
    }
}