package me.roundaround.blanksigns.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import me.roundaround.blanksigns.config.BlankSignsConfig;
import me.roundaround.blanksigns.server.PlayerPreferenceTracker;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SignItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SignItem.class)
public class SignItemMixin {
  @WrapWithCondition(
      method = "postPlacement", at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/block/AbstractSignBlock;openEditScreen(Lnet/minecraft/entity/player/PlayerEntity;" +
               "Lnet/minecraft/block/entity/SignBlockEntity;Z)V"
  )
  )
  public boolean shouldOpenEditScreen(
      AbstractSignBlock instance,
      PlayerEntity player,
      SignBlockEntity blockEntity,
      boolean front,
      @Local(argsOnly = true) World world
  ) {
    MinecraftServer server = world.getServer();
    if (server == null) {
      return false;
    }

    if (!player.shouldCancelInteraction()) {
      return true;
    }


    // If in single player, just use the config value directly. If the mod is disabled server-side, then it is disabled
    // for everyone, regardless of their preference
    boolean disabled = !BlankSignsConfig.getInstance().modEnabled.getValue();
    if (server.isSingleplayer() || disabled) {
      return disabled;
    }

    // At this point we know 1. the player is sneaking, 2. we're in multiplayer, and 3. the mod is enabled server-side
    return PlayerPreferenceTracker.getInstance().disabledForPlayer(player);
  }
}
