public class ServerUDP {


    private static final String DATA_FOLDER = "./data/";
    private static final Set<String> FULL_ACCESS_CLIENTS = new HashSet<>(Arrays.asList("Client1"));
    private static final Set<String> connectedClients = new HashSet<>();

    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(4444);
        System.out.println("Server is running");

        while (true) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String request = new String(receivePacket.getData(), 0, receivePacket.getLength());

            if (request.contains("TERMINATED")) {
                String clientId = request.split(":")[0];
                connectedClients.remove(clientId);
                continue;
            }

            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            // Parse the request
            String[] parts = request.split(":");
            String clientId = parts[0];
            String operation = parts[1];
            String filename = parts.length > 2 ? parts[2] : "";
            String content = parts.length > 3 ? parts[3] : "";



            String response = "";

            if (isClientConnected(clientId)) {
                response = "CLIENT_ALREADY_CONNECTED_WITH_THIS_ID";
            } else {
                connectedClients.add(clientId);
                System.out.println("Client with ID: " + clientId + " connected");
                System.out.println("This request was accepted :" + request);
                if (FULL_ACCESS_CLIENTS.contains(clientId)) {
                    switch (operation) {
                        case "read":
                            response = readFile(filename);
                            break;
                        case "write":
                            writeFile(filename, content);
                            response = "File written successfully";
                            break;
                        case "execute":
                            response = executeOperation(filename);
                            break;
                        default:
                            response = "Invalid operation";
                    }

                } else {
                    response = readFile(filename);
                }
            }
            DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddress, clientPort);
            serverSocket.send(sendPacket);
        }
    }
