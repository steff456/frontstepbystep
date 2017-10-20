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
package co.edu.uniandes.csw.bookstore.dtos;

import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ISIS2603
 */
public class BookDetailDTO extends BookDTO {

    /*
    * Relación a una editorial
     */
    private EditorialDTO editorial;
    // relación  cero o muchos reviews 
    private List<ReviewDTO> reviews;

    // relación  cero o muchos author
    private List<AuthorDTO> authors;

    public BookDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity
     */
    public BookDetailDTO(BookEntity entity) {
        super(entity);
        if (entity.getEditorial() != null) {
            this.editorial = new EditorialDTO(entity.getEditorial());
        } else {
            entity.setEditorial(null);
        }
        if (entity.getReviews() != null) {
            reviews = new ArrayList<>();
            for (ReviewEntity entityReview : entity.getReviews()) {
                reviews.add(new ReviewDTO(entityReview));
            }
        }
        if (entity.getAuthors() != null) {
            authors = new ArrayList<>();
            for (AuthorEntity entityAuthor : entity.getAuthors()) {
                authors.add(new AuthorDTO(entityAuthor));
            }

        }
    }

    @Override
    public BookEntity toEntity() {
        BookEntity bookE = super.toEntity();
        if (this.getEditorial() != null) {
            bookE.setEditorial(this.getEditorial().toEntity());
        }
        if (getReviews() != null) {
            List<ReviewEntity> reviewsEntity = new ArrayList<>();
            for (ReviewDTO dtoReview : getReviews()) {
                reviewsEntity.add(dtoReview.toEntity());
            }
            bookE.setReviews(reviewsEntity);
        }
        if (authors != null) {
            List<AuthorEntity> authorsEntity = new ArrayList<>();
            for (AuthorDTO dtoAuthor : authors) {
                authorsEntity.add(dtoAuthor.toEntity());
            }
            bookE.setAuthors(authorsEntity);
        }
        return bookE;
    }

    /**
     * @return the reviews
     */
    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    /**
     * @param reviews the reviews to set
     */
    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    /**
     * @return the authors
     */
    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    /**
     * @param authors the authors to set
     */
    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public void setEditorial(EditorialDTO editorial) {
        this.editorial = editorial;
    }

    public EditorialDTO getEditorial() {
        return editorial;
    }
}
