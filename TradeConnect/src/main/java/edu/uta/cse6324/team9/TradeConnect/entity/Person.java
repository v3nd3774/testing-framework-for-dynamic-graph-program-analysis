package edu.uta.cse6324.team9.TradeConnect.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Node
@Getter
@NoArgsConstructor
@ToString(exclude = {"id"})
public class Person {

    public Person(String name) {
        this.name = name;
    }

    @Id @GeneratedValue private Long id;
    private String name;

    @Relationship("BUY") private Set<Buy> buys;
    @Relationship("SELL") private Set<Sell> sells;

    public void addBuy(Buy buy){
        buys = Optional.ofNullable(buys).orElseGet(HashSet::new);
        buys.add(buy);
    }

    public void addSell(Sell sell){
        sells = Optional.ofNullable(sells).orElseGet(HashSet::new);
        sells.add(sell);
    }

}
