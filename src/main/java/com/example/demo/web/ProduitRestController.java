package com.example.demo.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ProduitRepository;
import com.example.demo.entities.Produit;
import com.sun.xml.bind.v2.runtime.unmarshaller.IntArrayData;

@RestController		//dans ce cas on n'a pas besoin de @ResponseBody dans chaque méthode
public class ProduitRestController {
	@Autowired
	private ProduitRepository produitRepository ;
	
	
	
	@GetMapping("listProduit")
	@ResponseBody	//inclure la réponse dans le corps de la réponse avec la sérialisation en json de la liste des objets produit
	public List<Produit> listProduit(){
		return produitRepository.findAll();
	}
	
	@GetMapping("Produit/{id}")
	@ResponseBody	//inclure la réponse dans le corps de la réponse avec la sérialisation en json de la liste des objets produit
	public Produit getOne(@PathVariable Long id){
		return produitRepository.findById(id).get();
	}
	
}
