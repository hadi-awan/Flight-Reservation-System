/*
 * Models an aircraft type with a model name, a maximum number of economy seats, and a max number of first class seats
 */

public final class Aircraft implements Comparable<Aircraft> {
    int numEconomySeats;
    int numFirstClassSeats;
    String[][] seatLayout;

    String model;
    int rows = 4;

    public Aircraft(int seats, String model) {
        this.numEconomySeats = seats;
        this.numFirstClassSeats = 0;
        this.model = model;
    }

    public Aircraft(int economy, int firstClass, String model) {
        this.numEconomySeats = economy;
        this.numFirstClassSeats = firstClass;
        this.model = model;
    }

    public int getNumSeats() {
        return numEconomySeats;
    }

    public int getTotalSeats() {
        return numEconomySeats + numFirstClassSeats;
    }


    public int getNumFirstClassSeats() {
        return numFirstClassSeats;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setNumEconomySeats(int numEconomySeats) {
        this.numEconomySeats = numEconomySeats;
    }


    public int getRows() {
        if (getTotalSeats() <= rows) {
            this.rows = 2;
        }
        return rows;
    }

    public String[][] getSeatLayout() {
        // Returns a 2D array for the seat-layout of the aircraft.
        int seats = getNumSeats();
        int columns = seats / getRows();
        this.seatLayout = new String[columns][getRows()];
        setSeatLayout(seats);
        return seatLayout;
    }

    public void setSeatLayout(int capacity) {
        // Labels the seats for the aircraft.
        String[] labels = {"A", "B", "C", "D"};
        int columns = capacity / rows;
        for (int col = 1; col <= columns; col++) {
            for (int row = 0; row < rows; row++) {
                String label = labels[row];
                if(numFirstClassSeats>0 && (col * rows) < numFirstClassSeats) {
                    label += "+";
                }
                this.seatLayout[col - 1][row] =  col + label;
            }
        }
    }

    public void print() {
        System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
    }

    /*
     * Sorting by no of seats in the economy class. It returns  less than 0 i.e
     * 0 < if this(keywork) is less than aircraft, > 0 if this is supposed to be
     * greater  than object aircraft and 0 if they are equal.
     */
    @Override
    public int compareTo(Aircraft aircraft) {
        if (this.numEconomySeats == aircraft.numEconomySeats) {
            if (this.numFirstClassSeats == aircraft.numFirstClassSeats) {
                return 0;
            }
            else if (this.numFirstClassSeats > aircraft.numFirstClassSeats) {
                return 1;
            }
            else {
                return -1;
            }

        }
        else if (this.numEconomySeats > aircraft.numEconomySeats) {
            return 1;
        }
        return -1;
    }
}
