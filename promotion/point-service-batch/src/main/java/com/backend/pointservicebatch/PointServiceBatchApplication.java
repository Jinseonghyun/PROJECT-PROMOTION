package com.backend.pointservicebatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PointServiceBatchApplication {

	private final JobLauncher jobLauncher;
	private final Job pointBalanceSyncJob;

    public PointServiceBatchApplication(JobLauncher jobLauncher, Job pointBalanceSyncJob) {
        this.jobLauncher = jobLauncher;
        this.pointBalanceSyncJob = pointBalanceSyncJob;
    }

    public static void main(String[] args) {
		SpringApplication.run(PointServiceBatchApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner() {
		return args -> {
			jobLauncher.run(                       // jobLauncher를 사용하여 작업을 실행
					pointBalanceSyncJob,           // 실행할 작업(Job)
					new JobParametersBuilder()
							.addLong("timestamp", System.currentTimeMillis()) // 현재 시간을 타임스탬프로 추가
							.toJobParameters()
			);
		};
	}
}
