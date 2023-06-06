package com.sang.task;

import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("mulitiInstanceCompleteTask")
public class MulitiInstanceCompleteTask implements Serializable {
    /**
     * 完成任务是需要触发的方法
     * @param execution
     * @return
     *     false 表示会签任务还没有结束
     *     true 表示会签任务结束了
     */
    public boolean completeTask(DelegateExecution execution) {
        System.out.println("总的会签任务数量：" + execution.getVariable("nrOfInstances")
                + "当前获取的会签任务数量：" + execution.getVariable("nrOfActiveInstances")
                + " - " + "已经完成的会签任务数量：" + execution.getVariable("nrOfCompletedInstances"));
        //有一个人同意就通过
        Boolean flag = (Boolean) execution.getVariable("flag");
        System.out.println("当前意见："+flag);
        return flag;
    }
}
