package edu.uta.cse6324.team9.tradeconnect.repository;

import edu.uta.cse6324.team9.tradeconnect.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Person findByName(String name);
}
