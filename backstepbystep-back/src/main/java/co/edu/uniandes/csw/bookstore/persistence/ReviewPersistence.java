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
package co.edu.uniandes.csw.bookstore.persistence;

import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ISIS2603
 */
@Stateless
public class ReviewPersistence {

    private static final Logger LOGGER = Logger.getLogger(ReviewPersistence.class.getName());

    @PersistenceContext(unitName = "BookStorePU")
    protected EntityManager em;

    public ReviewEntity create(ReviewEntity entity) {
        LOGGER.info("Creando un review nuevo");
        em.persist(entity);
        LOGGER.info("Review creado");
        return entity;
    }

    public ReviewEntity update(ReviewEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando review con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando review con id={0}", id);
        ReviewEntity entity = em.find(ReviewEntity.class, id);
        em.remove(entity);
    }

    public ReviewEntity find(Long bookid, Long reviewid) {
        TypedQuery<ReviewEntity> q = em.createQuery("select p from ReviewEntity p where (p.book.id = :bookid) and (p.id = :reviewid)", ReviewEntity.class);
        q.setParameter("bookid", bookid);
        q.setParameter("reviewid", reviewid);
        List<ReviewEntity> results = q.getResultList();
        ReviewEntity review = null;
        if (results == null) {
            review = null;
        } else if (results.isEmpty()) {
            review = null;
        } else if (results.size() >= 1) {
            review = results.get(0);
        }

        return review;
    }
}
