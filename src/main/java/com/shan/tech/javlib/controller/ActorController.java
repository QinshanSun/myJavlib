package com.shan.tech.javlib.controller;

import com.shan.tech.javlib.pojo.Actor;
import com.shan.tech.javlib.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actor")
public class ActorController {

  private ActorService actorService;

  @GetMapping("/id")
  public Actor getActor(@RequestParam(name = "Id") Long id){
    return  actorService.findById(id).orElseThrow();
  }

  @GetMapping("label")
  public Actor getActorByLabel(@RequestParam(name = "label") String label){
    return  actorService.findByLabel(label).orElseThrow();
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
