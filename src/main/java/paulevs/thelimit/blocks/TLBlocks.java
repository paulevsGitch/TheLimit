package paulevs.thelimit.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.world.structures.TheLimitStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class TLBlocks {
	public static final List<BlockBase> BLOCKS = new ArrayList<>();
	
	public static final BlockBase GLAUCOLIT = make("glaucolit", StoneBlock::new);
	public static final BlockBase VITILIT = make("vitilit", StoneBlock::new);
	public static final BlockBase HYPHUM = make("hyphum", StoneBlock::new);
	
	public static final BlockBase STELLATA_LOG = make("stellata_log", LogBlock::new);
	public static final BlockBase STELLATA_BARK = make("stellata_bark", LogBlock::new);
	public static final BlockBase STELLATA_STEM = make("stellata_stem", StemBlock::new);
	public static final BlockBase STELLATA_BRANCH = make("stellata_branch", BranchBlock::new);
	public static final BlockBase STELLATA_FLOWER = make("stellata_flower", StellataFlowerBlock::new);
	public static final BlockBase STELLATA_SAPLING = makeSapling("stellata_sapling", () -> TheLimitStructures.STELLATA_TREE);
	
	public static final BlockBase STELLATA_PLANKS = make("stellata_planks", PlanksBlock::new);
	
	public static final BlockBase CALABELLUM = make("calabellum", CalabellumPlant::new);
	public static final BlockBase CALABELLUM_SAPLING = makeSapling("calabellum_sapling", () -> TheLimitStructures.CALABELLUM);
	
	public static final BlockBase NIVERUS_LOG = make("niverus_log", LogBlock::new);
	public static final BlockBase NIVERUS_BARK = make("niverus_bark", LogBlock::new);
	public static final BlockBase NIVERUS_LEAVES = make("niverus_leaves", LeavesBlock::new);
	public static final BlockBase NIVERUS_OUTER_LEAVES = make("niverus_outer_leaves", MossBlock::new);
	public static final BlockBase NIVERUS_VINE = make("niverus_vine", VineBlock::new);
	
	public static final BlockBase GUTTARBA_SHORT = make("guttarba_short", PlantBlock::new);
	public static final BlockBase GUTTARBA_NORMAL = make("guttarba_normal", PlantBlock::new);
	public static final BlockBase GUTTARBA_TALL = make("guttarba_tall", DoublePlantBlock::new);
	
	public static final BlockBase GLOW_PLANT = make("glow_plant", GlowingPlant::new);
	
	public static final BlockBase MOSS = make("moss", MossBlock::new);
	
	//public static final FlowingVoidFluid VOID_FLUID_FLOWING = make("void_fluid_flowing", FlowingVoidFluid::new);
	//public static final StillVoidFluid VOID_FLUID_STILL = make("void_fluid_still", StillVoidFluid::new);
	public static final VoidFluidBlock VOID_FLUID = make("void_fluid", VoidFluidBlock::new);
	
	public static final BlockBase BISMUTH_BLOCK = make("bismuth_block", MetalBlock::new);
	public static final BlockBase BISMUTH_TILES = make("bismuth_tiles", MetalBlock::new);
	public static final BlockBase BISMUTH_LARGE_TILE = make("bismuth_large_tile", MetalBlock::new);
	public static final BlockBase BISMUTH_DECORATED = make("bismuth_decorated", MetalBlobBlock::new);
	public static final BlockBase BISMUTH_LANTERN = make("bismuth_lantern", MetalLantern::new);
	
	private static <T extends BlockBase> T make(String name, Function<Identifier, T> constructor) {
		Identifier id = TheLimit.id(name);
		T block = constructor.apply(id);
		block.setTranslationKey(id.toString());
		BLOCKS.add(block);
		return block;
	}
	
	private static SaplingBlock makeSapling(String name, Supplier<Structure> structure) {
		Identifier id = TheLimit.id(name);
		SaplingBlock block = new SaplingBlock(id, structure);
		block.setTranslationKey(id.toString());
		BLOCKS.add(block);
		return block;
	}
	
	public static void init() {
		((TemplateBlockBase) NIVERUS_VINE).setLightEmittance(0.75F);
		((TemplateBlockBase) HYPHUM).setSounds(TLBlockSounds.HYPHUM);
	}
	
	public static boolean isStone(BlockState state) {
		return state.isOf(GLAUCOLIT) || state.isOf(VITILIT);
	}
}
