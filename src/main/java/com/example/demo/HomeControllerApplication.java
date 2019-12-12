package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;

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

//    @RequestMapping("/")
//    public String index(Model model) {
//        model.addAttribute("applications", applicationRepository.findAll());
//        return "index";
//    }

    @GetMapping("/applytojob/{id}")
    public String applyToJob(@PathVariable("id") long id, Model model) {

        // grab currently authenticated user
        User user = userService.getAuthenticatedUser();

        // debugging sys-outs
        System.out.println("****** currently authenticated user :: " +
                "\n\tID = " + user.getId() +
                "\n\tusername = " + user.getUsername() +
                "\n\tResumes :: ");
        int counter = 1;
        for(Resume resume : user.getResumes()){
            System.out.println("\n\t**Resume #" + counter + "**" +
                    "\n\t\tID = " + resume.getId() +
                    "\n\t\tName = " + resume.getName() +
                    "\n\t\tContent (trimmed) = " + resume.getContent().substring(0, 25) +
                    "\n\t\tis connected to user '" + resume.getUser().getUsername() + "'");
            counter++;
        }

        // be sure to pass in a list of all the resumes which belong to the current user
        model.addAttribute("allResumes", user.getResumes());
        System.out.println("+++ added the above resumes to the model as 'allResumes'");

        // grab the job that was passed in
        Job job = jobRepository.findById(id).get();
        // debugging sys-out
        System.out.println("\n********* applying to JOB :: " +
                "\n\tID = " + job.getId() +
                "\n\tTitle = " + job.getTitle() +
                "\n\tDescription (trimmed) = " + job.getDescription().substring(0, 25));
        // add "job" to the model
        model.addAttribute("job", job);
        System.out.println("+++ added the above job to model as 'job'");

        // grab today's date
        LocalDate today = LocalDate.now();
        // debugging sys-out
        System.out.println("*********** today's date :: " + today);


        // THIS VERSUS THAT ::

//        // THIS ::
//        // create a new application object with current user and today's date
//        // and pass it to the applicationform
//        Application formApplication = new Application(user, job.getId(), today);
//        applicationRepository.save(formApplication);
//        // debugging sys-out
//        System.out.println("********* will CREATE new Application object to pass to the form, with... " +
//                "\n\tID = " + formApplication.getId() +
//                "\n\tlinked to User with first name = '" + formApplication.getUser().getFirstName() + "'" +
//                "\n\tfor job with id = " + formApplication.getJobId() +
//                "\n\twith application date = " + formApplication.getDateApplied());
//        model.addAttribute("application", formApplication);
//        System.out.println("+++ added the above application to model as 'application'");

        // THAT ::
        model.addAttribute("application", new Application(user, job.getId(), today));

        return "applicationform";

    }

    @PostMapping("/processapplication")
    public String processApplication(@Valid @ModelAttribute Application application,
                                     BindingResult result,
                                     @RequestParam("selectedResumeId") long selectedResumeId,
                                     Model model) {

        System.out.println("at top of process application method");

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
        System.out.println("********* just SAVED form's Application object, with... " +
                "\n\tID = " + application.getId() +
                "\n\tUser = '" + application.getUser().getUsername() + "'" +
                "\n\tJob ID = " + application.getJobId() +
                "\n\tResume ID = " + application.getResumeId() +
                "\n\tApplication Date = " + application.getDateApplied() +
                "\n\tSTATUS = " + application.getStatus());

        // 2. process the Application
        //      set its "status" to EITHER "Rejected" OR "Accepted"
        Resume selectedResume = resumeRepository.findById(selectedResumeId).get();
        Job selectedJob = jobRepository.findById(application.getJobId()).get();

        applicationRepository.save(application);
        return "redirect:/";

    }


}
