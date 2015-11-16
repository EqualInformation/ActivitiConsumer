package com.equalinformation.bpm.poc.consumer;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;
import javax.swing.*;

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

        MenuBar menuBar = createMenuBar(menuCommand);

        layout.addComponent(menuBar);

    }

    private MenuBar createMenuBar(MenuBar.Command menuCommand) {
        MenuBar menuBar = new MenuBar();

        MenuItem tasks = menuBar.addItem("Tasks", null, null);
        MenuItem processes = menuBar.addItem("Processes", null, null);
        MenuItem reports = menuBar.addItem("Reports", null, null);
        MenuItem manage = menuBar.addItem("Manage", null, null);

        MenuItem inbox = tasks.addItem("Inbox", null, menuCommand);
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
