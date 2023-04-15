package paulevs.thelimit.blocks.basic;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;

public class LogBlock extends PillarBlock {
	public LogBlock(Identifier identifier) {
		super(identifier, Material.WOOD);
		setSounds(WOOD_SOUNDS);
		setHardness(1F);
	}
}
