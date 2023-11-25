import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClientUDP {

    public static void main(String[] args) throws IOException, SocketException, UnknownHostException {
        Scanner input = new Scanner(System.in);

        //Ktijohet socket lidhja me server;
        DatagramSocket dSocketa = new DatagramSocket();

        //Marrim IP adresen e local hostit
        InetAddress IpAdresa = InetAddress.getByName("localhost");

        //Deklarimi i nje byte array per dergimin e te dhenave
        byte [] dergimiDhena = new byte[1024];

        //Mesazhi
        String mesazhi = input.nextLine();

      //  4. Të behet lidhja me serverin duke përcaktuar sakt portin dhe IP Adresën e serverit;
        DatagramPacket Paketa = new DatagramPacket(dergimiDhena, dergimiDhena.length, IpAdresa, 5000);
        dSocketa.send(Paketa);
        dSocketa.close();

    }
}
