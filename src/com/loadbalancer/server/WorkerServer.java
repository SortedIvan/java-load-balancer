package com.loadbalancer.server;
import java.net.*;
import java.io.*;

public class WorkerServer {
    private int port;
    private Boolean isRunning;
    private ServerSocket serverSocket;
    private Boolean connectedToLb;
    private Socket loadBalancerSocket;

    public WorkerServer(int port, String ip) {
        this.port = port;
        this.connectedToLb = false;
        this.isRunning = true;
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 
    }
    
    public void ServerLoop() {

        while (isRunning) {

            // try accepting a new connection (for now its always assumed its the load balancer)
            try {
                loadBalancerSocket = serverSocket.accept();
            }
            catch (Exception ex) {

            }

            System.out.println("Load balancer connected");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(loadBalancerSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(loadBalancerSocket.getOutputStream(), true)) {

                String receivedMessage;
                while ((receivedMessage = in.readLine()) != null) {
                    out.println(receivedMessage);
                }

            loadBalancerSocket.close();
            System.out.println("Load balancer disconnected.");
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    }
    }
}