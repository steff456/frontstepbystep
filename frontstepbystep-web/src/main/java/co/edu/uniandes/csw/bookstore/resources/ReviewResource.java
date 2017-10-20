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

import co.edu.uniandes.csw.bookstore.dtos.ReviewDTO;
import co.edu.uniandes.csw.bookstore.ejb.ReviewLogic;
import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;

import java.util.ArrayList;
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
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso REST correspondiente a "reviews".
 *
 * Note que la aplicación (definida en RestConfig.java) define la ruta "/api" y
 * este recurso tiene la ruta "reviews". Al ejecutar la aplicación, el recurse
 * será accesible a través de la ruta "/api/books/idBook/reviews"
 *
 *
 * @author ISIS2603
 *
 */
@Produces("application/json")
@Consumes("application/json")
public class ReviewResource {

    @Inject
    ReviewLogic reviewLogic;

    @GET
    public List<ReviewDTO> getReviews(@PathParam("idBook") Long idBook) throws BusinessLogicException {
        return listEntity2DTO(reviewLogic.getReviews(idBook));
    }

    @GET
    @Path("{id: \\d+}")
    public ReviewDTO getReview(@PathParam("idBook") Long idBook, @PathParam("id") Long id) throws BusinessLogicException {
        ReviewEntity entity = reviewLogic.getReview(idBook, id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + idBook + "/reviews/" + id + " no existe.", 404);
        }
        return new ReviewDTO(entity);
    }

    @POST
    public ReviewDTO createReview(@PathParam("idBook") Long idBook, ReviewDTO review) throws BusinessLogicException {
        return new ReviewDTO(reviewLogic.createReview(idBook, review.toEntity()));
    }

    @PUT
    @Path("{id: \\d+}")
    public ReviewDTO updateReview(@PathParam("idBook") Long idBook, @PathParam("id") Long id, ReviewDTO review) throws BusinessLogicException {
        review.setId(id);
        ReviewEntity entity = reviewLogic.getReview(idBook, id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + idBook + "/reviews/" + id + " no existe.", 404);
        }
        return new ReviewDTO(reviewLogic.updateReview(idBook, review.toEntity()));

    }

    @DELETE
    @Path("{id: \\d+}")
    public void deleteReview(@PathParam("idBook") Long idBook, @PathParam("id") Long id) throws BusinessLogicException {
        ReviewEntity entity = reviewLogic.getReview(idBook, id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + idBook + "/reviews/" + id + " no existe.", 404);
        }
        reviewLogic.deleteReview(idBook, id);
    }

    private List<ReviewDTO> listEntity2DTO(List<ReviewEntity> entityList) {
        List<ReviewDTO> list = new ArrayList<>();
        for (ReviewEntity entity : entityList) {
            list.add(new ReviewDTO(entity));
        }
        return list;
    }

}
