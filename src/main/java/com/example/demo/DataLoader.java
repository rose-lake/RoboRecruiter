package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component

public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String ... strings) throws Exception{
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));
        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");

        User user =  new User("jim@jim.com", "password", "Jim", "Jimmerson", true, "jim");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        user=new User("admin@admin.com", "password", "Admin", "User", true, "admin");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

        jobRepository.save(new Job("Software Engineering","The Software Engineer position is the core role within the Software Development Group. Members of this role are responsible for creation and maintenance of the majority of company's product code bases.","10/10/2019", new Arrays.asList("Responsive Design", "HTML", "CSS", "JavaScript", "JSON", "REST", "SOAP", "Cloud Configuration Management", "full systems development lifecycle", "API interfaces", "analytical", "communication", "AngularJS"),"")

    }

}
