package com.equalinformation.bpm.poc.consumer.ws.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * Created by bpupadhyaya on 1/29/16.
 */
public class RESTClientTest3CompleteTask {
    public static void main(String...args) {
        String taskId = "110101";
        String taskAction = "complete";
        Client client = Client.create();
        boolean success = false;
        try {
            client.addFilter(new HTTPBasicAuthFilter("kermit", "kermit"));

            WebResource webResource = client.resource("http://localhost:8091/activiti-rest/service/runtime/tasks/"+taskId);
            String input = "{\"action\":\""+taskAction +"\", "
                    + "\"variables\":[]}";
            ClientResponse response = webResource.accept("application/json")
                    .type("application/json").post(ClientResponse.class, input);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Activiti call failed : HTTP error code : "
                        + response.getStatus());
            }
            success = true;

            System.out.println("Task completed? : "+success);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
