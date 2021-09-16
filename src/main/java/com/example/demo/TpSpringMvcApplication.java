package com.example.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;

import com.example.demo.dao.ProduitRepository;
import com.example.demo.entities.Produit;

@SpringBootApplication
public class TpSpringMvcApplication implements CommandLineRunner{

	@Autowired
	private ProduitRepository produitRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(TpSpringMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		/*produitRepository.save(new Produit(null , "Tshirt" , new Date() , false));
		produitRepository.save(new Produit(null , "puma" , new Date() , true));
		produitRepository.save(new Produit(null , "adidas" , new Date() , false));
		produitRepository.save(new Produit(null , "nike" , new Date() , true));*/
		
		produitRepository.findByNameContains("", PageRequest.of(0, 6)).forEach(p->{
			System.out.println(p.toString());
		});
		System.out.println("*************************************00");
		produitRepository.findByName("adidas").forEach(p->{
			System.out.println(p.toString());
		});;
	}

}
