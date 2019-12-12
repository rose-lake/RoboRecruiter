package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

        model.addAttribute("interview", interview);
        return "scheduleinterview";
    }

    @RequestMapping("/processinterviewform")
    public String loadFromPage(@ModelAttribute Interview interview,
                               @RequestParam("selected-date") String date,
                               @RequestParam("selected-time") String time) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate myDate1 = LocalDate.parse(date, dateFormatter);
        interview.setDateScheduled(myDate1);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime selectedTime = LocalTime.parse(time, timeFormatter);
        interview.setTimeWindowStart(selectedTime);
        interviewRepository.save(interview);
        Link link = linkRepository.findById(interview.getLink().getId()).get();
        link.setStatus("Interview Scheduled");
        linkRepository.save(link);
        return "redirect:/";
    }

    @RequestMapping("/takeinterview/{id}")
    public String takeInterview(@PathVariable("id") long id, Model model) {
        QAWrapper list = new QAWrapper();
        Interview interview = interviewRepository.findById(id).get();
        Job job = interview.getLink().getJob();
        for (String s : job.getTechnicalQuestions()) {
            list.addQA(new QuestionAnswer(s));
        }
        for (String s : Interview.getBehavioralQuestions()) {
            list.addQA(new QuestionAnswer(s));
        }
        model.addAttribute("list", list);
        model.addAttribute("interview", interview);
        return "takeinterview";
    }

    @PostMapping("/processtakeinterview")
    public String processTakeInterview(@ModelAttribute("list") QAWrapper list, @ModelAttribute("interview") Interview interview) throws IOException {

        File file = new File("interview.txt");
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter("interview.txt"));
        for (QuestionAnswer qa : list.getList()) {
            writer.write(qa.getQuestion() + "\n");
            writer.write(qa.getAnswer() + "\n\n");
        }
        writer.close();

        String subject = "Interview for " + interview.getLink().getJob().getTitle();
        String content = "Attached is the interview chat history for a recently conducted interview\n" +
                "Job title: " + interview.getLink().getJob().getTitle() + "\n" +
                "Applicant: " + interview.getLink().getUser().getFirstName() + interview.getLink().getUser().getLastName();

        EmailService mailer = new EmailService();
        try {
            mailer.sendEmailAttachment(subject, content, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/";
    }

}
