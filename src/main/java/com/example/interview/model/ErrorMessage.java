package com.example.interview.model;

import java.time.LocalDateTime;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RegisterForReflection
public class ErrorMessage {
	private int statusCode;
	private LocalDateTime timestamp;
	private String message;
	private String description;
}
