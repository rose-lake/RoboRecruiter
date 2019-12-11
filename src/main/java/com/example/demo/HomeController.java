package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
//        new EmailService("smtp.mailtrap.io", 25, "c453e71d4d93a3", "aacac8fce692f2", "Hello");
        model.addAttribute("applications", applicationRepository.findAll());
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
        model.addAttribute("application", applicationRepository.findById(id));
        return "appealform";
    }

    @PostMapping("/processappeal")
    public String processappeal(@RequestParam("explain") String s) {
        return "redirect:/";
    }


    //************************************
    // RESUME :: add, list, delete
    //************************************
    @RequestMapping("/addresume")
    public String addResume(Model model) {
        model.addAttribute("resume", new Resume());
        return "resumeform";
    }

    @RequestMapping("/resumelist")
    public String resumeList(Model model) {
        model.addAttribute("resumes", resumeRepository.findAll());
        return "resumelist";
    }

    @RequestMapping("/deleteresume/{id}")
    public String deleteResume(@PathVariable("id") long id, Model model){

        Resume deleteResume = resumeRepository.findById(id).get();
        String deleteResumeName = deleteResume.getName();
        System.out.println("DELETING RESUME with id = " + deleteResume.getId() + " and name = " + deleteResumeName);

        // UN-HOOK resume from user
        User user = deleteResume.getUser();
        user.deleteResumeById(id);
        userRepository.save(user);

        // delete resume from our repository
        resumeRepository.deleteById(id);

        model.addAttribute("infoMessage", "Successfully deleted resume '" + deleteResumeName + "' !");
        model.addAttribute("resumes", resumeRepository.findAll());
        return "resumelist";

    }

    @PostMapping("/processresume")
    public String processResume(@Valid @ModelAttribute Resume resume,
                                BindingResult result,
                                @RequestParam("file") MultipartFile file,
                                Model model)
            throws IOException {

        if (result.hasErrors()) {
            System.out.println("*** BINDING result had errors ***");
            System.out.println(result.getAllErrors().toString());
            return "resumeform";
        }

        if (file.isEmpty()) {
            System.out.println("*** FILE was EMPTY!");
            return "resumeform";
        }

        if (!file.getContentType().equalsIgnoreCase("text/plain")){
            System.out.println("*** FILE was NOT of type 'text/plain'");
            model.addAttribute("fileTypeMessage","Your file type must be 'text/plain'!");
            return "resumeform";
        }

        // if we get here, there were no binding errors and the file was not empty

        // save NAME :: was passed in with the form
        resumeRepository.save(resume);

        // save CONTENT :: read in the TEXT file and save it as STRING
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String st;
        StringBuilder key = new StringBuilder();
        while ((st = br.readLine()) != null) {
            key.append(st).append(" ");
        }
        resume.setContent(key.toString());
        resumeRepository.save(resume);

        // make USER - RESUME LINK
        // add the resume to user
        User currentUser = userService.getAuthenticatedUser();
        currentUser.addResume(resume);
        userRepository.save(currentUser);
        // add the user to resume
        resume.setUser(currentUser);
        resumeRepository.save(resume);

        // test code ::
        System.out.println("*********************** CONTENT of just saved resume :: " +
                "\n\tID = " + resume.getId() +
                "\n\tResume's USER's FIRST NAME = " + resume.getUser().getFirstName() +
                "\n\tNAME = " + resume.getName() +
                "\n\tCONTENT = " + resume.getContent());

        return "redirect:/";
    }


}
