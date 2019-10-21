package challange.revolut.domain.valueobject

import spock.lang.Specification

import static challange.revolut.domain.valueobject.Amount.of

class AmountSpec extends Specification {


    def "Amount addition"() {
        expect:
        initValue.add(component) == expectedValue

        where:
        initValue | component | expectedValue
        of(0) | of(1 ) | of(1)
        of(0) | of(1.3333) | of(1.3333)
        of(1.3333) | of(0 ) | of(1.3333)
        of(1) | of(-1 ) | of(0)
        of(-1)| of( -1) | of(-2)
        of(0.321) | of(1.321 ) | of(1.642)


    }

    def "Amount substraction"() {
        expect:
        initValue.subtract(component) == expectedValue

        where:
        initValue | component | expectedValue
        of(0) | of(1 ) | of(-1)
        of(0) | of(1.3333) | of(-1.3333)
        of(1.3333) | of(0 ) | of(1.3333)
        of(1) | of(-1 ) | of(2)
        of(-1)| of( -1) | of(0)
        of(0.321) | of(1.321 ) | of(-1.000)
    }

    def "IsPositive"() {
        expect:
        value.isPositive() == expect

        where:
        value | expect
        of(1.3333)  | true
        of(1)       | true
        of(0.3333)  | true
        of(1.3333)  | true
        of(1.3333)  | true

        of(0)       | false
        of(-1)      | false
        of(-1.543)  | false

    }

    def "Amount is immutable"() {
        given:
        def value = of(100.01)

        when:
        value.add(of(200))

        then:
        value.getValue() == 100.01
    }
}
