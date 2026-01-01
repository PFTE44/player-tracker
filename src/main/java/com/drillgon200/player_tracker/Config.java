package com.drillgon200.player_tracker;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean trackingDefault = false;

    @SuppressWarnings("unused")
    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        trackingDefault = configuration.getBoolean(
            "trackingDefault",
            Configuration.CATEGORY_GENERAL,
            trackingDefault,
            "By default, should a player be trackable or not?");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
