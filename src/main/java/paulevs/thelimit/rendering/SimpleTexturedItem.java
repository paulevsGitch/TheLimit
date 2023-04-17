package paulevs.thelimit.rendering;

import net.minecraft.item.ItemBase;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ItemRegistry;

public interface SimpleTexturedItem extends AutoTextured {
	@Override
	default void registerTextures(ExpandableAtlas atlas) {
		ItemBase item = (ItemBase) this;
		Identifier id = ItemRegistry.INSTANCE.getId(item);
		if (id == null) return;
		item.setTexturePosition(atlas.addTexture(Identifier.of(id.modID + ":item/" + id.id)).index);
	}
}
