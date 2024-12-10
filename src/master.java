import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class master{
    DatagramSocket socket;
    List<Integer> receivedMessagesList = new ArrayList<>();
    int port;
    master(DatagramSocket socket, int port){
        this.socket = socket;
        this.port = port;
    }
    public void getMessage(){
        byte[] buffer = new byte[4];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try{
            while(true) {
                socket.receive(packet);
                ByteBuffer byteBuffer = ByteBuffer.wrap(packet.getData());
                int receivedMessage = byteBuffer.getInt();


                if(receivedMessage != 0 && receivedMessage != -1 ){
                    System.out.println("received from slave of ip " + packet.getAddress() +
                    " on port: " + packet.getPort() + ": " + receivedMessage);
                    receivedMessagesList.add(receivedMessage);
                    System.out.println(receivedMessagesList);
                }

                else if(receivedMessage == 0 && !receivedMessagesList.isEmpty()){
                int sum = 0;
                double average = 0;
                for(int i = 0; i<receivedMessagesList.size(); i++){
                    sum += receivedMessagesList.get(i);
                    average += ((double) sum /receivedMessagesList.size());
                }
                int roundedValue = (int)Math.floor(average);
                System.out.println(roundedValue);
                sendBroadcastMessage(port, roundedValue);
                receivedMessagesList.add(roundedValue);
                }

                else{
                    System.out.println(receivedMessage);
                    sendBroadcastMessage(port, receivedMessage);
                    socket.close();
                    return;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
   }

    public void sendBroadcastMessage(int port, int receivedMessage){
        try{
            byte[] msgBytes = ByteBuffer.allocate(4).putInt(receivedMessage).array();
            InetAddress broadcastIP = getBroadcastAddress();
            socket.setBroadcast(true);

            DatagramPacket packet = new DatagramPacket(
                    msgBytes,
                    msgBytes.length,
                    broadcastIP,
                    port
            );
            socket.send(packet);
            System.out.println("Packet has been sent using broadcast ip: " + broadcastIP + " to hosts on port: " + port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public InetAddress getBroadcastAddress(){
        try{
            //pobranie interfejsów sieciowych
            Enumeration<NetworkInterface> networkif = NetworkInterface.getNetworkInterfaces();
                while(networkif.hasMoreElements()){
                    NetworkInterface currentInterface = networkif.nextElement();
                    //przefiltrowanie przez aktywne interfejsy sieciowe, mające dostęp do adresu rozgłoszeniwoego
                    if(currentInterface.isUp()){ //sprawdza czy interfejs jest aktywny w sieci
                        for(InterfaceAddress addr : currentInterface.getInterfaceAddresses()){
                            InetAddress broadcast = addr.getBroadcast();
                                if(broadcast != null ) return broadcast;
                        }
                    }
                    }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    return null; // jesli nie udalo sie znalezc adresu rozgloszeniowego
    }
}
