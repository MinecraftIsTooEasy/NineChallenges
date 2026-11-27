package vbonedra.nine_challenges.challenge;

import net.minecraft.I18n;
import net.minecraft.ResourceLocation;
import vbonedra.nine_challenges.gui.DescriptionPainter;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

public enum Challenge {
    BADDER_BOSSES(0, "badder_bosses") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.badder_bosses.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.badder_bosses.description.detailed"));
        }
    },
    HOSTILE_CHAMPIONS(1, "hostile_champions") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.hostile_champions.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.hostile_champions.description.detailed"));
        }
    },
    ON_DIET(2, "on_diet") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.on_diet.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.on_diet.description.detailed"));
        }
    },
    FAITH_IS_MY_ARMOR(3, "faith_is_my_armor") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.faith_is_my_armor.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.faith_is_my_armor.description.detailed"));
        }
    },
    PHARMACOPHOBIA(4, "pharmacophobia") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.pharmacophobia.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.pharmacophobia.description.detailed"));
        }
    },
    BARREN_LAND(5, "barren_land") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.barren_land.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.barren_land.description.detailed"));
        }
    },
    SWARM_INTELLIGENCE(6, "swarm_intelligence") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.swarm_intelligence.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.swarm_intelligence.description.detailed"));
        }
    },
    INTO_DARKNESS(7, "into_darkness") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.into_darkness.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.into_darkness.description.detailed"));
        }
    },
    FORBIDDEN_RUNES(8, "forbidden_runes") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.forbidden_runes.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.forbidden_runes.description.detailed"));
        }
    };

    public final String name;
    public final int bit;
    public final ResourceLocation icon;

    Challenge(int bit, String name) {
        this.bit = bit;
        this.name = name;
        this.icon = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/icons/challenge/" + name + ".png");
    }

    public int challengeMask() {
        return 1 << bit;
    }

    public boolean isIn(int challengeMask) {
        return (challengeMask & challengeMask()) != 0;
    }

    static public int countChallenges(int challengeMask) {
        return Integer.bitCount(challengeMask);
    }

    public String getTitle() {
        return I18n.getString("challenge."+this.name+".title");
    }

    // draws description, could be text or textures or anything render could handle
    public abstract void renderDescription(DescriptionPainter descriptionPainter);

}