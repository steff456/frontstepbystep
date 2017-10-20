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

import co.edu.uniandes.csw.bookstore.dtos.AuthorDetailDTO;
import co.edu.uniandes.csw.bookstore.ejb.BookLogic;
import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
/**
 * URI: books/{booksId: \\d+}/authors
 *
 * @author ISIS2603
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookAuthorsResource {

    @Inject
    private BookLogic bookLogic;

    /**
     * Convierte una lista de AuthorEntity a una lista de AuthorDetailDTO.
     *
     * @param entityList Lista de AuthorEntity a convertir.
     * @return Lista de AuthorDetailDTO convertida.
     * 
     */
    private List<AuthorDetailDTO> authorsListEntity2DTO(List<AuthorEntity> entityList) {
        List<AuthorDetailDTO> list = new ArrayList<>();
        for (AuthorEntity entity : entityList) {
            list.add(new AuthorDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de AuthorDetailDTO a una lista de AuthorEntity.
     *
     * @param dtos Lista de AuthorDetailDTO a convertir.
     * @return Lista de AuthorEntity convertida.
     * 
     */
    private List<AuthorEntity> authorsListDTO2Entity(List<AuthorDetailDTO> dtos) {
        List<AuthorEntity> list = new ArrayList<>();
        for (AuthorDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

    /**
     * Obtiene una colección de instancias de AuthorDetailDTO asociadas a una
     * instancia de Book
     *
     * @param booksId Identificador de la instancia de Book
     * @return Colección de instancias de AuthorDetailDTO asociadas a la
     * instancia de Book
     * 
     */
    @GET
    public List<AuthorDetailDTO> listAuthors(@PathParam("booksId") Long booksId) {
        return authorsListEntity2DTO(bookLogic.listAuthors(booksId));
    }

    /**
     * Obtiene una instancia de Author asociada a una instancia de Book
     *
     * @param booksId Identificador de la instancia de Book
     * @param authorsId Identificador de la instancia de Author
     * @return 
     * 
     */
    @GET
    @Path("{authorsId: \\d+}")
    public AuthorDetailDTO getAuthors(@PathParam("booksId") Long booksId, @PathParam("authorsId") Long authorsId) {
        return new AuthorDetailDTO(bookLogic.getAuthor(booksId, authorsId));
    }

    /**
     * Asocia un Author existente a un Book
     *
     * @param booksId Identificador de la instancia de Book
     * @param authorsId Identificador de la instancia de Author
     * @return Instancia de AuthorDetailDTO que fue asociada a Book
     * 
     */
    @POST
    @Path("{authorsId: \\d+}")
    public AuthorDetailDTO addAuthors(@PathParam("booksId") Long booksId, @PathParam("authorsId") Long authorsId) {
        return new AuthorDetailDTO(bookLogic.addAuthor(booksId, authorsId));
    }

    /**
     * Remplaza las instancias de Author asociadas a una instancia de Book
     *
     * @param booksId Identificador de la instancia de Book
     * @param authors Colección de instancias de AuthorDTO a asociar a instancia
     * de Book
     * @return Nueva colección de AuthorDTO asociada a la instancia de Book
     * 
     */
    @PUT
    public List<AuthorDetailDTO> replaceAuthors(@PathParam("booksId") Long booksId, List<AuthorDetailDTO> authors) {
        return authorsListEntity2DTO(bookLogic.replaceAuthors(booksId, authorsListDTO2Entity(authors)));
    }

    /**
     * Desasocia un Author existente de un Book existente
     *
     * @param booksId Identificador de la instancia de Book
     * @param authorsId Identificador de la instancia de Author
     * 
     */
    @DELETE
    @Path("{authorsId: \\d+}")
    public void removeAuthors(@PathParam("booksId") Long booksId, @PathParam("authorsId") Long authorsId) {
        bookLogic.removeAuthor(booksId, authorsId);
    }
}
