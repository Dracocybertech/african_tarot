package people;

public class TooManyCardsException extends Exception {
    public TooManyCardsException() {
    }

    public TooManyCardsException(String msg) {
        super(msg);
    }
}
