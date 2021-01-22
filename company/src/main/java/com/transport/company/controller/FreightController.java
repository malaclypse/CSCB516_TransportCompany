package com.transport.company.controller;

import com.transport.company.dto.FreightCreationDto;
import com.transport.company.entity.Freight;
import com.transport.company.entity.Vehicle;
import com.transport.company.exception.ResourceNotFoundException;
import com.transport.company.service.ClientService;
import com.transport.company.service.DriverService;
import com.transport.company.service.FreightService;
import com.transport.company.service.VehicleService;
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

@Controller
@RequestMapping("/freights")
public class FreightController {
    @Autowired
    private FreightService freightService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ClientService clientService;

    @GetMapping("")
    public String showFreightList(Model model) throws ResourceNotFoundException {
        // 2 as the company ID is hardcoded because I have not implemented login functionality
        model.addAttribute("freightList", freightService.getFreights());
        return "redirect:/freights/page/1?sort-field=id&sort-dir=asc";
    }

    @GetMapping(value = "/page/{page-number}")
    public String showFreightList( @PathVariable(name = "page-number") final int pageNo,
                                  @RequestParam(name = "sort-field") final String sortField,
                                  @RequestParam(name = "sort-dir") final String sortDir,
                                  Model model) throws ResourceNotFoundException {
        var page = freightService.findPaginated(1, 100, sortField, sortDir);

        model.addAttribute("freightList", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "freight/freights";
    }

    @GetMapping("/addNewFreight")
    public String showCreateForm(Model model) throws ResourceNotFoundException {
        FreightCreationDto freightCreationForm = new FreightCreationDto();

        freightCreationForm.addFreight(new Freight());
        model.addAttribute("form", freightCreationForm);
        model.addAttribute("drivers", driverService.getDrivers());
        model.addAttribute("vehicles", vehicleService.getVehicles());
        model.addAttribute("clients", clientService.getClients());

        return "freight/addNewFreight";
    }

    @GetMapping("/editFreight/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) throws ResourceNotFoundException {
        Freight freight = freightService.getFreight(id);

        model.addAttribute("freight", freight);
        model.addAttribute("drivers", driverService.getDrivers());
        model.addAttribute("vehicles", vehicleService.getVehicles());
        model.addAttribute("clients", clientService.getClients());
        return "freight/editFreight";
    }

    @PostMapping(value = "/save")
    public String saveBooks(@ModelAttribute FreightCreationDto form, Model model) throws ResourceNotFoundException {
        for (var freight : form.getFreights()){
            freightService.createFreight(freight);
        }

        model.addAttribute("freights", freightService.getFreights());

        return "redirect:/freights";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Freight freight,
                             BindingResult result, Model model) throws ResourceNotFoundException {
        if (result.hasErrors()) {
            freight.setId(id);
            return "freight/editFreight";
        }

        freightService.updateFreight(freight);
        return "redirect:/freights";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) throws ResourceNotFoundException {
        freightService.deleteFreight(id);

        return "redirect:/freights";
    }

    @GetMapping("/download/freights.csv")
    public void downloadCsv(HttpServletResponse response) throws ResourceNotFoundException, IllegalAccessException, IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=freights.csv");
        CsvUtil.downloadFreightCsv(response.getWriter(), freightService.getFreights()); ;
    }
}
