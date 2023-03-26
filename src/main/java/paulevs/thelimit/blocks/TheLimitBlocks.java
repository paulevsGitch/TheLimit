package paulevs.thelimit.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.thelimit.TheLimit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TheLimitBlocks {
	public static final List<BlockBase> BLOCKS = new ArrayList<>();
	
	public static final BlockBase GLAUCOLIT = make("glaucolit", StoneBlock::new);
	public static final BlockBase VITILIT = make("vitilit", StoneBlock::new);
	public static final BlockBase HYPHUM = make("hyphum", StoneBlock::new);
	
	public static final BlockBase STELLATA_LOG = make("stellata_log", LogBlock::new);
	public static final BlockBase STELLATA_BARK = make("stellata_bark", LogBlock::new);
	public static final BlockBase STELLATA_STEM = make("stellata_stem", StemBlock::new);
	public static final BlockBase STELLATA_BRANCH = make("stellata_branch", BranchBlock::new);
	public static final BlockBase STELLATA_FLOWER = make("stellata_flower", StellataFlowerBlock::new);
	
	public static final BlockBase GUTTARBA_SHORT = make("guttarba_short", PlantBlock::new);
	public static final BlockBase GUTTARBA_NORMAL = make("guttarba_normal", PlantBlock::new);
	public static final BlockBase GUTTARBA_TALL = make("guttarba_tall", DoublePlantBlock::new);
	
	private static BlockBase make(String name, Function<Identifier, BlockBase> constructor) {
		Identifier id = TheLimit.id(name);
		BlockBase block = constructor.apply(id);
		block.setTranslationKey(name);
		BLOCKS.add(block);
		return block;
	}
	
	private static BlockBase make(String name, BiFunction<Identifier, Material, BlockBase> constructor, Material material) {
		Identifier id = TheLimit.id(name);
		BlockBase block = constructor.apply(id, material);
		//block.setTranslationKey(name);
		BLOCKS.add(block);
		return block;
	}
	
	public static void init() {
		BLOCKS.stream().map(BlockBase::getTranslationKey).forEach(System.out::println);
		BLOCKS.stream().map(BlockBase::getTranslatedName).forEach(System.out::println);
	}
}
