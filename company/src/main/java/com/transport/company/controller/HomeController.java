package com.transport.company.controller;

import com.transport.company.entity.Driver;
import com.transport.company.entity.Freight;
import com.transport.company.exception.ResourceNotFoundException;
import com.transport.company.service.ClientService;
import com.transport.company.service.DriverService;
import com.transport.company.service.FreightService;
import com.transport.company.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private DriverService driverService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private FreightService freightService;

    @GetMapping("/index")
    public String showIndex(Model model) { return "index";}

    @GetMapping("/report")
    public String showReport(Model model) throws ResourceNotFoundException {

        model.addAttribute("totalFreightCount", freightService.getFreights().size());
        model.addAttribute("totalPrice", freightService.getFreights()
                                                                    .stream()
                                                                    .mapToDouble(Freight::getPrice)
                                                                    .sum());

        Map<Driver, List<Freight>> driverFreight = driverService.getFreightsForDrivers();
        model.addAttribute("driverFreights", driverFreight);

        Map<Driver, Integer> driverFreightCount = driverService.getFreightCountForDrivers();
        model.addAttribute("driverFreightCount", driverFreightCount);

        Map<Driver, Double> driverEarnings = driverService.getEarningsPerDriver();
        model.addAttribute("driverEarnings", driverEarnings);

        return "report";
    }

}
