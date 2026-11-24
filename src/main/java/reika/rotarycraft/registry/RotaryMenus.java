/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.registry;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import reika.rotarycraft.RotaryCraft;
import reika.rotarycraft.content.menu.AerosolizerMenu;
import reika.rotarycraft.content.menu.AmmoMenu;
import reika.rotarycraft.content.menu.AutoBreederMenu;
import reika.rotarycraft.content.menu.AutoCrafterMenu;
import reika.rotarycraft.content.menu.BaitBoxMenu;
import reika.rotarycraft.content.menu.BigFurnaceMenu;
import reika.rotarycraft.content.menu.BlastFurnaceMenu;
import reika.rotarycraft.content.menu.BlowerMenu;
import reika.rotarycraft.content.menu.BorerMenu;
import reika.rotarycraft.content.menu.BucketFillerMenu;
import reika.rotarycraft.content.menu.CctvMenu;
import reika.rotarycraft.content.menu.CentrifugeMenu;
import reika.rotarycraft.content.menu.CoilMenu;
import reika.rotarycraft.content.menu.CompactorMenu;
import reika.rotarycraft.content.menu.ComposterMenu;
import reika.rotarycraft.content.menu.CraftPatternMenu;
import reika.rotarycraft.content.menu.CrystallizerMenu;
import reika.rotarycraft.content.menu.CvtMenu;
import reika.rotarycraft.content.menu.DefoliatorMenu;
import reika.rotarycraft.content.menu.DistributionClutchMenu;
import reika.rotarycraft.content.menu.DropProcessorMenu;
import reika.rotarycraft.content.menu.DryingBedMenu;
import reika.rotarycraft.content.menu.ExtractorMenu;
import reika.rotarycraft.content.menu.FermenterMenu;
import reika.rotarycraft.content.menu.FertilizerMenu;
import reika.rotarycraft.content.menu.FillingStationMenu;
import reika.rotarycraft.content.menu.FireworkMachineMenu;
import reika.rotarycraft.content.menu.FractionatorMenu;
import reika.rotarycraft.content.menu.GprMenu;
import reika.rotarycraft.content.menu.GrinderMenu;
import reika.rotarycraft.content.menu.HeaterMenu;
import reika.rotarycraft.content.menu.IgniterMenu;
import reika.rotarycraft.content.menu.ItemCannonMenu;
import reika.rotarycraft.content.menu.ItemFilterMenu;
import reika.rotarycraft.content.menu.LandmineMenu;
import reika.rotarycraft.content.menu.LavaMakerMenu;
import reika.rotarycraft.content.menu.LineBuilderMenu;
import reika.rotarycraft.content.menu.MachineMenu;
import reika.rotarycraft.content.menu.MagneticEngineMenu;
import reika.rotarycraft.content.menu.MobRadarMenu;
import reika.rotarycraft.content.menu.MultiClutchMenu;
import reika.rotarycraft.content.menu.MusicBoxMenu;
import reika.rotarycraft.content.menu.ObsidianMakerMenu;
import reika.rotarycraft.content.menu.ParticleEmitterMenu;
import reika.rotarycraft.content.menu.PlayerDetectorMenu;
import reika.rotarycraft.content.menu.PowerBusMenu;
import reika.rotarycraft.content.menu.ProjectorMenu;
import reika.rotarycraft.content.menu.PulseJetFurnaceMenu;
import reika.rotarycraft.content.menu.PurifierMenu;
import reika.rotarycraft.content.menu.RefrigeratorMenu;
import reika.rotarycraft.content.menu.ScaleableChestMenu;
import reika.rotarycraft.content.menu.ScreenMenu;
import reika.rotarycraft.content.menu.SortingMenu;
import reika.rotarycraft.content.menu.SpawnerControllerMenu;
import reika.rotarycraft.content.menu.SpyCamMenu;
import reika.rotarycraft.content.menu.SpyCamViewMenu;
import reika.rotarycraft.content.menu.TerraformerMenu;
import reika.rotarycraft.content.menu.VacuumMenu;
import reika.rotarycraft.content.menu.WeatherControllerMenu;
import reika.rotarycraft.content.menu.WinderMenu;
import reika.rotarycraft.content.menu.WorktableMenu;
import reika.rotarycraft.content.menu.MachineMenu.Layout;

