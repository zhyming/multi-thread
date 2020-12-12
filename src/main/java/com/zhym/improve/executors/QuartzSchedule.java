package com.zhym.improve.executors;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/8 0008 2:40
 */
public class QuartzSchedule {

    public static void main(String[] args) throws SchedulerException {
        JobDetail job = newJob(SimpleJob.class)
                .withIdentity("JOb1", "Group1").build();

        Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0/5 * * * * ?")).build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }



    static class SimpleJob implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println(" ================== " + System.currentTimeMillis());
        }
    }
}
