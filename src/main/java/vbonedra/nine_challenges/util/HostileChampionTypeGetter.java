package vbonedra.nine_challenges.util;

import net.minecraft.Entity;

public final class HostileChampionTypeGetter {
    public static byte getHostileChampionType(Entity entity) {
        if (entity instanceof IHostileChampionType champion) {
            byte type = champion.NC$getHostileChampionType();
            if (type != HostileChampion.Unset) return type;
        }

        if (entity.worldObj != null && entity.worldObj.isRemote) {
            Byte cached = HostileChampionTypeCache.clientHostileChampionTypeMap.get(entity.entityId);
            if (cached != null) return cached;
        }

        return HostileChampion.Unset;
    }
}