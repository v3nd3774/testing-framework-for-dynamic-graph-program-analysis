package edu.uta.cse6324.team9.tradeconnect.repository;

import edu.uta.cse6324.team9.tradeconnect.entity.Ticker;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TickerRepository extends Neo4jRepository<Ticker, Long> {

    Ticker findByName(String name);
}
