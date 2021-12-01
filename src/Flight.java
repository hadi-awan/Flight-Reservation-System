import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/*
 *  Class to model an airline flight. In this simple system, all flights originate from Toronto
 *  This class models a simple flight that has only economy seats
 */
public class Flight
{
    public enum Status
    {
        // A special class that returns a status of a flight.
        DELAYED, ONTIME, ARRIVED, INFLIGHT
    };

    // Fields
    String flightNum;
    String airline;
    String origin, dest;
    String departureTime;
    Status status; 
    int flightDuration;
    Aircraft aircraft;
    protected int passengers; // count of (economy) passengers on this flight - initially 0
    protected ArrayList<Passenger> manifest;
    protected TreeMap<String, Passenger> seatMap;

    public Flight()
    {
        // Constructor method which initializes all the fields.
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

    public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
    {
        // Constructor method which initializes all the fields.
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

    public String getFlightNum()
    {
        // Accessor method
        return flightNum;
    }

    public void setFlightNum(String flightNum)
    {
        // Mutator method
        this.flightNum = flightNum;
    }

    public String getAirline()
    {
        // Accessor method
        return airline;
    }

    public void setAirline(String airline)
    {
        // Mutator method
        this.airline = airline;
    }

    public String getOrigin()
    {
        // Accessor method
        return origin;
    }

    public void setOrigin(String origin)
    {
        // Mutator method
        this.origin = origin;
    }

    public String getDest()
    {
        // Accessor method
        return dest;
    }

    public void setDest(String dest)
    {
        // Mutator method
        this.dest = dest;
    }

    public String getDepartureTime()
    {
        // Accessor method
        return departureTime;
    }

    public void setDepartureTime(String departureTime)
    {
        // Mutator method
        this.departureTime = departureTime;
    }

    public Status getStatus()
    {
        // Accessor method
        return status;
    }

    public void setStatus(Status status)
    {
        // Mutator method
        this.status = status;
    }

    public int getFlightDuration()
    {
        // Accessor method
        return flightDuration;
    }

    public void setFlightDuration(int dur)
    {
        // Mutator method
        this.flightDuration = dur;
    }

    public int getPassengers()
    {
        // Accessor method
        return passengers;
    }

    public void setPassengers(int passengers)
    {
        // Mutator method
        this.passengers = passengers;
    }

    public ArrayList<Passenger> getManifest()
    {
        // Accessor method
        return manifest;
    }

    public void setManifest(ArrayList<Passenger> manifest)
    {
        // Mutator method
        this.manifest = manifest;
    }

    FlightType getFlightType()
    {
        // Accessor method which returns the type of flight (the default is MEDIUMHAUL)
        return FlightType.MEDIUMHAUL;
    }

    public void printPassengerManifest()
    {
        //Prints all the passengers on the flight
        for (Passenger passenger : manifest)
        {
            System.out.println(passenger.toString());
        }
    }

    public void printSeats()
    {
        //Uses the 2D array seatLayout[][] in the aircraft object for this flight to print seats
        String[][] seatLayout = aircraft.getSeatLayout();
        for (int row = 0; row < seatLayout[1].length; row++)
        {
            for (int col = 0; col < seatLayout.length; col++)
            {
                String seat = seatLayout[col][row];
                if (seatMap.containsKey(seat))
                {
                    seat = "XX";
                }
                System.out.print(seat+ " ");
            }
            if (row == 1)
            {
                System.out.println("\n");
            }
            else
            {
                System.out.println();
            }
        }

        System.out.println();
        System.out.println();
        System.out.println("XX = Occupied" + "\t" + "+ = First Class");
    }

    public boolean seatsAvailable()
    {
        // Checks to see if there is room on this flight by comparing
        // the current passenger count with the aircraft max capacity of economy seats
        return passengers <= aircraft.getNumSeats();
    }

    /*
     * Cancel a seat of the given passenger
     */
    public void cancelSeat(Passenger p) throws PassengerNotInManifestException
    {
        // Check if the passenger actually exists
        for (Map.Entry<String, Passenger> entry : seatMap.entrySet())
        {
            String key = entry.getKey();
            Passenger passenger = entry.getValue();
            if (passenger.equals(p))
            {
                // If the passenger exists, then remove passengers from manifest and seat map
                manifest.remove(passenger);
                seatMap.remove(key);
                // If there are more than 0 passengers, then reduce the passenger count
                if (passengers > 0)
                {
                    passengers--;
                }
                return;
            }
        }
        // If the passenger is not found, then throw a PassengerNotInManifestException.
        throw new PassengerNotInManifestException("Passenger " + p.toString() + " not found");
    }

    public void reserveSeat(Passenger p, String seat) throws DuplicatePassengerException, SeatOccupiedException
    {
        // Reserves a seat on this flight ensuring that there are no duplicate passengers
        // Checks if the passenger already exists.
        for (Passenger passenger : manifest)
        {
            if (passenger.equals(p))
            {
                // If the passenger already exists, then we do not reserve a seat and throw a DuplicatePassengerException.
                throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport());
            }
        }
        if (seatsAvailable())
        {
            // Checks if a seat is occupied.
            if (seatMap.containsKey(seat))
            {
                // If a seat is taken, then we do not reserve a seat and throw a SeatOccupiedException
                throw new SeatOccupiedException("Seat " + seat + " already occupied");
            }
            else
            {
                // If the seat is not occupied, then add the passenger on board and increase the passenger count.
                manifest.add(p);
                seatMap.put(seat, p);
                passengers++;
            }
        }
    }

    @Override
    public String toString()
    {
        // Overriding the toString method of class Objects.
        // Return a string consisting of the airline, the flight-number, the destination,
        // the departure-time, the flight-duration, and the status.
        return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
    }
}
