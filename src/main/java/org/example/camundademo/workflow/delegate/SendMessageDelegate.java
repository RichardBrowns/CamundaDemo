package org.example.camundademo.workflow.delegate;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.jboss.logging.Logger;

public class SendMessageDelegate implements JavaDelegate {
    private static final Logger LOG = Logger.getLogger(SendMessageDelegate.class);
    private static final String MESSAGE_NAME = "message";
    private static final String DEFAULT_BUSINESS_KEY = "messageBusinessKey";

    private String businessKey = DEFAULT_BUSINESS_KEY; // 默认业务键

    public SendMessageDelegate() {
        // 如果需要在构造函数中传入业务键，可以在这里设置
    }

    public SendMessageDelegate(String businessKey) {
        this.businessKey = businessKey != null ? businessKey : DEFAULT_BUSINESS_KEY;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOG.info("Starting to execute SendMessageDelegate");

        try {
            MessageCorrelationBuilder correlationBuilder = delegateExecution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation(MESSAGE_NAME);

            if (!businessKey.equals(DEFAULT_BUSINESS_KEY)) {
                correlationBuilder.processInstanceBusinessKey(businessKey);
            }

            correlationBuilder.correlate();
            LOG.info("Message successfully correlated");
        } catch (ProcessEngineException e) {
            LOG.error("Error while correlating message", e);
            throw new RuntimeException("Failed to correlate message", e);
        }

        LOG.info("Finished executing SendMessageDelegate");
    }
}
