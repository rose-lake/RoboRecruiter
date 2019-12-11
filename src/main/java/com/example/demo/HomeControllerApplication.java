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

        LocalDate today = LocalDate.now();

        // create a new application object with those two bits set
        // and pass it to the applicationform
        model.addAttribute("application", new Application(user, job.getId(), today));
        model.addAttribute("job", job);

        model.addAttribute("allResumes", resumeRepository.findAll());
        return "applicationform";
    }

    @PostMapping("/processapplication/")
    public String processApplication(@Valid @ModelAttribute Application application,
                                     BindingResult result,
                                     @RequestParam("resumeId") long resumeId,
                                     Model model) {
        // application object will need ::
        // resume, selected via... id? @ParamRequest
        // dateApplied -- java.time.LocalDate   also @Param?

        // 1. create application in the database with all values passed in through the form
        //      this sets .status="Submitted"
        // 2. call Application.process()
        //      this sets .status to EITHER "Rejected" or "Accepted"
        // status -- "Submitted"
        // interview [null]
        return "redirect:/";

    }


}
