package edu.uta.cse6324.team9.TradeConnect;

import edu.uta.cse6324.team9.TradeConnect.entity.Buy;
import edu.uta.cse6324.team9.TradeConnect.entity.Person;
import edu.uta.cse6324.team9.TradeConnect.entity.Sell;
import edu.uta.cse6324.team9.TradeConnect.entity.Ticker;
import edu.uta.cse6324.team9.TradeConnect.repository.PersonRepository;
import edu.uta.cse6324.team9.TradeConnect.repository.TickerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableNeo4jRepositories
@Slf4j
public class TradeConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeConnectApplication.class, args);
	}


	/*@Bean
	CommandLineRunner demo(PersonRepository personRepository, TickerRepository tickerRepository) {
		return args -> {

			personRepository.deleteAll();

			Person greg = new Person("Greg");
			Person roy = new Person("Roy");
			Person craig = new Person("Craig");

			List<Person> team = Arrays.asList(greg, roy, craig);

			log.info("Before linking up with Neo4j...");

			team.stream().forEach(person -> log.info("\t" + person.toString()));

			personRepository.save(greg);
			personRepository.save(roy);
			personRepository.save(craig);

			greg = personRepository.findByName(greg.getName());

			Ticker apple = new Ticker("AAPL");
			Ticker tesla = new Ticker("TSLA");

			tickerRepository.save(apple);
			tickerRepository.save(tesla);

			greg.addBuy(new Buy(2, BigDecimal.valueOf(125), apple));
			greg.addSell(new Sell(3, BigDecimal.valueOf(587), tesla));

			craig.addSell(new Sell(5, BigDecimal.valueOf(122), apple));
			craig.addBuy(new Buy(8, BigDecimal.valueOf(524), tesla));

			personRepository.save(greg);
			personRepository.save(craig);

			System.out.println(personRepository.findByName(greg.getName()));

		};
	}*/


}
