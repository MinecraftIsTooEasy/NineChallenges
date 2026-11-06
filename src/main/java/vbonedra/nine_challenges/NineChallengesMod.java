package vbonedra.nine_challenges;

import vbonedra.nine_challenges.event.ExampleEvent;
import net.fabricmc.api.ModInitializer;

import net.xiaoyu233.fml.ModResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NineChallengesMod implements ModInitializer {
    public static final String NINE_CHALLENGES_MOD_ID = "nine_challenges";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote worldInfo, warnings, and errors.
    public static final Logger LOGGER = LogManager.getLogger(NINE_CHALLENGES_MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello MITE Dev world!");

        // Add resource pack domain, default "minecraft"
        ModResourceManager.addResourcePackDomain(NINE_CHALLENGES_MOD_ID);

        //Register an event listening object
        ExampleEvent.register();
    }
}
