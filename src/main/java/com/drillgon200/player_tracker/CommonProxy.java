package com.drillgon200.player_tracker;

import net.minecraft.world.storage.MapStorage;

import com.drillgon200.player_tracker.commands.TrackingCommand;
import com.drillgon200.player_tracker.world.TrackablePlayersData;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        MainRegistry.LOG.info(Config.trackingDefault);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new TrackingCommand());
        MapStorage pws = event.getServer()
            .getEntityWorld().perWorldStorage;
        TrackablePlayersData.getInstance()
            .load((TrackablePlayersData) pws.loadData(TrackablePlayersData.class, RefStrings.NBT_NAME), pws);
    }
}
