package com.example.interview.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotEmpty
	@Size(min = 3, max = 100)
	private String nome;

	@NotEmpty
	@Pattern(regexp = "M|F", flags = Flag.CASE_INSENSITIVE)
	private String sexo;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@NotNull
	private LocalDate nascimento;

	@ManyToOne
	private Cidade cidade;

	public Integer getIdade() {
		return  Long.valueOf(LocalDate.from(nascimento).until(LocalDate.now(),ChronoUnit.YEARS)).intValue();
	}
}
