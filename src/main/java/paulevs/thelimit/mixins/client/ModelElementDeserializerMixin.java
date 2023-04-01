package paulevs.thelimit.mixins.client;

import com.google.gson.JsonObject;
import net.modificationstation.stationapi.api.client.render.model.json.ModelElement;
import net.modificationstation.stationapi.api.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ModelElement.Deserializer.class, remap = false)
public abstract class ModelElementDeserializerMixin {
	@Shadow protected abstract Vec3f deserializeVec3f(JsonObject object, String name);
	
	@Inject(method = "deserializeTo", at = @At("HEAD"), cancellable = true)
	private void thelimit_deserializeTo(JsonObject object, CallbackInfoReturnable<Vec3f> info) {
		info.setReturnValue(this.deserializeVec3f(object, "to"));
	}
	
	@Inject(method = "deserializeFrom", at = @At("HEAD"), cancellable = true)
	private void thelimit_deserializeFrom(JsonObject object, CallbackInfoReturnable<Vec3f> info) {
		info.setReturnValue(this.deserializeVec3f(object, "from"));
	}
}
