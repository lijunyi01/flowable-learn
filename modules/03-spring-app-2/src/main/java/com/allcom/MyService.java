package com.allcom;

import com.allcom.dao.AppRepository;
import com.allcom.entity.Person;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private AppRepository appRepository;

    @Transactional
    public void startProcess(String assignee) {
        Person person = appRepository.findByUsername(assignee);
        Map<String, Object> variables = new HashMap<String, Object>();
        //variables.put("person", person.getUsername());     // 或者可以 variables.put("person", person.getPersonid());
        variables.put("person", person);
        // 以上如果直接用person，会报无法序列化的错误;但如果类Person implements Serializable，就可以了！
        runtimeService.startProcessInstanceByKey("oneTaskProcess",variables);
    }

    @Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }


    public void createDemoUsers() {
        if (appRepository.findAllPerson().size() == 0) {
            appRepository.addPerson(new Person("jbarrez", "Joram", "Barrez", new Date(System.currentTimeMillis())));
            appRepository.addPerson(new Person("trademakers", "Tijs", "Rademakers", new Date(System.currentTimeMillis())));
        }
    }
}
