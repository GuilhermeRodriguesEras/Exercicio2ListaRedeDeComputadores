import java.io.*;
import java.net.*;

class UDPClient {
    public static void main(String args[]) throws Exception {
        // Create datagram socket
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");

        while (true) {
            // Read a sentence from the console
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            String sentence = inFromUser.readLine();
            // Allocate buffers
            byte[] sendData = sentence.getBytes();
            byte[] receiveData = new byte[1024]; 
            // Send packet to the server
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);
            // Get the response from the server
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            // Print the received response using the actual length of received data
            String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("RECEIVED FROM SERVER: " + modifiedSentence);
        }
        // Close the socket (não alcançável, pois o loop é infinito)
        // clientSocket.close();
    }
}
