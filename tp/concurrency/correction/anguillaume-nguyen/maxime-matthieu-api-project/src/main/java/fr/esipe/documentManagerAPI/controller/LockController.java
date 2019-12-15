package fr.esipe.documentManagerAPI.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import fr.esipe.documentManagerAPI.exceptions.Conflict;
import fr.esipe.documentManagerAPI.exceptions.GenericException;
import fr.esipe.documentManagerAPI.exceptions.NotFound;
import fr.esipe.documentManagerAPI.model.DocumentModel;
import fr.esipe.documentManagerAPI.model.Lock;
import fr.esipe.documentManagerAPI.service.DocumentService;
import fr.esipe.documentManagerAPI.service.LockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/documents")
public class LockController {

    @Autowired
    private LockService serv;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/{id}/lock", method = RequestMethod.POST)
    public Optional<Lock> create(@PathVariable int id) {
        System.out.println("------> : create");
        logger.debug("create lock.");
        Date currentDate = new Date();
        Lock loc = new Lock("Maxime", currentDate, id);
        if (serv.findLockByDocId(id) == null) {
            serv.createLock(loc);
        } else {
            throw new Conflict();
        }
        return serv.findLockById(id);
    }

    /*
    @RequestMapping(value = "/{id}/lock", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Lock> getActiveIsTrueAndById(@PathVariable int id){
        logger.debug("Getting lock with id = {}", id);
        return serv.findLockByDocId(id);
    }
    */

    @RequestMapping(value = "/{id}/lock", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Lock> getById(@PathVariable int id){
        logger.debug("Getting lock with id = {}", id);
        if (serv.findLockByDocId(id) == null){
            throw new GenericException(HttpStatus.NO_CONTENT, "Aucun verrou n'est posé.");
        }
        return serv.findLockById(id);
    }

    @RequestMapping(value = "/{id}/lock", method = RequestMethod.DELETE)
    public String delete(@PathVariable Integer id){
        if (serv.findLockByDocId(id) == null){
            throw new NotFound();
        }else {
            serv.deleteLockByDocId(id);
            throw  new GenericException(HttpStatus.NOT_FOUND, "Le verrou est supprimé");
        }
    }




}
