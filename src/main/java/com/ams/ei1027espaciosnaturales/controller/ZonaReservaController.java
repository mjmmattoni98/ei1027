//package com.ams.ei1027espaciosnaturales.controller;
//
//import com.ams.ei1027espaciosnaturales.dao.ZonaReservaDAO;
//import com.ams.ei1027espaciosnaturales.model.ZonaReserva;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@Controller
//@RequestMapping("/zona_reserva")
//public class ZonaReservaController extends RolController{
//    private ZonaReservaDAO zonaReservaDAO;
//
//    @Autowired
//    public void setZonaReservaDAO(ZonaReservaDAO zr) {
//        this.zonaReservaDAO = zr;
//    }
//
//    @RequestMapping("/list")
//    public String listZonaReserva(Model model) {
//        model.addAttribute("zonas_reservas", zonaReservaDAO.getZonasReservas());
//        return "zona_reserva/list";
//    }
//
//    @RequestMapping(value = "/add")
//    public String addZonaReserva(Model model) {
//        model.addAttribute("zona_reserva", new ZonaReserva());
//        return "zona_reserva/add";
//    }
//
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String processAddZonaReserva(@ModelAttribute("zona_reserva") ZonaReserva zr,
//                                       BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "zona_reserva/add";
//        }
//        try {
//            zonaReservaDAO.addZonaReserva(zr);
//        }
//        catch (DuplicateKeyException e){
//            throw new EspaciosNaturalesException("Ya está asignada la reserva a la zona", "CPDuplicada", "zona_reserva/add");
//        }
//        catch (DataAccessException e){
//            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
//        }
//        return "redirect:list";
//    }
//
//    @RequestMapping(value = "/delete/{zona}/{reserva}")
//    public String processDeleteZonaReserva(@PathVariable int zona, @PathVariable int reserva) {
//        zonaReservaDAO.deleteZonaReserva(zona, reserva);
//        return "redirect:../list";
//    }
//}
