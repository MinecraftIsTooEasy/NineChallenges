package vbonedra.nine_challenges.util.hostile_champion;

import net.minecraft.StatCollector;

import java.util.*;

public class HostileChampion {
    public static final Map<Byte, String> langKeyMap = new HashMap<>();
    public static final Map<Byte, Integer> colorMap = new HashMap<>();

    private static byte initHostileChampionType(int id, String langKey, int color) {
        return initHostileChampionType((byte) id, langKey, color);
    }
    private static byte initHostileChampionType(byte id, String langKey, int color) {
        langKeyMap.put(id, langKey);
        colorMap.put(id, color);
        return id;
    }

    public static final float spawnChance = 0.1F;

    public static final byte Unset = initHostileChampionType(-1, "", 0);
    public static final byte Vanilla = initHostileChampionType(0, "", 0);

    public static final byte Blazing = initHostileChampionType(1, "entity.challenge.hostile_champions.blazing.name", 0xff8800);
    public static final float championBlazingDamageMultiplier = 1.25F;
    public static final int championBlazingFireAspectSeconds = 4;
    public static final float championBlazingExplosionSize = 2.0F;
    public static final float championBlazingExplosionFireChance = 0.6847F * 2F / 3F;

    public static final byte Projecting = initHostileChampionType(2, "entity.challenge.hostile_champions.projecting.name", 0x8800ff);
    public static final float championProjectingReachMultiplier = 1.5F;
    public static final float championProjectingDamageMultiplierMelee = 1.25F;
    public static final float championProjectingDamageMultiplierRanged = 1.25F;

    public static final byte Antimagic = initHostileChampionType(3, "entity.challenge.hostile_champions.antimagic.name", 0x00ff00);
    public static final float championAntimagicHealthMultiplier = 1.5F;

    public static final byte Giant = initHostileChampionType(4, "entity.challenge.hostile_champions.giant.name", 0x0088ff);
    public static final float championGiantScale = 1.9F;
    public static final float championGiantReachMultiplier = 1.25F;
    public static final float championGiantHealthMultiplier = 5F;
    public static final float championGiantExplosionMultiplier = 2.0F;
    public static final int championGiantAdditionalDropAmount = 3;

    public static final byte Blessed = initHostileChampionType(5, "entity.challenge.hostile_champions.blessed.name", 0xffff00);
    public static final float championBlessedRegenerationMultiplier = 4F;

    public static final byte Growing = initHostileChampionType(6, "entity.challenge.hostile_champions.growing.name", 0xff0000);
    public static final int championGrowingMinGrowTicks = 20 * 20;
    public static final int championGrowingMaxGrowTicks = championGrowingMinGrowTicks + 5 * 60 * 20;
    public static final float championGrowingAdditionalDamagePerTick = 0.01F / 20;
    public static final float championGrowingAdditionalEffectiveHealthPerTick = 0.01F / 20;


    public static Set<Byte> getAllChampionTypes() {
        return new LinkedHashSet<>(Set.of(Blazing, Projecting, Antimagic, Giant, Blessed, Growing));
    }

    public static boolean hasAnyHostileChampionType(int hostileChampionType) {
        return hostileChampionType != Unset && hostileChampionType != Vanilla;
    }

    public static String getPrefix(byte hostileChampionType) {
        System.out.println(StatCollector.translateToLocal(langKeyMap.get(hostileChampionType)) + " ");
        if (hasAnyHostileChampionType(hostileChampionType)) return "";
        return StatCollector.translateToLocal(langKeyMap.get(hostileChampionType)) + " ";
    }

}
