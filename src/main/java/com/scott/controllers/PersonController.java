package com.scott.controllers;

import com.scott.models.*;
import com.scott.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/people")
@CrossOrigin(origins="http://localhost:3000")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TagRepository tagRepository;
    @Autowired
    SNSRepository snsRepository;
    @Autowired
    NationRepository nationRepository;
    @Autowired
    ArticleRepository articleRepository;

    @PostMapping("/create")
    public Person createPerson(@RequestBody Person person){
        return personRepository.save(person);
    }

    @GetMapping
    public List<Person> findAllPeople(){
        return personRepository.findAll();
    }
    @GetMapping("/{personId}")
    public Person findPersonById(@PathVariable Long personId){
        return personRepository.findById(personId).orElseThrow(()->new RuntimeException("Person has not been found!"));
    }
    @PutMapping("/{personId}/update")
    public Person updatePerson(@PathVariable Long personId, @RequestBody Person person){
        Person update=this.findPersonById(personId);
        update.setName(person.getName());
        update.setDescription(person.getDescription());
        update.setGender(person.getGender());
        //update.setRoles(person.getRoles());
        //update.setNationality(person.getNationality());
        //update.setNationalTeams(person.getNationalTeams());
        //update.setRoutines(person.getRoutines());
        //update.setRoutinesForCoaches(person.getRoutinesForCoaches());

        update.setBirth((person.getBirth()));
        //update.setSns_addresses(person.getSns_addresses());
        //update.setArticles(person.getArticles());
        //update.setBroadcasts(person.getBroadcasts());
        return personRepository.save(update);
    }

    @DeleteMapping("/{personId}/delete")
    public void deletePerson(@PathVariable Long personId){
        personRepository.deleteById(personId);
    }

    @PutMapping("/{personId}/addRole/{roleId}")
    public Person addRole(@PathVariable Long personId, @PathVariable Long roleId){
        Person person=personRepository.findById(personId).get();
        Role role=roleRepository.findById(roleId).get();
        person.addRole(role);
        return personRepository.save(person);
    }
    @PutMapping("/{personId}/removeRole/{roleId}")
    public Person removeRole(@PathVariable Long personId, @PathVariable Long roleId){
        Person person=personRepository.findById(personId).get();
        Role role=roleRepository.findById(roleId).get();
        person.removeRole(role);
        return personRepository.save(person);
    }
    @GetMapping("/{personId}/roles")
    public Set<Role> findAllRolesForPerson(@PathVariable Long personId){
        Person person=personRepository.findById(personId).get();
        return person.getRoles();
    }
    @GetMapping("/{personId}/tags")
    public Set<Tag> findAllTagsForPerson(@PathVariable Long personId){
        Person person=personRepository.findById(personId).get();
        return person.getTags();
    }
    
    @GetMapping("/{personId}/routines")
    public Set<Routine> findAllRoutinesForPerson(@PathVariable Long personId){
        Person person=personRepository.findById(personId).get();
        return person.getRoutines();
    }
    @PutMapping("/{personId}/addTag/{tagId}")
    public Person addTag(@PathVariable Long personId, @PathVariable Long tagId){
        Person person=personRepository.findById(personId).get();
        Tag tag=tagRepository.findById(tagId).get();
        person.addTag(tag);
        return personRepository.save(person);
    }


    @PutMapping("/{personId}/removeTag/{tagId}")
    public Person removeTag(@PathVariable Long personId, @PathVariable Long tagId){
        Person person=personRepository.findById(personId).get();
        Tag tag=tagRepository.findById(tagId).get();
        person.removeTag(tag);
        return personRepository.save(person);
    }

    @PutMapping("/{personId}/addSNS")
    public Person addSNS(@PathVariable Long personId, @RequestBody SNS sns){
        Person person=personRepository.findById(personId).get();
        sns.setPerson(person);
        snsRepository.save(sns);
        person.addSNS(sns);
        return personRepository.save(person);
    }
    @PutMapping("/{personId}/removeSNS/{snsId}")
    public Person removeSNS(@PathVariable Long personId, @PathVariable Long snsId){
        Person person=personRepository.findById(personId).get();
        SNS sns=snsRepository.findById(snsId).get();
        sns.removePerson(person);
        //person.removeSNS(sns);
        snsRepository.save(sns);
        return personRepository.save(person);
    }

    @PutMapping("/{personId}/addTagAlt")
    public Person addTag(@PathVariable Long personId, @RequestBody Tag tag){
        Person person=personRepository.findById(personId).get();
        tagRepository.save(tag);
        person.addTag(tag);
        return personRepository.save(person);
    }
    @PutMapping("/{personId}/removeDeleteTag/{tagId}")
    public Person removeDeleteTag(@PathVariable Long personId, @PathVariable Long tagId){
        Person person=personRepository.findById(personId).get();
        Tag tag=tagRepository.findById(tagId).get();
        person.removeTag(tag);
        tagRepository.deleteById(tagId);
        return personRepository.save(person);
    }
    @PutMapping("/{personId}/removeTagAlt/{tagId}")
    public Person removeTagAlt(@PathVariable Long personId, @PathVariable Long tagId){
        Person person=personRepository.findById(personId).get();
        Tag tag=tagRepository.findById(tagId).get();
        person.removeTag(tag);

        return personRepository.save(person);
    }


    @PutMapping("/{personId}/removeSNSDelete/{snsId}")
    public Person removeDeleteSNS(@PathVariable Long personId, @PathVariable Long snsId){
        Person person=personRepository.findById(personId).get();
        SNS sns=snsRepository.findById(snsId).get();
        person.removeSNS(sns);
        snsRepository.deleteById(snsId);
        return personRepository.save(person);
    }

    @GetMapping("/{personId}/snss")
    public Set<SNS> findAllSNSForPerson(@PathVariable Long personId){
        Person person=personRepository.findById(personId).get();
        return person.getSns_addresses();
    }

    @PutMapping("/{personId}/assignNation/{nationId}")
    public Person assignNation(@PathVariable Long personId, @PathVariable Long nationId){
        Person person=personRepository.findById(personId).get();
        Nation nation=nationRepository.findById(nationId).get();
        person.setNationality(nation);
        System.out.println("updated!");
        return personRepository.save(person);
    }
    @PutMapping("/{personId}/removeNation/{nationId}")
    public Person removeNation(@PathVariable Long personId, @PathVariable Long nationId){
        Person person=personRepository.findById(personId).get();
        Nation nation=nationRepository.findById(nationId).get();
        person.removeNationality(nation);
        System.out.println("updated!");
        return personRepository.save(person);
    }

    @GetMapping("/{personId}/articles")
    public Set<Article> findArticlesForPerson(@PathVariable Long personId){
        Person person=personRepository.findById(personId).get();
        return person.getArticles();
    }
    @PutMapping("/{personId}/addArticle/{articleId}")
    public Person addArticle(@PathVariable Long personId, @PathVariable Long articleId){
        Person person=personRepository.findById(personId).get();
        Article article=articleRepository.findById(articleId).get();
        person.addArticle(article);
        return personRepository.save(person);
    }

    @PutMapping("/{personId}/removeArticle/{tagId}")
    public Person removeArticle(@PathVariable Long personId, @PathVariable Long articleId){
        Person person=personRepository.findById(personId).get();
        Article article=articleRepository.findById(articleId).get();
        person.removeArticle(article);
        return personRepository.save(person);
    }

    @GetMapping("/{offset}/{pageSize}/{field}")
    public Page<Person> findPeoplePaginationAndSorting(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
        return personRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }

    @GetMapping("/{personId}/broadcasts")
    public Set<Broadcast> findBroadcastsForPerson(@PathVariable Long personId){
        Person person=personRepository.findById(personId).get();
        return person.getBroadcasts();
    }
}
