package paulevs.thelimit.mixins.client;

import net.minecraft.sortme.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.thelimit.rendering.SkyRenderer;

import java.nio.FloatBuffer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Inject(method = "method_1839", at = @At("HEAD"))
	private void method_1839(float r, float g, float b, float a, CallbackInfoReturnable<FloatBuffer> info) {
		SkyRenderer.setFogColor(r, g, b);
	}
}
