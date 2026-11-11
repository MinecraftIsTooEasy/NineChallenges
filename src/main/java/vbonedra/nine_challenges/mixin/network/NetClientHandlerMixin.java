package vbonedra.nine_challenges.mixin.network;

import net.minecraft.NetClientHandler;
import net.minecraft.Packet1Login;
import net.minecraft.Packet250CustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenges;
import vbonedra.nine_challenges.network.NineChallengesNetwork;

@Mixin(NetClientHandler.class)
public abstract class NetClientHandlerMixin {

    @Inject(method = "handleLogin", at = @At("HEAD"))
    private void handleLogin_ResetClientChallenge(Packet1Login packet, CallbackInfo ci) {
        Challenges.resetClientMask();
    }

    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    private void handleCustomPayload_TryToSaveMask(Packet250CustomPayload packet, CallbackInfo ci) {
        if (NineChallengesNetwork.receiveChallengeMask(packet)) {
            ci.cancel();
        }
    }

}