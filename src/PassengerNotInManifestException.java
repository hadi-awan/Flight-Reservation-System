/*
 * A custom error class that is thrown when a given passenger does not exist in the flight manifest
 */

class PassengerNotInManifestException extends Exception {
    public PassengerNotInManifestException(String message) {
        super(message);
    }
}
