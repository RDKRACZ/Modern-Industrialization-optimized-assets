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
package aztech.modern_industrialization.materials.part;

import aztech.modern_industrialization.materials.set.MaterialBlockSet;
import aztech.modern_industrialization.textures.TextureHelper;
import java.io.IOException;
import net.minecraft.client.texture.NativeImage;

public class BlockPart extends UnbuildablePart<MaterialBlockSet> {

    public BlockPart() {
        super("block");
    }

    @Override
    public BuildablePart of(MaterialBlockSet set) {
        return new RegularPart(this.key).asBlock().withTextureRegister((mtm, partContext, part, itemPath) -> {
            String template = String.format("modern_industrialization:textures/materialsets/blocks/%s.png", set.name);
            try {
                NativeImage image = mtm.getAssetAsTexture(template);
                TextureHelper.colorize(image, partContext.getColoramp());
                String texturePath = String.format("modern_industrialization:textures/blocks/%s.png", itemPath);
                mtm.addTexture(texturePath, image);
                image.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
