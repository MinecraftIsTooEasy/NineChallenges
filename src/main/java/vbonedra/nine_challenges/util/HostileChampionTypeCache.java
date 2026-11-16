package vbonedra.nine_challenges.util;

import net.minecraft.Minecraft;
import java.util.HashMap;
import java.util.Map;

public final class HostileChampionTypeCache {
    public static final Map<Integer, Byte> clientHostileChampionTypeMap = new HashMap<>();

    public static void clear() { clientHostileChampionTypeMap.clear(); }

    public static void receiveHostileChampionTypeFromServer(int entityId, byte type) {
        clientHostileChampionTypeMap.put(entityId, type);

        Minecraft mc = Minecraft.getMinecraft();

        if (mc == null || mc.theWorld == null || !mc.theWorld.isRemote) return;

        if (mc.theWorld.getEntityByID(entityId) instanceof IHostileChampionType iHostileChampionType) {
            iHostileChampionType.NC$setHostileChampionType(type);
        }
    }
}