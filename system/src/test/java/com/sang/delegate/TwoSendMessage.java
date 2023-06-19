package com.sang.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class TwoSendMessage implements JavaDelegate {
    /**
     * 触发发送邮件的操作
     * @param delegateExecution
     */
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("消息2");
    }
}
