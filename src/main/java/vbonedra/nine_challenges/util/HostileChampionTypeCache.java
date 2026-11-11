package vbonedra.nine_challenges.util;

import java.util.HashMap;
import java.util.Map;

public final class HostileChampionTypeCache {
    public static final Map<Integer, Byte> clientHostileChampionTypeMap = new HashMap<>();

    public static void clear() {
        clientHostileChampionTypeMap.clear();
    }

    public static void receiveHostileChampionTypeFromServer(int entityId, byte type) {
        clientHostileChampionTypeMap.put(entityId, type);
    }
}