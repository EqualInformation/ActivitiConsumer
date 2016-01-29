package com.equalinformation.bpm.poc.consumer.ws.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * Created by bpupadhyaya on 1/29/16.
 */
public class RESTClientTest2RetrieveCurrentTasks {
    public static void main(String...args) {
        String taskListString = "";
//        String executionId = "105174";
        Client client;
        String output = "";
        String processInstanceId = "110097";

        try {
            client = Client.create();
            client.addFilter(new HTTPBasicAuthFilter("kermit", "kermit"));

//            WebResource webResource = client.resource("http://localhost:8091/activiti-rest/service/runtime/executions/"+executionId+"/activities");
            WebResource webResource = client.resource("http://localhost:8091/activiti-rest/service/runtime/tasks?processInstanceId="+processInstanceId);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Activiti call failed : HTTP error code : "
                        + response.getStatus());
            }

            output = response.getEntity(String.class);

//            JsonParser parser = new JsonParser();
//            JsonObject rootObject = parser.parse(output).getAsJsonObject();
//            JsonElement taskElement = rootObject.get("data");
//
//            Gson gson = new Gson();
//
//            taskListString = gson.fromJson(taskElement, String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("task list: "+output);
    }
}
