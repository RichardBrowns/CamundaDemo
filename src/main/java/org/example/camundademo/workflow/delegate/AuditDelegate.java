package org.example.camundademo.workflow.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jboss.logging.Logger;

public class AuditDelegate implements JavaDelegate {
    private static final Logger LOG = Logger.getLogger(AuditDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            LOG.info("审核流程的服务任务回调完成");
        } catch (Exception e) {
            LOG.error("在执行审核流程服务任务回调时遇到错误", e);
            throw new RuntimeException("Failed to execute audit delegate", e);
        }
    }
}