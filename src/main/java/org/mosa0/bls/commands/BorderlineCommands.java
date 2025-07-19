package org.mosa0.bls.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static org.mosa0.bls.events.EventHandler.*;

import net.minecraft.util.math.BlockPos;

import net.minecraft.world.World;
import org.mosa0.bls.events.BorderlinePersistentVars;
import org.mosa0.bls.events.events.BlockSwap;
import org.mosa0.bls.events.events.LiquidSwap;

public class BorderlineCommands implements ModInitializer {
    // Makes a basic running boolean, and allows writing to persistent variables.
    public static boolean isRunning;
    private BorderlinePersistentVars vars;

    private int executeTimerChange(CommandContext<ServerCommandSource> context) {
        // Takes the inputted arguments.
        int minutes = IntegerArgumentType.getInteger(context, "minutes");
        int seconds = IntegerArgumentType.getInteger(context, "seconds");
        boolean newMax = BoolArgumentType.getBool(context, "newMax");

        if (minutes <= 0 && seconds <= 4 && newMax) {
            // To stop events from happening constantly, this sets a buffer of 5 seconds. It's not much though, so I strongly recommend at least a minute.
            vars.timerTicksMax = 100L;

            context.getSource().sendFeedback(() -> Text.literal("Changed max length of timer to be 00:05.%nThis is an intention failsafe."), true);
            return 0;
        } else {
            // Runs the command as intended.
            timerTicks = (minutes * 1200L) + (seconds * 20L);

            if (newMax) {
                // Changes the max values to the inputted values.
                vars.timerTicksMax = timerTicks;
                vars.markDirty();

                context.getSource().sendFeedback(() -> Text.literal("Changed max length of timer to be %02d:%02d.".formatted(minutes, seconds)), true);
                newMax = false;
            } else {
                // This skips changing the max values and just outputs text.
                context.getSource().sendFeedback(() -> Text.literal("Changed length of timer to be %02d:%02d.".formatted(minutes, seconds)), true);
            }
            return 1;
        }
    }

    private int executeRun(CommandContext<ServerCommandSource> context) {
        World world = context.getSource().getWorld();
        if (isRunning) {
            // Basic Failsafe for redundancy.
            context.getSource().sendFeedback(() -> Text.literal("ERROR: Borderline Stupid is already running."), true);
            return 0;
        } else {
            if (!vars.wasSetUp) {

                // Gets world spawn cords.
                BlockPos worldSpawn = world.getLevelProperties().getSpawnPos();
                double worldCenterX = worldSpawn.getX();
                double worldCenterY = worldSpawn.getY();
                double worldCenterZ = worldSpawn.getZ();

                // Teleports everyone to worldSpawn.
                world.getPlayers().forEach(player -> {
                    player.teleport(worldCenterX, worldCenterY, worldCenterZ, false);
                });


                // Sets the worldborder center worldSpawn.
                world.getWorldBorder().setCenter(worldCenterX, worldCenterZ);
                // Sets the worldborder size to 100 x 100.
                world.getWorldBorder().setSize(100);

                // Removes spawn protection.

                // Sets the max value for timer variables to their default values, and marks the setup boolean to true.
                vars.timerTicksMax = 30000L;
                vars.wasSetUp = false;
                vars.markDirty();
                context.getSource().sendFeedback(() -> Text.literal("Setup complete."), true);
            }
            // Sets timer to the max value, and starts the timer method.
            timerTicks = vars.timerTicksMax;
            isRunning = true;

            context.getSource().sendFeedback(() -> Text.literal("Started Borderline Stupid."), true);
            return 1;
        }
    }

    private int executeStop(CommandContext<ServerCommandSource> context){
        if (!isRunning) {
            // Basic Failsafe for redundancy (part 2).
            context.getSource().sendFeedback(() -> Text.literal("ERROR: Borderline Stupid is not running."), true);
            return 0;
        } else {
            // Stops the timer
            isRunning = false;
            context.getSource().sendFeedback(() -> Text.literal("Stopping Borderline Stupid."), true);
            return 1;
        }

    }

