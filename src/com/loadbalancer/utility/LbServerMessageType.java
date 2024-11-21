package com.loadbalancer.utility;

public enum LbServerMessageType {
    Connect,
    Disconnect,
    LyricsSearch,
    LyricsSearchResult;

    public static LbServerMessageType fromInteger(int x) {
        switch(x) {
        case 0:
            return Connect;
        case 1:
            return Disconnect;
        case 2:
            return LyricsSearch;
        case 3:
            return LyricsSearchResult;
        }
        return null;
    }
}