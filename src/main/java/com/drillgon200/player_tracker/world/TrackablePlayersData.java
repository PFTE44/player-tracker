package com.drillgon200.player_tracker.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

import com.drillgon200.player_tracker.Config;
import com.drillgon200.player_tracker.MainRegistry;
import com.drillgon200.player_tracker.RefStrings;

public class TrackablePlayersData extends WorldSavedData {

    private NBTTagCompound data = new NBTTagCompound();
    private static TrackablePlayersData INSTANCE = new TrackablePlayersData(RefStrings.NBT_NAME);

    public static final List<String> EMPTY = Arrays.asList("");

    private static List<String> trackableNames;
    private static List<String> knownNames;

    // so basically you NEED a string constructor
    // but the string is just an arbitrary identifier (in case you attach multiple of these datas to a world)
    public TrackablePlayersData(String a) {
        super(a);
        // super(RefStrings.NBT_NAME);
    }

    public static TrackablePlayersData getInstance() {
        return INSTANCE;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        data = compound.getCompoundTag(RefStrings.NBT_NAME);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setTag(RefStrings.NBT_NAME, data);
    }

    public NBTTagCompound getDataReal() {
        return data;
    }

    public String[] getData() {
        // probably better delimiter
        // readFromNBT();
        return data.getString(RefStrings.NBT_SHOWN_NAME)
            .split(",");
    }

    public String[] getOtherData() {
        return data.getString(RefStrings.NBT_KNOWN_NAME)
            .split(",");
    }

    public void load(TrackablePlayersData ndata, MapStorage pws) {
        if (ndata == null) {
            MainRegistry.LOG.info("Tracking Data not found in world, creating it!");
            pws.setData(RefStrings.NBT_NAME, INSTANCE);
            INSTANCE.getDataReal()
                .setString(RefStrings.NBT_SHOWN_NAME, "");
            INSTANCE.getDataReal()
                .setString(RefStrings.NBT_KNOWN_NAME, "");
            INSTANCE.markDirty();
            trackableNames = new ArrayList<String>();
            knownNames = new ArrayList<String>();
        } else {
            MainRegistry.LOG.info("Reading player tracker data.");
            INSTANCE = ndata;
            trackableNames = new ArrayList<String>(Arrays.asList(ndata.getData()));
            trackableNames.removeAll(EMPTY);
            knownNames = new ArrayList<String>(Arrays.asList(ndata.getOtherData()));
            knownNames.removeAll(EMPTY);
        }
    }

    public List<String> getTrackablePlayerNames() {
        return trackableNames;
    }

    public List<String> getKnownNames() {
        return knownNames;
    }

    public void addTrackableName(String name) {
        addTrackableName(name, true);
    }

    public void addTrackableName(String name, boolean doCheck) {
        if (doCheck && trackableNames.contains(name)) return;
        // readFromNBT();
        trackableNames.add(name);
        data.setString(RefStrings.NBT_SHOWN_NAME, data.getString(RefStrings.NBT_SHOWN_NAME) + "," + name + ",");
        markDirty();
    }

    public void removeTrackableName(String name) {
        if (!trackableNames.contains(name)) return;
        // readFromNBT();
        trackableNames.remove(name);
        String[] s = data.getString(RefStrings.NBT_SHOWN_NAME)
            .split("," + name + ",");
        data.setString(RefStrings.NBT_SHOWN_NAME, s.length == 0 ? "" : s[0] + s[1]);
        markDirty();
    }

    public void addKnownName(String name) {
        if (knownNames.contains(name)) return;
        if (Config.trackingDefault) addTrackableName(name, false);
        knownNames.add(name);
        data.setString(RefStrings.NBT_KNOWN_NAME, data.getString(RefStrings.NBT_KNOWN_NAME) + "," + name + ",");
        markDirty();
    }
}
