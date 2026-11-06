package vbonedra.nine_challenges.mixin.gui;

import net.minecraft.GuiCreateWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiCreateWorld.class)
public interface GuiCreateWorldAccessor {
    // used to read private moreOptions
    @Accessor("moreOptions")
    boolean getMoreOptions();
}