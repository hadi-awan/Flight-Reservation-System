import java.util.Objects;

/*
 * Represents  a passenger object
 * Contains attributes such as passenger name, passport and seatNumber
 */

public class Passenger
{
    // Instance variables
    private String name;
    private String passport;

    private String seat;
    private String seatType;

    public Passenger(String name, String passport, String seat, String seatType)
    {
        // Constructor method which initializes the instance variables.
        this.name = name;
        this.passport = passport;
        this.seat = seat;
        this.seatType = seatType;
    }

    public String getName()
    {
        // Accessor method
        return name;
    }

    public String getPassport()
    {
        // Accessor method
        return passport;
    }

    public String getSeat()
    {
        // Accessor method
        return seat;
    }

    public void setSeat(String seat)
    {
        // Mutator method
        this.seat = seat;
    }

    public String getSeatType()
    {
        // Accessor method
        return seatType;
    }

    public void setSeatType(String seatType)
    {
        // Mutator method
        this.seatType = seatType;
    }


    @Override
    public int hashCode()
    {
        // If a class overrides equals, it must override hashCode.
        // If two objects are equal, then their hashCode values must be equal as well.
        // Therefore, we take a prime number (7) as a hash.
        // Finally, we return hash.
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        // Overriding the equals method of class Objects.
        // Comparing two objects of the class Passenger.
        // If the two objects are equal, then return true.
        // If the two objects are not equal, then return false.
        if (this == obj)
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        final Passenger passenger = (Passenger) obj;

        if (!Objects.equals(this.name, passenger.name))
        {
            return false;
        }
        return Objects.equals(this.passport, passenger.passport);
    }

    @Override
    public String toString()
    {
        // Overriding the toString method of class Objects.
        // Returns a string containing the name of the passenger, their passport-number, and their seat-number
        return name + "\t" + passport + "\t" + seat;
    }
}
