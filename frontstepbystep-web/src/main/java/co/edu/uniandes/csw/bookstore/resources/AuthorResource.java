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
import co.edu.uniandes.csw.bookstore.ejb.AuthorLogic;
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
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.WebApplicationException;

/**
 * URI: authors/
 *
 * @author ISIS2603
 */
@Path("/authors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AuthorResource {

    @Inject
    private AuthorLogic authorLogic;

    /**
     * Convierte una lista de AuthorEntity a una lista de AuthorDetailDTO.
     *
     * @param entityList Lista de AuthorEntity a convertir.
     * @return Lista de AuthorDetailDTO convertida.
     *
     */
    private List<AuthorDetailDTO> listEntity2DTO(List<AuthorEntity> entityList) {
        List<AuthorDetailDTO> list = new ArrayList<>();
        for (AuthorEntity entity : entityList) {
            list.add(new AuthorDetailDTO(entity));
        }
        return list;
    }

    /**
     * Obtiene la lista de los registros de Author
     *
     * @return Colección de objetos de AuthorDetailDTO
     *
     */
    @GET
    public List<AuthorDetailDTO> getAuthors() {
        return listEntity2DTO(authorLogic.getAuthors());
    }

    /**
     * Obtiene los datos de una instancia de Author a partir de su ID
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de AuthorDetailDTO con los datos del Author consultado
     *
     */
    @GET
    @Path("{id: \\d+}")
    public AuthorDetailDTO getAuthor(@PathParam("id") Long id) {
        AuthorEntity entity = authorLogic.getAuthor(id);
        if (entity == null) {
            throw new WebApplicationException("El author no existe", 404);
        }
        return new AuthorDetailDTO(entity);
    }

    /**
     * Se encarga de crear un Author en la base de datos
     *
     * @param dto Objeto de AuthorDetailDTO con los datos nuevos
     * @return Objeto de AuthorDetailDTOcon los datos nuevos y su ID
     *
     */
    @POST
    public AuthorDetailDTO createAuthor(AuthorDetailDTO dto) {
        return new AuthorDetailDTO(authorLogic.createAuthor(dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Author
     *
     * @param id Identificador de la instancia de Author a modificar
     * @param dto Instancia de AuthorDetailDTO con los nuevos datos
     * @return Instancia de AuthorDetailDTO con los datos actualizados
     *
     */
    @PUT
    @Path("{id: \\d+}")
    public AuthorDetailDTO updateAuthor(@PathParam("id") Long id, AuthorDetailDTO dto) {
        AuthorEntity entity = dto.toEntity();
        entity.setId(id);
        AuthorEntity oldEntity = authorLogic.getAuthor(id);
        if (oldEntity == null) {
            throw new WebApplicationException("El author no existe", 404);
        }
        entity.setBooks(oldEntity.getBooks());
        return new AuthorDetailDTO(authorLogic.updateAuthor(entity));
    }

    /**
     * Elimina una instancia de Author de la base de datos
     *
     * @param id Identificador de la instancia a eliminar
     *
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteAuthor(@PathParam("id") Long id) {
        AuthorEntity entity = authorLogic.getAuthor(id);
        if (entity == null) {
            throw new WebApplicationException("El author no existe", 404);
        }
        authorLogic.deleteAuthor(id);
    }

    @Path("{authorsId: \\d+}/books")
    public Class<AuthorBooksResource> getAuthorBooksResource(@PathParam("authorsId") Long authorsId) {
        AuthorEntity entity = authorLogic.getAuthor(authorsId);
        if (entity == null) {
            throw new WebApplicationException("El author no existe", 404);
        }
        return AuthorBooksResource.class;
    }

}
