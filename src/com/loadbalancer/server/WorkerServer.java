package com.loadbalancer.server;
import java.net.*;
import java.io.*;

// Will have access to some kind of lyrics file structure where lyrics can be searched within the server
// The servers will all have a replica of this such that they can all process requests
// Consistency is an issue here => perhaps out of scope for this project
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
                System.out.println(ex.getMessage());
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