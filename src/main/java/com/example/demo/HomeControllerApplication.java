package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/applytojob/{id}")
    public String applyToJob(@PathVariable("id") long id, Model model){

        // grab currently authenticated user
        User user = userService.getAuthenticatedUser();


        Job job = jobRepository.findById(id).get();

        model.addAttribute("application", new Application());

        // when PASSING IN to the NEW APPLICATION form, be sure to pass in...
        //      application.job
        //      application.user
        // already set!

        // application object will need ::
        // resumeId
        // dateApplied -- java.time.LocalDate

        // processApplication CONTROLLER method ::
        // status -- "Submitted"
        // interview [null]

        // 1. create application in the database with all values passed in through the form
        //      this sets .status="Submitted"
        // 2. call Application.process()
        //      this sets .status to EITHER "Rejected" or "Accepted"
        //
        // call Application.process()






        return "redirect:/";
    }

//    @PostMapping("/processapplication/")


}
