package com.equalinformation.bpm.poc.consumer.ws.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * Created by bpupadhyaya on 11/16/15.
 */
public class JerseyClientTest {
    public static void main(String...args) {
        try {
            Client client = Client.create();
            client.addFilter(new HTTPBasicAuthFilter("kermit", "kermit"));
            WebResource webResource = client.resource("http://localhost:8091/activiti-rest/service/runtime/tasks?candidateUser?kermit");
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Activiti call failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Response status: "+response.getStatus());
            String output = response.getEntity(String.class);
            System.out.println("Output from Server .... \n");
            System.out.println(output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
