package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class HomeControllerApplication {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    InterviewRepository interviewRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ResumeRepository resumeRepository;

    @GetMapping("/applytojob/{id}")
    public String applyToJob(@PathVariable("id") long id, Model model) {

        // grab currently authenticated user
        User user = userService.getAuthenticatedUser();

        // grab the job that was passed in
        Job job = jobRepository.findById(id).get();

        // grab today's date
        LocalDate today = LocalDate.now();

        // create a new application object with current user and today's date
        // and pass it to the applicationform
        model.addAttribute("application", new Application(user, job.getId(), today));

        // add "job" to the model
        model.addAttribute("job", job);

        // be sure to pass in a list of all the resumes which belong to the current user
        model.addAttribute("allResumes", user.getResumes());

        return "applicationform";

    }

    @PostMapping("/processapplication/")
    public String processApplication(@Valid @ModelAttribute Application application,
                                     BindingResult result,
                                     @RequestParam("selectedResumeId") long selectedResumeId,
                                     Model model) {

        if(result.hasErrors()){
            System.out.println("*** BINDING result had errors ***");
            System.out.println(result.getAllErrors().toString());
            return "applicationform";
        }

        if(!resumeRepository.existsById(selectedResumeId)){
            System.out.println("*** could not find RESUME with id " + selectedResumeId + " in the resumeRepository");
            return "applicationform";
        }

        // 1. save application in the database with new info from form (just resumeId),
        //      and set status to "Submitted"
        application.setResumeId(selectedResumeId);
        application.setStatus("Submitted");
        applicationRepository.save(application);

        // 2. process the Application
        //      this will (internally) set its "status" to EITHER "Rejected" or "Accepted"
        Resume selectedResume = resumeRepository.findById(selectedResumeId).get();
        Job selectedJob = jobRepository.findById(application.getJobId()).get();

        applicationRepository.save(application);
        return "redirect:/";

    }


}
