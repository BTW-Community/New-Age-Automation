package net.minecraft.src;

import java.util.List;

public class AutoPlusTileEntityLoom extends TileEntity implements IInventory {
    public ItemStack stackWeaving = null;
    private final double maxPlayerInteractionDistSq = 64.0D;
    private final int timeToWeave = 200;
    private boolean validateContentsOnUpdate = true;
    private boolean containsIngrediantsToWeave;
    public int weaveCounter = 0;
    
    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        
        NBTTagCompound var6 = var1.getCompoundTag("stackWeaving");

        if (var6 != null)
        {
            this.stackWeaving = ItemStack.loadItemStackFromNBT(var6);
        }

        if (var1.hasKey("grindCounter"))
        {
            this.weaveCounter = var1.getInteger("weaveCounter");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);

        if (this.stackWeaving != null)
        {
            NBTTagCompound var5 = new NBTTagCompound();
            this.stackWeaving.writeToNBT(var5);
            var1.setCompoundTag("stackWeaving", var5);
        }

        var1.setInteger("weaveCounter", this.weaveCounter);
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();

        if (!this.worldObj.isRemote)
        {
            int var1 = this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord);
            AutoPlusBlockLoom var2 = (AutoPlusBlockLoom)Block.blocksList[var1];

            if (this.validateContentsOnUpdate)
            {
                this.validateContentsForWeaving(var2);
            }

            if (this.containsIngrediantsToWeave && var2.GetIsMechanicalOn(this.worldObj, this.xCoord, this.yCoord, this.zCoord))
            {
                ++this.weaveCounter;

                if (this.weaveCounter >= timeToWeave)
                {
                    this.weaveContents(var2);
                    this.weaveCounter = 0;
                    this.validateContentsOnUpdate = true;
                }
            }
        }
    }

    private boolean weaveContents(AutoPlusBlockLoom var1)
    {
        if (this.stackWeaving != null && FCCraftingManagerMillStone.getInstance().HasRecipeForSingleIngredient(this.stackWeaving))
        {
            List var2 = FCCraftingManagerMillStone.getInstance().GetCraftingResult(this.stackWeaving);

            if (var2 != null)
            {
                if (this.stackWeaving.itemID == FCBetterThanWolves.fcCompanionCube.blockID && this.stackWeaving.getItemDamage() == 0)
                {
                    this.worldObj.playAuxSFX(2242, this.xCoord, this.yCoord, this.zCoord, 0);
                }

                for (int var3 = 0; var3 < var2.size(); ++var3)
                {
                    ItemStack var4 = ((ItemStack)var2.get(var3)).copy();

                    if (var4 != null)
                    {
                        FCUtilsItem.EjectStackFromBlockTowardsFacing(this.worldObj, this.xCoord, this.yCoord, this.zCoord, var4, var1.GetFacing(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord)));
                    }
                }

                this.stackWeaving = null;
                return true;
            }
        }

        return false;
    }

    private void validateContentsForWeaving(AutoPlusBlockLoom var1)
    {
    	if (this.stackWeaving != null)
        {
            if (AutoPlusCraftingManagerLoom.getInstance().HasRecipeForSingleIngredient(this.stackWeaving))
            {
                this.containsIngrediantsToWeave = true;
                var1.setFull(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord), true);
            }
            else
            {
                var1.setFull(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord), false);
                this.weaveCounter = 0;
                this.containsIngrediantsToWeave = false;
            }
        }
        else
        {
            this.weaveCounter = 0;
            this.containsIngrediantsToWeave = false;
        }

        this.validateContentsOnUpdate = false;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 1;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int var1)
    {
        return var1 == 0 ? this.stackWeaving : null;
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int var1, int var2)
    {
        return FCUtilsInventory.DecrStackSize(this, var1, var2);
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        if (var1 == 0 && this.stackWeaving != null)
        {
            ItemStack var2 = this.stackWeaving;
            this.stackWeaving = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int var1, ItemStack var2)
    {
        if (var1 == 0)
        {
            this.stackWeaving = var2;

            if (var2 != null && var2.stackSize > this.getInventoryStackLimit())
            {
                var2.stackSize = this.getInventoryStackLimit();
            }

            this.onInventoryChanged();
        }
    }

	@Override
	public String getInvName() {
		return "container.autoPlusLoom";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
    public boolean isUseableByPlayer(EntityPlayer var1)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this ? var1.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= maxPlayerInteractionDistSq : false;
    }

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

    public void attemptToAddItemsFromStack(ItemStack stack)
    {
        if (this.stackWeaving == null)
        {
            this.stackWeaving = stack.copy();
            stack.stackSize = 0;
            this.onInventoryChanged();
        }
        else if (this.stackWeaving.itemID == stack.itemID) {
        	if (this.stackWeaving.stackSize + stack.stackSize <= stack.getItem().maxStackSize) {
        		this.stackWeaving.stackSize += stack.stackSize;
                stack.stackSize = 0;
                this.onInventoryChanged();
        	}
        	else {
        		stack.stackSize -= (stack.getItem().maxStackSize - this.stackWeaving.stackSize);
        		this.stackWeaving.stackSize = stack.getItem().maxStackSize;
                this.onInventoryChanged();
        	}
        }
    }

	@Override
	public boolean isStackValidForSlot(int var1, ItemStack var2) {
		// TODO Auto-generated method stub
		return false;
	}
}