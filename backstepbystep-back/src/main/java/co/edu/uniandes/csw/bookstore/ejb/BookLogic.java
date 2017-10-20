/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.bookstore.ejb;

import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.persistence.BookPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ISIS2603
 */
@Stateless
public class BookLogic {

    private static final Logger LOGGER = Logger.getLogger(BookLogic.class.getName());

    @Inject
    private BookPersistence persistence;

    public List<BookEntity> getBooks() {
        LOGGER.info("Inicia proceso de consultar todos los libros");
        List<BookEntity> books = persistence.findAll();
        LOGGER.info("Termina proceso de consultar todos los libros");
        return books;
    }

    public BookEntity getBook(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar libro con id={0}", id);
        BookEntity book = persistence.find(id);
        if (book == null) {
            LOGGER.log(Level.SEVERE, "El libro con el id {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar libro con id={0}", id);
        return book;
    }

    public BookEntity createBook(BookEntity entity) throws BusinessLogicException {
        LOGGER.info("Inicia proceso de creación de libro");
        if (!validateISBN(entity.getIsbn())) {
            throw new BusinessLogicException("El ISBN es inválido");
        }
        persistence.create(entity);
        LOGGER.info("Termina proceso de creación de libro");
        return entity;
    }

    public BookEntity updateBook(Long id, BookEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar libro con id={0}", id);
        if (!validateISBN(entity.getIsbn())) {
            throw new BusinessLogicException("El ISBN es inválido");
        }
        BookEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar libro con id={0}", entity.getId());
        return newEntity;
    }

    public void deleteBook(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar libro con id={0}", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar libro con id={0}", id);
    }

    private boolean validateISBN(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Obtiene una colección de instancias de AuthorEntity asociadas a una
     * instancia de Book
     *
     * @param bookId Identificador de la instancia de Book
     * @return Colección de instancias de AuthorEntity asociadas a la instancia
     * de Book
     * 
     */
    public List<AuthorEntity> listAuthors(Long bookId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores del libro con id = {0}", bookId);
        return getBook(bookId).getAuthors();
    }

    /**
     * Obtiene una instancia de AuthorEntity asociada a una instancia de Book
     *
     * @param bookId Identificador de la instancia de Book
     * @param authorsId Identificador de la instancia de Author
     * 
     */
    public AuthorEntity getAuthor(Long bookId, Long authorsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un autor del libro con id = {0}", bookId);
        List<AuthorEntity> list = getBook(bookId).getAuthors();
        AuthorEntity authorsEntity = new AuthorEntity();
        authorsEntity.setId(authorsId);
        int index = list.indexOf(authorsEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Author existente a un Book
     *
     * @param bookId Identificador de la instancia de Book
     * @param authorsId Identificador de la instancia de Author
     * @return Instancia de AuthorEntity que fue asociada a Book
     * 
     */
    public AuthorEntity addAuthor(Long bookId, Long authorsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar un autor al libro con id = {0}", bookId);
        BookEntity bookEntity = getBook(bookId);
        AuthorEntity authorsEntity = new AuthorEntity();
        authorsEntity.setId(authorsId);
        bookEntity.getAuthors().add(authorsEntity);
        return getAuthor(bookId, authorsId);
    }

    /**
     * Remplaza las instancias de Author asociadas a una instancia de Book
     *
     * @param bookId Identificador de la instancia de Book
     * @param list Colección de instancias de AuthorEntity a asociar a instancia
     * de Book
     * @return Nueva colección de AuthorEntity asociada a la instancia de Book
     * 
     */
    public List<AuthorEntity> replaceAuthors(Long bookId, List<AuthorEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar un autor del libro con id = {0}", bookId);
        BookEntity bookEntity = getBook(bookId);
        bookEntity.setAuthors(list);
        return bookEntity.getAuthors();
    }

    /**
     * Desasocia un Author existente de un Book existente
     *
     * @param bookId Identificador de la instancia de Book
     * @param authorsId Identificador de la instancia de Author
     * 
     */
    public void removeAuthor(Long bookId, Long authorsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un autor del libro con id = {0}", bookId);
        BookEntity entity = getBook(bookId);
        AuthorEntity authorsEntity = new AuthorEntity();
        authorsEntity.setId(authorsId);
        entity.getAuthors().remove(authorsEntity);
    }
}
