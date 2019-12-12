# links
https://www.baeldung.com/dates-in-thymeleaf

# how to initialize simply
.of(int year, int month, int dayOfMonth)
Obtains an instance of LocalDate from a year, month and day.

# question:
do we need to add the dependency? (see link above)
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-java8time</artifactId>
    <version>3.0.1.RELEASE</version>
</dependency>

# what we need

## Date
- call to LocalDate.now() to save + see current day
- use localDate.plusDays(14) to save current day plus two weeks

## Time
- use LocalTime.plusMinutes(30) to save user selected Interview Time plus 30 minutes for the Interview Window
- call to LocalTime.now() to save + see current time


### the logic would go something like:


# if Application.status == "Accepted"

# check if we have MISSED the SCHEDULING WINDOW
√ if LocalDate.now().isAfter(link.dateApplied.plusDays(14))
√ set Link.status = "Did Not Schedule"


# if Application.status == "Interview Scheduled"

## check if we are IN the INTERVIEW WINDOW
√ if LocalDate.now() == Interview.dateScheduled
    √ if LocalTime.now().isBefore(Interview.timeWindowEnd) || .equals(windowEnd)
        √ if LocalTime.now().isAfter(Interview.timeWindowStart) || .equals(windowStart)
            √ set Application.status = *"Take Interview"*
    √ ELSE, we've missed the interview.
        √ set Application.status = *"Missed Interview"*
√ else if localDate.now() is AFTER interview scheduled date
    √ set link.status = *"Missed Interview"*


## check if we are PAST the INTERVIEW WINDOW
- if LocalDate.now() == Interview.dateScheduled
- use the ELSE clause of the `if LocalTime.now().isBefore(Interview.timeWindowEnd)`, above... meaning, we are therefore exactly equal to or after the end of the Interview Window... meaning we've missed the interview.
- set Application.status = *"Missed Interview"*


# Documentation

this is an old(er) article but still provides some good basics:
https://howtodoinjava.com/java8/date-and-time-api-changes-in-java-8-lambda/
namely:
LocalDate localDate = LocalDate.now();
System.out.println(localDate.toString());                //2013-05-15
System.out.println(localDate.plusDays(12).toString());   //2013-05-27

the official java docs are less "friendly"/helpful, but here:
https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/time/LocalDate.html
