package elec332.core.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import elec332.core.util.BlockLoc;
import elec332.core.util.NBTHelper;
import elec332.core.world.WorldHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

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
        super(new NBTHelper(new BlockLoc(tile).toNBT(new NBTTagCompound())).addToTag(message, "data").addToTag(id, "id").toNBT());
    }

    @Override
    public IMessage onMessageThreadSafe(AbstractPacket message, MessageContext ctx) {
        BlockLoc loc = new BlockLoc(message.networkPackageObject);
        int i = message.networkPackageObject.getInteger("id");
        NBTTagCompound data = message.networkPackageObject.getCompoundTag("data");
        if (ctx.side == Side.CLIENT)
            return processClient(loc, i, data, ctx);
        processPacket(WorldHelper.getTileAt(ctx.getServerHandler().playerEntity.getServerForPlayer(), loc), i, data, ctx);
        return null;
    }

    @SideOnly(Side.CLIENT)
    private IMessage processClient(BlockLoc loc, int id, NBTTagCompound data, MessageContext ctx){
        processPacket(WorldHelper.getTileAt(net.minecraft.client.Minecraft.getMinecraft().theWorld, loc), id, data, ctx);
        return null;
    }

    public abstract void processPacket(TileEntity tile, int id, NBTTagCompound message, MessageContext ctx);

}
