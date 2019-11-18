package com.comviva.cdr.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.comviva.cdr.dao.DaoFactory;
import com.comviva.cdr.util.TestLogger;
import com.comviva.cdr.util.ApplicationConfiguration;
import com.comviva.cdr.util.ConfigurationChangeListner;

public class UdpListener {

	private static final TestLogger logger = new TestLogger(UdpListener.class);

	private static final String FILE_PATH = "src/main/java/config.properties";
	public static final int port;
	public static final String strIP;
	public static String storageType;
	public static List<String> listOfValidIP;
	private static ApplicationConfiguration readConfigFileObj = ApplicationConfiguration.getInstance();
	static ThreadPoolExecutor executor ;

	static {
		ConfigurationChangeListner listner = new ConfigurationChangeListner(FILE_PATH);

		try {
			new Thread(listner).start();
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		port = Integer.parseInt(readConfigFileObj.getConfiguration("port"));
		strIP = readConfigFileObj.getConfiguration("serverIP");
		storageType = readConfigFileObj.getConfiguration("storageType");
		listOfValidIP = readConfigFileObj.getValidIp();
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
	}

	public static DatagramSocket createSocket(DatagramSocket socket, int port, InetAddress serverIP)
			throws SocketException {
		socket = new DatagramSocket(port, serverIP);
		return socket;
	}
	

	public static void main(String[] args) {

		InetAddress serverIP = null;

		try {
			// serverIP = InetAddress.getByName(strIP);
			serverIP = InetAddress.getLocalHost();
			System.out.println(strIP + " " + serverIP);
		} catch (UnknownHostException e2) {
			e2.printStackTrace();
		}

		DatagramSocket datagramsocket = null;
		try {
			datagramsocket = createSocket(datagramsocket, port, serverIP);
		} catch (SocketException e1) {
			e1.printStackTrace();
			logger.error("Socket not Created " + e1);
		}

		byte[] receive = new byte[65537];

		DatagramPacket dpReceive = null;

		while (true) {
			System.out.println("hello loop");
			dpReceive = new DatagramPacket(receive, receive.length);

			try {
				datagramsocket.receive(dpReceive);
				logger.info("packet received");
			} catch (IOException e) {

				e.printStackTrace();
				logger.debug("packet not received");

			}

			String sourceIP = dpReceive.getAddress().getHostAddress();
			System.out.println("source IP " + sourceIP);

			if (listOfValidIP.contains(sourceIP) == true) {

				String dpString = new String(receive, 0, dpReceive.getLength());
				String[] parsedString = dpString.split("\\|");

				System.out.println("dpString  " + dpString);

				if (parsedString[0].equalsIgnoreCase("ReloadConfig")) {
					storageType = readConfigFileObj.getConfiguration("storageType");
					listOfValidIP = readConfigFileObj.getValidIp();

				}

				else {

					if (storageType.equalsIgnoreCase("file") == true) {
						DaoFactory fileObj = new DaoFactory(parsedString[1] , parsedString[0] , dpString , sourceIP);
						executor.execute(fileObj);

					} else {
						System.out.println("Invalid CDR data format");
						logger.warn("Invalid CDR data format");
					}

				}
			}

		}

	}
}
