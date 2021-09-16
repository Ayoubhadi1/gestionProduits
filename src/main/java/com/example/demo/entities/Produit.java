package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Produit {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min = 5 , max = 15)
	private String name;
	@Temporal(TemporalType.DATE)
	//pour lui, les données de la requette http post envoyé par le form c'est string, 
	//donc quand spring va enregistrer la date dans l'objet Date , il doit savoir quel est le format de conversion qu'il 
	//va utiliser pour le stocker
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date dateExp;
	private boolean disponible;
	@DecimalMin("4")
	private int score;
	
}
