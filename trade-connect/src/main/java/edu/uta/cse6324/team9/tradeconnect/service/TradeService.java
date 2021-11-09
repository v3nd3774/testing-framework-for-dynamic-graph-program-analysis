package edu.uta.cse6324.team9.tradeconnect.service;

import edu.uta.cse6324.team9.tradeconnect.entity.Buy;
import edu.uta.cse6324.team9.tradeconnect.entity.Person;
import edu.uta.cse6324.team9.tradeconnect.entity.Ticker;
import edu.uta.cse6324.team9.tradeconnect.repository.PersonRepository;
import edu.uta.cse6324.team9.tradeconnect.repository.TickerRepository;
import io.github.v3nd3774.uta2218.cse6324.generate.GraphTest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* TradeService would allow to create/insert person node and ticker node
* it could also update node with BUY/SELL Relationship between person and ticker
* custom complex logic can be written in this class
* */
@Service
public class TradeService {

    //Autowire Person Repository
    @Setter @Autowired private PersonRepository personRepository;
    //Autowire Ticker Repository
    @Setter @Autowired private TickerRepository tickerRepository;

    //Clean all the nodes in Graph DB

    public void deleteAll(){
        personRepository.deleteAll();
        tickerRepository.deleteAll();
    }

    public void saveAllPersons(List<Person> personList){
        personList.forEach(this::savePerson);
    }

    //save Person node to Graph DB
    @GraphTest(init = "one-purchase.dot", inbound = {"person_input.json"}, outbound = {"person_output.json"})
    public Person savePerson(Person person){
        person.getBuys().forEach(buy -> {
            Ticker ticker = tickerRepository.findByName(buy.getTicker().getName());
            if (null != ticker)
                buy.setTicker(ticker);
        });
        return personRepository.save(person);
    }

    //save Ticker node to Graph DB
    public Ticker saveTicker(Ticker ticker){
        return tickerRepository.save(ticker);
    }

    /*Other services to operate on the Neo4j Graph !!!*/



}
