package vbonedra.nine_challenges.challenge;

import net.minecraft.ResourceLocation;
import vbonedra.nine_challenges.gui.DescriptionPainter;

import static vbonedra.nine_challenges.NineChallengesMod.NINE_CHALLENGES_MOD_ID;

public enum Challenge { // TODO: add .lang
    BADDER_BOSSES("badder_bosses") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.text("TODO: Badder Bosses description");
        }
    },
    HOSTILE_CHAMPIONS("hostile_champions") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.text("TODO: Hostile Champions description");
        }
    },
    ON_DIET("on_diet") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.text("TODO: On Diet description");
        }
    },
    FAITH_IS_MY_ARMOR("faith_in_my_armor") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.text("TODO: Faith is My Armor description");
        }
    },
    PHARMACOPHOBIA("pharmacophobia") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.text("TODO: Pharmacophobia description");
        }
    },
    BARREN_LAND("barren_land") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.text("TODO: Barren Land description");
        }
    },
    SWARM_INTELLIGENCE("swarm_intelligence") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.text("TODO: Swarm Intelligence description");
        }
    },
    INTO_DARKNESS("into_darkness") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.text("TODO: Into Darkness description");
        }
    },
    FORBIDDEN_RUNES("forbidden_runes") {
        @Override public void renderDescription(DescriptionPainter descriptionPainter) {
            descriptionPainter.text("TODO: Forbidden Runes description");
        }
    };

    public final String name;
    public final ResourceLocation icon;

    Challenge(String name) {
        this.name = name;
        this.icon = new ResourceLocation(NINE_CHALLENGES_MOD_ID, "textures/gui/challenge_icons/" + name + ".png");
    }

    /** Draws this challenge's description (strings and textures) via DescriptionPainter. */
    public abstract void renderDescription(DescriptionPainter descriptionPainter);

    public int mask() {
        return 1 << ordinal();
    }

    public boolean isIn(int mask) {
        return (mask & mask()) != 0;
    }
}