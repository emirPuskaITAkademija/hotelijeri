package com.academy.hotelijeri.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;

//    @GetMapping("/country")
//    public String showCountries(Model model) {
//        Country country = new Country();
//        //country id=null, name=null
//        model.addAttribute("country", country);
//        List<Country> countries = countryRepository.findAll();
//        model.addAttribute("countries", countries);
//        return "country";//country.html
//    }

    @GetMapping("/country")
    public String showCountryPage(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Country country = new Country();
        //country id=null, name=null
        model.addAttribute("country", country);
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Country> countryPage = countryRepository.findAll(pageRequest);
        model.addAttribute("countries", countryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", countryPage.getTotalPages());
        return "country";//country.html
    }

    @PostMapping("/country")
    public String saveCountry(@ModelAttribute("country") Country country) {
        //1. country čiji je id=1, name=Njemačkaaa => UPDATE statement jer je id != null
        //2. country čiji je id=null, name=Maroko => INSERT statement je id == null
        countryRepository.save(country);
        return "redirect:/country";
    }

    @GetMapping("/country/edit/{id}")
    public String editCountry(@PathVariable int id, Model model) {
        Country country = countryRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("No country with id " + id));
        //country, id=1, name=Njemačka
        model.addAttribute("country", country);
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "country";//country.html -> HTTP response
    }

    @GetMapping("/country/delete/{id}")
    public String deleteTown(@PathVariable int id) {
        countryRepository.deleteById(id);
        return "redirect:/country";
    }
}
