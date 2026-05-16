package com.github.awruff.noclickdelay.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow
    public GameOptions options;

    @Shadow
    private int attackCooldown;

    @Redirect(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/KeyBinding;set(IZ)V")
    )
    private void noClickDelay$resetAttackCooldown(int keyCode, boolean pressed) {
        if (keyCode == options.attackKey.getKeyCode() && !pressed) {
            attackCooldown = 0;
        }

        KeyBinding.set(keyCode, pressed);
    }

}
