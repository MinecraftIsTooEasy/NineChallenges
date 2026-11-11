package vbonedra.nine_challenges.util;

import net.minecraft.*;

import java.util.Objects;

public enum HostileChampionType {
    Unset(-1, "", 0),
    Vanilla(0, "", 0),
    Blazing(1, "entity.challenge.hostile_champions.blazing.name", 0xff8800),
    Projecting(2, "entity.challenge.hostile_champions.projecting.name", 0x8800ff),
    Antimagic(3, "entity.challenge.hostile_champions.antimagic.name", 0x00ff00),
    Giant(4, "entity.challenge.hostile_champions.giant.name", 0x0088ff),
    Blessed(5, "entity.challenge.hostile_champions.blessed.name", 0xffff00),
    Growing(6, "entity.challenge.hostile_champions.growing.name", 0xff0000);

    public final byte id;
    public final String langKey;
    public final int color;

    HostileChampionType(int id, String langKey, int color) {
        this.id = (byte) id;
        this.langKey = langKey;
        this.color = color;
    }

    public static boolean isHostileChampionTypeUnset(int hostileChampionType) {
        return hostileChampionType != Unset.id;
    }
    public boolean hasHostileChampionType(int hostileChampionType) {
        return hostileChampionType != Unset.id && hostileChampionType != Vanilla.id;
    }

    public String getPrefix(int hostileChampionType) {
        if (hostileChampionType < 1) return "";
        String pre = StatCollector.translateToLocal("entity.prefix.pre");
        pre = Objects.equals(pre, "") ? "" : pre;
        String post = StatCollector.translateToLocal("entity.prefix.post");
        post = Objects.equals(post, "") ? "" : post;
        return pre + StatCollector.translateToLocal(this.langKey) + post;
    }
}
