/*
 * MIT License
 *
 * Copyright (c) 2020 Azercoco & Technici4n
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package aztech.modern_industrialization.machines.blockentities;

import aztech.modern_industrialization.machines.BEP;
import aztech.modern_industrialization.machines.components.GunpowderOverclockComponent;
import aztech.modern_industrialization.machines.components.MachineInventoryComponent;
import aztech.modern_industrialization.machines.components.sync.GunpowderOverclockGui;
import aztech.modern_industrialization.machines.components.sync.ProgressBar;
import aztech.modern_industrialization.machines.gui.MachineGuiParameters;
import aztech.modern_industrialization.machines.helper.SteamHelper;
import aztech.modern_industrialization.machines.init.MachineTier;
import aztech.modern_industrialization.machines.models.MachineModelClientData;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import aztech.modern_industrialization.util.Simulation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;

public class SteamCraftingMachineBlockEntity extends AbstractCraftingMachineBlockEntity {

    private final GunpowderOverclockComponent gunpowderOverclock;

    public SteamCraftingMachineBlockEntity(BEP bep, MachineRecipeType recipeType, MachineInventoryComponent inventory, MachineGuiParameters guiParams,
            ProgressBar.Parameters progressBarParams, MachineTier tier) {
        super(bep, recipeType, inventory, guiParams, progressBarParams, tier);
        gunpowderOverclock = new GunpowderOverclockComponent();

        GunpowderOverclockGui.Parameters gunpowderOverclockGuiParams = new GunpowderOverclockGui.Parameters(progressBarParams.renderX,
                progressBarParams.renderY + 20);
        registerClientComponent(new GunpowderOverclockGui.Server(gunpowderOverclockGuiParams, () -> gunpowderOverclock.overclockGunpowderTick));
        this.registerComponents(gunpowderOverclock);
    }

    @Override
    public long consumeEu(long max, Simulation simulation) {
        return SteamHelper.consumeSteamEu(getInventory().getFluidStacks(), max, simulation);
    }

    @Override
    protected MachineModelClientData getModelData() {
        MachineModelClientData data = new MachineModelClientData();
        orientation.writeModelData(data);
        data.isActive = isActiveComponent.isActive;
        return data;
    }

    @Override
    protected ActionResult onUse(PlayerEntity player, Hand hand, Direction face) {
        ActionResult result = super.onUse(player, hand, face);
        if (!result.isAccepted()) {
            return gunpowderOverclock.onUse(this, player, hand);
        }
        return result;
    }

    @Override
    public long getMaxRecipeEu() {
        if (gunpowderOverclock.isOverclocked()) {
            return tier.getMaxEu() * 2L;
        } else {
            return tier.getMaxEu();
        }

    }

    @Override
    public long getBaseRecipeEu() {
        if (gunpowderOverclock.isOverclocked()) {
            return tier.getBaseEu() * 2L;
        } else {
            return tier.getBaseEu();
        }

    }

    public void tick() {
        super.tick();
        gunpowderOverclock.tick(this);
    }

}
