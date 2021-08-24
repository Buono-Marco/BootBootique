package com.mb.bootbootique.controllers;

import com.mb.bootbootique.entities.Boot;
import com.mb.bootbootique.repositories.BootRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/boots")
public class BootController {

    private BootRepository bootRepository;

    public BootController(BootRepository bootRepository) {
        this.bootRepository = bootRepository;
    }

    @GetMapping("/")
    public Iterable<Boot> getAllBoots() {
        return this.bootRepository.findAll();
    }

    @PostMapping("/")
    public Boot addBoot(@RequestBody Boot boot) {
        return this.bootRepository.save(boot);
    }

    @DeleteMapping("/{id}")
    public Boot deleteBoot(@PathVariable("id") Integer id) {
        Optional<Boot> optionalBoot = this.bootRepository.findById(id);

        if(optionalBoot.isEmpty()) {
            return null;
        }

        Boot bootToDelete = optionalBoot.get();
        this.bootRepository.delete(bootToDelete);
        return bootToDelete;
    }

    @PutMapping("/{id}/quantity/increment")
    public Boot incrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> bootOptional = this.bootRepository.findById(id);

        if(bootOptional.isEmpty()) {
            return null;
        }

        Boot bootToUpdate = bootOptional.get();
        bootToUpdate.setQuantity(bootToUpdate.getQuantity() + 1);
        return this.bootRepository.save(bootToUpdate);
    }

    @PutMapping("/{id}/quantity/decrement")
    public Boot decrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> bootOptional = this.bootRepository.findById(id);

        if(bootOptional.isEmpty()) {
            return null;
        }

        Boot bootToUpdate = bootOptional.get();
        if(bootToUpdate.getQuantity() > 0) {
            bootToUpdate.setQuantity(bootToUpdate.getQuantity() - 1);
            return this.bootRepository.save(bootToUpdate);
        } else {
            return bootToUpdate;
        }
    }

}
