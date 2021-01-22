package com.transport.company.controller;

import com.transport.company.dto.DriverCreationDto;
import com.transport.company.dto.DriverQualificationOptionsDto;
import com.transport.company.entity.Driver;
import com.transport.company.entity.DriverQualificationEnum;
import com.transport.company.exception.ResourceNotFoundException;
import com.transport.company.service.DriverService;
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
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("")
    public String showDriverList(Model model) throws ResourceNotFoundException {
        // 2 as the company ID is hardcoded because I have not implemented login functionality
        model.addAttribute("driverList", driverService.getDrivers());
        return "redirect:/drivers/page/1?sort-field=id&sort-dir=asc";
    }

    @GetMapping(value = "/page/{page-number}")
    public String showDriverList( @PathVariable(name = "page-number") final int pageNo,
                                  @RequestParam(name = "sort-field") final String sortField,
                                  @RequestParam(name = "sort-dir") final String sortDir,
            Model model) throws ResourceNotFoundException {
        var page = driverService.findPaginated(1, 100, sortField, sortDir);

        model.addAttribute("driverList", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("totalDrivers", (long) page.getContent().size());

        return "driver/drivers";
    }

    @GetMapping("/addNewDriver")
    public String showCreateForm(Model model) {
        DriverCreationDto driverCreationForm = new DriverCreationDto();

        driverCreationForm.addDriver(new Driver());
        model.addAttribute("form", driverCreationForm);

        return "driver/addNewDriver";
    }

    @GetMapping("/editDriver/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) throws ResourceNotFoundException {
        Driver driver = driverService.getDriver(id);
        var driverQualifications = driver.getDriverQualifications().stream().map(Enum::toString).collect(Collectors.toList());

        model.addAttribute("qualifications", new DriverQualificationOptionsDto().getOptions().values());
        model.addAttribute("driverQualifications", driverQualifications);
        model.addAttribute("driver", driver);
        return "driver/editDriver";
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable("id") long id, Model model) throws ResourceNotFoundException {
        Driver driver = driverService.getDriver(id);
        var driverQualifications = driver.getDriverQualifications().stream().map(Enum::toString).collect(Collectors.toList());

        model.addAttribute("qualifications", new DriverQualificationOptionsDto().getOptions().values());
        model.addAttribute("driverQualifications", driverQualifications);
        model.addAttribute("driver", driver);
        return "driver/driverInfo";
    }

    @PostMapping(value = "/save")
    public String saveBooks(@ModelAttribute DriverCreationDto form, Model model) throws ResourceNotFoundException {
        for (var driver : form.getDrivers()){
            driverService.createDriver(driver);
        }

        model.addAttribute("drivers", driverService.getDrivers());

        return "redirect:/drivers";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Driver driver,
                             BindingResult result, Model model) throws ResourceNotFoundException {
        if (result.hasErrors()) {
            driver.setId(id);
            return "driver/editDriver";
        }

        driverService.updateDriver( driver.getId(), driver);
        return "redirect:/drivers";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) throws ResourceNotFoundException {
        driverService.deleteDriver(id);

        return "redirect:/drivers";
    }

    @GetMapping("/download/drivers.csv")
    public void downloadCsv(HttpServletResponse response) throws IOException, IOException, ResourceNotFoundException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=drivers.csv");
        CsvUtil.downloadDriverCsv(response.getWriter(), driverService.getDrivers()); ;
    }
}
