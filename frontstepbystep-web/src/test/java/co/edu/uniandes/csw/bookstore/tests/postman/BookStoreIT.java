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
package co.edu.uniandes.csw.bookstore.tests.postman;

import co.edu.uniandes.csw.bookstore.resources.BookResource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author ISIS2603
 */
@RunWith(Arquillian.class)
public class BookStoreIT {

    private static final String BASEPATH = System.getProperty("user.dir");
    String path = BASEPATH.concat("\\collections\\runners\\backstepbystep-paso5CollectionRunner.bat");

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "backstepbystep-web.war")
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(BookResource.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));

    }

    public void setPostmanCollectionValues() throws FileNotFoundException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException, ExecutionException {

        FileWriter wrt = new FileWriter(path);
        try {
            wrt.write("newman run ".concat(BASEPATH.concat("\\collections\\backstepbystep-paso5.postman_collection.json").concat(" --disable-unicode")));
            wrt.flush();
            wrt.close();
        } catch (Exception e) {
        }

    }

    /**
     * Test para la colección de postman
     */
    @Test
    @RunAsClient
    public void postman() throws FileNotFoundException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException, ExecutionException {
        setPostmanCollectionValues();

        try {
            String prop = System.getProperty("user.dir");
            Process process = Runtime.getRuntime().exec(prop + "\\collections\\runners\\backstepbystep-paso5CollectionRunner.bat");

            InputStream inputStream = process.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String ln;
            Logger log = Logger.getAnonymousLogger();

            while ((ln = bf.readLine()) != null) {
                line = line.concat(ln);
                System.out.println(ln);
            }

            inputStream.close();
            bf.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
