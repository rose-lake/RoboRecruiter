package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class HomeControllerInterview {
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

    @GetMapping("/schedule/{id}")
    public String index(@RequestParam("id") long applicationId,
            Model model){

        //preload Today's Date for Application
        LocalDate today = LocalDate.now();
        model.addAttribute("currentDate", today);

        //preload Today's Time for Interview
        LocalTime timeNow = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String timeString = timeNow.format(timeFormatter);
        model.addAttribute("currentTime", timeString);

//        , @ModelAttribute Application application, @ModelAttribute Interview interview, @ModelAttribute Job job

        model.addAttribute("interview", new Interview());
        model.addAttribute("application", applicationRepository.findById(applicationId).get());
        return "interviewform";
    }

    @RequestMapping("/processinterviewform")
    public String loadFromPage(@RequestParam("selected-date") String interviewDate,
                               @RequestParam("selected-time") String interviewTime,
                               Model model){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate strSelectedDate = LocalDate.parse(interviewDate, dateFormatter);
        model.addAttribute("interviewDate", strSelectedDate);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try{
            LocalTime selectedTime = LocalTime.parse(interviewTime, timeFormatter);
            model.addAttribute("interviewTime",selectedTime);
            return "confirm";
        } catch (DateTimeParseException e) {
            model.addAttribute("errorMessage", "Please enter all fields!");
            return "form";
        }
    }

}
