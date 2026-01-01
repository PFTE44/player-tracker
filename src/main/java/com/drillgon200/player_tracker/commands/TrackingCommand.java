package com.drillgon200.player_tracker.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import com.drillgon200.player_tracker.Config;
import com.drillgon200.player_tracker.RefStrings;
import com.drillgon200.player_tracker.world.TrackablePlayersData;

public class TrackingCommand extends CommandBase {

    private static final List<String> TRUE_OR_FALSE = new ArrayList<String>(
        Arrays.asList(new String[] { "true", "false" }));

    @Override
    public String getCommandName() {
        return RefStrings.COMMAND_NAME;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return RefStrings.COMMAND_USAGE;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (canCommandSenderUseCommand(sender)) {
            if (args.length == 1) {
                if (TrackablePlayersData.getInstance()
                    .getTrackablePlayerNames()
                    .contains(args[0])) {
                    IChatComponent component = new ChatComponentText("That player is trackable!");
                    sender.addChatMessage(component);
                } else {
                    if (TrackablePlayersData.getInstance()
                        .getKnownNames()
                        .contains(args[0])) {
                        IChatComponent component = new ChatComponentText("That player is not trackable!");
                        sender.addChatMessage(component);
                    } else {
                        String a = Config.trackingDefault ? "" : "not ";
                        IChatComponent component = new ChatComponentText("That player is " + a + "trackable!");
                        sender.addChatMessage(component);
                    }
                }
            } else if (args.length == 2) {
                if (args[1].toLowerCase()
                    .equals("true")) {
                    TrackablePlayersData.getInstance()
                        .addTrackableName(args[0]);
                } else if (args[1].toLowerCase()
                    .equals("false")) {
                        TrackablePlayersData.getInstance()
                            .removeTrackableName(args[0]);
                    } else {
                        // invalid
                        IChatComponent component = new ChatComponentTranslation("commands.tracking.invalid_option");
                        component.getChatStyle()
                            .setColor(EnumChatFormatting.RED);
                        sender.addChatMessage(component);
                        return;
                    }
                TrackablePlayersData.getInstance()
                    .addKnownName(args[0]);
                IChatComponent component = new ChatComponentText(
                    "Made " + args[0] + (Boolean.valueOf(args[1]) ? "" : "un") + "trackable.");
                sender.addChatMessage(component);
            } else {
                IChatComponent component = new ChatComponentText(getCommandUsage(sender));
                sender.addChatMessage(component);
            }
        }
    }

    // permission level is 4 by default

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            return Collections.emptyList();
        } else if (isUsernameIndex(args, args.length - 1)) {
            return getListOfStringsMatchingLastWord(
                args,
                MinecraftServer.getServer()
                    .getAllUsernames());
        } else if (args.length - 1 == 1) {
            return TRUE_OR_FALSE;
        }

        return super.addTabCompletionOptions(sender, args);
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return index == 0;
    }

    // getPlayer
}
