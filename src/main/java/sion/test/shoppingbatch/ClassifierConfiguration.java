package sion.test.shoppingbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * STUDY - ClassifierCompositeItemProcessor
 * 여러개의 프로세서 중에서 조건에 맞는 프로세서를 선택해서 작업을 처리하고 싶을 때
 */
@Configuration
@RequiredArgsConstructor
public class ClassifierConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .<ProcessorInfo, ProcessorInfo>chunk(10)
            .reader(new ItemReader<ProcessorInfo>() {
                int i = 0;

                @Override
                public ProcessorInfo read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                    i++;
                    ProcessorInfo processorInfo = ProcessorInfo.builder().id(i).build();
                    return i > 3 ? null : processorInfo;
                }
            })
            .processor(customItemProcessor())
            .writer(items -> System.out.println(items))
            .build();
    }

    /**
     * ClassifierCompositeItemProcessor가 가지고 있는 classiFier에게 어떤 프로세서를 선택할 것인지 선택권을 넘긴다
     * classifier는 내부적으로 processorMap을 가지고 있다
     * 조건에 따라 classifier가 하나의 프로세서를 리턴하고, 그렇게 반환된 프로세서를 가지고 작업을 처리한다
     *
     */
    @Bean
    public ItemProcessor<? super ProcessorInfo, ? extends ProcessorInfo> customItemProcessor() {

        ClassifierCompositeItemProcessor<ProcessorInfo, ProcessorInfo> processor = new ClassifierCompositeItemProcessor<>();

        ProcessorClassifier<ProcessorInfo, ItemProcessor<?, ? extends ProcessorInfo>> classifier = new ProcessorClassifier<>();
        Map<Integer, ItemProcessor<ProcessorInfo, ProcessorInfo>> processorMap = new HashMap<>();
        processorMap.put(1, new CustomItemProcessor11());
        processorMap.put(2, new CustomItemProcessor22());
        processorMap.put(3, new CustomItemProcessor33());

        classifier.setProcessorMap(processorMap);
        processor.setClassifier(classifier);

        return processor;
    }
}
