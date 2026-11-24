/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.registry;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import reika.rotarycraft.RotaryCraft;
import reika.rotarycraft.content.block.AdvancedGearBlock;
import reika.rotarycraft.content.block.AerosolizerBlock;
import reika.rotarycraft.content.block.AmmoWeaponBlock;
import reika.rotarycraft.content.block.AutoBreederBlock;
import reika.rotarycraft.content.block.AutoCrafterBlock;
import reika.rotarycraft.content.block.BaitBoxBlock;
import reika.rotarycraft.content.block.BeamMirrorBlock;
import reika.rotarycraft.content.block.BeltHubBlock;
import reika.rotarycraft.content.block.BigFurnaceBlock;
import reika.rotarycraft.content.block.BlastFurnaceBlock;
import reika.rotarycraft.content.block.BedrockBreakerBlock;
import reika.rotarycraft.content.block.BedrockSliceBlock;
import reika.rotarycraft.content.block.BlastGlassBlock;
import reika.rotarycraft.content.block.BlowerBlock;
import reika.rotarycraft.content.block.BorerBlock;
import reika.rotarycraft.content.block.BridgeBlock;
import reika.rotarycraft.content.block.BucketFillerBlock;
import reika.rotarycraft.content.block.BusControllerBlock;
import reika.rotarycraft.content.block.CanolaBlock;
import reika.rotarycraft.content.block.CaveScannerBlock;
import reika.rotarycraft.content.block.CctvBlock;
import reika.rotarycraft.content.block.CentrifugeBlock;
import reika.rotarycraft.content.block.ChainDriveBlock;
import reika.rotarycraft.content.block.ChunkLoaderBlock;
import reika.rotarycraft.content.block.CompactorBlock;
import reika.rotarycraft.content.block.ComposterBlock;
import reika.rotarycraft.content.block.CrystallizerBlock;
import reika.rotarycraft.content.block.DefoliatorBlock;
import reika.rotarycraft.content.block.DisplayBlock;
import reika.rotarycraft.content.block.DistributionClutchBlock;
import reika.rotarycraft.content.block.DropProcessorBlock;
import reika.rotarycraft.content.block.DryingBedBlock;
import reika.rotarycraft.content.block.EcuBlock;
import reika.rotarycraft.content.block.ExtractorBlock;
import reika.rotarycraft.content.block.FanBlock;
import reika.rotarycraft.content.block.FermenterBlock;
import reika.rotarycraft.content.block.FertilizerBlock;
import reika.rotarycraft.content.block.FillingStationBlock;
import reika.rotarycraft.content.block.FireworkMachineBlock;
import reika.rotarycraft.content.block.FloodlightBlock;
import reika.rotarycraft.content.block.FluidCompressorBlock;
import reika.rotarycraft.content.block.FractionatorBlock;
import reika.rotarycraft.content.block.FrictionHeaterBlock;
import reika.rotarycraft.content.block.GprBlock;
import reika.rotarycraft.content.block.GrinderBlock;
import reika.rotarycraft.content.block.GrindstoneBlock;
import reika.rotarycraft.content.block.GroundHydratorBlock;
import reika.rotarycraft.content.block.HeatRayBlock;
import reika.rotarycraft.content.block.HeaterBlock;
import reika.rotarycraft.content.block.IgniterBlock;
import reika.rotarycraft.content.block.ItemCannonBlock;
import reika.rotarycraft.content.block.ItemFilterBlock;
import reika.rotarycraft.content.block.ItemRefresherBlock;
import reika.rotarycraft.content.block.LampBlock;
import reika.rotarycraft.content.block.LandmineBlock;
import reika.rotarycraft.content.block.LavaMakerBlock;
import reika.rotarycraft.content.block.LawnSprinklerBlock;
import reika.rotarycraft.content.block.LightBridgeBlock;
import reika.rotarycraft.content.block.LineBuilderBlock;
import reika.rotarycraft.content.block.MagneticEngineBlock;
import reika.rotarycraft.content.block.MagnetizerBlock;
import reika.rotarycraft.content.block.MiningPipeBlock;
import reika.rotarycraft.content.block.MirrorBlock;
import reika.rotarycraft.content.block.MobRadarBlock;
import reika.rotarycraft.content.block.MultiClutchBlock;
import reika.rotarycraft.content.block.MusicBoxBlock;
import reika.rotarycraft.content.block.ObsidianMakerBlock;
import reika.rotarycraft.content.block.ParticleEmitterBlock;
import reika.rotarycraft.content.block.PileDriverBlock;
import reika.rotarycraft.content.block.PlayerDetectorBlock;
import reika.rotarycraft.content.block.PortalShaftBlock;
import reika.rotarycraft.content.block.PowerBusBlock;
import reika.rotarycraft.content.block.ProjectorBlock;
import reika.rotarycraft.content.block.PulseJetFurnaceBlock;
import reika.rotarycraft.content.block.PumpBlock;
import reika.rotarycraft.content.block.PurifierBlock;
import reika.rotarycraft.content.block.RefrigeratorBlock;
import reika.rotarycraft.content.block.ReservoirBlock;
import reika.rotarycraft.content.block.ScaleableChestBlock;
import reika.rotarycraft.content.block.ScreenBlock;
import reika.rotarycraft.content.block.SmokeDetectorBlock;
import reika.rotarycraft.content.block.SonicBorerBlock;
import reika.rotarycraft.content.block.SortingBlock;
import reika.rotarycraft.content.block.SpawnerControllerBlock;
import reika.rotarycraft.content.block.SpillwayBlock;
import reika.rotarycraft.content.block.SplitBeltBlock;
import reika.rotarycraft.content.block.SprinklerBlock;
import reika.rotarycraft.content.block.SpyCamBlock;
import reika.rotarycraft.content.block.TerraformerBlock;
import reika.rotarycraft.content.block.VacuumBlock;
import reika.rotarycraft.content.block.WeatherControllerBlock;
import reika.rotarycraft.content.block.WetterBlock;
import reika.rotarycraft.content.block.WinderBlock;
import reika.rotarycraft.content.block.WoodcutterBlock;
import reika.rotarycraft.content.block.WorktableBlock;
import reika.rotarycraft.content.block.EngineBlock;
import reika.rotarycraft.content.block.FlammableBlock;
import reika.rotarycraft.content.block.FlywheelBlock;
import reika.rotarycraft.content.block.GearboxBlock;
import reika.rotarycraft.content.block.MachineBlock;
import reika.rotarycraft.content.block.MachineBlock.Placement;
import reika.rotarycraft.content.block.ShaftBlock;
import reika.rotarycraft.content.block.PipingBlock;
import reika.rotarycraft.content.blockentity.AdvancedGearBlockEntity;
import reika.rotarycraft.content.blockentity.RotaryCraftBlockEntity;
import reika.rotarycraft.content.item.MachineBlockItem;
import reika.rotarycraft.lib.LegacyResistance;

