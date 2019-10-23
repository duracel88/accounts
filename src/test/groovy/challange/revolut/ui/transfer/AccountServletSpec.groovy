package challange.revolut.ui.transfer

import static groovyx.net.http.ContentType.TEXT

class AccountServletSpec extends ServletBaseSpec {


    def "Return 404 when id is not specified"() {
        given:
        expect:
        def response = client.get(path: "/account", requestContentType: TEXT)
        response.status == 404
    }

    def "Return balance when id is specified"() {
        given:
        expect:
        def response = client.get(path: "/account/${idGBP1}", requestContentType: TEXT)
        response.status == 200
    }
}
