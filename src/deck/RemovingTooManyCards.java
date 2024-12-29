package deck;

public class RemovingTooManyCards extends Exception {
    public RemovingTooManyCards() {
    }

    public RemovingTooManyCards(String msg) {
        super(msg);
    }
}
