/*******************************************************************************
 * Own addition to the private NeoForge 1.21.1 RotaryCraft port (not in the original); not for distribution.
 ******************************************************************************/
package reika.rotarycraft.compat.chisel;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import reika.rotarycraft.RotaryCraft;
import reika.rotarycraft.registry.RotaryCreativeTabs;

/** The bedrock chisel for Chisels &amp; Bits, registered only when that mod is loaded (see OptionalMods.chiselsAndBits()). */
public final class ChiselCompat {

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RotaryCraft.MOD_ID);

	public static final DeferredItem<BedrockBitChiselItem> BEDROCK_BIT_CHISEL = ITEMS.register("bedrock_bit_chisel",
			() -> new BedrockBitChiselItem(new Item.Properties().stacksTo(1)));

	private ChiselCompat() {}

	public static void register(IEventBus modBus) {
		ITEMS.register(modBus);
		modBus.addListener(ChiselCompat::creativeTab);
	}

	private static void creativeTab(BuildCreativeModeTabContentsEvent e) {
		if (e.getTab() == RotaryCreativeTabs.TOOLS.get())
			e.accept(BEDROCK_BIT_CHISEL.get());
	}
}
