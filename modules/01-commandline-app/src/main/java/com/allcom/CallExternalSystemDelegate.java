package com.allcom;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

// 流程定义文件 holiday-request.bpmn20.xml 里提及（配置）了该类；作为serviceTask（id="externalSystemCall"）的执行代理
public class CallExternalSystemDelegate implements JavaDelegate {

    public void execute(DelegateExecution execution) {
        System.out.println("Calling the external system for employee " + execution.getVariable("employee"));
    }
}
