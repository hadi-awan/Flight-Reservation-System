/*
 * A custom error class that is thrown when a seat is already taken/occupied
 */

class SeatOccupiedException extends Exception {
    public SeatOccupiedException(String message) {
        super(message);
    }
}
