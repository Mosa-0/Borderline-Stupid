package com.gmail.pokepuff37.bls.events;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import static com.gmail.pokepuff37.bls.events.PersistentVariables.*;

public class Setup {
    public static void runSetup() {
        timerTicksMax = 30000L;
        timerMinutesMax = 25;
        timerSecondsMax = 0;
    }

}
