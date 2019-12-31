package com.roselake.jbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private LinkRepository linkRepository;

    @GetMapping("register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "index";
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/search")
    public String search(@RequestParam("search") String s, Model model) {

        Iterable<Job> jobs = jobRepository.findByDescriptionContainingIgnoreCaseOrTitleContainingIgnoreCase(s, s);
        ArrayList<Job> myjobs = new ArrayList<>();
        for ( Job job : jobs ) {
            myjobs.add(job);
        }

        model.addAttribute("jobs", myjobs);
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "joblist";
    }
    @RequestMapping("/joblist")
    public String joblist(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "joblist";
    }

    @RequestMapping("/appeal/{id}")
    public String appeal(Model model, @PathVariable("id") long id) {
        model.addAttribute("link", linkRepository.findById(id).get());
        return "appealform";
    }

    @PostMapping("/processappeal/{id}")
    public String processappeal(@RequestParam("explain") String s, @PathVariable("id") long id) {
        Link link = linkRepository.findById(id).get();
        String subject = "Appeal:" + link.getStatus();
        link.setStatus("Pending Appeal");
        linkRepository.save(link);
        EmailService mailer = new EmailService();

        try {
            mailer.sendPlainTextEmail(subject, s);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/";
    }


}
