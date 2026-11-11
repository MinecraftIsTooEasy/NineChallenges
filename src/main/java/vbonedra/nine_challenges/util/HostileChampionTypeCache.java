package vbonedra.nine_challenges.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HostileChampionTypeCache {
    public static final Map<Integer, Byte> clientHostileChampionTypeMap = new HashMap<>();
    public static final Set<Integer> requestedEntities = new HashSet<>();

    public static void receiveHostileChampionTypeFromServer(int entityId, byte type) {
        clientHostileChampionTypeMap.put(entityId, type);
    }
}
