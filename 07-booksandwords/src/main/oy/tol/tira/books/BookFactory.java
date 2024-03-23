package oy.tol.tira.books;

/**
 * <p>
 * Implement the <code>createBook()</code> method to return your instance of the Book interface.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public final class BookFactory {
    private BookFactory() {
    }

    /**
     * @return Your implementation of the Book interface.
     */
    public static Book createBook() {
        // return null;
        //return new BadBookImplementation();
        return new HashTableBookImplementation();
    }
}
