public class UdpServer {
    public static void main(String[] args) throws IOException {

        DatagramSocket ServerSocket = new DatagramSocket(5000);
        DatagramSocket ServerSocket2 = new DatagramSocket(5001);

        byte [] leximiDhena = new byte[1024];

        DatagramPacket pranuesiDhenave = new DatagramPacket(leximiDhena, leximiDhena.length);

        ServerSocket.receive(pranuesiDhenave);
        ServerSocket2.receive(pranuesiDhenave);

        String mesazhi = new String(leximiDhena, 0, pranuesiDhenave.getLength());
        String mesazhi2 = new String(leximiDhena, 0, pranuesiDhenave.getLength());

        System.out.println("Mesazhi klientit: " + mesazhi);
        System.out.println("Mesazhi i klientit (pa priviligje): " + mesazhi2);
        System.out.println("IP e klientit: " + pranuesiDhenave.getAddress());
    }
}
class Server1 {
    public static void main (String args[]) throws Exception {
        System.out.println("Enter Port Number!!!");
        Scanner input = new Scanner(System.in);
        int SPort = input.nextInt();
        DatagramSocket srvskt = new DatagramSocket(SPort);
        byte[] data = new byte[1024];
        System.out.println("Enter a full file name to save data to it? ");
        String path = input.next();
        System.out.println("file: " + path + "will be created");
        FileOutputStream FOS = new FileOutputStream(path);
        DatagramPacket srvpkt = new DatagramPacket(data,1024);
        System.out.println("Listening to portL " + SPort);
        int Packetcounter=0;

        while (true){
            srvskt.receive(srvpkt);
            Packetcounter++;
            String words = new String (srvpkt.getData());
            InetAdress ip = srvpkt.getAddress();
            int port = srvpkt.getPort();
            System.out.println("Packet #" + Packetcounter + "Recieved from Host/ port" + ip + "/" + port);
            FOS.write(data);
            //out 16.flush();
            if(Packetcounter >=100)
            break;
        }
        FOS.close();
        System.out.println("Data has been written to the file!")
            }
}