/** Original: GuiHandler / GuiRegistry.MACHINE - one menu type per container layout. */
public final class RotaryMenus {

	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, RotaryCraft.MOD_ID);

	private static final Map<Layout, Supplier<MenuType<MachineMenu>>> TYPES = new EnumMap<>(Layout.class);

	static {
		for (Layout l : Layout.values()) {
			Supplier<MenuType<MachineMenu>>[] self = new Supplier[1];
			self[0] = MENUS.register(l.name().toLowerCase(Locale.ROOT), () -> IMenuTypeExtension.create((id, inv, buf) -> MachineMenu.fromNetwork(self[0].get(), id, inv, l, buf)));
			TYPES.put(l, self[0]);
		}
	}

	public static final Supplier<MenuType<BlastFurnaceMenu>> BLAST_FURNACE = MENUS.register("blast_furnace", () -> IMenuTypeExtension.create(BlastFurnaceMenu::fromNetwork));
	public static final Supplier<MenuType<GrinderMenu>> GRINDER = MENUS.register("grinder", () -> IMenuTypeExtension.create(GrinderMenu::fromNetwork));
	public static final Supplier<MenuType<WinderMenu>> WINDER = MENUS.register("winder", () -> IMenuTypeExtension.create(WinderMenu::fromNetwork));
	public static final Supplier<MenuType<FillingStationMenu>> FILLING_STATION = MENUS.register("filling_station", () -> IMenuTypeExtension.create(FillingStationMenu::fromNetwork));
	public static final Supplier<MenuType<CompactorMenu>> COMPACTOR = MENUS.register("compactor", () -> IMenuTypeExtension.create(CompactorMenu::fromNetwork));
	public static final Supplier<MenuType<RefrigeratorMenu>> REFRIGERATOR = MENUS.register("refrigerator", () -> IMenuTypeExtension.create(RefrigeratorMenu::fromNetwork));
	public static final Supplier<MenuType<DryingBedMenu>> DRYING_BED = MENUS.register("drying_bed", () -> IMenuTypeExtension.create(DryingBedMenu::fromNetwork));
	public static final Supplier<MenuType<FermenterMenu>> FERMENTER = MENUS.register("fermenter", () -> IMenuTypeExtension.create(FermenterMenu::fromNetwork));
	public static final Supplier<MenuType<CentrifugeMenu>> CENTRIFUGE = MENUS.register("centrifuge", () -> IMenuTypeExtension.create(CentrifugeMenu::fromNetwork));
	public static final Supplier<MenuType<FractionatorMenu>> FRACTIONATOR = MENUS.register("fractionator", () -> IMenuTypeExtension.create(FractionatorMenu::fromNetwork));
	public static final Supplier<MenuType<PulseJetFurnaceMenu>> PULSE_JET = MENUS.register("pulse_jet_furnace", () -> IMenuTypeExtension.create(PulseJetFurnaceMenu::fromNetwork));
	public static final Supplier<MenuType<ExtractorMenu>> EXTRACTOR = MENUS.register("extractor", () -> IMenuTypeExtension.create(ExtractorMenu::fromNetwork));
	public static final Supplier<MenuType<BorerMenu>> BORER = MENUS.register("borer", () -> IMenuTypeExtension.create(BorerMenu::fromNetwork));
	public static final Supplier<MenuType<PlayerDetectorMenu>> PLAYER_DETECTOR = MENUS.register("player_detector", () -> IMenuTypeExtension.create(PlayerDetectorMenu::fromNetwork));
	public static final Supplier<MenuType<MobRadarMenu>> MOB_RADAR = MENUS.register("mob_radar", () -> IMenuTypeExtension.create(MobRadarMenu::fromNetwork));
	public static final Supplier<MenuType<LavaMakerMenu>> LAVA_MAKER = MENUS.register("lava_maker", () -> IMenuTypeExtension.create(LavaMakerMenu::fromNetwork));
	public static final Supplier<MenuType<DefoliatorMenu>> DEFOLIATOR = MENUS.register("defoliator", () -> IMenuTypeExtension.create(DefoliatorMenu::fromNetwork));
	public static final Supplier<MenuType<WeatherControllerMenu>> WEATHER_CONTROLLER = MENUS.register("weather_controller", () -> IMenuTypeExtension.create(WeatherControllerMenu::fromNetwork));
	public static final Supplier<MenuType<ItemCannonMenu>> ITEM_CANNON = MENUS.register("item_cannon", () -> IMenuTypeExtension.create(ItemCannonMenu::fromNetwork));
	public static final Supplier<MenuType<SpawnerControllerMenu>> SPAWNER_CONTROLLER = MENUS.register("spawner_controller", () -> IMenuTypeExtension.create(SpawnerControllerMenu::fromNetwork));
	public static final Supplier<MenuType<CoilMenu>> COIL = MENUS.register("coil", () -> IMenuTypeExtension.create(CoilMenu::fromNetwork));
	public static final Supplier<MenuType<CvtMenu>> CVT = MENUS.register("cvt", () -> IMenuTypeExtension.create(CvtMenu::fromNetwork));
	public static final Supplier<MenuType<ItemFilterMenu>> ITEM_FILTER = MENUS.register("item_filter", () -> IMenuTypeExtension.create(ItemFilterMenu::fromNetwork));
	public static final Supplier<MenuType<AutoCrafterMenu>> AUTO_CRAFTER = MENUS.register("auto_crafter", () -> IMenuTypeExtension.create(AutoCrafterMenu::fromNetwork));
	public static final Supplier<MenuType<DropProcessorMenu>> DROP_PROCESSOR = MENUS.register("drop_processor", () -> IMenuTypeExtension.create(DropProcessorMenu::fromNetwork));
	public static final Supplier<MenuType<MusicBoxMenu>> MUSIC_BOX = MENUS.register("music_box", () -> IMenuTypeExtension.create(MusicBoxMenu::fromNetwork));
	public static final Supplier<MenuType<CctvMenu>> CCTV = MENUS.register("cctv", () -> IMenuTypeExtension.create(CctvMenu::fromNetwork));
	public static final Supplier<MenuType<TerraformerMenu>> TERRAFORMER = MENUS.register("terraformer", () -> IMenuTypeExtension.create(TerraformerMenu::fromNetwork));
	public static final Supplier<MenuType<ProjectorMenu>> PROJECTOR = MENUS.register("projector", () -> IMenuTypeExtension.create(ProjectorMenu::fromNetwork));
	public static final Supplier<MenuType<MagneticEngineMenu>> MAGNETIC_ENGINE = MENUS.register("magnetic_engine", () -> IMenuTypeExtension.create(MagneticEngineMenu::fromNetwork));
	public static final Supplier<MenuType<PurifierMenu>> PURIFIER = MENUS.register("purifier", () -> IMenuTypeExtension.create(PurifierMenu::fromNetwork));
	public static final Supplier<MenuType<LandmineMenu>> LANDMINE = MENUS.register("landmine", () -> IMenuTypeExtension.create(LandmineMenu::fromNetwork));
	public static final Supplier<MenuType<AmmoMenu>> AMMO = MENUS.register("ammo", () -> IMenuTypeExtension.create(AmmoMenu::fromNetwork));
	public static final Supplier<MenuType<CraftPatternMenu>> CRAFT_PATTERN = MENUS.register("craft_pattern", () -> IMenuTypeExtension.create(CraftPatternMenu::fromNetwork));
	public static final Supplier<MenuType<FireworkMachineMenu>> FIREWORK_MACHINE = MENUS.register("firework_machine", () -> IMenuTypeExtension.create(FireworkMachineMenu::fromNetwork));
	public static final Supplier<MenuType<SpyCamMenu>> SPY_CAM = MENUS.register("spy_cam", () -> IMenuTypeExtension.create(SpyCamMenu::fromNetwork));
	public static final Supplier<MenuType<SpyCamViewMenu>> SPY_CAM_VIEW = MENUS.register("spy_cam_view", () -> IMenuTypeExtension.create(SpyCamViewMenu::fromNetwork));
	public static final Supplier<MenuType<ScreenMenu>> SCREEN = MENUS.register("cctv_screen", () -> IMenuTypeExtension.create(ScreenMenu::fromNetwork));
	public static final Supplier<MenuType<GprMenu>> GPR = MENUS.register("gpr", () -> IMenuTypeExtension.create(GprMenu::fromNetwork));
	public static final Supplier<MenuType<PowerBusMenu>> POWER_BUS = MENUS.register("power_bus", () -> IMenuTypeExtension.create(PowerBusMenu::fromNetwork));
	public static final Supplier<MenuType<DistributionClutchMenu>> DISTRIBUTION_CLUTCH = MENUS.register("distribution_clutch", () -> IMenuTypeExtension.create(DistributionClutchMenu::fromNetwork));
	public static final Supplier<MenuType<AerosolizerMenu>> AEROSOLIZER = MENUS.register("aerosolizer", () -> IMenuTypeExtension.create(AerosolizerMenu::fromNetwork));
	public static final Supplier<MenuType<BlowerMenu>> BLOWER = MENUS.register("blower", () -> IMenuTypeExtension.create(BlowerMenu::fromNetwork));
	public static final Supplier<MenuType<VacuumMenu>> VACUUM = MENUS.register("vacuum", () -> IMenuTypeExtension.create(VacuumMenu::fromNetwork));
	public static final Supplier<MenuType<ScaleableChestMenu>> SCALEABLE_CHEST = MENUS.register("scaleable_chest", () -> IMenuTypeExtension.create(ScaleableChestMenu::fromNetwork));
	public static final Supplier<MenuType<BaitBoxMenu>> BAIT_BOX = MENUS.register("bait_box", () -> IMenuTypeExtension.create(BaitBoxMenu::fromNetwork));
	public static final Supplier<MenuType<AutoBreederMenu>> AUTO_BREEDER = MENUS.register("auto_breeder", () -> IMenuTypeExtension.create(AutoBreederMenu::fromNetwork));
	public static final Supplier<MenuType<ObsidianMakerMenu>> OBSIDIAN_MAKER = MENUS.register("obsidian_maker", () -> IMenuTypeExtension.create(ObsidianMakerMenu::fromNetwork));
	public static final Supplier<MenuType<IgniterMenu>> IGNITER = MENUS.register("firestarter", () -> IMenuTypeExtension.create(IgniterMenu::fromNetwork));
	public static final Supplier<MenuType<FertilizerMenu>> FERTILIZER = MENUS.register("fertilizer", () -> IMenuTypeExtension.create(FertilizerMenu::fromNetwork));
	public static final Supplier<MenuType<SortingMenu>> SORTING = MENUS.register("sorting", () -> IMenuTypeExtension.create(SortingMenu::fromNetwork));
	public static final Supplier<MenuType<BucketFillerMenu>> BUCKET_FILLER = MENUS.register("bucket_filler", () -> IMenuTypeExtension.create(BucketFillerMenu::fromNetwork));
	public static final Supplier<MenuType<LineBuilderMenu>> LINE_BUILDER = MENUS.register("line_builder", () -> IMenuTypeExtension.create(LineBuilderMenu::fromNetwork));
	public static final Supplier<MenuType<BigFurnaceMenu>> BIG_FURNACE = MENUS.register("big_furnace", () -> IMenuTypeExtension.create(BigFurnaceMenu::fromNetwork));
	public static final Supplier<MenuType<MultiClutchMenu>> MULTI_CLUTCH = MENUS.register("multi_clutch", () -> IMenuTypeExtension.create(MultiClutchMenu::fromNetwork));
	public static final Supplier<MenuType<ParticleEmitterMenu>> PARTICLE_EMITTER = MENUS.register("particle_emitter", () -> IMenuTypeExtension.create(ParticleEmitterMenu::fromNetwork));
	public static final Supplier<MenuType<CrystallizerMenu>> CRYSTALLIZER = MENUS.register("crystallizer", () -> IMenuTypeExtension.create(CrystallizerMenu::fromNetwork));
	public static final Supplier<MenuType<ComposterMenu>> COMPOSTER = MENUS.register("composter", () -> IMenuTypeExtension.create(ComposterMenu::fromNetwork));
	public static final Supplier<MenuType<HeaterMenu>> HEATER = MENUS.register("heater", () -> IMenuTypeExtension.create(HeaterMenu::fromNetwork));
	public static final Supplier<MenuType<WorktableMenu>> WORKTABLE = MENUS.register("worktable", () -> IMenuTypeExtension.create(WorktableMenu::fromNetwork));

	private RotaryMenus() {}

	public static MenuType<MachineMenu> get(Layout l) {
		return TYPES.get(l).get();
	}
}
