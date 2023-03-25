package paulevs.thelimit.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import paulevs.thelimit.TheLimit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TheLimitBlocks {
	public static final List<BlockBase> BLOCKS = new ArrayList<>();
	
	public static final BlockBase GLAUCOLIT = make("glaucolit", TemplateBlockBase::new, Material.STONE);
	public static final BlockBase VITILIT = make("vitilit", TemplateBlockBase::new, Material.STONE);
	public static final BlockBase HYPHUM = make("hyphum", TemplateBlockBase::new, Material.STONE);
	
	public static final BlockBase STELLATA_LOG = make("stellata_log", LogBlock::new);
	public static final BlockBase STELLATA_BARK = make("stellata_bark", LogBlock::new);
	public static final BlockBase STELLATA_STEM = make("stellata_stem", StemBlock::new);
	public static final BlockBase STELLATA_BRANCH = make("stellata_branch", BranchBlock::new);
	public static final BlockBase STELLATA_FLOWER = make("stellata_flower", StellataFlowerBlock::new);
	
	public static final BlockBase BLOB_GRASS = make("blob_grass", PlantBlock::new);
	
	private static BlockBase make(String name, Function<Identifier, BlockBase> constructor) {
		Identifier id = TheLimit.id(name);
		BlockBase block = constructor.apply(id);
		BLOCKS.add(block);
		return block;
	}
	
	private static BlockBase make(String name, BiFunction<Identifier, Material, BlockBase> constructor, Material material) {
		Identifier id = TheLimit.id(name);
		BlockBase block = constructor.apply(id, material);
		BLOCKS.add(block);
		return block;
	}
	
	public static void init() {}
}
