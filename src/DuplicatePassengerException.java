 // This is a custom error class that is thrown when a given passenger is already in the flight.

class DuplicatePassengerException extends Exception
{
    public DuplicatePassengerException(String message)
    {
        super(message);
    }
}
