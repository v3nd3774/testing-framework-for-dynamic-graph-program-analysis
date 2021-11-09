package edu.uta.cse6324.team9.tradeconnect.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Node
@Getter @Setter
@NoArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"})
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

    public void setBuys(Set<Buy> buys){
        this.buys = Optional.ofNullable(this.buys).orElseGet(HashSet::new);
        this.buys.addAll(buys);
    }

    public void setSells(Set<Sell> sells) {
        this.sells = Optional.ofNullable(this.sells).orElseGet(HashSet::new);
        this.sells.addAll(sells);
    }
}
