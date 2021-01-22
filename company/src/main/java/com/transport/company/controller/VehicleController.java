package com.transport.company.controller;

import com.transport.company.dto.VehicleCreationDto;
import com.transport.company.entity.Vehicle;
import com.transport.company.exception.ResourceNotFoundException;
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
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("")
    public String showVehicleList(Model model) throws ResourceNotFoundException {
        // 2 as the company ID is hardcoded because I have not implemented login functionality
        model.addAttribute("vehicleList", vehicleService.getVehicles());
        return "redirect:/vehicles/page/1?sort-field=id&sort-dir=asc";
    }

    @GetMapping(value = "/page/{page-number}")
    public String showVehicleList( @PathVariable(name = "page-number") final int pageNo,
                                  @RequestParam(name = "sort-field") final String sortField,
                                  @RequestParam(name = "sort-dir") final String sortDir,
                                  Model model) throws ResourceNotFoundException {
        var page = vehicleService.findPaginated(1, 100, sortField, sortDir);

        model.addAttribute("vehicleList", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "vehicle/vehicles";
    }

    @GetMapping("/addNewVehicle")
    public String showCreateForm(Model model) {
        VehicleCreationDto vehicleCreationForm = new VehicleCreationDto();

        vehicleCreationForm.addVehicle(new Vehicle());
        model.addAttribute("form", vehicleCreationForm);

        return "vehicle/addNewVehicle";
    }

    @GetMapping("/editVehicle/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleService.getVehicle( id);

        model.addAttribute("vehicle", vehicle);
        return "vehicle/editVehicle";
    }

    @PostMapping(value = "/save")
    public String saveBooks(@ModelAttribute VehicleCreationDto form, Model model) throws ResourceNotFoundException {
        for (var vehicle : form.getVehicles()){
            vehicleService.createVehicle( vehicle);
        }

        model.addAttribute("vehicles", vehicleService.getVehicles());

        return "redirect:/vehicles";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Vehicle vehicle,
                             BindingResult result, Model model) throws ResourceNotFoundException {
        if (result.hasErrors()) {
            vehicle.setId(id);
            return "vehicle/editVehicle";
        }

        vehicleService.updateVehicle(vehicle.getId(), vehicle);
        return "redirect:/vehicles";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) throws ResourceNotFoundException {
        vehicleService.deleteVehicle( id);

        return "redirect:/vehicles";
    }

    @GetMapping("/download/vehicles.csv")
    public void downloadCsv(HttpServletResponse response) throws IOException, IOException, ResourceNotFoundException, IllegalAccessException, IntrospectionException, InvocationTargetException, IntrospectionException, InvocationTargetException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=vehicles.csv");
        CsvUtil.downloadVehiclesCsv(response.getWriter(), vehicleService.getVehicles()); ;
    }
}
