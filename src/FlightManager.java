import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class FlightManager {
    // Contains a list of Flights departing from Toronto in a single day
    TreeMap<String, Flight> flights = new TreeMap<>();

    // Contains of an array of cities that are destinations
    String[] cities = {"Dallas", "New York", "London", "Paris", "Tokyo"};
    final int DALLAS = 0;
    final int NEWYORK = 1;
    final int LONDON = 2;
    final int PARIS = 3;
    final int TOKYO = 4;

    // Flight times in hours.
    int[] flightTimes = {
            3, // Dallas
            1, // New York
            7, // London
            8, // Paris
            16// Tokyo
    };

    // Contains a list of available airplane types and their seat capacity
    ArrayList<Aircraft> airplanes = new ArrayList<>();

    Random random = new Random(); // random number generator 

    public FlightManager() {
        // Create some aircraft types with max seat capacities
        airplanes.add(new Aircraft(85, "Boeing 737"));
        airplanes.add(new Aircraft(80, "Airbus 320"));
        airplanes.add(new Aircraft(37, "Dash-8 100"));
        airplanes.add(new Aircraft(12, "Bombardier 5000"));
        airplanes.add(new Aircraft(200, 12, "Boeing 747"));

        // Populate flights from a file.
        readFlightFromFile("flights.txt");
    }

    private String generateFlightNumber(String airline) {
        // Generates and returns a flight number string from the airline name parameter
        // For example, if parameter string airline is "Air Canada" the flight number should be "ACxxx" where xxx is a random 3 digit number between 101 and 300
        // Assume every airline name is always 2 words.
        int flightNo = random.ints(1, 101, 300 + 1).findFirst().getAsInt();
        String initials = getInitials(airline);
        return initials + flightNo;
    }


    private String getInitials(String string) {
        // Gets the first character of every word in the sentence
        String initials = "";
        for (String s : string.split(" ")) {
            initials += s.charAt(0);
        }
        return initials;
    }

    public void printAllFlights() {
        // Prints all flights in flights array list 
        for (Map.Entry<String, Flight> entry : flights.entrySet()) {
            String key = entry.getKey();
            Flight flight = entry.getValue();
            System.out.println(flight.toString());
        }
    }

    public boolean seatsAvailable(String flightNum) throws FlightFullException, FlightNotFoundException {
        // Given a flight number (e.g. "UA220"), check to see if there are ECONOMY seats available
        // If a flight is full or if a flight does not exist, then we throw a FlightFullException or a FlightNotFoundException respectively
        Flight flight = getFlightByNumber(flightNum);
        if (flight != null) {
            if (flight.seatsAvailable()) {
                return true;
            }
            throw new FlightFullException("Flight " + flightNum + " is full");
        }
        else {
            throw new FlightNotFoundException("Flight " + flightNum + " Not Found");
        }
    }

    public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seat) throws FlightFullException, FlightNotFoundException, DuplicatePassengerException, SeatOccupiedException {
        // Given a flight number string flightNum and a seat type, reserve a seat on a flight
        // If successful, return a Reservation object
        // NOTE: seat types are not used for basic Flight objects (seats are all "Economy Seats")
        // class LongHaulFlight defines two seat types
        Flight flight = getFlightByNumber(flightNum);
        String seatType = LongHaulFlight.ECONOMY;
        if (seat.endsWith("+")) {
            seatType = LongHaulFlight.FIRST_CLASS;
        }
        Passenger passenger = new Passenger(name, passport, seat, seatType);
        if (flight != null) {
            if (seatType.equals(LongHaulFlight.FIRST_CLASS) && flight instanceof LongHaulFlight) {
                LongHaulFlight lhf = (LongHaulFlight) flight;
                lhf.reserveSeat(passenger, seat);
                if (lhf.seatsAvailable()) {
                    Reservation reservation = new Reservation(flightNum, lhf.toString(), name, passport, seat);
                    reservation.setFirstClass();
                    return reservation;
                }
                else {
                    throw new FlightFullException("Long Haul " + flightNum + " is full");
                }
            }
            else {
                flight.reserveSeat(passenger, seat);
                if (flight.seatsAvailable()) {
                    return new Reservation(flightNum, flight.toString(), name, passport, seat);
                }
                else {
                    throw new FlightFullException("Flight " + flightNum + " is full");
                }
            }
        }
        else {
            throw new FlightNotFoundException("Flight " + flightNum + " Not Found");
        }
    }


    public boolean cancelReservation(Reservation res, String name, String passport) throws FlightNotFoundException, PassengerNotInManifestException {
        // Given a Reservation object, cancel the seat on the flight based on passenger name and passport
        Flight flight = getFlightByNumber(res.getFlightNum());
        if (flight != null) { 
            Passenger passenger = new Passenger(name, passport, null, null);
            for (Passenger p : flight.getManifest()) {
                if (p.equals(passenger)) {
                    passenger = p;
                    break;
                }
            }
            if (flight instanceof LongHaulFlight) {
                LongHaulFlight lhf = (LongHaulFlight) flight;
                flight.cancelSeat(passenger);
            }
            else {
                flight.cancelSeat(passenger);
            }
        }
        else {
            throw new FlightNotFoundException("Flight " + res.flightNum + " Not Found");
        }
        return false;
    }

    private void readFlightFromFile(String filename) {
        // Reads the information from the "flights.txt" file.
        // It retrieves the information by placing it into an array
            // The first element in the array represents the airline
            // The second element in the array represents the destination
            // The third element in the array represents the departure
            // The fourth element in the array represents the capacity
        // After setting the elements of the array, generate the flight number
        // Then, get an aircraft model that is large enough to support the capacity
        // Next, set the index of the city if it a destination
        // Finally, create a new object Flight and input all our variables
        // If this file does not exist, then catch a FileNotFoundException
        try {
            File myObj = new File(filename);
            try (Scanner reader = new Scanner(myObj)) {
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] details = line.split(" ");
                    if (details.length == 4) {
                        String airline = details[0];
                        airline = airline.trim().replace("_", " ");
                        String dest = details[1].trim().replace("_", " ");
                        String departure = details[2];
                        int capacity = Integer.valueOf(details[3]);
                        String flightNum = generateFlightNumber(airline);
                        Aircraft aircraft = getBigEnoughAirplaneModel(capacity);
                        aircraft = new Aircraft(capacity, aircraft.getNumFirstClassSeats(), aircraft.getModel());
                        // Get the city index from which we can get the flight time.
                        int city_index = 0;
                        for (int i = 0; i < cities.length; i++) {
                            if (cities[i].equalsIgnoreCase(dest)) {
                                city_index = i;
                                break;
                            }
                        }
                        Flight flight = new Flight(flightNum, airline, dest, departure, flightTimes[city_index], aircraft);
                        if (aircraft.getNumFirstClassSeats() > 0) {
                            flight = new LongHaulFlight(flightNum, airline, dest, departure, flightTimes[city_index], aircraft);
                        }
                        flights.put(flightNum, flight);
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public Aircraft getBigEnoughAirplaneModel(int capacity)
    {
        // Returns a model of an aircraft that is large enough according to @capacity.
        ArrayList<Integer> aircraft_capacities = new ArrayList<>();
        List<Integer> big_enough_aircrafts = new ArrayList<>();
        for (Aircraft airplane : airplanes) {
            int size = airplane.getTotalSeats();
            if (size >= capacity) {
                big_enough_aircrafts.add(size);
            }
            aircraft_capacities.add(size);
        }
        Collections.sort(big_enough_aircrafts);
        int big_enough_aircraft_capacity = big_enough_aircrafts.get(0);
        int index = aircraft_capacities.indexOf(big_enough_aircraft_capacity);
        return airplanes.get(index);
    }

    public void printAllAircraft() {
        for (Aircraft aircraft : airplanes) {
            aircraft.print();
        }
    }

    public void printSeats(String flightNum) throws FlightNotFoundException {
        Flight flight = getFlightByNumber(flightNum);
        if (flight == null) {
            throw new FlightNotFoundException("Flight " + flightNum + " Not Found");
        }
        flight.printSeats();
    }

    public void printPassengerManifest(String flightNum) throws FlightNotFoundException {
        // Prints the passenger manifest for this flight (i.e information about all passengers).
        Flight flight = getFlightByNumber(flightNum);
        if (flight == null) {
            throw new FlightNotFoundException("Flight " + flightNum + " Not Found");
        }
        for (Passenger passenger : flight.getManifest()) {
            System.out.println(passenger.toString());
        }
    }

    public void sortAircraft() {
        // Sort the array list of Aircraft objects
        Collections.sort(airplanes);
    }

    public Flight getFlightByNumber(String flightNum) {
        // Takes flightNumber as the input and returns a flight object or null if there is no flight with that flight number
        Flight flight = null;
        for (Map.Entry<String, Flight> flght : flights.entrySet()) {
            Flight f = flght.getValue();
            if (f.getFlightNum().equals(flightNum)) {
                flight = f;
                break;
            }
        }
        return flight;
    }
}
