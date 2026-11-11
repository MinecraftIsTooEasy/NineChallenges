package vbonedra.nine_challenges.mixin.challenges.hostile_champions.antimagic;

import net.minecraft.EnchantmentHelper;
import net.minecraft.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vbonedra.nine_challenges.util.ChampionEnchantContext;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    // TODO: add more enchants: thorns, sharpness, smite, ...

    @Inject(method = "getKnockbackModifier(Lnet/minecraft/EntityLivingBase;Lnet/minecraft/EntityLivingBase;)I", at = @At("HEAD"), cancellable = true)
    private static void getKnockbackModifier_Antimagic(EntityLivingBase attacker, EntityLivingBase victim, CallbackInfoReturnable<Integer> cir) {
        if (ChampionEnchantContext.isEnchantImmune(victim)) cir.setReturnValue(0);
    }
    @Inject(method = "getStunModifier(Lnet/minecraft/EntityLivingBase;Lnet/minecraft/EntityLivingBase;)I", at = @At("HEAD"), cancellable = true)
    private static void getStunModifier_Antimagic(EntityLivingBase attacker, EntityLivingBase victim, CallbackInfoReturnable<Integer> cir) {
        if (ChampionEnchantContext.isEnchantImmune(victim)) cir.setReturnValue(0);
    }
    @Inject(method = "getVampiricTransfer(Lnet/minecraft/EntityLivingBase;Lnet/minecraft/EntityLivingBase;F)I", at = @At("HEAD"), cancellable = true)
    private static void getVampiricTransfer_Antimagic(EntityLivingBase attacker, EntityLivingBase victim, float damage, CallbackInfoReturnable<Integer> cir) {
        if (ChampionEnchantContext.isEnchantImmune(victim)) cir.setReturnValue(0);
    }

    @Inject(method = "getFireAspectModifier(Lnet/minecraft/EntityLivingBase;)I", at = @At("HEAD"), cancellable = true)
    private static void getFireAspectModifier_Antimagic(EntityLivingBase attacker, CallbackInfoReturnable<Integer> cir) {
        if (ChampionEnchantContext.isCurrentVictimEnchantImmune()) cir.setReturnValue(0);
    }
    @Inject(method = "getLootingModifier(Lnet/minecraft/EntityLivingBase;)I", at = @At("HEAD"), cancellable = true)
    private static void getLootingModifier_Antimagic(EntityLivingBase attacker, CallbackInfoReturnable<Integer> cir) {
        if (ChampionEnchantContext.isCurrentVictimEnchantImmune()) cir.setReturnValue(0);
    }
    @Inject(method = "getButcheringModifier(Lnet/minecraft/EntityLivingBase;)I", at = @At("HEAD"), cancellable = true)
    private static void getButcheringModifier_Antimagic(EntityLivingBase attacker, CallbackInfoReturnable<Integer> cir) {
        if (ChampionEnchantContext.isCurrentVictimEnchantImmune()) cir.setReturnValue(0);
    }



}