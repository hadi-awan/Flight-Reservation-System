# Flight Reservation System

A simple version of a Flight Reservation System that models flights departing from Pearson airport during a single day.

## Description

This project implements concepts from the Java Programming Language including objects and classes, inheritance, interfaces, Java Collections, file IO and exceptions. Furthermore, this Flight Reservation system makes use of data structures including an arraylist, and a tree map to enhance efficiency. The goal of this assignment was to allow a user to parse input from a text file "flights.txt", and list information about the flights contained in the text file. Furthermore, I improved the functionality of this project to allow a user to book and/or cancel a flight for a given passenger. In addition, I modified my program to allow for different classes of flights, including: SHORTHAUL (economy class), MEDIUMHAUL (economy class), and LONGHAUL(first class). Finally, I added custom exception classes to catch errors throughout my program. 

### Executing Program

When executing the program, make sure to run the file called FlightReservationSystem.java. Also, make sure that the "flights.txt" file is stored in your current directory.

When running the program, you have 6 options to input:

1) **LIST:** lists flight information for all flights
2) **RES flight name passport seat:** reserve a flight for passenger with name, passport and specified seat. For example: res UA267 Hadi DD1234 7B. A first class seat is designated by a row, one of ABCD and a ‘+’ character (e.g. 4A+)
3) **CANCEL flight name passport:** cancel a reservation on flight for passenger with name and passport
4) **SEATS flight:** prints out the seats on this flight. If seat is occupied, print “XX “ instead of the seat string
5) **PASMAN flight:** prints the passenger manifest for this flight (i.e information about all passengers)
6) **MYRES:** prints all reservations

## Author
Hadi Awan


