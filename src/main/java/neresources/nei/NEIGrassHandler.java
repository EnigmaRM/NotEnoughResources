package neresources.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neresources.reference.Resources;
import neresources.registry.GrassSeedRegistry;
import neresources.utils.TranslationHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class NEIGrassHandler extends TemplateRecipeHandler
{
    private static final int GRASS_X = 75;
    private static final int GRASS_Y = 5;
    private static final int OUTPUT_X = 2;
    private static final int OUTPUT_SCALE = 20;
    private static final int OUTPUT_Y = 50;
    private static final int INPUT_ARROW_Y = 20;

    @Override
    public String getGuiTexture()
    {
        return Resources.Gui.GRASS_NEI.toString();
    }

    @Override
    public String getRecipeName()
    {
        return TranslationHelper.translateToLocal("ner.grass.title");
    }

    @Override
    public int recipiesPerPage()
    {
        return 1;
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 130);
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(GRASS_X, INPUT_ARROW_Y, 16, 30), NEIConfig.GRASS, new Object()));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(NEIConfig.GRASS))
            arecipes.add(new CachedSeedOutput(GrassSeedRegistry.getInstance().getAllDrops().keySet()));
        else
            super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if (ingredients[0] instanceof ItemStack)
        {
            ItemStack ingredient = (ItemStack) ingredients[0];
            if (ingredient.isItemEqual(new ItemStack(Blocks.tallgrass, 1, 1)))
                arecipes.add(new CachedSeedOutput(GrassSeedRegistry.getInstance().getAllDrops().keySet()));
        }
        else super.loadUsageRecipes(inputId, ingredients);
    }

    public class CachedSeedOutput extends TemplateRecipeHandler.CachedRecipe
    {
        private Set<ItemStack> seeds = new LinkedHashSet<ItemStack>();

        public CachedSeedOutput(Set<ItemStack> seeds)
        {
            this.seeds.addAll(seeds);
        }

        @Override
        public PositionedStack getResult()
        {
            return new PositionedStack(new ItemStack(Blocks.tallgrass, 1, 1), GRASS_X, GRASS_Y);
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            List<PositionedStack> list = new ArrayList<PositionedStack>();
            int xOffset = 0;
            int yOffset = 0;
            for (ItemStack itemStack : seeds)
            {
                list.add(new PositionedStack(itemStack, OUTPUT_X + xOffset, OUTPUT_Y + yOffset));
                yOffset += OUTPUT_SCALE;
                if (yOffset > 147)
                {
                    yOffset = 0;
                    xOffset += OUTPUT_SCALE;
                }
            }
            return list;
        }
    }
}
