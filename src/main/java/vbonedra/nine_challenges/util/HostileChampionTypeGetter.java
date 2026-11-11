package vbonedra.nine_challenges.util;

import net.minecraft.Entity;

public class HostileChampionTypeGetter {

    public static byte getHostileChampionType(Entity entity) {
        if (entity == null || entity.worldObj == null) {
            return HostileChampion.Vanilla;
        }

        if (!entity.worldObj.isRemote) {
            if (entity instanceof IHostileChampionType) {
                byte type = ((IHostileChampionType) entity).NC$getHostileChampionType();
                return type == HostileChampion.Unset ? HostileChampion.Vanilla : type;
            }
            return HostileChampion.Vanilla;
        }

        Byte cachedType = HostileChampionTypeCache.clientHostileChampionTypeMap.get(entity.entityId);
        return cachedType != null ? cachedType : HostileChampion.Vanilla;
    }

}