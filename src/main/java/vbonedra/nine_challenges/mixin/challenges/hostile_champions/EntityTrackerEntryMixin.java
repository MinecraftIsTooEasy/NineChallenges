package vbonedra.nine_challenges.mixin.challenges.hostile_champions;

import moddedmite.rustedironcore.network.Network;
import net.minecraft.Entity;
import net.minecraft.EntityTrackerEntry;
import net.minecraft.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vbonedra.nine_challenges.challenge.Challenge;
import vbonedra.nine_challenges.network.S2CSendHostileChampionType;
import vbonedra.nine_challenges.util.hostile_champion.HostileChampion;
import vbonedra.nine_challenges.util.hostile_champion.IHostileChampionType;

import static vbonedra.nine_challenges.challenge.Challenges.isChallengeEnabled;

@Mixin(EntityTrackerEntry.class)
public class EntityTrackerEntryMixin {

    @Shadow public Entity myEntity;

    @Inject(method = "tryStartWachingThis", at = @At(value = "TAIL"))
    private void tryStartWatchingThis_SendChampionType(ServerPlayer player, CallbackInfo ci) {
        if (!isChallengeEnabled(Challenge.HOSTILE_CHAMPIONS)) return;
        if (player == null || player.isDead || player.playerNetServerHandler == null) return;
        if (!(this.myEntity instanceof IHostileChampionType)) return;

        byte hostileChampionType = ((IHostileChampionType) this.myEntity).NC$getHostileChampionType();
        if (hostileChampionType == HostileChampion.Unset) return;

        Network.sendToClient(player, new S2CSendHostileChampionType(this.myEntity.entityId, hostileChampionType));
    }



}
