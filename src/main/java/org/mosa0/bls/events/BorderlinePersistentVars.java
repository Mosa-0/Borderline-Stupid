package org.mosa0.bls.events;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import org.mosa0.bls.BorderlineStupidInitializer;

public class BorderlinePersistentVars extends PersistentState {
    // Initiates class type variables.
    public Boolean wasSetUp;
    public Long timerTicksMax;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putBoolean("wasSetUp", wasSetUp);
        nbt.putLong("timerTicksMax", timerTicksMax);
        return nbt;
    }

    public static BorderlinePersistentVars createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        BorderlinePersistentVars state = new BorderlinePersistentVars();
        state.wasSetUp = tag.getBoolean("wasSetUp");
        state.timerTicksMax = tag.getLong("timerTicksMax");
        return state;
    }

    public static BorderlinePersistentVars createNew() {
        BorderlinePersistentVars state = new BorderlinePersistentVars();
        state.wasSetUp = false;
        state.timerTicksMax = 0L;
        return state;
    }

    private static final Type<BorderlinePersistentVars> type = new Type<>(
            BorderlinePersistentVars::createNew,
            BorderlinePersistentVars::createFromNbt,
            null
    );

    public static BorderlinePersistentVars getServerState(MinecraftServer server) {
        ServerWorld serverWorld = server.getOverworld();
        assert serverWorld != null;

        return serverWorld.getPersistentStateManager().getOrCreate(type, BorderlineStupidInitializer.MOD_ID);
    }

}