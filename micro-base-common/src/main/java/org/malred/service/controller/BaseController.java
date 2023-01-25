package org.malred.service.controller;

import org.malred.service.config.Token;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通用controller,定义通用crud方法
 */
public abstract class BaseController<T> extends Token {
    @GetMapping("/findAll")
    public abstract List<T> findAll();

    @GetMapping("/find/{id}")
    public abstract T findById(@PathVariable Long id);

    @PostMapping("/save")
    public abstract T insert();

    @PutMapping("/save")
    public abstract T update();

    @DeleteMapping("/delete/{id}")
    public abstract void delete(@PathVariable Long id);
}
