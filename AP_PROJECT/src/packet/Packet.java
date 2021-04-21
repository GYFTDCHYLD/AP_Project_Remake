package packet;

import java.io.Serializable;

import network.Client;

public abstract class Packet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
 

	public static enum PacketTypes{
		INVALID(-1), REGISTER(00), LOGIN(01),LOGOUT(02), CHAT(03), COMPLAIN(04), ASSIGNCOMPLAIN(05), COMPLAINSCHEDULE(06), USERS(07), PASSWORDRESET(8);
		
		private int packetId;
		
		private PacketTypes(int packetId) {
			this.packetId = packetId;
		}
		
		public int getId() {
			return packetId;
		}	
	}
	
	public byte packetId;
	
	public Packet(int packetId) {
		this.packetId = (byte) packetId;
	}
	
	
	public abstract void writeData(Client client);
	public abstract Object getData();
	
	
	public String readData(byte[] data, int start, int end) {
		String message = new String(data).trim();
		return message.substring(start,end);
	}
	
	
	
	public static PacketTypes lookupPacket(String packetId) {
		try {
			return lookupPacket(Integer.parseInt(packetId));
		}catch(NumberFormatException e) {
			return PacketTypes.INVALID;
		}
	}
	
	
	public static PacketTypes lookupPacket(int id) {
		for(PacketTypes p : PacketTypes.values()) {
			if(p.getId() == id) {
				return p;
			}
		} 
		return PacketTypes.INVALID;
	}


	public byte getPacketId() {
		return packetId;
	}

	
}
