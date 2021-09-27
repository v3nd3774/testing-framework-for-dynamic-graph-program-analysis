package edu.uta.cse6324.team9.TradeConnect.repository;

import edu.uta.cse6324.team9.TradeConnect.entity.Person;
import edu.uta.cse6324.team9.TradeConnect.entity.Ticker;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TickerRepository extends Neo4jRepository<Ticker, Long> {
}
