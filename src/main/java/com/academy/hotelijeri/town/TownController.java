package com.academy.hotelijeri.town;

import com.academy.hotelijeri.country.Country;
import com.academy.hotelijeri.country.CountryRepository;
import com.academy.hotelijeri.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class TownController {

    @Autowired
    private TownRepository townRepository;

    @Autowired
    private CountryRepository countryRepository;


    @GetMapping("/town")
    public String showTownPage(Model model) {
        Town town = new Town();
        model.addAttribute("town", town);
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
        List<Town> towns = townRepository.findAll();
        model.addAttribute("towns", towns);
        return "town";//town.html
    }

    @PostMapping("/town")
    public String saveTown(@ModelAttribute("town") Town town) {
        townRepository.save(town);
        return "redirect:/town";
    }

    @GetMapping("/town/edit/{id}")
    public String editTown(@PathVariable int id, Model model) {
        Town town = townRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ne postoji grad sa id=" + id));

        model.addAttribute("town", town);
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
        List<Town> towns = townRepository.findAll();
        model.addAttribute("towns", towns);
        return "town";//town.html
    }

    @GetMapping("/town/delete/{id}")
    public String deleteTown(@PathVariable int id) {
        townRepository.deleteById(id);
        return "redirect:/town";
    }
}
