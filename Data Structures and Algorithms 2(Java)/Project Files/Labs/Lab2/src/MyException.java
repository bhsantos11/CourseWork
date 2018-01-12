public class MyException extends Exception {
    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MyException(Throwable throwable){
        super(throwable);
    }
}