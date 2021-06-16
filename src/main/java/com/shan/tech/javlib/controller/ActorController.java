package com.shan.tech.javlib.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/actor")
public class ActorController {

  private ActorService actorService;

  @GetMapping("/id={id}")
  public Actor getActor(@PathVariable(name = "id") Long id) {
    return actorService.findById(id).orElseThrow();
  }

  @GetMapping("/label={label}")
  public List<Actor> getActorByLabel(@PathVariable(name = "label") String label) {
    return actorService.findByLabel(label);
  }

  @GetMapping("/name={name}")
  public List<Actor> getActorByName(@PathVariable(name = "name") String name) {
    return actorService.findActorsByName(name);
  }

  @GetMapping
  public List<Actor> getActors(@RequestParam(name = "id", required = false) Long id,
                               @RequestParam(name = "label", required = false) String label,
                               @RequestParam(name = "name", required = false) String name) {
    List<Actor> actorList = new ArrayList<>();
    if (id != null) {
      Actor actor = actorService.findById(id).orElseThrow();
      actorList.add(actor);
      return actorList;
    } else if (!Strings.isNullOrEmpty(label)) {
      List<Actor> actor = actorService.findByLabel(label);
      actorList.addAll(actor);
      return actorList;
    } else if (!Strings.isNullOrEmpty(name)) {
      actorList = actorService.findActorsByName(name);
    }
    return actorList;
  }


  @GetMapping("/findAllByPage")
  public PageInfo<Actor> findAllByPage(@RequestParam(name = "num") int num,
                                       @RequestParam(name = "size") int size) {
    return actorService.findActorsByPage(num, size);
  }

  @PostMapping
  public void insertActor(@RequestBody Actor actor){
    actorService.insertActor(actor);
  }

  @Autowired
  public void setActorService(ActorService actorService) {
    this.actorService = actorService;
  }
}
