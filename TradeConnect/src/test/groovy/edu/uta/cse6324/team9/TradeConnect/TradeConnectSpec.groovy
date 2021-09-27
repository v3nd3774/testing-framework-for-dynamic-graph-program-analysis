package edu.uta.cse6324.team9.TradeConnect

import edu.uta.cse6324.team9.TradeConnect.entity.Person
import edu.uta.cse6324.team9.TradeConnect.service.TradeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class TradeConnectSpec extends Specification{

    @Autowired TradeService tradeService

    def "deleteAll Test"(){
        when:
        tradeService.deleteAll()

        then:
        true
    }

    def "Insert Person Test"() {
        given:
        Person person = buildPerson();

        when:
        Person savedPerson = tradeService.savePerson(person)

        then:
        savedPerson.getId() == 2
    }

    Person buildPerson() {
        Person greg = new Person("Greg");

    }
}
