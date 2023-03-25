package paulevs.thelimit.mixins.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.mine_diver.glsl.Shaders", remap = false)
public class ShadersMixin {
	@Shadow public static void setProgramUniform1i(String name, int x) {}
	
	@Inject(method = "useProgram", at = @At("RETURN"))
	private static void thelimit_useProgram(int program, CallbackInfo info) {
		if (program == 8 || program == 9) {
			setProgramUniform1i("worldTime", 18000);
		}
	}
}
