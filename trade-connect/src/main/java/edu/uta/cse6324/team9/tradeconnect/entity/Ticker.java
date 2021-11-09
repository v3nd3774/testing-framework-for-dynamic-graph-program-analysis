package edu.uta.cse6324.team9.tradeconnect.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter @Setter
@NoArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"})
public class Ticker {
    @Id @GeneratedValue private Long id;
    private String name;

    public Ticker(String name) {
        this.name = name;
    }
}