    // All eventTest functions are below here.

    private int executeBlockSwap(CommandContext<ServerCommandSource> context) {
        MinecraftServer server = context.getSource().getServer();
        if (server.getOverworld().getWorldBorder().getSize() <= 1000) {
            try {
                // Calls the PickBlock method.
                BlockSwap.PickBlock(server);

                context.getSource().sendFeedback(() -> Text.literal("Block Swap Test."), true);
            } catch (Exception e) {
                context.getSource().sendError(Text.literal("An error occurred while executing Block Swap: " + e.getMessage()));
            }

            // Calls te PickBlock method.
            BlockSwap.PickBlock(server);

            context.getSource().sendFeedback(() -> Text.literal("Swapped some blocks TEMP"), true);
            return 1;
        } else {
            // Failsafe to stop your server from exploding.
            context.getSource().sendFeedback(() -> Text.literal("ERROR: Worldborder size is too large."), true);
            return 0;
        }
    }

    private int executeLiquidSwap(CommandContext<ServerCommandSource> context) {
        MinecraftServer server = context.getSource().getServer();
        if (server.getOverworld().getWorldBorder().getSize() <= 1000) {
            // Calls the LiquidSwapHandler method.
            LiquidSwap.LiquidSwapHandler(server);
            return 1;
        } else {
            // Failsafe to stop your server from exploding.
            context.getSource().sendFeedback(() -> Text.literal("ERROR: Worldborder size is too large."), true);
            return 0;
        }
    }

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            vars = BorderlinePersistentVars.getServerState(server);
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LiteralCommandNode<ServerCommandSource> blsNode = CommandManager
                    .literal("bls") // All main commands are stored under /bls so commands for BLS are not buried by vanilla commands.
                    .build();

            LiteralCommandNode<ServerCommandSource> timerChangeNode = CommandManager
                    .literal("timerChange")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.argument("minutes",IntegerArgumentType.integer(0, 99))
                            .then(CommandManager.argument("seconds", IntegerArgumentType.integer(0,59))
                                    .then(CommandManager.argument("newMax", BoolArgumentType.bool())
                                            .executes(this::executeTimerChange))))
                    .build();

            LiteralCommandNode<ServerCommandSource> runNode = CommandManager
                    .literal("run")
                            .requires(source -> source.hasPermissionLevel(2))
                                    .executes(this::executeRun)
                    .build();

            LiteralCommandNode<ServerCommandSource> stopNode = CommandManager
                    .literal("unrun") // I find unrun funnier than stop.
                            .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                                    .executes(this::executeStop)
                    .build();

            // Its mainly for me to help with debugging, but I am leaving this in so you can test the events if you want.
            // (However I highly suggest you go in blind for maximum chaos). I suspect like 3 people will actually read the warning here...
            LiteralCommandNode<ServerCommandSource> eventTestNode = CommandManager
                    .literal("eventTest")
                            .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(3))
                    .build();

            LiteralCommandNode<ServerCommandSource> blockSwapNode = CommandManager
                    .literal("blockSwap")
                            .executes(this::executeBlockSwap)
                    .build();

            LiteralCommandNode<ServerCommandSource> liquidSwapNode = CommandManager
                    .literal("liquidSwap")
                            .executes(this::executeLiquidSwap)
                    .build();

            // Collecting minors.
            dispatcher.getRoot().addChild(blsNode);

            // bls minors.
            blsNode.addChild(timerChangeNode);
            blsNode.addChild(runNode);
            blsNode.addChild(stopNode);
            blsNode.addChild(eventTestNode);

            // eventTest minors.
            eventTestNode.addChild(blockSwapNode);
            eventTestNode.addChild(liquidSwapNode);

        });
    }
}