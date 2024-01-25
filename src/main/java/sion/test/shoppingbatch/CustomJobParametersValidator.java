package sion.test.shoppingbatch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;

public class CustomJobParametersValidator implements org.springframework.batch.core.JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        if (parameters.getString("name") == null) {
            throw  new JobParametersInvalidException("name parameters not found");
        }
    }
}