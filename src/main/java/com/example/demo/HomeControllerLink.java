package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;

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
        // be sure to pass in a list of all the resumes which belong to the current user
        model.addAttribute("allResumes", user.getResumes());

        // grab the job that was passed in
        Job job = jobRepository.findById(jobId).get();
        // add "job" to the model
        model.addAttribute("job", job);

        // grab today's date
        LocalDate today = LocalDate.now();

        // hook up LINK with USER and JOB ::
        Link formLink = new Link(user, job, today);
        linkRepository.save(formLink);

        user.addLink(formLink);
        userRepository.save(user);

        job.addLink(formLink);
        jobRepository.save(job);

        // possibly need to save link to linkRepository again?
        linkRepository.save(formLink);

        model.addAttribute("link", formLink);
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
        Job job = link.getJob();
        ArrayList<String> keywords = job.getKeywordList();

        // set the 80% goal based on job's number of keywords
        int keywordCount = keywords.size();
        int passCount = (keywordCount * 8) / 10;
        System.out.println("keywordCount :: " + keywordCount +
                "\npassCount :: " + passCount);

        // do the matching logic
        String content = link.getResume().getContent();
        int matchCount = 0;
        for(String keyword : keywords){
            if (content.contains(keyword)){
                matchCount++;
            }
        }
        System.out.println("matchCount :: " + matchCount);

        // set link.status to either "Rejected" or "Accepted"
        if(matchCount >= passCount){
            System.out.println("matchCount >= passCount :: " + matchCount + " >= " + passCount +
                    "\nset status to 'Accepted'");
            link.setStatus("Accepted");
        }
        else {
            System.out.println("matchCount < passCount :: "  + matchCount + " < " + passCount +
                    "\nset status to 'Rejected'");
            link.setStatus("Rejected");
        }

        linkRepository.save(link);
        return "redirect:/";

    }


}
