package com.aman.jobms.job;
import java.util.List;

import com.aman.jobms.job.dto.JobDTO;

public interface JobService {
	
	void save(Job job);
	List<JobDTO> getAllJob();
	JobDTO getJobById(Long id);
	boolean deleteJobById(Long id);
	
	boolean updateJob(Job updateJob, Long id);
	
}
