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
package aztech.modern_industrialization.machines.components;

import aztech.modern_industrialization.MIItem;
import aztech.modern_industrialization.machines.IComponent;
import aztech.modern_industrialization.machines.multiblocks.ShapeTemplate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;

public class ActiveShapeComponent implements IComponent {
    public final ShapeTemplate[] shapeTemplates;
    private int activeShape = 0;

    public ActiveShapeComponent(ShapeTemplate[] shapeTemplates) {
        this.shapeTemplates = shapeTemplates;
    }

    public ActionResult onUse(PlayerEntity player, Hand hand, Direction face) {
        Item handItem = player.getStackInHand(hand).getItem();
        if (handItem == MIItem.ITEM_SCREWDRIVER && shapeTemplates.length > 1) {
            activeShape = (activeShape + 1) % shapeTemplates.length;
            return ActionResult.success(player.getEntityWorld().isClient());
        }
        return ActionResult.PASS;
    }

    public ShapeTemplate getActiveShape() {
        return shapeTemplates[activeShape];
    }

    public int getActiveShapeIndex() {
        return activeShape;
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        tag.putInt("activeShape", activeShape);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        activeShape = tag.getInt("activeShape");
    }
}
