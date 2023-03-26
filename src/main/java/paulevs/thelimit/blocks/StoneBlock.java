package paulevs.thelimit.blocks;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class StoneBlock extends TemplateBlockBase {
	public StoneBlock(Identifier identifier) {
		super(identifier, Material.STONE);
		setHardness(1F);
		setBlastResistance(2F);
	}
}
