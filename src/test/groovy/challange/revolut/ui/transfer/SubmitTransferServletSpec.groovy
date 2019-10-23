package challange.revolut.ui.transfer


import groovyx.net.http.HttpResponseDecorator

import java.nio.charset.StandardCharsets

import static groovyx.net.http.ContentType.TEXT

class SubmitTransferServletSpec extends ServletBaseSpec {

    def "Accept valid transfer request"() {
        given:
        expect:
        def body = """{"from":"${idGBP1}", "to":"${idGBP2}", "value":1, "currency":"GBP"}"""
        def response = client.post(path: "/transfer", body: body, requestContentType: TEXT)
        response.status == 202
        readData(response).contains("transactionId")
    }

    def "Decline invalid transfer request with negative value"() {
        given:
        expect:
        def body = """{"from":"${idGBP1}", "to":"${idGBP2}", "value":-1, "currency":"GBP"}"""
        def response = client.post(path: "/transfer", body: body, requestContentType: TEXT)
        response.status == 400
    }

    def "Decline invalid transfer request with insufficient balance"() {
        given:
        expect:
        def body = """{"from":"${idGBP1}", "to":"${idGBP2}", "value":1000, "currency":"GBP"}"""
        def response = client.post(path: "/transfer", body: body, requestContentType: TEXT)
        response.status == 406
    }

    def "Cannot send money to account with different currency"() {
        given:
        expect:
        def body = """{"from":"${idGBP1}", "to":"${idPLN1}", "value":1000, "currency":"GBP"}"""
        def response = client.post(path: "/transfer", body: body, requestContentType: TEXT)
        response.status == 400
    }

    def "Cannot send money to account in different currency"() {
        given:
        expect:
        def body = """{"from":"${idGBP1}", "to":"${idGBP2}", "value":1000, "currency":"PLN"}"""
        def response = client.post(path: "/transfer", body: body, requestContentType: TEXT)
        response.status == 400
    }

    def "Currency field is mandatory"() {
        given:
        expect:
        def body = """{"from":"${idGBP1}", "to":"${idGBP2}", "value":1000}"""
        def response = client.post(path: "/transfer", body: body, requestContentType: TEXT)
        response.status == 400
    }

    def "Value field is mandatory"() {
        given:
        expect:
        def body = """{"from":"${idGBP1}", "to":"${idGBP2}", "currency":"PLN"}"""
        def response = client.post(path: "/transfer", body: body, requestContentType: TEXT)
        response.status == 400
    }


    def "Get all transactions"() {
        expect:
        def body = """{"from":"${idGBP1}", "to":"${idGBP2}", "value":1, "currency":"GBP"}"""
        client.post(path: "/transfer", body: body, requestContentType: TEXT)

        def response = client.get(path: "/transfer")
        response.status == 200
        def data = readData(response)

        data.contains(""""from":"${idGBP1}\"""")
        data.contains(""""to":"${idGBP2}\"""")
        data.contains(""""amount":1.0""")
        data.contains("timestamp")
        data.contains(""""currency":"GBP\"""")

    }

    def readData(HttpResponseDecorator response) {
        def input = response.data
        int n = input.available()
        byte[] bytes = new byte[n]
        input.read(bytes, 0, n)
        return new String(bytes, StandardCharsets.UTF_8)
    }
}
