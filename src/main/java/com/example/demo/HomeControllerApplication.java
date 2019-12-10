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

        // grab the job that was passed in
        Job job = jobRepository.findById(id).get();

        // create a new application object with those two bits set
        // and pass it to the applicationform
        model.addAttribute("application", new Application(user, job));
        return "applicationform";
    }

//    @PostMapping("/processapplication/")
    // application object will need ::
    // resume, selected via... id? @ParamRequest
    // dateApplied -- java.time.LocalDate   also @Param?

    // 1. create application in the database with all values passed in through the form
    //      this sets .status="Submitted"
    // 2. call Application.process()
    //      this sets .status to EITHER "Rejected" or "Accepted"
            // status -- "Submitted"
            // interview [null]
//        return "redirect:/";

}
