package com.mb.bootbootique.controllers;

import com.mb.bootbootique.entities.Boot;
import com.mb.bootbootique.enums.BootType;
import com.mb.bootbootique.exceptions.QueryNotSupportedException;
import com.mb.bootbootique.repositories.BootRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/boots")
public class BootController {

    private final BootRepository bootRepository;

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

    @GetMapping("/search")
    public List<Boot> searchBoots(
            @RequestParam(required = false) BootType type,
            @RequestParam(required = false) Float size,
            @RequestParam(required = false) String material,
            @RequestParam(name = "quantity", required = false) Integer minQuantity
            ) throws QueryNotSupportedException {
        if(Objects.nonNull(type)) {
            if(Objects.nonNull(size) && Objects.nonNull(material)
                    && Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a type, a size, a material and a minimum quantity
                throw new QueryNotSupportedException(
                        "This query is not supported! Try a different " +
                                "combination of search parameters.");
            } else if(Objects.nonNull(size) && Objects.nonNull(material)) {
                // call the repository method that accepts a type, a size, a material
                throw new QueryNotSupportedException(
                        "This query is not supported! Try a different " +
                                "combination of search parameters.");
            } else if(Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a type, a size and a minimum quantity
                throw new QueryNotSupportedException(
                        "This query is not supported! Try a different " +
                                "combination of search parameters.");
            } else if(Objects.nonNull(size)) {
                // call the repository method that accepts a type and size
                throw new QueryNotSupportedException(
                        "This query is not supported! Try a different " +
                                "combination of search parameters.");
            } else {
                // call the repository method that accepts a type
                return this.bootRepository.findBootsByType(type);
            }
        } else if(Objects.nonNull(size)) {
            if(Objects.nonNull(material) && Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a size, a material and a minimum quantity
                throw new QueryNotSupportedException(
                        "This query is not supported! Try a different " +
                                "combination of search parameters.");
            } else if(Objects.nonNull(material)) {
                // call the repository method that accepts a size and material
                throw new QueryNotSupportedException(
                        "This query is not supported! Try a different " +
                                "combination of search parameters.");
            } else if(Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a size and a minimum quantity
                throw new QueryNotSupportedException(
                        "This query is not supported! Try a different " +
                                "combination of search parameters.");
            } else {
                // call the repository method that accepts a size
                return this.bootRepository.findBootsBySize(size);
            }
        } else if(Objects.nonNull(material)) {
            if(Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a material and a minimum quantity
                throw new QueryNotSupportedException(
                        "This query is not supported! Try a different " +
                                "combination of search parameters.");
            } else {
                // call the repository method that accepts a material
                return this.bootRepository.findBootsByMaterial(material);
            }
        } else if(Objects.nonNull(minQuantity)) {
            // call the repository method that accepts a minimum quantity
            return this.bootRepository.findBootsByQuantityGreaterThan(minQuantity);
        } else {
            throw new QueryNotSupportedException(
                    "This query is not supported! Try a different " +
                            "combination of search parameters.");
        }
    }

}
