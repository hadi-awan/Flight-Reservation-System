import java.util.Objects;

/*
 * A simple class to model an electronic airline flight reservation
 *
 * This class has been done for you
 */
public class Reservation
{
    // Fields
    String flightNum;
    String flightInfo;
    boolean firstClass;
    String name;
    String passport;
    String seat;

    public Reservation(String flightNum, String info, String name, String passport, String seat)
    {
        // Constructor Method which initializes the flightNum, flightInfo, firstClass,
        // name, passport, and seat variables.
        this.flightNum = flightNum;
        this.flightInfo = info;
        this.firstClass = false;

        this.name = name;
        this.passport = passport;
        this.seat = seat;
    }

    public boolean isFirstClass()
    {
        // Method which returns if a reservation is or is not first class
        return firstClass;
    }

    public void setFirstClass()
    {
        // Mutator method
        this.firstClass = true;
    }

    public String getFlightNum()
    {
        // Accessor method
        return flightNum;
    }

    public String getFlightInfo()
    {
        // Accessor method
        return flightInfo;
    }

    public void setFlightInfo(String flightInfo)
    {
        // Mutator method
        this.flightInfo = flightInfo;
    }

    public String getName()
    {
        // Accessor method
        return name;
    }

    public void setName(String name)
    {
        // Mutator method
        this.name = name;
    }

    public String getPassport()
    {
        // Accessor method
        return passport;
    }

    public void setPassport(String passport)
    {
        // Mutator method
        this.passport = passport;
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

    public void print()
    {
        // Method which prints the flight-information, the name of the passenger, the passport-number, and seat-number.
        System.out.println(flightInfo + " " + name + " " + passport + " " + seat);
    }

    @Override
    public int hashCode()
    {
        // If a class overrides equals, it must override hashCode.
        // If two objects are equal, then their hashCode values must be equal as well.
        // Therefore, we take a prime number (3) as a hash.
        // We take another prime number (59), as a multiplier different than hash.
        // We compute the hashcode for each variable and add them into final hash.
        // We repeat this for all the variable that participated in the equals method.
        // Finally, we return hash.
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.flightNum);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.passport);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        // Overriding the equals method of class Objects.
        // Comparing two object of the class Reservation.
        // If the two objects are equal, then return true.
        // If the two objects are not equal, then return false.
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if (!Objects.equals(this.flightNum, other.flightNum))
        {
            return false;
        }
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        if (!Objects.equals(this.passport, other.passport))
        {
            return false;
        }
        return true;
    }
}
