package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.awt.image.IntegerInterleavedRaster;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

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

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("links", linkRepository.findAll());
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/joblist")
    public String joblist(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        return "joblist";
    }

    @RequestMapping("/appeal/{id}")
    public String appeal(Model model, @PathVariable("id") long id) {
        model.addAttribute("link", linkRepository.findById(id));
        return "appealform";
    }

    @PostMapping("/processappeal")
    public String processappeal(@RequestParam("explain") String s, @ModelAttribute("app") long id) {
        String subject = "Appeal:" + linkRepository.findById(id).get().getStatus();

        EmailService mailer = new EmailService();

        try {
            mailer.sendPlainTextEmail(subject, s);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Failed to sent email.");
            ex.printStackTrace();
        }
        return "redirect:/";
    }

    @RequestMapping("/takeinterview/{id}")
    public String takeInterview(@PathVariable("id") long id, Model model){
        QAWrapper list = new QAWrapper();
        Interview interview = interviewRepository.findById(id).get();
        Job job = interview.getLink().getJob();
        for (String s : job.getTechnicalQuestions()){
            list.addQA(new QuestionAnswer(s));
        }
        for (String s : interview.getBehavorialQuestions())
    }



}
