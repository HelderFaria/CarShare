package isep.carshare;

/**
 * Created by Raul Abreu on 18/12/2014.
 */
public class Constants {
    final static String host;
    final static int port;
    final static String service;
    final static String VehicleURI;
    final static String ownerQuery;

    static {
        service = "raul";
    }
    static {
        port = 5222;
    }
    static {
        host = "raulabreuisep.ddns.net";

    }
    static {
        VehicleURI = "http://raulabreuisep.ddns.net:3001/vehicle";
    }
    static {
        ownerQuery = "?owner=";
    }

}
