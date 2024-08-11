package com.aman.jobms.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aman.jobms.job.dto.JobDTO;

@RestController
@RequestMapping("/jobs")
public class Jobcontroller {
	
	@Autowired
	private JobService jobService;
	
	@PostMapping("/save")
	public ResponseEntity<String>  createJob(@RequestBody Job job) {
		jobService.save(job);
		return new ResponseEntity<>("Job added successfully",HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public ResponseEntity<List<JobDTO>> getAllJob() {
		return ResponseEntity.ok(jobService.getAllJob());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<JobDTO> getJobId(@PathVariable Long id) {
		JobDTO jobDTO = jobService.getJobById(id);
		if (jobDTO!=null) {
			return new ResponseEntity<>(jobDTO,HttpStatus.OK);
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		boolean deleted=jobService.deleteJobById(id);
		if (deleted) {
			return new ResponseEntity<>("Job deleted Successfully",HttpStatus.OK);
			
		}
		
		return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
	}
	@PutMapping("/{id}")
	public ResponseEntity<String> updateJob(@RequestBody Job updateJob ,@PathVariable Long id){
		boolean updated=jobService.updateJob(updateJob,id);
		if (updated) {
			
			return new ResponseEntity<>("Job updated Successfully",HttpStatus.OK);
			
		}
		return new ResponseEntity<>("Job Not found",HttpStatus.NOT_FOUND);
	}

}
