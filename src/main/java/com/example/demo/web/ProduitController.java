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

import com.example.demo.dao.ProduitRepository;
import com.example.demo.entities.Produit;
import com.sun.xml.bind.v2.runtime.unmarshaller.IntArrayData;

@Controller
public class ProduitController {
	@Autowired
	private ProduitRepository produitRepository ;
	
	@GetMapping(path = "/index")
	public String index() {
		return "index";
	}
	
	@GetMapping(path = "/produits")
	public String listProduits(Model model , 
			//pour récupérer les parametres
			@RequestParam(name = "page" , defaultValue = "0") int page , 
			@RequestParam(name = "size" , defaultValue = "5") int size ,
			@RequestParam(name = "keyword" , defaultValue = "") String key) {
		Page<Produit> pageProduits = produitRepository.findByNameContains(key, PageRequest.of(page, size)); //joue aussi le role de findAll si key et vide
		model.addAttribute("produits", pageProduits.getContent());
		model.addAttribute("pages", new int[pageProduits.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		model.addAttribute("keyword", key);
		return "produits";
	}
	
	@GetMapping(path = "/deleteProduit")
	public String deleteProduit(Long id , String keyword , int page , int size) { //je peux récupérer le id directement avec le meme nom sans RequestParam
		produitRepository.deleteById(id);
		return "redirect:/produits?keyword="+keyword+"&page="+page+"&size="+size;
	}
	
	@GetMapping(path = "/formProduit")
	public String formProduit(Model model) {
		model.addAttribute("produit", new Produit());
		model.addAttribute("mode", "new");
		return "formProduit";
	}
	
	@PostMapping(path = "/saveProduit")
	public String savePrd(@Valid Produit produit , BindingResult bindingResult , Model model) {	//bindingResult est une collection qui contient la liste des erreurs générés lors de la validation
		if(bindingResult.hasErrors()) return "formProduit";
		produitRepository.save(produit);
		model.addAttribute("produit", produit);
		return "confirmation";
	}
	
	@GetMapping(path = "/editProduit")
	public String editProduit(Model model , Long id) {
		Produit p = produitRepository.findById(id).get();
		model.addAttribute("produit", p);
		model.addAttribute("mode", "edit");
		return "formProduit";
	}
	
	/*@GetMapping("listProduit")
	@ResponseBody	//inclure la réponse dans le corps de la réponse avec la sérialisation en json de la liste des objets produit
	public List<Produit> listProduit(){
		return produitRepository.findAll();
	}
	
	@GetMapping("Produit/{id}")
	@ResponseBody	//inclure la réponse dans le corps de la réponse avec la sérialisation en json de la liste des objets produit
	public Produit getOne(@PathVariable Long id){
		return produitRepository.findById(id).get();
	}*/
	
}
