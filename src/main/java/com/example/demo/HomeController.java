package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
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

    @PostMapping("/search")
    public String search(@RequestParam("search") String s, Model model) {
        model.addAttribute("jobs", jobRepository.findByDescriptionContainingIgnoreCaseOrTitleContainingIgnoreCase(s,s));
        return "joblist";
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
