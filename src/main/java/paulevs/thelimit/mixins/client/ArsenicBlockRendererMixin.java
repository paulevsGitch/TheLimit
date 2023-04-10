package paulevs.thelimit.mixins.client;

import net.minecraft.block.BlockBase;
import net.minecraft.client.render.block.BlockRenderer;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.client.texture.Sprite;
import net.modificationstation.stationapi.api.world.BlockStateView;
import net.modificationstation.stationapi.impl.client.arsenic.renderer.render.ArsenicBlockRenderer;
import net.modificationstation.stationapi.mixin.arsenic.client.BlockRendererAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.thelimit.blocks.TLBlockProperties;
import paulevs.thelimit.blocks.TLBlocks;
import paulevs.thelimit.rendering.VoidFluidRenderer;

@Mixin(value = ArsenicBlockRenderer.class, remap = false)
public abstract class ArsenicBlockRendererMixin {
	@Shadow public abstract void renderTopFace(BlockBase block, double renderX, double renderY, double renderZ, int textureIndex);
	
	@Shadow public abstract boolean renderFluid(BlockBase block, int x, int y, int z);
	
	@Shadow @Final private BlockRendererAccessor blockRendererAccessor;
	
	@Inject(method = "<init>", at = @At("TAIL"))
	private void thelimit_onInit(BlockRenderer blockRenderer, CallbackInfo info) {
		VoidFluidRenderer.side = -1;
	}
	
	@ModifyVariable(method = "renderFluid", at = @At("STORE"))
	private Sprite thelimit_customFluidSprite1(Sprite sprite) {
		if (VoidFluidRenderer.side == -1) return sprite;
		return VoidFluidRenderer.layer == 0 ? VoidFluidRenderer.voidFluid : VoidFluidRenderer.voidFluidEmission;
	}
	
	@ModifyVariable(method = "renderBottomFace", at = @At("STORE"))
	private Sprite thelimit_customFluidSprite2(Sprite sprite) {
		if (VoidFluidRenderer.side == -1) return sprite;
		return VoidFluidRenderer.layer == 0 ? VoidFluidRenderer.voidFluid : VoidFluidRenderer.voidFluidEmission;
	}
	
	@Inject(method = "renderFluid", at = @At(value = "FIELD", target = "Lnet/minecraft/block/BlockBase;maxY:D"))
	private void thelimit_stopRenderFluid(BlockBase block, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		VoidFluidRenderer.side = -1;
	}
	
	@Inject(method = "renderFluid", at = @At("HEAD"), cancellable = true)
	private void thelimit_stopRenderFluidLayered(BlockBase block, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
		if (block != TLBlocks.VOID_FLUID) return;
		if (VoidFluidRenderer.layer != -1) return;
		VoidFluidRenderer.layer = 0;
		this.renderFluid(block, x, y, z);
		VoidFluidRenderer.layer = 1;
		this.renderFluid(block, x, y, z);
		VoidFluidRenderer.layer = -1;
		info.setReturnValue(true);
	}
	
	@Inject(method = "renderWorld", at = @At("HEAD"), cancellable = true)
	public void renderWorld(BlockBase block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir, CallbackInfo info) {
		if (block.getRenderType() == 4) {
			BlockState state = ((BlockStateView) blockRendererAccessor.getBlockView()).getBlockState(x, y, z);
			if (!state.getProperties().contains(TLBlockProperties.VOIDLOGGED)) return;
			if (!state.get(TLBlockProperties.VOIDLOGGED)) return;
			renderFluid(TLBlocks.VOID_FLUID, x, y, z);
			cir.setReturnValue(true);
			info.cancel();
		}
	}
}
