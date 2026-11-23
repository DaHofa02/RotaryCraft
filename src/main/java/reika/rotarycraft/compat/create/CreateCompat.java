/*******************************************************************************
 * Own addition to the private NeoForge 1.21.1 RotaryCraft port (not in the original); not for distribution.
 ******************************************************************************/
package reika.rotarycraft.compat.create;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import reika.rotarycraft.RotaryCraft;
import reika.rotarycraft.registry.RotaryCreativeTabs;

/**
 * Create compatibility: two converters between RotaryCraft shaft power and Create's rotation and stress units (SU), registered only when
 * Create is loaded (see {@link reika.rotarycraft.compat.OptionalMods#create()}; nothing here may be touched otherwise). Ratio, loss and
 * speed mapping are in the config section conversion.create.
 */
public final class CreateCompat {

	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RotaryCraft.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RotaryCraft.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, RotaryCraft.MOD_ID);

	private static BlockBehaviour.Properties props() {
		return BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(4F, 10F).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion();
	}

	public static final DeferredBlock<StressConverterBlock> ROTARY_TO_CREATE = BLOCKS.register("rotary_to_create",
			() -> new StressConverterBlock(props(), true));
	public static final DeferredBlock<StressConverterBlock> CREATE_TO_ROTARY = BLOCKS.register("create_to_rotary",
			() -> new StressConverterBlock(props(), false));

	public static final DeferredHolder<Item, BlockItem> ROTARY_TO_CREATE_ITEM = ITEMS.registerSimpleBlockItem(ROTARY_TO_CREATE);
	public static final DeferredHolder<Item, BlockItem> CREATE_TO_ROTARY_ITEM = ITEMS.registerSimpleBlockItem(CREATE_TO_ROTARY);

	@SuppressWarnings("DataFlowIssue")
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RotaryToCreateBlockEntity>> ROTARY_TO_CREATE_BE = TYPES.register("rotary_to_create",
			() -> BlockEntityType.Builder.of(RotaryToCreateBlockEntity::new, ROTARY_TO_CREATE.get()).build(null));
	@SuppressWarnings("DataFlowIssue")
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CreateToRotaryBlockEntity>> CREATE_TO_ROTARY_BE = TYPES.register("create_to_rotary",
			() -> BlockEntityType.Builder.of(CreateToRotaryBlockEntity::new, CREATE_TO_ROTARY.get()).build(null));

	private CreateCompat() {}

	public static void register(IEventBus modBus) {
		BLOCKS.register(modBus);
		ITEMS.register(modBus);
		TYPES.register(modBus);
		modBus.addListener(CreateCompat::creativeTab);
	}

	private static void creativeTab(BuildCreativeModeTabContentsEvent e) {
		if (e.getTab() == RotaryCreativeTabs.POWER.get()) {
			e.accept(ROTARY_TO_CREATE_ITEM.get());
			e.accept(CREATE_TO_ROTARY_ITEM.get());
		}
	}
}
