import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class slave{
    DatagramSocket socket;
    slave(DatagramSocket socket){
       this.socket = socket;
    }
    public void sendMessageToMaster(int masterPort, int messagetoSend){
        try{
            byte[] msgBytes = ByteBuffer.allocate(4).putInt(messagetoSend).array();
            DatagramPacket packetToSend = new DatagramPacket(
                    msgBytes,
                    msgBytes.length,
                    InetAddress.getLocalHost(),
                    masterPort
            );
            socket.send(packetToSend);
            System.out.println("packet has been sent to master " + messagetoSend);
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}