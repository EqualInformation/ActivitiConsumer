package com.equalinformation.bpm.poc.consumer.ws.domain;

import com.vaadin.ui.Button;

/**
 * Created by bpupadhyaya on 11/17/15.
 */
public class Task {
    private String id;
    private String url;
    private String owner;
    private String assignee;
    private String delegationState;
    private String name;
    private String description;
    private String createTime;
    private String dueDate;
    private String priority;
    private String suspended;
    private String taskDefinitionKey;
    private String tenantId;
    private String category;
    private String formKey;
    private String parentTaskId;
    private String parentTaskURL;
    private String executionId;
    private String executionURL;
    private String processInstanceId;
    private String processInstanceURL;
    private String processDefinitionId;
    private String processDefinitionURL;
    private String[] variables;
    private Button action;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDelegationState() {
        return delegationState;
    }

    public void setDelegationState(String delegationState) {
        this.delegationState = delegationState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSuspended() {
        return suspended;
    }

    public void setSuspended(String suspended) {
        this.suspended = suspended;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public String getParentTaskURL() {
        return parentTaskURL;
    }

    public void setParentTaskURL(String parentTaskURL) {
        this.parentTaskURL = parentTaskURL;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getExecutionURL() {
        return executionURL;
    }

    public void setExecutionURL(String executionURL) {
        this.executionURL = executionURL;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceURL() {
        return processInstanceURL;
    }

    public void setProcessInstanceURL(String processInstanceURL) {
        this.processInstanceURL = processInstanceURL;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionURL() {
        return processDefinitionURL;
    }

    public void setProcessDefinitionURL(String processDefinitionURL) {
        this.processDefinitionURL = processDefinitionURL;
    }

    public String[] getVariables() {
        return variables;
    }

    public void setVariables(String[] variables) {
        this.variables = variables;
    }

    public Button getAction() {
        if(this.action == null) {
            action = new Button("Action");
        }

        return action;
    }

    public void setAction(Button action) {
        this.action = action;
    }
}

