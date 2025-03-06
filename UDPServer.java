// created on 29/09/2010 at 22:33
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

class UDPServer {

	public static void main(String args[]) throws Exception {
		//Create server socket
		DatagramSocket serverSocket = new DatagramSocket(9876);
		int erros = 0;
		ArrayList<String> letrasQueJaForam = new ArrayList<String>();
		String PalavraSecreta = "camuflagem", erradoText = " Letras erradas: ";
		byte[] Palavra = new byte[PalavraSecreta.length()];
		Arrays.fill(Palavra, (byte) '_');
		String capitalizedSentence;

		while(true) {
			byte[] receiveData = new byte[1024];
			//block until packet is sent by client
			DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivedPacket);
			//Get the information about the datagram of the client
			InetAddress IPAddress = receivedPacket.getAddress();
			int port = receivedPacket.getPort();
			//Get the data of the packet
			String sentence = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
			System.out.println("RECEIVED FROM CLIENT "+IPAddress.getHostAddress()+": " + sentence);
			
			if(sentence.length() != 1){
				capitalizedSentence = "Digite apenas uma letra!!";
			}
			else{
				if(letrasQueJaForam.contains(sentence)){
					capitalizedSentence = "Letra já foi usada!";}
				else{
					letrasQueJaForam.add(sentence);
					if(PalavraSecreta.contains(sentence)){
						for(int i = 0; i < PalavraSecreta.length(); i++){
							if(PalavraSecreta.charAt(i) == sentence.charAt(0)){
								Palavra[i] = sentence.getBytes()[0];
							}
						}
						capitalizedSentence = new String(Palavra) + erradoText;
						if(Arrays.equals(PalavraSecreta.getBytes(), Palavra)){
							capitalizedSentence = "Você ganhou! A palavra era: " + PalavraSecreta;
							erros = 0;
							erradoText = " Letras erradas: ";
							letrasQueJaForam.clear();
							Arrays.fill(Palavra, (byte) '_');
						}
					}
					else{
						erros++;
						erradoText += sentence + " ";
						capitalizedSentence = new String(Palavra) + " Erros:" + erradoText;
						if (erros == 5) {
							capitalizedSentence = "Você perdeu! A palavra era: " + PalavraSecreta;
							erros = 0;
							erradoText = " Letras erradas: ";
							letrasQueJaForam.clear();
							Arrays.fill(Palavra, (byte) '_');
						}
					}
				}
			}
			//Change the data to capital letters
			byte[] sendData = capitalizedSentence.getBytes();
			//Send back the response to the client
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
	}
}
