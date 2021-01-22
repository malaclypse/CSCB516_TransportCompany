package com.transport.company.controller;

import com.transport.company.dto.ClientCreationDto;
import com.transport.company.entity.Client;
import com.transport.company.exception.ResourceNotFoundException;
import com.transport.company.service.ClientService;
import com.transport.company.util.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("")
    public String showClientList(Model model) throws ResourceNotFoundException {
        var clientList = clientService.getClients();
        // 2 as the company ID is hardcoded because I have not implemented login functionality
        model.addAttribute("clientList", clientList);

        return "redirect:/clients/page/1?sort-field=id&sort-dir=asc";
    }

    @GetMapping(value = "/page/{page-number}")
    public String showClientList( @PathVariable(name = "page-number") final int pageNo,
                                  @RequestParam(name = "sort-field") final String sortField,
                                  @RequestParam(name = "sort-dir") final String sortDir,
                                  Model model) throws ResourceNotFoundException {
        var page = clientService.findPaginated(1, 100, sortField, sortDir);

        model.addAttribute("clientList", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "client/clients";
    }

    @GetMapping("/addNewClient")
    public String showCreateForm(Model model) {
        ClientCreationDto clientCreationForm = new ClientCreationDto();

        clientCreationForm.addClient(new Client());
        model.addAttribute("form", clientCreationForm);

        return "client/addNewClient";
    }

    @GetMapping("/editClient/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) throws ResourceNotFoundException {
        Client client = clientService.getClient( id);

        model.addAttribute("client", client);
        return "client/editClient";
    }

    @PostMapping(value = "/save")
    public String saveClient(@ModelAttribute ClientCreationDto form, Model model) throws ResourceNotFoundException {
        for (var client : form.getClients()){
            clientService.createClient(client);
        }

        model.addAttribute("clients", clientService.getClients());

        return "redirect:/clients";
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable("id") long id, @Valid Client client,
                             BindingResult result, Model model) throws ResourceNotFoundException {
        if (result.hasErrors()) {
            client.setId(id);
            return "client/editClient";
        }

        clientService.updateClient( client.getId(), client);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) throws ResourceNotFoundException {
        clientService.deleteClient(id);

        return "redirect:/clients";
    }

    @GetMapping("/download/clients.csv")
    public void downloadCsv(HttpServletResponse response) throws IOException, IOException, ResourceNotFoundException, IllegalAccessException, IntrospectionException, InvocationTargetException, IOException, IntrospectionException, InvocationTargetException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=clients.csv");
        CsvUtil.downloadClientsCsv(response.getWriter(), clientService.getClients()); ;
    }
}
