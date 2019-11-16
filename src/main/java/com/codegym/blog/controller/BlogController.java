package com.codegym.blog.controller;

import com.codegym.blog.model.Blog;
import com.codegym.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("blog", blogService.findAll());
        return modelAndView;
    }

    @GetMapping("/create-new-post")
    public ModelAndView showCreateNewPost(){
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("newPost", new Blog());
        return modelAndView;
    }

    @PostMapping("/create-new-post")
    public String createNewPost(@ModelAttribute Blog blog){
        blogService.save(blog);
        return "redirect:/home";
    }

    @GetMapping("/show-content/{id}")
    public ModelAndView showContent(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/content");
        modelAndView.addObject("blog", blogService.findById(id));
        return modelAndView;
    }

    @GetMapping("/edit-content/{id}")
    public ModelAndView showEditContent(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("blog", blogService.findById(id));
        return modelAndView;
    }

    @PostMapping("/edit-content")
    public ModelAndView editContent(@ModelAttribute Blog blog){
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("message", "Edit Blog finish");
        return modelAndView;
    }

    @GetMapping("/delete-post/{id}")
    public ModelAndView showDeletePost(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/delete");
        modelAndView.addObject("blog", blogService.findById(id));
        return modelAndView;
    }

    @PostMapping("/delete-post")
    public String deletePost(@ModelAttribute Blog blog){
        blogService.remove(blog.getId());
        return "redirect:/home";
    }
}