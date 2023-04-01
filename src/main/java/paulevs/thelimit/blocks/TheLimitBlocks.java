package paulevs.thelimit.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.thelimit.TheLimit;
import paulevs.thelimit.world.structures.TheLimitStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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
	public static final BlockBase STELLATA_SAPLING = makeSapling("stellata_sapling", () -> TheLimitStructures.STELLATA_TREE);
	
	public static final BlockBase STELLATA_PLANKS = make("stellata_planks", PlanksBlock::new);
	
	public static final BlockBase CALABELLUM = make("calabellum", CalabellumPlant::new);
	public static final BlockBase CALABELLUM_SAPLING = makeSapling("calabellum_sapling", () -> TheLimitStructures.CALABELLUM);
	
	public static final BlockBase GUTTARBA_SHORT = make("guttarba_short", PlantBlock::new);
	public static final BlockBase GUTTARBA_NORMAL = make("guttarba_normal", PlantBlock::new);
	public static final BlockBase GUTTARBA_TALL = make("guttarba_tall", DoublePlantBlock::new);
	
	public static final BlockBase GLOW_PLANT = make("glow_plant", GlowingPlant::new);
	
	public static final BlockBase MOSS = make("moss", MossBlock::new);
	
	//public static final FlowingVoidFluid VOID_FLUID_FLOWING = make("void_fluid_flowing", FlowingVoidFluid::new);
	//public static final StillVoidFluid VOID_FLUID_STILL = make("void_fluid_still", StillVoidFluid::new);
	public static final VoidFluidBlock VOID_FLUID = make("void_fluid", VoidFluidBlock::new);
	
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
		//VOID_FLUID_FLOWING.setStillFluid(VOID_FLUID_STILL::getDefaultState);
		//VOID_FLUID_STILL.setFlowingFluid(VOID_FLUID_FLOWING::getDefaultState);
	}
}
