
package com.littleforge.multitile.strucutres;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.creativemd.littletiles.common.action.block.LittleActionPlaceStack;
import com.creativemd.littletiles.common.structure.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.structure.registry.LittleStructureType;
import com.creativemd.littletiles.common.tile.math.vec.LittleVec;
import com.creativemd.littletiles.common.tile.math.vec.LittleVecContext;
import com.creativemd.littletiles.common.tile.place.PlacePreview;
import com.creativemd.littletiles.common.tile.place.PlacePreviews;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.place.PlacementMode;
import com.creativemd.littletiles.common.util.vec.SurroundingBox;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class MultiTileClayForge extends LittleStructurePremade {
	
	private int seriesIndex = 13;
	private String seriesName = type.id.toString().split("_")[0];
	private int tick = 0;
	private boolean hasFuel = false;
	private String test;
	
	public MultiTileClayForge(LittleStructureType type) {
		super(type);
	}
	
	@Override
	protected void loadFromNBTExtra(NBTTagCompound nbt) {
		tick = nbt.getInteger("tick");
	}
	
	@Override
	protected void writeToNBTExtra(NBTTagCompound nbt) {
		nbt.setInteger("tick", tick);
	}
	
	private String nextSeries() {
		int seriesAt = Integer.parseInt(type.id.toString().split("_")[1]);
		if (seriesIndex > seriesAt) {
			return seriesName + "_" + (seriesAt + 1);
		}
		return "";
	}
	
	@Override
	public void tick() {
		if (getWorld().isRemote)
			return;
		
		tick++;
		System.out.println(tick + " " + this.getMainTile().getBlockPos());
		if (tick >= 20) {
			
			tick = 0;
			
			SurroundingBox box = new SurroundingBox(false).add(tiles.entrySet());
			long minX = box.getMinX();
			long minY = box.getMinY();
			long minZ = box.getMinZ();
			LittleGridContext context = box.getContext();
			BlockPos min = new BlockPos(context.toBlockOffset(minX), context.toBlockOffset(minY), context.toBlockOffset(minZ));
			
			LittleVecContext minVec = new LittleVecContext(new LittleVec((int) (minX - (long) min.getX() * (long) context.size), (int) (minY - (long) min.getY() * (long) context.size), (int) (minZ - (long) min.getZ() * (long) context.size)), context);
			
			LittlePreviews previews = getStructurePremadeEntry(nextSeries()).previews.copy(); // Change this line to support different states
			LittleVec previewMinVec = previews.getMinVec();
			minVec.forceContext(previews);
			
			for (LittlePreview preview : previews) {
				preview.box.sub(previewMinVec);
				preview.box.add(minVec.getVec());
			}
			
			previews.convertToSmallest();
			
			List<PlacePreview> placePreviews = new ArrayList<>();
			previews.getPlacePreviews(placePreviews, null, true, LittleVec.ZERO);
			
			HashMap<BlockPos, PlacePreviews> splitted = LittleActionPlaceStack.getSplittedTiles(previews.context, placePreviews, min);
			
			
			this.removeStructure();
			// Places new structure
			LittleActionPlaceStack.placeTilesWithoutPlayer(this.getWorld(), previews.context, splitted, previews.getStructure(), PlacementMode.all, min, null, null, null, null);
			
			//System.out.println("10 seconds "+this.getMainTile().getBlockPos());
		}
	}
	
}
