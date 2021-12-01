/*
 *  Models an airline flight. In this simple system, all flights originate from Toronto.
 *  A simple flight has only economy seats.
 */

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Flight {
    public enum Status {
        // A special class that returns a status of a flight.
        DELAYED, ONTIME, ARRIVED, INFLIGHT
    };

    String flightNum;
    String airline;
    String origin, dest;
    String departureTime;
    Status status; 
    int flightDuration;
    Aircraft aircraft;
    protected int passengers; 
    protected ArrayList<Passenger> manifest;
    protected TreeMap<String, Passenger> seatMap;

    public Flight() {
        this.flightNum = null;
        this.airline = null;
        this.dest = null;
        this.origin = null;
        this.departureTime = null;
        this.flightDuration = 0;
        this.aircraft = null;

        passengers = 0;
        status = Status.ONTIME;

        this.manifest = new ArrayList<>();
        this.seatMap = new TreeMap<>();
    }

    public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft) {
        this.flightNum = flightNum;
        this.airline = airline;
        this.dest = dest;
        this.origin = "Toronto";
        this.departureTime = departure;
        this.flightDuration = flightDuration;
        this.aircraft = aircraft;
        passengers = 0;
        status = Status.ONTIME;

        this.manifest = new ArrayList<>();
        this.seatMap = new TreeMap<>();
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(int dur) {
        this.flightDuration = dur;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public ArrayList<Passenger> getManifest() {
        return manifest;
    }

    public void setManifest(ArrayList<Passenger> manifest) {
        this.manifest = manifest;
    }

    FlightType getFlightType() {
        // Returns the type of flight (the default is MEDIUMHAUL)
        return FlightType.MEDIUMHAUL;
    }

    public void printPassengerManifest() {
        for (Passenger passenger : manifest) {
            System.out.println(passenger.toString());
        }
    }

    public void printSeats() {
        // Uses the 2D array seatLayout[][] in the aircraft object for this flight to print seats
        String[][] seatLayout = aircraft.getSeatLayout();
        for (int row = 0; row < seatLayout[1].length; row++) {
            for (int col = 0; col < seatLayout.length; col++) {
                String seat = seatLayout[col][row];
                if (seatMap.containsKey(seat)) {
                    seat = "XX";
                }
                System.out.print(seat+ " ");
            }
            if (row == 1) {
                System.out.println("\n");
            }
            else {
                System.out.println();
            }
        }

        System.out.println();
        System.out.println();
        System.out.println("XX = Occupied" + "\t" + "+ = First Class");
    }

    public boolean seatsAvailable() {
        // Checks to see if there is room on this flight 
        return passengers <= aircraft.getNumSeats();
    }

    public void cancelSeat(Passenger p) throws PassengerNotInManifestException {
        // Check if the passenger actually exists
        for (Map.Entry<String, Passenger> entry : seatMap.entrySet()) {
            String key = entry.getKey();
            Passenger passenger = entry.getValue();
            if (passenger.equals(p)) {
                // If the passenger exists, then remove passengers from manifest and seat map
                manifest.remove(passenger);
                seatMap.remove(key);
                // If there are more than 0 passengers, then reduce the passenger count
                if (passengers > 0) {
                    passengers--;
                }
                return;
            }
        }
        // If the passenger is not found, then throw a PassengerNotInManifestException.
        throw new PassengerNotInManifestException("Passenger " + p.toString() + " not found");
    }

    public void reserveSeat(Passenger p, String seat) throws DuplicatePassengerException, SeatOccupiedException {
        // Reserves a seat on this flight ensuring that there are no duplicate passengers
        // Checks if the passenger already exists.
        for (Passenger passenger : manifest) {
            if (passenger.equals(p)) {
                // If the passenger already exists, then we do not reserve a seat and throw a DuplicatePassengerException.
                throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport());
            }
        }
        if (seatsAvailable()) {
            // Checks if a seat is occupied.
            if (seatMap.containsKey(seat)) {
                // If a seat is taken, then we do not reserve a seat and throw a SeatOccupiedException
                throw new SeatOccupiedException("Seat " + seat + " already occupied");
            }
            else {
                // If the seat is not occupied, then add the passenger on board and increase the passenger count.
                manifest.add(p);
                seatMap.put(seat, p);
                passengers++;
            }
        }
    }

    @Override
    public String toString() {
        return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
    }
}
