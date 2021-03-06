package pt.upa.broker;

import pt.upa.broker.ws.TransportView;
import pt.upa.broker.ws.cli.BrokerClient;


public class BrokerClientApplication {

    public static void main(String[] args) throws Exception {
        // Check arguments
        if (args.length == 0) {
            System.err.println("Argument(s) missing!");
            System.err.println("Usage: java " + BrokerClientApplication.class.getName()
                    + " wsURL OR uddiURL wsName");
            return;
        }
        String uddiURL = null;
        String wsName = null;
        String wsURL = null;
        if (args.length == 1) {
            wsURL = args[0];
        } else if (args.length >= 2) {
            uddiURL = args[0];
            wsName = args[1];
        }

        // Create client
        BrokerClient client = null;

        if (wsURL != null) {
            System.out.printf("Creating client for server at %s%n", wsURL);
            client = new BrokerClient(wsURL);
        } else if (uddiURL != null) {
            System.out.printf("Creating client using UDDI at %s for server with name %s%n",
                uddiURL, wsName);
            client = new BrokerClient(uddiURL, wsName);
        }

        // the following remote invocations are just basic examples
        // the actual tests are made using JUnit

        System.out.println("Invoke ping()...");
        String result = client.ping("client");
        System.out.println(result);

	
        client.clearTransports();
		String id = client.requestTransport("Porto", "Lisboa", 7);
		//client.requestTransport("Lisboa", "Lisboa", 7);
		TransportView t = client.viewTransport(id);
		System.out.println("Transport id: "+id);
		System.out.println("ID: "+t.getId()+"\nState: "+t.getState());
		
		System.out.println("------------------------------");
		Thread.sleep(5000);
		System.out.println("Print List: "+client.listTransports().get(0).toString());
		t=client.viewTransport(id);
		System.out.print("ID:"+t.getId()+"\nState: "+t.getState() +"\nPrice: "+t.getPrice());
		
        
    }
}
