package com.allcom;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HolidayRequest {

    public static void main(String[] args) {

        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();

        // 从ProcessEngine 对象创建 RepositoryService对象；后者用于部署流程定义文件
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("process/holiday-request.bpmn20.xml")
                .deploy();

        // We can now verify that the process definition is known to the engine (and learn a bit about the API) by querying it
        // through the API. This is done by creating a new ProcessDefinitionQuery object through the RepositoryService.
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());

        // We now have the process definition deployed to the process engine, so process instances can be started using
        // this process definition as a blueprint.

        // To start the process instance, we need to provide some initial process variables. Typically, you’ll get
        // these through a form that is presented to the user or through a REST API when a process is triggered by something automatic.
        // In this example, we’ll keep it simple and use the java.util.Scanner class to simply input some data on the command line:
        Scanner scanner = new Scanner(System.in);

        System.out.println("Who are you?");
        String employee = scanner.nextLine();

        System.out.println("How many holidays do you want to request?");
        Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

        System.out.println("Why do you need them?");
        String description = scanner.nextLine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        // 以下的key就是holiday-request.bpmn20.xml里的process id ：<process id="holidayRequest" name="Holiday Request" isExecutable="true">
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);

        // 查询task
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
        }

        System.out.println("Which task would you like to complete?");
        int taskIndex = Integer.valueOf(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());

        System.out.println(processVariables.get("employee") + " wants " +
                processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");

        boolean approved = scanner.nextLine().toLowerCase().equals("y");
        variables = new HashMap<String, Object>();
        variables.put("approved", approved);
        taskService.complete(task.getId(), variables);

        // 历史数据查询
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities =
          historyService.createHistoricActivityInstanceQuery()
           .processInstanceId(processInstance.getId())
           .finished()
           .orderByHistoricActivityInstanceEndTime().asc()
           .list();

        for (HistoricActivityInstance activity : activities) {
          System.out.println(activity.getActivityId() + " start at:" + activity.getStartTime() + " end at:" + activity.getEndTime() + " took " + activity.getDurationInMillis() + " milliseconds");
        }

    }
}