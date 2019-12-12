package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeControllerInterview {
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

    @GetMapping("/scheduleinterview/{id}")
    public String index(@PathVariable("id") long linkId,
                        Model model) {

        //preload Today's Date
        LocalDate today = LocalDate.now();
        model.addAttribute("currentDate", today);

        //preload Today's Time for Interview
        LocalTime timeNow = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String timeString = timeNow.format(timeFormatter);
        model.addAttribute("currentTime", timeString);

        // hook link INTO interview
        Link link = linkRepository.findById(linkId).get();
        Interview interview = new Interview(link);
        interviewRepository.save(interview);

        // hook interview INTO link
        link.setInterview(interview);
        linkRepository.save(link);

        model.addAttribute("interview", new Interview(link));
        return "scheduleinterview";
    }

    @RequestMapping("/processinterviewform")
    public String loadFromPage(@ModelAttribute Interview interview,
                               Model model) {
        model.addAttribute("interview", interview);
        return "interviewconfirm";
    }

    //"/takeinterview/{id}"

}
