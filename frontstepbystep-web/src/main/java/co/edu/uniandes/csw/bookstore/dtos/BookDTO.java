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

import co.edu.uniandes.csw.bookstore.entities.BookEntity;

import java.util.Date;

/**
 *
 * @author ISIS2603
 */
public class BookDTO {

    private Long id;
    private String name;
    private String isbn;
    private String image;
    private String description;
    private Date publishingdate;

    /**
     * Constructor por defecto
     */
    public BookDTO() {
    }

    public BookDTO(BookEntity bookE) {
        if (bookE != null) {
            this.id = bookE.getId();
            this.name = bookE.getName();
            this.isbn = bookE.getIsbn();
            this.image = bookE.getImage();
            this.description = bookE.getDescription();
            this.publishingdate = bookE.getPublishDate();
            
        }
    }

    public BookEntity toEntity() {

        BookEntity bookE = new BookEntity();
        bookE.setId(this.id);
        bookE.setName(this.name);
        bookE.setIsbn(this.isbn);
        bookE.setImage(this.image);
        bookE.setDescription(this.description);
        bookE.setPublishDate(this.publishingdate);
     
        return bookE;
    }
    
        public Long getId() {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     *
     * @param id nuevo valor del atributo
     * @generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the publishingdate
     */
    public Date getPublishingdate() {
        return publishingdate;
    }

    /**
     * @param publishingdate the publishingdate to set
     */
    public void setPublishingdate(Date publishingdate) {
        this.publishingdate = publishingdate;
    }


}
