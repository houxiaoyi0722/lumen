/* 
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */
 
package com.sang.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * @author hxy
 */
@Slf4j
public class GlobalJobListener implements JobListener {

  public String getName() {
    return "GlobalJobListener";
  }

  public void jobToBeExecuted(JobExecutionContext inContext) {
    log.info("Job1Listener says: Job {} : {} Is about to be executed.",inContext.getJobDetail().getKey().getGroup(), inContext.getJobDetail().getKey().getName());
  }

  public void jobExecutionVetoed(JobExecutionContext inContext) {
    log.info("Job1Listener says: Job {} : {} Execution was vetoed.",inContext.getJobDetail().getKey().getGroup(), inContext.getJobDetail().getKey().getName());
  }

  public void jobWasExecuted(JobExecutionContext inContext, JobExecutionException inException) {
    log.info("Job1Listener says: Job {} : {} was executed." ,inContext.getJobDetail().getKey().getGroup(), inContext.getJobDetail().getKey().getName());
  }

}
