package data;

public interface AsyncResponse<E> {
    void processFinished(E e);
}
