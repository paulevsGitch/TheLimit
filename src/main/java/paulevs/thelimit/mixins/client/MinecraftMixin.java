package paulevs.thelimit.mixins.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "printOpenGLError", at = @At("HEAD"), cancellable = true)
	private void thelimit_cancelPrint(String string, CallbackInfo info) {
		info.cancel();
	}
}
