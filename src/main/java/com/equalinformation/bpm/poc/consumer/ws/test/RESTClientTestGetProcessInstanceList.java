package com.equalinformation.bpm.poc.consumer.ws.test;

import com.equalinformation.bpm.poc.consumer.ws.domain.Instance;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bpupadhyaya on 1/29/16.
 */
public class RESTClientTestGetProcessInstanceList {
    public static void main(String...args) {
        List<Instance> processInstanceList = null;
        String processDefinitionId = "process-NPIQuoteSetupFrag1:4:104367";

        try {
            Client client = Client.create();
            client.addFilter(new HTTPBasicAuthFilter("kermit", "kermit"));
            //TODO move URL to a property file or equivalent
            WebResource webResource = client.resource("http://localhost:8091/activiti-rest/service/runtime/process-instances?processDefinitionId="+processDefinitionId);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Activiti call failed : HTTP error code : "
                        + response.getStatus());
            }

            String output = response.getEntity(String.class);

            JsonParser parser = new JsonParser();
            JsonObject rootObject = parser.parse(output).getAsJsonObject();
            JsonElement taskElement = rootObject.get("data");

            Gson gson = new Gson();
            processInstanceList = new ArrayList<Instance>();

            if (taskElement.isJsonObject()) {
                Instance instance = gson.fromJson(taskElement, Instance.class);
                processInstanceList.add(instance);
            } else if (taskElement.isJsonArray()) {
                Type projectListType = new TypeToken<List<Instance>>() {
                }.getType();
                processInstanceList = gson.fromJson(taskElement, projectListType);
            }

        } catch (Exception e) {
            //TODO log exceptions
            e.printStackTrace();
        }

        System.out.println("Size of list: "+processInstanceList.size());
        System.out.println(processInstanceList.get(0).getId());
        System.out.println(processInstanceList.get(0).getUrl());
    }
}
