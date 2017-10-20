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

import java.util.Date;

/**
 *
 * @author ISIS2603
 */
public class AuthorDTO {

    private Long id;
    private String name;
    private Date birthDate;
    private String description;
    private String image;

    /**
     * 
     */
    public AuthorDTO() {
    }

    /**
     * Crea un objeto AuthorDTO a partir de un objeto AuthorEntity.
     *
     * @param entity Entidad AuthorEntity desde la cual se va a crear el nuevo
     * objeto.
     * 
     */
    public AuthorDTO(AuthorEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.birthDate = entity.getBirthDate();
            this.description = entity.getDescription();
            this.image = entity.getImage();
        }
    }

    /**
     * Convierte un objeto AuthorDTO a AuthorEntity.
     *
     * @return Nueva objeto AuthorEntity.
     * 
     */
    public AuthorEntity toEntity() {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(this.getId());
        entity.setName(this.getName());
        entity.setBirthDate(this.getBirthDate());
        entity.setDescription(this.description);
        entity.setImage(this.image);
        return entity;
    }

    /**
     * Obtiene el atributo id.
     *
     * @return atributo id.
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     *
     * @param id nuevo valor del atributo
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el atributo name.
     *
     * @return atributo name.
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el valor del atributo name.
     *
     * @param name nuevo valor del atributo
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el atributo birthDate.
     *
     * @return atributo birthDate.
     * 
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Establece el valor del atributo birthDate.
     *
     * @param birthdate nuevo valor del atributo
     * 
     */
    public void setBirthDate(Date birthdate) {
        this.birthDate = birthdate;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

}
