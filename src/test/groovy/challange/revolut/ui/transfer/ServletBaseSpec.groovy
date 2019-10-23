package challange.revolut.ui.transfer

import challange.revolut.Application
import challange.revolut.infrastructure.db.DatabaseModule
import challange.revolut.service.ServiceModule
import challange.revolut.ui.UIModule
import com.google.inject.Guice
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification

abstract class ServletBaseSpec extends Specification{

    def idGBP1 = 'd47bf3a2-a0f7-489a-81ce-5a4835cc5ab8'
    def idGBP2 = '428360c4-6d6b-47b4-a1f9-1b48c03bc073'
    def idPLN1 = 'd47bf3a2-a0f7-489a-81ce-5a4835cc5ab7'

    @Shared
    def client = new RESTClient( "http://localhost:4201")

    def static server

    def setupSpec() {
        client.handler.failure = client.handler.success
        Guice.createInjector(new UIModule(), new ServiceModule(), new DatabaseModule())
        server = Application.startHttpServer(4201)
    }

    def cleanupSpec(){
        server.stop()
    }
}
