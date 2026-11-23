/*******************************************************************************
 * Own addition to the private NeoForge 1.21.1 RotaryCraft port (not in the original); not for distribution.
 ******************************************************************************/
package reika.rotarycraft.compat.chisel;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.Unbreakable;

import mod.chiselsandbits.item.ChiselItem;

/**
 * Chisels &amp; Bits: a bedrock chisel that never wears out. It works like the netherite chisel (same tier, so the same speed and modes),
 * but carries the vanilla unbreakable component and takes no damage while chiseling.
 */
public class BedrockBitChiselItem extends ChiselItem {

	public BedrockBitChiselItem(Properties props) {
		super(Tiers.NETHERITE, props.fireResistant());
	}

	@Override
	public boolean isDamageableDuringChiseling() {
		return false;
	}

	@Override
	public DataComponentMap components() {
		return DataComponentMap.builder().addAll(super.components()).set(DataComponents.UNBREAKABLE, new Unbreakable(true)).build();
	}
}
