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
package co.edu.uniandes.csw.bookstore.resources;

import co.edu.uniandes.csw.bookstore.ejb.BookLogic;
import co.edu.uniandes.csw.bookstore.dtos.BookDetailDTO;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import java.util.ArrayList;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso REST correspondiente a "books".
 *
 * Note que la aplicación (definida en RestConfig.java) define la ruta "/api" y
 * este recurso tiene la ruta "books". Al ejecutar la aplicación, el recurse
 * será accesibe a través de la ruta "/api/books"
 *
 * @author ISIS2603
 */
@Path("books")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class BookResource {

    @Inject
    BookLogic bookLogic;

    @GET
    public List<BookDetailDTO> getBooks() throws BusinessLogicException {
        return listBookEntity2DetailDTO(bookLogic.getBooks());
    }

    @GET
    @Path("{id: \\d+}")
    public BookDetailDTO getBook(@PathParam("id") Long id) throws BusinessLogicException {
        BookEntity entity = bookLogic.getBook(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + id + " no existe.", 404);
        }
        return new BookDetailDTO(entity);
    }

    /**
     * Ejemplo: { "description": "La comunicación en arquitectos de software.",
     * "editorial": { "id": 200, "name": "Oveja Negra 2" }, "image":
     * "https://images-na.ssl-images-amazon.com/images/I/516GyHY9p6L.jpg",
     * "isbn": "930330149-8", "name": "La comunicación en el software",
     * "publishingdate": "2017-08-20T00:00:00-05:00" }
     *
     * @param book
     * @return
     * @throws BusinessLogicException
     */
    @POST
    public BookDetailDTO createBook(BookDetailDTO book) throws BusinessLogicException {        
         return new BookDetailDTO(bookLogic.createBook(book.toEntity()));
    }

    /**
     *
     * Ejemplo: { "description": "Las habilidades gerenciales en arquitectos de
     * software.", "editorial": { "id": 200, "name": "Oveja Negra 2" }, "image":
     * "https://images-na.ssl-images-amazon.com/images/I/516GyHY9p6L.jpg",
     * "isbn": "930330149-8", "name": "La comunicación en el software",
     * "publishingdate": "2017-08-20T00:00:00-05:00" }
     *
     * @param id
     * @param book
     * @return
     * @throws BusinessLogicException
     */
    @PUT
    @Path("{id: \\d+}")
    public BookDetailDTO updateBook(@PathParam("id") Long id, BookDetailDTO book) throws BusinessLogicException {
        book.setId(id);
        BookEntity entity = bookLogic.getBook(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + id + " no existe.", 404);
        }
        return new BookDetailDTO(bookLogic.updateBook(id, book.toEntity()));
    }

    @DELETE
    @Path("{booksId: \\d+}")
    public void deleteBook(@PathParam("booksId") Long id) throws BusinessLogicException {
        BookEntity entity = bookLogic.getBook(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + id + " no existe.", 404);
        }
        bookLogic.deleteBook(id);
    }

    @Path("{idBook: \\d+}/reviews")
    public Class<ReviewResource> getReviewResource(@PathParam("idBook") Long booksId) {
        BookEntity entity = bookLogic.getBook(booksId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + booksId + "/reviews no existe.", 404);
        }
        return ReviewResource.class;
    }

    @Path("{booksId: \\d+}/authors")
    public Class<BookAuthorsResource> getBookAuthorsResource(@PathParam("booksId") Long booksId) {
        BookEntity entity = bookLogic.getBook(booksId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + booksId + "/reviews no existe.", 404);
        }
        return BookAuthorsResource.class;
    }

    private List<BookDetailDTO> listBookEntity2DetailDTO(List<BookEntity> entityList) {
        List<BookDetailDTO> list = new ArrayList<>();
        for (BookEntity entity : entityList) {
            list.add(new BookDetailDTO(entity));
        }
        return list;
    }

}
