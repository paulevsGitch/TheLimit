package paulevs.thelimit.mixins.client;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas.Sprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Atlas.class, remap = false)
public interface AtlasAccessor {
	@Accessor("textures")
	Int2ObjectMap<Sprite> getTextures();
}
