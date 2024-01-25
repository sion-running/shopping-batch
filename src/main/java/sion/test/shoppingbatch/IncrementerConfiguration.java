package sion.test.shoppingbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;


@org.springframework.context.annotation.Configuration
@RequiredArgsConstructor
public class IncrementerConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     *  STUDY
     *  incrementer() api
     *  Job Launcher에서 job을 수행시킬 때 job과 parameters를 받는데,
     *  job parameters는 job을 식별할 수 있는 값이다
     *  따라서, job parameters가 동일하면 job을 두 번 실행시킬 수 없다 (job failed일 경우는 재시작 가능)
     *
     *  그러나, 어떤 경우에는 실패가 아니더라도 재시작이 필요할 수가 있다
     *  그럴 때는 기존의 job parameters의 값을 가지고도 job을 여러 번 실행시키고자 할 때 사용한다
     *  기본적으로, 스프링부트에서 RunIdIncrementer라는 구현체를 지원하며
     *  커스텀하게 구현해서 사용하는 것도 가능하다
     *
     */
    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .start(step1())
                .next(step2())
                .incrementer(new CustomJobParametersIncrementer())
//                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step1 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step2 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }

}