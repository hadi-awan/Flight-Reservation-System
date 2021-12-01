import java.util.ArrayList;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!
public class FlightReservationSystem
{
    public static void main(String[] args)
    {
        // Create a FlightManager object
        FlightManager manager = new FlightManager();

        // List of reservations that have been made
        ArrayList<Reservation> myReservations = new ArrayList<>();	// my flight reservations

        Scanner scanner = new Scanner(System.in);
        System.out.print(">");

        while (scanner.hasNextLine())
        {
            String inputLine = scanner.nextLine();
            if (inputLine == null || inputLine.equals(""))
            {
                continue;
                // If the user does not input anything, then continue with the program
            }

            // The command line is a scanner that scans the inputLine string
            // For example: list AC201
            Scanner commandLine = new Scanner(inputLine);

            // The action string is the command to be performed (e.g. list, cancel etc)
            String action = commandLine.next();

            if (action == null || action.equals(""))
            {
                continue;
                // If the user does not input anything, then continue with the program
            }

            if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
            {
                return;
                // If the user inputs "Q" or "QUIT", then exit the program with a return statement
            }
            // lists flight information for all flights
            else if (action.equalsIgnoreCase("LIST"))
            {
                manager.printAllFlights();
                //If the user inputs "LIST", then print a list of the flight information in the flights arraylist
            }
            // reserve a flight for passenger with name, passport and specified seat.
            // For example: res UA267 McInerney DD1234 7B.
            // A first class seat is designated by a row, one of ABCD and a ‘+’ character (e.g. 4A+)
            else if (action.equalsIgnoreCase("RES"))
            {
                // If the user inputs "RES", and supplies a flight-number, name, passport-number,
                // and seat-number, then reserve a flight for the passenger.
                // If the seat-number contains a '+' character, then it is a first class seat.
                // Otherwise, it is an economy seat by default.
                // Of course, if the flight is full, or if the flight does not exist,
                // or if the passenger already exists, or if a seat is already occupied,
                // then we should return various exceptions accordingly.
                String flight;
                String name;
                String passport;
                String seat;
                if (commandLine.hasNext())
                {
                    flight = commandLine.next().toUpperCase().trim();
                    if (commandLine.hasNext())
                    {
                        name = commandLine.next();
                        if (commandLine.hasNext())
                        {
                            passport = commandLine.next();
                            if (commandLine.hasNext())
                            {
                                seat = commandLine.next();
                                try
                                {
                                    Reservation reservation = manager.reserveSeatOnFlight(flight, name, passport, seat);
                                    myReservations.add(reservation);
                                    reservation.print();
                                }
                                catch (FlightFullException | FlightNotFoundException | DuplicatePassengerException | SeatOccupiedException ex)
                                {
                                    System.out.println(ex.getMessage());
                                }

                            }
                            else
                            {
                                System.err.println("Seat is required. Enter values in this order Flight Name Passport Seat");
                            }
                        }
                        else
                        {
                            System.err.println("Passport is required. Enter values in this order Flight Name Passport Seat");
                        }
                    }
                    else
                    {
                        System.err.println("Name is required. Enter values in this order Flight Name Passport Seat");
                    }
                }
                else
                {
                    System.err.println("Flight Number is required. Enter values in this order Flight Name PassportNumber");
                }
            }
            // cancel a reservation on flight for passenger with name and passport
            else if (action.equalsIgnoreCase("CANCEL"))
            {
                // If the user inputs "CANCEL", and supplies a flight-number, name, and passport-number,
                // then cancel a flight for a passenger.
                // Of course, if the passenger does not already exist, then we should return
                // a FlightNotFoundException or a PassengerNotManifestException accordingly.
                String flight;
                String name;
                String passport;
                if (commandLine.hasNext())
                {
                    flight = commandLine.next().toUpperCase().trim();
                    if (commandLine.hasNext())
                    {
                        name = commandLine.next().trim();
                        if (commandLine.hasNext())
                        {
                            passport = commandLine.next().trim();
                            Reservation reservation = null;
                            for (Reservation r : myReservations)
                            {
                                if (r.getFlightNum().equals(flight))
                                {
                                    reservation = r;
                                    break;
                                }
                            }
                            if (reservation == null)
                            {
                                System.out.println("Reservation not found");
                            }
                            else
                                {
                                try
                                {
                                    manager.cancelReservation(reservation, name, passport);
                                    myReservations.remove(reservation);
                                }
                                catch (FlightNotFoundException | PassengerNotInManifestException ex)
                                {
                                    System.out.println(ex.getMessage());
                                }
                            }
                        }
                        else
                        {
                            System.err.println("Passport is required. Enter values in this order Flight Name Passport");
                        }
                    }
                    else
                    {
                        System.err.println("Name is required. Enter values in this order Flight Name Passport");
                    }

                }
                else
                {
                    System.err.println("Flight is required. Enter values in this order Flight Name Passport");
                }
            }
            // Prints out the seats on this flight (example input: seats AC220)
            else if (action.equalsIgnoreCase("SEATS"))
            {
                // If the user inputs "SEATS", then we should print out all the seats
                // on the flight.
                // Of course, if the flight does not exist, then we should return
                // a FlightNotFoundException.
                String flight = null;
                if (commandLine.hasNext())
                {
                    flight = commandLine.next().toUpperCase();
                    try
                    {
                        manager.printSeats(flight);
                    }
                    catch (FlightNotFoundException ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            // prints the passenger manifest for this flight (example input: pasman AC220)
            else if (action.equalsIgnoreCase("PASMAN"))
            {
                // If the user inputs "manifest", then we should print out the passenger
                // manifest for this flight.
                // Of course, if the flight does not exist, then we should return
                // a FlightNotFoundException.
                String flight = null;
                if (commandLine.hasNext())
                {
                    flight = commandLine.next().toUpperCase();

                    try
                    {
                        manager.printPassengerManifest(flight);
                    }
                    catch (FlightNotFoundException ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            // Print all the reservations in array list myReservations
            else if (action.equalsIgnoreCase("MYRES"))
            {
                // Print all the reservations in the arraylist myReservations
                for (Reservation myReservation : myReservations)
                {
                    myReservation.print();
                }
            }

            System.out.print("\n>");
        }
    }
}
