package com.tms.userservice.model;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
public abstract class Auditable {
    @CreatedDate
    @Field(value = "createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field(value = "updatedAt")
    private LocalDateTime updatedAt;
}
