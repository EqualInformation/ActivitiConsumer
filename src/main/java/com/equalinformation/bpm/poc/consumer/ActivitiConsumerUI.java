package com.equalinformation.bpm.poc.consumer;

import com.equalinformation.bpm.poc.consumer.ws.ActivitiRESTClient;
import com.equalinformation.bpm.poc.consumer.ws.domain.Task;
import com.equalinformation.bpm.poc.consumer.ws.domain.TaskHistory;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.MenuBar.MenuItem;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by : bpupadhyaya on 11-15-2015
 */

@Theme("mytheme")
@Widgetset("com.equalinformation.bpm.poc.consumer.MyAppWidgetset")
public class ActivitiConsumerUI extends UI {

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = ActivitiConsumerUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    private Table summaryTable = new Table("Inbox-Summary");
    private Table detailTable = new Table("Inbox-Detail");
    private Table historicActivitiInstancesTable = new Table("Historic-Activiti-Instances");
    private ActivitiRESTClient activitiRESTClient = new ActivitiRESTClient();
    private List<Task> taskList = new ArrayList<Task>();
    private List<TaskHistory> historicTaskList = new ArrayList<TaskHistory>();
    private int inboxCount = 0;
    private int archivedCount = 0;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        //layout.setDefaultComponentAlignment(Alignment.CENTER);
        setContent(layout);

        final Label selection = new Label("Your selection >> ");
        layout.addComponent(selection);

