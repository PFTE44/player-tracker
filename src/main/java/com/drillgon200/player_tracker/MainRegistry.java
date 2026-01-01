package com.drillgon200.player_tracker;

import net.minecraft.entity.player.EntityPlayerMP;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.drillgon200.player_tracker.items.ModItems;
import com.drillgon200.player_tracker.world.TrackablePlayersData;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

@Mod(modid = RefStrings.MODID, name = RefStrings.NAME, version = RefStrings.VERSION)
public class MainRegistry {

    @SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
    public static CommonProxy proxy;

    public static final Logger LOG = LogManager.getLogger(RefStrings.MODID);

    @EventHandler
    public void PreLoad(FMLPreInitializationEvent PreEvent) {
        ModItems.init();
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    // no idea where to put this
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP playerMP) {
            TrackablePlayersData.getInstance()
                .addKnownName(playerMP.getCommandSenderName());
        }
    }
}
