package edu.uta.cse6324.team9.TradeConnect.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter @Setter
@NoArgsConstructor
@ToString(exclude = {"id"})
public class Ticker {
    @Id @GeneratedValue private Long id;
    private String name;

    public Ticker(String name) {
        this.name = name;
    }
}
