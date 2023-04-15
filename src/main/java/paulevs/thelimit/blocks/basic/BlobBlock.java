package paulevs.thelimit.blocks.basic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.world.BlockStateView;
import paulevs.thelimit.rendering.AutoTextureBlock;
import paulevs.thelimit.rendering.BlobTileHelper;

public class BlobBlock extends TemplateBlockBase implements AutoTextureBlock {
	private final int[] textures = new int[47];
	private final String textureID;
	
	public BlobBlock(Identifier identifier, Material material) {
		super(identifier, material);
		this.textureID = identifier.modID + ":block/" + identifier.id + "/" + identifier.id;
	}
	
	@Environment(value= EnvType.CLIENT)
	public void registerTextures(ExpandableAtlas atlas) {
		for (byte i = 0; i < 47; i++) {
			textures[i] = atlas.addTexture(Identifier.of(textureID + "_" + i)).index;
		}
	}
	
	@Override
	public int getTextureForSide(BlockView view, int x, int y, int z, int side) {
		return getConnectedTextureForSide(view, x, y, z, side, this);
	}
	
	@Override
	public int getTextureForSide(int i) {
		return textures[15];
	}
	
	public int getConnectedTextureForSide(BlockView view, int x, int y, int z, int side, BlockBase filter) {
		if (!(view instanceof BlockStateView bsView)) return textures[15];
		return textures[BlobTileHelper.getTexture(bsView, x, y, z, filter, Direction.byId(side))];
	}
}
