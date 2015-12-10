package com.equalinformation.bpm.poc.consumer.ws.test;

import com.equalinformation.bpm.poc.consumer.ws.domain.TaskHistory;
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
 * Created by bpupadhyaya on 12/7/15.
 */
public class HistoricActivitiInstancesTest {
    public static void main(String...args) {
        try {
            Client client = Client.create();
            client.addFilter(new HTTPBasicAuthFilter("kermit", "kermit"));
            WebResource webResource = client.resource("http://localhost:8091/activiti-rest/service/history/historic-activity-instances?start=0&size=100&sort=endTime&order=desc");
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Activiti call failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Response status: "+response.getStatus());
            String output = response.getEntity(String.class);
            System.out.println("Output from Server .... \n");
            System.out.println(output);

            // Manually parsing until get the "data" element.
            JsonParser parser = new JsonParser();
            JsonObject rootObject = parser.parse(output).getAsJsonObject();
            JsonElement taskElement = rootObject.get("data");

            Gson gson = new Gson();
            List<TaskHistory> taskHistoryList = new ArrayList<TaskHistory>();

            // Check if "task" element is an array or an object and parse accordingly.
            if (taskElement.isJsonObject()) {
                //The returned list has only 1 element
                TaskHistory taskHistory = gson.fromJson(taskElement, TaskHistory.class);
                taskHistoryList.add(taskHistory);
            }
            else if (taskElement.isJsonArray()) {
                //The returned list has >1 elements
                Type projectListType = new TypeToken<List<TaskHistory>>() {}.getType();
                taskHistoryList = gson.fromJson(taskElement, projectListType);
            }

            System.out.println("Size of list: " + taskHistoryList.size());
            System.out.println("Some data from first element: " + taskHistoryList.get(0).getAssignee());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
