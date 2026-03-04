package com.tms.projectservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "projects")
@Getter
@Setter
public class Project extends Auditable{
    @Id
    private String id;

    @Field(value = "name")
    private String name;

    @Field(value = "description")
    private String description;

    @Field(value = "members")
    private List<String> members;
}