/** Original: MachineRegistry entries, one block per machine instead of metadata-shared blocks. */
public final class RotaryBlocks {

	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RotaryCraft.MOD_ID);

	/**
	 * Original BlockBasicMachine/BlockRotaryCraftMachine: hardness 4, setResistance(15) (= blast resistance 9, see LegacyResistance),
	 * Material.iron (metal sound, needs a tool).
	 */
	public static BlockBehaviour.Properties machineProperties() {
		return BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(4F, LegacyResistance.of(15F)).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion();
	}

	public static final DeferredBlock<ShaftBlock> SHAFT = machine("shaft", ShaftBlock::new);
	public static final DeferredBlock<GearboxBlock> GEARBOX = machine("gearbox", GearboxBlock::new);
	public static final DeferredBlock<FlywheelBlock> FLYWHEEL = machine("flywheel", FlywheelBlock::new);
	public static final DeferredBlock<MachineBlock> BEVEL_GEARS = machine("bevel_gears", p -> new MachineBlock(p, RotaryBlockEntities.BEVEL_GEARS, Placement.NONE, true));
	public static final DeferredBlock<MachineBlock> CLUTCH = machine("clutch", p -> new MachineBlock(p, RotaryBlockEntities.CLUTCH, Placement.SIX_SIDED, true));
	/** Original MachineRegistry.FUELENHANCER (BlockMMachine, no orientation). */
	public static final DeferredBlock<MachineBlock> FUEL_ENHANCER = machine("fuel_enhancer", p -> new MachineBlock(p, RotaryBlockEntities.FUEL_ENHANCER, Placement.NONE, false));
	/** Original MachineRegistry.COOLINGFIN (side placed, 6 orientations, BlockDMMachine). */
	public static final DeferredBlock<MachineBlock> COOLING_FIN = machine("cooling_fin", p -> new MachineBlock(p, RotaryBlockEntities.COOLING_FIN, Placement.SIDE_ATTACHED, false));
	/** Original MachineRegistry.MIRROR and SOLARTOWER (BlockSolar, no orientation). */
	public static final DeferredBlock<MirrorBlock> MIRROR = machine("solar_mirror", MirrorBlock::new);
	public static final DeferredBlock<MachineBlock> SOLAR_TOWER = machine("solar_tower", p -> new MachineBlock(p, RotaryBlockEntities.SOLAR_TOWER, Placement.NONE, false));
	/** Original MachineRegistry.PIPEPUMP (BlockDMMachine, 6 orientations). */
	public static final DeferredBlock<MachineBlock> PIPE_PUMP = machine("pipe_pump", p -> new MachineBlock(p, RotaryBlockEntities.PIPE_PUMP, Placement.SIX_SIDED, false));
	/** Original MachineRegistry.VANDEGRAFF (BlockMMachine, no orientation). */
	public static final DeferredBlock<MachineBlock> VAN_DE_GRAFF = machine("van_de_graff", p -> new MachineBlock(p, RotaryBlockEntities.VAN_DE_GRAFF, Placement.NONE, false));
	/** Original MachineRegistry.DRYING (BlockMIMachine). */
	public static final DeferredBlock<DryingBedBlock> DRYING_BED = machine("drying_bed", DryingBedBlock::new);
	/** Original MachineRegistry.WINDER (BlockDMIMachine, 4 orientations, GUI). */
	public static final DeferredBlock<WinderBlock> WINDER = machine("winder", WinderBlock::new);
	/** Original MachineRegistry.FILLINGSTATION (BlockDMIMachine, 4 orientations, GUI). */
	public static final DeferredBlock<FillingStationBlock> FILLING_STATION = machine("filling_station", FillingStationBlock::new);
	/** Original MachineRegistry.COMPACTOR (BlockDMIMachine, 4 orientations, GUI). */
	public static final DeferredBlock<CompactorBlock> COMPACTOR = machine("compactor", CompactorBlock::new);
	/** Original MachineRegistry.HEATRAY (BlockDMMachine, 4 orientations, z-flipped). */
	public static final DeferredBlock<HeatRayBlock> HEAT_RAY = machine("heat_ray", HeatRayBlock::new);
	/** Original MachineRegistry.REFRIGERATOR (BlockDMIMachine, 4 orientations, GUI). */
	public static final DeferredBlock<RefrigeratorBlock> REFRIGERATOR = machine("refrigerator", RefrigeratorBlock::new);
	public static final DeferredBlock<MachineBlock> DYNAMOMETER = machine("dynamometer", p -> new MachineBlock(p, RotaryBlockEntities.DYNAMOMETER, Placement.FOUR_SIDED, true));
	public static final DeferredBlock<MachineBlock> SHAFT_JUNCTION = machine("shaft_junction", p -> new MachineBlock(p, RotaryBlockEntities.SHAFT_JUNCTION, Placement.FOUR_SIDED, false));

	public static final DeferredBlock<EngineBlock> DC_ENGINE = machine("dc_engine", p -> new EngineBlock(p, RotaryBlockEntities.DC_ENGINE, EngineType.DC));
	public static final DeferredBlock<EngineBlock> WIND_ENGINE = machine("wind_engine", p -> new EngineBlock(p, RotaryBlockEntities.WIND_ENGINE, EngineType.WIND));
	public static final DeferredBlock<EngineBlock> STEAM_ENGINE = machine("steam_engine", p -> new EngineBlock(p, RotaryBlockEntities.STEAM_ENGINE, EngineType.STEAM));
	public static final DeferredBlock<EngineBlock> GAS_ENGINE = machine("gas_engine", p -> new EngineBlock(p, RotaryBlockEntities.GAS_ENGINE, EngineType.GAS));
	public static final DeferredBlock<EngineBlock> AC_ENGINE = machine("ac_engine", p -> new EngineBlock(p, RotaryBlockEntities.AC_ENGINE, EngineType.AC));
	public static final DeferredBlock<EngineBlock> SPORT_ENGINE = machine("sport_engine", p -> new EngineBlock(p, RotaryBlockEntities.SPORT_ENGINE, EngineType.SPORT));
	public static final DeferredBlock<EngineBlock> MICRO_ENGINE = machine("micro_engine", p -> new EngineBlock(p, RotaryBlockEntities.MICRO_ENGINE, EngineType.MICRO));

	public static final DeferredBlock<EngineBlock> HYDRO_ENGINE = machine("hydro_engine", p -> new EngineBlock(p, RotaryBlockEntities.HYDRO_ENGINE, EngineType.HYDRO));
	public static final DeferredBlock<EngineBlock> JET_ENGINE = machine("jet_engine", p -> new EngineBlock(p, RotaryBlockEntities.JET_ENGINE, EngineType.JET));

	/** Original: MachineRegistry.ECU (BlockMachine). */
	public static final DeferredBlock<EcuBlock> ECU = machine("ecu", EcuBlock::new);

	/** Original: MachineRegistry.BLASTFURNACE / WORKTABLE (BlockIMachine). */
	public static final DeferredBlock<BlastFurnaceBlock> BLAST_FURNACE = machine("blast_furnace", BlastFurnaceBlock::new);
	public static final DeferredBlock<WorktableBlock> WORKTABLE = machine("worktable", WorktableBlock::new);
	/** Original: MachineRegistry.FRICTION (BlockDMMachine). */
	public static final DeferredBlock<FrictionHeaterBlock> FRICTION_HEATER = machine("friction_heater", FrictionHeaterBlock::new);
	/** Original: MachineRegistry.FERMENTER (BlockIMachine) and CENTRIFUGE (BlockMIMachine). */
	public static final DeferredBlock<FermenterBlock> FERMENTER = machine("fermenter", FermenterBlock::new);
	/** Original: MachineRegistry.FAN (BlockDMMachine, 6 orientations, z-flipped). */
	public static final DeferredBlock<FanBlock> FAN = machine("fan", FanBlock::new);
	/** Original: MachineRegistry.MOBHARVESTER (BlockMMachine, no orientation). */
	public static final DeferredBlock<MachineBlock> MOB_HARVESTER = machine("mob_harvester", p -> new MachineBlock(p, RotaryBlockEntities.MOB_HARVESTER, Placement.NONE, false));
	/** Original: MachineRegistry.AGGREGATOR (BlockMMachine, no orientation). */
	public static final DeferredBlock<MachineBlock> AGGREGATOR = machine("aggregator", p -> new MachineBlock(p, RotaryBlockEntities.AGGREGATOR, Placement.NONE, false));
	/** Original: MachineRegistry.SPILLER (BlockMachine, a plain cube: steel with the spiller grate below). */
	public static final DeferredBlock<MachineBlock> SPILLER = machine("spiller", p -> new MachineBlock(p, RotaryBlockEntities.SPILLER, Placement.NONE, false));
	/** Original: MachineRegistry.FILLER (BlockMachine, a plain cube: steel with the filler grate below). */
	public static final DeferredBlock<MachineBlock> FILLER = machine("filler", p -> new MachineBlock(p, RotaryBlockEntities.FILLER, Placement.NONE, false));
	/** Original: MachineRegistry.SMOKEDETECTOR (BlockMIMachine). */
	public static final DeferredBlock<SmokeDetectorBlock> SMOKE_DETECTOR = machine("smoke_detector", SmokeDetectorBlock::new);
	public static final DeferredBlock<PlayerDetectorBlock> PLAYER_DETECTOR = machine("player_detector", PlayerDetectorBlock::new);
	public static final DeferredBlock<MobRadarBlock> MOB_RADAR = machine("mob_radar", MobRadarBlock::new);
	public static final DeferredBlock<LavaMakerBlock> LAVA_MAKER = machine("lava_maker", LavaMakerBlock::new);
	public static final DeferredBlock<HeaterBlock> HEATER = machine("heater", HeaterBlock::new);
	public static final DeferredBlock<ComposterBlock> COMPOSTER = machine("composter", ComposterBlock::new);
	public static final DeferredBlock<CrystallizerBlock> CRYSTALLIZER = machine("crystallizer", CrystallizerBlock::new);
	public static final DeferredBlock<LampBlock> LAMP = machine("lamp", LampBlock::new);
	public static final DeferredBlock<GroundHydratorBlock> HYDRATOR = machine("hydrator", GroundHydratorBlock::new);
	public static final DeferredBlock<WetterBlock> WETTER = machine("wetter", WetterBlock::new);
	public static final DeferredBlock<ParticleEmitterBlock> PARTICLE_EMITTER = machine("particle_emitter", ParticleEmitterBlock::new);
	public static final DeferredBlock<MultiClutchBlock> MULTI_CLUTCH = machine("multi_clutch", MultiClutchBlock::new);
	public static final DeferredBlock<BigFurnaceBlock> BIG_FURNACE = machine("big_furnace", BigFurnaceBlock::new);
	public static final DeferredBlock<SpillwayBlock> SPILLWAY = machine("spillway", SpillwayBlock::new);
	public static final DeferredBlock<FluidCompressorBlock> FLUID_COMPRESSOR = machine("fluid_compressor", FluidCompressorBlock::new);
	public static final DeferredBlock<LineBuilderBlock> LINE_BUILDER = machine("line_builder", LineBuilderBlock::new);
	public static final DeferredBlock<MagnetizerBlock> MAGNETIZER = machine("magnetizer", MagnetizerBlock::new);
	public static final DeferredBlock<BeamMirrorBlock> BEAM_MIRROR = machine("beam_mirror", BeamMirrorBlock::new);
	public static final DeferredBlock<ItemRefresherBlock> ITEM_REFRESHER = machine("item_refresher", ItemRefresherBlock::new);
	public static final DeferredBlock<GrindstoneBlock> GRINDSTONE = machine("grindstone", GrindstoneBlock::new);
	public static final DeferredBlock<BucketFillerBlock> BUCKET_FILLER = machine("bucket_filler", BucketFillerBlock::new);
	public static final DeferredBlock<SortingBlock> SORTING = machine("sorting", SortingBlock::new);
	public static final DeferredBlock<FertilizerBlock> FERTILIZER = machine("fertilizer", FertilizerBlock::new);
	public static final DeferredBlock<LawnSprinklerBlock> LAWN_SPRINKLER = machine("lawn_sprinkler", LawnSprinklerBlock::new);
	public static final DeferredBlock<SprinklerBlock> SPRINKLER = machine("sprinkler", SprinklerBlock::new);
	public static final DeferredBlock<IgniterBlock> IGNITER = machine("firestarter", IgniterBlock::new);
	public static final DeferredBlock<ObsidianMakerBlock> OBSIDIAN_MAKER = machine("obsidian_maker", ObsidianMakerBlock::new);
	public static final DeferredBlock<AutoBreederBlock> AUTO_BREEDER = machine("auto_breeder", AutoBreederBlock::new);
	public static final DeferredBlock<BaitBoxBlock> BAIT_BOX = machine("bait_box", BaitBoxBlock::new);
	public static final DeferredBlock<ScaleableChestBlock> SCALEABLE_CHEST = machine("scaleable_chest", ScaleableChestBlock::new);
	public static final DeferredBlock<VacuumBlock> VACUUM = machine("vacuum", VacuumBlock::new);
	public static final DeferredBlock<BlowerBlock> BLOWER = machine("blower", BlowerBlock::new);
	public static final DeferredBlock<AerosolizerBlock> AEROSOLIZER = machine("aerosolizer", AerosolizerBlock::new);
	public static final DeferredBlock<FloodlightBlock> FLOODLIGHT = machine("floodlight", FloodlightBlock::new);
	public static final DeferredBlock<WoodcutterBlock> WOODCUTTER = machine("woodcutter", WoodcutterBlock::new);
	public static final DeferredBlock<LightBridgeBlock> LIGHT_BRIDGE = machine("light_bridge", LightBridgeBlock::new);
	public static final DeferredBlock<BeltHubBlock> BELT_HUB = machine("belt_hub", BeltHubBlock::new);
	public static final DeferredBlock<ChainDriveBlock> CHAIN_DRIVE = machine("chain_drive", ChainDriveBlock::new);
	public static final DeferredBlock<SplitBeltBlock> SPLIT_BELT = machine("split_belt", SplitBeltBlock::new);
	public static final DeferredBlock<DistributionClutchBlock> DISTRIBUTION_CLUTCH = machine("distribution_clutch", DistributionClutchBlock::new);
	public static final DeferredBlock<BusControllerBlock> BUS_CONTROLLER = machine("bus_controller", BusControllerBlock::new);
	public static final DeferredBlock<PowerBusBlock> POWER_BUS = machine("power_bus", PowerBusBlock::new);
	public static final DeferredBlock<CaveScannerBlock> CAVE_SCANNER = machine("cave_scanner", CaveScannerBlock::new);
	public static final DeferredBlock<DisplayBlock> DISPLAY = machine("display", DisplayBlock::new);
	public static final DeferredBlock<ScreenBlock> SCREEN = machine("cctv_screen", ScreenBlock::new);
	public static final DeferredBlock<SpyCamBlock> SPY_CAM = machine("spy_cam", SpyCamBlock::new);
	public static final DeferredBlock<SonicBorerBlock> SONIC_BORER = machine("sonic_borer", SonicBorerBlock::new);
	public static final DeferredBlock<PileDriverBlock> PILE_DRIVER = machine("pile_driver", PileDriverBlock::new);
	public static final DeferredBlock<DefoliatorBlock> DEFOLIATOR = machine("defoliator", DefoliatorBlock::new);
	public static final DeferredBlock<WeatherControllerBlock> WEATHER_CONTROLLER = machine("weather_controller", WeatherControllerBlock::new);
	public static final DeferredBlock<ChunkLoaderBlock> CHUNK_LOADER = machine("chunk_loader", ChunkLoaderBlock::new);
	public static final DeferredBlock<ItemCannonBlock> ITEM_CANNON = machine("item_cannon", ItemCannonBlock::new);
	public static final DeferredBlock<SpawnerControllerBlock> SPAWNER_CONTROLLER = machine("spawner_controller", SpawnerControllerBlock::new);
	public static final DeferredBlock<AdvancedGearBlock> WORM_GEAR = machine("worm_gear", p -> new AdvancedGearBlock(p, AdvancedGearBlockEntity.Kind.WORM));
	public static final DeferredBlock<AdvancedGearBlock> HIGH_GEAR = machine("high_gear", p -> new AdvancedGearBlock(p, AdvancedGearBlockEntity.Kind.HIGH));
	public static final DeferredBlock<AdvancedGearBlock> INDUSTRIAL_COIL = machine("industrial_coil", p -> new AdvancedGearBlock(p, AdvancedGearBlockEntity.Kind.COIL));
	public static final DeferredBlock<AdvancedGearBlock> BEDROCK_COIL = machine("bedrock_coil", p -> new AdvancedGearBlock(p, AdvancedGearBlockEntity.Kind.BEDROCK_COIL));
	public static final DeferredBlock<AdvancedGearBlock> CVT = machine("cvt", p -> new AdvancedGearBlock(p, AdvancedGearBlockEntity.Kind.CVT));
	public static final DeferredBlock<ItemFilterBlock> ITEM_FILTER = machine("item_filter", ItemFilterBlock::new);
	public static final DeferredBlock<AutoCrafterBlock> AUTO_CRAFTER = machine("auto_crafter", AutoCrafterBlock::new);
	public static final DeferredBlock<DropProcessorBlock> DROP_PROCESSOR = machine("drop_processor", DropProcessorBlock::new);
	public static final DeferredBlock<MusicBoxBlock> MUSIC_BOX = machine("music_box", MusicBoxBlock::new);
	public static final DeferredBlock<CctvBlock> CCTV = machine("cctv", CctvBlock::new);
	public static final DeferredBlock<PortalShaftBlock> PORTAL_SHAFT = machine("portal_shaft", PortalShaftBlock::new);
	public static final DeferredBlock<TerraformerBlock> TERRAFORMER = machine("terraformer", TerraformerBlock::new);
	public static final DeferredBlock<ProjectorBlock> PROJECTOR = machine("projector", ProjectorBlock::new);
	public static final DeferredBlock<MagneticEngineBlock> MAGNETIC_ENGINE = machine("magnetic_engine", MagneticEngineBlock::new);
	public static final DeferredBlock<MachineBlock> DYNAMO = machine("dynamo", p -> new MachineBlock(p, RotaryBlockEntities.DYNAMO, MachineBlock.Placement.FOUR_SIDED, false));
	public static final DeferredBlock<PurifierBlock> PURIFIER = machine("purifier", PurifierBlock::new);
	public static final DeferredBlock<MachineBlock> FUEL_ENGINE = machine("fuel_engine", p -> new MachineBlock(p, RotaryBlockEntities.FUEL_ENGINE, MachineBlock.Placement.FOUR_SIDED, false));
	public static final DeferredBlock<MachineBlock> DISTILLERY = machine("distillery", p -> new MachineBlock(p, RotaryBlockEntities.DISTILLERY, MachineBlock.Placement.NONE, false));
	public static final DeferredBlock<MachineBlock> BOILER = machine("boiler", p -> new MachineBlock(p, RotaryBlockEntities.BOILER, MachineBlock.Placement.NONE, false));
	public static final DeferredBlock<MagneticEngineBlock> STEAM_TURBINE = machine("steam_turbine", p -> new MagneticEngineBlock(p, RotaryBlockEntities.STEAM_TURBINE));
	public static final DeferredBlock<MachineBlock> AIR_COMPRESSOR = machine("air_compressor", p -> new MachineBlock(p, RotaryBlockEntities.AIR_COMPRESSOR, MachineBlock.Placement.FOUR_SIDED, false));
	public static final DeferredBlock<MagneticEngineBlock> PNEUMATIC_ENGINE = machine("pneumatic_engine", p -> new MagneticEngineBlock(p, RotaryBlockEntities.PNEUMATIC_ENGINE));
	public static final DeferredBlock<MachineBlock> FORCE_FIELD = machine("force_field", p -> new MachineBlock(p, RotaryBlockEntities.FORCE_FIELD, MachineBlock.Placement.NONE, false));
	public static final DeferredBlock<MachineBlock> CONTAINMENT = machine("containment", p -> new MachineBlock(p, RotaryBlockEntities.CONTAINMENT, MachineBlock.Placement.NONE, false));
	public static final DeferredBlock<MachineBlock> AIR_GUN = machine("air_gun", p -> new MachineBlock(p, RotaryBlockEntities.AIR_GUN, MachineBlock.Placement.FOUR_SIDED, false));
	public static final DeferredBlock<LandmineBlock> LANDMINE = machine("landmine", LandmineBlock::new);
	public static final DeferredBlock<MachineBlock> SELF_DESTRUCT = machine("self_destruct", p -> new MachineBlock(p, RotaryBlockEntities.SELF_DESTRUCT, MachineBlock.Placement.NONE, false));
	public static final DeferredBlock<MachineBlock> SONIC_WEAPON = machine("sonic_weapon", p -> new MachineBlock(p, RotaryBlockEntities.SONIC_WEAPON, MachineBlock.Placement.NONE, false));
	public static final DeferredBlock<MachineBlock> EMP = machine("emp", p -> new MachineBlock(p, RotaryBlockEntities.EMP, MachineBlock.Placement.NONE, false));
	public static final DeferredBlock<AmmoWeaponBlock> MACHINE_GUN = machine("machine_gun", p -> new AmmoWeaponBlock(p, RotaryBlockEntities.MACHINE_GUN, MachineBlock.Placement.FOUR_SIDED));
	public static final DeferredBlock<AmmoWeaponBlock> TNT_CANNON = machine("tnt_cannon", p -> new AmmoWeaponBlock(p, RotaryBlockEntities.TNT_CANNON, MachineBlock.Placement.NONE));
	public static final DeferredBlock<AmmoWeaponBlock> BLOCK_CANNON = machine("block_cannon", p -> new AmmoWeaponBlock(p, RotaryBlockEntities.BLOCK_CANNON, MachineBlock.Placement.NONE));
	public static final DeferredBlock<AmmoWeaponBlock> RAILGUN = machine("railgun", p -> new AmmoWeaponBlock(p, RotaryBlockEntities.RAILGUN, MachineBlock.Placement.NONE));
	public static final DeferredBlock<AmmoWeaponBlock> FREEZE_GUN = machine("freeze_gun", p -> new AmmoWeaponBlock(p, RotaryBlockEntities.FREEZE_GUN, MachineBlock.Placement.NONE));
	public static final DeferredBlock<MachineBlock> LASER_GUN = machine("laser_gun", p -> new MachineBlock(p, RotaryBlockEntities.LASER_GUN, MachineBlock.Placement.NONE, false));
	public static final DeferredBlock<AmmoWeaponBlock> MULTI_CANNON = machine("multi_cannon", p -> new AmmoWeaponBlock(p, RotaryBlockEntities.MULTI_CANNON, MachineBlock.Placement.NONE));
	public static final DeferredBlock<AmmoWeaponBlock> ANTI_AIR_GUN = machine("anti_air_gun", p -> new AmmoWeaponBlock(p, RotaryBlockEntities.ANTI_AIR_GUN, MachineBlock.Placement.NONE));
	public static final DeferredBlock<MachineBlock> FLAME_TURRET = machine("flame_turret", p -> new MachineBlock(p, RotaryBlockEntities.FLAME_TURRET, MachineBlock.Placement.NONE, false));
	public static final DeferredBlock<FireworkMachineBlock> FIREWORK_MACHINE = machine("firework_machine", FireworkMachineBlock::new);
	public static final DeferredBlock<GprBlock> GPR = machine("gpr", GprBlock::new);
	/** Original: BlockRegistry.BRIDGE (BlockLightBridge, Material.portal, light 0.5, unbreakable, glass sound, no item). */
	public static final DeferredBlock<BridgeBlock> BRIDGE = BLOCKS.register("bridge", () -> new BridgeBlock(
			BlockBehaviour.Properties.of().mapColor(MapColor.NONE).strength(-1F, 3600000F).sound(SoundType.GLASS).lightLevel(st -> 7).noLootTable().noOcclusion()
			.isValidSpawn((st, l, p, e) -> false).isRedstoneConductor((st, l, p) -> false).isSuffocating((st, l, p) -> false).isViewBlocking((st, l, p) -> false)
			.pushReaction(PushReaction.BLOCK)));
	public static final DeferredBlock<CentrifugeBlock> CENTRIFUGE = machine("centrifuge", CentrifugeBlock::new);
	/** Original: MachineRegistry.FRACTIONATOR (BlockMIMachine). */
	public static final DeferredBlock<FractionatorBlock> FRACTIONATOR = machine("fractionator", FractionatorBlock::new);
	/** Original: MachineRegistry.PUMP (BlockDMMachine). */
	public static final DeferredBlock<PumpBlock> PUMP = machine("pump", PumpBlock::new);
	/** Original: MachineRegistry.RESERVOIR (BlockMachine). */
	public static final DeferredBlock<ReservoirBlock> RESERVOIR = machine("reservoir", ReservoirBlock::new);
	/** Original: MachineRegistry.PULSEJET (BlockMIMachine) and BlockRegistry.BLASTGLASS. */
	public static final DeferredBlock<PulseJetFurnaceBlock> PULSE_JET = machine("pulse_jet_furnace", PulseJetFurnaceBlock::new);
	public static final DeferredBlock<BlastGlassBlock> BLAST_GLASS = BLOCKS.register("blast_glass", () -> new BlastGlassBlock(
			BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(10F, 6000F).sound(SoundType.GLASS).requiresCorrectToolForDrops().noOcclusion()
			.isValidSpawn((s, l, p, e) -> false).isRedstoneConductor((s, l, p) -> false).isSuffocating((s, l, p) -> false).isViewBlocking((s, l, p) -> false)));
	public static final DeferredItem<BlockItem> BLAST_GLASS_ITEM = RotaryItems.ITEMS.registerSimpleBlockItem("blast_glass", BLAST_GLASS);

	/**
	 * Original: BlockRegistry.DECO (BlockDeco, metadata 0-5, RotaryNames.blockNames): HSLA steel, anthracite, lonsdaleite, borer shield,
	 * bedrock ingot and coal coke storage blocks. Material.iron (any pickaxe, metal sound), hardness 4, setResistance(10) = blast
	 * resistance 6 (the bedrock ingot block: 6000, and the wither and the dragon cannot break it - tags wither_immune/dragon_immune).
	 * Anthracite and coke burn (FlammableBlock). ReactorCraft uses the steel and bedrock ingot blocks as radiation shields
	 * (c:storage_blocks/steel, c:storage_blocks/bedrock) and the steel block in its recipes.
	 * Not ported: BlockDeco.onBlockExploded put the bedrock ingot block back after an explosion; with resistance 6000 no explosion short
	 * of power ~1800 reaches it anyway.
	 */
	public static final DeferredBlock<Block> STEEL_BLOCK = deco("steel_block", decoProperties(LegacyResistance.of(10F)));
	public static final DeferredBlock<FlammableBlock> ANTHRACITE_BLOCK = flammableDeco("anthracite_block", 30, 4);
	public static final DeferredBlock<Block> LONSDALEITE_BLOCK = deco("lonsdaleite_block", decoProperties(LegacyResistance.of(10F)));
	public static final DeferredBlock<Block> SHIELD_BLOCK = deco("shield_block", decoProperties(LegacyResistance.of(10F)));
	public static final DeferredBlock<Block> BEDROCK_INGOT_BLOCK = deco("bedrock_ingot_block", decoProperties(6000F));
	public static final DeferredBlock<FlammableBlock> COKE_BLOCK = flammableDeco("coke_block", 20, 3);
	/** Original: MachineRegistry.GRINDER (BlockDMIMachine). */
	public static final DeferredBlock<GrinderBlock> GRINDER = machine("grinder", GrinderBlock::new);
	/** Original: MachineRegistry.EXTRACTOR (BlockMIMachine). */
	public static final DeferredBlock<ExtractorBlock> EXTRACTOR = machine("extractor", ExtractorBlock::new);
	/** Original: MachineRegistry.BEDROCKBREAKER (BlockDMMachine) and BlockRegistry.BEDROCKSLICE (no item). */
	public static final DeferredBlock<BedrockBreakerBlock> BEDROCK_BREAKER = machine("bedrock_breaker", BedrockBreakerBlock::new);
	public static final DeferredBlock<BedrockSliceBlock> BEDROCK_SLICE = BLOCKS.register("bedrock_slice", () -> new BedrockSliceBlock(
			BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(-1F, 3600000F).noLootTable().noOcclusion().isValidSpawn((s, l, p, e) -> false)
			.pushReaction(PushReaction.BLOCK)));
	/** Original: MachineRegistry.BORER (BlockDMachine) and BlockRegistry.MININGPIPE (Material.iron, hardness 0, resistance 0, no item). */
	public static final DeferredBlock<BorerBlock> BORER = machine("borer", BorerBlock::new);
	public static final DeferredBlock<MiningPipeBlock> MINING_PIPE = BLOCKS.register("mining_pipe", () -> new MiningPipeBlock(
			BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(0F, 0F).sound(SoundType.METAL).noLootTable().noOcclusion().isValidSpawn((s, l, p, e) -> false)));

	/** Original: BlockRegistry.CANOLA (BlockCanola, Material.plants, grass sound, no item: planted from the seeds). */
	public static final DeferredBlock<CanolaBlock> CANOLA = BLOCKS.register("canola", () -> new CanolaBlock(
			BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().randomTicks().sound(SoundType.GRASS)
			.pushReaction(PushReaction.DESTROY)));

	/** Original: BlockPiping (MachineRegistry.HOSE, PIPE, FUELLINE, BEDPIPE): Material.iron (metal sound, also the hose), hardness from the config, setResistance(1). */
	public static final DeferredBlock<PipingBlock> HOSE = pipe("hose", RotaryBlockEntities.HOSE, true);
	public static final DeferredBlock<PipingBlock> PIPE = pipe("pipe", RotaryBlockEntities.PIPE, false);
	public static final DeferredBlock<PipingBlock> FUEL_LINE = pipe("fuel_line", RotaryBlockEntities.FUEL_LINE, false);
	public static final DeferredBlock<PipingBlock> BEDROCK_PIPE = pipe("bedrock_pipe", RotaryBlockEntities.BEDROCK_PIPE, false);
	public static final DeferredBlock<PipingBlock> VALVE = pipe("valve", RotaryBlockEntities.VALVE, false);
	public static final DeferredBlock<PipingBlock> BYPASS = pipe("bypass", RotaryBlockEntities.BYPASS, false);
	public static final DeferredBlock<PipingBlock> SEPARATION = pipe("separation", RotaryBlockEntities.SEPARATION, false);
	public static final DeferredBlock<PipingBlock> SUCTION = pipe("suction", RotaryBlockEntities.SUCTION, false);

	private RotaryBlocks() {}

	private static DeferredBlock<PipingBlock> pipe(String name, Supplier<? extends BlockEntityType<? extends RotaryCraftBlockEntity>> type,
			boolean flammable) {
		return machine(name, p -> new PipingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(0F, LegacyResistance.of(1F)).sound(SoundType.METAL).noOcclusion()
				.dynamicShape(), type, flammable));
	}

	private static BlockBehaviour.Properties decoProperties(float blastResistance) {
		return BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(4F, blastResistance).sound(SoundType.METAL).requiresCorrectToolForDrops();
	}

	private static DeferredBlock<Block> deco(String name, BlockBehaviour.Properties props) {
		DeferredBlock<Block> block = BLOCKS.registerSimpleBlock(name, props);
		RotaryItems.ITEMS.registerSimpleBlockItem(name, block);
		return block;
	}

	private static DeferredBlock<FlammableBlock> flammableDeco(String name, int flammability, int spreadSpeed) {
		DeferredBlock<FlammableBlock> block = BLOCKS.register(name, () -> new FlammableBlock(decoProperties(LegacyResistance.of(10F)), flammability, spreadSpeed));
		RotaryItems.ITEMS.registerSimpleBlockItem(name, block);
		return block;
	}

	private static <B extends Block> DeferredBlock<B> machine(String name, Function<BlockBehaviour.Properties, B> factory) {
		DeferredBlock<B> block = BLOCKS.register(name, () -> factory.apply(machineProperties()));
		RotaryItems.ITEMS.register(name, () -> new MachineBlockItem(block.get(), new Item.Properties()));
		return block;
	}
}
