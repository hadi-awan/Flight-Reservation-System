/*
 * A long haul flight is a flight that travels thousands of kilometers and typically has separate seating areas
 */
public class LongHaulFlight extends Flight
{
    // Fields
    int numFirstClassPassengers;
    String seatType;

    // Possible seat types
    public static final String FIRST_CLASS = "First Class Seat";
    public static final String ECONOMY = "Economy Seat";

    public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
    {
        // Constructor method which uses the super() call to initialize all inherited variables.
        // Also initializes the new instance variables.
        super(flightNum, airline, dest, departure, flightDuration, aircraft);
        this.numFirstClassPassengers = 0;
        this.seatType = null;
    }

    public LongHaulFlight()
    {
        // Default constructor
    }

    @Override
    FlightType getFlightType()
    {
        // Returns the type of flight (in this case LongHaulFlight)
        return FlightType.LONGHAUL;
    }

    @Override
    public void reserveSeat(Passenger p, String seat) throws DuplicatePassengerException, SeatOccupiedException
    {
        // Reserves a seat for a passenger.
        // If the passenger already exists or a seat is already occupied,
        // then throw a DuplicatePassengerException or a SeatOccupiedException respectively.
        super.reserveSeat(p, seat);
        if (numFirstClassPassengers <= super.aircraft.getNumFirstClassSeats())
        {
            numFirstClassPassengers++;
        }
    }

    @Override
    public void cancelSeat(Passenger p) throws PassengerNotInManifestException
    {
        // Cancels a seat for a passenger.
        // If the passenger is not in the flight manifest,
        // then throw a PassengerNotInManifestException.
        super.cancelSeat(p);
        if (p.getSeatType().equals(FIRST_CLASS) && numFirstClassPassengers > 0)
        {
            numFirstClassPassengers--;
        }
        else
        {
            super.cancelSeat(p);
        }
    }

    public int getPassengerCount()
    {
        // Returns the total passenger count of ECONOMY passengers and first class passengers.
        return super.getPassengers() + numFirstClassPassengers;
    }

    @Override
    public String toString()
    {
        // Overrides the toString method of class Flight.
        // Returns a string containing the toString method from class Flight, followed by " LongHaul".
        return super.toString() + "  LongHaul";
    }
}
