# Projekti2_ComputerNetworks_Grupi15

Ky projekt është bërë për lëndën Rrjetat Kompjuterike

Klasa ClientUDP është pjesë e një programi në Java që implementon një klient përmes protokollit UDP për të komunikuar me një server. Kjo klasë përmban një metodë main dhe një metodë ndihmëse SendPacketToServer.

Metoda main
Inicializimi i Socket-it dhe adresa e serverit:

DatagramSocket clientSocket = new DatagramSocket();: Krijon një objekt DatagramSocket për lidhjen e klientit.
InetAddress serverAddress = InetAddress.getByName("localhost");: Përmban adresën e serverit, në këtë rast është "localhost" ose adresa IP e vetes.
Identifikimi i klientit:

Scanner scanner = new Scanner(System.in);: Përdoret për të lexuar input-in nga përdoruesi.
System.out.println("Enter client ID:");: Kërkon nga përdoruesi të shkruajë ID-në e klientit.
String clientId = scanner.nextLine();: Lexon ID-në e klientit nga input-i.
Cikli kryesor i klientit:

while (true) {: Fillon një cikël i pafund kryesor që mundëson përdoruesin të kryejë operacione të ndryshme me serverin.
Zgjedhja e operacioneve:

while (!operationSuccess && !terminateOperation) {: Fillon një cikël brenda ciklit kryesor për të siguruar që operacioni të ekzekutohet me sukses ose të ndërprehet në rast gabimesh.
Zgjedh operacionet në varësi të ID-së së klientit ("Client1" ka mundësinë për operacione të shkrimit).
Krijimi dhe dërgimi i kërkesave:

String request = clientId + ":" + operation + ":" + filename;: Krijon një kërkesë në formatin e caktuar.
Nëse operacioni është shkrim ("write"), shtohet edhe përmbajtja e skedarit në kërkesë.
SendPacketToServer(request, serverAddress, clientSocket);: Dërgon kërkesën në server duke përdorur metodën ndihmëse.
Përpunimi i përgjigjeve të serverit:

clientSocket.setSoTimeout(10000);: Vendos një kohë limit prej 10 sekondash për pritur përgjigjen nga serveri.
Përdor një try-catch blok për të kapur përgjigjet dhe për të menaxhuar situata të ndryshme (timeout, gabime nga serveri).
Menaxhimi i përgjigjeve:

String response = new String(receivePacket.getData(), 0, receivePacket.getLength());: Lexon dhe shfaq përgjigjen nga serveri.
operationSuccess = true;: Nëse përgjigja është e suksesshme, cikli brenda ciklit kryesor mbyllet.
Menaxhimi i gabimeve:

catch (SocketTimeoutException e): Përgjigjet në rast timeout-it.
catch (IOException e): Përgjigjet në rast gabimesh të tjera.
Përfundimi i ciklit kryesor:

Nëse përdoruesi dëshiron të ndërprejë veprimin ose ka ndodhur një gabim, cikli kryesor mbyllet.
Dërgimi i kërkesës për terminim dhe mbyllja e lidhjes:

String request = clientId + ":" + "TERMINATED";: Krijon një kërkesë për të shpëtuar në server se klienti po largohet.
SendPacketToServer(request, serverAddress, clientSocket);: Dërgon kërkesën në server.
clientSocket.close();: Mbyll lidhjen e klientit.
Përpunimi i gabimeve në rastin kur serveri nuk është i aktivizuar:

catch (SocketException e): Përgjigjet në rastin kur nuk ka lidhje me serverin.
Metoda SendPacketToServer
Dërgimi i kërkesës te serveri:
DatagramPacket sendPacket = new DatagramPacket(request.getBytes(), request.length(), serverAddress, 4444);: Krijon një paketë të gatshme për dërgim me kërkesën në server.
clientSocket.send(sendPacket);: Dërgon kërkesën në server përmes socket-it të klientit.

Server-Side
Klasa ServerUDP është pjesë e një programi në Java që implementon një server për komunikim me klientë përmes protokollit UDP. Kjo klasë përmban një metodë main dhe disa metoda ndihmëse.

Metoda main
Inicializimi i Server Socket-it:

DatagramSocket serverSocket = new DatagramSocket(4444);: Krijon një objekt DatagramSocket që dëgjon në portin 4444 për kërkesat e klientëve.

Cikli Kryesor i Serverit:

while (true) {: Fillon një cikël të pafunduar për të pritur dhe shëruar kërkesat e klientëve.
Pranimi i Kërkesave nga Klientët:

byte[] receiveData = new byte[1024];: Krijon një varg bajtësh për të pranuar të dhënat nga klienti.
DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);: Krijon një objekt DatagramPacket për pranimin e të dhënave nga klienti.
serverSocket.receive(receivePacket);: Blokon ekzekutimin për të pritur derisa të marrë një pako nga klienti.
String request = new String(receivePacket.getData(), 0, receivePacket.getLength());: Konverton bajtat nga paketa në një string, për të marrë kërkesën nga klienti.

Menaxhimi i Kërkesave:

if (request.contains("TERMINATED")) {: Kontrollon nëse kërkesa përmban fjalën "TERMINATED".
String clientId = request.split(":")[0];: Nxjerr ID-në e klientit nga kërkesa.
connectedClients.remove(clientId);: Heq klientin nga lista e klientëve të lidhur dhe vazhdon me iterimin e radhës.
Identifikimi i Adresës dhe Portit të Klientit:

InetAddress clientAddress = receivePacket.getAddress();: Nxjerr adresën e klientit nga paketa e pranuar.
int clientPort = receivePacket.getPort();: Nxjerr portin e klientit nga paketa e pranuar.
Parsimi i Kërkesës:

String[] parts = request.split(":");: Ndahet kërkesa në pjesë duke përdorur karakterin ":".
String clientId = parts[0];: Nxjerr ID-në e klientit nga pjesa e parë.
String operation = parts[1];: Nxjerr llojin e operacionit nga pjesa e dytë.
String filename = parts.length > 2 ? parts[2] : "";: Nxjerr emrin e skedarit nga pjesa e tretë nëse ekziston, ndryshe është string bosh.
String content = parts.length > 3 ? parts[3] : "";: Nxjerr përmbajtjen e skedarit nga pjesa e katërt nëse ekziston, ndryshe është string bosh.
Përgjigje dhe Kthim te Klienti:

String response = "";: Krijon një string për përgjigjen që do t'i kthehet klientit.
if (isClientConnected(clientId)) { ... } else { ... }: Kontrollon nëse klienti është i lidhur dhe përgjigjet sipas kësaj.
Operacione nëse Klienti është i Lidhur:

DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.length(), clientAddress, clientPort);: Krijon një paketë për dërgimin e përgjigjes në adresën dhe portin e klientit.
serverSocket.send(sendPacket);: Dërgon përgjigjen në klient përmes socket-it të serverit.
Metoda isClientConnected
Verifikon Lidhjen e Klientit:
return connectedClients.contains(clientId);: Kthen true nëse klienti është i lidhur, në të kundërt kthen false.
Metoda readFile
Lexon Përmbajtjen e Skedarit:
Path filePath = Paths.get(DATA_FOLDER + filename);: Krijon një objekt Path duke përdorur rrugën e dosjes dhe emrin e dosjes.
return new String(Files.readAllBytes(filePath));: Kthen përmbajtjen e dosjes si një string.
Metoda writeFile
Shkruan përmbajtjen e Skedarit:
Path directoryPath = Paths.get(DATA_FOLDER);: Krijon një objekt Path për dosjen ku do të ruhen skedarët.
Path filePath = directoryPath.resolve(filename);: Krijon rrugën e plotë të dosjes.
Files.write(filePath, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);: Shkruan përmbajtjen e skedarit, krijon skedarin nëse nuk ekziston dhe e zëvendëson nëse ai ekziston.
Metoda executeOperation
Fshin Skedarin:
Path filePath = Paths.get(DATA_FOLDER + filename);: Krijon një objekt Path duke përdorur rrugën e dosjes dhe emrin e dosjes.
Files.deleteIfExists(filePath);: Fshin dosjen nëse ajo ekziston.
Ky është një përshkrim i detajuar për klasën ServerUDP duke përfshirë çdo hap dhe pjesë të rëndësishme të implementimit të saj.

Ky kod implementon një server dhe një klient përmes protokollit UDP për komunikim. Serveri ka aftësi për të lexuar, shkruar, dhe fshirë skedarë, ndërsa klienti mund të kryejë këto operacione duke lidhur dhe komunikuar me serverin përmes një lidhjeje UDP.

Ky projekt demostron sistemin klient-server permes protokollit UDP.
Ku klienti i kyçr me id e caktuar "Client1" ka rolin e administratorit, i cili mund te kryej funksionet si read(), write() dhe execute().
Klienti fillimisht duhet te shtyp operacionin (nese eshte si administrator), 
pastaj duhet te shtyp emrin e fajllit qe deshiron ta kryej operacionin.
Nese shtypet funksioni read() klienti lexon brenda fajllit te cilin e cakton.
Nese shtypet funksioni write() klienti krijon nje fajll dhe shkruan brenda tij.
Nese shtypet funksioni execute() klienti do te fshije nje fajll te cilin e cakton.

Kur klienti nuk eshte ne rolin e administratorit mundet vetem te lexoj nga fajlli i zgjedhur.

##Teknologjite e perdorura
-Java
-Socketat UDP

##Projekti munde te ekzekutohet si ne vazhdim:
*Ekzekutohet "ServerUDP.java" per te kyçur serverin.
*Ne nje terminal tjeter, ekzekutohet "ClientUDP.java" per te nisur klientin.



