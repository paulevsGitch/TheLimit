package paulevs.thelimit.mixins.client;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.level.BlockView;
import net.modificationstation.stationapi.api.world.BlockStateView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.thelimit.blocks.TheLimitBlocks;
import paulevs.thelimit.rendering.VoidFluidRenderer;

@Mixin(BlockRenderer.class)
public class BlockRendererMixin {
	@Shadow private BlockView blockView;
	@Unique private boolean thelimit_apply;
	
	@Inject(method = "renderFluid", at = @At("HEAD"))
	private void thelimit_renderFluid(BlockBase block, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		thelimit_apply = block == TheLimitBlocks.VOID_FLUID;
		if (thelimit_apply) {
			VoidFluidRenderer.POS.set(x, y, z);
		}
	}
	
	@Inject(method = "renderFluid", at = @At("RETURN"))
	private void thelimit_stopRenderFluid(BlockBase block, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		VoidFluidRenderer.side = -1;
	}
	
	@Inject(method = "method_43", at = @At("HEAD"), cancellable = true)
	private void thelimit_getFluidHeight(int x, int y, int z, Material material, CallbackInfoReturnable<Float> info) {
		if (!thelimit_apply) return;
		if (!(this.blockView instanceof BlockStateView view)) return;
		info.setReturnValue(VoidFluidRenderer.getFluidHeight(view, x, y, z));
	}
}
