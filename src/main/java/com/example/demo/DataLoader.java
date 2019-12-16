package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

@Component

public class DataLoader implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    ResumeRepository resumeRepository;

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void run(String... strings) throws Exception {

        if (roleRepository.count() == 0) {

            //*******************************************
            // Making Roles :: for Security Layer
            //*******************************************
            roleRepository.save(new Role("USER"));
            roleRepository.save(new Role("ADMIN"));
            Role adminRole = roleRepository.findByName("ADMIN");
            Role userRole = roleRepository.findByName("USER");

            //*******************************************
            // Making Users :: for Security Layer
            //*******************************************
            passwordEncoder = new BCryptPasswordEncoder();
            User user = new User("user@user.com", passwordEncoder.encode("user"),
                    "UserFirstName", "UserLastName",
                    true, "user");
            user.setRoles(Arrays.asList(userRole));
            userRepository.save(user);

            User admin = new User("admin@admin.com", passwordEncoder.encode("admin"),
                    "AdminFirstName", "AdminLastName",
                    true, "admin");
            admin.setRoles(Arrays.asList(adminRole));
            userRepository.save(admin);

            //*******************************************
            // Making Jobs ::
            // in DATA LOADER, keep job titles unique for building LINKS by hand!
            //*******************************************
            jobRepository.save(new Job("Software Engineering",
                    "The Software Engineer position is the core role within the Software Development Group. " +
                            "Members of this role are responsible for creation and maintenance of the majority of company's " +
                            "product code bases.",
                    LocalDate.of(2019, 10, 10),
                    new ArrayList<>(Arrays.asList("Responsive Design", "HTML", "CSS", "JavaScript", "JSON", "REST", "SOAP",
                            "Cloud Configuration Management", "full systems development lifecycle", "API interfaces",
                            "analytical", "communication", "AngularJS")),
                    new ArrayList<>(Arrays.asList("What is SDLC OR Software Development Life Cycle?",
                            "What are the different types of models available in SDLC?",
                            "Explain the role of a Software Project Manager?"))));
            Job jobSoftwareEng = jobRepository.findByTitleContains("Software Engineer");

            jobRepository.save(new Job("Web Developer",
                    "From intuitive information architecture to visually appealing UI, we implement streamlined, " +
                            "functional, and responsive websites for the best user experience and successful ROI. " +
                            "Together our team of strategists, designers, developers, and marketers work with our clients " +
                            "to ensure our end result is the start of a new and engaging online presence for their business.",
                    LocalDate.of(2019, 10, 11),
                    new ArrayList<>(Arrays.asList("HTML5", "CSS3", "LESS", "SASS", "AngularJS", "Angular", "Git", "AWS",
                            "Python", "D3", "Agile/Scrum", "cyber security", "multitask", "collaborative", "typing")),
                    new ArrayList<>(Arrays.asList("What is Functional programming",
                            "What is the difference between classical inheritance and prototypal inheritance",
                            "What are the pros and cons of functional programming vs object-oriented programming?"))));
            Job jobWebDev = jobRepository.findByTitleContains("Web Developer");

            jobRepository.save(new Job("Database Developer",
                    "The Database Developer will be responsible for managing company data infrastructure and " +
                            "assets, assisting in the planning, design and implementation of company data interfaces and " +
                            "data management components.",
                    LocalDate.of(2019, 10, 12),
                    new ArrayList<>(Arrays.asList("database coding", "performance tuning", "troubleshooting", "ETL Technologies",
                            "C", "C++", "Python", "Java", "PostgreSQL", "Oracle SQL", "NoSQL", "Scylla", "Apache Cassandra", "Redis", "analytical")),
                    new ArrayList<>(Arrays.asList("What are DMVs?", " How are transactions used?",
                            "What are DBCC commands?"))));
            Job jobDatabase = jobRepository.findByTitleContains("Database Developer");


            jobRepository.save(new Job("Android Developer",
                    "We're looking for a developer who is passionate about building high quality Android apps. " +
                            "The ideal candidate will work closely with other developers to bring designs to life.",
                    LocalDate.of(2019, 10, 13),
                    new ArrayList<>(Arrays.asList("Java", "Kotlin", "Android Studio", "Objective-C", "Swift", "XCode",
                            "mobile product development", "visual Studio App Center", "typing", "collaborative")),
                    new ArrayList<>(Arrays.asList("What’s the difference between an implicit and an explicit intent?",
                            " When should you use a Fragment, rather than an Activity?",
                            " What is a ThreadPool? And is it more effective than using several separate Threads?"))));
            Job jobAndroid = jobRepository.findByTitleContains("Android Developer");


            jobRepository.save(new Job("QA Tester",
                    "We are looking for a passionate, experienced engineer with a strong background in programming" +
                            " combined with a passion for software quality and test automation.",
                    LocalDate.of(2019, 10, 14),
                    new ArrayList<>(Arrays.asList("SDLC", "UFT", "VBScripts", "Agile", "Scrum", "JIRA", "qTest", "SQL",
                            "test strategies", "test plans", "fast-paced", "presentation", "interpersonal", "team player")),
                    new ArrayList<>(Arrays.asList("Explain how you distinguish a symptom vs. a cause when testing.",
                            "What testing methods are you familiar with? Do you have a favorite?",
                            "What is a ThreadPool? Can you explain the SDLC and Agile methodology"))));
            Job jobQATester = jobRepository.findByTitleContains("QA Tester");


            //*******************************************
            // Making Resumes ::
            // be sure to give them UNIQUE TITLES
            //*******************************************

            // QA RESUME
            resumeRepository.save(new Resume("QATester100", "\"SDLC\", \"UFT\", \"VBScripts\", \"Agile\", \"Scrum\", \"JIRA\", \"qTest\", \"SQL\", \"test strategies\", \"test plans\", \"fast-paced\", \"presentation\", \"interpersonal\", \"team player\""));
            Resume qa100 = resumeRepository.findByNameContains("QATester100");
            qa100.setUser(user);
            resumeRepository.save(qa100);
            user.addResume(qa100);
            userRepository.save(user);

            // ANDROID RESUME
            resumeRepository.save(new Resume("Android100", "Java”,”Kotlin”, “Android Studio”, “Objective-C”, “Swift”, “XCode”, \"mobile product development”, “Visual Studio App Center\"\n"));
            Resume android100 = resumeRepository.findByNameContains("Android100");
            android100.setUser(user);
            resumeRepository.save(android100);
            user.addResume(android100);
            userRepository.save(user);

            // WEB DEV RESUME
            resumeRepository.save(new Resume("WebDev100", "\"HTML5\", \"CSS3\", \"LESS\", \"SASS\", \"AngularJS\", \"Angular\", \"Git\", \"AWS\", \"Python\", \"D3\", \"Agile/Scrum\", \"cyber security\", \"multitask\""));
            Resume webDev100 = resumeRepository.findByNameContains("WebDev100");
            webDev100.setUser(user);
            resumeRepository.save(webDev100);
            user.addResume(webDev100);
            userRepository.save(user);

//            resumeRepository.save(new Resume("Android75", "Java”,”Kotlin”, “Android Studio”, “Objective-C”, “Swift”, “XCode”"));
//            Resume android75 = resumeRepository.findByNameContains("Android75");
//            android75.setUser(user);
//            resumeRepository.save(android75);
//            user.addResume(android75);
//            userRepository.save(user);

//            resumeRepository.save(new Resume("SoftwareEng100", "\"Responsive Design\", \"HTML\", \"CSS\", \"JavaScript\", \"JSON\", \"REST\", \"SOAP\", \"Cloud Configuration Management\", \"full systems development lifecycle\", \"API interfaces\", \"analytical\", \"communication\", \"AngularJS\""));
//            Resume se100 = resumeRepository.findByNameContains("SoftwareEng100");
//            se100.setUser(user);
//            resumeRepository.save(se100);
//            user.addResume(se100);
//            userRepository.save(user);

//            resumeRepository.save(new Resume("Database100", "\"database coding\", \"performance tuning\", \"troubleshooting\",\"ETL Technologies\", \"C\", \"C++\", \"Python\", \"Java\", \"PostgreSQL\", \"Oracle SQL\", \"NoSQL\", \"Scylla\", \"Apache Cassandra\", \"Redis\", \"analytical\""));
//            Resume database100 = resumeRepository.findByNameContains("Database100");
//            database100.setUser(user);
//            resumeRepository.save(database100);
//            user.addResume(database100);
//            userRepository.save(user);


            //*******************************************
            // Making LINKS ::
            // be sure to give them UNIQUE TITLES
            //*******************************************

            // create a DID NOT SCHEDULE, use QA RESUME
            linkRepository.save(new Link(LocalDate.of(2019, 12, 10),
                    "Accepted",
                    "linkQA"));
            Link linkQA = linkRepository.findByNameContains("linkQA");
            linkQA.setUser(user);
            linkQA.setResume(qa100);
            linkQA.setJob(jobQATester);
            linkRepository.save(linkQA);

            // create a SCHEDULED INTERVIEW, use ANDROID RESUME
            Link linkAndroid = new Link(LocalDate.of(2019, 12, 13),
                    "Interview Scheduled",
                    "linkAndroid");
            linkRepository.save(linkAndroid);
            linkAndroid = linkRepository.findByNameContains("linkAndroid");
            linkAndroid.setUser(user);
//        userRepository.save(user);
            linkAndroid.setResume(android100);
//        resumeRepository.save(database100);
            linkAndroid.setJob(jobDatabase);
//        jobRepository.save(jobDatabase);
            linkRepository.save(linkAndroid);

            Interview interviewAndroid = new Interview(LocalDate.of(2019, 12, 16),
                    LocalTime.of(12, 15),
                    LocalTime.of(12, 45));
            interviewRepository.save(interviewAndroid);
            interviewAndroid.setLink(linkAndroid);
            interviewRepository.save(interviewAndroid);
            interviewAndroid = interviewRepository.findByLink(linkAndroid);
            linkAndroid.setInterview(interviewAndroid);
            linkRepository.save(linkAndroid);

            // create a SCHEDULED INTERVIEW, use WEB DEV RESUME
            linkRepository.save(new Link(LocalDate.of(2019, 12, 13),
                    "Interview Scheduled",
                    "linkWebDev"));
            Link linkWebDev = linkRepository.findByNameContains("linkWebDev");
            linkWebDev.setJob(jobWebDev);
//        jobRepository.save(jobWebDev);
            linkWebDev.setResume(webDev100);
//        resumeRepository.save(webDev100);
            linkWebDev.setUser(user);
//        userRepository.save(user);
            linkRepository.save(linkWebDev);

            Interview interviewWebDev = new Interview(LocalDate.of(2019, 12, 16),
                    LocalTime.of(14, 0),
                    LocalTime.of(14, 30));
            interviewRepository.save(interviewWebDev);
            interviewWebDev.setLink(linkWebDev);
            interviewRepository.save(interviewWebDev);
            interviewWebDev = interviewRepository.findByLink(linkWebDev);
            linkWebDev.setInterview(interviewWebDev);
            linkRepository.save(linkWebDev);
        }
    }
}
