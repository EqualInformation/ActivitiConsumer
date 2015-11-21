package com.equalinformation.bpm.poc.consumer;

import com.equalinformation.bpm.poc.consumer.ws.ActivitiRESTClient;
import com.equalinformation.bpm.poc.consumer.ws.domain.Task;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.MenuBar.MenuItem;

import javax.servlet.annotation.WebServlet;
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

        MenuBar.Command inboxCommand = new MenuBar.Command() {
            public void menuSelected(MenuItem selectedItem) {
                createTaskTable(layout);
            }
        };

        MenuBar menuBar = createMenuBar(menuCommand, inboxCommand);

        layout.addComponent(menuBar);

    }

    private void createTaskTable(VerticalLayout layout) {
        Table table = new Table("Inbox");

        // Columns
        table.addContainerProperty("ID", String.class, null);
        table.addContainerProperty("URL",  String.class, null);
        table.addContainerProperty("Owner", String.class, null);
        //table.addContainerProperty("Asignee",  String.class, null);
        //table.addContainerProperty("Delegation State",  String.class, null);
        //table.addContainerProperty("Name", String.class, null);
        //table.addContainerProperty("Description",  String.class, null);
        //table.addContainerProperty("Create time", String.class, null);
        //table.addContainerProperty("Due date",  String.class, null);
        //table.addContainerProperty("Priority",  String.class, null);
        //table.addContainerProperty("Suspended", String.class, null);
        //table.addContainerProperty("Task Definition ID",  String.class, null);
        //table.addContainerProperty("Tenand ID", String.class, null);
        //table.addContainerProperty("Category",  String.class, null);
        //table.addContainerProperty("Form key",  String.class, null);
        //table.addContainerProperty("Parent task ID", String.class, null);
        //table.addContainerProperty("Parent task URL",  String.class, null);
        //table.addContainerProperty("Execution ID", String.class, null);
        //table.addContainerProperty("Execution URL",  String.class, null);
        //table.addContainerProperty("Process definition ID",  String.class, null);
        //table.addContainerProperty("Process definition URL", String.class, null);
        //table.addContainerProperty("Variables",  String.class, null);

        ActivitiRESTClient activitiRESTClient = new ActivitiRESTClient();
        List<Task> taskList = activitiRESTClient.getTaskList();
        System.out.println("First element ID: "+taskList.get(0).getId());

        // Rows
        table.addItem(new Object[]{taskList.get(0).getId(), taskList.get(0).getUrl(),taskList.get(0).getOwner()}, 1);
        table.addItem(new Object[]{taskList.get(1).getId(), taskList.get(1).getUrl(),taskList.get(1).getOwner()}, 2);
        table.addItem(new Object[]{taskList.get(2).getId(), taskList.get(2).getUrl(),taskList.get(2).getOwner()}, 3);


        // Show exactly the currently contained rows (items)
        table.setPageLength(table.size());

        layout.addComponent(table);
    }

    private MenuBar createMenuBar(MenuBar.Command menuCommand, MenuBar.Command inboxCommand) {
        MenuBar menuBar = new MenuBar();

        MenuItem tasks = menuBar.addItem("Tasks", null, null);
        MenuItem processes = menuBar.addItem("Processes", null, null);
        MenuItem reports = menuBar.addItem("Reports", null, null);
        MenuItem manage = menuBar.addItem("Manage", null, null);

        MenuItem inbox = tasks.addItem("Inbox", null, inboxCommand);
        tasks.addSeparator();
        MenuItem queued = tasks.addItem("Queued", null, menuCommand);
        tasks.addSeparator();
        MenuItem involved = tasks.addItem("Involved", null, menuCommand);
        tasks.addSeparator();
        MenuItem archived = tasks.addItem("Archived", null, menuCommand);

        MenuItem myInstances = processes.addItem("My instances", null, null);
        processes.addSeparator();
        MenuItem deployedProcessDefinition = processes.addItem("Deployed process definitions", null, null);
        return menuBar;
    }

}
