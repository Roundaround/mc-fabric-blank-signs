package me.roundaround.blanksigns.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SignItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SignItem.class)
public class SignItemMixin {
  @ModifyExpressionValue(
      method = "postPlacement", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isClient:Z")
  )
  public boolean wrapIsClient(boolean isClient, @Local(argsOnly = true) PlayerEntity player) {
    if (!isClient) {
      return player.shouldCancelInteraction();
    }
    return true;
  }
}
