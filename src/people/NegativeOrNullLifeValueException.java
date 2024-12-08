package people;

public class NegativeOrNullLifeValueException extends Exception {
    public NegativeOrNullLifeValueException(){
    }

    public NegativeOrNullLifeValueException(String msg){
       super(msg); 
    }
}
