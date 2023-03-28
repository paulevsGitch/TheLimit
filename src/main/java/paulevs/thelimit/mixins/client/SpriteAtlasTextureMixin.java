package paulevs.thelimit.mixins.client;

import net.modificationstation.stationapi.api.client.texture.Sprite;
import net.modificationstation.stationapi.api.client.texture.SpriteAtlasTexture;
import net.modificationstation.stationapi.api.client.texture.SpriteAtlasTexture.Data;
import net.modificationstation.stationapi.api.registry.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = SpriteAtlasTexture.class, remap = false)
public class SpriteAtlasTextureMixin {
	@Shadow @Final private Map<Identifier, Sprite> sprites;
	
	@Inject(method = "upload", at = @At("RETURN"))
	public void thelinit_upload(Data data, CallbackInfo info) {
		/*System.out.println("A!");
		ExpandableAtlas atlas = Atlases.getTerrain();
		if (atlas == null) return;
		atlas.idToTex.keySet().forEach(System.out::println);*/
		//this.sprites.keySet().forEach(System.out::println);
		
		/*Sprite sprite = this.sprites.get(TheLimit.id("thelimit:block/guttarba_normal"));
		if (sprite != null) {
			TheLimitBlocks.VOID_FLUID.texture = 200;//sprite.;
		}*/
		
		//TheLimitBlocks.VOID_FLUID.texture = sprites.size() - 1;//s
		//Atlases.getTerrain().idToTex.keySet().forEach(System.out::println);
		
		/*Sprite sprite = this.sprites.get(TheLimit.id("block/guttarba_normal"));
		System.out.println(sprite);
		if (sprite != null) return;
		System.out.println(sprite.getAtlas());
		System.out.println(sprite.getAtlas().);*/
	}
}
