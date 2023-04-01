package paulevs.thelimit.mixins.client;

import net.modificationstation.stationapi.api.client.texture.Sprite;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.thelimit.rendering.VoidFluidRenderer;

@Mixin(value = Sprite.class, remap = false)
public class SpriteMixin {
	@Shadow @Final private float uMin;
	@Shadow @Final private float uMax;
	@Shadow @Final private float vMin;
	@Shadow @Final private float vMax;
	
	@Inject(method = "getMinU", at = @At("HEAD"), cancellable = true)
	private void thelimit_getMinU(CallbackInfoReturnable<Float> info) {
		if (VoidFluidRenderer.side == -1) return;
		if (VoidFluidRenderer.side < 2) {
			float delta = (VoidFluidRenderer.POS.getX() & 3) * 0.25F;
			delta = MathHelper.lerp(delta, this.uMin, this.uMax);
			info.setReturnValue(delta);
		}
		else {
			int pos = VoidFluidRenderer.side < 4 ? VoidFluidRenderer.POS.getZ() : VoidFluidRenderer.POS.getX();
			float delta = (pos & 1) * 0.5F;
			delta = MathHelper.lerp(delta, this.uMin, this.uMax);
			info.setReturnValue(delta);
		}
	}
	
	@Inject(method = "getMaxU", at = @At("HEAD"), cancellable = true)
	private void thelimit_getMaxU(CallbackInfoReturnable<Float> info) {
		if (VoidFluidRenderer.side == -1) return;
		if (VoidFluidRenderer.side < 2) {
			float delta = (VoidFluidRenderer.POS.getX() & 3) * 0.25F + 0.25F;
			delta = MathHelper.lerp(delta, this.uMin, this.uMax);
			info.setReturnValue(delta);
		}
		else {
			int pos = VoidFluidRenderer.side < 4 ? VoidFluidRenderer.POS.getZ() : VoidFluidRenderer.POS.getX();
			float delta = (pos & 1) * 0.5F + 0.5F;
			delta = MathHelper.lerp(delta, this.uMin, this.uMax);
			info.setReturnValue(delta);
		}
	}
	
	@Inject(method = "getMinV", at = @At("HEAD"), cancellable = true)
	private void thelimit_getMinV(CallbackInfoReturnable<Float> info) {
		if (VoidFluidRenderer.side == -1) return;
		if (VoidFluidRenderer.side < 2) {
			float delta = (VoidFluidRenderer.POS.getZ() & 3) * 0.25F;
			delta = MathHelper.lerp(delta, this.vMin, this.vMax);
			info.setReturnValue(delta);
		}
		else {
			float delta = (VoidFluidRenderer.POS.getY() & 1) * 0.5F;
			delta = MathHelper.lerp(delta, this.vMin, this.vMax);
			info.setReturnValue(delta);
		}
	}
	
	@Inject(method = "getMaxV", at = @At("HEAD"), cancellable = true)
	private void thelimit_getMaxV(CallbackInfoReturnable<Float> info) {
		if (VoidFluidRenderer.side == -1) return;
		if (VoidFluidRenderer.side < 2) {
			float delta = (VoidFluidRenderer.POS.getZ() & 3) * 0.25F + 0.25F;
			delta = MathHelper.lerp(delta, this.vMin, this.vMax);
			info.setReturnValue(delta);
		}
		else {
			float delta = (VoidFluidRenderer.POS.getY() & 1) * 0.5F + 0.5F;
			delta = MathHelper.lerp(delta, this.vMin, this.vMax);
			info.setReturnValue(delta);
		}
	}
}
