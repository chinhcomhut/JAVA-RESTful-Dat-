package com.codegym.blog.controller;
import com.codegym.blog.model.Blog;
import com.codegym.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ApiBlogController {
    @Autowired
//    private CustomerService customerService;
    private BlogService blogService;

    //-------------------Retrieve All Customers--------------------------------------------------------

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<List<Blog>> listAllCustomers() {
        Iterable<Blog> blogs = blogService.findAll();
//        if (c.isEmpty()) {
//            return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
//        }
        return new ResponseEntity<List<Blog>>((List<Blog>) blogs, HttpStatus.OK);
    }

    //-------------------Retrieve Single Customer--------------------------------------------------------

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Blog> getCustomer(@PathVariable("id") long id) {
        System.out.println("Fetching Customer with id " + id);
        Blog customer = blogService.findById(id);
        if (customer == null) {
            System.out.println("Customer with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Blog>(customer, HttpStatus.OK);
    }

    //-------------------Create a Customer--------------------------------------------------------

    @RequestMapping(value = "/customers/", method = RequestMethod.POST)
    public ResponseEntity<Void> createCustomer(@RequestBody Blog blog, UriComponentsBuilder ucBuilder) {
//        System.out.println("Creating Customer " + blog.getLastName());
        blogService.save(blog);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/customers/{id}").buildAndExpand(blog.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //------------------- Update a Customer --------------------------------------------------------

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Blog> updateCustomer(@PathVariable("id") long id, @RequestBody Blog blog) {
        System.out.println("Updating Customer " + id);

       Blog currentCustomer = blogService.findById(id);

        if (currentCustomer == null) {
            System.out.println("Customer with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }

//        currentCustomer.setFirstName(customer.getFirstName());
//        currentCustomer.setLastName(customer.getLastName());
//        currentCustomer.setId(customer.getId());

       blogService.save(currentCustomer);
        return new ResponseEntity<Blog>(currentCustomer, HttpStatus.OK);
    }

    //------------------- Delete a Customer --------------------------------------------------------

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Blog> deleteCustomer(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Customer with id " + id);

        Blog customer = blogService.findById(id);
        if (customer == null) {
            System.out.println("Unable to delete. Customer with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }

        blogService.remove(id);
        return new ResponseEntity<Blog>(HttpStatus.NO_CONTENT);
    }
}
