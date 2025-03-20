package com.gmail.pokepuff37.bls.events;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.gmail.pokepuff37.bls.BorderlineStupid.MOD_ID;

public class PersistentVariables {


    public record DirtBrokenPayload(Integer totalDirtBlocksBroken) implements CustomPayload {
        public static final Identifier DIRT_BROKEN_ID = Identifier.of(MOD_ID, "dirt_broken");
        public static final CustomPayload.Id<DirtBrokenPayload> ID = new CustomPayload.Id<>(DIRT_BROKEN_ID);
        public static final PacketCodec<PacketByteBuf, DirtBrokenPayload> CODEC = PacketCodec.tuple(
                PacketCodecs.INTEGER, DirtBrokenPayload::totalDirtBlocksBroken,
                DirtBrokenPayload::new);

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
}
