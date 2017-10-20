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

import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ISIS2603
 */
@Stateless
public class BookPersistence {

    private static final Logger LOGGER = Logger.getLogger(BookPersistence.class.getName());

    @PersistenceContext(unitName = "BookStorePU")
    protected EntityManager em;

    public BookEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando libro con id={0}", id);
        return em.find(BookEntity.class, id);
    }

    public List<BookEntity> findAll() {
        LOGGER.info("Consultando todos los libros");
        Query q = em.createQuery("select u from BookEntity u");
        return q.getResultList();
    }

    public BookEntity create(BookEntity entity) {
        LOGGER.info("Creando un libro nuevo");
        em.persist(entity);
        LOGGER.info("Libro creado");
        return entity;
    }

    public BookEntity update(BookEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando libro con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando libro con id={0}", id);
        BookEntity entity = em.find(BookEntity.class, id);
        em.remove(entity);
    }
}
