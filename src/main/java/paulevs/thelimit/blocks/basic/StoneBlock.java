package paulevs.thelimit.blocks.basic;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import paulevs.thelimit.rendering.SimpleTexturedBlock;

public class StoneBlock extends TemplateBlockBase implements SimpleTexturedBlock {
	public StoneBlock(Identifier identifier) {
		super(identifier, Material.STONE);
		setHardness(1F);
		setBlastResistance(2F);
	}
}
