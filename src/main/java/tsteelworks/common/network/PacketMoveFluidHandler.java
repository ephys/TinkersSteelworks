package tsteelworks.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fluids.FluidStack;
import tsteelworks.TSteelworks;
import tsteelworks.common.blocks.logic.DeepTankLogic;

public class PacketMoveFluidHandler implements IMessageHandler<PacketMoveFluidHandler.PacketMoveFluid, IMessage> {
	public static void moveFluidGUI(DeepTankLogic tank, FluidStack fluid) {
		PacketMoveFluid packet = new PacketMoveFluid(
				tank.xCoord,
				(short) tank.yCoord,
				tank.zCoord,
				tank.getWorldObj().provider.dimensionId,
				fluid.fluidID,
				GuiScreen.isShiftKeyDown());

		TSteelworks.getNetHandler().sendToServer(packet);
	}

	@Override
	public IMessage onMessage(PacketMoveFluid packet, MessageContext messageContext) {
		// todo: move fluid down
		return null;
	}

	public static class PacketMoveFluid implements IMessage {
		private int x;
		private short y;
		private int z;
		private int worldId;
		private int fluidID;
		private boolean isShiftKeyDown;

		public PacketMoveFluid() {}

		public PacketMoveFluid(int x, short y, int z, int worldId, int fluidID, boolean isShiftKeyDown) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.worldId = worldId;
			this.fluidID = fluidID;
			this.isShiftKeyDown = isShiftKeyDown;
		}

		@Override
		public void fromBytes(ByteBuf buffer) {
			x = buffer.readInt();
			y = buffer.readShort();
			z = buffer.readInt();

			worldId = buffer.readInt();
			fluidID = buffer.readInt();
			isShiftKeyDown = buffer.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf buffer) {
			buffer.writeInt(x)
				.writeShort(y)
				.writeInt(z)

				.writeInt(worldId)
				.writeInt(fluidID)
				.writeBoolean(isShiftKeyDown);
		}
	}
}