package people;

public class NegativeLifeValueException extends Exception {
    public NegativeLifeValueException() {
    }

    public NegativeLifeValueException(String msg) {
        super(msg);
    }
}
