package com.example.bpm.config;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.persistence.StrongUuidGenerator;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.camunda.bpm.engine.impl.history.HistoryLevel.HISTORY_LEVEL_FULL;

/**
 * Конфигурация, настраивющая контекст BPM
 */
@ComponentScan("com.example.bpm")
@Configuration
public class BPMEngineConfig {

    /**
     * Бин для создания механизма процессов
     *
     * @param dataSource                          источник данных
     * @param transactionManager                  механизм для управления трензакциями
     * @param bpmnParseListeners                  список слушателей событий БПМ
     * @return механизм создания процессов
     * @throws IOException исключение
     */
    @Bean
    public SpringProcessEngineConfiguration createProcessEngineConfiguration(DataSource dataSource,
                                                                             PlatformTransactionManager transactionManager,
                                                                             List<BpmnParseListener> bpmnParseListeners
    ) throws IOException {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        config.setDataSource(dataSource);
        config.setTransactionManager(transactionManager);
        config.setCustomPreBPMNParseListeners(bpmnParseListeners);
        config.setFailedJobRetryTimeCycle("R0/PT0S");
        config.setDatabaseSchemaUpdate("true");
        config.setMetricsEnabled(false);
        config.setHistoryLevel(HISTORY_LEVEL_FULL);
        //отключаем стандартные неиспользуемые чекеры авторизации
        config.setCommandCheckers(new ArrayList<>());
        //https://docs.camunda.org/manual/7.7/user-guide/process-engine/id-generator/#the-uuid-generator
        //Always use the StrongUuidGenerator for production setups.
        config.setIdGenerator(new StrongUuidGenerator());
        return config;
    }

    /**
     * Сздание фабрики по управлению процессами
     *
     * @param configuration механизм создания процессов
     * @return фабрика по управлению процессами
     */
    @Bean(name = "processEngine")
    public ProcessEngineFactoryBean processEngineFactory(SpringProcessEngineConfiguration configuration) {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(configuration);
        return processEngineFactoryBean;
    }


    /**
     * Создание сервиса {@link RuntimeService}
     *
     * @param processEngine предоставляет доступ ко всем службам, которые раскрывают операции BPM и рабочего процесса
     * @return сервис обеспечивающий доступ к {@link Deployment}s, {@link ProcessDefinition}s и {@link ProcessInstance}s.
     */
    @Bean
    public RuntimeService bpmRuntimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    /**
     * Создает сервис {@link RepositoryService}
     *
     * @param processEngine редоставляет доступ ко всем службам, которые раскрывают операции BPM и рабочего процесса
     * @return служба, обеспечивающая доступ к хранилищу определений и развертываний процессов
     */
    @Bean
    public RepositoryService bpmRepositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }
}
