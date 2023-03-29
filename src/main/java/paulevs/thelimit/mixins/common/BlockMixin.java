package paulevs.thelimit.mixins.common;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.DimensionRegistry;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.world.dimension.DimensionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.world.dimension.DimensionTraveler;

import java.util.Objects;

@Mixin(BlockBase.class)
public class BlockMixin {
	@Inject(
		method = "activate(Lnet/minecraft/level/Level;IIILnet/minecraft/entity/player/PlayerBase;)V",
		at = @At("HEAD")
	)
	public void thelimit_activate(Level level, int x, int y, int z, PlayerBase player, CallbackInfo info) {
		Identifier id = TheLimit.id("the_limit");
		if (player.dimensionId == Objects.requireNonNull(DimensionRegistry.INSTANCE.get(id)).getLegacyID()) return;
		DimensionHelper.switchDimension(player, TheLimit.id("the_limit"), 1, new DimensionTraveler());
	}
}
