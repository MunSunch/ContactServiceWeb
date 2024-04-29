package com.munsun.contact_service.controllers;

import com.munsun.contact_service.models.Contact;
import com.munsun.contact_service.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ContactController {
    private final ContactService service;

    @GetMapping
    public String getMainPage(Model model) {
        model.addAttribute("contacts", service.getContacts());
        return "index";
    }

    @GetMapping("/save")
    public String getSavePage(Model model) {
        model.addAttribute("contact", service.getEmptyContact());
        return "pages/save_page";
    }

    @PostMapping("/save")
    public String addContact(@ModelAttribute Contact contact) {
        service.addContact(contact);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable Long id, Model model) {
        Contact contact = service.getContact(id);
        model.addAttribute("contact", contact);
        return "pages/save_page";
    }

    @PostMapping("/edit")
    public String editContact(@ModelAttribute Contact contact) {
        service.addContact(contact);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        service.removeContact(id);
        return "redirect:/";
    }
}