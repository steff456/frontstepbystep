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

import co.edu.uniandes.csw.bookstore.ejb.EditorialLogic;
import co.edu.uniandes.csw.bookstore.dtos.EditorialDetailDTO;
import co.edu.uniandes.csw.bookstore.entities.EditorialEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.persistence.EditorialPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso REST correspondiente a "editorials".
 *
 * Note que la aplicación (definida en RestConfig.java) define la ruta "/api" y
 * este recurso tiene la ruta "editorials". Al ejecutar la aplicación, el
 * recurso será accesibe a través de la ruta "/api/editorials"
 *
 * @author ISIS2603
 *
 */
@Path("editorials")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EditorialResource {

    @Inject
    EditorialLogic editorialLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    private static final Logger LOGGER = Logger.getLogger(EditorialPersistence.class.getName());

    /**
     * POST http://localhost:8080/backstepbystep-web/api/editorials Ejemplo
     * json: { "name":"Norma" }
     *
     * @param editorial correponde a la representación java del objeto json
     * enviado en el llamado.
     * @return Devuelve el objeto json de entrada que contiene el id creado por
     * la base de datos y el tipo del objeto java. Ejemplo: { "type":
     * "editorialDetailDTO", "id": 1, "name": "Norma" }
     * @throws BusinessLogicException
     */
    @POST
    public EditorialDetailDTO createEditorial(EditorialDetailDTO editorial) throws BusinessLogicException {
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        EditorialEntity editorialEntity = editorial.toEntity();
        // Invoca la lógica para crear la editorial nueva
        EditorialEntity nuevoEditorial = editorialLogic.createEditorial(editorialEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        return new EditorialDetailDTO(nuevoEditorial);
    }

    /**
     * GET para todas las editoriales.
     * http://localhost:8080/backstepbystep-web/api/editorials
     *
     * @return la lista de todas las editoriales en objetos json DTO.
     * @throws BusinessLogicException
     */
    @GET
    public List<EditorialDetailDTO> getEditorials() throws BusinessLogicException {
        return listEntity2DetailDTO(editorialLogic.getEditorials());
    }

    /**
     * GET para una editorial
     * http://localhost:8080/backstepbystep-web/api/editorials/1
     *
     * @param id corresponde al id de la editorial buscada.
     * @return La editorial encontrada. Ejemplo: { "type": "editorialDetailDTO",
     * "id": 1, "name": "Norma" }
     * @throws BusinessLogicException
     *
     * En caso de no existir el id de la editorial buscada se retorna un 404 con
     * el mensaje.
     */
    @GET
    @Path("{id: \\d+}")
    public EditorialDetailDTO getEditorial(@PathParam("id") Long id) throws BusinessLogicException {
        EditorialEntity entity = editorialLogic.getEditorial(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /editorials/" + id + " no existe.", 404);
        }
        return new EditorialDetailDTO(editorialLogic.getEditorial(id));
    }

    /**
     * PUT http://localhost:8080/backstepbystep-web/api/editorials/1 Ejemplo
     * json { "id": 1, "name": "cambio de nombre" }
     *
     * @param id corresponde a la editorial a actualizar.
     * @param editorial corresponde a al objeto con los cambios que se van a
     * realizar.
     * @return La editorial actualizada.
     * @throws BusinessLogicException
     *
     * En caso de no existir el id de la editorial a actualizar se retorna un
     * 404 con el mensaje.
     */
    @PUT
    @Path("{id: \\d+}")
    public EditorialDetailDTO updateEditorial(@PathParam("id") Long id, EditorialDetailDTO editorial) throws BusinessLogicException {
        editorial.setId(id);
        EditorialEntity entity = editorialLogic.getEditorial(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /editorials/" + id + " no existe.", 404);
        }
        return new EditorialDetailDTO(editorialLogic.updateEditorial(id, editorial.toEntity()));
    }

    /**
     * DELETE http://localhost:8080/backstepbystep-web/api/editorials/1
     *
     * @param id corresponde a la editorial a borrar.
     * @throws BusinessLogicException
     *
     * En caso de no existir el id de la editorial a actualizar se retorna un
     * 404 con el mensaje.
     * @throws java.sql.SQLException
     *
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteEditorial(@PathParam("id") Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una editorial con id {0}", id);
        EditorialEntity entity = editorialLogic.getEditorial(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /editorials/" + id + " no existe.", 404);
        }
        editorialLogic.deleteEditorial(id);

    }

    @Path("{editorialsId: \\d+}/books")
    public Class<EditorialBooksResource> getEditorialBooksResource(@PathParam("editorialsId") Long editorialsId) {
        EditorialEntity entity = editorialLogic.getEditorial(editorialsId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /editorials/" + editorialsId + " no existe.", 404);
        }
        return EditorialBooksResource.class;
    }

    /**
     *
     * lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EditorialEntity a una lista de
     * objetos EditorialDetailDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<EditorialDetailDTO> listEntity2DetailDTO(List<EditorialEntity> entityList) {
        List<EditorialDetailDTO> list = new ArrayList<>();
        for (EditorialEntity entity : entityList) {
            list.add(new EditorialDetailDTO(entity));
        }
        return list;
    }

}
