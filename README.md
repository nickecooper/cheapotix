CheapoTix is a simple full-stack web application that helps users find cheap NBA tickets and set up email alerts based on their preferred price thresholds. Built with Spring Boot, Thymeleaf, and PostgreSQL, it regularly checks for the latest ticket prices through third-party RESTful APIs, notifying users via email when tickets fall below their chosen threshold.


FEATURES

View Ticket Prices: Display current NBA ticket prices for different arenas.

Price Alerts: Set minimum ticket price thresholds and receive email notifications when the price meets or drops below the set value.

Scheduled Database Updates: fetch the latest ticket prices via Spring Boot scheduled tasks into the database.

Frequency of Email Alerts: Define how often email alerts are recieved.


TECH STACK

Backend: Spring Boot (Java)

Frontend: Thymeleaf (HTML, CSS)

Database: PostgreSQL

Scheduler: Spring Boot Scheduled Tasks

APIs: TicketMaster Discovery API (for updated minimum ticket prices), SendGrid Web API (for sending users email alerts)
