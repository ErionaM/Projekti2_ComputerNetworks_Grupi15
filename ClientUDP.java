public class ClientUDP {
    public static void main(String[] args) throws IOException {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter client ID:");
        String clientId = scanner.nextLine();

        try {
            while (true) {
                boolean operationSuccess = false;
                boolean terminateOperation = false;

                while (!operationSuccess && !terminateOperation) {
                    String operation;
                    String filename;
                    String content = "";

                    if ("Client1".equals(clientId)) {
                        System.out.println("Enter one operation (read/write/execute):");
                        operation = scanner.nextLine();

                        System.out.println("Enter filename:");
                        filename = scanner.nextLine();

                        if ("write".equals(operation)) {
                            System.out.println("Enter content to write:");
                            content = scanner.nextLine();
                        }
                    } else {
                        System.out.println("Enter filename:");
                        filename = scanner.nextLine();

                        System.out.println("Read-only access granted.");
                        operation = "read";
                    }

                    String request = clientId + ":" + operation + ":" + filename;
                    if ("write".equals(operation)) {
                        request += ":" + content;
                    }

                    SendPacketToServer(request, serverAddress, clientSocket);

                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                    try {
                        clientSocket.setSoTimeout(10000);

                        clientSocket.receive(receivePacket);

                        String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                        System.out.println("Response from server: " + response);

                        if (response.equalsIgnoreCase("CLIENT_ALREADY_CONNECTED_WITH_THIS_ID")) {
                            throw new IOException("CLIENT_ALREADY_CONNECTED_WITH_THIS_ID");
                        }
                        operationSuccess = true;
                    } catch (SocketTimeoutException e) {
                        System.out.println("Timeout reached. The server may be down or not responding.");
                        terminateOperation = true;
                    } catch (IOException e) {
                        System.out.println("Another client is already connected with this id.");
                        terminateOperation = true;
                    }
                }

                if (terminateOperation) {
                    break;
                }

                System.out.println("Do you want to perform another operation? (yes/no)");
                String answer = scanner.nextLine();
                if (!"yes".equalsIgnoreCase(answer)) {
                    break;  // Exit the main loop if the user doesn't want another operation
                } else {
                    String request = clientId + ":" + "TERMINATED";
                    SendPacketToServer(request, serverAddress, clientSocket);
                }
            }

            String request = clientId + ":" + "TERMINATED";
            SendPacketToServer(request, serverAddress, clientSocket);
            clientSocket.close();
            System.out.println("Client program terminated.");
        } catch (SocketException e) {
            String request = clientId + ":" + "TERMINATED";
            SendPacketToServer(request, serverAddress, clientSocket);
            clientSocket.close();
            System.out.println("Server is not running.");
        }
    }
