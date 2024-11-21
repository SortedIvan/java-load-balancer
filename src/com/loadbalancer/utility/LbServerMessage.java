package com.loadbalancer.utility;

// Meant to represent a "struct" => keep all fields public
// Should be structured: 
// {1..:content:extra}
public class LbServerMessage {
    public LbServerMessageType messageType;
    public String messageContent;
    public String additionalHeaderInfo;

    public LbServerMessage(LbServerMessageType messageType, String messageContent) {
        if (messageContent.equals(null)) {
            return;
        }

        this.messageContent = messageContent;
        this.messageType = messageType;
    }

    public LbServerMessage(String rawContent) {
        if (messageContent.equals(null)) {
            return;
        }

        String[] parts = rawContent.split("\\:");
        if (parts.length < 3) {
            return;
        }

        if (parts[1].length() == 0) {return;}

        try {
            int messageType = Integer.parseInt(parts[0]);
            LbServerMessageType typeParsed = LbServerMessageType.fromInteger(messageType);
            if (typeParsed.equals(null)) {return;}
            
            this.messageType = typeParsed;
            this.messageContent = parts[1];
            this.additionalHeaderInfo = parts[2];
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
