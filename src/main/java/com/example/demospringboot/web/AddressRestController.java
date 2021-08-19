package com.example.demospringboot.web;

import com.example.demospringboot.domain.Address;
import com.example.demospringboot.domain.AddressRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressRestController {

    private final AddressRepository repository;

    public AddressRestController(AddressRepository repository) {
        this.repository = repository;
    }


    @PostMapping("/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public Address saveAddress(@RequestBody Address employee) {

        return repository.save(employee);
    }


    @GetMapping("/addresses")
    @ResponseStatus(HttpStatus.OK)
    public List<Address> getAllAddresses() {

        return repository.findAll();
    }

    @GetMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Address getAddressById(@PathVariable long id) {

        Address address = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id = " + id));

        return address;
    }

    //Обновление адреса по id
    @PutMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Address updateAddress(@PathVariable("id") long id, @RequestBody Address address) {

        return repository.findById(id)
                .map(entity -> {
                    entity.setCity(address.getCity());
                    entity.setCountry(address.getCountry());
                    entity.setZipCode(address.getZipCode());
                    return repository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id = " + id));
    }



    @PatchMapping("/addresses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAddressById(@PathVariable long id) {
        repository.findById(id)
                .map(address -> {
                    return repository.save(address);
                })
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id = " + id));
    }


    @DeleteMapping("/addresses")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllAddress() {
        repository.deleteAll();
    }
}
