package com.equalinformation.bpm.poc.consumer.ws.test;

import com.equalinformation.bpm.poc.consumer.ws.domain.Instance;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bpupadhyaya on 1/29/16.
 */
public class RESTClientTest1CreateProcessInstance {
    public static void main(String... args) {
        String processDefinitionId = "YourProcessDefinitionIdHere";
        Instance instanceDetail = new Instance();
        String instanceDetailString= "";
        List<Instance> processInstanceList = null;

        try {
            Client client = Client.create();
            client.addFilter(new HTTPBasicAuthFilter("kermit", "kermit"));
            //TODO
            WebResource webResource = client.resource("http://localhost:8091/activiti-rest/service/runtime/process-instances");
            String input = "{\"processDefinitionId\":\""+processDefinitionId+"\", "
                    + "\"variables\":[]}";
            ClientResponse response = webResource.accept("application/json")
                    .type("application/json").post(ClientResponse.class, input);

//            if (response.getStatus() != 200) {
//                throw new RuntimeException("Activiti call failed : HTTP error code : "
//                        + response.getStatus());
//            }
            System.out.println("Response status: "+response.getStatus());

            String output = response.getEntity(String.class);

            JsonParser parser = new JsonParser();
            JsonObject rootObject = parser.parse(output).getAsJsonObject();
            JsonElement taskElement = rootObject.get("data");

            Gson gson = new Gson();
            processInstanceList = new ArrayList<Instance>();

//            if (taskElement.isJsonObject()) {
//                Instance instance = gson.fromJson(taskElement, Instance.class);
//                processInstanceList.add(instance);
//            } else if (taskElement.isJsonArray()) {
//                Type projectListType = new TypeToken<List<Instance>>() {
//                }.getType();
//                processInstanceList = gson.fromJson(taskElement, projectListType);
//            }

//            instanceDetail  = gson.fromJson(taskElement, Instance.class);
//            instanceDetailString  = gson.fromJson(taskElement, String.class);

//            System.out.println("Instance details:");
//            System.out.println("instance id:"+instanceDetail.getId());
//            System.out.println("instance url: "+instanceDetail.getUrl());
//            System.out.println("process defn url: "+instanceDetail.getProcessDefinitionUrl());
//            System.out.println("activiti id: "+instanceDetail.getActivityId());
            System.out.println("Returned list size: "+output);

        } catch (Exception e) {
            //TODO
            e.printStackTrace();
        }

    }
}
