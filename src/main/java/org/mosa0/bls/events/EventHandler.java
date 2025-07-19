package org.mosa0.bls.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import org.mosa0.bls.commands.BorderlineCommands;

import java.util.List;

import static org.mosa0.bls.commands.BorderlineCommands.isRunning;
import static org.mosa0.bls.events.events.BlankRoundClass.BlankRound;
import static org.mosa0.bls.events.events.BlockSwap.PickBlock;

public class EventHandler {

    // Variables for counting down. timerSeconds and timerMinutes are kinda redundant.
    public static long timerTicks;
    public static int timerSeconds;
    public static int timerMinutes;

    // Initializes the TimerCount method.
    public static void initialize(MinecraftServer server) {
        TimerCount(server);
    }

    // Handles the counting down on the timer
    public static void TimerCount(MinecraftServer server) {

        // Makes an interface for EventCall.
        interface EventList {
            void EventCall();
        }

        // Makes a list of all events.
        List<EventList> Events = List.of(
                () -> {
                    BlankRound();
                    PickBlock(server);
                }
        );

        // Runs the calculations for the timer.
        ServerTickEvents.START_SERVER_TICK.register(s -> {
            if (isRunning && timerTicks > -1) {
                System.out.printf("%02d:%02d ", timerMinutes, timerSeconds);
                System.out.println(timerTicks);

                // Runs an event at random
                if (timerTicks == 0) {
                    Events.get((int) (Math.random() * 2)).EventCall();
                }

                timerTicks--;
                timerMinutes = (int) timerTicks / 1200;
                timerSeconds = (int) (timerTicks / 20) % 60;
            }
        });
    }
}