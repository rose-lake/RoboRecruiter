package com.roselake.jbc;

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


//        // this is similar to what we had to MOVE OVER before, when dealing with an "empty link"
//        // only create the interview on a successfull processing of the interview
//        // hook link INTO interview
//        Link link = linkRepository.findById(linkId).get();
//        Interview interview = new Interview(link);
//        interviewRepository.save(interview);
//
//        // hook interview INTO link
//        link.setInterview(interview);
//        linkRepository.save(link);

        // grab the associated LINK, and create our new INTERVIEW with link
        Link link = linkRepository.findById(linkId).get();
        model.addAttribute("interview", new Interview(link));
        return "scheduleinterview";
    }

    @PostMapping("/processinterviewform")
    public String loadFromPage(@ModelAttribute Interview interview,
                               @RequestParam("selected-date") String date,
                               @RequestParam("selected-time") String time,
                               Model model) {

        // set the interview DATE scheduled
        // do not have to error-check the date because on the FRONT END we only allow them to select dates in the 14-day window
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate selectedDate = LocalDate.parse(date, dateFormatter);

        // set the interview TIME scheduled
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime selectedTime = LocalTime.parse(time, timeFormatter);

        // if they are scheduling for TODAY,
        // error-check the selectedTime to be sure it's equal to or greater than currentTime
        // this COULD maybe be done on the FRONT END, but for now we'll handle it this way
        if (selectedDate.equals(LocalDate.now()) && selectedTime.isBefore(LocalTime.now())) {

            //preload Today's Date
            LocalDate today = LocalDate.now();
            model.addAttribute("currentDate", today);

            //preload Today's Time for Interview
            LocalTime timeNow = LocalTime.now();
            timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String timeString = timeNow.format(timeFormatter);
            model.addAttribute("currentTime", timeString);

            model.addAttribute("timeMessage", "Please select a time that is after the current time");

            return "scheduleinterview";

        }


        // if we get to here, the selected time + dates were valid
        interviewRepository.save(interview);

        // interview should already have the link set to it (pass-through from form)
        // hook interview INTO link
        Link link = interview.getLink();
        link.setInterview(interview);
        linkRepository.save(link);

        // set the DATE + TIMES
        interview.setDateScheduled(selectedDate);
        interview.setTimeWindowStart(selectedTime);
        interview.setTimeWindowEnd(selectedTime.plusMinutes(30));
        interviewRepository.save(interview);

        // set the LINK status
        link.setStatus("Interview Scheduled");
        linkRepository.save(link);

        return "redirect:/";
    }

    @RequestMapping("/takeinterview/{id}")
    public String takeInterview(@PathVariable("id") long id, Model model) {
        QAWrapper list = new QAWrapper();
        System.out.println("trying to fetch interview with id = " + id + " from interview repository!");
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

    @PostMapping("/processtakeinterview/{id}")
    public String processTakeInterview(@ModelAttribute("list") QAWrapper list,
                                       @PathVariable("id") long id)
            throws IOException {

        Interview interview = interviewRepository.findById(id).get();

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
                "Applicant: " + interview.getLink().getUser().getFirstName() + " " + interview.getLink().getUser().getLastName();

        EmailService mailer = new EmailService();
        try {
            mailer.sendEmailAttachment(subject, content, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // set the LINK status to Pending Offer
        Link link = interview.getLink();
        link.setStatus("Pending Offer");
        linkRepository.save(link);

//        return "redirect:/";
        return "interviewconfirm";
    }

}
