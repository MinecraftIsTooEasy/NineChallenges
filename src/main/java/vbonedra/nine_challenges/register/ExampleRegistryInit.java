package vbonedra.nine_challenges.register;

import vbonedra.nine_challenges.NineChallengesMod;
import huix.glacier.api.entrypoint.IGameRegistry;
import huix.glacier.api.registry.MinecraftRegistry;

public class ExampleRegistryInit implements IGameRegistry {
	// Registrar instance, using this mod's nine_challenges as the namespace
	public static final MinecraftRegistry registry = new MinecraftRegistry(NineChallengesMod.NINE_CHALLENGES_MOD_ID).initAutoItemRegister();

	// Create an instance of the item
	// public static Item EXAMPLE_ITEM;

	@Override
	public void onGameRegistry() {
		// Register items, bind texture and create localized key
		// registry.registerItem(NineChallengesMod.MOD_ID + ":example_item", "exampleItem", EXAMPLE_ITEM);
	}
}
