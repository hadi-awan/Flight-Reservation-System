/*
 * Represents  a passenger object
 * Contains attributes such as passenger name, passport and seatNumber
 */

import java.util.Objects;

public class Passenger
{
    private String name;
    private String passport;
    private String seat;
    private String seatType;

    public Passenger(String name, String passport, String seat, String seatType) {
        this.name = name;
        this.passport = passport;
        this.seat = seat;
        this.seatType = seatType;
    }

    public String getName() {
        return name;
    }

    public String getPassport() {
        return passport;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }


    @Override
    public int hashCode() {
        // If a class overrides equals, it must override hashCode
        // If two objects are equal, then their hashCode values must be equal as well
        // Therefore, we take a prime number (7) as a hash
        // Finally, we return hash
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        // Overriding the equals method of class Objects
        // Comparing two objects of the class Passenger
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Passenger passenger = (Passenger) obj;
        if (!Objects.equals(this.name, passenger.name)) {
            return false;
        }
        return Objects.equals(this.passport, passenger.passport);
    }

    @Override
    public String toString() {
        return name + "\t" + passport + "\t" + seat;
    }
}
