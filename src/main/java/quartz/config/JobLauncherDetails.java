package quartz.config;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;

import java.util.Map;

public class JobLauncherDetails implements Job {
    public static final String JOB_NAME = "jobName";

    private JobLocator jobLocator;
    private JobLauncher jobLauncher;

    public void setJobLocator(JobLocator jobLocator) {
        this.jobLocator = jobLocator;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {

        this.jobLauncher = jobLauncher;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        try {
            Map<String, Object> jobDataMap = jobExecutionContext.getMergedJobDataMap();
            String jobName = (String) jobDataMap.get(JOB_NAME);
            jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
