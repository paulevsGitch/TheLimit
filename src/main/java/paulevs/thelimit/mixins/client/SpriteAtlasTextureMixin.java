package paulevs.thelimit.mixins.client;

import net.modificationstation.stationapi.api.client.texture.Sprite;
import net.modificationstation.stationapi.api.client.texture.SpriteAtlasTexture;
import net.modificationstation.stationapi.api.client.texture.SpriteAtlasTexture.Data;
import net.modificationstation.stationapi.api.client.texture.TextureStitcher;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.resource.ResourceManager;
import net.modificationstation.stationapi.api.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.rendering.VoidFluidRenderer;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Mixin(value = SpriteAtlasTexture.class, remap = false)
public class SpriteAtlasTextureMixin {
	@Shadow @Final private Map<Identifier, Sprite> sprites;
	
	@Shadow @Final private Set<Identifier> spritesToLoad;
	
	@Inject(method = "upload", at = @At("RETURN"))
	public void thelinit_upload(Data data, CallbackInfo info) {
		VoidFluidRenderer.sprite_small = this.sprites.get(TheLimit.id("block/chthonia_void_fluid"));
		VoidFluidRenderer.sprite_big = this.sprites.get(TheLimit.id("block/chthonia_void_fluid"));
	}
	
	@Inject(method = "stitch", at = @At(
		value = "INVOKE",
		target = "Lnet/modificationstation/stationapi/api/util/profiler/Profiler;swap(Ljava/lang/String;)V",
		shift = Shift.BEFORE,
		ordinal = 0
	), locals = LocalCapture.CAPTURE_FAILHARD)
	private void thelinit_addTextures(ResourceManager m, Stream<Identifier> s, Profiler p, CallbackInfoReturnable<Data> info, Set<Identifier> set, int i, TextureStitcher ts) {
		set.add(TheLimit.id("block/chthonia_void_fluid"));
		set.add(TheLimit.id("block/chthonia_void_fluid"));
	}
}
