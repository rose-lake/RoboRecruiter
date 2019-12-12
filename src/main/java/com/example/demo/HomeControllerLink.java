package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class HomeControllerLink {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    LinkRepository linkRepository;
    @Autowired
    InterviewRepository interviewRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ResumeRepository resumeRepository;

    @GetMapping("/applytojob/{id}")
    public String applyToJob(@PathVariable("id") long jobId, Model model) {

        // grab currently authenticated user
        User user = userService.getAuthenticatedUser();

//        // debugging sys-outs
//        System.out.println("****** currently authenticated user :: " +
//                "\n\tID = " + user.getId() +
//                "\n\tusername = " + user.getUsername() +
//                "\n\tResumes :: ");
//        int counter = 1;
//        for(Resume resume : user.getResumes()){
//            System.out.println("\n\t**Resume #" + counter + "**" +
//                    "\n\t\tID = " + resume.getId() +
//                    "\n\t\tName = " + resume.getName() +
//                    "\n\t\tContent (trimmed) = " + resume.getContent().substring(0, 25) +
//                    "\n\t\tis connected to user '" + resume.getUser().getUsername() + "'");
//            counter++;
//        }

        // be sure to pass in a list of all the resumes which belong to the current user
        model.addAttribute("allResumes", user.getResumes());
//        System.out.println("+++ added the above resumes to the model as 'allResumes'");

        // grab the job that was passed in
        Job job = jobRepository.findById(jobId).get();
//        // debugging sys-out
//        System.out.println("\n********* applying to JOB :: " +
//                "\n\tID = " + job.getId() +
//                "\n\tTitle = " + job.getTitle() +
//                "\n\tDescription (trimmed) = " + job.getDescription().substring(0, 25));
        // add "job" to the model
        model.addAttribute("job", job);
//        System.out.println("+++ added the above job to model as 'job'");

        // grab today's date
        LocalDate today = LocalDate.now();
//        // debugging sys-out
//        System.out.println("*********** today's date :: " + today);


        // create a new link object with current user, the job being applied to, and today's date
        model.addAttribute("link", new Link(user, job, today));

        return "linkform";

    }

    @PostMapping("/processlink")
    public String processLink(@Valid @ModelAttribute Link link,
                                     BindingResult result,
                                     @RequestParam("selectedResumeId") long selectedResumeId,
                                     Model model) {

        System.out.println("at top of process application method");

        if(result.hasErrors()){
            System.out.println("*** BINDING result had errors ***");
            System.out.println(result.getAllErrors().toString());
            return "linkform";
        }

        if(!resumeRepository.existsById(selectedResumeId)){
            System.out.println("*** could not find RESUME with id " + selectedResumeId + " in the resumeRepository");
            return "linkform";
        }

        // 1. hook LINK + RESUME
        Resume selectedResume = resumeRepository.findById(selectedResumeId).get();
        link.setResume(selectedResume);
        linkRepository.save(link);
        selectedResume.addLink(link);
        resumeRepository.save(selectedResume);

        // 2. set status to "Submitted"
        link.setStatus("Submitted");
        linkRepository.save(link);

        System.out.println("********* just SAVED form's LINK object, with... " +
                "\n\tID = " + link.getId() +
                "\n\tUser = '" + link.getUser().getUsername() + "'" +
                "\n\tJob ID = " + link.getJob().getId() +
                "\n\tResume ID = " + link.getResume().getId() +
                "\n\tDate Applied = " + link.getDateApplied() +
                "\n\tSTATUS = " + link.getStatus());

        // 2. process the LINK :: set link.status to EITHER "Rejected" OR "Accepted"
        // use resume and job objects inside the link
        Job selectedJob = link.getJob();

        // set the 80% goal based on job's number of keywords
        // do the matching logic
        // set link.status to either "Rejected" or "Accepted"


        linkRepository.save(link);
        return "redirect:/";

    }


}
