package com.drillgon200.player_tracker;

public class RefStrings {

    public static final String MODID = "player_tracker";
    public static final String NAME = "Player Tracker Compass";
    public static final String VERSION = "1.0";

    public static final String CLIENTSIDE = "com.drillgon200.player_tracker.ClientProxy";
    public static final String SERVERSIDE = "com.drillgon200.player_tracker.ServerProxy";

    public static final String NBT_NAME = "trackable_players";// NO TOUCHING!
    public static final String NBT_SHOWN_NAME = "shown_players";
    public static final String NBT_KNOWN_NAME = "known_players";

    public static final String COMMAND_NAME = "set_tracking";
    public static final String COMMAND_USAGE = "Enable or disable tracking of a player.\n/" + COMMAND_NAME
        + " [player] [true|false]";
}
