/*
 * A custom error class that is thrown when a given flight does not exist or is null
 */

class FlightNotFoundException extends Exception {
    public FlightNotFoundException(String message) {
        super(message);
    }
}
