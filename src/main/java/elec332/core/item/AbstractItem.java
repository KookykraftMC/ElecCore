package elec332.core.item;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Elec332 on 21-8-2016.
 */
public abstract class AbstractItem extends Item {

    public AbstractItem(ResourceLocation rl){
        if (rl != null) {
            setRegistryName(rl);
            setUnlocalizedNameFromName();
            this.name = getRegistryName().toString().split(":")[1];
        } else {
            this.name = "<unknown>";
        }
    }

    protected final String name;

    public void setUnlocalizedNameFromName(){
        setUnlocalizedName(getRegistryName().toString().replace(":", ".").toLowerCase());
    }

}
