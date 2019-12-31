package com.roselake.jbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Controller
public class HomeControllerResume {
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

//    // LATER... ONCE EVERYTHING ELSE IS WORKING...
//    // change this to "/archiveresume/{id}" and change the functionality to reflect that...
//    @RequestMapping("/deleteresume/{id}")
//    public String deleteResume(@PathVariable("id") long id, Model model){
//
//        Resume deleteResume = resumeRepository.findById(id).get();
//        String deleteResumeName = deleteResume.getName();
//        System.out.println("DELETING RESUME with id = " + deleteResume.getId() + " and name = " + deleteResumeName);
//
//        // UN-HOOK resume from user
//        User user = deleteResume.getUser();
//        user.deleteResumeById(id);
//        userRepository.save(user);
//
////        // UN-HOOK resume from any links (if any)
////        // is this really necessary ??
////        // NO! Do not do this, or it will mess up your existing LINK,
////        // the LINK should not cease referencing this resume just because you deleted it from your active resumes...
////        // You really shouldn't be running around deleting your resumes...
////        // You can "archive" or "hide" them from your list,
////        // but don't ever actually delete them because for all you know you may have created a LINK using that resume!...
////        // This is why the project description doesn't include deleting. heh.
////        if(deleteResume.getLinks().size() != 0) {
////            for (Link link : deleteResume.getLinks()){
////                // for each link found in deleteResume, unhook deleteResume from inside the link
////                link.setResume(null);
////            }
////        }
//
//        // delete resume from our repository
//        resumeRepository.deleteById(id);
//
//        model.addAttribute("infoMessage", "Successfully deleted resume '" + deleteResumeName + "' !");
//        model.addAttribute("resumes", resumeRepository.findAll());
//        return "resumelist";
//
//    }

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

        if (!Objects.requireNonNull(file.getContentType()).equalsIgnoreCase("text/plain")){
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

        // hook USER and RESUME
        // add the resume to user
        User currentUser = userService.getAuthenticatedUser();
        currentUser.addResume(resume);
        userRepository.save(currentUser);
        // add the user to resume
        resume.setUser(currentUser);
        resumeRepository.save(resume);

        // this is a brand-new RESUME, so there is no LINK for it

        // test code ::
        System.out.println("*********************** CONTENT of just saved resume :: " +
                "\n\tID = " + resume.getId() +
                "\n\tResume's USER's FIRST NAME = " + resume.getUser().getFirstName() +
                "\n\tNAME = " + resume.getName() +
                "\n\tCONTENT = " + resume.getContent());

        return "redirect:/";
    }

}
