# RoboRecruiter

Our application is a job board that automates some of the preliminary aspects of the interview process(resume screening, preliminary 
interview questions) in an effort to save both employers and prospective employees time and money.

[![Screenshot](src/main/resources/static/images/Screenshot (1).png)]


In our application users first are allowed to submit one or many text resumes. They can then search through the available jobs and apply to those they want to with one of the resumes they submitted. Once they apply, their resume is scanned for keywords and if there is an 80% match from the corresponding job's keyword list, their applicaton is accepted and they are allowed to schedule an online interview. If their resume does not contain enough keywords they are rejected immediately.

[![Screenshot](src/main/resources/static/images/Screenshot (2).png)]

The applicant is given a 2 week window in which they must schedule and take the interview. Should they login within the designated interview time, a pop-up will appear letting them know it is time to conduct the interview. Once the applicant completes the interview, the responses are sent to the hiring manager via email.

If the applicant fails to conduct the interview or misses the interview time frame, they are allowed to send an appeal email to the hiring manager with an explanation as to why.

The main technologies we used to develop our applicaton are Java, Thymeleaf, Spring MVC, HTML/CSS, Bootstrap, and H2/SQL.

>Further Expansion/Testing
Some features we talked about but haven't yet implemented are hiring manager functionality, allowing them to login, view relevant applications, and make decisions about appeals/offers. One way we could do this is to implement an in-application inbox that allows them to view all the relevant information inside the apllication itself.

Our program is currently written with H2 but it is able to run using MySQL given one changes the application.properties file.
Login: user, password: user

