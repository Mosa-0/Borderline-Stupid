package org.mosa0.bls;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.mosa0.bls.block.BorderlineBlocks;
import org.mosa0.bls.events.EventHandler;
import org.mosa0.bls.item.BorderlineItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BorderlineStupidInitializer implements ModInitializer {
	public static final String MOD_ID = "bls";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		BorderlineItems.initialize();
		BorderlineBlocks.initialize();
		ServerLifecycleEvents.SERVER_STARTED.register(EventHandler::initialize);
	}
}