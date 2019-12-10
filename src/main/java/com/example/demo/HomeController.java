package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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
    private ApplicationRepository applicationRepository;

    @GetMapping("register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "registration";
        } else
        {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "index";
    }
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("applications",applicationRepository.findAll());
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/addResume")
    public String addResume(Model model){
        model.addAttribute("resume", new Resume());
        return "resumeform";
    }

    @PostMapping("/processresume")
    public String processResume(@Valid @ModelAttribute Resume resume, @RequestParam("des") String description){
        String[] array = description.split(",");
        ArrayList<String> list = new ArrayList<>();
        for(int i=0; i<array.length; i++){
            list.add(array[i].trim());
        }
        resume.setResumeKeywords(list);
        resumeRepository.save(resume);
        return "redirect:/";
    }

    @RequestMapping("/joblist")
    public String joblist(Model model){
        model.addAttribute("jobs", jobRepository.findAll());
        return "joblist";
    }
}
