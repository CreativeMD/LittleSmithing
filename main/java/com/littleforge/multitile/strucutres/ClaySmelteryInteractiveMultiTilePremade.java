package com.littleforge.multitile.strucutres;

import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.parent.IStructureTileList;
import com.littleforge.common.strucutres.type.premade.interactive.InteractivePremade;

import net.minecraft.item.ItemStack;

public class ClaySmelteryInteractiveMultiTilePremade extends InteractivePremade {
	
	public ClaySmelteryInteractiveMultiTilePremade(LittleStructureType type, IStructureTileList mainBlock) {
		super(type, mainBlock);
	}
	
	@Override
	public void onPremadeActivated(ItemStack heldItem) {
		// TODO Auto-generated method stub
		
	}
	
}
