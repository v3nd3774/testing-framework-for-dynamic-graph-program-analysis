package edu.uta.cse6324.team9.TradeConnect.repository;

import edu.uta.cse6324.team9.TradeConnect.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Person findByName(String name);
}
