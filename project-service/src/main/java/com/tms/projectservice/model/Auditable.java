package com.tms.projectservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public abstract class Auditable {

    @CreatedDate
    @Field(value = "createdAt")
    private LocalDateTime createdAt;

    @CreatedBy
    @Field(value = "updatedAt")
    private String createdBy;

}