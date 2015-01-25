package neresources.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import neresources.registry.*;
import neresources.utils.LogHelper;

public class ClientSyncRequestMessage implements IMessage, IMessageHandler<ClientSyncMessage, IMessage>
{
    private boolean test;
    
    public ClientSyncRequestMessage()
    {
    }
    
    public ClientSyncRequestMessage(boolean test)
    {
        this.test = test;
        LogHelper.info("Requesting Sync...");
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.test = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(this.test);
    }

    @Override
    public IMessage onMessage(ClientSyncMessage message, MessageContext ctx)
    {
        LogHelper.info("Receiving Sync");
        DungeonRegistry.getInstance().regFromBytes(message.dungeonReg);
        DungeonRegistry.catFromBytes(message.dungeonCat);
        EnchantmentRegistry.fromBytes(message.enchantments);
        MobRegistry.getInstance().fromBytes(message.mobs);
        OreRegistry.regFromBytes(message.oresMatches);
        OreRegistry.dropsFromBytes(message.oresDropMap);
        PlantRegistry.getInstance().regFromBytes(message.plantReg);
        PlantRegistry.getInstance().dropsFromBytes(message.plantDrops);
        LogHelper.info("Synced");
        return null;
    }
}