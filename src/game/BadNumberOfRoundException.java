package game;

public class BadNumberOfRoundException extends Exception{
    public BadNumberOfRoundException(){
    }

    public BadNumberOfRoundException(String msg){
       super(msg); 
    }
}
