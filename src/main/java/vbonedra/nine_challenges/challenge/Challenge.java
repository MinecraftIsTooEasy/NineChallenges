package vbonedra.nine_challenges.challenge;

import net.minecraft.I18n;
import net.minecraft.ResourceLocation;
import vbonedra.nine_challenges.gui.DescriptionPainter;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

public enum Challenge {
    BADDER_BOSSES("badder_bosses") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.badder_bosses.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.badder_bosses.description.detailed"));
        }
    },
    HOSTILE_CHAMPIONS("hostile_champions") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.hostile_champions.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.hostile_champions.description.detailed"));
        }
    },
    ON_DIET("on_diet") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.on_diet.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.on_diet.description.detailed"));
        }
    },
    FAITH_IS_MY_ARMOR("faith_is_my_armor") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.faith_is_my_armor.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.faith_is_my_armor.description.detailed"));
        }
    },
    PHARMACOPHOBIA("pharmacophobia") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.pharmacophobia.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.pharmacophobia.description.detailed"));
        }
    },
    BARREN_LAND("barren_land") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.barren_land.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.barren_land.description.detailed"));
        }
    },
    SWARM_INTELLIGENCE("swarm_intelligence") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.swarm_intelligence.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.swarm_intelligence.description.detailed"));
        }
    },
    INTO_DARKNESS("into_darkness") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.into_darkness.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.into_darkness.description.detailed"));
        }
    },
    FORBIDDEN_RUNES("forbidden_runes") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.processAndDrawText(I18n.getString("challenge.forbidden_runes.description.short"));
            descriptionPainter.processAndDrawText(I18n.getString("challenge.forbidden_runes.description.detailed"));
        }
    };

    public final String name;
    public final ResourceLocation icon;

    Challenge(String name) {
        this.name = name;
        this.icon = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/icons/challenge/" + name + ".png");
    }

    public String getTitle() {
        return I18n.getString("challenge."+this.name+".title");
    }

    // draws description, could be text or textures or anything render could handle
    public abstract void renderDescription(DescriptionPainter descriptionPainter);

    public int challengeMask() {
        return 1 << ordinal();
    }

    public boolean isIn(int challengeMask) {
        return (challengeMask & challengeMask()) != 0;
    }

    static public int countChallenges(int challengeMask) {
        return Integer.bitCount(challengeMask);
    }
}