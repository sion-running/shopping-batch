package sion.test.shoppingbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ValidatorConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     * STUDY
     * 스프링배치는 JobParametersValidator를 통한 파라미터의 검증을 총 두 번 수행한다
     * 첫 번째는, job 수행 전 jobRepository의 기능이 시작하기 전
     * 두 번째는, job이 실제로 실행되기 전에
     *
     * 1) SimpleJobLauncher 클래스에 보면, job.getJobParametersValidator().validate(jobParameters);
     * 라인 위에 reposotory에서 무엇이든 생성하기 전에 파라미터를 검증한다는 주석이 있음
     *
     * 2) SimpleJob의 부모 클래스인 AbstractJob에, jobParametersValidator 속성이 있고
     * 기본적으로는 DefaultJobParametersValidator()를 할당하는데,
     * 아래 테스트 코드처럼 job을 생성하는 빈에서
     * validator api를 호출해서 구현체(CustomJobParametersValidator)를 넣어주면 그것으로 검증함
     *
     *
     */
    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("batchJob")
                .start(step1())
                .next(step2())
                .next(step3())
//                .validator(new CustomJobParametersValidator())
                .validator(new DefaultJobParametersValidator(new String[]{"name", "date"}, new String[]{"count"})) // 기본으로 제공해주는 구현체
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

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step3 has executed");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}