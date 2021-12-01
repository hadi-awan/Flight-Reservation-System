/* 
 * A custom error class that is thrown when a given flight is full.
 */

class FlightFullException extends Exception {
    public FlightFullException(String message) {
        super(message);
    }
}
