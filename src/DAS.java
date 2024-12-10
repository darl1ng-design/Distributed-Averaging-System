import java.net.DatagramSocket;
import java.net.SocketException;
public class DAS {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("too less arguments to run the application");
            return;
        }
        int inputPort = Integer.parseInt(args[0]);
        int message = Integer.parseInt(args[1]);
        DatagramSocket socket = null;
        boolean isTaken = false;

        try{
            socket = new DatagramSocket(inputPort);
        }catch(SocketException e){
            isTaken = true;
        }
        try {
            if (isTaken) {
                slave s = new slave(new DatagramSocket());
                System.out.println("running master on port " + inputPort);
                s.sendMessageToMaster(inputPort, message);
            } else {
                master m = new master(socket, inputPort);
                System.out.println("running master on port " + inputPort);
                m.getMessage();
            }
        }catch (NumberFormatException e){
            System.out.println("Argument portu zawiera niewłaściwy typ danych " + args[0].getClass().getName());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
