package elec332.core.network.packets;

import elec332.core.main.ElecCore;
import elec332.core.util.NBTHelper;
import elec332.core.world.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Elec332 on 4-9-2015.
 */
public abstract class AbstractPacketTileAction extends AbstractPacket {

    public AbstractPacketTileAction(){
    }

    public AbstractPacketTileAction(TileEntity tile, NBTTagCompound message){
        this(tile, message, 0);
    }

    public AbstractPacketTileAction(TileEntity tile, NBTTagCompound message, int id){
        super(new NBTHelper().addToTag(message, "data").addToTag(id, "id").addToTag(tile.getPos()).serializeNBT());
    }

    @Override
    public IMessage onMessageThreadSafe(AbstractPacket message, MessageContext ctx) {
        NBTHelper tag = new NBTHelper(message.networkPackageObject);
        BlockPos loc = tag.getPos();
        int i = tag.getInteger("id");
        NBTTagCompound data = tag.getCompoundTag("data");
        World world;
        if (ctx.side.isServer()) {
            world = ctx.getServerHandler().playerEntity.worldObj;
        } else {
            world = ElecCore.proxy.getClientWorld();
        }
        processPacket(WorldHelper.getTileAt(world, loc), i, data, ctx);
        return null;
    }

    public abstract void processPacket(TileEntity tile, int id, NBTTagCompound message, MessageContext ctx);

}
