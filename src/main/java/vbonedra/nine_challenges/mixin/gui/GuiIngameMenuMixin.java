package vbonedra.nine_challenges.mixin.gui;

import net.minecraft.GuiButton;
import net.minecraft.GuiIngameMenu;
import net.minecraft.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.gui.ChallengesGui;
import vbonedra.nine_challenges.gui.GrailButton;
import vbonedra.nine_challenges.util.ActiveChallengeHolder;

@Mixin(GuiIngameMenu.class)
public abstract class GuiIngameMenuMixin extends GuiScreen {

    @Unique private static final int CHALLENGES_BUTTON_ID = 6847; // TODO: find better id setting way, its too unstable at the moment

    @Inject(at = @At("TAIL"), method = "initGui")
    private void initGui_AddChallengeGuiButton(CallbackInfo ci) {
        this.buttonList.add(new GrailButton(CHALLENGES_BUTTON_ID, this.width / 2 + 104, this.height / 4 + 24, new ActiveChallengeHolder()));
    }

    @Inject(at = @At("HEAD"), method = "actionPerformed", cancellable = true)
    private void actionPerformed_OpenChallengeGui(GuiButton button, CallbackInfo ci) {
        if (button.id == CHALLENGES_BUTTON_ID && button.enabled) {
            this.mc.displayGuiScreen(new ChallengesGui((GuiScreen) (Object) this, Challenges.getActiveMask()));
            ci.cancel();
        }
    }
}