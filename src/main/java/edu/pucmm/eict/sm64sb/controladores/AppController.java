package edu.pucmm.eict.sm64sb.controladores;

import edu.pucmm.eict.sm64sb.entidades.Foto;
import edu.pucmm.eict.sm64sb.repositorios.FotoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

@Controller
@RequestMapping(path = "/")
public class AppController {

    FotoRepository fotoRepository;

    public AppController(FotoRepository fotoRepository) {
        this.fotoRepository = fotoRepository;
    }

    @RequestMapping(path = "/")
    public String index(Model model){

        return "redirect:/listar";
    }

    @RequestMapping(path = "/listar")
    public String listar(Model model){
        model.addAttribute("fotos", fotoRepository.findAll());
        return "/listar";
    }

    @PostMapping(path = "/procesarFoto")
    public String procesarFoto(@RequestParam(name = "foto") MultipartFile file) throws IOException {
        // Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        String encodedString = Base64.getEncoder().encodeToString(bytes);
        //creando salvando el objeto.
        Foto foto = new Foto(file.getOriginalFilename(), file.getContentType(), encodedString);
        fotoRepository.save(foto);
        return "redirect:/listar";
    }

    @GetMapping(path = "/visualizar/{id}")
    public String visualizarFoto( @PathVariable(name = "id") Long id, Model model, RedirectAttributes redirectAttributes){
        Foto foto = fotoRepository.findById(id).orElse(null);
        if(foto==null){
            redirectAttributes.addFlashAttribute("error", "Foto no existe");
            return "redirect:/listar";
        }
        model.addAttribute("foto", foto);
        return "/visualizar";
    }

    @GetMapping(path = "/eliminar/{id}")
    public String borrarFoto(@PathVariable(name = "id") Long id){
        fotoRepository.deleteById(id);
        return "redirect:/listar";
    }

}
