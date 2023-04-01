package paulevs.thelimit.mixins.client;

import net.minecraft.block.Fluid;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Fluid.class)
public class FluidMixin {
	@Inject(method = "method_1223", at = @At("HEAD"), cancellable = true)
	private static void thelimit_fixVector(BlockView arg, int i, int j, int k, Material arg2, CallbackInfoReturnable<Double> info) {
		if (arg2 != Material.WATER && arg2 != Material.LAVA) {
			info.setReturnValue(0.0);
		}
	}
}
