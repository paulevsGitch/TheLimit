package paulevs.thelimit.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateFluid;

public class VoidFluidBlock extends TemplateFluid {
	public boolean initialised;
	private int tex = -1;
	
	public VoidFluidBlock(Identifier id) {
		super(id, Material.WATER);
		//this.texture = 10;
	}
	
	/*@Override
	public void onBlockPlaced(Level level, int x, int y, int z) {
		super.onBlockPlaced(level, x, y, z);
		level.setTileMeta(x, y, z, 8);
	}*/
	
	@Override
	@Environment(value=EnvType.CLIENT)
	public int getRenderPass() {
		return 0;
	}
	
	@Override
	public int getTextureForSide(int i) {
		//System.out.println("Call!");
		if (tex != -1) return tex;
		if (Atlases.getTerrain() == null) return 0;
		
		tex = 1;
		
		//Sprite sprite = Atlases.getTerrain().getTexture(TheLimit.id("block/guttarba_normal"));
		//System.out.println(sprite);
		
		//SpriteAtlasTexture atlas = StationRenderAPI.getBakedModelManager().getAtlas(Atlases.GAME_ATLAS_TEXTURE);
		//atlas.getSprite()
		//System.out.println(Atlases.getTerrain().getTexture());
		//System.out.println(atlas);
		//Sprite sprite = atlas.getSprite(TheLimit.id("block/guttarba_normal"));
		//tex = atlas.getSprite(TheLimit.id("block/guttarba_normal")).;
		//Atlases.getTerrain().idToTex.values().forEach(sprite -> System.out.println(sprite.getId()));
		
		//if (!initialised) return 0;
		//System.out.println(Atlases.getTerrain().getTexture(TheLimit.id("block/guttarba_normal")));
		//System.out.println(Atlases.getTerrain().getTexture(0));
		//if (tex == -1) tex = Atlases.getTerrain().getTexture(TheLimit.id("block/guttarba_normal")).index;
		//AtlasAccessor accessor = (AtlasAccessor) Atlases.getTerrain();
		//accessor.getTextures().values().stream().filter(sprite -> sprite.index)
		return 0;
	}
}
