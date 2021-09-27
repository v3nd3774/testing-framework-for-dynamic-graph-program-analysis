package edu.uta.cse6324.team9.TradeConnect.service;

import edu.uta.cse6324.team9.TradeConnect.entity.Person;
import edu.uta.cse6324.team9.TradeConnect.entity.Ticker;
import edu.uta.cse6324.team9.TradeConnect.repository.PersonRepository;
import edu.uta.cse6324.team9.TradeConnect.repository.TickerRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

    @Setter(onMethod_ = {@Autowired}) private PersonRepository personRepository;
    @Setter(onMethod_ = {@Autowired}) private TickerRepository tickerRepository;

    public void deleteAll(){
        personRepository.deleteAll();
        tickerRepository.deleteAll();
    }

    public Person savePerson(Person person){
        return personRepository.save(person);
    }

    public Ticker saveTicker(Ticker ticker){
        return tickerRepository.save(ticker);
    }

    /*Other services to operate on the Neo4j Graph !!!*/

}