        MenuBar.Command menuCommand = new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                selection.setValue("Selected " + selectedItem.getText() + " from menu.");
            }
        };

        taskList = fetchTaskDataFromActivitiEngine();
        historicTaskList = fetchHistoricTaskDataFromActivitiEngine();
        inboxCount = taskList.size();
        archivedCount = historicTaskList.size();

        MenuBar.Command inboxCommand = new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                createActivitiTaskTableSummary(layout);
                createActivitiTaskTableDetail(layout);
            }
        };

        MenuBar.Command archivedCommand = new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                createActivitiHistoricInstancesTable(layout);
            }
        };


        MenuBar menuBar = createMenuBar(menuCommand, inboxCommand, archivedCommand);

        layout.addComponent(menuBar);

    }

    private void createActivitiTaskTableSummary(VerticalLayout layout) {

        // Columns
        summaryTable.addContainerProperty("ID", String.class, null);
        summaryTable.addContainerProperty("URL",  String.class, null);
//        summaryTable.addContainerProperty("Owner", String.class, null);
//        summaryTable.addContainerProperty("Assignee",  String.class, null);
//        summaryTable.addContainerProperty("Delegation State",  String.class, null);
//        summaryTable.addContainerProperty("Name", String.class, null);
//        summaryTable.addContainerProperty("Description",  String.class, null);
//        summaryTable.addContainerProperty("Create time", String.class, null);
//        summaryTable.addContainerProperty("Due date",  String.class, null);
//        summaryTable.addContainerProperty("Priority",  String.class, null);
//        summaryTable.addContainerProperty("Suspended", String.class, null);
//        summaryTable.addContainerProperty("Task definition key",  String.class, null);
//        summaryTable.addContainerProperty("Tenant ID", String.class, null);
//        summaryTable.addContainerProperty("Category",  String.class, null);
//        summaryTable.addContainerProperty("Form key",  String.class, null);
//        summaryTable.addContainerProperty("Parent task ID", String.class, null);
//        summaryTable.addContainerProperty("Parent task URL",  String.class, null);
//        summaryTable.addContainerProperty("Execution ID", String.class, null);
//        summaryTable.addContainerProperty("Execution URL",  String.class, null);
//        summaryTable.addContainerProperty("Process definition ID",  String.class, null);
//        summaryTable.addContainerProperty("Process definition URL", String.class, null);
//        summaryTable.addContainerProperty("Variables",  String[].class, null);

        // Rows
        if (taskList.size() >= 1) {
            int i = 0;
            for (Task task : taskList) {
                summaryTable.addItem(new Object[]{task.getId(),
                        task.getUrl(),
//                        task.getOwner(),
//                        task.getAssignee(),
//                        task.getDelegationState(),
//                        task.getName(),
//                        task.getDescription(),
//                        task.getCreateTime(),
//                        task.getDueDate(),
//                        task.getPriority(),
//                        task.getSuspended(),
//                        task.getTaskDefinitionKey(),
//                        task.getTenantId(),
//                        task.getCategory(),
//                        task.getFormKey(),
//                        task.getParentTaskId(),
//                        task.getParentTaskURL(),
//                        task.getExecutionId(),
//                        task.getExecutionURL(),
//                        task.getProcessDefinitionId(),
//                        task.getProcessDefinitionURL()
//                        task.getVariables()
                        },
                        ++i);
            }

            // Show exactly the currently contained rows (items)
            summaryTable.setPageLength(summaryTable.size());
        } else {
            summaryTable.addItem(new Object[]{"0","No task found"},0);

        }

        layout.addComponent(summaryTable);
    }

    private void createActivitiTaskTableDetail(VerticalLayout layout) {
        detailTable.setSelectable(true);

            // Columns
        detailTable.addContainerProperty("ID", String.class, null);
//        detailTable.addContainerProperty("URL",  String.class, null);
//        detailTable.addContainerProperty("Owner", String.class, null);
//        detailTable.addContainerProperty("Assignee",  String.class, null);
//        detailTable.addContainerProperty("Delegation State",  String.class, null);
        detailTable.addContainerProperty("Name", String.class, null);
//        detailTable.addContainerProperty("Description",  String.class, null);
        detailTable.addContainerProperty("Create time", String.class, null);
//        detailTable.addContainerProperty("Due date",  String.class, null);
        detailTable.addContainerProperty("Priority", String.class, null);
        detailTable.addContainerProperty("Suspended", String.class, null);
//        detailTable.addContainerProperty("Task definition key",  String.class, null);
//        detailTable.addContainerProperty("Tenant ID", String.class, null);
//        detailTable.addContainerProperty("Category",  String.class, null);
//        detailTable.addContainerProperty("Form key",  String.class, null);
//        detailTable.addContainerProperty("Parent task ID", String.class, null);
//        detailTable.addContainerProperty("Parent task URL",  String.class, null);
        detailTable.addContainerProperty("Execution ID", String.class, null);
//        detailTable.addContainerProperty("Execution URL",  String.class, null);
        detailTable.addContainerProperty("Process Instance ID", String.class, null);
        detailTable.addContainerProperty("Process definition ID",  String.class, null);
//        detailTable.addContainerProperty("Process definition URL", String.class, null);
            //table.addContainerProperty("Variables",  String[].class, null);
        detailTable.addContainerProperty("Action", Button.class, null);

            // Rows
        if(taskList.size() >= 1) {
            int i = 0;
            for (Task task : taskList) {
                task.getAction().setCaption("Complete");
                task.getAction().setData("row" + i);
                task.getAction().addListener(new TaskActionListener()); //TODO

                detailTable.addItem(new Object[]{task.getId(),
//                        task.getUrl(),
//                        task.getOwner(),
//                        task.getAssignee(),
//                        task.getDelegationState(),
                        task.getName(),
//                        task.getDescription(),
                        task.getCreateTime(),
//                        task.getDueDate(),
                        task.getPriority(),
                        task.getSuspended(),
//                        task.getTaskDefinitionKey(),
//                        task.getTenantId(),
//                        task.getCategory(),
//                        task.getFormKey(),
//                        task.getParentTaskId(),
//                        task.getParentTaskURL(),
                        task.getExecutionId(),
//                        task.getExecutionURL(),
                        task.getProcessInstanceId(),
                        task.getProcessDefinitionId(),
//                        task.getProcessDefinitionURL()
//                        task.getVariables()
                        task.getAction()
                        },
                        "row" + i);
                i += 1;
            }

            // Show exactly the currently contained rows (items)
            detailTable.setPageLength(detailTable.size());
        } else {
            detailTable.addItem(new Object[]{"0","None", "None", "None", "None", "None", "None", new Button("No action required")},0);
        }

        layout.addComponent(detailTable);
    }

    private List<Task> fetchTaskDataFromActivitiEngine() {
        taskList = activitiRESTClient.getTaskList();
        if(taskList.size() >= 1) {
            System.out.println("First element ID: " + taskList.get(0).getId());
        }
        return taskList;
    }

    private List<TaskHistory> fetchHistoricTaskDataFromActivitiEngine() {
        historicTaskList = activitiRESTClient.getHistoricActivitiInstances();
        if(historicTaskList.size() >= 1) {
            System.out.println("First element ID: " + historicTaskList.get(0).getId());
        }
        return historicTaskList;
    }

    private MenuBar createMenuBar(MenuBar.Command menuCommand, MenuBar.Command inboxCommand, MenuBar.Command archivedCommand) {
        MenuBar menuBar = new MenuBar();

        MenuItem tasks = menuBar.addItem("Tasks", null, null);
        MenuItem processes = menuBar.addItem("Processes", null, null);
        MenuItem reports = menuBar.addItem("Reports", null, null);
        MenuItem manage = menuBar.addItem("Manage", null, null);

        MenuItem inbox = tasks.addItem("Inbox"+"("+inboxCount+")", null, inboxCommand);
        tasks.addSeparator();
        MenuItem queued = tasks.addItem("Queued", null, menuCommand);
        tasks.addSeparator();
        MenuItem involved = tasks.addItem("Involved", null, menuCommand);
        tasks.addSeparator();
        MenuItem archived = tasks.addItem("Archived"+"("+archivedCount+")", null, archivedCommand);

        MenuItem myInstances = processes.addItem("My instances", null, null);
        processes.addSeparator();
        MenuItem deployedProcessDefinition = processes.addItem("Deployed process definitions", null, null);
        return menuBar;
    }

    private class TaskActionListener implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            boolean completed = false;
            String rowId = (String) event.getButton().getData();
            Container container = detailTable.getContainerDataSource();
            Item task = container.getItem(rowId);
            Property taskAction = task.getItemProperty("Action");
            String taskId = task.getItemProperty("ID").getValue().toString();
            System.out.println("Button clicked : " + taskId);

            completed = activitiRESTClient.completeTask(taskId);

            if (completed == true) {
                System.out.println("Status: " + ((completed) ? "completed":"failed"));
                taskAction.setValue(new Button("Done"));
                detailTable.removeItem(rowId);
            }

        }
    }

    private void createActivitiHistoricInstancesTable(VerticalLayout layout) {

        // Columns
        historicActivitiInstancesTable.addContainerProperty("Count", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("ID", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("Activity ID", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("Activity Name", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("Activity Type", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("Process Definition ID", String.class, null);
//        historicActivitiInstancesTable.addContainerProperty("Process Definition URL", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("Process Instance ID", String.class, null);
//        historicActivitiInstancesTable.addContainerProperty("Process Instance URL", String.class, null);
//        historicActivitiInstancesTable.addContainerProperty("Execution ID", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("Task ID", String.class, null);
//        historicActivitiInstancesTable.addContainerProperty("Called Process Instance ID", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("Asignee", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("Start Time", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("End Time", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("Duration in Millis", String.class, null);
        historicActivitiInstancesTable.addContainerProperty("Tenant ID", String.class, null);


        // Rows
        if(historicTaskList.size() >= 1) {
            int i = 0;
            for (TaskHistory taskHistory : historicTaskList) {
                historicActivitiInstancesTable.addItem(new Object[]{(i+1)+"",
                        taskHistory.getId(),
                        taskHistory.getActivityId(),
                        taskHistory.getActivityName(),
                        taskHistory.getActivityType(),
                        taskHistory.getProcessDefinitionId(),
//                        taskHistory.getProcessDefinitionUrl(),
                        taskHistory.getProcessInstanceId(),
//                        taskHistory.getProcessInstanceUrl(),
//                        taskHistory.getExecutionId(),
                        taskHistory.getTaskId(),
//                        taskHistory.getCalledProcessInstanceId(),
                        taskHistory.getAssignee(),
                        taskHistory.getStartTime(),
                        taskHistory.getEndTime(),
                        taskHistory.getDurationInMillis(),
                        taskHistory.getTenantId()
                        },
                        "row" + i);
                i += 1;
            }

            // Show exactly the currently contained rows (items)
            historicActivitiInstancesTable.setPageLength(historicActivitiInstancesTable.size());
        } else {
            historicActivitiInstancesTable.addItem(new Object[]{"0","None", "None", "None", "None", "None", "None", "None", "None", "None", "None", "None", "None", "None", "None", "None"},0);
        }

        layout.addComponent(historicActivitiInstancesTable);
    }

}


