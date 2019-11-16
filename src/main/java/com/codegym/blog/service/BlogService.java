package com.codegym.blog.service;

import com.codegym.blog.model.Blog;

public interface BlogService {
    Iterable<Blog> findAll();
    Blog findById(Long id);
    void save (Blog blog);
    void remove(Long id);
}
