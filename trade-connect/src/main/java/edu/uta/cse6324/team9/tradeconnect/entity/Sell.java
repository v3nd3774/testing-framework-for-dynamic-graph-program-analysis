package edu.uta.cse6324.team9.tradeconnect.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.math.BigDecimal;

@RelationshipProperties
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor
@Getter @Setter
public class Sell {
    @Id @GeneratedValue private Long id;
    private int count;
    private BigDecimal cost;

    @TargetNode
    private Ticker ticker;

    public Sell(int count, BigDecimal cost, Ticker ticker) {
        this.count = count;
        this.cost = cost;
        this.ticker = ticker;
    }
}
