package com.equalinformation.bpm.poc.consumer.ws;

import com.equalinformation.bpm.poc.consumer.ws.domain.Task;
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
 * Created by bpupadhyaya on 11/16/15.
 */
public class ActivitiRESTClient {

    //TODO modify later for parameter, current one is the first pass
    public List<Task> getTaskList() {
        List<Task> taskList = null;
        try {
            Client client = Client.create();
            client.addFilter(new HTTPBasicAuthFilter("kermit", "kermit"));
            WebResource webResource = client.resource("http://localhost:8091/activiti-rest/service/runtime/tasks?candidateUser?kermit");
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Activiti call failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Response status: " + response.getStatus());
            String output = response.getEntity(String.class);
            System.out.println("Output from Server .... \n");
            System.out.println(output);

            // Manually parsing until get the "data" element.
            JsonParser parser = new JsonParser();
            JsonObject rootObject = parser.parse(output).getAsJsonObject();
            JsonElement taskElement = rootObject.get("data");

            Gson gson = new Gson();
            taskList = new ArrayList<Task>();

            // Check if "task" element is an array or an object and parse accordingly.
            if (taskElement.isJsonObject()) {
                //The returned list has only 1 element
                Task task = gson.fromJson(taskElement, Task.class);
                taskList.add(task);
            } else if (taskElement.isJsonArray()) {
                //The returned list has >1 elements
                Type projectListType = new TypeToken<List<Task>>() {
                }.getType();
                taskList = gson.fromJson(taskElement, projectListType);
            }

            System.out.println("Size of list: " + taskList.size());
            if(taskList.size() >= 1) {
                System.out.println("Some data from first element: " + taskList.get(0).getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return taskList;
    }

    public boolean completeTask(String taskId) {
        boolean success = false;
        try {
            Client client = Client.create();
            client.addFilter(new HTTPBasicAuthFilter("kermit", "kermit"));
            WebResource webResource = client.resource("http://localhost:8091/activiti-rest/service/runtime/tasks/"+taskId);
            String input = "{\"action\":\"complete\", "
                    + "\"variables\":[]}";
            ClientResponse response = webResource.accept("application/json")
                    .type("application/json").post(ClientResponse.class, input);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Activiti call failed : HTTP error code : "
                        + response.getStatus());
            }
            System.out.println("completeTask(): Response status: "+response.getStatus());
            success = true;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    //TODO modify later for parameter, current one is the first pass
    public List<Task> getHistoricActivitiInstances() {
        List<Task> historicTaskList = null;
        try {
            Client client = Client.create();
            client.addFilter(new HTTPBasicAuthFilter("kermit", "kermit"));
            WebResource webResource = client.resource("http://localhost:8091/activiti-rest/service/history/historic-activity-instances");
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Activiti call failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Response status: " + response.getStatus());
            String output = response.getEntity(String.class);
            System.out.println("Output from Server .... \n");
            System.out.println(output);

            // Manually parsing until get the "data" element.
            JsonParser parser = new JsonParser();
            JsonObject rootObject = parser.parse(output).getAsJsonObject();
            JsonElement taskElement = rootObject.get("data");

            Gson gson = new Gson();
            historicTaskList = new ArrayList<Task>();

            // Check if "task" element is an array or an object and parse accordingly.
            if (taskElement.isJsonObject()) {
                //The returned list has only 1 element
                Task task = gson.fromJson(taskElement, Task.class);
                historicTaskList.add(task);
            } else if (taskElement.isJsonArray()) {
                //The returned list has >1 elements
                Type projectListType = new TypeToken<List<Task>>() {
                }.getType();
                historicTaskList = gson.fromJson(taskElement, projectListType);
            }

            System.out.println("Size of list: " + historicTaskList.size());
            if(historicTaskList.size() >= 1) {
                System.out.println("Some data from first element: " + historicTaskList.get(0).getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return historicTaskList;
    }

}
